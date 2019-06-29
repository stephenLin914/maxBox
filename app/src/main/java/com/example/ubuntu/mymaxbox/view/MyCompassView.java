package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.example.ubuntu.mymaxbox.R;

public class MyCompassView extends View {
    private static final String TAG = "MyCompassView";

    private float degree;
    private float curDegree;

    private Matrix backMatrix;
    private Matrix compassMatrix;
    private Bitmap backBitmap;
    private Bitmap compassBitmap;
    private Paint paint = new Paint();
    private RectF rectF;
    private Shader shader;


    private String dirEngName = "N";

    private MyRotateView rotateView;


    public MyCompassView(Context context) {
        super(context);
    }

    public MyCompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyCompassView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        prapareDrawing();

//        canvas.drawBitmap(backBitmap, backMatrix, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawBitmap(compassBitmap, compassMatrix, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#1CA2EF"));
        paint.setTextSize(40f);
        String degreeStr = String.valueOf(degree);
        canvas.drawText(degreeStr.substring(0, degreeStr.indexOf(".")), rectF.centerX(), rectF.centerY(), paint);
        if( dirEngName!=null ) {
            paint.setTextSize(20f);
            canvas.drawText(dirEngName, rectF.centerX(), rectF.centerY()+40, paint);
        }


        RotateAnimation ra = new RotateAnimation(curDegree, -degree
                , Animation.ABSOLUTE, rectF.centerX(), Animation.ABSOLUTE, rectF.centerY());
        curDegree = -degree;
        //设置动画持续时间
        ra.setDuration(200);
        //运行动画
        startAnimation(ra);
    }

    private void prapareDrawing() {
        backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.compass_compass_bg);
        compassBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.compass_point);

        float avaliableWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        float avaliableHeight = getHeight() - getPaddingBottom() - getPaddingTop();
        float backBitmapWidth = backBitmap.getWidth();
        float compasBitmapWidth = compassBitmap.getWidth();
        float backBitmapHeight = backBitmap.getHeight();
        float compassBitmapHeight = compassBitmap.getHeight();
        float left = getPaddingLeft();
        float top = getPaddingTop();
//        top = top + avaliableHeight/2 - backBitmapHeight/2;

        float scale = Math.min(avaliableWidth/(backBitmapWidth*1.0f), avaliableHeight/(backBitmapHeight*1.0f));
        Log.d(TAG, "prapareDrawing: " + "scale: " + scale);
        backMatrix = new Matrix();
        compassMatrix = new Matrix();
        float comLeft = backBitmapWidth/2 - compasBitmapWidth/2;
        float comTop = backBitmapHeight/2 - compassBitmapHeight/2;
        compassMatrix.postTranslate(comLeft, comTop);
        if( rotateView!=null ) {
            Log.d("test", "rotateView is not null!");
            rotateView.setMatrix(compassMatrix);
            rotateView.postInvalidate();
        }

        rectF = new RectF(left, top, left+backBitmapWidth, top+backBitmapHeight);
//        rectF = new RectF();
        backMatrix.mapRect(rectF);

        shader = new BitmapShader(compassBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(backMatrix);
    }

    public float getDegree() {
        return degree;
    }

    public void setRotateView(MyRotateView rotateView) {
        this.rotateView = rotateView;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public void setDirEngName(String dirEngName) {
        this.dirEngName = dirEngName;
    }
}
