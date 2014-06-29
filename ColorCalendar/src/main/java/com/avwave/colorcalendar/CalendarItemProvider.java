package com.avwave.colorcalendar;

import android.view.View;

/**
 * Created by rayarvin on 6/29/14.
 */
public interface CalendarItemProvider {
    public View getMonthDayItem(int day, View convertView, int dayIndex);
}
