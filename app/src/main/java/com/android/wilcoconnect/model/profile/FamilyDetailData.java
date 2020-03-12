package com.android.wilcoconnect.model.profile;

import com.google.gson.annotations.SerializedName;

public class FamilyDetailData {

    @SerializedName("EmployeeID")
    public String EmployeeID;

    @SerializedName("RelationID")
    public int RelationID;

    @SerializedName("Relation")
    public String Relation;

    @SerializedName("EmpFamilyID")
    public int EmpFamilyID;

    @SerializedName("Age")
    public int Age;

    @SerializedName("FamilyMemberName")
    public String FamilyMemberName;

    @SerializedName("Gender")
    public String Gender;

    @SerializedName("Occupation")
    public String Occupation;

    @SerializedName("EnableUser")
    public boolean EnableUser;

    @SerializedName("EmployeeName")
    public String EmployeeName;

    @SerializedName("FamilyPersonDateofBirth")
    public String FamilyPersonDateofBirth;

    @SerializedName("relationList")
    public String relationList;

    @SerializedName("Created_Date")
    public String Created_Date;

    @SerializedName("Created_By")
    public String Created_By;

    @SerializedName("Modified_Date")
    public String Modified_Date;

    @SerializedName("Modifed_By")
    public String Modifed_By;

    @SerializedName("lstFamily")
    public String lstFamily;

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public int getRelationID() {
        return RelationID;
    }

    public void setRelationID(int relationID) {
        RelationID = relationID;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public int getEmpFamilyID() {
        return EmpFamilyID;
    }

    public void setEmpFamilyID(int empFamilyID) {
        EmpFamilyID = empFamilyID;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getFamilyMemberName() {
        return FamilyMemberName;
    }

    public void setFamilyMemberName(String familyMemberName) {
        FamilyMemberName = familyMemberName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public boolean isEnableUser() {
        return EnableUser;
    }

    public void setEnableUser(boolean enableUser) {
        EnableUser = enableUser;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getFamilyPersonDateofBirth() {
        return FamilyPersonDateofBirth;
    }

    public void setFamilyPersonDateofBirth(String familyPersonDateofBirth) {
        FamilyPersonDateofBirth = familyPersonDateofBirth;
    }

    public String getRelationList() {
        return relationList;
    }

    public void setRelationList(String relationList) {
        this.relationList = relationList;
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

    public String getLstFamily() {
        return lstFamily;
    }

    public void setLstFamily(String lstFamily) {
        this.lstFamily = lstFamily;
    }
}