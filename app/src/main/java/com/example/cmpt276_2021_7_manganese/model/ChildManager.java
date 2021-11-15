/**
 * This class is for ChildManager
 */
package com.example.cmpt276_2021_7_manganese.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This class is for ChildManager
 * it use singleton model
 * we can populate data Children data by this class
 * @author  Shuai Li
 */
public class ChildManager implements Iterable<Child> {
    private ArrayList<Child> manager = new ArrayList<>();
    private static ChildManager instance;
    String json;

    public ChildManager() {
    }

    public static ChildManager getInstance() {
        if (instance == null) {
            instance = new ChildManager();
        }
        return instance;
    }

    public static ChildManager getInstance(ChildManager Manager) {
        if (instance == null) {
            instance = Manager;
        }
        return instance;
    }

    public void add(Child child) {
        manager.add(child);
    }

    public void removeChild(int index) {
        if (index < 0 || index > manager.size()) {
            throw new IndexOutOfBoundsException("Index out of range");
        } else {
            manager.remove(index);
        }
    }

    public int getSize() {
        return manager.size();
    }

    public ArrayList<Child> getManager() {
        return manager;
    }

    public void setManager(ArrayList<Child> manager) {
        this.manager = manager;
    }

    public String[] StringChildData() {
        String[] Str = new String[manager.size()];
        for (int i = 0; i < manager.size(); i++) {
            Str[i] = manager.get(i).getName();
        }
        return Str;
    }

    @Override
    public Iterator<Child> iterator() {
        return manager.iterator();
    }

    public void printAll() {
        int cnt = 0;
        for (Child c : manager) {
            System.out.println(cnt++ + ": " + c);
        }
    }

    public Child getByIndex(int index) {
        if (index > manager.size() || index < 0) {
            System.out.println("PROBLEM: in getByIndex, i should be [0, " + manager.size() + ").");
            return new Child("");
        }
        return manager.get(index);
    }

    public void TransferToDatabase(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Child", Context.MODE_PRIVATE);
        Gson getGson = new GsonBuilder().create();
        json = getGson.toJson(manager);
        preferences.edit().putString("Child", json).commit();
    }

    public void UseDatabase(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Child", Context.MODE_PRIVATE);
        Gson getGson = new GsonBuilder().create();
        json = getGson.toJson(manager);
        manager = getGson.fromJson(preferences.getString("Child", "[]"), new TypeToken<ArrayList<Child>>() {
        }.getType());
        for (Child child : manager) {
            manager.add(child);
        }
    }

    public String getGsonString() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        return jsonString;
    }

    public void load(String jsonString) {
        Gson gson = new Gson();
        ChildManager loaded = gson.fromJson(jsonString, ChildManager.class);
        manager = loaded.manager;
    }
}
