package com.example.cmpt276_2021_7_manganese;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276_2021_7_manganese.R;
import com.gyf.immersionbar.ImmersionBar;

public class StartTakeBreathActivity extends AppCompatActivity {

    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvOperation;
    private String count;
    private boolean isIn = false;
    private boolean isDown = false;
    private TextView mTvTitleBreath;
    private TextView mTvTip;
    private int time = 0;
    private int countNow;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                runCount();
                if (time > 10) {
                    time = 10;
                    mTvOperation.setText("OUT");
                    player.stop();
                    handler.removeMessages(200);
                }
                if (time>3){
                    mTvOperation.setText("OUT");
                    mTvTip.setText("Now release button and exhale");
                }

            } else if (msg.what == 300) {

                if (time > 0) {
                    runReduce();
                    if (time < 3) {
                        mTvOperation.setText("IN");
                    }
                } else {
                    handler.removeMessages(300);
                    time = 0;
                    countNow++;
                    player.stop();
                    if (countNow == Integer.parseInt(count)) {
                        mTvOperation.setText("GOOD Job");
                    }
                }

            } else {
                handler.removeMessages(200);
                if (time < 3) {
                    mTvOperation.setText("IN");
                    time = 0;
                    player.stop();
                    mIvIcIamge.clearAnimation();

                } else if (3 <= time && time <= 10) {
                    player.start();
                    mTvOperation.setText("OUT");
                    beginScale(R.anim.zoom_out, 3000);
                    handler.sendEmptyMessage(300);

                } else {
                    time = 10;
                    player.stop();
                    mTvOperation.setText("OUT");
                    player.start();
                    beginScale(R.anim.zoom_out, 3000);
                    handler.sendEmptyMessage(300);
                }

            }


        }

        ;
    };
    private ImageView mIvIcIamge;
    private MediaPlayer player;
    private Animation an;


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
        mTvOperation.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }

        });

        mTvOperation.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (v.getId() == R.id.tv_operation) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        runCount();
                        mTvOperation.setText("IN");
                        isDown = true;

                        player.start();
                        mIvIcIamge.clearAnimation();

                        beginScale(R.anim.zoom_in, 5000);

                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        isDown = false;
                        handler.sendEmptyMessage(100);
                    }
                }
                return false;
            }
        });
    }


    private synchronized void beginScale(int animation, long time) {
        an = AnimationUtils.loadAnimation(StartTakeBreathActivity.this, animation);
        an.setDuration(time);
        an.setFillAfter(true);
        mIvIcIamge.startAnimation(an);
    }

    public void runReduce() {

        Message obtain = Message.obtain();

        time = time - 1;
        obtain.what = 300;
        handler.sendMessageDelayed(obtain, 1000);
    }

    public void runCount() {

        Message obtain = Message.obtain();

        time = time + 1;
        obtain.what = 200;
        handler.sendMessageDelayed(obtain, 1000);
    }

    private void initData() {
        count = getIntent().getStringExtra("count");
        mTvTitle.setText("StartBreath");
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.breath_sound);
        }
        mTvTitleBreath.setText("Let's  take " + count + " breaths together！");
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvOperation = (TextView) findViewById(R.id.tv_operation);
        mTvTitleBreath = (TextView) findViewById(R.id.tv_title_breath);
        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mIvIcIamge = (ImageView) findViewById(R.id.iv_ic_iamge);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != player) {
            player.stop();
        }
    }
}