package com.android.wilcoconnect.model.leave;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Holiday {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<HolidayData> data;

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

    public ArrayList<HolidayData> getData() {
        return data;
    }

    public void setData(ArrayList<HolidayData> data) {
        this.data = data;
    }
}
