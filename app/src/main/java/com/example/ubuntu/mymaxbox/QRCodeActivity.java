package com.example.ubuntu.mymaxbox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubuntu.mymaxbox.android.CaptureActivity;


public class QRCodeActivity extends AppCompatActivity {


    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

//    private Button btn_scan;
    private TextView tv_scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        tv_scanResult = (TextView) findViewById(R.id.tv_scanResult);
//        btn_scan = (Button) findViewById(R.id.btn_scan);

        Intent intent = getIntent();
        String data = intent.getStringExtra(DECODED_CONTENT_KEY);
        tv_scanResult.setText(data);
//        btn_scan.setOnClickListener(this);
    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_scan:
//                //动态权限申请
//                if (ContextCompat.checkSelfPermission(QRCodeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(QRCodeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
//                } else {
//                    goScan();
//                }
//                break;
//            default:
//                break;
//        }
//    }

    /**
     * 跳转到扫码界面扫码
     */
//    private void goScan(){
//        Intent intent = new Intent(QRCodeActivity.this, CaptureActivity.class);
//        startActivityForResult(intent, REQUEST_CODE_SCAN);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    goScan();
//                } else {
//                    Toast.makeText(this, "你拒绝了权限申请，可能无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 扫描二维码/条码回传
//        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
//            if (data != null) {
//                //返回的文本内容
//                String content = data.getStringExtra(DECODED_CONTENT_KEY);
//                //返回的BitMap图像
//                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
//
//                tv_scanResult.setText("你扫描到的内容是：" + content);
//            }
//        }
//    }
}
