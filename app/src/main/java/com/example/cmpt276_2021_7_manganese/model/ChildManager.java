/**
 * This class is for ChildManager
 *
 *
 */


package com.example.cmpt276_2021_7_manganese.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;




public class ChildManager implements Iterable<Child> {

    private ArrayList<Child> manager = new ArrayList<>();

    private static ChildManager instance;


    public ChildManager() {
    }


    public static ChildManager getInstance() {

        if (instance == null) {

            instance = new ChildManager();

        }

        return instance;
    }


    public  static  ChildManager getInstance(ChildManager Manager) {
        if(instance == null) {
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


    @Override
    public Iterator<Child> iterator() {
        return manager.iterator();
    }


    public void printAll() {
        int cnt = 0;
        for(Child c : manager) {
            System.out.println(cnt++ + ": " + c);
        }
    }



    public Child getByIndex(int index) {

        if(index > manager.size() || index < 0) {
            System.out.println("PROBLEM: in getByIndex, i should be [0, " + manager.size() + ").");
            return new Child("");
        }

        return manager.get(index);
    }





}
