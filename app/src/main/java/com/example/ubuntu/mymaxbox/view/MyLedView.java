package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ubuntu.mymaxbox.R;

import java.util.concurrent.TimeUnit;

public class MyLedView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "MyLedView";

    private ImageView ledRes;
    private Matrix matrix = new Matrix();
    private Paint paint = new Paint();
    private RectF drawableRect;
    private Shader shader;
    private Bitmap img;

    private int[] drawPosXData;

    public void setDrawPosXData(int[] drawPosXData) {
        this.drawPosXData = drawPosXData;
    }

    public void setDrawPosYData(int[] drawPosYData) {
        this.drawPosYData = drawPosYData;
    }

    private int[] drawPosYData;

    public MyLedView(Context context) {
        super(context);
    }

    public MyLedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLedRes(ImageView ledRes) {
        this.ledRes = ledRes;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        if( ledRes==null ) {
//            super.onDraw(canvas);
//            return;
//        }
//        String name = (String) ledRes.getTag();
//        BitmapDrawable drawable = (BitmapDrawable) ledRes.getDrawable();
//        img = drawable.getBitmap();
//
//        prepareDrawing();
        if( drawPosXData==null || drawPosYData==null ) {
            return;
        }
        Bitmap ledDot = BitmapFactory.decodeResource(getResources(), R.drawable.led_dot2);
        float ledWidth = 40;
        float ledHeight = 40;
        for (int i=0; i<drawPosXData.length; ++i ) {
            canvas.drawBitmap(ledDot, ledWidth*drawPosXData[i], ledHeight*drawPosYData[i], paint);
        }
//        Log.d(TAG, "ledWidth：" + ledWidth + " ledHeight:" + ledHeight);
//        switch (name) {
//            case "狗头" :
//                canvas.drawRect(drawableRect, paint);
//                break;
////            canvas.drawBitmap(img, 30f, 30f, paint);
//            case "心":
//                try {
////                    while( true ) {
//                        for( int i=0; i<heartPosX1.length; ++i ) {
//                            float left = ledWidth*heartPosX1[i];
//                            float top = ledHeight*heartPoxY1[i];
//                            Log.d(TAG, "left: " + left + " top: " + top);
//                            canvas.drawBitmap(ledDot, left, top, paint);
//                        }
//                        Thread.sleep(5000);
////                        TimeUnit.MILLISECONDS.sleep(1000);
//                        for( int i=0; i<heartPosX2.length; ++i ) {
//                            float left = ledWidth*heartPosX2[i];
//                            float top = ledHeight*heartPoxY2[i];
//                            canvas.drawBitmap(ledDot, left, top, paint);
//                        }
//                        Thread.sleep(5000);
//                        for( int i=0; i<heartPosX3.length; ++i ) {
//                            float left = ledWidth*heartPosX3[i];
//                            float top = ledHeight*heartPoxY3[i];
//                            canvas.drawBitmap(ledDot, left, top, paint);
//                        }
//                        Thread.sleep(5000);
//                        for( int i=0; i<heartPosX4.length; ++i ) {
//                            float left = ledWidth*heartPosX4[i];
//                            float top = ledHeight*heartPoxY4[i];
//                            canvas.drawBitmap(ledDot, left, top, paint);
//                        }
////                        TimeUnit.MILLISECONDS.sleep(1000);
////                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
////                        TimeUnit.MILLISECONDS.sleep(1000);
////                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
    }

    private void prepareDrawing() {
        shader = new BitmapShader(img, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        paint.setAntiAlias(true);
        paint.setShader(shader);

        int avaliableWidth = getWidth()-getPaddingLeft()+getPaddingRight();
        int avaliableHeight = getHeight()-getPaddingTop()+getPaddingBottom();
        int left = getPaddingLeft();
        int top = getPaddingTop();

        drawableRect = new RectF(left, top, left+avaliableWidth, top+avaliableHeight);

        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
//        float scale = Math.max((avaliableWidth/(imgWidth*1.0f)), (avaliableHeight/(imgHeight*1.0f)));
        matrix.setScale(avaliableWidth/(imgWidth*1.0f), avaliableHeight/(imgHeight*1.0f));
        matrix.postTranslate(left, top);

        shader.setLocalMatrix(matrix);


    }
}
