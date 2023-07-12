package com.elai.analytics.domain;

import java.util.Map;

public class eventType{
    private static final long serialVersionUID = 1L;
    private String typeName;
    private Long typeItem;
    private String typeItemLabel;
    private String itemName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(Long typeItem) {
        this.typeItem = typeItem;
    }

    public String getTypeItemLabel() {
        return typeItemLabel;
    }

    public void setTypeItemLabel(String typeItemLabel) {
        this.typeItemLabel = typeItemLabel;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }




}
