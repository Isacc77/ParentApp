package com.example.cmpt276_2021_7_manganese.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskManager implements Iterable<Task> {

    private ArrayList<Task> tasksManager = new ArrayList<>();
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


    public void add(String task) {
        tasksManager.add(new Task(task));
    }

    public void add(Task task) {
        tasksManager.add(task);
    }


    public void removeTask(int index) {
        if (index < 0 || index > tasksManager.size()) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_RANGE);
        } else {
            tasksManager.remove(index);
        }
    }

    public int getSize() {
        return tasksManager.size();
    }

    public ArrayList<Task> getTasksManager() {
        return tasksManager;
    }

    public void setTasksManager(ArrayList<Task> tasksManager) {
        this.tasksManager = tasksManager;
    }

    public String[] StringTaskData() {
        String[] Str = new String[tasksManager.size()];
        for (int i = 0; i < tasksManager.size(); i++) {
            Str[i] = tasksManager.get(i).getTaskInfo();
        }
        return Str;
    }




    @Override
    public Iterator<Task> iterator() {
        return tasksManager.iterator();
    }

    public void printAll() {
        int index = 0;
        for (Task t : tasksManager) {
            System.out.println(index++ + ": " + t);
        }
    }

    public Task getByIndex(int index) {
        if (index > tasksManager.size() || index < 0) {
            System.out.println(INDEX_OUT_OF_RANGE_FOR_GET_BY_INDEX + tasksManager.size());
            return new Task("");
        }
        return tasksManager.get(index);
    }
}
