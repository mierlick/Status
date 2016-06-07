package com.captech.merlick.status;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.captech.merlick.status.entities.Event;
import com.captech.merlick.status.entities.EventType;
import com.captech.merlick.status.listItems.BaseViewHolder;
import com.captech.merlick.status.listItems.BatteryChangeViewHolder;
import com.captech.merlick.status.listItems.LogLengthViewHolder;
import com.captech.merlick.status.listItems.TimeTickViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by merlick on 5/19/16.
 */
public class EventAdapter extends CursorAdapter {

    private LayoutInflater cursorInflator;

    public EventAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.cursorInflator =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return EventType.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        Cursor cursor = getCursor();

        int columnIndex = cursor.getColumnIndex(Event.COLUMN_EVENT_TYPE);
        String eventType = cursor.getString(columnIndex);

        return EventType.valueOf(eventType).getViewId();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        BaseViewHolder viewHolder = null;
        View itemView = null;

        int viewType = this.getItemViewType(cursor.getPosition());
        if (viewType == EventType.BatteryChange.getViewId()) {
            itemView = cursorInflator.inflate(R.layout.battery_change_entry_item, parent, false);

            TextView titleOfEventTextView = (TextView) itemView.findViewById(R.id.title_of_event);
            TextView timestampOfEventTextView = (TextView) itemView.findViewById(R.id.timestamp_of_event);
            TextView batteryStatusTextView = (TextView) itemView.findViewById(R.id.battery_stats);
            TextView batteryChargingTextView = (TextView) itemView.findViewById(R.id.battery_charging);

            viewHolder = new BatteryChangeViewHolder(titleOfEventTextView, timestampOfEventTextView, batteryStatusTextView, batteryChargingTextView);

        } else if (viewType == EventType.TimeTicks.getViewId()) {
            itemView = cursorInflator.inflate(R.layout.time_tick_entry_item, parent, false);

            TextView titleOfEventTextView = (TextView) itemView.findViewById(R.id.title_of_event);
            TextView timestampOfEventTextView = (TextView) itemView.findViewById(R.id.timestamp_of_event);
            TextView currentDateTextView = (TextView) itemView.findViewById(R.id.current_time_of_event);

            viewHolder = new TimeTickViewHolder(titleOfEventTextView, timestampOfEventTextView, currentDateTextView);

        } else if (viewType == EventType.LogLength.getViewId()) {
            itemView = cursorInflator.inflate(R.layout.log_length_entry_item, parent, false);

            TextView titleOfEventTextView = (TextView) itemView.findViewById(R.id.title_of_event);
            TextView timestampOfEventTextView = (TextView) itemView.findViewById(R.id.timestamp_of_event);
            TextView currentLogLengthTextView = (TextView) itemView.findViewById(R.id.current_log_length);

            viewHolder = new LogLengthViewHolder(titleOfEventTextView, timestampOfEventTextView, currentLogLengthTextView);

        } else {
            itemView = cursorInflator.inflate(R.layout.basic_entry_item, parent, false);

            TextView titleOfEventTextView = (TextView) itemView.findViewById(R.id.title_of_event);
            TextView timestampOfEventTextView = (TextView) itemView.findViewById(R.id.timestamp_of_event);

            viewHolder = new BaseViewHolder(titleOfEventTextView, timestampOfEventTextView);
        }

        itemView.setTag(viewHolder);

        return itemView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int viewType = this.getItemViewType(cursor.getPosition());

        String eventTitle = cursor.getString(cursor.getColumnIndex(Event.COLUMN_EVENT_TITILE));
        String eventTime = cursor.getString(cursor.getColumnIndex(Event.COLUMN_EVENT_TIME));

        if (viewType == EventType.BatteryChange.getViewId()) {
            if (view.getTag() instanceof BatteryChangeViewHolder) {
                BatteryChangeViewHolder holder = (BatteryChangeViewHolder) view.getTag();

                Integer batteryPercent = cursor.getInt(cursor.getColumnIndex(Event.COLUMN_EVENT_BATTERY_PERCENT));
                Float batteryTemp = cursor.getFloat(cursor.getColumnIndex(Event.COLUMN_EVENT_BATTERY_TEMP));
                Float batteryVolts = cursor.getFloat(cursor.getColumnIndex(Event.COLUMN_EVENT_BATTERY_VOLTS));
                String batteryPluggedIn  = cursor.getString(cursor.getColumnIndex(Event.COLUMN_EVENT_BATTERY_PLUG));
                String batteryChangeStatus  = cursor.getString(cursor.getColumnIndex(Event.COLUMN_EVENT_BATTERY_CHARGE));
                String degreeF = "\u2109";

                String batteryStatus = String.format("%d%% | %.1f%s | %.3fV", batteryPercent, batteryTemp, degreeF, batteryVolts);
                String batteryCharging = String.format("%s | %s", batteryPluggedIn, batteryChangeStatus);

                holder.getTitleOfEventTextView().setText(eventTitle);
                holder.getTimestampOfEventTextView().setText(eventTime);
                holder.getBatteryStatusTextView().setText(batteryStatus);
                holder.getBatteryChargingTextView().setText(batteryCharging);
            }

        } else if (viewType == EventType.TimeTicks.getViewId()) {
            if ( view.getTag() instanceof TimeTickViewHolder) {
                TimeTickViewHolder holder = (TimeTickViewHolder) view.getTag();

                Date eventDate = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                try {
                    eventDate = dateFormat.parse(eventTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String currentTime = String.format("The time is now %s", timeFormat.format(eventDate));

                holder.getTitleOfEventTextView().setText(eventTitle);
                holder.getTimestampOfEventTextView().setText(eventTime);
                holder.getCurrentDateTextView().setText(currentTime);
            }
        } else if (viewType == EventType.LogLength.getViewId()) {
            if ( view.getTag() instanceof LogLengthViewHolder) {
                LogLengthViewHolder holder = (LogLengthViewHolder) view.getTag();

                Integer logLength = cursor.getInt(cursor.getColumnIndex(Event.COLUMN_LOG_LENGTH));

                String logLengthMessage = String.format("Log Length: %d", logLength);

                holder.getTitleOfEventTextView().setText(eventTitle);
                holder.getTimestampOfEventTextView().setText(eventTime);
                holder.getCurrentLogLengthTextView().setText(logLengthMessage);
            }
        } else {
            BaseViewHolder holder = (BaseViewHolder) view.getTag();

            holder.getTitleOfEventTextView().setText(eventTitle);
            holder.getTimestampOfEventTextView().setText(eventTime);
        }
    }

}
