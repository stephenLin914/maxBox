package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.ubuntu.mymaxbox.R;

public class RulerBackView extends View {
    private static final String TAG = "RulerBackView";

    private Matrix mMatrix;

    private float mViewWidth;
    private float mViewHeight;

    public RulerBackView(Context context) {
        super(context);
    }

    public RulerBackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RulerBackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RulerBackView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap backGroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.tool_ruler_bg);

        mMatrix = new Matrix();
        float widthScale = mViewWidth/backGroundImg.getWidth();
        float heightScale = mViewHeight/backGroundImg.getHeight();
        mMatrix.setScale(widthScale, heightScale);
        canvas.drawBitmap(backGroundImg, mMatrix, new Paint());
//        canvas.drawBitmap(backGroundImg, 0f, 0f, new Paint());
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = getHeight();
        mViewWidth = getWidth();
        Log.d(TAG, "height: " + mViewHeight + " width: " + mViewWidth);
    }
}
