package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import com.example.cmpt276_2021_7_manganese.model.Prefs;

public class StartTakeBreathActivity extends AppCompatActivity {
    private Prefs prefs;
    private ImageView breatheImage;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvOperation;
    private TextView guideText;
    private Button startButton;
    private String count;
    private MediaPlayer MediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_take_breath);
        initView();
        initListener();
        initialData();
        prefs = new Prefs(this);
        breatheImage = findViewById(R.id.breathe);
        guideText = findViewById(R.id.guideText);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
        startIntroAnimation();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvOperation = (TextView) findViewById(R.id.tv_operation);
    }

    private void initialData(){
        count = getIntent().getStringExtra("count");
        mTvTitle.setText("Let's take "+ count +" breaths");
    }

    private void initListener() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void startIntroAnimation() {
        ViewAnimator
                .animate(guideText)
                .scale(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideText.setText("Breathe");
                    }
                })
                .start();
    }

    private void startAnimation() {
        ViewAnimator
                .animate(breatheImage)
                .alpha(0, 1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideText.setText("Inhale... Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(breatheImage)
                .scale(0.02f, 1.5f, 0.02f)
                .rotation(360)
                .repeatCount(5)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideText.setText("Good Job!");
                        breatheImage.setScaleX(1.0f);
                        breatheImage.setScaleY(1.0f);
                        prefs.setBreaths(prefs.getBreaths() + 1);

                        new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(), StartTakeBreathActivity.class));
                                finish();
                            }

                        }.start();
                    }

                })
                .start();
    }



}