package com.example.ubuntu.mymaxbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.view.MyGradienterView;


public class GradienterActivity extends AppCompatActivity implements SensorEventListener{
    private static final String TAG = "GradienterActivity";

    //定义水平仪能处理的最大倾斜角度，超过该角度气泡直接位于边界
    private int MAX_ANGLE = 30;
    //定义Sensor管理器
    SensorManager sensorManager;
    private MyGradienterView gradienterView;


    private ImageView gradienterLock;
    private TextView pitch;
    private TextView roll;
    private boolean locked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradienter);

        gradienterView = findViewById(R.id.myGradienterView);
        //获取传感器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        gradienterLock = findViewById(R.id.gradienter_lock);
        pitch = findViewById(R.id.gradienter_pitch);
        roll = findViewById(R.id.gradienter_roll);

        gradienterLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !locked ) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tools_level_lock_on);
                    gradienterLock.setImageBitmap(bitmap);
                    //取消注册
                    sensorManager.unregisterListener(GradienterActivity.this);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tools_level_lock_off);
                    gradienterLock.setImageBitmap(bitmap);
                    //注册
                    sensorManager.registerListener(GradienterActivity.this,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                            SensorManager.SENSOR_DELAY_GAME);
                }
                locked = !locked;
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float values[] = event.values;
        //获取传感器的类型
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                //获取与Y轴的夹角
                float yAngle = values[1];
                //获取与Z轴的夹角
                float zAngle = values[2];
                String p = String.valueOf(yAngle);
                String r = String.valueOf(yAngle);
                pitch.setText(p.substring(0, p.indexOf(".")+2)+"°");
                roll.setText(r.substring(0, r.indexOf(".")+2)+"°");

//                Log.d(TAG, "onSensorChanged: " + "yAngle: " + yAngle + " zAngle: " + zAngle);


                //气泡位于中间时（水平仪完全水平）
                float x = gradienterView.getX();
                float y = gradienterView.getY();
                float radius = gradienterView.getRadius();
                float ballWidth = gradienterView.getBallWidth();
                float centerX = gradienterView.getCenterX();
                float centerY = gradienterView.getCenterY();

                float maxDistance = (radius-ballWidth/2)*(radius-ballWidth/2);

//                Log.d(TAG, "centerX: " + x + " centerY: " + y);
                //如果与Z轴的倾斜角还在最大角度之内
                if (Math.abs(zAngle) <= MAX_ANGLE) {
                    //根据与Z轴的倾斜角度计算X坐标轴的变化值
                    float deltaX = radius * zAngle / MAX_ANGLE;
                    x += deltaX;
                }
                //如果与Z轴的倾斜角已经大于MAX_ANGLE，气泡应到最左边
                else if (zAngle > MAX_ANGLE) {
                    x = gradienterView.getX() + radius - ballWidth/2;
                }
                //如果与Z轴的倾斜角已经小于负的Max_ANGLE,气泡应到最右边
                else if( -zAngle > MAX_ANGLE ){
                    x = centerX - radius;
                }

                //如果与Y轴的倾斜角还在最大角度之内
                if (Math.abs(yAngle) <= MAX_ANGLE) {
                    //根据与Z轴的倾斜角度计算X坐标轴的变化值
                    float deltaY = radius * yAngle / MAX_ANGLE;
                    y += deltaY;
                }
                //如果与Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
                else if (yAngle > MAX_ANGLE) {
                    y = gradienterView.getX() + radius - ballWidth/2;
                }
                //如果与Y轴的倾斜角已经小于负的Max_ANGLE,气泡应到最上边
                else {
                    y = centerY - radius;
                }
//                Log.d(TAG, "onSensorChanged: " + "yAngle: " + yAngle + " zAngle: " + zAngle
//                + " x: " + x + " y: " + y);
                //如果计算出来的X，Y坐标还位于水平仪的仪表盘之内，则更新水平仪气泡坐标
//                Log.d(TAG," x: " + x + " y: " + y + " centerX: " + centerX + " centerY: " + centerY);

                if (((gradienterView.getX()-x)*(gradienterView.getX()-x) +
                        (gradienterView.getY()-y)*(gradienterView.getY()-y)) <= maxDistance ) {
//                    Log.d(TAG, " dis: " + ((centerX-x)*(centerX-x) + (centerY-y)*(centerY-y)) + " distance: " + maxDistance);
                    gradienterView.setBallPosition(x, y);
                }

                //通知组件更新
                gradienterView.postInvalidate();
                //show.invalidate();
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onStop() {
        //取消注册
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onPause() {
        //取消注册
        sensorManager.unregisterListener(this);
        super.onPause();
    }

}
