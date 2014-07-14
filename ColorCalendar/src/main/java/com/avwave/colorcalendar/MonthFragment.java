package com.avwave.colorcalendar;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by rayarvin on 6/29/14.
 */
public class MonthFragment extends Fragment {
    public static final int DAYS_IN_WEEK = 7;
    public static final String[] DAYS = new String[]{"M", "T", "W", "T", "F", "S", "S"};
    public GridView mGridView;
    public BaseAdapter mGridAdapter;
    public TextView mMonthNameTextView;
    public Calendar mCalendar;
    public MyCalendarProperties mProperties;
    public CalendarItemProvider mListener;
    public int mYear;
    public int mMonth;
    //Utils
    public Display mDisplay;


    public MonthFragment() {
        mCalendar = Calendar.getInstance();
    }

    @SuppressLint("ValidFragment")
    public MonthFragment(int year, int month) {
        this();
        mYear = year;
        mMonth = month;
        mCalendar.set(year, month, 1);
    }

    public void setProperties(MyCalendarProperties properties) {
        mProperties = properties;
    }

    public void setOnShowCalendarListener(CalendarItemProvider listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mProperties == null) {
            mProperties = new MyCalendarProperties(getActivity());
        }
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        mDisplay = wm.getDefaultDisplay();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.month_fragment, null);
        mGridView = (GridView) (view != null ? view.findViewById(R.id.grid) : null);
        mMonthNameTextView = (TextView) (view != null ? view.findViewById(R.id.txt_month_name) : null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMonthNameTextView();
        mGridAdapter = new MonthAdapter();
        mGridView.setAdapter(mGridAdapter);
    }

    public void initMonthNameTextView() {
        mMonthNameTextView.setText(mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        mMonthNameTextView.setTextColor(mProperties.getHeaderFontColor());
        mMonthNameTextView.setTextSize(mProperties.getHeaderFontSize());
        Typeface typeface = mProperties.getHeaderFontTypeface();
        if (typeface != null) {
            mMonthNameTextView.setTypeface(typeface);
        }

    }

    public int getHowManyWeeksInMonth() {
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mCalendar.set(Calendar.MONTH, mMonth);
        // Month value starts from 0 to 11 for Jan to Dec
        return mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
    }

    public int getHowManyDaysInMonth() {
        mCalendar.set(mYear, mMonth, 1);
        return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @SuppressWarnings("ResourceType")
    public int getHowManyDaysInPrevMonth() {
        if (mMonth == 0) {
            //If january  go to december of previous year
            mCalendar.set(mYear - 1, Calendar.DECEMBER, 1);
        } else {
            mCalendar.set(mYear, mMonth - 1, 1);
        }
        return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public int getFirstDayOfMonth() {
        mCalendar.set(mYear, mMonth, 1);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        return getDayOfWeekNumber(dayOfWeek);
    }

    public int getDayOfWeekNumber(int constant) {
        switch (constant) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
        }
        return 0;
    }

    public View getCurrentView(int position, View convertView) {
        int currentDay;
        int firstDayNum = getFirstDayOfMonth();


        if (firstDayNum > position) {
            //fill previous month
            int prevMonthDays = getHowManyDaysInPrevMonth();
            currentDay = prevMonthDays - (firstDayNum - position);
            currentDay++;
            if (mListener != null) {
                convertView = mListener.getMonthDayItem(currentDay, convertView, MyCalendar.DAY_PREVIOUS_MONTH);
                if (convertView == null) {
                    convertView = getDefaultCalendarItemView(convertView);
                    ((TextView) convertView).setTextColor(mProperties.getOutOfBoundsNumberFontColor());
                    ((TextView) convertView).setText("" + currentDay);
                    convertView.setTag(true);
                }
            } else {
                convertView = getDefaultCalendarItemView(convertView);
                ((TextView) convertView).setTextColor(mProperties.getOutOfBoundsNumberFontColor());
                ((TextView) convertView).setText("" + currentDay);
                convertView.setTag(true);
            }
        } else if (position > getHowManyDaysInMonth() + firstDayNum - 1) {
            //fill next month

            currentDay = position - (getHowManyDaysInMonth() + firstDayNum);
            currentDay++;
            if (mListener != null) {
                convertView = mListener.getMonthDayItem(currentDay, convertView, MyCalendar.DAY_NEXT_MONTH);
                if (convertView == null) {
                    convertView = getDefaultCalendarItemView(convertView);
                    ((TextView) convertView).setTextColor(mProperties.getOutOfBoundsNumberFontColor());
                    ((TextView) convertView).setText("" + currentDay);
                    convertView.setTag(true);
                }
            } else {
                convertView = getDefaultCalendarItemView(convertView);
                ((TextView) convertView).setTextColor(mProperties.getOutOfBoundsNumberFontColor());
                ((TextView) convertView).setText("" + currentDay);
                convertView.setTag(true);
            }
        } else {
            //fill current month
            currentDay = position - firstDayNum;
            currentDay++;
            if (mListener != null) {
                convertView = mListener.getMonthDayItem(currentDay,convertView,MyCalendar.DAY_NEXT_MONTH);
                if (convertView == null) {
                    convertView = getDefaultCalendarItemView(convertView);
                    ((TextView) convertView).setText("" + currentDay);
                }
            } else {
                convertView = getDefaultCalendarItemView(convertView);
                ((TextView) convertView).setTextColor(mProperties.getNumberFontColor());
                ((TextView) convertView).setText("" + currentDay);
                ((TextView) convertView).setGravity(Gravity.CENTER);
            }

        }
        ((TextView) convertView).setTypeface(mProperties.getNumberFontTypeface());

        return convertView;
    }

    public View getDefaultCalendarItemView(View view) {
        if (view == null) {
            view = new TextView(getActivity());
            ((TextView) view).setGravity(Gravity.CENTER);
            ((TextView) view).setTextColor(mProperties.getNumberFontColor());
            ((TextView) view).setTextSize(mProperties.getNumberFontSize());
            ((TextView) view).setTypeface(mProperties.getNumberFontTypeface());

        }
        return view;
    }

    public View getWeekDayName(int position, View convertView) {
        if (convertView == null || !(convertView instanceof TextView)) {
            convertView = new TextView(getActivity());
            ((TextView) convertView).setTextColor(mProperties.getDaysFontColor());
            ((TextView) convertView).setTextSize(mProperties.getDaysFontSize());
            ((TextView) convertView).setGravity(Gravity.CENTER_HORIZONTAL);
        }
        ((TextView) convertView).setText(DAYS[position]);
        return convertView;
    }

    public LocalDate getDateAtPosition(int position) {
        mCalendar.set(mYear, mMonth, position);

        return new LocalDate(mCalendar);
    }

    public class MonthAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            int count;
            if (mProperties.isDayNamesVisible()) {
                count = (getHowManyWeeksInMonth() + 1) * DAYS_IN_WEEK;
            } else {
                count = (getHowManyWeeksInMonth()) * DAYS_IN_WEEK;
            }
            return count;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (mProperties.isDayNamesVisible() && position < 7) {
                return getWeekDayName(position, convertView);
            } else {
                convertView = getCurrentView(position, convertView);
            }
            if (mProperties.isDayNamesVisible()) {
                convertView = getCurrentView(position - 7, convertView);
            }
            setViewWidthHeight(convertView);

            return convertView;
        }

        public void setViewWidthHeight(View convertView) {
            int orientation = getActivity().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {

                int width = mDisplay.getWidth();  // deprecated

                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = width / DAYS_IN_WEEK;
                params.height = width / DAYS_IN_WEEK;
                convertView.setLayoutParams(params);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                int height = mDisplay.getHeight();  // deprecated

                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = height / DAYS_IN_WEEK;
                params.height = height / DAYS_IN_WEEK;
                convertView.setLayoutParams(params);
            }
        }
    }
}
