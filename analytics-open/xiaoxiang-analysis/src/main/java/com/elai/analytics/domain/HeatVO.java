package com.elai.analytics.domain;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel(value = "网页热力分析", description = "网页热力分析")
public class HeatVO implements Serializable {

    //地址
    private  String  urlPath;
    //浏览总次数
    private  String  viewCount;
    //元素点击用户数
    private  String  loginCount;
    //元素点击共次数
    private  String  clickCount;

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }
}

