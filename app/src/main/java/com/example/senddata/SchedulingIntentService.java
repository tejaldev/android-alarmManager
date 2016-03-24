package com.example.senddata;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by tejalpar on 3/23/16.
 */
public class SchedulingIntentService extends IntentService {
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
        Log.d("SchedulingIntentService", "Ready to send data.");
    }
}
