package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

/**
 * Created by huwei.
 * Data 2020-01-10.
 * Time 14:48.
 * 消息详情
 */
public class H5MsgDetailActivity extends BaseActivity {
    private ImageView img_back;
    private WebView webView;
    private TextView tv_title;
    private String id;
    String url;
    String activityId;
    String type;
    String mPageFlag;
    private ProgressBarLayout mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_detail);
        webView = (WebView) findViewById(R.id.webView);
        tv_title = (TextView) findViewById(R.id.tv_title);
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.my_file_loading_layout);
        activityId  = getIntent().getStringExtra("activityId");
        id = getIntent().getStringExtra("id");
        type=getIntent().getStringExtra("type");
        mPageFlag=getIntent().getStringExtra("page");
        img_back = (ImageView) findViewById(R.id.back_editMessage);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        initView();
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        SharedPreferences tokenDb = getApplicationContext().getSharedPreferences("tokenDb", getApplicationContext().MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        //LOAD_CACHE_ELSE_NETWORK
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setBlockNetworkImage(false);
        webSettings.setJavaScriptEnabled(true);
        showLoadingBar(false);
        if ("sign".equals(type)) {
            hideLoadingBar();
            url = RetrofitUtils.getBaseUrl() + "/user/apply_detail"+ "?activityId=" + activityId + "&token=" + token;
            tv_title.setText("报名详情");
        }else if("msg".equals(type)){
            hideLoadingBar();
            url = RetrofitUtils.getBaseUrl() + "/news/news_detail" + "?id=" + id + "&token=" + token + "&isRemoveHeader=" + 1;
            tv_title.setText("消息详情");
        }

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
                Log.i("hw", "==========onPageStarted=========" + url + "|" + view);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("hw", "=====onPageFinished======" + Thread.currentThread().getName() + "i" + Thread.currentThread().getId());
            }
        });


    }
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

    private void back(){
        Intent intent = new Intent();
        intent.putExtra("id",id);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if("MsgListActivity".equals(mPageFlag)) {

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
