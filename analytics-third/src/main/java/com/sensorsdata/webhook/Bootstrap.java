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

package com.sensorsdata.webhook;

import com.sensorsdata.webhook.middleman.SfWebhookAsyncMiddleManServlet;
import com.sensorsdata.webhook.middleman.SfWebhookMiddleManProcessor;
import com.sensorsdata.webhook.processor.SfWebhookProcessor;
import com.sensorsdata.webhook.processor.SfWebhookServlet;
import com.sensorsdata.webhook.server.EndpointServer;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServlet;

@Builder
public class Bootstrap {

  /**
   * 本地端口
   */
  int localPort;

  /**
   * 实现 SfWebhookMiddleManProcessor 接口的处理类实例
   */
  SfWebhookMiddleManProcessor middleManProcessor;

  /**
   * 实现 SfWebhookProcessor 接口的处理实例
   */
  SfWebhookProcessor webhookProcessor;

  /**
   * 用于校验数据是来自服务器的 secret token
   */
  String secretTokenForSignatureCheck;

  /**
   * jetty 服务的线程池最大大小，最小为 8
   */
  int serverThreadPoolMaxSize;

  public EndpointServer startServer() throws Exception {
    if (webhookProcessor != null && middleManProcessor != null) {
      throw new Exception("can have only one of SfWebhookProcessor & SfWebhookMiddleManProcessor");
    }
    System.out.println("webhook注释");
    byte[] secretTokenBytes = null;
    if (StringUtils.isNotBlank(secretTokenForSignatureCheck)) {
      secretTokenBytes = secretTokenForSignatureCheck.getBytes(StandardCharsets.UTF_8);
    }
    System.out.println("webhook注释");
    HttpServlet httpServlet;
    if (webhookProcessor != null) {
      httpServlet = new SfWebhookServlet(webhookProcessor, secretTokenBytes);
    } else {
      httpServlet = new SfWebhookAsyncMiddleManServlet(middleManProcessor, secretTokenBytes);
    }

    serverThreadPoolMaxSize = Math.max(8, serverThreadPoolMaxSize);

    EndpointServer endpointServer = new EndpointServer();
    endpointServer.startServer(localPort, httpServlet, serverThreadPoolMaxSize);
    return endpointServer;
  }
}
