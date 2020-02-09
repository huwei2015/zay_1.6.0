package com.example.administrator.zahbzayxy.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.AppUrls;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.CacheActivity;
import com.example.administrator.zahbzayxy.utils.DataHelper;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.Utils;

import org.greenrobot.eventbus.EventBus;

/**
 * HYY 跳转h5页面，如常见问题，用户手册
 */
public class H5PageActivity extends BaseActivity{
    WebAppInterface appInterface;
    private RelativeLayout header_topRL;
    WebView mwebView;
    private String h5Type;
    private TextView backPage;
    private TextView titleText;
    private String h5Url;
    private String title;

    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;

    private DataHelper mDataHelper=new DataHelper();
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
        header_topRL=(RelativeLayout) findViewById(R.id.header_top);
        initWebView();
        if (!CacheActivity.activityList.contains(getApplication())) {
            CacheActivity.addActivity(H5PageActivity.this);
        }
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.load_bar_layout_index);
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
        appInterface = new WebAppInterface(getApplicationContext());
        mwebView.addJavascriptInterface(appInterface, "home");
        String url=RetrofitUtils.getBaseUrl() +AppUrls.common_problem_URL+"?token="+token;
        if("common_problem".equals(h5Type)){//常见问题
            url= RetrofitUtils.getBaseUrl() +AppUrls.common_problem_URL+"?token="+token;
            titleText.setText("常见问题");
        }else if("user_manual".equals(h5Type)){//用户手册
            url=RetrofitUtils.getBaseUrl() +AppUrls.user_manual_URL+"?token="+token;
            titleText.setText("使用手册");
        }else{
            h5Url=getIntent().getStringExtra("url");
            title=getIntent().getStringExtra("title");
            url=RetrofitUtils.getBaseUrl() +h5Url;
            if(url.indexOf("/organ_show")!=-1){
                header_topRL.setBackground(getResources().getDrawable(R.mipmap.fc_bg));
            }else{
                header_topRL.setBackground(getResources().getDrawable(R.mipmap.header_bg));
            }
            titleText.setText(title);
        }
        mwebView.loadUrl(url);
        mwebView.getSettings().setJavaScriptEnabled(true);

        mwebView.setWebChromeClient(new WebChromeClient() {

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }
        });

        mwebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public  WebResourceResponse  shouldInterceptRequest(WebView  view, String url) {
                if(mDataHelper.hasLocalResource(url)){
                    WebResourceResponse response=mDataHelper.getReplacedWebResourceResponse(getApplicationContext(),url);if (response != null) {
                        return response;
                    }
                }
                return super.shouldInterceptRequest(view,url);
            }
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public  WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (mDataHelper.hasLocalResource(url)) {
                    WebResourceResponse  response = mDataHelper.getReplacedWebResourceResponse(getApplicationContext(),
                            url); if (response != null) {
                        return response;
                    }
                }
                return super.shouldInterceptRequest(view, request);
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
                showLoadingBar(false);
                Log.i("hw","==========onPageStarted========="+ url+"|"+view);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoadingBar();
                Log.i("hw","=====onPageFinished======"+Thread.currentThread().getName()+"i"+Thread.currentThread().getId());
            }
        });
    }

    private ProgressBarLayout mLoadingBar;
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

    class WebAppInterface{
        private Context context;
        public WebAppInterface(Context context) {
            this.context = context;
        }
        @JavascriptInterface
        public void turnH5Page(String url,String titleText){
            Intent intent=new Intent(getApplicationContext(), H5PageActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("url",url);
            bundle.putString("title",titleText);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @JavascriptInterface
        public void goBackIndex(){
            CacheActivity.finishActivity();
        }

        /**
         * 报名在线支付
         * @param orderNumber
         * @param price
         */
        @JavascriptInterface
        public void onlinePay(String orderNumber,String price){
            Intent intent=new Intent(context, PayUiActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("orderNumber",orderNumber);
            bundle.putString("testPrice",price);
            bundle.putBoolean("isLessonOrder",true);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
//        i.setType("image/*");//图片上传
//        i.setType("file/*");//文件上传
        i.setType("*/*");//文件上传
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    // 3.选择图片后处理
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            // Uri result = (((data == null) || (resultCode != RESULT_OK)) ? null : data.getData());
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        } else {
            //这里uploadMessage跟uploadMessageAboveL在不同系统版本下分别持有了
            //WebView对象，在用户取消文件选择器的情况下，需给onReceiveValue传null返回值
            //否则WebView在未收到返回值的情况下，无法进行任何操作，文件选择器会失效
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            } else if (uploadMessageAboveL != null) {
                uploadMessageAboveL.onReceiveValue(null);
                uploadMessageAboveL = null;
            }
        }
    }

    // 4. 选择内容回调到Html页面
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }
}
