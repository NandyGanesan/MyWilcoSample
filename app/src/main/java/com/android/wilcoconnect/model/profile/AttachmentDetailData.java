package com.android.wilcoconnect.model.profile;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AttachmentDetailData {

    @SerializedName("SerialNo")
    public int SerialNo;

    @SerializedName("DocumentID")
    public String DocumentID;

    @SerializedName("DocumentList")
    public ArrayList<AttachmentsList> DocumentList;

    public class AttachmentsList{

        @SerializedName("AttachmentFileName")
        public String AttachmentFileName;

        @SerializedName("DocumentID")
        public int DocumentID;

        @SerializedName("Sequence")
        public int Sequence;

        @SerializedName("SerialNo")
        public int SerialNo;

        @SerializedName("UrlLocation")
        public String UrlLocation;

        @SerializedName("lstserialno")
        public ArrayList<Integer> lstserialno;

        @SerializedName("filelist")
        public ArrayList<String> filelist;

        @SerializedName("fileicontype")
        public ArrayList<String> fileicontype;

        @SerializedName("url")
        public String url;

        public String getAttachmentFileName() {
            return AttachmentFileName;
        }

        public void setAttachmentFileName(String attachmentFileName) {
            AttachmentFileName = attachmentFileName;
        }

        public int getDocumentID() {
            return DocumentID;
        }

        public void setDocumentID(int documentID) {
            DocumentID = documentID;
        }

        public int getSequence() {
            return Sequence;
        }

        public void setSequence(int sequence) {
            Sequence = sequence;
        }

        public int getSerialNo() {
            return SerialNo;
        }

        public void setSerialNo(int serialNo) {
            SerialNo = serialNo;
        }

        public String getUrlLocation() {
            return UrlLocation;
        }

        public void setUrlLocation(String urlLocation) {
            UrlLocation = urlLocation;
        }

        public ArrayList<Integer> getLstserialno() {
            return lstserialno;
        }

        public void setLstserialno(ArrayList<Integer> lstserialno) {
            this.lstserialno = lstserialno;
        }

        public ArrayList<String> getFilelist() {
            return filelist;
        }

        public void setFilelist(ArrayList<String> filelist) {
            this.filelist = filelist;
        }

        public ArrayList<String> getFileicontype() {
            return fileicontype;
        }

        public void setFileicontype(ArrayList<String> fileicontype) {
            this.fileicontype = fileicontype;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public int getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(int serialNo) {
        SerialNo = serialNo;
    }

    public String getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    public ArrayList<AttachmentsList> getDocumentList() {
        return DocumentList;
    }

    public void setDocumentList(ArrayList<AttachmentsList> documentList) {
        DocumentList = documentList;
    }
}
