package com.android.wilcoconnect.model.profile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EducationDetails {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<EducationDetailData> data;

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

    public List<EducationDetailData> getData() {
        return data;
    }

    public void setData(List<EducationDetailData> data) {
        this.data = data;
    }
}
