package com.captech.merlick.status.entities;

/**
 * Created by merlick on 5/19/16.
 */
public enum EventType {
    ConfigurationChange(0, "Configuration Change"),
    BatteryChange(1, "Battery Change"),
    PowerConnected(2, "Power Connected"),
    PowerDisconnected(3, "Power Disconnected"),
    ScreenOn(4, "Screen On"),
    ScreenOff(5, "Screen Off"),
    TimeTicks(6, "Time Tick"),
    LogLength(7, "Log Length");

    private int viewId;

    private String eventName;

    EventType(int viewId, String eventName) {
        this.viewId = viewId;
        this.eventName = eventName;
    }

    public int getViewId() {
        return viewId;
    }

    public String getEventName() {
        return eventName;
    }
}
