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
import com.sensorsdata.webhook.entry.SfWebhookRequestEntry;
import com.sensorsdata.webhook.entry.SfWebhookResponseEntry;
import com.sensorsdata.webhook.middleman.SfWebhookMiddleManProcessor;
import com.sensorsdata.webhook.server.EndpointServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpMethod;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 中间人转发请求样例，即接到 SF 的请求后，框架调用 handleWebhookRequest，构造并返回一个 Request，框架会帮你执行这个请求
 * handleNewEndpointResponse 可以用于处理远端返回的请求，返回 SF 需要的格式，不实现或返回空被认为全部处理成功
 */
public class MiddleManServer {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void main(String[] args) throws Exception {
    EndpointServer endpointServer = Bootstrap.builder()
        .localPort(12378)
        .middleManProcessor(new SfWebhookMiddleManProcessor() {
          @Override
          public Request handleWebhookRequest(List<SfWebhookRequestEntry> requestEntries, HttpClient httpClient)
              throws Exception {
            // 接到一组请求后，拼装成一个 JSON，以 POST 的方式请求新的服务器
            Map<String, Object> requestBodyMap = new LinkedHashMap<>();
            requestBodyMap.put("proxy_time", System.currentTimeMillis());
            List<String> userIds = new ArrayList<>();
            for (SfWebhookRequestEntry requestEntry : requestEntries) {
              userIds.add(requestEntry.getUserProfile().getFirstId());
            }
            requestBodyMap.put("user_id", userIds);
            String requestBody = OBJECT_MAPPER.writeValueAsString(requestBodyMap);
            // 拼装结果即 {"proxy_time":1566208055297,"user_id":["ABC","DEF"]}

            Request request = httpClient.newRequest("https://webhook.site/155648c7-ff47-4328-b67e-87cea674d972")
                .method(HttpMethod.POST)
                .header("Hello", "World")
                .content(new StringContentProvider("application/json", requestBody, StandardCharsets.UTF_8));

            // 只需要构造 Request，返回给框架异步调用
            return request;
          }

          @Override
          public List<SfWebhookResponseEntry> handleNewEndpointResponse(int responseStatusCode, String responseBody,
              List<SfWebhookRequestEntry> requestEntries) throws Exception {
            System.out.println("response from remote: " + responseBody);
            return Collections.emptyList();
          }
        })
        .build()
        .startServer();

    endpointServer.join();
  }
}
