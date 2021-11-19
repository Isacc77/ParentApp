//image from https://www.reddit.com/r/BabyYoda/comments/et2yz7/sweet_baby_yoda_background_i_made/
package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

//        tv_notice.setSelected(true);
        Toolbar toolbar = findViewById(R.id.helpScreenToolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, HelpActivity.class);
    }
}