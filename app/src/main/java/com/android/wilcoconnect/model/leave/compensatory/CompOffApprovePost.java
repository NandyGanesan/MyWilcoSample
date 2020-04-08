package com.android.wilcoconnect.model.leave.compensatory;

public class CompOffApprovePost {

    public String email;

    public String employeeCode;

    public int empCompensatoryID;

    public String status;

    public String approverRemarks;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproverRemarks() {
        return approverRemarks;
    }

    public void setApproverRemarks(String approverRemarks) {
        this.approverRemarks = approverRemarks;
    }
}
