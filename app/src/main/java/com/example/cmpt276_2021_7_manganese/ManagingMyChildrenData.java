package com.example.cmpt276_2021_7_manganese;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
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

import com.example.cmpt276_2021_7_manganese.model.Child;
import com.example.cmpt276_2021_7_manganese.model.ChildManager;
import com.google.gson.Gson;

public class ManagingMyChildrenData extends AppCompatActivity {


    private ChildManager manager;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private TextView tv_notice;
    private TextView emptyListInfo;
    private ListView lv_child_data;

    private final int GO_TO_CHILD_INFO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managing_my_children_data);


        Toolbar toolbar = findViewById(R.id.tb_manage_child);
        setSupportActionBar(toolbar);
        this.setTitle("Manage your children");


        tv_notice = findViewById(R.id.tv_manage_child_info);
        tv_notice.setSelected(true);


        lv_child_data =  findViewById(R.id.lv_manage_child);

        populateListView();
        registerClick();
        // check and load the data to manager
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        loadDataBeforeLaunch();

        // check if manager is empty, and if yes, show info
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

                startActivityForResult(intent, GO_TO_CHILD_INFO);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GO_TO_CHILD_INFO) { }
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

    public void onDestroy() {
        saveDataBeforeTerminate();
        super.onDestroy();
    }


}