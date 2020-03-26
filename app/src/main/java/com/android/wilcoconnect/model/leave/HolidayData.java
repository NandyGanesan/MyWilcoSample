package com.android.wilcoconnect.model.leave;

import com.google.gson.annotations.SerializedName;

public class HolidayData {

    @SerializedName("HolidayID")
    public int HolidayID;

    @SerializedName("LeaveDate")
    public String LeaveDate;

    @SerializedName("LeaveDay")
    public String LeaveDay;

    @SerializedName("Description")
    public String Description;

    @SerializedName("StateName")
    public String StateName;

    @SerializedName("StateID")
    public int StateID;

    public int getHolidayID() {
        return HolidayID;
    }

    public void setHolidayID(int holidayID) {
        HolidayID = holidayID;
    }

    public String getLeaveDate() {
        return LeaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        LeaveDate = leaveDate;
    }

    public String getLeaveDay() {
        return LeaveDay;
    }

    public void setLeaveDay(String leaveDay) {
        LeaveDay = leaveDay;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public int getStateID() {
        return StateID;
    }

    public void setStateID(int stateID) {
        StateID = stateID;
    }
}
