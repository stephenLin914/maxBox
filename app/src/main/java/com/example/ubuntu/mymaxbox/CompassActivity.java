package com.example.ubuntu.mymaxbox;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.view.MyCompassView;
import com.example.ubuntu.mymaxbox.view.MyRotateView;

import java.util.ArrayList;
import java.util.List;


public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "CompassActivity";

    private MyCompassView compassView;
    private MyRotateView rotateView;
    //Sensor管理器
    private SensorManager sensorManager;
    private Direction[] dirs;
    private TextView dirTextView;


    public LocationClient mLocationClient;
    private TextView positionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        setContentView(R.layout.activity_compass);

        compassView = findViewById(R.id.my_compass_view);
        dirTextView = findViewById(R.id.dir_name);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        dirs = new Direction[] {
                new Direction(345, 360,"北", "N"),
                new Direction(0, 15,"北", "N"),
                new Direction(16, 70,"东北", "EN"),
                new Direction(71, 105,"东", "E"),
                new Direction(106, 165,"东南", "ES"),
                new Direction(166, 195,"南", "S"),
                new Direction(196, 255,"西南", "WS"),
                new Direction(256, 285,"西", "W"),
                new Direction(286, 345,"西北","WN")
        };
        positionText = findViewById(R.id.position_text_view);
        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED ) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if( !permissionList.isEmpty() ) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            requestLocation();
        }
    }

    private void requestLocation() {
        mLocationClient.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //为系统注册方向传感器监听器
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:

                float degree = event.values[0];
//                Log.d("test", "degree: " + degree);
                String dirName = null;
                String dirEngName = null;
                for( int i=0; i<dirs.length; ++i ) {
                    if( degree>dirs[i].start && degree<dirs[i].end ) {
                        dirName = dirs[i].name;
                        dirEngName = dirs[i].engName;
                    }
                }
                dirTextView.setText(dirName);
                compassView.setDegree(degree);
                compassView.setDirEngName(dirEngName);
                compassView.invalidate();

                break;
            default:
                break;
        }

    }
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            runOnUiThread(new Thread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度:").append(bdLocation.getLatitude());
                    currentPosition.append("经度:").append(bdLocation.getLongitude())
                            .append("\n");
                    currentPosition.append("国家：").append(bdLocation.getCountry());
                    currentPosition.append("省：").append(bdLocation.getProvince());
                    currentPosition.append("市：").append(bdLocation.getCity());
                    currentPosition.append("区：").append(bdLocation.getDistrict());
                    currentPosition.append("街道：").append(bdLocation.getStreet())
                            .append("\n");
                    currentPosition.append("定位方式：");
                    if( bdLocation.getLocType() == BDLocation.TypeGpsLocation ) {
                        currentPosition.append("GPS");
                    } else if( bdLocation.getLocType() == BDLocation.TypeNetWorkLocation ) {
                        currentPosition.append("网络");
                    }
                    positionText.setText(currentPosition);
                }
            }));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        //取消注册
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        //取消注册
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    class Direction {
        int start;
        int end;
        String name;
        String engName;

        public Direction(int start, int end, String name, String engName) {
            this.start = start;
            this.end = end;
            this.name = name;
            this.engName = engName;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if( grantResults.length>0 ) {
                    for( int result : grantResults ) {
                        if( result != PackageManager.PERMISSION_GRANTED ) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
