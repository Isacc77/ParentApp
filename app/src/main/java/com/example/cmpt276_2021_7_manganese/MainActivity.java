package com.example.cmpt276_2021_7_manganese;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cmpt276_2021_7_manganese.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
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
        this.setTitle("Main Menu");



        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
                Intent addChild = AddChild.makeLaunchIntent(MainActivity.this, "New Child");

                startActivity(addChild);
        });

        btnChildManager = findViewById(R.id.btn_childManager);
        btnFlipCoin = findViewById(R.id.btn_flipCoin);
        btnTimer = findViewById(R.id.btn_timer);
        setListeners();

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
//                case R.id.btn_childManager:
//                    intent = new Intent(MainActivity.this, ChildrenManager.class);
//                    break;
//
//                case R.id.btn_flipCoin:
//                    intent = new Intent(MainActivity.this, FlipCoin.class);
//                    break;
//
//                case R.id.btn_timer:
//                    intent = new Intent(MainActivity.this, Timer.class);
//                    break;

            }
            startActivity(intent);
        }

    }

















}