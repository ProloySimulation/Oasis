package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignDataObj {
    @SerializedName("data")
    private List<Stores> assignStorArr;

    public List<Stores> getAssignStorArr() {
        return assignStorArr;
    }
}
