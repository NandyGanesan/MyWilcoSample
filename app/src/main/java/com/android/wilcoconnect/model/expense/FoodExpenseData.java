package com.android.wilcoconnect.model.expense;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FoodExpenseData {

    @SerializedName("ID")
    public int ID;

    @SerializedName("BillDate")
    public String BillDate;

    @SerializedName("strBillDate")
    public String strBillDate;

    @SerializedName("Remarks")
    public String Remarks;

    @SerializedName("RequestedBy")
    public String RequestedBy;

    @SerializedName("strRequestedDate")
    public String strRequestedDate;

    @SerializedName("RequestedDate")
    public String RequestedDate;

    @SerializedName("RequestedAmount")
    public double RequestedAmount;

    @SerializedName("ProjectID")
    public String ProjectID;

    @SerializedName("Status")
    public String Status;

    @SerializedName("ActionBy")
    public String ActionBy;

    @SerializedName("ApprovedPerson")
    public String ApprovedPerson;

    @SerializedName("ActionDate")
    public String ActionDate;

    @SerializedName("ActionRemarks")
    public String ActionRemarks;

    @SerializedName("Created_Date")
    public String Created_Date;

    @SerializedName("Created_By")
    public String Created_By;

    @SerializedName("Modified_Date")
    public String Modified_Date;

    @SerializedName("Modified_By")
    public String Modified_By;

    @SerializedName("ApprovedAmount")
    public double ApprovedAmount;

    @SerializedName("FoodReceipt")
    public String FoodReceipt;

    @SerializedName("ProjectName")
    public String ProjectName;

    @SerializedName("RequesterName")
    public String RequesterName;

    @SerializedName("LastName")
    public String LastName;

    @SerializedName("Receipt")
    public String Receipt;

    @SerializedName("ReceiptAttachmentCount")
    public int ReceiptAttachmentCount;

    @SerializedName("ReceiptAttach")
    public String ReceiptAttach;

    @SerializedName("fileReceiptlist")
    public String fileReceiptlist;

    @SerializedName("fileReceipticontype")
    public String fileReceipticontype;

    @SerializedName("EitherProjectOrNot")
    public String EitherProjectOrNot;

    @SerializedName("purposeType")
    public String purposeType;

    @SerializedName("EitherApproveOrReject")
    public String EitherApproveOrReject;

    @SerializedName("ClaimNumber")
    public String ClaimNumber;

    @SerializedName("SettlementAmount")
    public String SettlementAmount;

    @SerializedName("RemarksFromFinance")
    public String RemarksFromFinance;

    @SerializedName("")
    public List<EmpReceiptList> empReceiptList;

    public class EmpReceiptList {

        @SerializedName("ID")
        public int ID;

        @SerializedName("filepath")
        public ArrayList<String> filepath;

        @SerializedName("fileReceipticontype")
        public ArrayList<String> fileReceipticontype;

        @SerializedName("ReceiptAttachmentCount")
        public int ReceiptAttachmentCount;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public ArrayList<String> getFilepath() {
            return filepath;
        }

        public void setFilepath(ArrayList<String> filepath) {
            this.filepath = filepath;
        }

        public ArrayList<String> getFileReceipticontype() {
            return fileReceipticontype;
        }

        public void setFileReceipticontype(ArrayList<String> fileReceipticontype) {
            this.fileReceipticontype = fileReceipticontype;
        }

        public int getReceiptAttachmentCount() {
            return ReceiptAttachmentCount;
        }

        public void setReceiptAttachmentCount(int receiptAttachmentCount) {
            ReceiptAttachmentCount = receiptAttachmentCount;
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBillDate() {
        return BillDate;
    }

    public void setBillDate(String billDate) {
        BillDate = billDate;
    }

    public String getStrBillDate() {
        return strBillDate;
    }

    public void setStrBillDate(String strBillDate) {
        this.strBillDate = strBillDate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        RequestedBy = requestedBy;
    }

    public String getStrRequestedDate() {
        return strRequestedDate;
    }

    public void setStrRequestedDate(String strRequestedDate) {
        this.strRequestedDate = strRequestedDate;
    }

    public String getRequestedDate() {
        return RequestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        RequestedDate = requestedDate;
    }

    public double getRequestedAmount() {
        return RequestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        RequestedAmount = requestedAmount;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getActionBy() {
        return ActionBy;
    }

    public void setActionBy(String actionBy) {
        ActionBy = actionBy;
    }

    public String getApprovedPerson() {
        return ApprovedPerson;
    }

    public void setApprovedPerson(String approvedPerson) {
        ApprovedPerson = approvedPerson;
    }

    public String getActionDate() {
        return ActionDate;
    }

    public void setActionDate(String actionDate) {
        ActionDate = actionDate;
    }

    public String getActionRemarks() {
        return ActionRemarks;
    }

    public void setActionRemarks(String actionRemarks) {
        ActionRemarks = actionRemarks;
    }

    public String getCreated_Date() {
        return Created_Date;
    }

    public void setCreated_Date(String created_Date) {
        Created_Date = created_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        Created_By = created_By;
    }

    public String getModified_Date() {
        return Modified_Date;
    }

    public void setModified_Date(String modified_Date) {
        Modified_Date = modified_Date;
    }

    public String getModified_By() {
        return Modified_By;
    }

    public void setModified_By(String modified_By) {
        Modified_By = modified_By;
    }

    public double getApprovedAmount() {
        return ApprovedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        ApprovedAmount = approvedAmount;
    }

    public String getFoodReceipt() {
        return FoodReceipt;
    }

    public void setFoodReceipt(String foodReceipt) {
        FoodReceipt = foodReceipt;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getRequesterName() {
        return RequesterName;
    }

    public void setRequesterName(String requesterName) {
        RequesterName = requesterName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getReceipt() {
        return Receipt;
    }

    public void setReceipt(String receipt) {
        Receipt = receipt;
    }

    public int getReceiptAttachmentCount() {
        return ReceiptAttachmentCount;
    }

    public void setReceiptAttachmentCount(int receiptAttachmentCount) {
        ReceiptAttachmentCount = receiptAttachmentCount;
    }

    public String getReceiptAttach() {
        return ReceiptAttach;
    }

    public void setReceiptAttach(String receiptAttach) {
        ReceiptAttach = receiptAttach;
    }

    public String getFileReceiptlist() {
        return fileReceiptlist;
    }

    public void setFileReceiptlist(String fileReceiptlist) {
        this.fileReceiptlist = fileReceiptlist;
    }

    public String getFileReceipticontype() {
        return fileReceipticontype;
    }

    public void setFileReceipticontype(String fileReceipticontype) {
        this.fileReceipticontype = fileReceipticontype;
    }

    public String getEitherProjectOrNot() {
        return EitherProjectOrNot;
    }

    public void setEitherProjectOrNot(String eitherProjectOrNot) {
        EitherProjectOrNot = eitherProjectOrNot;
    }

    public String getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }

    public String getEitherApproveOrReject() {
        return EitherApproveOrReject;
    }

    public void setEitherApproveOrReject(String eitherApproveOrReject) {
        EitherApproveOrReject = eitherApproveOrReject;
    }

    public String getClaimNumber() {
        return ClaimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        ClaimNumber = claimNumber;
    }

    public String getSettlementAmount() {
        return SettlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        SettlementAmount = settlementAmount;
    }

    public String getRemarksFromFinance() {
        return RemarksFromFinance;
    }

    public void setRemarksFromFinance(String remarksFromFinance) {
        RemarksFromFinance = remarksFromFinance;
    }

    public List<EmpReceiptList> getEmpReceiptList() {
        return empReceiptList;
    }

    public void setEmpReceiptList(List<EmpReceiptList> empReceiptList) {
        this.empReceiptList = empReceiptList;
    }
}
