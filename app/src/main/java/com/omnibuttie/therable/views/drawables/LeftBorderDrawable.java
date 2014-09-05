package com.omnibuttie.therable.views.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class LeftBorderDrawable extends Drawable {
    private int borderColorResource;
    private int backgroundColorResource;

    public LeftBorderDrawable(int borderColor, int backgroundColor) {
        this.borderColorResource = borderColor;
        this.backgroundColorResource = backgroundColor;
    }

    @Override
    public void draw(Canvas canvas) {
        int height = getBounds().height();
        int width = getBounds().width();
        RectF backRect = new RectF(0.0f, 0.0f, width, height);
        RectF borderRect = new RectF(0.0f, 0.0f, 4.0f, height);

        Paint backRectPaint = new Paint();
        backRectPaint.setColor(backgroundColorResource);
        backRectPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(backRect, backRectPaint);

        Paint borderRectPaint = new Paint();
        borderRectPaint.setColor(borderColorResource);
        borderRectPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(borderRect, borderRectPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}