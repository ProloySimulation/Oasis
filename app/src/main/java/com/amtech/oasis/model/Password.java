package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class Password {
    @SerializedName("currentPassword")
    private String currentPass;
    @SerializedName("password")
    private String newPass;
    @SerializedName("password_confirmation")
    private String confirmPass;

    public Password(String currentPass, String newPass, String confirmPass) {
        this.currentPass = currentPass;
        this.newPass = newPass;
        this.confirmPass = confirmPass;
    }
}
