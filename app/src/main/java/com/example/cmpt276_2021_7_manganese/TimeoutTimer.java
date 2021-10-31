
package com.example.cmpt276_2021_7_manganese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * This TimeoutTimer activity represents the screen containing timeout features.
 * Features such as..
 * @author Rio Samson
 */
public class TimeoutTimer extends AppCompatActivity {
    static private final String PREFS_TAG = "Time Settings";
    static private final String SAVE_TIMER_KEY = "Different timer settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout_timer);
        setupTimerSettings();
    }

    private int loadSavedData() {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_TAG,
                MODE_PRIVATE);
        return prefs.getInt(SAVE_TIMER_KEY, getResources().getInteger(R.integer.default_timer));
    }

    private void setupTimerSettings() {
        RadioGroup radioGroup = findViewById(R.id.timeRadioGroup);
        int[] timerSettings = getResources().getIntArray(R.array.times);

        for(int i = 0; i < timerSettings.length; i++) {
            final int setting = timerSettings[i];
            RadioButton button = new RadioButton(this);
            button.setText("" + setting);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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