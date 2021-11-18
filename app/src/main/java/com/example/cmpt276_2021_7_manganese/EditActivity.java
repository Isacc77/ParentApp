package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    public static Intent makeLaunchIntent(Context c) {
        Intent intent = new Intent(c, EditActivity.class);
        return intent;
    }

}