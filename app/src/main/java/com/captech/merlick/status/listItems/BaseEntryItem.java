package com.captech.merlick.status.listItems;

import com.captech.merlick.status.entities.EventType;

import java.util.Date;

/**
 * Created by merlick on 5/19/16.
 */
public class BaseEntryItem {

    private EventType eventType;

    private String eventTitle;

    private Date eventTime;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
}
