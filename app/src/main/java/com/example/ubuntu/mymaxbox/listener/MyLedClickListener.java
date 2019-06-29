package com.example.ubuntu.mymaxbox.listener;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.entity.Tool;
import com.example.ubuntu.mymaxbox.view.MyLedView;

public class MyLedClickListener implements View.OnClickListener {
    private static final String TAG = "MyLedClickListener";

    private MyLedView imgContent;

    public MyLedClickListener(MyLedView imgContent) {
        this.imgContent = imgContent;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        ImageView imgRes = v.findViewById(R.id.led_img);
        imgContent.setLedRes(imgRes);
        imgContent.invalidate();
    }
}
