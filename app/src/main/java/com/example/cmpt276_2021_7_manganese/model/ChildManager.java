/**
 * This class is for ChildManager
 */
package com.example.cmpt276_2021_7_manganese.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

/**
 * This class is for ChildManager
 * it use singleton model
 * we can populate data Children data by this class
 * @author  Shuai Li
 * @author Yam for Iteration 2
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

    public ArrayList<Child> getManager() {
        return childArrayList;
    }

    public void setManager(ArrayList<Child> manager) {
        this.childArrayList = manager;
    }

    public String[] StringChildData() {
        String[] Str = new String[childArrayList.size()];
        for (int i = 0; i < childArrayList.size(); i++) {
            Str[i] = childArrayList.get(i).getName();
        }
        return Str;
    }
/*
    public Child[] ChildData() {
        Child[] child = new Child[manager.size()];
        for (int i = 0; i < manager.size(); i++) {
            child[i].name = manager.get(i).getName();
            child[i].PhotoUrl = manager.get(i).getPhotoUrl();
        }
        return child;
    }

 */
    public List<Child> ChildData(){
        List<Child> childData = new ArrayList<>();
        if(manager.size() == 0)
            return null;
        else {
            for (int i = 0; i < manager.size(); i++) {
                childData.add(i, new Child(manager.get(i).name, manager.get(i).PhotoUrl));
            }
        }
        return childData;
    }


    @Override
    public Iterator<Child> iterator() {
        return childArrayList.iterator();
    }

    public void printAll() {
        int cnt = 0;
        for (Child c : childArrayList) {
            System.out.println(cnt++ + ": " + c);
        }
    }

    public Child getByIndex(int index) {
        return childArrayList.get(index);
    }

    public String getGsonString(){
        Gson gson = new Gson();
        String jsonString = gson.toJson(this);
        return jsonString;
    }

    public void load(String jsonString){
        Gson gson = new Gson();
        ChildManager loaded = gson.fromJson(jsonString, ChildManager.class);
        childArrayList = loaded.childArrayList;
    }
}
