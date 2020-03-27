package com.example.administrator.zahbzayxy.activities;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.AppUrls;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.Utils;

/**
 * 直播课跳转h5播放页面
 */
public class LivePlayActivity extends BaseActivity{

    WebView mwebView;
    WebSettings webSettings;
    private int webinar_id;
    private TextView backLive;
    RelativeLayout rlContent;
    RelativeLayout header_top;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFullScreen(LivePlayActivity.this,getWindow());
        webinar_id = getIntent().getIntExtra("webinar_id",0);
        setContentView(R.layout.activity_live_play);
        mwebView=(WebView)findViewById(R.id.play_wv);
        mwebView.requestFocus(View.FOCUS_DOWN);
        rlContent = (RelativeLayout)findViewById(R.id.rl_web_content);
        header_top = (RelativeLayout)findViewById(R.id.header_top);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        backLive=(TextView)findViewById(R.id.backLive);
        backLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initWebView();
    }


    private void initWebView() {
        SharedPreferences tokenDb = getApplicationContext().getSharedPreferences("tokenDb", getApplicationContext().MODE_PRIVATE);
        String token = tokenDb.getString("token","");
        webSettings = mwebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }


        Log.i("=======================","进来了.....");
        String url=AppUrls.LIVE_URL+webinar_id;
        Log.i("=======================","进来了....."+url);
        mwebView.loadUrl(url);
        mwebView.getSettings().setJavaScriptEnabled(true);


        mwebView.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        mwebView.setWebChromeClient(new WebChromeClient(){
            private View mCustomView;
            private CustomViewCallback mCustomViewCallback;


            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                if(mCustomView != null){
                    callback.onCustomViewHidden();
                    return;
                }
                mCustomView = view;
                rlContent.addView(view);
                mCustomViewCallback = callback;
                header_top.setVisibility(View.GONE);
                mwebView.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                mwebView.setVisibility(View.VISIBLE);
                header_top.setVisibility(View.VISIBLE);
                mwebView.requestFocus(View.FOCUS_DOWN);
                if(mCustomView == null){
                    return;
                }
                mCustomView.setVisibility(View.GONE);
                rlContent.removeView(mCustomView);
                mCustomViewCallback.onCustomViewHidden();
                mCustomView =null;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

        });


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                break;
        }
    }

}
