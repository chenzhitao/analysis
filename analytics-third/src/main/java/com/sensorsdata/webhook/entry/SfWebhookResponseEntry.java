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

package com.sensorsdata.webhook.entry;

import lombok.Data;

@Data
public class SfWebhookResponseEntry {
  /**
   * 请求处理是否成功
   */
  boolean succeed = true;

  /**
   * 处理失败的原因
   */
  String failReason;

  public void succeeded() {
    succeed = true;
  }

  public void failed(String reason) {
    succeed = false;
    failReason = reason;
  }
}
