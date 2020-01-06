package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.myviews.PageFrameLayout;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

/**
 * Created by huwei.
 * Data 2020-01-03.
 * Time 11:09.
 * 引导启动也
 */
public class SplashActivity extends BaseActivity {
    private PageFrameLayout contentFragmentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_splash);
        contentFragmentLayout = (PageFrameLayout) findViewById(R.id.contentFragmentLayout);
        //设置资源文件和选中圆点
        contentFragmentLayout.setUpViews(new int[]{
                R.layout.activity_guide1,
                R.layout.activity_guide2,
                R.layout.activity_guide3
        }, R.mipmap.img_point_selected, R.mipmap.img_point);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
