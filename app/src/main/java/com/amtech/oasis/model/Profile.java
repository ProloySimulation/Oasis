package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("role")
    private String assignedName;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public Profile(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getUserId() {
        return userId;
    }

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
