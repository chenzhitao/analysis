package com.elai.third.tencent.sample.dto;

//套餐包信息统计
public class SmsPackagesStatisticsDto {

    private  String secretId;
    private  String secretKey;
    private  String region ;
    private  String smsSdkAppId;
    private  Long tLimit;
    private  Long offset;
    private  String beginTime;
    private  String endTime;

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

    public Long gettLimit() {
        return tLimit;
    }

    public void settLimit(Long tLimit) {
        this.tLimit = tLimit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
