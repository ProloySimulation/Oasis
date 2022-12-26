package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class ProfileObject {
    @SerializedName("data")
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }
}
