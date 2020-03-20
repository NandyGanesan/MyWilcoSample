package com.android.wilcoconnect.model.MenuList;

import com.google.gson.annotations.SerializedName;

public class SubMenu {

    @SerializedName("MenuID")
    public int MenuID;

    @SerializedName("MenuName")
    public String MenuName;

    @SerializedName("ParentID")
    public int ParentID;

    @SerializedName("MenuType")
    public String MenuType;

    @SerializedName("ActiveStatus")
    public boolean ActiveStatus;

    @SerializedName("SortOrder")
    public String SortOrder;

    public int getMenuID() {
        return MenuID;
    }

    public void setMenuID(int menuID) {
        MenuID = menuID;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public int getParentID() {
        return ParentID;
    }

    public void setParentID(int parentID) {
        ParentID = parentID;
    }

    public String getMenuType() {
        return MenuType;
    }

    public void setMenuType(String menuType) {
        MenuType = menuType;
    }

    public boolean isActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(String sortOrder) {
        SortOrder = sortOrder;
    }
}