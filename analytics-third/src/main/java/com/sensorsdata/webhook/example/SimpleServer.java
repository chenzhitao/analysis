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

package com.sensorsdata.webhook.example;

import com.sensorsdata.webhook.Bootstrap;
import com.sensorsdata.webhook.entry.SfWebhookEntry;
import com.sensorsdata.webhook.processor.SfWebhookProcessor;
import com.sensorsdata.webhook.server.EndpointServer;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 一个简单的 Server，将接到的请求打印出来
 */
public class SimpleServer {

  public static void main(String[] args) throws Exception {
    EndpointServer endpointServer = Bootstrap.builder()
        .localPort(12379)
        .webhookProcessor(new SfWebhookProcessor() {
          private AtomicLong eventCounter = new AtomicLong(0);

          @Override
          public void handleWebhookEntries(List<SfWebhookEntry> events) {
            // 在这处理一批请求，并设置是否处理成功
            for (SfWebhookEntry webhookEvent : events) {
              long count = eventCounter.incrementAndGet();
              System.out.printf("webhook request [%d]: %s%n", count, webhookEvent.getRequest());

              if (count == 3) {
                webhookEvent.getResponse().failed("some reason");
              } else {
                webhookEvent.getResponse().succeeded();
              }
            }
          }
        })
        .build()
        .startServer();

    endpointServer.join();
  }
}
