package com.example.cmpt276_2021_7_manganese;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmpt276_2021_7_manganese.model.Child;
import com.example.cmpt276_2021_7_manganese.model.ChildManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class ManagingMyChildrenData extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Child";


    private static int index = 0; // get child by index in manager

    private ChildManager manager;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private TextView tv_notice;
    private TextView emptyListInfo;
    private ListView lv_child_data;

    private final int REQUEST_CODE_AddCHILD = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managing_my_children_data);


        Toolbar toolbar = findViewById(R.id.tb_manage_child);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        tv_notice = findViewById(R.id.tv_manage_child_info);
        tv_notice.setSelected(true);


        lv_child_data =  findViewById(R.id.lv_manage_child);

        populateListView();
        registerClick();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        loadDataBeforeLaunch();

        emptyInfo();


    }


    private void saveDataBeforeTerminate() {
        Gson gson = new Gson();
        String strObject;
        if (manager.getSize() > 0) {
            strObject = gson.toJson(manager);
        } else {
            strObject = "";
        }
        editor.putString("child_manager", strObject);
        editor.commit();
    }


    private void loadDataBeforeLaunch() {
        // to retrieve
        Gson gson = new Gson();
        String strObject = preferences.getString("child_manager", "");
        if (strObject == null || strObject.equals("") || strObject.length() <= 0) {
            manager = ChildManager.getInstance();
        } else {
            manager = ChildManager.getInstance(gson.fromJson(strObject, ChildManager.class));
        }
    }



    private void registerClick() {


        lv_child_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = AddChild.makeLaunchIntent(ManagingMyChildrenData.this, "edit children", position);
                startActivityForResult(intent, REQUEST_CODE_AddCHILD);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_AddCHILD) { }
        populateListView();
        emptyInfo();
    }




    private void populateListView() {


        manager = ChildManager.getInstance();


        ArrayAdapter<Child> adapter = new ArrayAdapter<Child>(this, R.layout.da_item, manager.getManager());
        lv_child_data.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }






    private void emptyInfo() {
        emptyListInfo = findViewById(R.id.tv_emptyList);

        if (manager.getSize() <= 0) {
            emptyListInfo.setVisibility(View.VISIBLE);

        } else {
            emptyListInfo.setVisibility(View.GONE);
        }
    }


    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, ManagingMyChildrenData.class);
    }


    public static Intent makeLaunchIntent(Context c, String message, int position) {
        index = position;
        Intent intent = new Intent(c, AddChild.class);
        intent.putExtra("Child", message);
        return intent;
    }






    public void onDestroy() {
        saveDataBeforeTerminate();
        super.onDestroy();
    }


}