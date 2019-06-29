package com.example.ubuntu.mymaxbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.ubuntu.mymaxbox.adapter.LedAdapter;
import com.example.ubuntu.mymaxbox.entity.Led;
import com.example.ubuntu.mymaxbox.view.MyLedView;

import java.util.ArrayList;

public class LedActivity extends AppCompatActivity {
    private static final String TAG = "LedActivity";
    private ArrayList<Led> mLedList = new ArrayList<>();

    private MyLedView imgContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);

        initLedData();

        imgContent = findViewById(R.id.led_content);

        RecyclerView recyclerView = findViewById(R.id.led_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LedAdapter ledAdapter = new LedAdapter(mLedList, imgContent);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ledAdapter);
    }

    private void initLedData() {
        mLedList.add(new Led(R.drawable.led1, "狗头"));
        mLedList.add(new Led(R.drawable.led2, "笑脸"));
        mLedList.add(new Led(R.drawable.led3,"信封"));
        mLedList.add(new Led(R.drawable.led4,"心"));
        mLedList.add(new Led(R.drawable.led5,"sos"));
        mLedList.add(new Led(R.drawable.led6,"啤酒"));
        mLedList.add(new Led(R.drawable.led7,"吃豆人"));
    }
}
