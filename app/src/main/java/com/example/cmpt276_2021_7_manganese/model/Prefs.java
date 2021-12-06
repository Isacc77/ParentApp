package com.example.cmpt276_2021_7_manganese.model;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Calendar;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setBreaths(int breaths) {
        preferences.edit().putInt("breaths", breaths).apply(); //saving breaths to system
    }

    public int getBreaths() {
        return preferences.getInt("breaths", 0);
    }
}

