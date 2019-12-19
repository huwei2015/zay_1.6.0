package com.example.administrator.zahbzayxy.activities;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

/**
 * Created by huwei.
 * Data 2019/8/13.
 * Time 11:00.
 * Description.我的证书
 */
public class ZhengShuDetailActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private ImageView imageView;
    private ProgressBar progressBar;
    private RelativeLayout rl_title;
    int userCardId;
    String cerModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhengshu_detail);
        initView();
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        imageView = (ImageView) findViewById(R.id.pMyRenZhengDetailBack_iv);
        userCardId = getIntent().getIntExtra("userCardId", 0);
        Log.i("fdfdf","hw====userCardId==="+userCardId);
        cerModel = getIntent().getStringExtra("cerModel");
        Log.i("dfdfd","hw======cerModel======"+cerModel);
        imageView.setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        // 支持方大缩小
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        // 设置编码
        settings.setDefaultTextEncodingName("utf-8");
        //隐藏缩放控件
        settings.setDisplayZoomControls(false);
        //自适应屏幕
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setDatabaseEnabled(true);
        // 支持js
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        //提高渲染优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        settings.setAppCacheMaxSize(1024 * 1024 * 8);
//        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
//        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
//                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
            @Override
            public void onProgressChanged(WebView view, final int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == progressBar.getVisibility()) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
            }
        });
        if (cerModel.equals("1")) {//1是横版  2是竖版
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            webView.loadUrl(RetrofitUtils.getBaseUrl()+"certificate/app/view/preview"+"?userCerId=" + userCardId);
            webView.loadUrl("http://www.zayxy.com/certificate/app/view/preview"+"?userCerId=" + userCardId);
        } else if (cerModel.equals("2")) {
//            webView.loadUrl(RetrofitUtils.getBaseUrl()+"certificate/app/view/preview"+"?userCerId=" + userCardId);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            webView.loadUrl("http://www.zayxy.com/certificate/app/view/preview"+"?userCerId=" + userCardId);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pMyRenZhengDetailBack_iv:
                finish();
                break;
        }
    }
}
