package com.elai.analytics.domain;

import java.util.List;
import java.util.Map;

public class AnalyzeFunnelQueryDo {

    private static final long serialVersionUID = 1L;



    //漏斗ID
    private   Long funnelId;



    //时间区间
    private String []dateRange;

    //按时间类型分组
    private String eventTypeArray;

    //事件数组集合
    private List<Map> eventItemList;



    public Long getFunnelId() {
        return funnelId;
    }

    public void setFunnelId(Long funnelId) {
        this.funnelId = funnelId;
    }

    public String getEventTypeArray() {
        return eventTypeArray;
    }

    public void setEventTypeArray(String eventTypeArray) {
        this.eventTypeArray = eventTypeArray;
    }

    public List<Map> getEventItemList() {
        return eventItemList;
    }

    public void setEventItemList(List<Map> eventItemList) {
        this.eventItemList = eventItemList;
    }

    public String[] getDateRange() {
        return dateRange;
    }

    public void setDateRange(String[] dateRange) {
        this.dateRange = dateRange;
    }


}
