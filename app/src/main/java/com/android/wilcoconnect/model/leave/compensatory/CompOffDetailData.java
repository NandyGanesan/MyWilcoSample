package com.android.wilcoconnect.model.leave.compensatory;

import com.google.gson.annotations.SerializedName;

public class CompOffDetailData {

    @SerializedName("EmpCompensatoryID")
    public int EmpCompensatoryID;

    @SerializedName("EmployeeID")
    public String EmployeeID;

    @SerializedName("FromDate")
    public String FromDate;

    @SerializedName("strFromDate")
    public String strFromDate;

    @SerializedName("ToDate")
    public String ToDate;

    @SerializedName("strToDate")
    public String strToDate;

    @SerializedName("NoOfDays")
    public double NoOfDays;

    @SerializedName("RequestRemarks")
    public String RequestRemarks;

    @SerializedName("RequestStatusID")
    public int RequestStatusID;

    @SerializedName("RequestStatus")
    public String RequestStatus;

    @SerializedName("ApprovedBy")
    public String ApprovedBy;

    @SerializedName("ApproverName")
    public String ApproverName;

    @SerializedName("ApprovedRemarks")
    public String ApprovedRemarks;

    @SerializedName("ApprovedDate")
    public String ApprovedDate;

    @SerializedName("strApprovedDate")
    public String strApprovedDate;

    @SerializedName("ActiveStatus")
    public boolean ActiveStatus;

    @SerializedName("CreatedDate")
    public String CreatedDate;

    @SerializedName("CreatedBy")
    public String CreatedBy;

    @SerializedName("ModifiedDate")
    public String ModifiedDate;

    @SerializedName("AppliedDate")
    public String AppliedDate;

    @SerializedName("strAppliedDate")
    public String strAppliedDate;

    @SerializedName("ModifiedBy")
    public String ModifiedBy;

    @SerializedName("ID")
    public String ID;

    @SerializedName("FirstName")
    public String FirstName;

    @SerializedName("PrimaryApprover")
    public String PrimaryApprover;

    @SerializedName("config")
    public String config;

    public int getEmpCompensatoryID() {
        return EmpCompensatoryID;
    }

    public void setEmpCompensatoryID(int empCompensatoryID) {
        EmpCompensatoryID = empCompensatoryID;
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

    public String getStrFromDate() {
        return strFromDate;
    }

    public void setStrFromDate(String strFromDate) {
        this.strFromDate = strFromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getStrToDate() {
        return strToDate;
    }

    public void setStrToDate(String strToDate) {
        this.strToDate = strToDate;
    }

    public double getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(double noOfDays) {
        NoOfDays = noOfDays;
    }

    public String getRequestRemarks() {
        return RequestRemarks;
    }

    public void setRequestRemarks(String requestRemarks) {
        RequestRemarks = requestRemarks;
    }

    public int getRequestStatusID() {
        return RequestStatusID;
    }

    public void setRequestStatusID(int requestStatusID) {
        RequestStatusID = requestStatusID;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }

    public String getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(String approvedBy) {
        ApprovedBy = approvedBy;
    }

    public String getApproverName() {
        return ApproverName;
    }

    public void setApproverName(String approverName) {
        ApproverName = approverName;
    }

    public String getApprovedRemarks() {
        return ApprovedRemarks;
    }

    public void setApprovedRemarks(String approvedRemarks) {
        ApprovedRemarks = approvedRemarks;
    }

    public String getApprovedDate() {
        return ApprovedDate;
    }

    public void setApprovedDate(String approvedDate) {
        ApprovedDate = approvedDate;
    }

    public String getStrApprovedDate() {
        return strApprovedDate;
    }

    public void setStrApprovedDate(String strApprovedDate) {
        this.strApprovedDate = strApprovedDate;
    }

    public boolean isActiveStatus() {
        return ActiveStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        ActiveStatus = activeStatus;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getAppliedDate() {
        return AppliedDate;
    }

    public void setAppliedDate(String appliedDate) {
        AppliedDate = appliedDate;
    }

    public String getStrAppliedDate() {
        return strAppliedDate;
    }

    public void setStrAppliedDate(String strAppliedDate) {
        this.strAppliedDate = strAppliedDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getPrimaryApprover() {
        return PrimaryApprover;
    }

    public void setPrimaryApprover(String primaryApprover) {
        PrimaryApprover = primaryApprover;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
