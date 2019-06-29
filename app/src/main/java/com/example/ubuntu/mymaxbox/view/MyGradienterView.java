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
import android.widget.ImageView;

import com.example.ubuntu.mymaxbox.R;

public class MyGradienterView extends View {
    private static final String TAG = "MyGradienterView";

    private Matrix backMatrix = new Matrix();
    private Paint paint = new Paint();
//    private Shader shader;
    Bitmap backBitmap;
    Bitmap ballBitmap;

    private float ballX;
    private float ballY;
    private float centerX;
    private float centerY;
    private float x;
    private float y;
    private boolean first = true;

    private float ballWidth = -1;
    private float radius;

    public MyGradienterView(Context context) {
        super(context);
    }

    public MyGradienterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGradienterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyGradienterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    RectF rectF;

    @Override
    protected void onDraw(Canvas canvas) {
        backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tools_level_bg_circle_all);
        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tools_level_icon_circle);
        ballWidth = ballBitmap.getWidth();

        prepareDraw();


        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setColor(Color.RED);

        canvas.drawBitmap(backBitmap, backMatrix, paint);
        canvas.drawBitmap(ballBitmap, ballX, ballY, paint);

//        canvas.drawRect(rectF, p);
//        canvas.drawPoint(centerX, centerY, p);
//        canvas.drawCircle(centerX, centerY, radius, p);
//        Log.d(TAG, "onDraw: " + "centerX: " + centerX + " centerY: " + centerY + " radius: " + radius);
    }



    private void prepareDraw() {

//        shader = new BitmapShader(backBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        paint.setAntiAlias(true);
//        paint.setShader(shader);

        int avaliableWidth = getWidth()-getPaddingLeft()+getPaddingRight();
        int avaliableHeight = getHeight()-getPaddingTop()+getPaddingBottom();
        int left = getPaddingLeft();
        int top = getPaddingTop();

        int imgWidth = backBitmap.getWidth();
        int imgHeight = backBitmap.getHeight();
        float scale = Math.min(avaliableWidth/(imgWidth*1.0f), avaliableHeight/(imgHeight*1.0f));

        backMatrix.setScale(scale, scale);
        backMatrix.postTranslate(left, top);

        rectF = new RectF(left, top, left+avaliableWidth, top+avaliableHeight);
        backMatrix.mapRect(rectF);

        radius = Math.min(rectF.width()/2, rectF.height()/2);
        float adjustWidth;
        float adjustHeight;
        if( avaliableWidth/(imgWidth*1.0f) < avaliableHeight/(imgHeight*1.0f) ) {
            adjustWidth = (getWidth()-rectF.right)/2;
            adjustHeight = (getWidth()-rectF.bottom)/2;
        } else {
            adjustWidth = (getWidth()-rectF.right)/2;
            adjustHeight = (getWidth()-rectF.bottom)/2;
        }
        if( first ) {
            ballX = rectF.centerX() + adjustWidth - ballBitmap.getWidth()/2;
            ballY = rectF.centerY() + adjustHeight - ballBitmap.getHeight()/2;
            centerX = rectF.centerX() + adjustWidth;
            centerY = rectF.centerY() + adjustHeight;
            x = ballX;
            y = ballY;

            Log.d(TAG, "centerX: " + centerX + " centerY: " + centerY);
            first = false;
        }
    }

    public void setBallPosition(float x, float y) {
        this.ballX = x;
        this.ballY = y;
    }

    public float getCenterX() {
        return centerX;
    }
    public float getCenterY() {
        return centerY;
    }

    public float getBallX() {
        return ballX;
    }

    public float getBallY() {
        return ballY;
    }
    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
    public float getRadius() {
        return radius;
    }

    public float getBallWidth() {
        return ballWidth;
    }
}
