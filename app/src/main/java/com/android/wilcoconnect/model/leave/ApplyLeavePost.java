package com.android.wilcoconnect.model.leave;

public class ApplyLeavePost {

    public String email;

    public String employeeCode;

    public int LeaveRequestID;

    public int leaveTypeID;

    public double noofDays;

    public String fromDate;

    public String toDate;

    public String session;

    public String requestRemarks;

    public String leaveforEmployeeID;

    public String getLeaveforEmployeeID() {
        return leaveforEmployeeID;
    }

    public void setLeaveforEmployeeID(String leaveforEmployeeID) {
        this.leaveforEmployeeID = leaveforEmployeeID;
    }

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

    public int getLeaveRequestID() {
        return LeaveRequestID;
    }

    public void setLeaveRequestID(int leaveRequestID) {
        LeaveRequestID = leaveRequestID;
    }

    public int getLeaveTypeID() {
        return leaveTypeID;
    }

    public void setLeaveTypeID(int leaveTypeID) {
        this.leaveTypeID = leaveTypeID;
    }

    public double getNoofDays() {
        return noofDays;
    }

    public void setNoofDays(double noofDays) {
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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getRequestRemarks() {
        return requestRemarks;
    }

    public void setRequestRemarks(String requestRemarks) {
        this.requestRemarks = requestRemarks;
    }
}
