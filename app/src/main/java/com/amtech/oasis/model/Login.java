package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String loginToken;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getLoginToken() {
        return loginToken;
    }
}
