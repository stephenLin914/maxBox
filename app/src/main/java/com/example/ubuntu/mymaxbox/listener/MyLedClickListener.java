package com.example.ubuntu.mymaxbox.listener;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.entity.Tool;
import com.example.ubuntu.mymaxbox.view.MyLedView;

public class MyLedClickListener implements View.OnClickListener {
    private static final String TAG = "MyLedClickListener";

    private int[] heartPosX1 = {8, 7, 6, 5, 4, 4, 4};
    private int[] heartPoxY1 = {13, 12, 12, 13, 14, 15, 16};
    private int[] heartPosX2 = {8, 7, 6, 5, 4, 4, 4, 5, 6, 7, 8};
    private int[] heartPoxY2 = {13, 12, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private int[] heartPosX3 = {8, 7, 6, 5, 4, 4, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private int[] heartPoxY3 = {13, 12, 12, 13, 14, 15, 16, 17, 18, 19, 20, 20, 19, 18, 17, 16};
    private int[] heartPosX4 = {8, 7, 6, 5, 4, 4, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 13, 13, 12, 11, 10, 9};
    private int[] heartPoxY4 = {13, 12, 12, 13, 14, 15, 16, 17, 18, 19, 20, 20, 19, 18, 17, 16, 15, 14, 13, 12, 12, 13};

    private MyLedView imgContent;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    imgContent.invalidate();
            }
            super.handleMessage(msg);
        }
    };

    public MyLedClickListener(MyLedView imgContent) {
        this.imgContent = imgContent;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        ImageView imgRes = v.findViewById(R.id.led_img);
        String name = (String) imgRes.getTag();
        switch (name) {
            case "心":
                displayHeart();
                break;
            default:
                break;
        }
    }

    private void displayHeart(){
        new Thread(new RefreshThread()).start();
    }

    class RefreshThread implements Runnable {
        @Override
        public void run() {
            Message message;
            try {
                while( true ) {
                    imgContent.setDrawPosXData(heartPosX1);
                    imgContent.setDrawPosYData(heartPoxY1);
                    message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    Thread.sleep(1000);
                    imgContent.setDrawPosXData(heartPosX2);
                    imgContent.setDrawPosYData(heartPoxY2);
                    message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    Thread.sleep(1000);
                    imgContent.setDrawPosXData(heartPosX3);
                    imgContent.setDrawPosYData(heartPoxY3);
                    message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    Thread.sleep(1000);
                    imgContent.setDrawPosXData(heartPosX4);
                    imgContent.setDrawPosYData(heartPoxY4);
                    message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    Thread.sleep(1000);
                    imgContent.setDrawPosXData(null);
                    imgContent.setDrawPosYData(null);
                    message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

