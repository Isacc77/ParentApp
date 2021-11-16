package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmpt276_2021_7_manganese.model.TaskManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This Tasks activity is about whose turn section
 * @author
 */

public class TasksActivity extends AppCompatActivity {
    private TaskManager t_manager;
    private TextView tv_notice;
    private TextView emptyListInfo;
    private ListView lv_task_data;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        tv_notice = findViewById(R.id.tv_hint_for_adding_task);
        lv_task_data = findViewById(R.id.lv_manage_task);
        toolbar = findViewById(R.id.tb_manage_task);

        tv_notice.setSelected(true);
        setUpToolBar(toolbar);
        setupFloatingActionButton();
    }

    private void setUpToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab_addTask);
        fab.setOnClickListener(view -> {
            Intent addTask = AddTasksActivity.makeLaunchIntent(TasksActivity.this);
            startActivity(addTask);
        });
    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, TasksActivity.class);
    }

}