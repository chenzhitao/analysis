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

import com.sensorsdata.webhook.entry.SfWebhookEntry;

import java.util.List;

/**
 * 实现处理逻辑
 * 参考：
 * https://manual.sensorsdata.cn/sf/latest/webhook_integration-35685104.html
 * https://manual.sensorsdata.cn/sf/latest/page-35685091.html
 */
public interface SfWebhookProcessor {
  void handleWebhookEntries(List<SfWebhookEntry> events);
}
