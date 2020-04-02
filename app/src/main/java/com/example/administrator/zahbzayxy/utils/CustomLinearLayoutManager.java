package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author yaofei.zhao
 * @date 2017/7/21
 * 禁止revycleview滑动的自定义管理器类
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
