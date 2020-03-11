package com.android.wilcoconnect.model.wilcoconnect;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class IssueTracking {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
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



    public class Data{

        @SerializedName("requestDetails")
        public List<MyRequestData.Data.MyRequests> requestDetails;

        @SerializedName("issueTracking")
        public List<IssueTrackingData> issueTracking;

        public List<MyRequestData.Data.MyRequests> getRequestDetails() {
            return requestDetails;
        }

        public void setRequestDetails(List<MyRequestData.Data.MyRequests> requestDetails) {
            this.requestDetails = requestDetails;
        }

        public List<IssueTrackingData> getIssueTracking() {
            return issueTracking;
        }

        public void setIssueTracking(List<IssueTrackingData> issueTracking) {
            this.issueTracking = issueTracking;
        }
    }
}
