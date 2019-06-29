package com.example.ubuntu.mymaxbox.listener;

import android.support.v4.view.ViewPager;
import android.widget.RadioButton;

public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

    private RadioButton btnLeft;
    private RadioButton btnRight;

    public MyPageChangeListener(RadioButton btnLeft, RadioButton btnRight) {
        this.btnLeft = btnLeft;
        this.btnRight = btnRight;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                btnLeft.setChecked(true);
                btnRight.setChecked(false);
                break;
            case 1:
                btnRight.setChecked(true);
                btnLeft.setChecked(false);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
