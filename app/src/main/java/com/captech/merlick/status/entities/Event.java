package com.captech.merlick.status.entities;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by merlick on 5/25/16.
 */
public class Event {

    public static final String TABLE_EVENTS = "events";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EVENT_TITILE = "eventTitle";
    public static final String COLUMN_EVENT_TYPE = "eventType";
    public static final String COLUMN_EVENT_TIME = "eventTime";
    public static final String COLUMN_EVENT_BATTERY_PERCENT = "eventBatteryPercent";
    public static final String COLUMN_EVENT_BATTERY_TEMP = "eventBatteryTemp";
    public static final String COLUMN_EVENT_BATTERY_VOLTS = "eventBatteryVolts";
    public static final String COLUMN_EVENT_BATTERY_PLUG = "eventBatteryPlugIn";
    public static final String COLUMN_EVENT_BATTERY_CHARGE = "eventBatteryCharge";
    public static final String COLUMN_LOG_LENGTH = "logLength";

    public static final String[] ALL_FIELDS = {Event.COLUMN_ID, Event.COLUMN_EVENT_TITILE, Event.COLUMN_EVENT_TYPE, Event.COLUMN_EVENT_TIME, Event.COLUMN_EVENT_BATTERY_PERCENT,
            Event.COLUMN_EVENT_BATTERY_TEMP, Event.COLUMN_EVENT_BATTERY_VOLTS, Event.COLUMN_EVENT_BATTERY_CHARGE, Event.COLUMN_EVENT_BATTERY_PLUG, Event.COLUMN_LOG_LENGTH};

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_EVENTS + " (" +
                    COLUMN_ID + " integer PRIMARY KEY autoincrement," +
                    COLUMN_EVENT_TITILE + "," +
                    COLUMN_EVENT_TYPE + "," +
                    COLUMN_EVENT_TIME + "," +
                    COLUMN_EVENT_BATTERY_PERCENT + "," +
                    COLUMN_EVENT_BATTERY_TEMP + "," +
                    COLUMN_EVENT_BATTERY_VOLTS + "," +
                    COLUMN_EVENT_BATTERY_PLUG + "," +
                    COLUMN_EVENT_BATTERY_CHARGE + "," +
                    COLUMN_LOG_LENGTH + "," +
                    " UNIQUE (" + COLUMN_ID +"));";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

}
