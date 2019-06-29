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

public class MyRotateView extends View {
    private Matrix matrix;
    private float degree;
    private float curDegree;

    public MyRotateView(Context context) {
        super(context);
    }

    public MyRotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyRotateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("test", "rotateView onDraw");
        if( matrix==null ) {
            return;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.compass_point);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public float getCurDegree() {
        return curDegree;
    }

    public void setCurDegree(float curDegree) {
        this.curDegree = curDegree;
    }
}
