package com.android.wilcoconnect.model.wilcoconnect;

import com.android.wilcoconnect.model.wilcoconnect.RequestFunction;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportFunction {

        @SerializedName("SupportFunctionID")
        public int SupportFunctionID;

        @SerializedName("SupportFunctionName")
        public String SupportFunctionName;

        @SerializedName("FunctionShortForm")
        public String FunctionShortForm;

        @SerializedName("Department")
        public String Department;

        @SerializedName("ActiveStatus")
        public boolean ActiveStatus;

        @SerializedName("requestType")
        public List<RequestFunction> requestType;

        public List<RequestFunction> getRequestType() {
            return requestType;
        }

        public void setRequestType(List<RequestFunction> requestType) {
            this.requestType = requestType;
        }

        public int getSupportFunctionID() {
            return SupportFunctionID;
        }

        public void setSupportFunctionID(int supportFunctionID) {
            SupportFunctionID = supportFunctionID;
        }

        public String getSupportFunctionName() {
            return SupportFunctionName;
        }

        public void setSupportFunctionName(String supportFunctionName) {
            SupportFunctionName = supportFunctionName;
        }

        public String getFunctionShortForm() {
            return FunctionShortForm;
        }

        public void setFunctionShortForm(String functionShortForm) {
            FunctionShortForm = functionShortForm;
        }

        public String getDepartment() {
            return Department;
        }

        public void setDepartment(String department) {
            Department = department;
        }

        public boolean isActiveStatus() {
            return ActiveStatus;
        }

        public void setActiveStatus(boolean activeStatus) {
            ActiveStatus = activeStatus;
        }
    }


