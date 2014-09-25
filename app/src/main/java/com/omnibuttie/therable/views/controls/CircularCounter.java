package com.omnibuttie.therable.views.controls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.omnibuttie.therable.R;

/**
 * Created by rayarvin on 9/23/14.
 */
public class CircularCounter extends View {
    //6:00
    private final static float START_DEGREES = 90;

    private int mBackgroundCenter;
    private int mBackgroundRadius;

    private int mOneDegrees;
    private int mTwoDegrees;
    private int mThreeDegrees;

    private int mOneValue = 0;

    private int mRange;

    private float mOneWidth;
    private float mTwoWidth;
    private float mThreeWidth;

    private float mTextSize;
    private float mMetricSize;

    private int mOneColor;
    private int mTwoColor;
    private int mThreeColor;


    private int mTextColor = -1;
    private int mBackgroundColor;

    private Paint mOnePaint;
    private Paint mTwoPaint;
    private Paint mThreePaint;
    private Paint mBackgroundPaint;
    private Paint mTextPaint;
    private Paint mMetricPaint;

    private RectF mOneBounds;
    private RectF mTwoBounds;
    private RectF mThreeBounds;

    private float mTextPosY;
    private float mMetricPosY;
    private float mMetricPaddingY;

    private String mMetricText;

    private Typeface mTypeface;

    private SpeedHandler mSpinHandler;


