package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

public class ShowPdfActivity extends BaseActivity {

    public static final String SHOW_PDF_FILE_NAME_KEY = "showPdfFileNameKey";
    public static final String SHOW_PDF_FILE_URL_KEY = "showPdfFileUrlKey";


    private ImageView mBackImg;
    private TextView mTitleTv;
    private WebView mWebView;
    private String mFileName, mFileUrl;
    private ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mBackImg = (ImageView) findViewById(R.id.show_pdf_back_img);
        mTitleTv = (TextView) findViewById(R.id.show_pdf_title_tv);
        mWebView = (WebView) findViewById(R.id.show_pdf_web_view);
        mLoadingBar = (ProgressBar) findViewById(R.id.show_pdf_loading_bar);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            ToastUtils.showShortInfo("文件加载失败");
            finish();
            return;
        }
        mFileName = intent.getStringExtra(SHOW_PDF_FILE_NAME_KEY);
        mFileUrl = intent.getStringExtra(SHOW_PDF_FILE_URL_KEY);
        if (TextUtils.isEmpty(mFileName) || TextUtils.isEmpty(mFileUrl)) {
            ToastUtils.showLongInfo("文件加载失败，请检查");
            finish();
            return;
        }
        mTitleTv.setText(mFileName);
        showFile();
    }

    private void showFile() {
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.loadUrl("file:///android_asset/show_pdf.html?" + mFileUrl);
    }

    private void initEvent() {
        mBackImg.setOnClickListener(view -> finish());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mLoadingBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadingBar.setVisibility(View.GONE);
            }


        });
    }

    private void destroyWebView() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            ((ViewGroup)(mWebView.getParent())).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }
}
