//Resource used: https://www.youtube.com/watch?v=MDuGwI6P-X8
package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

/**
 * This TimeoutTimer activity represents the screen containing timeout features.
 * Features such as..
 * @author Rio Samson
 */
public class TimeoutTimer extends AppCompatActivity {
    static private final String PREFS_TAG = "Time Settings";
    static private final String SAVE_TIMER_KEY = "Different timer settings";
    private final int MIN_TO_MS_FACTOR = 60000;
    private final int MIN_TO_S_FACTOR = 60;
    private final int ONE_SECOND_IN_MILLI = 1000;
    private long timerStartTime;
    private boolean isTimerRunning;
    private long timeLeft;
    private TextView timerClock;
    private CountDownTimer countDownTimer;
    private Button startPauseTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_timer);

        timerClock = findViewById(R.id.timerClock);
        startPauseTimer = findViewById(R.id.startBtn);
        timerStartTime = (long) loadSavedData() * MIN_TO_MS_FACTOR;
        timeLeft = timerStartTime;

        setupPreMadeTimerSettings();
        setupTimerClockWithButtons();
    }

    private void setupPreMadeTimerSettings() {
        RadioGroup radioGroup = findViewById(R.id.timeRadioGroup);
        int[] timerSettings = getResources().getIntArray(R.array.times);

        for(int i = 0; i < timerSettings.length; i++) {
            final int setting = timerSettings[i];
            RadioButton button = new RadioButton(this);
            button.setText("" + setting);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { //TODO what if clicked while running?
                    timerStartTime = (long) setting * MIN_TO_MS_FACTOR;
                    timeLeft = timerStartTime;
                    updateClock();
                    saveTimeSettings(setting);
                }
            });
            radioGroup.addView(button);
            int savedSetting = loadSavedData();
            if (setting == savedSetting) {
                button.setChecked(true);
            }
        }
    }

    private void setupTimerClockWithButtons() {
        Button resetTimer = findViewById(R.id.resetBtn);
        updateClock();

        startPauseTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimerRunning) {
                    countDownTimer.cancel();
                    startPauseTimer.setText("RESUME");
                    isTimerRunning = false;
                } else {
                    startTimer();
                    startPauseTimer.setText("PAUSE");
                    isTimerRunning = true;
                }
            }
        });

        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                timeLeft = timerStartTime;
                updateClock();
                startPauseTimer.setText("START");
                isTimerRunning = false;
            }
        });

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, ONE_SECOND_IN_MILLI) {
            @Override
            public void onTick(long mSecondsToFinish) {
                timeLeft = mSecondsToFinish;
                updateClock();
            }

            @Override
            public void onFinish() {
                timeLeft = timerStartTime;
                updateClock();
                startPauseTimer.setText("START");
                isTimerRunning = false;
            }
        }.start();
        isTimerRunning = true;
    }

    private void updateClock() {
        int min = (int) (timeLeft / MIN_TO_MS_FACTOR);
        int sec = (int) ((timeLeft / ONE_SECOND_IN_MILLI) % MIN_TO_S_FACTOR);
        String display = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        timerClock.setText(display);
    }

    private int loadSavedData() {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_TAG,
                MODE_PRIVATE);
        return prefs.getInt(SAVE_TIMER_KEY, getResources().getInteger(R.integer.default_timer));
    }

    private void saveTimeSettings(int timerSettings) {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_TAG, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SAVE_TIMER_KEY, timerSettings);
        editor.apply();
    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, TimeoutTimer.class);
    }
}