package com.android.wilcoconnect.model.leave.compensatory;

import com.google.gson.annotations.SerializedName;

public class GetCompOffDays {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        @SerializedName("CompensatoryLeaveConfigID")
        public int CompensatoryLeaveConfigID;

        @SerializedName("CreateBefore")
        public int CreateBefore;

        @SerializedName("AvailBefore")
        public int AvailBefore;

        @SerializedName("ActiveStatus")
        public boolean ActiveStatus;

        @SerializedName("CreatedDate")
        public String CreatedDate;

        @SerializedName("CreatedBy")
        public String CreatedBy;

        @SerializedName("ModifiedDate")
        public String ModifiedDate;

        @SerializedName("ModifiedBy")
        public String ModifiedBy;

        public int getCompensatoryLeaveConfigID() {
            return CompensatoryLeaveConfigID;
        }

        public void setCompensatoryLeaveConfigID(int compensatoryLeaveConfigID) {
            CompensatoryLeaveConfigID = compensatoryLeaveConfigID;
        }

        public int getCreateBefore() {
            return CreateBefore;
        }

        public void setCreateBefore(int createBefore) {
            CreateBefore = createBefore;
        }

        public int getAvailBefore() {
            return AvailBefore;
        }

        public void setAvailBefore(int availBefore) {
            AvailBefore = availBefore;
        }

        public boolean isActiveStatus() {
            return ActiveStatus;
        }

        public void setActiveStatus(boolean activeStatus) {
            ActiveStatus = activeStatus;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String createdDate) {
            CreatedDate = createdDate;
        }

        public String getCreatedBy() {
            return CreatedBy;
        }

        public void setCreatedBy(String createdBy) {
            CreatedBy = createdBy;
        }

        public String getModifiedDate() {
            return ModifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            ModifiedDate = modifiedDate;
        }

        public String getModifiedBy() {
            return ModifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            ModifiedBy = modifiedBy;
        }
    }
}
