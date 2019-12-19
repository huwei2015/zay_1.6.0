package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ${ZWJ} on 2017/7/26 0026.
 */
public class IsLogin {
    public static boolean dbIsLogin(Context context) {
        SharedPreferences sharedPreferences =context.getSharedPreferences("tokenDb",context.MODE_APPEND);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }

    }
}
