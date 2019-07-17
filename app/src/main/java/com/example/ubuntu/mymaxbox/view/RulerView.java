package com.example.ubuntu.mymaxbox.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.ubuntu.mymaxbox.R;

public class RulerView extends View {
    private static final String TAG = "RulerView";

    private Bitmap mBitmap;
    private float ctrlX;
    private float ctrlY1;
    private float ctrlY2;

    private float dpi;

    private final float lineLength = 20;


    public RulerView(Context context) {
        super(context);
    }

    public RulerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initRulerView();
    }

    public RulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RulerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_MOVE: {
                float pointY = event.getY();
                if( Math.abs(pointY-ctrlY1) < Math.abs(pointY-ctrlY2) ) {
                    ctrlY1 = pointY;
                } else {
                    ctrlY2 = pointY;
                }
                break;
            }
            default:break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
//        Log.d(TAG, "ctrlX: " + ctrlX + "    :" + ((getRight()-getLeft())/2-mBitmap.getWidth()/2));

        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);

        float left = getLeft();
        float right = getRight();
        float lineY1 = ctrlY1+mBitmap.getWidth()/2;
        float lineY2 = ctrlY2+mBitmap.getWidth()/2;
        float curX = left;
        while( curX < right ) {
            canvas.drawLine(curX, lineY1, curX+lineLength, lineY1, paint);
            canvas.drawLine(curX, lineY2, curX+lineLength, lineY2, paint);
            curX += lineLength + 10;
        }


        canvas.drawBitmap(mBitmap, ctrlX, ctrlY1, paint);
        canvas.drawBitmap(mBitmap, ctrlX, ctrlY2, paint);

        canvas.rotate(90);
        float textX = (getBottom()-getTop())/2-50;
        float cmTextY = getRight()-70;
        float inchTextY = getLeft()+20;
        String cmValue = String.valueOf(Math.abs(ctrlY1-ctrlY2)/(dpi/2.54f));
        cmValue = cmValue.substring(0,cmValue.indexOf(".")+2);
        String inchValue = String.valueOf(Math.abs(ctrlY1-ctrlY2)/dpi);
        inchValue = inchValue.substring(0,inchValue.indexOf(".")+2);
        paint.setColor(Color.BLUE);
        paint.setTextSize(50);
        canvas.drawText(cmValue+"cm", textX,-cmTextY, paint);
        canvas.drawText(inchValue+"inch", textX, -inchTextY, paint);
    }

    private void initRulerView() {

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tool_ruler_ctrl);

        dpi = getResources().getDisplayMetrics().ydpi;
//        Log.d(TAG, "dpi: " + dpi);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        ctrlX =  (getRight()-getLeft())/2-mBitmap.getWidth()/2;
        ctrlY1 =  getTop();
        ctrlY2 =  dpi/2.54f;
    }
}
