package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

public class BuyActivity extends BaseActivity {
  private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        webView= (WebView) findViewById(R.id.homePicture_webView);
        String homePictureUrl = getIntent().getStringExtra("homePictureUrl");
        webView.loadUrl(homePictureUrl);
    }
}
