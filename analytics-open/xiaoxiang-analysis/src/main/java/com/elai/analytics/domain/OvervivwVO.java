package com.elai.analytics.domain;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "事件分析对象", description = "事件分析对象")
public class OvervivwVO implements Serializable {

    private  String eventCount;
    private String eventDate;
    //环比、01
    private  String onyearFlow;
    //同比、02
    private  String comparison;
    //平台类型
    private  String platformType;

    private BigDecimal eventAmount;

    //对比数量
    private Integer combineCount;

    private String eventName;

    public String getEventCount() {
        return eventCount;
    }

    public void setEventCount(String eventCount) {
        this.eventCount = eventCount;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getOnyearFlow() {
        return onyearFlow;
    }

    public void setOnyearFlow(String onyearFlow) {
        this.onyearFlow = onyearFlow;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public BigDecimal getEventAmount() {
        return eventAmount;
    }

    public void setEventAmount(BigDecimal eventAmount) {
        this.eventAmount = eventAmount;
    }

    public Integer getCombineCount() {
        return combineCount;
    }

    public void setCombineCount(Integer combineCount) {
        this.combineCount = combineCount;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

