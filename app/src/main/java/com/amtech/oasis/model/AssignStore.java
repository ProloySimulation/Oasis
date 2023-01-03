package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignStore {
    @SerializedName("stores")
    private List<Stores> stores;

    public List<Stores> getStores() {
        return stores;
    }
}
