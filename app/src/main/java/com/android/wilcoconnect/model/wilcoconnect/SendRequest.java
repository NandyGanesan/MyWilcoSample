package com.android.wilcoconnect.model.wilcoconnect;

import androidx.annotation.Nullable;

public class SendRequest {

    public String Email;

    public String companyCode;

    public String employeeID;

    public int SupportFunctionID;

    public String statusCode;

    public int masterID;

    @Nullable
    public String statusLink;

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
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public int getSupportFunctionID() {
        return SupportFunctionID;
    }

    public void setSupportFunctionID(int supportFunctionID) {
        SupportFunctionID = supportFunctionID;
    }

    @Nullable
    public String getStatusLink() {
        return statusLink;
    }

    public void setStatusLink(@Nullable String statusLink) {
        this.statusLink = statusLink;
    }
}
