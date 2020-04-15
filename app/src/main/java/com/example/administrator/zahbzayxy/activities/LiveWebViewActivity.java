package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

/**
 * Created by huwei.
 * Data 2020-04-15.
 * Time 12:47.
 * 直播h5
 */
public class LiveWebViewActivity extends BaseActivity {
    private WebView live_webviwe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watch_web_activity);
        initView();
    }

    private void initView() {
        live_webviwe= (WebView) findViewById(R.id.live_webviwe);
    }
}
