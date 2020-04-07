package com.android.wilcoconnect.model.leave.leavebalance;

import com.google.gson.annotations.SerializedName;

public class LeaveTypeDetails {

    @SerializedName("LeaveTypeID")
    public int LeaveTypeID;

    @SerializedName("LeaveTypeText")
    public String LeaveTypeText;

    @SerializedName("ProbationNumberOfDays")
    public double ProbationNumberOfDays;

    @SerializedName("ConfirmationNumberOfDays")
    public double ConfirmationNumberOfDays;

    @SerializedName("ProRateFlag")
    public boolean ProRateFlag;

    @SerializedName("ProbConfirmationDays")
    public double ProbConfirmationDays;

    @SerializedName("ProbationFullNumberOfDays")
    public double ProbationFullNumberOfDays;

    @SerializedName("ConfirmationFullNumberOfDays")
    public double ConfirmationFullNumberOfDays;

    public int getLeaveTypeID() {
        return LeaveTypeID;
    }

    public void setLeaveTypeID(int leaveTypeID) {
        LeaveTypeID = leaveTypeID;
    }

    public String getLeaveTypeText() {
        return LeaveTypeText;
    }

    public void setLeaveTypeText(String leaveTypeText) {
        LeaveTypeText = leaveTypeText;
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

    public boolean isProRateFlag() {
        return ProRateFlag;
    }

    public void setProRateFlag(boolean proRateFlag) {
        ProRateFlag = proRateFlag;
    }

    public double getProbConfirmationDays() {
        return ProbConfirmationDays;
    }

    public void setProbConfirmationDays(double probConfirmationDays) {
        ProbConfirmationDays = probConfirmationDays;
    }

    public double getProbationFullNumberOfDays() {
        return ProbationFullNumberOfDays;
    }

    public void setProbationFullNumberOfDays(double probationFullNumberOfDays) {
        ProbationFullNumberOfDays = probationFullNumberOfDays;
    }

    public double getConfirmationFullNumberOfDays() {
        return ConfirmationFullNumberOfDays;
    }

    public void setConfirmationFullNumberOfDays(double confirmationFullNumberOfDays) {
        ConfirmationFullNumberOfDays = confirmationFullNumberOfDays;
    }
}
