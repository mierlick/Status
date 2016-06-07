package com.captech.merlick.status.listItems;

/**
 * Created by merlick on 5/19/16.
 */
public class BatteryChangeEntryItem extends BaseEntryItem {

    private int batteryPercent;

    private float batteryTemp;

    private float batteryVolts;

    private String batteryPluggedInStatus;

    private String batteryChageStatus;

    public int getBatteryPercent() {
        return batteryPercent;
    }

    public void setBatteryPercent(int batteryPercent) {
        this.batteryPercent = batteryPercent;
    }

    public float getBatteryTemp() {
        return batteryTemp;
    }

    public void setBatteryTemp(float batteryTemp) {
        this.batteryTemp = batteryTemp;
    }

    public float getBatteryVolts() {
        return batteryVolts;
    }

    public void setBatteryVolts(float batteryVolts) {
        this.batteryVolts = batteryVolts;
    }

    public String getBatteryPluggedInStatus() {
        return batteryPluggedInStatus;
    }

    public void setBatteryPluggedInStatus(String batteryPluggedInStatus) {
        this.batteryPluggedInStatus = batteryPluggedInStatus;
    }

    public String getBatteryChageStatus() {
        return batteryChageStatus;
    }

    public void setBatteryChageStatus(String batteryChageStatus) {
        this.batteryChageStatus = batteryChageStatus;
    }
}
