package com.example.cmpt276_2021_7_manganese;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TimerService extends Service {
    static private final String PREFS_TAG = "Time Settings";
    static private final String SAVE_TIMER_KEY = "Different timer settings";
    static final String INTENT_IS_RUNNING_KEY = "runningTimer";
    static private final String INTENT_TIME_LEFT_KEY = "timeRemaining";
    static private final String INTENT_IS_FINISHED_KEY = "timerDone";
    private final int DEFAULT_TIME_LEFT = 0;
    private final int MIN_TO_MS_FACTOR = 60000;
    private final int MIN_TO_S_FACTOR = 60;
    private final int ONE_SECOND_IN_MILLI = 1000;
    private final int MILLI_TO_HOUR_FACTOR = 3600000;
    private final int SEC_TO_HOUR_FACTOR = 3600;
    private long[] vibrationPattern = {0, 1000, 1000};
    private int indexVibrateRepeat = 1;

    private long timerStartTime;
    private static boolean isTimerRunning = false;
    private static long timeLeft;
    private TextView timerClock;
    private CountDownTimer countDownTimer;
    private Button startPauseTimer;

    private static boolean isTimerDone = true;

    private MediaPlayer player;
    private Vibrator vibrator;

    private NotificationManagerCompat notificationManager;
    public static final String CHANNEL_1_ID = "TimerDone";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timeLeft = intent.getLongExtra(INTENT_TIME_LEFT_KEY, DEFAULT_TIME_LEFT);
//        Toast.makeText(this, "start service", Toast.LENGTH_LONG).show();
        isTimerRunning = true;
        isTimerDone = false;
        countDownTimer = new CountDownTimer(timeLeft, ONE_SECOND_IN_MILLI) {
            @Override
            public void onTick(long mSecondsToFinish) {
                timeLeft = mSecondsToFinish;
            }

            @Override
            public void onFinish() {
                timeLeft = timerStartTime;
                isTimerRunning = false;
                isTimerDone = true;
                alarmBlast();
            }
        }.start();
        isTimerRunning = true;

        return START_STICKY;
    }

    private void alarmBlast() {
        Toast.makeText(this, "start player", Toast.LENGTH_LONG).show();
        player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        player.setLooping(true);
        player.start();
        vibrator.vibrate(VibrationEffect.createOneShot(3000, VibrationEffect.DEFAULT_AMPLITUDE));
//        vibrator.vibrate(vibrationPattern, indexVibrateRepeat);
//        vibrator.vibrate(VibrationEffect.createWaveform(vibrationPattern, indexVibrateRepeat));

//        timeoutNotificationSend(null);
    }

    public void timeoutNotificationSend(View v) {
        notificationManager = NotificationManagerCompat.from(this);

        Intent timerDoneIntent = new Intent(this, TimeoutTimer.class);
        PendingIntent timerPendingIntent = PendingIntent.getActivity(this, 0
                , timerDoneIntent, 0);

        Intent serviceIntent = TimeoutTimer.getServiceIntent(this);
        PendingIntent intentForBroadcastPending = PendingIntent.getBroadcast(this, 0
                , serviceIntent, 0);

//        Intent intentForBroadcast = new Intent(this, TimerService.class);
//        PendingIntent intentForBroadcastPending = PendingIntent.getBroadcast(this, 0
//                , intentForBroadcast, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle("Timeout Timer Done").setContentText("Timer has run out.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(timerPendingIntent)
                .setAutoCancel(true)
                .addAction(R.mipmap.ic_launcher, "Stop", intentForBroadcastPending)
                .build();

        notificationManager.notify(1, notification);

    }

    public static Intent makeLaunchIntent(Context c) {
        return new Intent(c, TimerService.class);
    }

    public static Intent makeStatusIntent(Context c) {
        Intent intent = new Intent(c, TimerService.class);
        intent.putExtra(INTENT_TIME_LEFT_KEY, timeLeft);
        intent.putExtra(INTENT_IS_RUNNING_KEY, isTimerRunning);
        intent.putExtra(INTENT_IS_FINISHED_KEY, isTimerDone);
        return intent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        Toast.makeText(this, "service cancel called", Toast.LENGTH_LONG).show();

        Intent statusIntent = TimeoutTimer.makeResetIntentForService(TimerService.this);
        if (statusIntent.getBooleanExtra("hasBeenReset", true)) {
            isTimerDone = true;
//            Toast.makeText(this, "TIMER SERVICE DONE", Toast.LENGTH_LONG).show();
        }

        isTimerRunning = false;

        if (countDownTimer != null) {
//            Toast.makeText(this, "stop service timer", Toast.LENGTH_LONG).show();
            countDownTimer.cancel();
        }

        if (player != null && player.isPlaying()) {
//            Toast.makeText(this, "stop player", Toast.LENGTH_LONG).show();
            player.stop();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
