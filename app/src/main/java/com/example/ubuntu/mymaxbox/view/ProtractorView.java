package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.ubuntu.mymaxbox.R;

public class ProtractorView extends View {
    private static final String TAG = "ProtractorView";

    private Bitmap mBitmap;
    private float protractorX;
    private float protractorY;

    private float pointX;
    private float pointY;

    private Path mPath;

    private float k;
    private float k1, k2;
    private float left, top, bottom;

    private final float DISTANCE = 350;
    private boolean isFirst = true;
    private Paint mPaint;

    public ProtractorView(Context context) {
        super(context);
    }

    public ProtractorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProtractorView);
        k = array.getFloat(R.styleable.ProtractorView_k, 0f);
        array.recycle();

        initProtractorView();
    }

    public ProtractorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProtractorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_POINTER_DOWN:
//            case MotionEvent.ACTION_MOVE:
//                pointX = event.getX();
//                pointY = event.getY();
//                k = (protractorY-pointY)/(protractorX-pointX);
//                Log.d(TAG, "k: " + k + "pointX: " + pointX + "pointY:" + pointY);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                pointX = 0;
//                pointY = 0;
//                k = -0.8f;
//            default:
//                break;
//        }
//        return true;
//    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(Color.RED);
        // 需要加上这句，否则画不出东西
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setPathEffect(new DashPathEffect(new float[] {15, 5}, 0));

        mPath.reset();
        mPath.moveTo(protractorX, protractorY);
        float x, y;
        x = y = 0;
        if( isFirst ) {
            pointX = 0;
            pointY = protractorY - k*protractorX;
            isFirst = !isFirst;
        }
        float b = pointY - pointX*k;
        if( k>=k2 && k<=k1 ) {
            x = left;
            y = b;
        } else if( k>k1 ) {
            x = -1*b/k;
            y = 0;
        } else if( k<k2 ) {
            x = (bottom-b)/k;
            y = bottom;
        }

        Log.d(TAG, "k: " + k + " b:" + b + " k1: " + k1 + " k2: " + k2 + "x: " + x + " y: " + y);
        mPath.lineTo(x, y);
        canvas.drawPath(mPath, mPaint);

        float a = k*k + 1;
        float b2 = 2*k*b - 2*protractorX - 2*k*protractorY;
        float c = protractorX*protractorX + protractorY*protractorY - 2*b*protractorY + b*b - DISTANCE*DISTANCE;

        float x2 = (float) ((-1*b2-Math.sqrt(b2*b2-4*a*c))/(2*a));
        float y2 = k*x2+b;
        Log.d(TAG, "Math.sqrt(b2*b2-4*a*c):" + Math.sqrt(b2*b2-4*a*c) + " a:" + a + " b2:"+b2 + " c:" + c + " x2:" + x2 + " y2:" + y2);

        canvas.drawBitmap(mBitmap, x2-mBitmap.getWidth()/2, y2-mBitmap.getHeight()/2, mPaint);
        canvas.drawBitmap(mBitmap, protractorX-mBitmap.getWidth()/2, protractorY-mBitmap.getHeight()/2, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        protractorX = getRight();
        protractorY = (getBottom()-getTop())/2;
        left = getLeft();
        top = getTop();
        bottom = getBottom();

        k1 = (protractorY-top)/(protractorX-left);
        k2 = (protractorY-bottom)/(protractorX-left);
    }

    private void initProtractorView() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tool_protractro_paint);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public float getPointX() {
        return pointX;
    }

    public void setK(float k) {
        this.k = k;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    public float getPointY() {
        return pointY;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }

    public float getProtractorX() {
        return protractorX;
    }

    public float getProtractorY() {
        return protractorY;
    }
}
