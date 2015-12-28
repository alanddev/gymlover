package com.alanddev.gymlover.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.alanddev.gymlover.R;
import com.alanddev.gymlover.ui.SplashScreenActivity;
import com.alanddev.gymlover.util.Utils;

import java.util.Calendar;

public class NotifyService extends Service {

    public static final int GREETNG_ID = 1;
    private static final int BUDGET_ID = 2;

    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.setLanguage(this);
        makeNotificationComplete();
        return Service.START_STICKY;
    }

    private void makeNotificationComplete() {
        String contentText="";
        String contentTitle=getResources().getString(R.string.notify_title);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayofyear = calendar.get(Calendar.DAY_OF_YEAR);
        if(hour>=7&&hour<=9){
            if(dayofyear==1){
                contentText = getResources().getString(R.string.notify_startyear);
            }else if(dayofmonth==1){
                contentText = getResources().getString(R.string.notify_startmonth);
            }else if(dayofweek==2){
                contentText = getResources().getString(R.string.notify_startweek);
            }else {
                contentTitle = getResources().getString(R.string.notify_morning);
            }
        }else if(hour>=18&&hour<=20){
            contentText = getResources().getString(R.string.notify_afternoon);
        }else if(hour>=22&hour<=24){
            contentText = getResources().getString(R.string.notify_noon);
        }
        if(!contentText.equals("")) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(contentTitle)
                            .setContentText(contentText);
            Intent notifyIntent =
                    new Intent(this, SplashScreenActivity.class);
            notifyIntent.putExtra("NOTIFICATION",1);
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent notifyPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            notifyIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(notifyPendingIntent);
            // Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(GREETNG_ID, mBuilder.build());
        }

    }

}
