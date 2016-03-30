package com.example.senddata;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by tejalpar on 3/23/16.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {
    private static final long INTERVAL = 5*1000;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        //onReceive is called when the Alarm set by app is triggered

        //1. create intent with IntentService class
        Intent schedulingServiceIntent = new Intent(context, SchedulingIntentService.class);


        //2. start the service keeping the device awake when service is started. This service will perform scheduled task.
        startWakefulService(context, schedulingServiceIntent);

    }

    /**
     * Sets Alarm.
     *
     * @param context Context of application/activity that needs to set Alarm.
     */
    public void setAlarm(Context context) {

        //1. Decide alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 40);
        //  set time in millis to help setInexactRepeating() method decide the absolute time to wakeup
        calendar.setTimeInMillis(System.currentTimeMillis());


        //2. set Pending Intent which is triggered when alarm fires
        Intent intent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //3. set Repeat time. Always prefer using setInexactRepeating() method instead of setRepeating()
        //   when setInexactRepeating()::  Android synchronizes repeating alarms from multiple apps and fires them at the same time. So multiple WakeUps
        //   are avoided
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL, pendingIntent);
        Log.i("AlarmReceiver", "Alarm set complete.");
    }


    /**
     * Cancels Alarm.
     *
     * @param context Context of application/activity that needs to cancel the scheduled Alarm.
     */
    public void cancelAlarm(Context context) {

        //1. Cancel pendingIntent of Alarm manager if its already set
        if(alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.i("AlarmReceiver", "Alarm cancelled.");
        }
    }
}
