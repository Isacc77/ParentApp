package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * This TimeoutTimer activity represents the screen containing timeout features.
 * Features such as..
 * @author Rio Samson
 */
public class TimeoutTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_timer);
    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, TimeoutTimer.class);
    }
}