package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class DashboardObject {
    @SerializedName("data")
    private Dashboard dashboard;

    public Dashboard getDashboard() {
        return dashboard;
    }
}
