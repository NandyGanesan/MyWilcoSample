package com.android.wilcoconnect.model.leave.Onduty;

public class OnDutyPost {

    public String email;

    public String employeeCode;

    public int workFromHomeEmployeeRequestID;

    public int onDutyID;

    public int noofDays;

    public String fromDate;

    public String toDate;

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

    public int getOnDutyID() {
        return onDutyID;
    }

    public void setOnDutyID(int onDutyID) {
        this.onDutyID = onDutyID;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
