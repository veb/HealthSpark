package com.avwave.colorcalendar;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rayarvin on 6/29/14.
 */
public class MyCalendar extends FrameLayout implements View.OnClickListener, MyCalendarProperties.OnPropertiesChangedListener{

    public final static int DAY_PREVIOUS_MONTH = 22;
    public final static int DAY_CURRENT_MONTH = 23;
    public final static int DAY_NEXT_MONTH = 24;
    public final static int MONTH_IN_YEAR = 12;
    public CustomViewPager mViewPager;
    private ImageButton mPrevItemBtn;
    private ImageButton mNextItemBtn;

    public FragmentStatePagerAdapter mAdapter;
    public FragmentManager mFragmentManager;
    public Context mContext;
    public Calendar mCalendar;
    public int mCurrentPage;

    public int mYear;
    public int mMonth;
    public int mDay;


    public MyCalendarProperties mCalendarProperties;
    public CalendarItemProvider mOnShowMyCalendarListener;

    public MyCalendar(Context context) {
        super(context);
        init(context);
    }

    public MyCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCalendar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyCalendarProperties getProperties() {
        return mCalendarProperties;
    }

    public void setProperties(MyCalendarProperties properties) {
        mCalendarProperties = properties;
        mAdapter.notifyDataSetChanged();
    }

    public CalendarItemProvider getOnShowMyCalendarListener() {
        return mOnShowMyCalendarListener;
    }

    public void setOnShowMyCalendarListener(CalendarItemProvider onShowMyCalendarListener) {
        mOnShowMyCalendarListener = onShowMyCalendarListener;
    }

    /**
     * Sets current calendar day to show
     * @param year
     * @param month
     * @param day
     */
    public void setDate(int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;

        mCalendar.set(year, month, day);
    }

    public void setFragmentManager(FragmentManager fm) {
        mFragmentManager = fm;
        mAdapter = new ScreenSlidePagerAdapter(mFragmentManager);
        mViewPager.setAdapter(mAdapter);
    }


    public void init(Context context) {
        mContext = context;
        mCalendarProperties = new MyCalendarProperties(context);
        mCalendarProperties.setOnPropertiesChangedListener(this);
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_calendar_layout, null);
        mViewPager = (CustomViewPager) (view != null ? view.findViewById(R.id.pager) : null);
        mPrevItemBtn = (ImageButton) (view != null ? view.findViewById(R.id.btn_swipe_left) : null);
        mNextItemBtn = (ImageButton) (view != null ? view.findViewById(R.id.btn_swipe_right) : null);

        mNextItemBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage < MONTH_IN_YEAR){
                    mViewPager.setCurrentItem(mCurrentPage + 1);
                    mCurrentPage++;
                }
            }
        });
        mPrevItemBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage > 0){
                    mViewPager.setCurrentItem(mCurrentPage - 1);
                    mCurrentPage--;
                }
            }
        });

        addView(view);
        mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date());
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = Calendar.JANUARY;
    }

    @Override
    public void onClick(View v) {
        mCurrentPage = mViewPager.getCurrentItem();
    }

    @Override
    public void save() {
        mNextItemBtn.setImageDrawable(mCalendarProperties.getNextPageButtonDrawable());
        mPrevItemBtn.setImageDrawable(mCalendarProperties.getPrevPageButtonDrawable());
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MonthFragment fragment = new MonthFragment(mYear, mMonth + position);
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
