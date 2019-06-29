package com.example.ubuntu.mymaxbox.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<View> mPagerViews;

    public MyPagerAdapter(ArrayList<View> mPagerViews) {
        this.mPagerViews = mPagerViews;
    }

    @Override
    public int getCount() {
        return mPagerViews.size();
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    //使从ViewGroup中移出当前Views
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mPagerViews.get(position));
    }

    //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mPagerViews.get(position));
        return mPagerViews.get(position);
    }
}
