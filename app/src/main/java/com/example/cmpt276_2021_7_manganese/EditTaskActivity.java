//image used: https://wallpaperaccess.com/baby-yoda-phone
package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cmpt276_2021_7_manganese.model.TaskManager;

/**
 * This activity is for editing the tasks already made. This screen will let you edit,
 * delete and go to next child for the task. The tasks are saved between app runs.
 * There is also a cancel button to cancel and delete button to delete the task.
 */
public class EditTaskActivity extends AppCompatActivity {
    private TaskManager taskManager;
    private static final String EXTRA_INTENT_MESSAGE = "Task index";
    private int taskListIndex;
    private final int defaultIndex = -1;
    private EditText taskName;
    private Button saveBtn;
    private TextView nameTitle;
    private TextView childName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskManager = TaskManager.getInstance();
        taskListIndex = getIntent().getIntExtra(EXTRA_INTENT_MESSAGE, defaultIndex);
        setupToolBar();
        setupButtons();
        setupTaskNamePanel();
        setupChildNamePanel();
    }

    private void setupChildNamePanel() {
        nameTitle = findViewById(R.id.name_title);
        childName = findViewById(R.id.child_name_field);
        childName.setText(taskManager.getTask(taskListIndex).getCurChildName());
    }

    private void setupTaskNamePanel() {
        taskName = findViewById(R.id.task_name_field);
        taskName.setText(taskManager.getTask(taskListIndex).getTaskInfo());
        saveBtn = findViewById(R.id.save_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = taskName.getText().toString();
                taskManager.getTask(taskListIndex).setTaskInfo(newName);
                finish();
            }
        });

    }

    private void setupButtons() {
        setupCancelBtn();
        setupTaskDoneBtn();
        setupDeleteTaskBtn();
    }

    private void setupCancelBtn() {
        Button cancelBtn = findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupTaskDoneBtn() {
        Button cancelBtn = findViewById(R.id.done_task_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNextChild();
                finish();
            }
        });
    }

    private void updateNextChild() {
        taskManager.getTask(taskListIndex).childDoneTask();
    }

    private void setupDeleteTaskBtn() {
        Button cancelBtn = findViewById(R.id.delete_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskManager.removeTask(taskListIndex);
                finish();
            }
        });
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.edit_task_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public static Intent makeLaunchIntent(Context c, int taskIndex) {
        Intent intent = new Intent(c, EditTaskActivity.class);
        intent.putExtra(EXTRA_INTENT_MESSAGE, taskIndex);
        return intent;
    }

    private void jsonSave() {
        String jsonStringForTask = taskManager.getGsonStringForTask();
        SharedPreferences prefs = this.getSharedPreferences("tag_task", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("save_task_info", jsonStringForTask);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jsonSave();
    }
}