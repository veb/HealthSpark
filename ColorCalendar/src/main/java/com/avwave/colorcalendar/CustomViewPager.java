package com.avwave.colorcalendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by rayarvin on 6/29/14.
 */
public class CustomViewPager extends ViewPager {

    private View.OnTouchListener mGestureListener;
    private GestureDetector mGestureDetector;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new Detector());
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        mGestureDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    class Detector extends GestureDetector.SimpleOnGestureListener {

    }
}
