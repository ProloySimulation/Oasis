package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("role")
    private String assignedName;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAssignedName() {
        return assignedName;
    }
}
