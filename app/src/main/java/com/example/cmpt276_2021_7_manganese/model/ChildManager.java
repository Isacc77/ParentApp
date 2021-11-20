/**
 * This class is for ChildManager
 */
package com.example.cmpt276_2021_7_manganese.model;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;

/**
 * This class is for ChildManager
 * it use singleton model
 * we can populate data Children data by this class
 * @author  Shuai Li
 */
public class ChildManager implements Iterable<Child> {
    private ArrayList<Child> childArrayList = new ArrayList<>();
    private static ChildManager instance;

    private ChildManager() {
    }

    public static ChildManager getInstance() {
        if (instance == null) {
            instance = new ChildManager();
        }
        return instance;
    }

//    public static ChildManager getInstance(ChildManager Manager) {
//        if (instance == null) {
//            instance = Manager;
//        }
//        return instance;
//    }

    public void add(Child child) {
        childArrayList.add(child);
    }

    public void removeChild(int index) {
        if (index < 0 || index > childArrayList.size()) {
            throw new IndexOutOfBoundsException("Index out of range");
        } else {
            childArrayList.remove(index);
        }
    }

    public int getSize() {
        return childArrayList.size();
    }

    public String[] StringChildData() {
        String[] Str = new String[childArrayList.size()];
        for (int i = 0; i < childArrayList.size(); i++) {
            Str[i] = childArrayList.get(i).getName();
        }
        return Str;
    }

    @Override
    public Iterator<Child> iterator() {
        return childArrayList.iterator();
    }

    public Child getByIndex(int index) {
        return childArrayList.get(index);
    }

    public String getGsonString() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        return jsonString;
    }

    public void load(String jsonString) {
        Gson gson = new Gson();
        ChildManager loaded = gson.fromJson(jsonString, ChildManager.class);
        childArrayList = loaded.childArrayList;
    }
}
