package com.example.cmpt276_2021_7_manganese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddTasksActivity extends AppCompatActivity {
    private EditText inputTask;
    private boolean isSaved = false;
    private String taskInfo;
    private final String SAVED = "Saved";
    private final String INVALID_INPUT = "Cannot save with invalid inputs!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        Toolbar toolbar = findViewById(R.id.add_task_toolbar);
        inputTask = findViewById(R.id.et_add_task);
        inputTask.addTextChangedListener(tw);

        setUpToolBar(toolbar);
    }

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String strName = inputTask.getText().toString().trim();
            if (strName.length() <= 0) {
                isSaved = false;
                return;
            }
            isSaved = true;
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void setUpToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_backup:
                if (isSaved) {
                    Toast.makeText(this, SAVED, Toast.LENGTH_SHORT).show();
                    taskInfo = inputTask.getText().toString();
                    finish();
                } else {
                    Toast.makeText(this, INVALID_INPUT, Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.backup_on_action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static Intent makeLaunchIntent(Context c, String message) {
        Intent intent = new Intent(c, AddTasksActivity.class);
        return intent;
    }

}