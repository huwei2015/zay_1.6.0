package com.example.administrator.zahbzayxy.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.Runnables.UserInfoRunnable;
import com.example.administrator.zahbzayxy.activities.BuyActivity;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.activities.LessonThiredActivity;
import com.example.administrator.zahbzayxy.activities.LoginActivity;
import com.example.administrator.zahbzayxy.activities.MyLessonActivity;
import com.example.administrator.zahbzayxy.activities.MyTiKuActivity;
import com.example.administrator.zahbzayxy.activities.NewMyTikuActivity;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.handmark.pulltorefresh.library.internal.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewHomeFragment extends Fragment {
    Context mContext;
    Unbinder mUnBind;
    View view;
    @BindView(R.id.home_wv)
    WebView mwebView;
    WebAppInterface appInterface;
    /**********FHS Start**********/
    private AlertDialog dialog;
    /**********FHS Start**********/

    public NewHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;

    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_new_home, container, false);
        mUnBind=ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        initWebView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*****************FHS Start****************/
        if (isHidden()){
            //当前fragment对用户不可见
            if (null != dialog && dialog.isShowing()){
                dialog.dismiss();
            }
        }else {
            //当前fragment对用户可见,在新线程中访问获取用户信息的接口
//            UserInfoRunnable.startUsrInfoRunnable(this, mContext, dialog);
            UserInfoRunnable.startUsrInfoRunnable(mContext, handler);
        }
        Log.d("HomeFragment", "onResume");
        /*****************FHS Start****************/
    }
    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mwebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(mwebView,true);

        }

        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token","");
        WebSettings webSettings = mwebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setBlockNetworkImage(false);
        webSettings.setJavaScriptEnabled(true);
        mwebView.loadUrl(RetrofitUtils.getBaseUrl()+"/static/html/zayh5index.html"+"?token="+token);
//        mwebView.loadUrl(RetrofitUtils.getBaseUrl()+"/static/html/zayh5index.html"+"?token="+token);
        mwebView.getSettings().setJavaScriptEnabled(true);

        appInterface=new WebAppInterface(mContext);
        mwebView.addJavascriptInterface(appInterface,"home");
        mwebView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
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


    class WebAppInterface{
        private Context context;
        public WebAppInterface(Context context) {
            this.context = context;
        }
        @JavascriptInterface
        public void user(int user){
           if (Integer.valueOf(user)!=null){
               if (user==1){
                   Intent intent=new Intent(mContext, MyLessonActivity.class);
                   mContext.startActivity(intent);
               }else if (user==2){
                   Intent intent=new Intent(mContext, NewMyTikuActivity.class);
                   //之前跳转的题库页面
//                   Intent intent=new Intent(mContext, MyTiKuActivity.class);
                   mContext.startActivity(intent);
               }
           }

        }
        @JavascriptInterface
        public void lessonClass(int name){
            Log.e("nameaa",name+"");
            if (Integer.valueOf(name)!=null){
                switch (name) {
                    case 407:
                        EventBus.getDefault().post(2);
                        break;
                    case 406:
                        EventBus.getDefault().post(1);
                        break;
                    case 404:
                        EventBus.getDefault().post(0);
                        break;
                    case 0:
                        EventBus.getDefault().post(0);
                        break;

                }
            }
        }
        @JavascriptInterface
        public void lessonDetail(int id){
            Intent intent=new Intent(context, LessonThiredActivity.class);
            Bundle bundle=new Bundle();
            bundle.putInt("courseId",id);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

        @JavascriptInterface
        public void bannerUrl(String url){
            if (!TextUtils.isEmpty(url)) {
                Intent intent = new Intent(context, BuyActivity.class);
                intent.putExtra("homePictureUrl", url);
                context.startActivity(intent);
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventForLogin(String login){
        if (!TextUtils.isEmpty(login)){
            if (login.equals("login")){
                initWebView();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnBind!=null){
            mUnBind.unbind();
        }
        EventBus.getDefault().unregister(this);
    }
	
    /**
     * @author tu-mengting
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        try{
            if (hidden){
                //当前fragment对用户不可见
                if (null != dialog && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
            else {
                //当前fragment对用户可见,在新线程中访问获取用户信息的接口
//                UserInfoRunnable.startUsrInfoRunnable(this, mContext, dialog);
                UserInfoRunnable.startUsrInfoRunnable(mContext, handler);
            }
            Log.d("HomeFragment", "onHiddenChanged");
        }catch (Exception e){
            Log.e("onHiddenChanged", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * @author tu-mengting
     * 用于更新画面
     */
    @SuppressLint("HandlerLeak")
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try{
                switch (msg.what){
                    case Constant.GET_USR_INFO_ERR:
                        showErrDialog(R.string.get_user_info_err);
                        break;
                    case Constant.NEED_COLLECT_PORTRAIT:
                        showPromptDialog(R.string.upload_portrait_prompt);
                        break;
                    default:
                        break;
                }
            }
            catch (Exception e){
                Log.e("handleMessage", StringUtil.getExceptionMessage(e));
            }
        }
    };
    /**
     * @author tu-mengting
     * 显示要进行人脸识别的对话框
     */
    private void showPromptDialog(int contentRes){
        try{
            closeAlertDialog();//清除之前的提示框
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            dialog = builder.setTitle("提示")
                    .setMessage(contentRes)
                    .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            mContext.startActivity(new Intent(mContext, EditMessageActivity.class));
                        }
                    }).create();
            dialog.setCanceledOnTouchOutside(false);
            if (this.isVisible()){
                dialog.show();
            } else {
                dialog = null;
            }
        }
        catch (Exception e){
            Log.e("showDialog", StringUtil.getExceptionMessage(e));
        }
    }

    /**
     * @author tu-mengting
     * 显示错误信息对话框
     */
    private void showErrDialog(int contentRes){
        try{
            closeAlertDialog();//清除之前的提示框
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            dialog = builder.setTitle("提示")
                    .setMessage(contentRes)
                    .setNegativeButton(R.string.btn_confirm, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getActivity(),LoginActivity.class));
                            dialogInterface.dismiss();
                        }
                    }).create();

            dialog.setCanceledOnTouchOutside(false);
            if (this.isVisible()){
                dialog.show();
                //设置对话框size
                Window dialogWindow =  dialog.getWindow();
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
                lp.dimAmount = 0f;//设置背景不变暗
                dialogWindow.setAttributes(lp);
            } else {
                dialog = null;
            }
        }
        catch (Exception e){
            Log.e("showDialog", StringUtil.getExceptionMessage(e));
        }
    }
    private void closeAlertDialog(){
        if (dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }
}







