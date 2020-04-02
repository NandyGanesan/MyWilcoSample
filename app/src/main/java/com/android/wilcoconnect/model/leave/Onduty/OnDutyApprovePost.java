package com.android.wilcoconnect.model.leave.Onduty;

public class OnDutyApprovePost {

    public String email;

    public String employeeCode;

    public int workFromHomeEmployeeRequestID;

    public String requeststatus;

    public String reason;

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

    public int getWorkFromHomeEmployeeRequestID() {
        return workFromHomeEmployeeRequestID;
    }

    public void setWorkFromHomeEmployeeRequestID(int workFromHomeEmployeeRequestID) {
        this.workFromHomeEmployeeRequestID = workFromHomeEmployeeRequestID;
    }

    public String getRequeststatus() {
        return requeststatus;
    }

    public void setRequeststatus(String requeststatus) {
        this.requeststatus = requeststatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
