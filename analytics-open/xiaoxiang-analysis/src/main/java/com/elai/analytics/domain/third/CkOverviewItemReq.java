package com.elai.analytics.domain.third;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(value = "事件分析对象传入参数", description = "事件分析对象传入参数")
public class CkOverviewItemReq implements Serializable {

    //select之后sql
    private String  sSql;
    //where之后sql
    private String  wSql;
    //排序
    private  String groupBy;
    //事件名称
    private String  eventName;

    private String methodName;
    private String fieldName;

    private String isInt; //字段是否数字

    private String categoryType; //事件类别 virtual虚拟/normal普通
    //原始参数
    private JSONObject jsonParam;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public String getIsInt() {
        return isInt;
    }

    public void setIsInt(String isInt) {
        this.isInt = isInt;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
