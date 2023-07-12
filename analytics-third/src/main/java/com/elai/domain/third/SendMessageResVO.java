package com.elai.domain.third;

import java.io.Serializable;


public class SendMessageResVO implements Serializable {

    //多个用,隔开
    private  String telephone;
    //推送参数ID
    private  Long   channelPushId;

    //模板编号
    private  String  templateCode;

    //发送参数
    private  String templateParam;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getChannelPushId() {
        return channelPushId;
    }

    public void setChannelPushId(Long channelPushId) {
        this.channelPushId = channelPushId;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}

