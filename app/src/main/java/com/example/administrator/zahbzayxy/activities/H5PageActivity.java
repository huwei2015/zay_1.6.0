package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.AppUrls;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.Utils;

/**
 * HYY 跳转h5页面，如常见问题，用户手册
 */
public class H5PageActivity extends BaseActivity{

    WebView mwebView;
    private String h5Type;
    private TextView backPage;
    private TextView titleText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFullScreen(H5PageActivity.this,getWindow());
        h5Type = getIntent().getStringExtra("h5Type");
        setContentView(R.layout.activity_h5_page);
        mwebView=(WebView)findViewById(R.id.htmlpage_wv);
        backPage=(TextView)findViewById(R.id.backPage);
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleText=(TextView)findViewById(R.id.titleText);
        initWebView();
    }


    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mwebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(mwebView,true);

        }

        SharedPreferences tokenDb = getApplicationContext().getSharedPreferences("tokenDb", getApplicationContext().MODE_PRIVATE);
        String token = tokenDb.getString("token","");
        WebSettings webSettings = mwebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        //LOAD_CACHE_ELSE_NETWORK
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setBlockNetworkImage(false);
        webSettings.setJavaScriptEnabled(true);
        String url=RetrofitUtils.getBaseUrl() +AppUrls.common_problem_URL+"?token="+token;
        if("common_problem".equals(h5Type)){//常见问题
            url= RetrofitUtils.getBaseUrl() +AppUrls.common_problem_URL+"?token="+token;
            titleText.setText("常见问题");
        }else if("user_manual".equals(h5Type)){//用户手册
            url=RetrofitUtils.getBaseUrl() +AppUrls.user_manual_URL+"?token="+token;
            titleText.setText("使用手册");
        }
        mwebView.loadUrl(url);
        mwebView.getSettings().setJavaScriptEnabled(true);

        mwebView.setWebViewClient(new WebViewClient() {
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
