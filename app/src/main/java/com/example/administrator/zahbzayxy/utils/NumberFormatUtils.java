package com.example.administrator.zahbzayxy.utils;

import android.text.TextUtils;

public class NumberFormatUtils {

    public static int parseInt(String intStr) {
        if (TextUtils.isEmpty(intStr)) return 0;
        int num = 0;
        try {
            num = Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            num = (int) Float.parseFloat(intStr);
        }
        return num;
    }

}
