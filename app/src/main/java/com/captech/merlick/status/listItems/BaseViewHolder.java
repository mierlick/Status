package com.captech.merlick.status.listItems;

import android.widget.TextView;

/**
 * Created by merlick on 5/19/16.
 */
public class BaseViewHolder {

    private TextView titleOfEventTextView;

    private TextView timestampOfEventTextView;

    public BaseViewHolder(TextView titleOfEventTextView, TextView timestampOfEventTextView) {
        this.titleOfEventTextView = titleOfEventTextView;
        this.timestampOfEventTextView = timestampOfEventTextView;
    }

    public TextView getTitleOfEventTextView() {
        return titleOfEventTextView;
    }

    public void setTitleOfEventTextView(TextView titleOfEventTextView) {
        this.titleOfEventTextView = titleOfEventTextView;
    }

    public TextView getTimestampOfEventTextView() {
        return timestampOfEventTextView;
    }

    public void setTimestampOfEventTextView(TextView timestampOfEventTextView) {
        this.timestampOfEventTextView = timestampOfEventTextView;
    }
}
