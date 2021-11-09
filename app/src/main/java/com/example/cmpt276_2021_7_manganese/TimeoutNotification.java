package com.example.cmpt276_2021_7_manganese;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class TimeoutNotification extends Application {
    public static final String CHANNEL_1_ID = "TimerDone";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotification();
    }

    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel timerDone = new NotificationChannel(
                    CHANNEL_1_ID, "Timeout Timer Done", NotificationManager.IMPORTANCE_HIGH
            );
            timerDone.setDescription("Timer Done Channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(timerDone);
        }
    }
}
