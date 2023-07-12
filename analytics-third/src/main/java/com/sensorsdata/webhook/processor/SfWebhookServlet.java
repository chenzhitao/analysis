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

package com.sensorsdata.webhook.processor;

import com.sensorsdata.webhook.common.SfUtils;
import com.sensorsdata.webhook.entry.SfWebhookEntry;
import com.sensorsdata.webhook.entry.SfWebhookRequestEntry;
import com.sensorsdata.webhook.entry.SfWebhookResponseEntry;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.IO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SfWebhookServlet extends HttpServlet {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  private final SfWebhookProcessor webhookProcessor;
  private final byte[] secretTokenForSignatureCheck;

  public SfWebhookServlet(SfWebhookProcessor webhookProcessor, byte[] secretTokenForSignatureCheck) {
    this.webhookProcessor = webhookProcessor;
    this.secretTokenForSignatureCheck = secretTokenForSignatureCheck;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    byte[] requestBodyBytes;
    try {
      // 判断请求是否通过 Gzip 压缩
      if ("application/octet-stream".equals(req.getContentType())) {
        requestBodyBytes = IO.readBytes(new GZIPInputStream(req.getInputStream()));
      } else {
        requestBodyBytes = IO.readBytes(req.getInputStream());
      }
      if (requestBodyBytes == null || requestBodyBytes.length == 0) {
        throw new IOException("request body is blank");
      }
    } catch (IOException e) {
      log.warn("can't read input stream.", e);
      resp.setStatus(HttpStatus.BAD_REQUEST_400);
      return;
    }

    if (!SfUtils.checkSignature(req, requestBodyBytes, secretTokenForSignatureCheck)) {
      resp.setStatus(HttpStatus.FORBIDDEN_403);
      return;
    }

    List<SfWebhookRequestEntry> requestEntries;
    try {
      requestEntries =
          OBJECT_MAPPER.readValue(requestBodyBytes, new TypeReference<List<SfWebhookRequestEntry>>() {
          });
    } catch (IOException e) {
      log.warn("can't parse request.", e);
      resp.setStatus(HttpStatus.BAD_REQUEST_400);
      return;
    }

    if (requestEntries.isEmpty()) {
      log.warn("empty request.");
      resp.setStatus(HttpStatus.OK_200);
      return;
    }
    log.debug("begin process. [request='{}']", requestEntries);

    List<SfWebhookEntry> webhookEvents = new ArrayList<>(requestEntries.size());
    for (SfWebhookRequestEntry requestEntry : requestEntries) {
      SfWebhookEntry sfWebhookEntry = new SfWebhookEntry();
      sfWebhookEntry.setRequest(requestEntry);
      sfWebhookEntry.setResponse(new SfWebhookResponseEntry());
      webhookEvents.add(sfWebhookEntry);
    }

    webhookProcessor.handleWebhookEntries(webhookEvents);

    List<SfWebhookResponseEntry> responseEntries = new ArrayList<>(requestEntries.size());
    for (SfWebhookEntry webhookEvent : webhookEvents) {
      responseEntries.add(webhookEvent.getResponse());
    }
    log.debug("done process. [response='{}']", responseEntries);

    try {
      resp.getOutputStream().write(OBJECT_MAPPER.writeValueAsBytes(responseEntries));
    } catch (IOException e) {
      log.warn("can't write result. [events='{}']", webhookEvents);
      log.warn("exception detail", e);
      resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
    }
  }
}