package com.amtech.oasis.model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AssignStorArr {
    @SerializedName("name")
    private String taskName;
    @SerializedName("task_start_datetime")
    private String taskDate;
    @SerializedName("task_end_datetime")
    private String taskEndDate;
    @SerializedName("id")
    private String taskId;
    @SerializedName("task_status")
    private String taskStatus;
    @SerializedName("assigneeStore")
    private ArrayList<AssignStore> assignStores;
    @SerializedName("tasks")
    private ArrayList<Tasks> allTasks;
    @SerializedName("checkListData")
    private ArrayList<CheckList> checkLists;
    @SerializedName("store")
    private List<Stores> stores;

    @SerializedName("latitude")
    private String storeLat;
    @SerializedName("longitude")
    private String storeLong;
    @SerializedName("region")
    private String region;
    @SerializedName("country")
    private String country;


    public String getStoreLat() {
        return storeLat;
    }

    public String getStoreLong() {
        return storeLong;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public List<Stores> getStores() {
        return stores;
    }

    public ArrayList<CheckList> getCheckLists() {
        return checkLists;
    }

    public ArrayList<Tasks> getAllTasks() {
        return allTasks;
    }

    public ArrayList<AssignStore> getAssignStores() {
        return assignStores;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public String getTaskId() {
        return taskId;
    }
}
