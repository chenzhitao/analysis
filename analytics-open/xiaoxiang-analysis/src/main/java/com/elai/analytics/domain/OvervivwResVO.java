package com.elai.analytics.domain;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "事件分析对象", description = "事件分析对象")
public class OvervivwResVO implements Serializable {
    //当前显示值  数量
    private  String eventCount; //
    private String eventDate;
    //环比值
    private  String onyearFlow;
    //同比值
    private  String comparison;
    //当前显示值  金额
    private BigDecimal eventAmount;
    //合计
    private BigDecimal totalNum;
    //均值
    private BigDecimal avgNum;
    //列表
    private List<OvervivwVO> overvivwList;

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

    public BigDecimal getEventAmount() {
        return eventAmount;
    }

    public void setEventAmount(BigDecimal eventAmount) {
        this.eventAmount = eventAmount;
    }

    public BigDecimal getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(BigDecimal totalNum) {
        this.totalNum = totalNum;
    }

    public BigDecimal getAvgNum() {
        return avgNum;
    }

    public void setAvgNum(BigDecimal avgNum) {
        this.avgNum = avgNum;
    }

    public List<OvervivwVO> getOvervivwList() {
        return overvivwList;
    }

    public void setOvervivwList(List<OvervivwVO> overvivwList) {
        this.overvivwList = overvivwList;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

