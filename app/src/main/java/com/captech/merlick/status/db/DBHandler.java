package com.captech.merlick.status.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.captech.merlick.status.entities.Event;

import java.sql.SQLException;

/**
 * Created by merlick on 5/25/16.
 */
public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "eventsDB";

    private ContentResolver eventResolver;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        eventResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Event.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized long addEvent(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(Event.TABLE_EVENTS, "", values);

        if (id <=0) {
           throw new SQLException("Unable to Insert Record");
        }

        return id;
    }

    public synchronized Cursor findEvents(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Event.TABLE_EVENTS);

        Cursor cursor = builder.query(getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    public synchronized int removeAllEvents() {
        return getWritableDatabase().delete(Event.TABLE_EVENTS, null, null);
    }

}
