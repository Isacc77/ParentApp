package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class WhoseTurnActivity extends AppCompatActivity {

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, WhoseTurnActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whose_turn);
    }











}