package com.captech.merlick.status.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.captech.merlick.status.R;
import com.captech.merlick.status.StatusActivity;
import com.captech.merlick.status.db.EventProvider;
import com.captech.merlick.status.entities.Event;
import com.captech.merlick.status.entities.EventType;

import java.util.Date;

/**
 * Created by merlick on 5/31/16.
 */
public class EventService extends Service {

    private static final String TAG = "EventService";

    private static final int NOTIFICATION_ID = 3;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(StatusActivity.ACTION_LOG_LENGTH);

        this.registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        Intent activityIntent = new Intent(this, StatusActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_stat_cloud));
        builder.setSmallIcon(R.drawable.ic_stat_cloud);
        builder.setContentTitle("Monitor Service");
        builder.setContentText("Logging System Events");
        builder.setOngoing(true);
        builder.setAutoCancel(false);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mBroadcastReceiver);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }



    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent myIntent) {

            Log.v(TAG, String.format("Intent: %s", myIntent.getAction()));

            if (myIntent.getAction().equals(Intent.ACTION_CONFIGURATION_CHANGED)) {
                addEventItem(EventType.ConfigurationChange, myIntent);
            } else if (myIntent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                addEventItem(EventType.ScreenOff, myIntent);
            } else if (myIntent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                addEventItem(EventType.ScreenOn, myIntent);
            } else if (myIntent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                addEventItem(EventType.PowerConnected, myIntent);
            } else if (myIntent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
                addEventItem(EventType.PowerDisconnected, myIntent);
            } else if (myIntent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                addEventItem(EventType.TimeTicks, myIntent);
            } else if (myIntent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                addEventItem(EventType.BatteryChange, myIntent);
            } else if (myIntent.getAction().equals(StatusActivity.ACTION_LOG_LENGTH)) {
                addEventItem(EventType.LogLength, myIntent);
            }
        }

        private void addEventItem(EventType eventType, Intent intent) {
            ContentValues values = new ContentValues();
            values.put(Event.COLUMN_EVENT_TITILE, eventType.getEventName());
            values.put(Event.COLUMN_EVENT_TYPE, eventType.toString());
            values.put(Event.COLUMN_EVENT_TIME, new Date().toString());

            if (eventType == EventType.BatteryChange) {
                int batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryTemp = getBatteryTempAsF(intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10);
                float batteryVolts = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) / 100;
                float batteryPercent = batteryLevel / (float)batteryScale * 100;

                String charging = batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING ? "Charging" : "Discharging";

                String pluggedIn = "No Power Source";
                if (plugged == BatteryManager.BATTERY_PLUGGED_AC) {
                    pluggedIn = "AC Power Source";
                } else if (plugged == BatteryManager.BATTERY_PLUGGED_USB) {
                    pluggedIn = "USB Power Source";
                }

                values.put(Event.COLUMN_EVENT_BATTERY_PERCENT, batteryPercent);
                values.put(Event.COLUMN_EVENT_BATTERY_TEMP, batteryTemp);
                values.put(Event.COLUMN_EVENT_BATTERY_VOLTS, batteryVolts);
                values.put(Event.COLUMN_EVENT_BATTERY_CHARGE, charging);
                values.put(Event.COLUMN_EVENT_BATTERY_PLUG, pluggedIn);

            } else if (eventType == EventType.LogLength) {
                int logLength = intent.getIntExtra(StatusActivity.EXTRA_LOG_LENGTH, -1);

                values.put(Event.COLUMN_LOG_LENGTH, logLength);
            }

            getContentResolver().insert(EventProvider.URL, values);
        }

        private float getBatteryTempAsF(float batteryTemp) {
            return (batteryTemp * 9) / 5 + 32;
        }
    };
}
