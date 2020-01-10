package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.Utils;

/**
 * Created by huwei.
 * Data 2020-01-10.
 * Time 14:48.
 * 消息详情
 */
public class H5MsgDetailActivity extends BaseActivity{
    private ImageView img_back;
    private WebView webView;
    private TextView tv_title;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_detail);
        webView=(WebView)findViewById(R.id.webView);
        tv_title= (TextView) findViewById(R.id.tv_title);
        initView();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(webView,true);

        }
        SharedPreferences tokenDb = getApplicationContext().getSharedPreferences("tokenDb", getApplicationContext().MODE_PRIVATE);
        String token = tokenDb.getString("token","");
        WebSettings webSettings = webView.getSettings();
        id= getIntent().getStringExtra("id");
        Log.i("hw","hw====token========"+token);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        //LOAD_CACHE_ELSE_NETWORK
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setBlockNetworkImage(false);
        webSettings.setJavaScriptEnabled(true);
        String url= RetrofitUtils.getBaseUrl() +"/news/news_detail"+"?id="+id+"&token="+token;

        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("hw","==========onPageStarted========="+ url+"|"+view);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("hw","=====onPageFinished======"+Thread.currentThread().getName()+"i"+Thread.currentThread().getId());
            }
        });


    }

}
