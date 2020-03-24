package com.android.wilcoconnect.model.leave;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LeaveType {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<Data> data;

    public class Data{

        @SerializedName("LeaveTypeID")
        public int LeaveTypeID;

        @SerializedName("LeaveType")
        public String LeaveType;

        @SerializedName("ActiveStatus")
        public String ActiveStatus;

        public int getLeaveTypeID() {
            return LeaveTypeID;
        }

        public void setLeaveTypeID(int leaveTypeID) {
            LeaveTypeID = leaveTypeID;
        }

        public String getLeaveType() {
            return LeaveType;
        }

        public void setLeaveType(String leaveType) {
            LeaveType = leaveType;
        }

        public String getActiveStatus() {
            return ActiveStatus;
        }

        public void setActiveStatus(String activeStatus) {
            ActiveStatus = activeStatus;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
