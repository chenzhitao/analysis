package com.elai.analytics.domain.third;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ApiModel(value = "留存分析对象", description = "留存分析对象")
public class CkKeepParamVO implements Serializable {

    //初始行为
    private  String initEvent;
    //后续行为
    private String followUpEvent;
    //开始时间
    private  String startDate;
    //结束时间
    private  String endDate;
    //留存/损失
    private String keepType;
    //留存日
    private Integer keepDay;

    //条件集合
    private List<Map<String,Object>> conditionList;
    //条件sql
    private String conditionSql;

    public String getInitEvent() {
        return initEvent;
    }

    public void setInitEvent(String initEvent) {
        this.initEvent = initEvent;
    }

    public String getFollowUpEvent() {
        return followUpEvent;
    }

    public void setFollowUpEvent(String followUpEvent) {
        this.followUpEvent = followUpEvent;
    }

    public Integer getKeepDay() {
        return keepDay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getKeepType() {
        return keepType;
    }

    public void setKeepType(String keepType) {
        this.keepType = keepType;
    }

    public List<Map<String, Object>> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Map<String, Object>> conditionList) {
        this.conditionList = conditionList;
    }

    public String getConditionSql() {
        return conditionSql;
    }

    public void setConditionSql(String conditionSql) {
        this.conditionSql = conditionSql;
    }

    public void setKeepDay(Integer keepDay) {
        this.keepDay = keepDay;
    }

}