    @SuppressLint("Recycle")
    public CircularCounter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context.obtainStyledAttributes(attrs, R.styleable.CircularMeter));
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mSpinHandler = new SpeedHandler(this);
        setupBounds();
        setupPaints();
        setupTextPosition();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mSpinHandler = null;
        mOnePaint = null;
        mOneBounds = null;
        mTwoPaint = null;
        mTwoBounds = null;
        mBackgroundPaint = null;
        mTextPaint = null;
        mMetricPaint = null;
    }


    /**
     * Set up paint variables to be used in onDraw method
     */
    private void setupPaints() {

        mOnePaint = new Paint();
        mOnePaint.setColor(mOneColor);
        mOnePaint.setAntiAlias(true);
        mOnePaint.setStyle(Paint.Style.STROKE);
        mOnePaint.setStrokeWidth(mOneWidth);

        mTwoPaint = new Paint();
        mTwoPaint.setColor(mTwoColor);
        mTwoPaint.setAntiAlias(true);
        mTwoPaint.setStyle(Paint.Style.STROKE);
        mTwoPaint.setStrokeWidth(mTwoWidth);

        mThreePaint = new Paint();
        mThreePaint.setColor(mThreeColor);
        mThreePaint.setAntiAlias(true);
        mThreePaint.setStyle(Paint.Style.STROKE);
        mThreePaint.setStrokeWidth(mThreeWidth);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mBackgroundColor);
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTypeface(mTypeface);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mMetricPaint = new Paint();
        mMetricPaint.setColor(mTextColor);
        mMetricPaint.setStyle(Paint.Style.FILL);
        mMetricPaint.setAntiAlias(true);
        mMetricPaint.setTextSize(mMetricSize);
        mMetricPaint.setTypeface(mTypeface);
        mMetricPaint.setTextAlign(Paint.Align.CENTER);
    }


    /**
     * Set the bounds of the bars.
     */
    private void setupBounds() {

        mBackgroundCenter = this.getLayoutParams().width / 2;
        mBackgroundRadius = mBackgroundCenter - this.getPaddingTop();

        mOneBounds = new RectF(this.getPaddingTop() + mOneWidth / 2,
                this.getPaddingLeft() + mOneWidth / 2,
                this.getLayoutParams().width - this.getPaddingRight()
                        - mOneWidth / 2, this.getLayoutParams().height
                - this.getPaddingBottom() - mOneWidth / 2);

        mTwoBounds = new RectF(
                this.getPaddingTop() + mTwoWidth / 2 + mOneWidth,
                this.getPaddingLeft() + mTwoWidth / 2 + mOneWidth,
                this.getLayoutParams().width - this.getPaddingRight()
                        - mTwoWidth / 2 - mOneWidth,
                this.getLayoutParams().height - this.getPaddingBottom()
                        - mTwoWidth / 2 - mOneWidth);

        mThreeBounds = new RectF(this.getPaddingTop() + mThreeWidth / 2
                + mTwoWidth + mOneWidth, this.getPaddingLeft() + mThreeWidth
                / 2 + mTwoWidth + mOneWidth, this.getLayoutParams().width
                - this.getPaddingRight() - mThreeWidth / 2 - mTwoWidth
                - mOneWidth, this.getLayoutParams().height
                - this.getPaddingBottom() - mThreeWidth / 2 - mTwoWidth
                - mOneWidth);
    }


    /**
     * Setting up text position
     */
    private void setupTextPosition() {
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds("1", 0, 1, textBounds);
        mTextPosY = mOneBounds.centerY() + (textBounds.height() / 2f);
        mMetricPosY = mTextPosY + mMetricPaddingY;
    }


    /**
     * Parse the attributes passed to the view and default values.
     */
    private void init(TypedArray a) {

        mTextSize = a.getDimension(R.styleable.CircularMeter_textSize,
                getResources().getDimension(R.dimen.textSize));
        mTextColor = a
                .getColor(R.styleable.CircularMeter_textColor, mTextColor);

        mMetricSize = a.getDimension(R.styleable.CircularMeter_metricSize,
                getResources().getDimension(R.dimen.metricSize));
        mMetricText = a.getString(R.styleable.CircularMeter_metricText);
        mMetricPaddingY = getResources().getDimension(R.dimen.metricPaddingY);

        mRange = a.getInt(R.styleable.CircularMeter_range, 100);

        mOneWidth = getResources().getDimension(R.dimen.width);
        mTwoWidth = getResources().getDimension(R.dimen.width);
        mThreeWidth = getResources().getDimension(R.dimen.width);

        mOneColor = -1213350;
        mTwoColor = -7747644;
        mThreeColor = -1;

        mOneDegrees = 0;
        mTwoDegrees = 0;
        mThreeDegrees = 0;

        String aux = a.getString(R.styleable.CircularMeter_typeface);
        if (aux != null)
            mTypeface = Typeface.createFromAsset(this.getResources()
                    .getAssets(), aux);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mBackgroundCenter, mBackgroundCenter,
                mBackgroundRadius, mBackgroundPaint);

        canvas.drawArc(mOneBounds, START_DEGREES, mOneDegrees, false, mOnePaint);
        canvas.drawArc(mTwoBounds, START_DEGREES, mTwoDegrees, false, mTwoPaint);
        canvas.drawArc(mThreeBounds, START_DEGREES, mThreeDegrees, false,
                mThreePaint);

        canvas.drawText(Integer.toString(mOneValue), mOneBounds.centerX(),
                mTextPosY, mTextPaint);
        canvas.drawText(mMetricText, mOneBounds.centerX(), mMetricPosY,
                mMetricPaint);
    }



	/*
     * Setters
	 *
	 */

    /**
     * Set the next values to be drawn
     *
     * @param v1
     * @param v2
     * @param v3
     */
    public void setValues(int v1, int v2, int v3) {

        if (v1 <= mRange)
            mOneDegrees = Math.round(((float) v1 * 360) / mRange);
        else
            mOneDegrees = 360;

        if (v2 <= mRange)
            mTwoDegrees = Math.round(((float) v2 * 360) / mRange);
        else
            mTwoDegrees = 360;

        if (v3 <= mRange)
            mThreeDegrees = Math.round(((float) v3 * 360) / mRange);
        else
            mThreeDegrees = 360;

        mOneValue = v1;

//        mSpinHandler.sendEmptyMessage(0);
    }


    public CircularCounter setRange(int range) {
        mRange = range;
        return this;
    }

    public CircularCounter setFirstWidth(float width) {
        mOneWidth = width;
        return this;
    }

    public CircularCounter setSecondWidth(float width) {
        mTwoWidth = width;
        return this;
    }

    public CircularCounter setThirdWidth(float width) {
        mThreeWidth = width;
        return this;
    }

    public CircularCounter setTextSize(float size) {
        mTextSize = size;
        return this;
    }

    public CircularCounter setMetricSize(float size) {
        mMetricSize = size;
        return this;
    }

    public CircularCounter setFirstColor(int color) {
        mOneColor = color;
        return this;
    }

    public CircularCounter setSecondColor(int color) {
        mTwoColor = color;
        return this;
    }

    public CircularCounter setThirdColor(int color) {
        mThreeColor = color;
        return this;
    }

    public CircularCounter setTextColor(int color) {
        mTextColor = color;
        return this;
    }

    public CircularCounter setMetricText(String text) {
        mMetricText = text;
        return this;
    }

    @Override
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    public CircularCounter setTypeface(Typeface typeface) {
        mTypeface = typeface;
        return this;
    }


    /**
     * Handles display invalidates
     */
    private static class SpeedHandler extends Handler {

        private CircularCounter act;

        public SpeedHandler(CircularCounter act) {
            super();
            this.act = act;
        }

        @Override
        public void handleMessage(Message msg) {
            act.invalidate();
            super.handleMessage(msg);
        }

    }

}