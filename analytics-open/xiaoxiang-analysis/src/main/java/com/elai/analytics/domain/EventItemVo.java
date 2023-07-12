package com.elai.analytics.domain;

public class EventItemVo {
    private static final long serialVersionUID = 1L;
    //事件类型
    private String eventType;
    //事件名称
    private String eventTypeName;
    //事件项
    private String eventItemCode;
    //事件项名称
    private String eventItemName;
    //表达式
    private String eventItemExpression;
    //值类型
    private String valueType;
    //值1
    private String value1Str;

    //值2
    private String value2Str;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public String getEventItemCode() {
        return eventItemCode;
    }

    public void setEventItemCode(String eventItemCode) {
        this.eventItemCode = eventItemCode;
    }

    public String getEventItemName() {
        return eventItemName;
    }

    public void setEventItemName(String eventItemName) {
        this.eventItemName = eventItemName;
    }

    public String getEventItemExpression() {
        return eventItemExpression;
    }

    public void setEventItemExpression(String eventItemExpression) {
        this.eventItemExpression = eventItemExpression;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValue1Str() {
        return value1Str;
    }

    public void setValue1Str(String value1Str) {
        this.value1Str = value1Str;
    }

    public String getValue2Str() {
        return value2Str;
    }

    public void setValue2Str(String value2Str) {
        this.value2Str = value2Str;
    }


}
