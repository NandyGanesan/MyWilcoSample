package com.android.wilcoconnect.model.wilcoconnect;

import com.google.gson.annotations.SerializedName;

public class RequestFunction {

        @SerializedName("RequestTypeID")
        public int RequestTypeID;

        @SerializedName("RequestDescription")
        public String RequestDescription;

        @SerializedName("SupportFunctionID")
        public int SupportFunctionID;

        @SerializedName("FunctionShortForm")
        public String FunctionShortForm;

        @SerializedName("ActiveStatus")
        public boolean ActiveStatus;

        @SerializedName("LocationSpecific")
        public boolean LocationSpecific;

        @SerializedName("CountrySpecific")
        public boolean CountrySpecific;

        @SerializedName("BranchSpecific")
        public boolean BranchSpecific;

        public int getRequestTypeID() {
            return RequestTypeID;
        }

        public void setRequestTypeID(int requestTypeID) {
            RequestTypeID = requestTypeID;
        }

        public String getRequestDescription() {
            return RequestDescription;
        }

        public void setRequestDescription(String requestDescription) {
            RequestDescription = requestDescription;
        }

        public int getSupportFunctionID() {
            return SupportFunctionID;
        }

        public void setSupportFunctionID(int supportFunctionID) {
            SupportFunctionID = supportFunctionID;
        }

        public String getFunctionShortForm() {
            return FunctionShortForm;
        }

        public void setFunctionShortForm(String functionShortForm) {
            FunctionShortForm = functionShortForm;
        }

        public boolean isActiveStatus() {
            return ActiveStatus;
        }

        public void setActiveStatus(boolean activeStatus) {
            ActiveStatus = activeStatus;
        }

        public boolean isLocationSpecific() {
            return LocationSpecific;
        }

        public void setLocationSpecific(boolean locationSpecific) {
            LocationSpecific = locationSpecific;
        }

        public boolean isCountrySpecific() {
            return CountrySpecific;
        }

        public void setCountrySpecific(boolean countrySpecific) {
            CountrySpecific = countrySpecific;
        }

        public boolean isBranchSpecific() {
            return BranchSpecific;
        }

        public void setBranchSpecific(boolean branchSpecific) {
            BranchSpecific = branchSpecific;
        }

    }
