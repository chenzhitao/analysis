package com.elai.third.tencent.sample.dto;

public class SmsSignDto {

    private  String secretId;
    private  String secretKey;
    private  String region ;


    private  Long signId;
    private  String signName;
    private  Long signType ;
    private  Long documentType;
    private  Long international;
    private  Long signPurpose ;
    private  String proofImage;

    private Long[] signIdSet;

    private String commissionImage;
    private String Remark;

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getCommissionImage() {
        return commissionImage;
    }

    public void setCommissionImage(String commissionImage) {
        this.commissionImage = commissionImage;
    }

    public Long[] getSignIdSet() {
        return signIdSet;
    }

    public void setSignIdSet(Long[] signIdSet) {
        this.signIdSet = signIdSet;
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

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Long getSignType() {
        return signType;
    }

    public void setSignType(Long signType) {
        this.signType = signType;
    }

    public Long getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Long documentType) {
        this.documentType = documentType;
    }

    public Long getInternational() {
        return international;
    }

    public void setInternational(Long international) {
        this.international = international;
    }

    public Long getSignPurpose() {
        return signPurpose;
    }

    public void setSignPurpose(Long signPurpose) {
        this.signPurpose = signPurpose;
    }

    public String getProofImage() {
        return proofImage;
    }

    public void setProofImage(String proofImage) {
        this.proofImage = proofImage;
    }
}
