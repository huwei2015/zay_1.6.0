package com.example.administrator.zahbzayxy.utils;

import android.app.Activity;

/**
 * Created by ${ZWJ} on 2018/3/27 0027.
 */

public class ScreenSizeUtil {
    public static int getScreenWidth(Activity activity) {

        return activity.getWindowManager().getDefaultDisplay().getWidth();

    }
    public static int getScreenHeight(Activity activity) {

        return activity.getWindowManager().getDefaultDisplay().getHeight();

    }

}
