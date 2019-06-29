package com.example.ubuntu.mymaxbox.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.ubuntu.mymaxbox.LedActivity;
import com.example.ubuntu.mymaxbox.R;
import com.example.ubuntu.mymaxbox.CompassActivity;
import com.example.ubuntu.mymaxbox.DecibelActivity;
import com.example.ubuntu.mymaxbox.FlashLightActivity;
import com.example.ubuntu.mymaxbox.GradienterActivity;
import com.example.ubuntu.mymaxbox.MagnifierActivity;
import com.example.ubuntu.mymaxbox.MirrorActivity;
import com.example.ubuntu.mymaxbox.ProtractorActivity;
import com.example.ubuntu.mymaxbox.QRCodeActivity;
import com.example.ubuntu.mymaxbox.ScaleActivity;
import com.example.ubuntu.mymaxbox.SizeTableActivity;
import com.example.ubuntu.mymaxbox.UnitActivity;
import com.example.ubuntu.mymaxbox.android.CaptureActivity;


public class MyToolOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        TextView toolName = v.findViewById(R.id.tool_name);
        String name = (String) toolName.getText();
        Intent intent = null;
        switch (name) {
            case "LED":
                intent = new Intent(context, LedActivity.class);
                context.startActivity(intent);
                break;
            case "二维码":
                intent = new Intent(context, CaptureActivity.class);
                context.startActivity(intent);
                break;
            case "分贝仪":
                intent = new Intent(context, DecibelActivity.class);
                context.startActivity(intent);
                break;
            case "水平仪":
                intent = new Intent(context, GradienterActivity.class);
                context.startActivity(intent);
                break;
            case "指南针":
                intent = new Intent(context, CompassActivity.class);
                context.startActivity(intent);
                break;
            case "手电筒":
                intent = new Intent(context, FlashLightActivity.class);
                context.startActivity(intent);
                break;
            case "镜子":
                intent = new Intent(context, MirrorActivity.class);
                context.startActivity(intent);
                break;
            case "放大镜":
                intent = new Intent(context, MagnifierActivity.class);
                context.startActivity(intent);
                break;
            case "刻度尺":
                intent = new Intent(context, ScaleActivity.class);
                context.startActivity(intent);
                break;
            case "量角器":
                intent = new Intent(context, ProtractorActivity.class);
                context.startActivity(intent);
                break;
            case "单位换算":
                intent = new Intent(context, UnitActivity.class);
                context.startActivity(intent);
                break;
            case "尺码表":
                intent = new Intent(context, SizeTableActivity.class);
                context.startActivity(intent);
                break;
            default:
                break;
        }
    }
}
