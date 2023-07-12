/*
 * Copyright 2019 Sensors Data Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sensorsdata.webhook.middleman;

import com.sensorsdata.webhook.common.SfUtils;
import com.sensorsdata.webhook.entry.SfWebhookRequestEntry;
import com.sensorsdata.webhook.entry.SfWebhookResponseEntry;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.http.HttpClientTransportOverHTTP;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.ProcessorUtils;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SfWebhookAsyncMiddleManServlet extends HttpServlet {

  private final SfWebhookMiddleManProcessor middleManProcessor;
  private final HttpClient httpClient;
  private final byte[] secretTokenForSignatureCheck;

  public SfWebhookAsyncMiddleManServlet(SfWebhookMiddleManProcessor middleManProcessor,
      byte[] secretTokenForSignatureCheck) {
    this.middleManProcessor = middleManProcessor;
    this.secretTokenForSignatureCheck = secretTokenForSignatureCheck;
    int selectors = Math.max(1, ProcessorUtils.availableProcessors() / 2);
    this.httpClient = new HttpClient(new HttpClientTransportOverHTTP(selectors), new SslContextFactory.Client());
  }

  @Override
  public void init() throws ServletException {
    try {
      httpClient.start();
    } catch (Exception e) {
      log.warn("start jetty http client with exception", e);
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    AsyncContext asyncContext = req.startAsync();
    ServletInputStream servletInputStream = req.getInputStream();
    // 请求是否通过 Gzip 压缩
    boolean isGzip = "application/octet-stream".equals(req.getContentType());
    SfWebhookRequestBodyReadListener readListener =
        new SfWebhookRequestBodyReadListener(asyncContext, httpClient, isGzip);
    servletInputStream.setReadListener(readListener);
  }

  /**
   * 用于异步读取来自 SF 服务器的请求，以及之后发起新的请求
   */
  private class SfWebhookRequestBodyReadListener implements ReadListener {

    private static final int BUFFER_SIZE = 8 * 1024;
    private AsyncContext asyncContext;
    private ByteArrayOutputStream receiveBuffer;
    private HttpClient httpClient;
    private boolean isGzip;

    SfWebhookRequestBodyReadListener(AsyncContext asyncContext, HttpClient httpClient, boolean isGzip) {
      this.asyncContext = asyncContext;
      this.receiveBuffer = new ByteArrayOutputStream(asyncContext.getRequest().getContentLength());
      this.httpClient = httpClient;
      this.isGzip = isGzip;
    }

    @Override
    public void onDataAvailable() {
      try {
        ServletInputStream inputStream = asyncContext.getRequest().getInputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        while (inputStream.isReady() && !inputStream.isFinished()) {
          int fllReadSize = inputStream.read(buffer, 0, Math.min(inputStream.available(), BUFFER_SIZE));
          if (fllReadSize == -1) {
            break;
          }
          receiveBuffer.write(buffer, 0, fllReadSize);
        }
      } catch (IOException e) {
        log.warn("read request with exception", e);
        asyncContext.complete();
      }
    }

    @Override
    public void onAllDataRead() {
      if (receiveBuffer.size() == 0) {
        ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.BAD_REQUEST_400);
        asyncContext.complete();
        return;
      }

      byte[] requestBodyBytes = receiveBuffer.toByteArray();
      if (isGzip) {
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(requestBodyBytes))) {
          requestBodyBytes = IO.readBytes(gzip);
        } catch (IOException e) {
          log.warn("can not decompress gzip request body bytes", e);
          ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE_415);
          asyncContext.complete();
          return;
        }
      }

      if (!SfUtils.checkSignature((HttpServletRequest) asyncContext.getRequest(), requestBodyBytes,
          secretTokenForSignatureCheck)) {
        ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.FORBIDDEN_403);
        asyncContext.complete();
        return;
      }

      List<SfWebhookRequestEntry> requestEntries;
      try {
        requestEntries =
            SfUtils.DEFAULT_OBJECT_MAPPER.readValue(requestBodyBytes, new TypeReference<List<SfWebhookRequestEntry>>() {
            });
      } catch (IOException e) {
        int fllSize = requestBodyBytes.length;
        log.warn("can't parse request. [fll_size={}]", fllSize);
        log.warn("exception detail", e);
        ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.BAD_REQUEST_400);
        asyncContext.complete();
        return;
      }
      log.info("webhook request. [requests='{}']", requestEntries);

      Request newRequest;
      try {
        newRequest = middleManProcessor.handleWebhookRequest(requestEntries, httpClient);
      } catch (Exception e) {
        log.warn("handle webhook request with exception. [requests='{}']", requestEntries);
        log.warn("exception detail", e);
        ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
        asyncContext.complete();
        return;
      }

      if (newRequest == null) {
        log.warn("request is null. [requests='{}']", requestEntries);
        ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.OK_200);
        asyncContext.complete();
        return;
      }

      newRequest.send(new SfRemoteResponseListener(asyncContext, middleManProcessor, requestEntries));
    }

    @Override
    public void onError(Throwable t) {
      log.warn("process response with exception", t);
      if (asyncContext.getResponse() instanceof HttpServletResponse) {
        ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.BAD_REQUEST_400);
      }
      asyncContext.complete();
    }


    /**
     * 用于读取和处理转发后的服务器的请求
     */
    protected class SfRemoteResponseListener extends BufferingResponseListener {

      private static final int REMOTE_RESPONSE_MAX_LENGTH = 2 * 1024 * 1024;

      private AsyncContext asyncContext;
      private SfWebhookMiddleManProcessor middleManProcessor;
      private List<SfWebhookRequestEntry> requestEntries;

      SfRemoteResponseListener(AsyncContext asyncContext,
          SfWebhookMiddleManProcessor middleManProcessor,
          List<SfWebhookRequestEntry> requestEntries) {
        super(REMOTE_RESPONSE_MAX_LENGTH);
        this.asyncContext = asyncContext;
        this.middleManProcessor = middleManProcessor;
        this.requestEntries = requestEntries;
      }

      @Override
      public void onComplete(Result result) {
        try {
          if (result.isSucceeded()) {
            String responseBody = getContentAsString();
            log.debug("new endpoint response. [response='{}']", responseBody);
            List<SfWebhookResponseEntry> sfWebhookResponseEntries;
            try {
              sfWebhookResponseEntries =
                  middleManProcessor.handleNewEndpointResponse(result.getResponse().getStatus(), responseBody,
                      requestEntries);
            } catch (Exception e) {
              log.warn("handle new endpoint response with exception. [response='{}']", responseBody);
              log.warn("exception detail", e);
              ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
              return;
            }
            if (CollectionUtils.isEmpty(sfWebhookResponseEntries)) {
              // 没有实现，直接返回成功
              return;
            }
            try {
              asyncContext.getResponse().getWriter().print(
                  SfUtils.DEFAULT_OBJECT_MAPPER.writeValueAsString(sfWebhookResponseEntries));
            } catch (IOException e) {
              log.warn("can't write response. [new_response='{}']", sfWebhookResponseEntries);
              log.warn("exception detail", e);
              ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
            }
          } else {
            ((HttpServletResponse) asyncContext.getResponse()).setStatus(HttpStatus.BAD_GATEWAY_502);
          }
        } finally {
          asyncContext.complete();
        }
      }
    }
  }
}
