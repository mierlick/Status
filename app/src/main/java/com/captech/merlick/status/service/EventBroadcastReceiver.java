package com.captech.merlick.status.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.captech.merlick.status.R;

/**
 * Created by merlick on 5/31/16.
 */
public class EventBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("STARTUP", "Starting up");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        Boolean startAtBoot = preferences.getBoolean(context.getString(R.string.key_pref_start_monitor_at_boot), false);

        if (startAtBoot) {
            Intent startServiceIntent = new Intent(context, EventService.class);
            context.startService(startServiceIntent);
        }

    }

}
