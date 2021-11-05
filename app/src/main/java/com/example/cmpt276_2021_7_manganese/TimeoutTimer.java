//Resource used: https://www.youtube.com/watch?v=MDuGwI6P-X8
//more resource taken from: https://codinginflow.com/tutorials/android/countdowntimer/part-2-configuration-changes
package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Locale;

//QUESTIONS:
//should the input be limited for  how many minutes? - android:maxLength="4"
//should the input only be until minute 60? display timer for an hour or more?
//what about orientation?

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
    private final int MILLI_TO_HOUR_FACTOR = 3600000;
//    private final int MILLI_TO_SEC_FACTOR = 1000;
    private final int SEC_TO_HOUR_FACTOR = 3600;
//    private final int MIN_TO_HOUR_FACTOR = 60;

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
        setupCustomTimerSettings();
    }

    private void setupCustomTimerSettings() {
        EditText inputTime = findViewById(R.id.input_minutes);
        Button setTimeBtn = findViewById(R.id.set_time_btn);

        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customTime = inputTime.getText().toString();
                if (!customTime.isEmpty()) {
                    int time = Integer.parseInt(customTime);
                    stopTimer();
                    if (time != 0) {
                        timerStartTime = (long) time * MIN_TO_MS_FACTOR;
                        timeLeft = timerStartTime;
                    }
                    updateClock();
                    startPauseTimer.setText("START");
                    isTimerRunning = false;
                    inputTime.setText("");
                }
            }
        });
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
        int hour = (int) (timeLeft / MILLI_TO_HOUR_FACTOR);
        int min = (int) (((timeLeft / ONE_SECOND_IN_MILLI) % SEC_TO_HOUR_FACTOR) / MIN_TO_S_FACTOR);
        int sec = (int) ((timeLeft / ONE_SECOND_IN_MILLI) % MIN_TO_S_FACTOR);
        String display = String.format(Locale.getDefault(), "%d:%02d:%02d", hour, min, sec);
        timerClock.setText(display);
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
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