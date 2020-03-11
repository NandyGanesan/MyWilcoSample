package com.android.wilcoconnect.model.wilcoconnect;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestType {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<Data> data;

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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{

        @SerializedName("supportFunction")
        public List<SupportFunction> supportFunction;

        public List<SupportFunction> getSupportFunction() {
            return supportFunction;
        }

        public void setSupportFunction(List<SupportFunction> supportFunction) {
            this.supportFunction = supportFunction;
        }

        @SerializedName("priorityList")
        public List<Priority> priorityList;

        public List<Priority> getPriorityList() {
            return priorityList;
        }

        public void setPriorityList(List<Priority> priorityList) {
            this.priorityList = priorityList;
        }
    }

}
