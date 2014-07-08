package com.omnibuttie.therable.views.CalendarGrid;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.util.AttributeSet;

import com.avwave.colorcalendar.CalendarItemProvider;
import com.avwave.colorcalendar.MonthFragment;
import com.avwave.colorcalendar.MyCalendar;
import com.avwave.colorcalendar.MyCalendarProperties;
import com.omnibuttie.therable.R;

/**
 * Created by rayarvin on 6/29/14.
 */
public class JournalCalendar extends MyCalendar {
    public JournalCalendar(Context context) {
        super(context);
    }

    public JournalCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JournalCalendar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private CalendarItemProvider mOnShowMyCalendarListener;
    private MyCalendarProperties mCalendarProperties;

    @Override
    public void setFragmentManager(FragmentManager fm) {

            mFragmentManager = fm;
            mAdapter = new MyAdapter(mFragmentManager);
            mViewPager.setAdapter(mAdapter);



    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    class MyAdapter extends FragmentStatePagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyMonthFragment fragment = new MyMonthFragment(mYear, mMonth + position);
            if (mOnShowMyCalendarListener != null) {
                fragment.setOnShowCalendarListener(mOnShowMyCalendarListener);
            }
            if (mCalendarProperties != null) {
                fragment.setProperties(mCalendarProperties);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return MONTH_IN_YEAR;
        }
    }
}
