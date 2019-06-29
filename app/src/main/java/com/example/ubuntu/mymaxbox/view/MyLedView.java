package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class MyLedView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "MyLedView";

    private ImageView ledRes;
    private Matrix matrix = new Matrix();
    private Paint paint = new Paint();
    private RectF drawableRect;
    private Shader shader;
    private Bitmap img;

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
        if( ledRes==null ) {
            super.onDraw(canvas);
            return;
        }
        String name = (String) ledRes.getTag();
        BitmapDrawable drawable = (BitmapDrawable) ledRes.getDrawable();
        img = drawable.getBitmap();

        prepareDrawing();

        switch (name) {
            case "狗头" :
                canvas.drawRect(drawableRect, paint);
//            canvas.drawBitmap(img, 30f, 30f, paint);
        }
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
