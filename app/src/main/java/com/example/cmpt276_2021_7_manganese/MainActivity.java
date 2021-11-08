package com.example.cmpt276_2021_7_manganese;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnFlipCoin;
    private Button btnTimer;
    private Button btnChildManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFloatingActionButton();
        btnChildManager = findViewById(R.id.btn_childManager);
        btnFlipCoin = findViewById(R.id.btn_flipCoin);
        btnTimer = findViewById(R.id.btn_timer);
        setListeners();

    }


    private void setupFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
                Intent addChild = AddChild.makeLaunchIntent(MainActivity.this, "New Child or update child info");
                startActivity(addChild);
        });
    }


    private void setListeners() {
        OnClick onClick = new OnClick();
        btnChildManager.setOnClickListener(onClick);
        btnFlipCoin.setOnClickListener(onClick);
        btnTimer.setOnClickListener(onClick);
    }


    private class OnClick implements View.OnClickListener {

        Intent intent = null;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                /**
                 * need to create childrenManager class, FlipCoin class, Timer class
                 * this will direct to those class from MainActivity
                 */
                case R.id.btn_childManager:
                    intent = new Intent(MainActivity.this, ManagingMyChildrenData.class);
                    break;

                case R.id.btn_flipCoin:
                    intent = new Intent(MainActivity.this, FlipCoinActivity.class);
                    break;

                case R.id.btn_timer:
                    intent = TimeoutTimer.makeLaunchIntent(MainActivity.this);
                    break;
            }
            startActivity(intent);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }






}