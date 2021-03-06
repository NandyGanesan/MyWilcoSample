package com.android.wilcoconnect.model.profile;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AdditionalDetails {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<Data> data;

    public class Data{

        @SerializedName("objAdditional")
        public AdditionalDetailData objAdditional;

        public AdditionalDetailData getAdditionalInfo() {
            return objAdditional;
        }

        public void setAdditionalInfo(AdditionalDetailData additionalInfo) {
            this.objAdditional = additionalInfo;
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
