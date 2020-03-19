package com.android.wilcoconnect.model.leave;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data{

    @SerializedName("leaveList")
    public ArrayList<MyLeaveData> leaveList;

    public ArrayList<MyLeaveData> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(ArrayList<MyLeaveData> leaveList) {
        this.leaveList = leaveList;
    }
}
