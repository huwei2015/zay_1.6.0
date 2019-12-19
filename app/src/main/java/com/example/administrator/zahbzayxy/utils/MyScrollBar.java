package com.example.administrator.zahbzayxy.utils;

import android.view.View;

/**
 * Created by ${ZWJ} on 2017/12/14 0014.
 */
public interface MyScrollBar {
    public static enum Gravity {
        TOP,
        TOP_FLOAT,
        BOTTOM,
        BOTTOM_FLOAT,
        CENTENT,
        CENTENT_BACKGROUND
    }

    public int getHeight(int tabHeight);

    public int getWidth(int tabWidth);

    public View getSlideView();

    public Gravity getGravity();

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
}
