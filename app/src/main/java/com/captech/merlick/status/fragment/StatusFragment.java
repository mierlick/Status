package com.captech.merlick.status.fragment;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.captech.merlick.status.EventAdapter;
import com.captech.merlick.status.R;
import com.captech.merlick.status.db.EventProvider;
import com.captech.merlick.status.entities.Event;
import com.captech.merlick.status.entities.EventType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by merlick on 5/24/16.
 */
public class StatusFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int EVENTS_LOADER = 1;

    private static final int FAST_SCROLL_COUNT = 50;

    private EventAdapter eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        eventAdapter = new EventAdapter(getContext(), null, 0);

        View view = inflater.inflate(R.layout.status_fragment, container, false);

        getLoaderManager().initLoader(EVENTS_LOADER, null, this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View footerView = View.inflate(getContext(), R.layout.footer, null);

        getListView().addFooterView(footerView);
    }

    @Override
    public void onResume() {
        super.onResume();

        getLoaderManager().restartLoader(EVENTS_LOADER, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String[] projection = null;
        String sortOrder = sharedPreferences.getString(getString(R.string.key_sort_list_preference), Event.COLUMN_EVENT_TITILE + " desc");

        boolean filter = sharedPreferences.getBoolean(getString(R.string.key_pref_filter_enabled), false);
        String[] selectionArgs = null;
        selectionArgs = getEventTypeFilter(filter, sharedPreferences);

        String selection = buildEventTypeSelection(selectionArgs.length);

        CursorLoader cursorLoader = new CursorLoader(getContext(), EventProvider.URL, projection, selection, selectionArgs, sortOrder);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        cursor.setNotificationUri(getContext().getContentResolver(), EventProvider.URL);

        boolean fastScroll = Boolean.FALSE;

        if (cursor.getCount() > FAST_SCROLL_COUNT) {
            fastScroll = Boolean.TRUE;
        }

        getListView().setFastScrollEnabled(fastScroll);

        eventAdapter.swapCursor(cursor);
        setListAdapter(eventAdapter);

        setFooterText();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        eventAdapter.swapCursor(null);
    }


    private String[] getEventTypeFilter(boolean filter, SharedPreferences sharedPrefs) {
        String[] filters = null;

        if(filter) {
            List<String> filterList = new ArrayList<String>();

            boolean filterBattery = sharedPrefs.getBoolean(getString(R.string.key_pref_filter_battery_change), false);
            boolean filterConfig = sharedPrefs.getBoolean(getString(R.string.key_pref_filter_config_change), false);
            boolean filterPower = sharedPrefs.getBoolean(getString(R.string.key_pref_filter_power_change), false);
            boolean filterScreen = sharedPrefs.getBoolean(getString(R.string.key_pref_filter_screen_change), false);
            boolean filterTimeTick = sharedPrefs.getBoolean(getString(R.string.key_pref_filter_time_ticks_change), false);
            boolean filterLogLength = sharedPrefs.getBoolean(getString(R.string.key_pref_filter_log_length_change), false);

            if (filterBattery) {
                filterList.add(EventType.BatteryChange.toString());
            }

            if (filterConfig) {
                filterList.add(EventType.ConfigurationChange.toString());
            }

            if (filterPower) {
                filterList.add(EventType.PowerConnected.toString());
                filterList.add(EventType.PowerDisconnected.toString());
            }

            if (filterScreen) {
                filterList.add(EventType.ScreenOff.toString());
                filterList.add(EventType.ScreenOn.toString());
            }

            if (filterTimeTick) {
                filterList.add(EventType.TimeTicks.toString());
            }

            if (filterLogLength) {
                filterList.add(EventType.LogLength.toString());
            }

            filters = filterList.toArray(new String[filterList.size()]);

        } else {
            filters = new String[8];
            filters[0] = EventType.BatteryChange.toString();
            filters[1] = EventType.ConfigurationChange.toString();
            filters[2] = EventType.PowerConnected.toString();
            filters[3] = EventType.PowerDisconnected.toString();
            filters[4] = EventType.ScreenOff.toString();
            filters[5] = EventType.ScreenOn.toString();
            filters[6] = EventType.TimeTicks.toString();
            filters[7] = EventType.LogLength.toString();
        }

        return filters;
    }

    @NonNull
    private String buildEventTypeSelection(int numberOfEventType) {
        StringBuffer selection = new StringBuffer().append(Event.COLUMN_EVENT_TYPE).append(" IN (");

        for (int i = 0; i < numberOfEventType; i++) {
            selection.append("?");

            if (i != numberOfEventType-1) {
                selection.append(",");
            }
        }

        selection.append(")");
        return selection.toString();
    }

    private void setFooterText() {
        TextView textView = (TextView) getView().findViewById(R.id.textViewFooter);
        textView.setText(String.format("Currently Showing %d entries", getListView().getAdapter().getCount()-1));
    }
}
