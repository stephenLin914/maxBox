package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.ubuntu.mymaxbox.R;

import java.util.Iterator;

public class RulerBackView extends View {
    private static final String TAG = "RulerBackView";

    private final float paddingTop = 50;
    private final float paddingRight = 27;
    private final float paddingLeft = 27;
    private final float unitBaseLength = 80;
    private final float rulerTextSize = 40;

    private final int CM = 0;
    private final int INCH = 1;

    private Matrix mMatrix;

    private float mViewWidth;
    private float mViewHeight;

    private Unit unit;
    private float dpi;

    public RulerBackView(Context context) {
        super(context);
    }

    public RulerBackView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initRulerBackView();
    }


    public RulerBackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RulerBackView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        float startX = (getRight()-getLeft())/2;
        float startY = getTop() + paddingTop;
        canvas.drawCircle(startX, startY, 10.0f, paint);
        RectF line = new RectF(startX-5, startY,startX+5,getBottom());
        canvas.drawRect(line, paint);

        float rulerRight = getRight()-paddingRight;

        paint.setColor(Color.BLACK);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(rulerTextSize);
        Iterator<Unit> cmUnitIt = getPixelIterator(getBottom(), CM);
        while( cmUnitIt.hasNext() ) {
            Unit unit = cmUnitIt.next();
            float unitStartX = rulerRight - unit.relativeLength * unitBaseLength;
            float unitEndX = rulerRight;
            float unitStartY = startY + unit.pixelOffset;
            float unitEndY = unitStartY;

            canvas.drawLine(unitStartX, unitStartY, unitEndX, unitEndY, paint);

            if( unit.value % 1 == 0 ) {
                canvas.save();
                String text = (int)unit.value + "";
                canvas.translate(unitStartX-rulerTextSize, unitStartY-textPaint.measureText(text)/2);
                canvas.rotate(90f);
                canvas.drawText(text, 0, 0, textPaint);
                canvas.restore();
            }
        }

        Iterator<Unit> inchUnitIt = getPixelIterator(getBottom(), INCH);
        while( inchUnitIt.hasNext() ) {
            Unit unit = inchUnitIt.next();
            float unitStartX = getLeft() + paddingLeft;
            float unitEndX = unitStartX + unit.relativeLength * unitBaseLength;
            float unitStartY = startY + unit.pixelOffset;
            float unitEndY = unitStartY;

            canvas.drawLine(unitStartX, unitStartY, unitEndX, unitEndY, paint);

            if( unit.value % 1 == 0 ) {
                canvas.save();
                String text = (int)unit.value + "";
                canvas.translate(unitEndX+rulerTextSize/2, unitStartY-textPaint.measureText(text)/2);
                canvas.rotate(90f);
                canvas.drawText(text, 0, 0, textPaint);
                canvas.restore();
            }
        }


    }

    private void initRulerBackView() {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpi = dm.ydpi;
//        Log.d(TAG, "dpi: " + dpi);
    }


    private class Unit {
        float value;
        int pixelOffset;
        float relativeLength;
    }

    public Iterator<Unit> getPixelIterator(final int numberOfPixels, final int type) {
        return new Iterator<Unit>() {

            Unit unit = new Unit();
            int unitIndex = 0;

            private float getValue() {
                return unitIndex * getPrecision(type);
            }

            private int getPixels() {
                return (int) (getValue() * getPixelPerUnit(type));
            }

            @Override
            public boolean hasNext() {
                return getPixels() < numberOfPixels;
            }

            @Override
            public Unit next() {
                unit.value = getValue();
                unit.pixelOffset = getPixels();
                unit.relativeLength = getScaleRelativeLength(unitIndex, type);
                ++unitIndex;
                return unit;
            }
        };
    }

    private float getPrecision(final int type) {
        if( type==INCH ) {
            return 1/4f;
        }
        return 1/10f;
    }

    private float getScaleRelativeLength(int unitIndex, final int type) {
        if( type==CM ) {
            if( unitIndex%10 == 0 ) {
                return 1f;
            } else if( unitIndex%5 == 0 ) {
                return 3/4f;
            } else {
                return 1/2f;
            }
        } else if( type==INCH ) {
            if( unitIndex%4 == 0 ) {
                return 1f;
            } else if( unitIndex%2 == 0 ) {
                return 3/4f;
            } else {
                return 1/2f;
            }
        }
        return 0;
    }

    private float getPixelPerUnit(final int type) {
        if( type==INCH ) {
            return dpi;
        }
        return dpi/2.54f;
    }

}
