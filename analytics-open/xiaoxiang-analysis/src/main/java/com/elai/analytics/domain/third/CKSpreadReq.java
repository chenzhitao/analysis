package com.elai.analytics.domain.third;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "事件分析对象传入参数", description = "事件分析对象传入参数")
public class CKSpreadReq implements Serializable {


    //事件名称
    private String  eventName;
    //select之后sql
    private String  sSql;
    //where之后sql
    private String  wSql;
    //开始时间
    private String  startDate;
    //结束时间
    private String  endDate;

    private String methodName;
    private String fieldName;

    private String isInt; //字段是否数字
    //排序
    private  String groupBy;
    //过去N天
    private Integer beforeDay;

    private String taskType;

    //应用集合
    private List<String> applicationCodeList;

    //原始参数
    private JSONObject jsonParam;

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getsSql() {
        return sSql;
    }

    public void setsSql(String sSql) {
        this.sSql = sSql;
    }

    public String getwSql() {
        return wSql;
    }

    public void setwSql(String wSql) {
        this.wSql = wSql;
    }

    public List<String> getApplicationCodeList() {
        return applicationCodeList;
    }

    public void setApplicationCodeList(List<String> applicationCodeList) {
        this.applicationCodeList = applicationCodeList;
    }

    public Integer getBeforeDay() {
        return beforeDay;
    }

    public void setBeforeDay(Integer beforeDay) {
        this.beforeDay = beforeDay;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public JSONObject getJsonParam() {
        return jsonParam;
    }

    public void setJsonParam(JSONObject jsonParam) {
        this.jsonParam = jsonParam;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getIsInt() {
        return isInt;
    }

    public void setIsInt(String isInt) {
        this.isInt = isInt;
    }
}
