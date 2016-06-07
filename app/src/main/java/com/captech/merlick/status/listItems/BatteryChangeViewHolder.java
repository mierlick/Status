package com.captech.merlick.status.listItems;

import android.widget.TextView;

/**
 * Created by merlick on 5/19/16.
 */
public class BatteryChangeViewHolder extends BaseViewHolder {

    private TextView batteryStatusTextView;

    private TextView batteryChargingTextView;

    public BatteryChangeViewHolder(TextView titleOfEventTextView, TextView timestampOfEventTextView, TextView batteryStatusTextView, TextView batteryChargingTextView) {
        super(titleOfEventTextView, timestampOfEventTextView);
        this.batteryStatusTextView = batteryStatusTextView;
        this.batteryChargingTextView = batteryChargingTextView;
    }

    public TextView getBatteryStatusTextView() {
        return batteryStatusTextView;
    }

    public void setBatteryStatusTextView(TextView batteryStatusTextView) {
        this.batteryStatusTextView = batteryStatusTextView;
    }

    public TextView getBatteryChargingTextView() {
        return batteryChargingTextView;
    }

    public void setBatteryChargingTextView(TextView batteryChargingTextView) {
        this.batteryChargingTextView = batteryChargingTextView;
    }
}
