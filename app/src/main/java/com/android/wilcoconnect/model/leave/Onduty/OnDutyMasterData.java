package com.android.wilcoconnect.model.leave.Onduty;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OnDutyMasterData {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<Data> data;

    public class Data{

        @SerializedName("OnDutyID")
        public int OnDutyID;

        @SerializedName("OnDutyName")
        public String OnDutyName;

        @SerializedName("OnDutyCode")
        public String OnDutyCode;

        public int getOnDutyID() {
            return OnDutyID;
        }

        public void setOnDutyID(int onDutyID) {
            OnDutyID = onDutyID;
        }

        public String getOnDutyName() {
            return OnDutyName;
        }

        public void setOnDutyName(String onDutyName) {
            OnDutyName = onDutyName;
        }

        public String getOnDutyCode() {
            return OnDutyCode;
        }

        public void setOnDutyCode(String onDutyCode) {
            OnDutyCode = onDutyCode;
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
