package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cmpt276_2021_7_manganese.model.Child;
import com.example.cmpt276_2021_7_manganese.model.Task;
import com.example.cmpt276_2021_7_manganese.model.TaskManager;

public class TaskHistoryActivity extends AppCompatActivity {
    TaskManager taskManager = TaskManager.getInstance();
    private static final String EXTRA_INTENT_MESSAGE = "Task Index";
    private int taskListIndex;
    private int defaultIndex = -1;
    private Task curTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_history);

        setupToolBar();

        taskListIndex = getIntent().getIntExtra(EXTRA_INTENT_MESSAGE, defaultIndex);
        curTask = taskManager.getTask(taskListIndex);
        curTask.updateHistoryInfo();
        Toast.makeText(this, "" + taskListIndex, Toast.LENGTH_SHORT).show();

        populateListView();
    }

    private void populateListView() {
        String[] myHistory = new String[curTask.historySize()];
        for (int i = 0; i < curTask.historySize(); i++) {
//            if (curTask.getHistoryChild(i) == )
            myHistory[i] = curTask.getHistoryChild(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.items_for_listview,
                myHistory);
        ListView list = (ListView) findViewById(R.id.history_list_view);
        list.setAdapter(adapter);
    }

    public static Intent makeLaunchIntent(Context c, int taskIndex) {
        Intent intent = new Intent(c, TaskHistoryActivity.class);
        intent.putExtra(EXTRA_INTENT_MESSAGE, taskIndex);
        return intent;
    }

    private void setupToolBar() {
        Toolbar toolbar = findViewById(R.id.task_history_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}