package com.android.wilcoconnect.model.leave.compensatory;

public class CompOffPost {

    public String email;

    public String employeeCode;

    public int empCompensatoryID;

    public int noofDays;

    public String fromDate;

    public String toDate;

    public String requestRemarks;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public int getEmpCompensatoryID() {
        return empCompensatoryID;
    }

    public void setEmpCompensatoryID(int empCompensatoryID) {
        this.empCompensatoryID = empCompensatoryID;
    }

    public int getNoofDays() {
        return noofDays;
    }

    public void setNoofDays(int noofDays) {
        this.noofDays = noofDays;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getRequestRemarks() {
        return requestRemarks;
    }

    public void setRequestRemarks(String requestRemarks) {
        this.requestRemarks = requestRemarks;
    }
}
