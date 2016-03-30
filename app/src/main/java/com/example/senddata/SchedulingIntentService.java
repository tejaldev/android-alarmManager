package com.example.senddata;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by tejalpar on 3/23/16.
 */
public class SchedulingIntentService extends IntentService {

    public static final String RESULT = "SchedulingIntentServiceResult";
    public static final String NOTIFICATION = "com.example.senddata";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * Used to name the worker thread, important only for debugging.
     */
    public SchedulingIntentService() {
        super("SchedulingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Send data here
        Log.i("SchedulingIntentService", "Ready to send data.");


        publishResults(Activity.RESULT_OK);
    }

    private void publishResults(int result) {
        Log.i("SchedulingIntentService", "Publishing results.");
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }
}
