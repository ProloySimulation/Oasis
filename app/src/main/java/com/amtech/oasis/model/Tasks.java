package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

public class Tasks {
    @SerializedName("name")
    private String taskName;
    @SerializedName("task_start_datetime")
    private String date;
    @SerializedName("id")
    private String taskId;

    public String getTaskName() {
        return taskName;
    }

    public String getDate() {
        return date;
    }

    public String getTaskId() {
        return taskId;
    }
}
