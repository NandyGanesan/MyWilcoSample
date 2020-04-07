package com.android.wilcoconnect.model.leave.leavebalance;

import com.google.gson.annotations.SerializedName;

public class LeaveDetails {

    @SerializedName("LeaveTaken")
    public double LeaveTaken;

    @SerializedName("LeaveAvailability")
    public double LeaveAvailability;

    @SerializedName("LeaveTypeText")
    public String LeaveTypeText;

    @SerializedName("EmployeeName")
    public String EmployeeName;

    @SerializedName("EmployeeCode")
    public String EmployeeCode;

    @SerializedName("AppliedLeave")
    public double AppliedLeave;

    @SerializedName("LeaveTypeID")
    public int LeaveTypeID;

    @SerializedName("LeaveEligible")
    public double LeaveEligible;

    @SerializedName("ProbationNumberOfDays")
    public double ProbationNumberOfDays;

    @SerializedName("ConfirmationNumberOfDays")
    public double ConfirmationNumberOfDays;

    @SerializedName("PreviousDay")
    public String PreviousDay;

    @SerializedName("NextDays")
    public String NextDays;

    @SerializedName("ApprovalFlag")
    public boolean ApprovalFlag;

    @SerializedName("ProbConfirmationDays")
    public double ProbConfirmationDays;

    @SerializedName("LeaveCarryForward")
    public double LeaveCarryForward;

    @SerializedName("CompOffStartDate")
    public String CompOffStartDate;

    @SerializedName("CompOffEndDate")
    public String CompOffEndDate;

    public double getLeaveTaken() {
        return LeaveTaken;
    }

    public void setLeaveTaken(double leaveTaken) {
        LeaveTaken = leaveTaken;
    }

    public double getLeaveAvailability() {
        return LeaveAvailability;
    }

    public void setLeaveAvailability(double leaveAvailability) {
        LeaveAvailability = leaveAvailability;
    }

    public String getLeaveTypeText() {
        return LeaveTypeText;
    }

    public void setLeaveTypeText(String leaveTypeText) {
        LeaveTypeText = leaveTypeText;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        EmployeeCode = employeeCode;
    }

    public double getAppliedLeave() {
        return AppliedLeave;
    }

    public void setAppliedLeave(double appliedLeave) {
        AppliedLeave = appliedLeave;
    }

    public int getLeaveTypeID() {
        return LeaveTypeID;
    }

    public void setLeaveTypeID(int leaveTypeID) {
        LeaveTypeID = leaveTypeID;
    }

    public double getLeaveEligible() {
        return LeaveEligible;
    }

    public void setLeaveEligible(double leaveEligible) {
        LeaveEligible = leaveEligible;
    }

    public double getProbationNumberOfDays() {
        return ProbationNumberOfDays;
    }

    public void setProbationNumberOfDays(double probationNumberOfDays) {
        ProbationNumberOfDays = probationNumberOfDays;
    }

    public double getConfirmationNumberOfDays() {
        return ConfirmationNumberOfDays;
    }

    public void setConfirmationNumberOfDays(double confirmationNumberOfDays) {
        ConfirmationNumberOfDays = confirmationNumberOfDays;
    }

    public String getPreviousDay() {
        return PreviousDay;
    }

    public void setPreviousDay(String previousDay) {
        PreviousDay = previousDay;
    }

    public String getNextDays() {
        return NextDays;
    }

    public void setNextDays(String nextDays) {
        NextDays = nextDays;
    }

    public boolean isApprovalFlag() {
        return ApprovalFlag;
    }

    public void setApprovalFlag(boolean approvalFlag) {
        ApprovalFlag = approvalFlag;
    }

    public double getProbConfirmationDays() {
        return ProbConfirmationDays;
    }

    public void setProbConfirmationDays(double probConfirmationDays) {
        ProbConfirmationDays = probConfirmationDays;
    }

    public double getLeaveCarryForward() {
        return LeaveCarryForward;
    }

    public void setLeaveCarryForward(double leaveCarryForward) {
        LeaveCarryForward = leaveCarryForward;
    }

    public String getCompOffStartDate() {
        return CompOffStartDate;
    }

    public void setCompOffStartDate(String compOffStartDate) {
        CompOffStartDate = compOffStartDate;
    }

    public String getCompOffEndDate() {
        return CompOffEndDate;
    }

    public void setCompOffEndDate(String compOffEndDate) {
        CompOffEndDate = compOffEndDate;
    }
}
