package com.android.wilcoconnect.model.leave.leavebalance;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetLeaveBalance {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;

    public class Data{

        @SerializedName("LeaveDetails")
        public ArrayList<LeaveDetails> LeaveDetails;

        @SerializedName("LeaveTypeDetails")
        public ArrayList<LeaveTypeDetails> LeaveTypeDetails;

        public ArrayList<LeaveTypeDetails> getLeaveTypeDetails() {
            return LeaveTypeDetails;
        }

        public void setLeaveTypeDetails(ArrayList<LeaveTypeDetails> leaveTypeDetails) {
            LeaveTypeDetails = leaveTypeDetails;
        }

        public ArrayList<LeaveDetails> getLeaveDetails() {
            return LeaveDetails;
        }

        public void setLeaveDetails(ArrayList<LeaveDetails> leaveDetails) {
            LeaveDetails = leaveDetails;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
