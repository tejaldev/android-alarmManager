package com.example.senddata;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private TextView updatesTextView;

    private AlarmReceiver alarmReceiver;
    private BroadcastReceiver scheduleServiceResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updatesTextView = (TextView) findViewById(R.id.updatesTextView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmReceiver.setAlarm(context);
                Snackbar.make(view, "Alarm set", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                updatesTextView.setText("Alarm Set");
            }
        });

        alarmReceiver = new AlarmReceiver();
        Button cancelAlarmButton = (Button) findViewById(R.id.cancelAlarmButton);
        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmReceiver.cancelAlarm(context);
                Snackbar.make(v, "Alarm cancelled", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                updatesTextView.setText("Alarm Cancelled");
            }
        });

        setScheduleServiceResultReceiver();

        /// How to schedule task with Alarm Manager
        //1. Set Alarm

        //2. When Alarm triggers AlarmReceiver() -> onReceive() method is called

        //3. onReceive() starts IntentService to perform scheduled task.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(scheduleServiceResultReceiver != null) {
            registerReceiver(scheduleServiceResultReceiver, new IntentFilter(SchedulingIntentService.NOTIFICATION));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(scheduleServiceResultReceiver != null) {
            unregisterReceiver(scheduleServiceResultReceiver);
        }
    }

    private void setScheduleServiceResultReceiver() {
        scheduleServiceResultReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                int resultCode =  bundle.getInt(SchedulingIntentService.RESULT);
                if(resultCode == Activity.RESULT_OK) {
                    Toast.makeText(MainActivity.this,
                            "Data send complete.",
                            Toast.LENGTH_LONG).show();
                    updatesTextView.setText("Data send complete.");
                }
            }
        };
    }
}
