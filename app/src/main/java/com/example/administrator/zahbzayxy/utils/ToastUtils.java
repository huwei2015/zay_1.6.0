package com.example.administrator.zahbzayxy.utils;

import android.widget.Toast;

import com.example.administrator.zahbzayxy.DemoApplication;

public class ToastUtils {

    public static void showShortInfo(String content){
        showInfo(content, Toast.LENGTH_SHORT);
    }

    public static void showLongInfo(String content){
        showInfo(content, Toast.LENGTH_LONG);
    }

    public static void showInfo(String content, int duration) {
        Toast.makeText(DemoApplication.getInstance(), content, duration).show();
    }

}
