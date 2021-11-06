package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ManagingMyChildrenData extends AppCompatActivity {


    private TextView tv_notice;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managing_my_children_data);




        tv_notice = findViewById(R.id.tv_manage_child_info);
        tv_notice.setSelected(true);





    }

















    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, ManagingMyChildrenData.class);
    }





}