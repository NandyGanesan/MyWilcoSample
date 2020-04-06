package com.android.wilcoconnect.model.leave.compensatory;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CompOffDetail {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<CompOffDetailData> data;

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

    public ArrayList<CompOffDetailData> getData() {
        return data;
    }

    public void setData(ArrayList<CompOffDetailData> data) {
        this.data = data;
    }
}
