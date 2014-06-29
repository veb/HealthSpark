package com.avwave.colorcalendar;

/**
 * Created by rayarvin on 6/29/14.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/**
 * Created by Navi on 10/17/13.
 */
public class MyCalendarProperties {

    //General properties
    private int mVerticalPadding;
    private int mHorizontalPadding;

    //Calendar day names properties
    private boolean isDayNamesVisible;
    private int mDaysFontColor;
    private int mDaysFontSize;

    //Calendar number properties
    private int mNumberFontColor;
    private int mOutOfBoundsNumberFontColor;
    private int mNumberFontSize;
    private Typeface mNumberFontTypeface;
    //Header - day name
    private int mHeaderFontSize;
    private int mHeaderFontColor;
    private Typeface mHeaderFontTypeface;
    //Buttons swipe left, swipe right
    private Drawable mPrevPageButtonDrawable;
    private Drawable mNextPageButtonDrawable;

    //Listener
    private OnPropertiesChangedListener mListener;

    public MyCalendarProperties(Context context) {
        Resources resources = context.getResources();
        mNumberFontColor = Color.WHITE;
        mOutOfBoundsNumberFontColor = Color.DKGRAY;
        mNumberFontSize = (int) resources.getDimension(R.dimen.calendar_font_size);
        mNumberFontTypeface = null;
        mHeaderFontSize = (int) resources.getDimension(R.dimen.calendar_header_font_size);
        mHeaderFontColor = Color.WHITE;
        mHeaderFontTypeface = null;
        mPrevPageButtonDrawable = resources.getDrawable(R.drawable.ic_prev_item);
        mNextPageButtonDrawable = resources.getDrawable(R.drawable.ic_next_item);
        mVerticalPadding = 20;
        mHorizontalPadding = 20;
        isDayNamesVisible = true;
        mDaysFontColor = Color.LTGRAY;
        mDaysFontSize = (int) resources.getDimension(R.dimen.calendar_font_size);
    }

    public int getVerticalPadding() {
        return mVerticalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        mVerticalPadding = verticalPadding;
    }

    public int getHorizontalPadding() {
        return mHorizontalPadding;
    }

    public void setHorizontalPadding(int horizontalPadding) {
        mHorizontalPadding = horizontalPadding;
    }

    public boolean isDayNamesVisible() {
        return isDayNamesVisible;
    }

    public void setDayNamesVisible(boolean dayNamesVisible) {
        isDayNamesVisible = dayNamesVisible;
    }

    public int getDaysFontColor() {
        return mDaysFontColor;
    }

    public void setDaysFontColor(int daysFontColor) {
        mDaysFontColor = daysFontColor;
    }

    public int getDaysFontSize() {
        return mDaysFontSize;
    }

    public void setDaysFontSize(int daysFontSize) {
        mDaysFontSize = daysFontSize;
    }

    public int getNumberFontColor() {
        return mNumberFontColor;
    }

    public void setNumberFontColor(int numberFontColor) {
        mNumberFontColor = numberFontColor;
    }

    public int getOutOfBoundsNumberFontColor() {
        return mOutOfBoundsNumberFontColor;
    }

    public void setOutOfBoundsNumberFontColor(int outOfBoundsNumberFontColor) {
        mOutOfBoundsNumberFontColor = outOfBoundsNumberFontColor;
    }

    public int getNumberFontSize() {
        return mNumberFontSize;
    }

    public void setNumberFontSize(int numberFontSize) {
        mNumberFontSize = numberFontSize;
    }

    public Typeface getNumberFontTypeface() {
        return mNumberFontTypeface;
    }

    public void setNumberFontTypeface(Typeface numberFontTypeface) {
        mNumberFontTypeface = numberFontTypeface;
    }

    public int getHeaderFontSize() {
        return mHeaderFontSize;
    }

    public void setHeaderFontSize(int headerFontSize) {
        mHeaderFontSize = headerFontSize;
    }

    public int getHeaderFontColor() {
        return mHeaderFontColor;
    }

    public void setHeaderFontColor(int headerFontColor) {
        mHeaderFontColor = headerFontColor;
    }

    public Typeface getHeaderFontTypeface() {
        return mHeaderFontTypeface;
    }

    public void setHeaderFontTypeface(Typeface headerFontTypeface) {
        mHeaderFontTypeface = headerFontTypeface;
    }

    public Drawable getPrevPageButtonDrawable() {
        return mPrevPageButtonDrawable;
    }

    public void setPrevPageButtonDrawable(Drawable prevPageButtonDrawable) {
        mPrevPageButtonDrawable = prevPageButtonDrawable;
    }

    public Drawable getNextPageButtonDrawable() {
        return mNextPageButtonDrawable;
    }

    public void setNextPageButtonDrawable(Drawable nextPageButtonDrawable) {
        mNextPageButtonDrawable = nextPageButtonDrawable;
    }

    public void commit(){
        mListener.save();
    }

    public void setOnPropertiesChangedListener(OnPropertiesChangedListener listener){
        mListener = listener;
    }

    public interface OnPropertiesChangedListener{
        public void save();
    }
}