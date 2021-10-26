package com.example.cmpt276_2021_7_manganese.model;

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


}
