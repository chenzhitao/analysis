package com.elai.third.tencent.sample.dto;

public class SendSmsDto {

    private  String secretId;
    private  String secretKey;
    private  String region ;
    private  String smsSdkAppId;
    private  String templateId;
    private  String[] phoneNumberSet;

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSmsSdkAppId() {
        return smsSdkAppId;
    }

    public void setSmsSdkAppId(String smsSdkAppId) {
        this.smsSdkAppId = smsSdkAppId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String[] getPhoneNumberSet() {
        return phoneNumberSet;
    }

    public void setPhoneNumberSet(String[] phoneNumberSet) {
        this.phoneNumberSet = phoneNumberSet;
    }
}
