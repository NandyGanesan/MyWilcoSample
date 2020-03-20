package com.android.wilcoconnect.model.profile;

import com.google.gson.annotations.SerializedName;

public class ReferenceDetailData {

    @SerializedName("EmpReferenceID")
    public int EmpReferenceID;

    @SerializedName("EmployeeID")
    public String EmployeeID;

    @SerializedName("EmployeeName")
    public String EmployeeName;

    @SerializedName("Name")
    public String Name;

    @SerializedName("Gender")
    public String Gender;

    @SerializedName("Organisation")
    public String Organisation;

    @SerializedName("Designation")
    public String Designation;

    @SerializedName("Duration")
    public String Duration;

    @SerializedName("NatureofAssociation")
    public String NatureofAssociation;

    @SerializedName("EmailID")
    public String EmailID;

    @SerializedName("ContactNumber")
    public String ContactNumber;

    @SerializedName("Created_Date")
    public String Created_Date;

    @SerializedName("Created_By")
    public String Created_By;

    @SerializedName("Modified_Date")
    public String Modified_Date;

    @SerializedName("Modifed_By")
    public String Modifed_By;

    public int getEmpReferenceID() {
        return EmpReferenceID;
    }

    public void setEmpReferenceID(int empReferenceID) {
        EmpReferenceID = empReferenceID;
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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getOrganisation() {
        return Organisation;
    }

    public void setOrganisation(String organisation) {
        Organisation = organisation;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getNatureofAssociation() {
        return NatureofAssociation;
    }

    public void setNatureofAssociation(String natureofAssociation) {
        NatureofAssociation = natureofAssociation;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
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
}
