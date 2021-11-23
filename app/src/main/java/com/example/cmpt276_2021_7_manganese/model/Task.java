package com.example.cmpt276_2021_7_manganese.model;

/**
 * This class is for Task
 * There are some basic operations for controlling each task
 * @author Shuai Li for Iteration2
 */
public class Task {
    String taskInfo;
    private final String TASK_CONTENT = "Task:";
    private Child curChildForTask;
    private ChildManager childManager;
    private int childIndex;

    public Task(String taskInfo) {
        childManager = ChildManager.getInstance();
        this.taskInfo = taskInfo;
        childIndex = 0;
    }

    public void childDoneTask() {
        updateInformation();
        if (childIndex != -1) {
            if (childIndex == (childManager.getSize() - 1)) {
                childIndex = 0;
            } else {
                childIndex++;
            }
        }
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public String getCurChildName() {
        updateInformation();
        if (childIndex == -1) {
            return "No Children";
        }
        curChildForTask = childManager.getByIndex(childIndex);
        return curChildForTask.getName();
    }

    public Child getCurChild() {
        return curChildForTask;
    }

    private void updateInformation() {
        childManager = ChildManager.getInstance();
        updateIndex();
    }

    private void updateIndex() {
        int childrenNumber = childManager.getSize();
        if (childrenNumber == 0) {
            childIndex = -1;
        } else if (childIndex == -1) {
            childIndex = 0;
        }
    }

    @Override
    public String toString() {
        return TASK_CONTENT + taskInfo ;
    }
}