package com.elai.analytics.domain;

import com.elai.common.annotation.Excel;
import com.elai.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分析漏斗对象 analyze_funnel
 *
 * @author elai
 * @date 2021-05-01
 */
public class AnalyzeFunnelDO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    private String lesseeId;

    /** 漏斗标题 */
    @Excel(name = "漏斗标题")
    private String title;

    /** 窗口期 */
    @Excel(name = "窗口期")
    private Long windowDate;

    /** 单位 */
    @Excel(name = "单位")
    private String windowUnit;

    /** 是否关联属性Y是N否 */
    @Excel(name = "是否关联属性Y是N否")
    private String relationProperty;

    /** 关联步骤 */
    @Excel(name = "关联步骤")
    private String funnelSteps;

    public String getLesseeId() {
        return lesseeId;
    }

    public void setLesseeId(String lesseeId) {
        this.lesseeId = lesseeId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
    public Long getId()
    {
        return id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getTitle()
    {
        return title;
    }

    public void setWindowDate(Long windowDate)
    {
        this.windowDate = windowDate;
    }
    public Long getWindowDate()
    {
        return windowDate;
    }

    public void setWindowUnit(String windowUnit)
    {
        this.windowUnit = windowUnit;
    }
    public String getWindowUnit()
    {
        return windowUnit;
    }

    public void setRelationProperty(String relationProperty)
    {
        this.relationProperty = relationProperty;
    }
    public String getRelationProperty()
    {
        return relationProperty;
    }

    public void setFunnelSteps(String funnelSteps)
    {
        this.funnelSteps = funnelSteps;
    }
    public String getFunnelSteps()
    {
        return funnelSteps;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("windowDate", getWindowDate())
            .append("windowUnit", getWindowUnit())
            .append("relationProperty", getRelationProperty())
            .append("funnelSteps", getFunnelSteps())
            .toString();
    }
}
