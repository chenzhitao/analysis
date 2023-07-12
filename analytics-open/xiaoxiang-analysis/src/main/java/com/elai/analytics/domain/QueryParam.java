package com.elai.analytics.domain;

import java.util.List;
import java.util.Map;

public class QueryParam {
    private static final long serialVersionUID = 1L;

    /**
     * 报表类型  1折现 2条形 3饼状 4面机
     */
    private int chartType;


    private String eventTypeLabel;

    //日期函数
    private String []dateRange;
    private String [] comparisonDateRange;

    public String[] getComparisonDateRange() {
        return comparisonDateRange;
    }

    public void setComparisonDateRange(String[] comparisonDateRange) {
        this.comparisonDateRange = comparisonDateRange;
    }

    //时间类型
    private String timeType;

    //对比
    private String combine;


    //按 * 维度
    private List<Map<String,Object>> filterTags;

    public List<Map<String, Object>> getQueryItems() {
        return queryItems;
    }

    public void setQueryItems(List<Map<String, Object>> queryItems) {
        this.queryItems = queryItems;
    }

    //事件类型
    private List<Map<String,Object>> queryItems;

    //事件类型
    private List<Map<String,Object>> userAttributes;


    public List<Map<String, Object>> getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(List<Map<String, Object>> userAttributes) {
        this.userAttributes = userAttributes;
    }


    public int getChartType() {
        return chartType;
    }

    public void setChartType(int chartType) {
        this.chartType = chartType;
    }

    public String[] getDateRange() {
        return dateRange;
    }

    public void setDateRange(String[] dateRange) {
        this.dateRange = dateRange;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getCombine() {
        return combine;
    }

    public void setCombine(String combine) {
        this.combine = combine;
    }

    public List<Map<String, Object>> getFilterTags() {
        return filterTags;
    }

    public void setFilterTags(List<Map<String, Object>> filterTags) {
        this.filterTags = filterTags;
    }

    public String getEventTypeLabel() {
        return eventTypeLabel;
    }

    public void setEventTypeLabel(String eventTypeLabel) {
        this.eventTypeLabel = eventTypeLabel;
    }
}
