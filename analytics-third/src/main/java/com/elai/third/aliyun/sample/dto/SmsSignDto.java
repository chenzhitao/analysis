package com.elai.third.aliyun.sample.dto;

import com.aliyun.dysmsapi20170525.models.AddSmsSignRequest;

import java.util.List;

public class SmsSignDto {

    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private Integer signSource;
    private String  remark;
    private List<SignFileDto>  signFileDto;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Integer getSignSource() {
        return signSource;
    }

    public void setSignSource(Integer signSource) {
        this.signSource = signSource;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SignFileDto> getSignFileDto() {
        return signFileDto;
    }

    public void setSignFileDto(List<SignFileDto> signFileDto) {
        this.signFileDto = signFileDto;
    }
}
