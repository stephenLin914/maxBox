package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ProtractorValueView extends View {
    private static final String TAG = "ProtractorValueView";

    private Paint mPaint;
    private String protractorValue;

    public ProtractorValueView(Context context) {
        super(context);
    }

    public ProtractorValueView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
    }

    public ProtractorValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProtractorValueView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if( protractorValue==null ) {
            return;
        }
        Log.d(TAG, "protractorValue: " + protractorValue);
        canvas.rotate(270);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(40);
        mPaint.setTextSize(100);
        canvas.drawText(protractorValue, -350, 120, mPaint);
    }

    public void setProtractorValue(String protractorValue) {
        this.protractorValue = protractorValue;
    }

}
