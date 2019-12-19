package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

public class HotNewsDetailActivity extends BaseActivity {
     WebView hotNewsWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_news_detail);
        hotNewsWebView= (WebView) findViewById(R.id.hotNews_webView);
        String hotNewsDetailUrl = getIntent().getStringExtra("hotNewsDetailUrl");
        hotNewsWebView.loadUrl(hotNewsDetailUrl);
    }
}
