package com.android.wilcoconnect.model.leave;

public class ApprovePost {

    public String email;

    public String employeeCode;

    public int leaveRequestID;

    public String approveStatus;

    public String approvedRemarks;

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
        return leaveRequestID;
    }

    public void setLeaveRequestID(int leaveRequestID) {
        this.leaveRequestID = leaveRequestID;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedRemarks() {
        return approvedRemarks;
    }

    public void setApprovedRemarks(String approvedRemarks) {
        this.approvedRemarks = approvedRemarks;
    }
}
