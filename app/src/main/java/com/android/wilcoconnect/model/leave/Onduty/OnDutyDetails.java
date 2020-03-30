package com.android.wilcoconnect.model.leave.Onduty;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OnDutyDetails {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<OnDutyData> data;

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

    public ArrayList<OnDutyData> getData() {
        return data;
    }

    public void setData(ArrayList<OnDutyData> data) {
        this.data = data;
    }
}
