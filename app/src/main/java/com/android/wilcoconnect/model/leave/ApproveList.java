package com.android.wilcoconnect.model.leave;

public class ApproveList {

    public String EmployeeId;

    public String EmployeeName;

    public String FromDate;

    public String ToDate;

    public String LeaveType;

    public String LeaveStatus;

    public int No_of_days;

    public String Remarks;

    public int getNo_of_days() {
        return No_of_days;
    }

    public void setNo_of_days(int no_of_days) {
        No_of_days = no_of_days;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getLeaveType() {
        return LeaveType;
    }

    public void setLeaveType(String leaveType) {
        LeaveType = leaveType;
    }

    public String getLeaveStatus() {
        return LeaveStatus;
    }

    public void setLeaveStatus(String leaveStatus) {
        LeaveStatus = leaveStatus;
    }
}
