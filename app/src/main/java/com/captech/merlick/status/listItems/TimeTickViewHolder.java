package com.captech.merlick.status.listItems;

import android.widget.TextView;

/**
 * Created by merlick on 5/19/16.
 */
public class TimeTickViewHolder extends BaseViewHolder {

    private TextView currentDateTextView;

    public TimeTickViewHolder(TextView titleOfEventTextView, TextView timestampOfEventTextView, TextView currentDateTextView) {
        super(titleOfEventTextView, timestampOfEventTextView);
        this.currentDateTextView = currentDateTextView;
    }

    public TextView getCurrentDateTextView() {
        return currentDateTextView;
    }

    public void setCurrentDateTextView(TextView currentDateTextView) {
        this.currentDateTextView = currentDateTextView;
    }
}
