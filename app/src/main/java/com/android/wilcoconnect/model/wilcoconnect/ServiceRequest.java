package com.android.wilcoconnect.model.wilcoconnect;

import com.google.gson.annotations.SerializedName;

public class ServiceRequest {

   public String Support;
   public String Request;
   public String Description;
   public String Summary;
   public String Priority;
   public String Status;
   public String lastComment;
   public String statusCode;
   public int masterID;
   public String Filepath;
    @SerializedName("IssueNumber")
    public String IssueNumber;

    public String getFilepath() {
        return Filepath;
    }

    public void setFilepath(String filepath) {
        Filepath = filepath;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public String getLastComment() {
        return lastComment;
    }

    public void setLastComment(String lastComment) {
        this.lastComment = lastComment;
    }

    public String getSupport() {
        return Support;
    }

    public void setSupport(String support) {
        Support = support;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public String getIssueNumber() {
        return IssueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        IssueNumber = issueNumber;
    }
}
