package com.example.administrator.zahbzayxy.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ChenDandan on 2017/3/10.
 */
public class KeyboardUtil {

    /**
     * 在activity中隐藏软键盘
     * @param context 传入activity
     */
    public static void hideKeyBoardForAct(Activity context) {
        View view = context.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            // 强制隐藏软键盘
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 在fragemnt中隐藏软键盘
     * @param context 传入activity
     */
    public static void hideKeyBoardForFragment(Fragment context) {
        FragmentActivity activity = context.getActivity();
        View view = activity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            // 强制隐藏软键盘
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    public static void hideKeyBoard(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            // 强制隐藏软键盘
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }
}
