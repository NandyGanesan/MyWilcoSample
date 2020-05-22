package com.android.wilcoconnect.model.expense;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodExpense {

    @SerializedName("code")
    public int code;

    @SerializedName("Status")
    public String Status;

    @SerializedName("message")
    public String message;

    @SerializedName("data")
    public ArrayList<FoodExpenseData> data;
}
