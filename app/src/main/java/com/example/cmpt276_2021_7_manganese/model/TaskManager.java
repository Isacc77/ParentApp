package com.example.cmpt276_2021_7_manganese.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskManager implements Iterable<Task> {
    private ArrayList<Task> taskManager = new ArrayList<>();
    private static TaskManager TaskInstance;
    private final String INDEX_OUT_OF_RANGE = "Index out of range";
    private final String INDEX_OUT_OF_RANGE_FOR_GET_BY_INDEX = "PROBLEM: in getByIndex, i should be from 0 to ";

    public TaskManager() {
    }

    public static TaskManager getInstance() {
        if (TaskInstance == null) {
            TaskInstance = new TaskManager();
        }
        return TaskInstance;
    }

    public static TaskManager getInstance(TaskManager Manager) {
        if (TaskInstance == null) {
            TaskInstance = Manager;
        }
        return TaskInstance;
    }

    public void add(Task task) {
        taskManager.add(task);
    }

    public void removeTask(int index) {
        if (index < 0 || index > taskManager.size()) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_RANGE);
        } else {
            taskManager.remove(index);
        }
    }

    public int getSize() {
        return taskManager.size();
    }

    public ArrayList<Task> getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(ArrayList<Task> taskManager) {
        this.taskManager = taskManager;
    }

    public String[] StringTaskData() {
        String[] Str = new String[taskManager.size()];
        for (int i = 0; i < taskManager.size(); i++) {
            Str[i] = taskManager.get(i).getTaskInfo();
        }
        return Str;
    }

    @Override
    public Iterator<Task> iterator() {
        return taskManager.iterator();
    }

    public void printAll() {
        int index = 0;
        for (Task t : taskManager) {
            System.out.println(index++ + ": " + t);
        }
    }

    public Task getByIndex(int index) {
        if (index > taskManager.size() || index < 0) {
            System.out.println(INDEX_OUT_OF_RANGE_FOR_GET_BY_INDEX + taskManager.size());
            return new Task("");
        }
        return taskManager.get(index);
    }

    public String getGsonStringForTask() {
        Gson gsonTask = new Gson();
        String jsonStringForTask = gsonTask.toJson(this);
        return jsonStringForTask;
    }

    public void loadTaskInfo(String jsonString) {
        Gson gsonTask = new Gson();
        TaskManager loaded = gsonTask.fromJson(jsonString, TaskManager.class);
        taskManager = loaded.taskManager;
    }

}
