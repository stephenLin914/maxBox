package com.example.ubuntu.mymaxbox;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;



public class FlashLightActivity extends AppCompatActivity {

    private ImageView flashLightView;
    private boolean isOn;
    private CameraManager cameraManager;
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        flashLightView = findViewById(R.id.flashLight_image);

        flashLightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] ids  = new String[0];
                try {
                    ids = cameraManager.getCameraIdList();
                    for (String id : ids) {
                        CameraCharacteristics c = cameraManager.getCameraCharacteristics(id);
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
                    openFlash();
                    flashLightView.setImageResource(R.drawable.flashlight_on);
                    isOn = !isOn;
                } else {
                    closeFlash();
                    flashLightView.setImageResource(R.drawable.flashlight_off);
                    isOn = !isOn;
                }
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void closeFlash() throws CameraAccessException {
        cameraManager.setTorchMode(cameraId, false);

    }

    private void openFlash() throws CameraAccessException {
        //打开或关闭手电筒
        cameraManager.setTorchMode(cameraId, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraManager = null;
    }
}
