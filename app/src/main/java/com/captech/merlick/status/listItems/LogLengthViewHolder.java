package com.captech.merlick.status.listItems;

import android.widget.TextView;

/**
 * Created by merlick on 5/19/16.
 */
public class LogLengthViewHolder extends BaseViewHolder {

    private TextView currentLogLengthTextView;

    public LogLengthViewHolder(TextView titleOfEventTextView, TextView timestampOfEventTextView, TextView currentLogLengthTextView) {
        super(titleOfEventTextView, timestampOfEventTextView);
        this.currentLogLengthTextView = currentLogLengthTextView;
    }

    public TextView getCurrentLogLengthTextView() {
        return currentLogLengthTextView;
    }

    public void setCurrentLogLengthTextView(TextView currentLogLengthTextView) {
        this.currentLogLengthTextView = currentLogLengthTextView;
    }
}
