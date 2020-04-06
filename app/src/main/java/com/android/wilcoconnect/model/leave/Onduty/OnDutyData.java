package com.android.wilcoconnect.model.leave.Onduty;

import com.google.gson.annotations.SerializedName;

public class OnDutyData {

    @SerializedName("WorkFromHomeEmployeeRequestID")
    public int WorkFromHomeEmployeeRequestID;

    @SerializedName("WorkFromHomeID")
    public int WorkFromHomeID;

    @SerializedName("EmployeeID")
    public String EmployeeID;

    @SerializedName("FromDate")
    public String FromDate;

    @SerializedName("ToDate")
    public String ToDate;

    @SerializedName("NumberOfDays")
    public int NumberOfDays;

    @SerializedName("LeaveRequestStatusID")
    public int LeaveRequestStatusID;

    @SerializedName("Reason")
    public String Reason;

    @SerializedName("ApprovedDate")
    public String ApprovedDate;

    @SerializedName("ApprovedRemarks")
    public String ApprovedRemarks;

    @SerializedName("ApprovedBy")
    public String ApprovedBy;

    @SerializedName("ActiveStatus")
    public boolean ActiveStatus;

    @SerializedName("Created_Date")
    public String Created_Date;

    @SerializedName("Created_By")
    public String Created_By;

    @SerializedName("Modified_Date")
    public String Modified_Date;

    @SerializedName("Modified_By")
    public String Modified_By;

    @SerializedName("OnDutyID")
    public String OnDutyID;

    @SerializedName("EmployeeName")
    public String EmployeeName;

    @SerializedName("RequestStatus")
    public String RequestStatus;

    @SerializedName("OnDutyName")
    public String OnDutyName;

    @SerializedName("OnDutyList")
    public String OnDutyList;

    @SerializedName("EditStatus")
    public boolean EditStatus;

    @SerializedName("PrimaryApprover")
    public String PrimaryApprover;

    @SerializedName("SecondaryApprover")
    public String SecondaryApprover;

    @SerializedName("PreviousDays")
    public int PreviousDays;

    @SerializedName("NextDays")
    public int NextDays;

    @SerializedName("strFromDate")
    public String strFromDate;

    @SerializedName("strToDate")
    public String strToDate;

    @SerializedName("strApprovedDate")
    public String strApprovedDate;

    public String getStrApprovedDate() {
        return strApprovedDate;
    }

    public void setStrApprovedDate(String strApprovedDate) {
        this.strApprovedDate = strApprovedDate;
    }

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

    public int getWorkFromHomeEmployeeRequestID() {
        return WorkFromHomeEmployeeRequestID;
    }

    public void setWorkFromHomeEmployeeRequestID(int workFromHomeEmployeeRequestID) {
        WorkFromHomeEmployeeRequestID = workFromHomeEmployeeRequestID;
    }

    public int getWorkFromHomeID() {
        return WorkFromHomeID;
    }

    public void setWorkFromHomeID(int workFromHomeID) {
        WorkFromHomeID = workFromHomeID;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
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

    public int getNumberOfDays() {
        return NumberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        NumberOfDays = numberOfDays;
    }

    public int getLeaveRequestStatusID() {
        return LeaveRequestStatusID;
    }

    public void setLeaveRequestStatusID(int leaveRequestStatusID) {
        LeaveRequestStatusID = leaveRequestStatusID;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public void setApprovedDate(String approvedDate) {
        ApprovedDate = approvedDate;
    }

    public String getApprovedRemarks() {
        return ApprovedRemarks;
    }

    public void setApprovedRemarks(String approvedRemarks) {
        ApprovedRemarks = approvedRemarks;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public boolean isActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        ActiveStatus = activeStatus;
    }

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

    public String getModified_Date() {
        return Modified_Date;
    }

    public void setModified_Date(String modified_Date) {
        Modified_Date = modified_Date;
    }

    public String getModified_By() {
        return Modified_By;
    }

    public void setModified_By(String modified_By) {
        Modified_By = modified_By;
    }

    public String getOnDutyID() {
        return OnDutyID;
    }

    public void setOnDutyID(String onDutyID) {
        OnDutyID = onDutyID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getOnDutyName() {
        return OnDutyName;
    }

    public void setOnDutyName(String onDutyName) {
        OnDutyName = onDutyName;
    }

    public String getOnDutyList() {
        return OnDutyList;
    }

    public void setOnDutyList(String onDutyList) {
        OnDutyList = onDutyList;
    }

    public boolean isEditStatus() {
        return EditStatus;
    }

    public void setEditStatus(boolean editStatus) {
        EditStatus = editStatus;
    }

    public String getPrimaryApprover() {
        return PrimaryApprover;
    }

    public void setPrimaryApprover(String primaryApprover) {
        PrimaryApprover = primaryApprover;
    }

    public String getSecondaryApprover() {
        return SecondaryApprover;
    }

    public void setSecondaryApprover(String secondaryApprover) {
        SecondaryApprover = secondaryApprover;
    }

    public int getPreviousDays() {
        return PreviousDays;
    }

    public void setPreviousDays(int previousDays) {
        PreviousDays = previousDays;
    }

    public int getNextDays() {
        return NextDays;
    }

    public void setNextDays(int nextDays) {
        NextDays = nextDays;
    }
}
