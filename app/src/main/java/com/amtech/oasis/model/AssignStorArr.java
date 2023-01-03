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
    @SerializedName("id")
    private String taskId;
    @SerializedName("assigneeStore")
    private ArrayList<AssignStore> assignStores;
    @SerializedName("tasks")
    private ArrayList<Tasks> allTasks;
    @SerializedName("checkListData")
    private ArrayList<CheckList> checkLists;
    @SerializedName("store")
    private List<Stores> stores;

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
