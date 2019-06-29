package com.example.ubuntu.mymaxbox;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.example.ubuntu.mymaxbox.adapter.MyPagerAdapter;
import com.example.ubuntu.mymaxbox.adapter.ToolAdapter;
import com.example.ubuntu.mymaxbox.entity.Tool;
import com.example.ubuntu.mymaxbox.listener.MyPageChangeListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private RadioButton radioButtonLeft;
    private RadioButton radioButtonRight;

    private ArrayList<View> pageViews;

    private ArrayList<Tool> leftTools = new ArrayList<Tool>();
    private ArrayList<Tool> rightTools = new ArrayList<Tool>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.viewPager);

        LayoutInflater layoutInflater = getLayoutInflater();
        View menuLeft = layoutInflater.inflate(R.layout.menu1, null);
        View menuRight = layoutInflater.inflate(R.layout.menu2, null);

        radioButtonLeft = findViewById(R.id.radio_btn_left);
        radioButtonRight = findViewById(R.id.radio_btn_right);
        radioButtonLeft.setChecked(true);

        pageViews = new ArrayList<View>();
        pageViews.add(menuLeft);
        pageViews.add(menuRight);

        mViewPager.setAdapter(new MyPagerAdapter(pageViews));
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new MyPageChangeListener(radioButtonLeft, radioButtonRight));


        initToolsData();
        ToolAdapter leftAdapter = new ToolAdapter(leftTools);
        RecyclerView leftTools = menuLeft.findViewById(R.id.menu_left);
        GridLayoutManager leftLayoutManager = new GridLayoutManager(this, 3);
        leftTools.setLayoutManager(leftLayoutManager);
        leftTools.setAdapter(leftAdapter);

        ToolAdapter rightAdapter = new ToolAdapter(rightTools);
        RecyclerView rightTools = menuRight.findViewById(R.id.menu_right);
        GridLayoutManager rightLayoutManager = new GridLayoutManager(this, 3);
        rightTools.setLayoutManager(rightLayoutManager);
        rightTools.setAdapter(rightAdapter);
    }

    private void initToolsData() {
        Tool led = new Tool(R.drawable.led_icon, "LED");
        leftTools.add(led);
        Tool QRcode = new Tool(R.drawable.qrcode_icon, "二维码");
        leftTools.add(QRcode);
        Tool decibel = new Tool(R.drawable.decibel_icon, "分贝仪");
        leftTools.add(decibel);
        Tool gradienter = new Tool(R.drawable.gradienter_icon, "水平仪");
        leftTools.add(gradienter);
        Tool compass = new Tool(R.drawable.compass_icon, "指南针");
        leftTools.add(compass);
        Tool flashLight = new Tool(R.drawable.flashlight_icon, "手电筒");
        leftTools.add(flashLight);

        Tool mirror = new Tool(R.drawable.mirror_icon, "镜子");
        rightTools.add(mirror);
        Tool magnifier = new Tool(R.drawable.magnifier_icon, "放大镜");
        rightTools.add(magnifier);
        Tool scale = new Tool(R.drawable.scale_icon, "刻度尺");
        rightTools.add(scale);
        Tool protractor = new Tool(R.drawable.protractor_icon, "量角器");
        rightTools.add(protractor);
        Tool unit = new Tool(R.drawable.unit_icon, "单位换算");
        rightTools.add(unit);
        Tool sizeTable = new Tool(R.drawable.sizetable_icon, "尺码表");
        rightTools.add(sizeTable);
    }
}
