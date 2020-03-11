package com.android.wilcoconnect.model.wilcoconnect;

import com.google.gson.annotations.SerializedName;

public class IssueTrackingData {

    @SerializedName("IssueDetailID")
    public int IssueDetailID;

    @SerializedName("IssueMasterID")
    public int IssueMasterID;

    @SerializedName("StatusID")
    public int StatusID;

    @SerializedName("Status")
    public String Status;

    @SerializedName("Remarks")
    public String Remarks;

    @SerializedName("Created_By")
    public String Created_By;

    @SerializedName("FirstName")
    public String FirstName;

    @SerializedName("Created_Date")
    public String Created_Date;

    public int getIssueDetailID() {
        return IssueDetailID;
    }

    public void setIssueDetailID(int issueDetailID) {
        IssueDetailID = issueDetailID;
    }

    public int getIssueMasterID() {
        return IssueMasterID;
    }

    public void setIssueMasterID(int issueMasterID) {
        IssueMasterID = issueMasterID;
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

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getCreated_Date() {
        return Created_Date;
    }

    public void setCreated_Date(String created_Date) {
        Created_Date = created_Date;
    }
}
