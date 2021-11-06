package com.example.cmpt276_2021_7_manganese;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import android.widget.Button;

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
                Intent addChild = AddChild.makeLaunchIntent(MainActivity.this, "New Child");
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


//                case R.id.btn_childManager:
//                    intent = new Intent(MainActivity.this, ChildrenManager.class);
//                    break;

//                case R.id.btn_flipCoin:
//                    intent = new Intent(MainActivity.this, FlipCoin.class);
//                    break;
//
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