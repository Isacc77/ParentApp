/**
 * This class is for child
 *
 */
package com.example.cmpt276_2021_7_manganese.model;

/**
 * This class is for Child data
 * Child's name
 * @author  Shuai Li
 */

public class Child {

    String name;

    public Child(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Name: " + name ;
    }



}
