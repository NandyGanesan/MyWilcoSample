package com.android.wilcoconnect.model.profile;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AttachmentDetails {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<Data> data;

    public class Data{

        @SerializedName("objAttachement")
        public AttachmentDetailData objAttachement;

        public AttachmentDetailData getObjAttachement() {
            return objAttachement;
        }

        public void setObjAttachement(AttachmentDetailData objAttachement) {
            this.objAttachement = objAttachement;
        }
    }

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

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
