package com.example.cmpt276_2021_7_manganese;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmpt276_2021_7_manganese.model.ChildManager;
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

        populateListView();
        emptyInfo();
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_AddCHILD) {
//            populateListView();
//            emptyInfo();
//        }
//    }

    private void populateListView() {
        t_manager = TaskManager.getInstance();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.da_item,
                t_manager.StringTaskData());

        lv_task_data.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void emptyInfo() {
        emptyListInfo = findViewById(R.id.tv_emptyList_task);
        if (t_manager.getSize() <= 0) {
            emptyListInfo.setVisibility(View.VISIBLE);
        } else {
            emptyListInfo.setVisibility(View.GONE);
        }
    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, TasksActivity.class);
    }

}