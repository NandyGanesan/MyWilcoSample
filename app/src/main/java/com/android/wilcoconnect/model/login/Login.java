package com.android.wilcoconnect.model.login;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("email")
    public String email;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @Nullable
    @SerializedName("grant_type")
    public String grant_type;

   public Login(String username, String password, @Nullable String grant_type) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Nullable
    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(@Nullable String grant_type) {
        this.grant_type = grant_type;
    }
}
