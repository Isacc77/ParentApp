package com.example.cmpt276_2021_7_manganese;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276_2021_7_manganese.model.ChildManager;
import com.example.cmpt276_2021_7_manganese.model.Task;
import com.example.cmpt276_2021_7_manganese.model.TaskManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This Tasks activity is about whose turn section
 *
 * @author
 */
public class TasksActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private ChildManager childManager;
    private TextView tv_notice;
    private TextView emptyListInfo;
    private ListView lv_task_data;
    private Toolbar toolbar;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        tv_notice = findViewById(R.id.tv_hint_for_adding_task);
        lv_task_data = findViewById(R.id.lv_manage_task);
        toolbar = findViewById(R.id.tb_manage_task);
        taskManager = TaskManager.getInstance();
        childManager = ChildManager.getInstance();

        tv_notice.setSelected(true);
        setUpToolBar(toolbar);
        setupFloatingActionButton();
        registerClick();
        populateListView();
        emptyInfo();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateListView();
        emptyInfo();
    }

    private void registerClick() {
        lv_task_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent intent = EditTaskActivity.makeLaunchIntent(TasksActivity.this, position);
                startActivity(intent);
            }
        });
    }
    
    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.lv_task_list,
                taskInfo());
        lv_task_data.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String[] taskInfo() {
//        if (taskManager.getSize() > 0 && childManager.getSize() > 0) {
            String[] Str = new String[taskManager.getSize()];
            for (int i = 0; i < taskManager.getSize(); i++) {
                Task task = taskManager.getTask(i);
                StringBuilder builder = new StringBuilder();
                builder.append(task.getTaskInfo());
                builder.append("    (");
                builder.append(task.getCurChildName());
                builder.append(")");
                Str[i] = builder.toString();
            }
            return Str;
//        }
//        return taskManager.StringTaskData();
    }

    private void emptyInfo() {
        emptyListInfo = findViewById(R.id.tv_emptyList_task);
        if (taskManager.getSize() <= 0) {
            emptyListInfo.setVisibility(View.VISIBLE);
        } else {
            emptyListInfo.setVisibility(View.GONE);
        }
    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, TasksActivity.class);
    }

    private void setUpToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupFloatingActionButton() {
        FloatingActionButton fab = findViewById(R.id.fab_addTask);
        fab.setOnClickListener(view -> {
//            if (childManager.getSize() != 0) {
                Intent addTask = AddTasksActivity.makeLaunchIntent(TasksActivity.this);
                startActivity(addTask);
//            } else {
//                Toast.makeText(this, "Error: No Children added to do task.",
//                        Toast.LENGTH_LONG).show();
//            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateListView();
    }

}