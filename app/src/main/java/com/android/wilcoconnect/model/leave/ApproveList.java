package com.android.wilcoconnect.model.leave;

import com.google.gson.annotations.SerializedName;

public class ApproveList {

    @SerializedName("EmployeeID")
    public String EmployeeID;

    @SerializedName("FirstName")
    public String FirstName;

    @SerializedName("email")
    public String email;

    @SerializedName("FromDate")
    public String FromDate;

    @SerializedName("strFromDate")
    public String strFromDate;

    @SerializedName("ToDate")
    public String ToDate;

    @SerializedName("strFromDate")
    public String strToDate;

    @SerializedName("LeaveTypeText")
    public String LeaveTypeText;

    @SerializedName("RequestStatus")
    public String RequestStatus;

    @SerializedName("No_of_days")
    public String No_of_days;

    @SerializedName("RequestRemarks")
    public String RequestRemarks;

    @SerializedName("LeaveRequestStatusID")
    public int LeaveRequestStatusID;

    public String getStrFromDate() {
        return strFromDate;
    }

    public void setStrFromDate(String strFromDate) {
        this.strFromDate = strFromDate;
    }

    public String getStrToDate() {
        return strToDate;
    }

    public void setStrToDate(String strToDate) {
        this.strToDate = strToDate;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getLeaveTypeText() {
        return LeaveTypeText;
    }

    public void setLeaveTypeText(String leaveTypeText) {
        LeaveTypeText = leaveTypeText;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getNo_of_days() {
        return No_of_days;
    }

    public void setNo_of_days(String no_of_days) {
        No_of_days = no_of_days;
    }

    public String getRequestRemarks() {
        return RequestRemarks;
    }

    public void setRequestRemarks(String requestRemarks) {
        RequestRemarks = requestRemarks;
    }

    public int getLeaveRequestStatusID() {
        return LeaveRequestStatusID;
    }

    public void setLeaveRequestStatusID(int leaveRequestStatusID) {
        LeaveRequestStatusID = leaveRequestStatusID;
    }
}
