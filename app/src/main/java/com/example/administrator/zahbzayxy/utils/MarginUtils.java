package com.example.administrator.zahbzayxy.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ${ZWJ} on 2017/6/12 0012.
 */
public class MarginUtils {
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
