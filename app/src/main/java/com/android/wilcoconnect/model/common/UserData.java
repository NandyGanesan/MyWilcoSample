package com.android.wilcoconnect.model.common;


import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserData {

    @SerializedName("Statuscode")
    public int Statuscode;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public Data data;

    public UserData() {
    }

    public class Data {

        @SerializedName("EmployeeID")
        public String EmployeeID;

        @SerializedName("LoginID")
        public int LoginID;

        @SerializedName("Password")
        public String Password;

        @SerializedName("Created_Date")
        public Date Created_Date;

        @Nullable
        @SerializedName("Created_By")
        public String Created_By;

        @SerializedName("Modified_Date")
        public Date Modified_Date;

        @SerializedName("Modifed_By")
        public String Modifed_By;

        @SerializedName("AllowLogin")
        public boolean AllowLogin;

        @SerializedName("Email")
        public String Email;

        @SerializedName("CompanyCode")
        public String CompanyCode;

        @SerializedName("FirstName")
        public String FirstName;

        public String getEmployeeID() {
            return EmployeeID;
        }

        public void setEmployeeID(String employeeID) {
            EmployeeID = employeeID;
        }

        public int getLoginID() {
            return LoginID;
        }

        public void setLoginID(int loginID) {
            LoginID = loginID;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public Date getCreated_Date() {
            return Created_Date;
        }

        public void setCreated_Date(Date created_Date) {
            Created_Date = created_Date;
        }

        @Nullable
        public String getCreated_By() {
            return Created_By;
        }

        public void setCreated_By(@Nullable String created_By) {
            Created_By = created_By;
        }

        public Date getModified_Date() {
            return Modified_Date;
        }

        public void setModified_Date(Date modified_Date) {
            Modified_Date = modified_Date;
        }

        public String getModifed_By() {
            return Modifed_By;
        }

        public void setModifed_By(String modifed_By) {
            Modifed_By = modifed_By;
        }

        public boolean isAllowLogin() {
            return AllowLogin;
        }

        public void setAllowLogin(boolean allowLogin) {
            AllowLogin = allowLogin;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getCompanyCode() {
            return CompanyCode;
        }

        public void setCompanyCode(String companyCode) {
            CompanyCode = companyCode;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }
    }

    public int getStatuscode() {
        return Statuscode;
    }

    public void setStatuscode(int statuscode) {
        Statuscode = statuscode;
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


}
