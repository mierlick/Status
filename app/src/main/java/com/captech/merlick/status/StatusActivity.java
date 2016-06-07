package com.captech.merlick.status;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.captech.merlick.status.db.EventProvider;
import com.captech.merlick.status.service.EventService;

public class StatusActivity extends AppCompatActivity {

    public static final String ACTION_LOG_LENGTH = "com.captech.merlick.status.intent.action.ACTION_LOG_LENGTH";
    public static final String EXTRA_LOG_LENGTH = "logLength";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Toolbar toolbarBottom = (Toolbar) findViewById(R.id.toolbar_bottom);
//        toolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return onMenuItemSelected(item);
//            }
//        });
//        toolbarBottom.inflateMenu(R.menu.main_menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    public boolean onMenuItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, StatusPreferenceActivity.class));
            return true;
        } else if (id == R.id.action_start) {
            startService(new Intent(getBaseContext(), EventService.class));
        } else if (id == R.id.action_stop) {
            stopService(new Intent(getBaseContext(), EventService.class));
        } else if (id == R.id.action_clear_log) {
            getContentResolver().delete(EventProvider.URL, null, null);
        } else if (id == R.id.action_send_broadcast) {
            ListView listView = (ListView) findViewById(android.R.id.list);

            int currentLogLength = listView.getAdapter().getCount()-1;

            Intent intent = new Intent();
            intent.setAction(ACTION_LOG_LENGTH);
            intent.putExtra(EXTRA_LOG_LENGTH, currentLogLength);

            sendBroadcast(intent);
        }

        return true;
    }
}
