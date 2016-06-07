package com.captech.merlick.status.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.captech.merlick.status.entities.Event;

/**
 * Created by merlick on 5/25/16.
 */
public class EventProvider extends ContentProvider {

    public static final String AUTHORITY = "com.captech.merlick.status.EventProvider";
    public static final Uri URL = Uri.parse(String.format("content://%s/%s", AUTHORITY, Event.TABLE_EVENTS));

    private static final UriMatcher uriMatcher = getUriMatcher();

    private DBHandler dbHandler;

    private static final int ALL_EVENTS = 1;

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, "events", ALL_EVENTS);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String eventTypes = null;

        return dbHandler.findEvents(projection, selection, selectionArgs, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ALL_EVENTS:
                return "AllEvents";
        }

        return "";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            long id = dbHandler.addEvent(values);
            Uri returnUri = ContentUris.withAppendedId(URL, id);
            getContext().getContentResolver().notifyChange(URL, null);
            return returnUri;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int result = dbHandler.removeAllEvents();
        getContext().getContentResolver().notifyChange(URL, null);
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
