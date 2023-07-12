package com.elai.domain.third;

import java.io.Serializable;


public class SendMessageReqVO implements Serializable {
    //返回结果 0，正常，1异常
    private String  code;
    //返回备注
    private String  message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

