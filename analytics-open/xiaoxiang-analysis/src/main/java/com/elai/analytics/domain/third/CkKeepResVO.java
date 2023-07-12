package com.elai.analytics.domain.third;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "", description = "")
public class CkKeepResVO implements Serializable {

    //
    private  String resDate;
    //
    private List<Integer> daysCount;
    //
    private List<BigDecimal> daysRate;

    public String getResDate() {
        return resDate;
    }

    public void setResDate(String resDate) {
        this.resDate = resDate;
    }

    public List<Integer> getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(List<Integer> daysCount) {
        this.daysCount = daysCount;
    }

    public List<BigDecimal> getDaysRate() {
        return daysRate;
    }

    public void setDaysRate(List<BigDecimal> daysRate) {
        this.daysRate = daysRate;
    }
}

