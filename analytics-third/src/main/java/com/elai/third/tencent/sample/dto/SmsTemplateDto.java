package com.elai.third.tencent.sample.dto;

public class SmsTemplateDto {

    private  String secretId;
    private  String secretKey;
    private  String region ;


    private  Long templateId;
    private  String templateName;
    private  String templateContent ;

    private  Long smsType;
    private  Long international;
    private  String remark ;


    private Long[] TemplateIdSet;


    public Long[] getTemplateIdSet() {
        return TemplateIdSet;
    }

    public void setTemplateIdSet(Long[] templateIdSet) {
        TemplateIdSet = templateIdSet;
    }

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

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public Long getSmsType() {
        return smsType;
    }

    public void setSmsType(Long smsType) {
        this.smsType = smsType;
    }

    public Long getInternational() {
        return international;
    }

    public void setInternational(Long international) {
        this.international = international;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
