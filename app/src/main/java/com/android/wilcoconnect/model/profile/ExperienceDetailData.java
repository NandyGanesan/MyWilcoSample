package com.android.wilcoconnect.model.profile;

import com.google.gson.annotations.SerializedName;

public class ExperienceDetailData {

    @SerializedName("EmpExperienceID")
    public int EmpExperienceID;

    @SerializedName("EmployeeID")
    public String EmployeeID;

    @SerializedName("EmployeeName")
    public String EmployeeName;

    @SerializedName("Organisation")
    public String Organisation;

    @SerializedName("FromDate")
    public String FromDate;

    @SerializedName("strFromDate")
    public String strFromDate;

    @SerializedName("strToDate")
    public String strToDate;

    @SerializedName("ToDate")
    public String ToDate;

    @SerializedName("LastPosition")
    public String LastPosition;

    @SerializedName("InitialPosition")
    public String InitialPosition;

    @SerializedName("JobResponsibility")
    public String JobResponsibility;

    @SerializedName("SuperiorPosition")
    public String SuperiorPosition;

    @SerializedName("GrossSalary")
    public long GrossSalary;

    @SerializedName("LeavingReason")
    public String LeavingReason;

    @SerializedName("Created_Date")
    public String Created_Date;

    @SerializedName("Created_By")
    public String Created_By;

    @SerializedName("Modified_Date")
    public String Modified_Date;

    @SerializedName("Modifed_By")
    public String Modifed_By;

    @SerializedName("UrlLocation")
    public String UrlLocation;

    @SerializedName("URL")
    public String URL;

    @SerializedName("DocumentID")
    public int DocumentID;

    @SerializedName("AttachmentID")
    public String AttachmentID;

    public int getEmpExperienceID() {
        return EmpExperienceID;
    }

    public void setEmpExperienceID(int empExperienceID) {
        EmpExperienceID = empExperienceID;
    }

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getOrganisation() {
        return Organisation;
    }

    public void setOrganisation(String organisation) {
        Organisation = organisation;
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

    public String getStrToDate() {
        return strToDate;
    }

    public void setStrToDate(String strToDate) {
        this.strToDate = strToDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getLastPosition() {
        return LastPosition;
    }

    public void setLastPosition(String lastPosition) {
        LastPosition = lastPosition;
    }

    public String getInitialPosition() {
        return InitialPosition;
    }

    public void setInitialPosition(String initialPosition) {
        InitialPosition = initialPosition;
    }

    public String getJobResponsibility() {
        return JobResponsibility;
    }

    public void setJobResponsibility(String jobResponsibility) {
        JobResponsibility = jobResponsibility;
    }

    public String getSuperiorPosition() {
        return SuperiorPosition;
    }

    public void setSuperiorPosition(String superiorPosition) {
        SuperiorPosition = superiorPosition;
    }

    public long getGrossSalary() {
        return GrossSalary;
    }

    public void setGrossSalary(long grossSalary) {
        GrossSalary = grossSalary;
    }

    public String getLeavingReason() {
        return LeavingReason;
    }

    public void setLeavingReason(String leavingReason) {
        LeavingReason = leavingReason;
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

    public String getModifed_By() {
        return Modifed_By;
    }

    public void setModifed_By(String modifed_By) {
        Modifed_By = modifed_By;
    }

    public String getUrlLocation() {
        return UrlLocation;
    }

    public void setUrlLocation(String urlLocation) {
        UrlLocation = urlLocation;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(int documentID) {
        DocumentID = documentID;
    }

    public String getAttachmentID() {
        return AttachmentID;
    }

    public void setAttachmentID(String attachmentID) {
        AttachmentID = attachmentID;
    }
}
