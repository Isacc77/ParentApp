package com.example.cmpt276_2021_7_manganese;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.gyf.immersionbar.ImmersionBar;

public class StartTakeBreathActivity extends AppCompatActivity {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvOperation;
    private String childName;
    private String count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
        setContentView(R.layout.activity_start_take_breath);
        initView();
        initData();
        initListener();

    }

    private void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        childName = getIntent().getStringExtra("childName");
        count = getIntent().getStringExtra("count");
        mTvTitle.setText("Let's choose"+count+"breath(s).");
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvOperation = (TextView) findViewById(R.id.tv_operation);
    }
}