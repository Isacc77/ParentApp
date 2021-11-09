package com.example.cmpt276_2021_7_manganese;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationButtonReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent serviceIntent = TimerService.makeLaunchIntent(N);
        Toast.makeText(context, "Stoping alarm", Toast.LENGTH_SHORT).show();
        context.stopService(intent);
//        context.startService();
//        boolean isClicked = true;
    }
}
