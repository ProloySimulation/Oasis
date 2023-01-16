package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class CheckList {
    @SerializedName("id")
    private String checkId;
    @SerializedName("name")
    private String checkListName;
    @SerializedName("isCaptureRequire")
    private String imageCheck;
    @SerializedName("status")
    private String checkStatus;
    @SerializedName("image")
    private String image;
    @SerializedName("longitude")
    private String storeLong;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("latitude")
    private String storeLat;

    public String getStoreLong() {
        return storeLong;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getStoreLat() {
        return storeLat;
    }

    public String getImage() {
        return image;
    }

    public String getCheckId() {
        return checkId;
    }

    public String getCheckListName() {
        return checkListName;
    }

    public String getImageCheck() {
        return imageCheck;
    }

    public String getCheckStatus() {
        return checkStatus;
    }
}
