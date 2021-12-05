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
import com.example.cmpt276_2021_7_manganese.model.ChildManager;
import com.example.cmpt276_2021_7_manganese.model.Task;
import com.example.cmpt276_2021_7_manganese.model.TaskHistory;
import com.example.cmpt276_2021_7_manganese.model.TaskManager;

import java.time.LocalDateTime;

public class TaskHistoryActivity extends AppCompatActivity {
    TaskManager taskManager = TaskManager.getInstance();
    private static final String EXTRA_INTENT_MESSAGE = "Task Index";
    private int taskListIndex;
    private int defaultIndex = -1;
    private Task curTask;
    private ChildManager childManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_history);

        setupToolBar();

        childManager = ChildManager.getInstance();
        taskListIndex = getIntent().getIntExtra(EXTRA_INTENT_MESSAGE, defaultIndex);
        curTask = taskManager.getTask(taskListIndex);
        Toast.makeText(this, "" + taskListIndex, Toast.LENGTH_SHORT).show();

        populateListView();
    }


    /**
     * Note, if you want to get the name, photoURL and date of each child, do this
     * TaskHistory curHistory = curTask.getHistoryInfo(i); the i is index of history
     * curHistory.getName, curHistory.getPhotoURL, curHistory.getDate
     * note* the get date gives you a Local Date Time, there are .getMonth methods like that
     * to get the information from it.
     */

//    private void populateListView() {
//        //make the string array to put into the list view adapter
//        String[] myHistory = new String[curTask.historySize()];
//        for (int i = 0; i < curTask.historySize(); i++) {
//            Child curChild = curTask.getHistoryInfo(i).getChild();
//            TaskHistory curHistory = curTask.getHistoryInfo(i);
//            Child ogChild = childManager.findById(curHistory.getId());
//            //IMPORTANT!! update if child deleted or changed name and photo
//            if (ogChild != null) {
//                if (ogChild.getName() != curChild.getName()) {
//                    curHistory.setName(ogChild.getName());
//                    curHistory.setChild(ogChild);
//                }
//                if (ogChild.getPhotoUrl() != curChild.getPhotoUrl()) {
//                    curHistory.setUrl(ogChild.getPhotoUrl());
//                    curHistory.setChild(ogChild);
//                }
//            } else {
//                curHistory.setName("Deleted");
//            }
//            String name = curTask.getHistoryInfo(i).getName();
//            LocalDateTime date =  curHistory.getDate();
//            String fullInfo = name + "              (" + date.getMonthValue() + "/" +
//                    date.getDayOfMonth() + "/" + date.getYear() + ")";
//            myHistory[i] = fullInfo;
//        }
//        //put the string of names into the listview
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this,
//                R.layout.items_for_listview,
//                myHistory);
//        ListView list = (ListView) findViewById(R.id.history_list_view);
//        list.setAdapter(adapter);
//    }

    private void populateListView() {
//        TaskHistory curHistory = curTask.getHistoryInfo(i);
        testTaskHistoryAdapter adapter = new testTaskHistoryAdapter(curTask.getManager(), this);
        ListView list = (ListView) findViewById(R.id.history_list_view);
        list.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
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

//        ab.
    }
}