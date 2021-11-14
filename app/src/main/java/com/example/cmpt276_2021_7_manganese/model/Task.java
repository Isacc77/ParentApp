package com.example.cmpt276_2021_7_manganese.model;

public class Task {

    String taskInfo;
    private final String TASK_CONTENT = "Task:";

    public Task(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public String toString() {
        return TASK_CONTENT + taskInfo ;
    }

}

