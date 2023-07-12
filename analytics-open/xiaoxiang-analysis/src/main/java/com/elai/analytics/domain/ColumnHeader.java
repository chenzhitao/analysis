package com.elai.analytics.domain;

public class ColumnHeader {

    private static final long serialVersionUID = 1L;

    ColumnHeader(){}

    public ColumnHeader(String columnName,String columnLabel,String comparisonLabel,boolean sortable){
        this.columnName = columnName;
        this.columnLabel = columnLabel;
        this.comparisonLabel = comparisonLabel;
        this.sortable = sortable;
    }
    //列名称
    private String columnName;

    //列描述
    private String columnLabel;

    //对比字段名称
    private String comparisonLabel;

    //是否排序
    private boolean sortable;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    public String getComparisonLabel() {
        return comparisonLabel;
    }

    public void setComparisonLabel(String comparisonLabel) {
        this.comparisonLabel = comparisonLabel;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }
}
