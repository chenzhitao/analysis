package com.elai.analytics.domain.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "漏斗sql查询返回对象", description = "漏斗sql查询返回对象")
public class CkFunneResVO implements Serializable {

    @ApiModelProperty(value = "日期")
    private  String resDate;
    @ApiModelProperty(value = "序号")
    private Integer levelIndex;
    @ApiModelProperty(value = "次数")
    private Integer levelCount;

    private  String eventCode;
    private  String eventName;

    public CkFunneResVO(){

    }

    public CkFunneResVO(String resDate, Integer levelIndex, Integer levelCount,
                        String eventCode, String eventName){
        this.resDate = resDate;
        this.levelIndex = levelIndex;
        this.levelCount = levelCount;
        this.eventCode = eventCode;
        this.eventName = eventName;
    }

    public String getResDate() {
        return resDate;
    }

    public void setResDate(String resDate) {
        this.resDate = resDate;
    }

    public Integer getLevelIndex() {
        return levelIndex;
    }

    public void setLevelIndex(Integer levelIndex) {
        this.levelIndex = levelIndex;
    }

    public Integer getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(Integer levelCount) {
        this.levelCount = levelCount;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}

