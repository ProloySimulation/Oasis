package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoreDataObj {
    @SerializedName("data")
    private List<AssignStorArr> assignStorArr;

    public List<AssignStorArr> getAssignStorArr() {
        return assignStorArr;
    }
}
