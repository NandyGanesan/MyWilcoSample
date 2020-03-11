package com.android.wilcoconnect.model.wilcoconnect;


import com.google.gson.annotations.SerializedName;

public class AddRequest {

    @SerializedName("supportFunctionID")
    public int supportFunctionID;

    @SerializedName("requestTypeId")
    public int requestTypeId;

    @SerializedName("issueSummary")
    public String issueSummary;

    @SerializedName("issueDescription")
    public String issueDescription;

    @SerializedName("priorityLevelID")
    public int priorityLevelID;

    @SerializedName("Email")
    public String Email;

    @SerializedName("EmployeeID")
    public String EmployeeID;

    @SerializedName("companyCode")
    public String companyCode;

    @SerializedName("IssueMasterID")
    public int IssueMasterID;

    @SerializedName("statuscode")
    public String statuscode;

    @SerializedName("comments")
    public String comments;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatusCode(String statuscode) {
        this.statuscode = statuscode;
    }

    public AddRequest() {
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getSupportFunctionID() {
        return supportFunctionID;
    }

    public void setSupportFunctionID(int supportFunctionID) {
        this.supportFunctionID = supportFunctionID;
    }

    public int getRequestTypeId() {
        return requestTypeId;
    }

    public void setRequestTypeId(int requestTypeId) {
        this.requestTypeId = requestTypeId;
    }

    public String getIssueSummary() {
        return issueSummary;
    }

    public void setIssueSummary(String issueSummary) {
        this.issueSummary = issueSummary;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public int getPriorityLevelID() {
        return priorityLevelID;
    }

    public void setPriorityLevelID(int priorityLevelID) {
        this.priorityLevelID = priorityLevelID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public int getIssueMasterID() {
        return IssueMasterID;
    }

    public void setIssueMasterID(int issueMasterID) {
        IssueMasterID = issueMasterID;
    }
}
