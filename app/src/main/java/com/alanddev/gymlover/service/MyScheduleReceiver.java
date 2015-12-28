package com.alanddev.gymlover.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class MyScheduleReceiver extends BroadcastReceiver {
    public MyScheduleReceiver() {
    }

    // restart service every 30 seconds
    private static final long REPEAT_TIME = 1000 * 60 * 60 * 2;
    //private static final long REPEAT_TIME = 1000 * 10;

    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager service = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, MyStartServiceReceiver.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar cal = Calendar.getInstance();
        // start 30 seconds after boot completed
        cal.add(Calendar.HOUR, 2);
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                cal.getTimeInMillis(), REPEAT_TIME, pending);
    }
}
