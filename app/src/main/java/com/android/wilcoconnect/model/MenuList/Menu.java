package com.android.wilcoconnect.model.MenuList;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Menu {

    @SerializedName("Statuscode")
    public int Statuscode;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<MainMenu> data;

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

    public ArrayList<MainMenu> getData() {
        return data;
    }

    public void setData(ArrayList<MainMenu> data) {
        this.data = data;
    }
}
