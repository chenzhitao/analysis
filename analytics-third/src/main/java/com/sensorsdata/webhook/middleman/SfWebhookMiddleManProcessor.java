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

import com.sensorsdata.webhook.entry.SfWebhookRequestEntry;
import com.sensorsdata.webhook.entry.SfWebhookResponseEntry;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;

import java.util.Collections;
import java.util.List;

public abstract class SfWebhookMiddleManProcessor {

  /**
   * 将 webhook 请求转换成向下游请求的内容
   *
   * @param requestEntries 原 webhook 请求
   * @param httpClient
   * @return 向下游请求的 request body
   */
  public abstract Request handleWebhookRequest(List<SfWebhookRequestEntry> requestEntries,
      HttpClient httpClient) throws Exception;

  /**
   * 如果目标服务有 response body，将调用该请求将 response 转化为对 sf 请求结果
   *
   * @param responseStatusCode 目标服务的返回码
   * @param responseBody       目标服务的 response body，可能为 null
   * @param requestEntries     本次请求对应的 SF request
   * @return 返回空集合表示全部成功
   */
  public List<SfWebhookResponseEntry> handleNewEndpointResponse(
      int responseStatusCode,
      String responseBody,
      List<SfWebhookRequestEntry> requestEntries) throws Exception {
    return Collections.emptyList();
  }
}
