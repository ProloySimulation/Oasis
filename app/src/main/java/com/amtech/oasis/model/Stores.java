package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class Stores {
    @SerializedName("name")
    private String storeName;
    @SerializedName("latitude")
    private String storeLat;
    @SerializedName("longitude")
    private String storeLong;
    @SerializedName("region")
    private String region;
    @SerializedName("country")
    private String country;
    @SerializedName("id")
    private String storeId;

    public String getStoreId() {
        return storeId;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreLat() {
        return storeLat;
    }

    public String getStoreLong() {
        return storeLong;
    }
}
