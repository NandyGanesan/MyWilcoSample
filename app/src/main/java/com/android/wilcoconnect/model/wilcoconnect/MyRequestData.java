package com.android.wilcoconnect.model.wilcoconnect;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyRequestData {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public List<Data> data;

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

    public class Data {

        @SerializedName("myrequests")
        public List<MyRequests> myrequests;

        public List<MyRequests> getMyrequests() {
            return myrequests;
        }

        public void setMyrequests(List<MyRequests> myrequests) {
            this.myrequests = myrequests;
        }

        public class MyRequests {

            @SerializedName("IssueMasterID")
            public int IssueMasterID;

            @SerializedName("IssueDetailID")
            public int IssueDetailID;

            @SerializedName("StatusID")
            public int StatusID;

            @SerializedName("Status")
            public String Status;

            @SerializedName("ShortDescription")
            public String ShortDescription;

            @SerializedName("statusCode")
            public String statusCode;

            @SerializedName("AssignedUserID")
            public String AssignedUserID;

            @SerializedName("RequestUserID")
            public String RequestUserID;

            @SerializedName("LastUpdatedDate")
            public String LastUpdatedDate;

            @SerializedName("SupportIndexFunctionID")
            public int SupportIndexFunctionID;

            @SerializedName("SupportFunctionName")
            public String SupportFunctionName;

            @SerializedName("RequestTypeID")
            public int RequestTypeID;

            @SerializedName("RequestDescription")
            public String RequestDescription;

            @SerializedName("IssueNumber")
            public String IssueNumber;

            @SerializedName("HistoryMaintain")
            public boolean HistoryMaintain;

            @SerializedName("PriorityLevelID")
            public int PriorityLevelID;

            @SerializedName("PriorityLevel")
            public String PriorityLevel;

            @SerializedName("userstatus")
            public String userstatus;

            @SerializedName("CurrentStatus")
            public String CurrentStatus;

            @SerializedName("statustoView")
            public String statustoView;

            @SerializedName("Remarks")
            public String Remarks;

            @SerializedName("FilePath")
            public String FilePath;

            @SerializedName("ClosedDescription")
            public String ClosedDescription;

            @SerializedName("lastupdatedComments")
            public String lastupdatedComments;

            @SerializedName("lastupdatedAttachment")
            public String lastupdatedAttachment;

            @SerializedName("Created_Date")
            public String Created_Date;

            @SerializedName("Created_By")
            public String Created_By;

            @SerializedName("DetailDescription")
            public String DetailDescription;

            public String getCreated_Date() {
                return Created_Date;
            }

            public void setCreated_Date(String created_Date) {
                Created_Date = created_Date;
            }

            public String getCreated_By() {
                return Created_By;
            }

            public void setCreated_By(String created_By) {
                Created_By = created_By;
            }

            public String getDetailDescription() {
                return DetailDescription;
            }

            public void setDetailDescription(String detailDescription) {
                DetailDescription = detailDescription;
            }

            public String getLastupdatedComments() {
                return lastupdatedComments;
            }

            public void setLastupdatedComments(String lastupdatedComments) {
                this.lastupdatedComments = lastupdatedComments;
            }

            public String getLastupdatedAttachment() {
                return lastupdatedAttachment;
            }

            public void setLastupdatedAttachment(String lastupdatedAttachment) {
                this.lastupdatedAttachment = lastupdatedAttachment;
            }

            public String getClosedDescription() {
                return ClosedDescription;
            }

            public void setClosedDescription(String closedDescription) {
                ClosedDescription = closedDescription;
            }

            public String getUserstatus() {
                return userstatus;
            }

            public void setUserstatus(String userstatus) {
                this.userstatus = userstatus;
            }

            public int getIssueMasterID() {
                return IssueMasterID;
            }

            public void setIssueMasterID(int issueMasterID) {
                IssueMasterID = issueMasterID;
            }

            public int getIssueDetailID() {
                return IssueDetailID;
            }

            public void setIssueDetailID(int issueDetailID) {
                IssueDetailID = issueDetailID;
            }

            public int getStatusID() {
                return StatusID;
            }

            public void setStatusID(int statusID) {
                StatusID = statusID;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String status) {
                Status = status;
            }

            public String getShortDescription() {
                return ShortDescription;
            }

            public void setShortDescription(String shortDescription) {
                ShortDescription = shortDescription;
            }

            public String getStatusCode() {
                return statusCode;
            }

            public void setStatusCode(String statusCode) {
                this.statusCode = statusCode;
            }

            public String getAssignedUserID() {
                return AssignedUserID;
            }

            public void setAssignedUserID(String assignedUserID) {
                AssignedUserID = assignedUserID;
            }

            public String getRequestUserID() {
                return RequestUserID;
            }

            public void setRequestUserID(String requestUserID) {
                RequestUserID = requestUserID;
            }

            public String getLastUpdatedDate() {
                return LastUpdatedDate;
            }

            public void setLastUpdatedDate(String lastUpdatedDate) {
                LastUpdatedDate = lastUpdatedDate;
            }

            public int getSupportIndexFunctionID() {
                return SupportIndexFunctionID;
            }

            public void setSupportIndexFunctionID(int supportIndexFunctionID) {
                SupportIndexFunctionID = supportIndexFunctionID;
            }

            public String getSupportFunctionName() {
                return SupportFunctionName;
            }

            public void setSupportFunctionName(String supportFunctionName) {
                SupportFunctionName = supportFunctionName;
            }

            public int getRequestTypeID() {
                return RequestTypeID;
            }

            public void setRequestTypeID(int requestTypeID) {
                RequestTypeID = requestTypeID;
            }

            public String getRequestDescription() {
                return RequestDescription;
            }

            public void setRequestDescription(String requestDescription) {
                RequestDescription = requestDescription;
            }

            public String getIssueNumber() {
                return IssueNumber;
            }

            public void setIssueNumber(String issueNumber) {
                IssueNumber = issueNumber;
            }

            public boolean isHistoryMaintain() {
                return HistoryMaintain;
            }

            public void setHistoryMaintain(boolean historyMaintain) {
                HistoryMaintain = historyMaintain;
            }

            public int getPriorityLevelID() {
                return PriorityLevelID;
            }

            public void setPriorityLevelID(int priorityLevelID) {
                PriorityLevelID = priorityLevelID;
            }

            public String getPriorityLevel() {
                return PriorityLevel;
            }

            public void setPriorityLevel(String priorityLevel) {
                PriorityLevel = priorityLevel;
            }

            public String getCurrentStatus() {
                return CurrentStatus;
            }

            public void setCurrentStatus(String currentStatus) {
                CurrentStatus = currentStatus;
            }

            public String getStatustoView() {
                return statustoView;
            }

            public void setStatustoView(String statustoView) {
                this.statustoView = statustoView;
            }

            public String getRemarks() {
                return Remarks;
            }

            public void setRemarks(String remarks) {
                Remarks = remarks;
            }

            public String getFilePath() {
                return FilePath;
            }

            public void setFilePath(String filePath) {
                FilePath = filePath;
            }
        }
    }
}
