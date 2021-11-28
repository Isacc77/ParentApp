package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cmpt276_2021_7_manganese.TasksActivity;

public class TakeBreathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_breath);
    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, TakeBreathActivity.class);
    }

}