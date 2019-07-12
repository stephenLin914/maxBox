package com.example.ubuntu.mymaxbox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MagnifierActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private static final String TAG = "MagnifierActivity";


    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private String mCameraId = "0";//通常0代表后摄，1代表前摄
    private TextureView cameraView;
    private ImageReader imageReader;//获取摄像头的图像数据
    private Size previewSize;//摄像头预览尺寸
    private int mWidth;
    private int mHeight;
    private CaptureRequest.Builder mCaptureRequestBuilder,captureRequestBuilder;
    private CaptureRequest mCaptureRequest;
    private CameraCaptureSession mPreviewSession;
    private CameraCharacteristics mCameraCharacteristics;


    private SurfaceTexture mSurfaceTexture;


    private float maxZoom;
    private Rect zoomRect;

    private boolean isOn;
    private String cameraId;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static
    {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private SeekBar mSeekBar;
    private ImageView mSwitchFlashLight;


    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            mSurfaceTexture = surface;
            MagnifierActivity.this.mWidth = width;
            MagnifierActivity.this.mHeight = height;
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            stopCamera();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };
    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            MagnifierActivity.this.mCameraDevice = camera;
            takePreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            MagnifierActivity.this.mCameraDevice.close();
            MagnifierActivity.this.mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnifier);

        cameraView = findViewById(R.id.magnifier_camera);
        cameraView.setSurfaceTextureListener(surfaceTextureListener);


        mSeekBar = findViewById(R.id.magnifier_seek_bar);

        mSeekBar.setOnSeekBarChangeListener(this);
        mSwitchFlashLight = findViewById(R.id.magnifier_flash_light);
        mSwitchFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitchFlashLight.setImageResource(R.drawable.btn_magn_light_pressed);
                String[] ids  = new String[0];
                try {
                    ids = mCameraManager.getCameraIdList();
                    for (String id : ids) {
                        CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                        //查询该摄像头组件是否包含闪光灯
                        Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                        Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                        if (flashAvailable != null && flashAvailable
                                && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                            //打开或关闭手电筒
                            cameraId = id;
                            break;
                        }
                    }
                    if( !isOn ) {
//                        openFlash();
                        mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_TORCH);
                        //开始预览
                        mCaptureRequest = mCaptureRequestBuilder.build();
                        //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
                        mPreviewSession.setRepeatingRequest(mCaptureRequest, null, null);
                        mSwitchFlashLight.setImageResource(R.drawable.btn_magn_light_pressed);
                        isOn = !isOn;
                    } else {
//                        closeFlash();
                        mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
                        //开始预览
                        mCaptureRequest = mCaptureRequestBuilder.build();
                        //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
                        mPreviewSession.setRepeatingRequest(mCaptureRequest, null, null);
                        mSwitchFlashLight.setImageResource(R.drawable.btn_magn_light_normal);
                        isOn = !isOn;
                    }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void closeFlash() throws CameraAccessException {
        mCameraManager.setTorchMode(cameraId, false);

    }

    private void openFlash() throws CameraAccessException {
        //打开或关闭手电筒
        mCameraManager.setTorchMode(cameraId, true);
    }

    //摄像头预览
    private void takePreview() {
//        SurfaceTexture mSurfaceTexture = cameraView.getSurfaceTexture();
        //设置TextureView的缓冲区大小
        mSurfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
        //获取Surface显示预览数据
        Surface mSurface = new Surface(mSurfaceTexture);
        try {
            //创建预览请求
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//            mCaptureRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
//
            //曝光
//            mCaptureRequestBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, 10);
//            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON);
//            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_LOCK, true);
//            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, minExposure);
            //数字变焦Rect
            zoomRect = createZoomReact(1.0f);
            if( zoomRect != null ) {
                mCaptureRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, zoomRect);
            }
            // 设置自动对焦模式
            mCaptureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //设置Surface作为预览数据的显示界面
            mCaptureRequestBuilder.addTarget(mSurface);
//            mCaptureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, 180);
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

    private void stopCamera() {
        if(mCameraDevice!=null){
            mCameraDevice.close();
            mCameraDevice=null;
        }
    }

    private void openCamera() {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
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

    private void setCameraCharacteristics(CameraManager manager) {

        try {
            //获取指定摄像头的特性参数
            mCameraCharacteristics = manager.getCameraCharacteristics(mCameraId);
            //获取摄像头支持的配置属性
            StreamConfigurationMap streamConfigurationMap = mCameraCharacteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
            );


            //数字变焦值；
            maxZoom = mCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
            Log.d(TAG, "maxZoom: " + maxZoom);
            //获取摄像头支持的最大属性
            Size largest = Collections.max(
                    Arrays.asList(streamConfigurationMap.getOutputSizes(ImageFormat.JPEG)), new MagnifierActivity.CompareSizesByArea()
            );
            //获取摄像头图像数据
            imageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, 2);

            //获取最佳预览尺寸
            previewSize = chooseOptimalSize(
                    streamConfigurationMap.getOutputSizes(SurfaceTexture.class), mWidth, mHeight, largest);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    //收集摄像头支持的大过Surface的分辨率
    private Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices)
        {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height)
            {
                bigEnough.add(option);
            }
        }
        // 如果找到多个预览尺寸，获取其中面积最小的
        if (bigEnough.size() > 0)
        {
            return Collections.min(bigEnough, new MagnifierActivity.CompareSizesByArea());
        }
        else
        {
            //没有合适的预览尺寸
            return choices[0];
        }
    }



    /**
     *      seekbar
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int currentZoom = 1;
        int t = progress;
        int lev = (int) (100/maxZoom);
        while( t > 1 ) {
            ++currentZoom;
            t = t-lev;
        }
        Rect rect = createZoomReact(currentZoom);
        mCaptureRequestBuilder.set(CaptureRequest.SCALER_CROP_REGION, rect);
        mCaptureRequest = mCaptureRequestBuilder.build();
        try {
            mPreviewSession.setRepeatingRequest(mCaptureRequest, null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private Rect createZoomReact(float currentZoom) {
        Rect zoomRect = null;
        if( currentZoom ==0 ) {
            return null;
        }
        Rect rect = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
        if( rect==null ) {
            return null;
        }
        float ratio = 1.0f/currentZoom;
        int cropWidth = rect.width()-Math.round((float)rect.width() * ratio);
        int cropHeight = rect.height()-Math.round((float)rect.height() * ratio);
        zoomRect = new Rect(cropWidth/2, cropHeight/2, rect.width()-cropWidth/2, rect.height()-cropHeight/2);
        return zoomRect;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mCameraDevice!=null) {
            stopCamera();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // 强转为long保证不会发生溢出
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
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
}
