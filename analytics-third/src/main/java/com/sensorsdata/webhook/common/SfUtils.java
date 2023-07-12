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

package com.sensorsdata.webhook.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@UtilityClass
public class SfUtils {

  public static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

  static {
    DEFAULT_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    DEFAULT_OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
  }

  public static boolean checkSignature(HttpServletRequest request, byte[] requestBodyBytes, byte[] secretToken) {
    if (secretToken == null) {
      // 没有提供 token 则不需要校验
      return true;
    }

    String signatureHeader = request.getHeader(Constant.HEADER_SIGNATURE);
    // 如果 header 为空，认为非法请求
    if (StringUtils.isBlank(signatureHeader)) {
      String requestBody;
      try {
        requestBody = new String(requestBodyBytes, StandardCharsets.UTF_8);
      } catch (Exception e) {
        requestBody = Base64.encodeBase64String(requestBodyBytes);
      }
      log.warn("invalid request. can't find sign header, please check config. [request='{}']", requestBody);
      return false;
    }

    String expectedSign = HmacUtils.hmacSha1Hex(secretToken, requestBodyBytes);
    if (!signatureHeader.equalsIgnoreCase(expectedSign)) {
      String requestBody;
      try {
        requestBody = new String(requestBodyBytes, StandardCharsets.UTF_8);
      } catch (Exception e) {
        requestBody = Base64.encodeBase64String(requestBodyBytes);
      }
      log.warn("check sign failed. [requestSign='{}', expectedSign='{}', requestBody='{}']", signatureHeader,
          expectedSign, requestBody);
      return false;
    }
    return true;
  }
}
