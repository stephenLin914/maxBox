package com.example.ubuntu.mymaxbox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.view.ProtractorValueView;
import com.example.ubuntu.mymaxbox.view.ProtractorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProtractorActivity extends AppCompatActivity {
    private static final String TAG = "ProtractorActivity";

    private ProtractorView mProtractor1;
    private ProtractorView mProtractor2;
    private ProtractorValueView mProtractorValue;
    private ImageView mProtractorCameraBtn;
    private TextureView mProtractorCameraView;

    private CameraManager mCameraManager;
    private CaptureRequest mCaptureRequest;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CameraCharacteristics mCameraCharacteristics;
    private CameraDevice mCameraDevice;
    private CameraCaptureSession mPreviewSession;
    private String mCameraId = "0";//通常0代表后摄，1代表前摄
    private ImageReader imageReader;//获取摄像头的图像数据

    private boolean isBtnPressed;

    private float pointX1;
    private float pointY1;
    private float pointX2;
    private float pointY2;
    private float protractorX;
    private float protractorY;
    private double dis1;
    private double dis2;
    private double dis3;


    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            ProtractorActivity.this.mCameraDevice = camera;
            takePreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            ProtractorActivity.this.mCameraDevice.close();
            ProtractorActivity.this.mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protractor);

        mProtractor1 = findViewById(R.id.protractor1);
        mProtractor2 = findViewById(R.id.protractor2);
        mProtractorValue = findViewById(R.id.protractor_value);
        mProtractorCameraBtn = findViewById(R.id.protractor_camera_btn);
        mProtractorCameraView = findViewById(R.id.protractor_camera_view);

//        setProtractorValue();
//        double cosDegree = (dis1*dis1+dis2*dis2-dis3*dis3)/(2*dis1*dis2);
//        String degreeValue = String.valueOf(Math.acos(cosDegree)*(180/Math.PI));
//        degreeValue = degreeValue.substring(0, degreeValue.indexOf(".")+2)+"°";
        mProtractorValue.setProtractorValue("84.0°");
        mProtractorValue.invalidate();

        mProtractorCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(ProtractorActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(ProtractorActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                }
                if( !isBtnPressed ) {
                    mProtractorCameraBtn.setImageResource(R.drawable.btn_mir_cam_pressed);
                    openCamera();
                } else {
                    mProtractorCameraBtn.setImageResource(R.drawable.btn_mir_cam_normal);
                    stopCamera();
                }
                isBtnPressed = !isBtnPressed;
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if( grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED ) {
                    try {
                        mCameraManager.openCamera(mCameraId, stateCallback, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        setProtractorValue();
        float k = (protractorY-y)/(protractorX-x);
        if( (x-pointX1)*(x-pointX1) + (y-pointY1)*(y-pointY1) <=
                (x-pointX2)*(x-pointX2) + (y-pointY2)*(y-pointY2)) {
            mProtractor1.setPointX(x);
            mProtractor1.setPointY(y);
            mProtractor1.setK(k);
            mProtractor1.invalidate();
        } else {
            mProtractor2.setPointX(x);
            mProtractor2.setPointY(y);
            mProtractor2.setK(k);
            mProtractor2.invalidate();
        }
        double cosDegree = (dis1*dis1+dis2*dis2-dis3*dis3)/(2*dis1*dis2);
        String degreeValue = String.valueOf(Math.acos(cosDegree)*(180/Math.PI));
        degreeValue = degreeValue.substring(0, degreeValue.indexOf(".")+2)+"°";
        mProtractorValue.setProtractorValue(degreeValue);
        mProtractorValue.invalidate();
//        Log.d(TAG, "degree: " + degree);
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void setProtractorValue() {

        pointX1 = mProtractor1.getPointX();
        pointY1 = mProtractor1.getPointY();
        pointX2 = mProtractor2.getPointX();
        pointY2 = mProtractor2.getPointY();
        protractorX = mProtractor1.getProtractorX();
        protractorY = mProtractor1.getProtractorY();

        dis1 = Math.sqrt((protractorX-pointX1)*(protractorX-pointX1) + (protractorY-pointY1)*(protractorY-pointY1));
        dis2 = Math.sqrt((protractorX-pointX2)*(protractorX-pointX2) + (protractorY-pointY2)*(protractorY-pointY2));
        dis3 = Math.sqrt((pointX1-pointX2)*(pointX1-pointX2) + (pointY1-pointY2)*(pointY1-pointY2));
    }


    private void openCamera() {
        mProtractorCameraView.setVisibility(View.VISIBLE);
        mCameraManager = (android.hardware.camera2.CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //设置摄像头特性参数
        setCameraCharacteristics(mCameraManager);

        //打开摄像头
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            try {
                mCameraManager.openCamera(mCameraId, stateCallback, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopCamera() {
        if(mCameraDevice!=null){
            mCameraDevice.close();
            mCameraDevice=null;
        }
        mProtractorCameraView.setVisibility(View.INVISIBLE);
    }

    //摄像头预览
    private void takePreview() {
        SurfaceTexture mSurfaceTexture = mProtractorCameraView.getSurfaceTexture();
        //设置TextureView的缓冲区大小
        mSurfaceTexture.setDefaultBufferSize(mProtractorCameraView.getWidth(), mProtractorCameraView.getHeight());
        //获取Surface显示预览数据
        Surface mSurface = new Surface(mSurfaceTexture);
        try {
            //创建预览请求
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 设置自动对焦模式
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //设置Surface作为预览数据的显示界面
            mCaptureRequestBuilder.addTarget(mSurface);
            //创建相机捕获会话，第一个参数是捕获数据的输出Surface列表，第二个参数是CameraCaptureSession的状态回调接口，当它创建好后会回调onConfigured方法，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            mCameraDevice.createCaptureSession(Arrays.asList(mSurface,imageReader.getSurface()),new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try {
                        //开始预览
                        mCaptureRequest = mCaptureRequestBuilder.build();
                        mPreviewSession = session;
                        //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
                        mPreviewSession.setRepeatingRequest(mCaptureRequest, null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void setCameraCharacteristics(CameraManager manager) {

        try {
            //获取指定摄像头的特性参数
            mCameraCharacteristics = manager.getCameraCharacteristics(mCameraId);
            //获取摄像头支持的配置属性
            StreamConfigurationMap streamConfigurationMap = mCameraCharacteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
            );
            //获取摄像头支持的最大属性
            Size largest = Collections.max(
                    Arrays.asList(streamConfigurationMap.getOutputSizes(ImageFormat.JPEG)), new ProtractorActivity.CompareSizesByArea()
            );
            //获取摄像头图像数据
            imageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, 2);
//
//            //设置获取图片的监听
//            imageReader.setOnImageAvailableListener(imageAvailableListener,null);


        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }




    class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // 强转为long保证不会发生溢出
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

}
