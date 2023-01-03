package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class CheckList {
    @SerializedName("name")
    private String checkListName;

    public String getCheckListName() {
        return checkListName;
    }
}
