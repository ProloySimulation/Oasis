package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class Dashboard {
    @SerializedName("taskCompleted")
    private String completedTask;
    @SerializedName("taskPending")
    private String pendingTask;
    @SerializedName("assignedStoreCount")
    private String assignedTask;

    public String getCompletedTask() {
        return completedTask;
    }

    public String getPendingTask() {
        return pendingTask;
    }

    public String getAssignedTask() {
        return assignedTask;
    }
}
