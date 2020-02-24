package com.example.administrator.zahbzayxy.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.DemoApplication;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.AppVersionBean;
import com.example.administrator.zahbzayxy.beans.LogoutBean;
import com.example.administrator.zahbzayxy.myinterface.MyLessonInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.DataCleanManager;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetActivity extends BaseActivity implements View.OnClickListener {
    //退出登录
    Button existLogin_bt;
    //WiFi设置
    Switch onlyWifi_st;
    //消息提醒
    Switch sw_msg;
    //开课提醒
    Switch lessonStart_st;
    //显示缓存大小
    TextView showCache_tv;
    //清除缓存
    RelativeLayout clearCache_rl;
    //关于我们
    RelativeLayout aboutUs_rl;
    //返回
    ImageView img_back;
    //当前版本号
    TextView textVersion;
    //个人信息
    RelativeLayout rl_info;
    //账户安全
    RelativeLayout rl_account;
    //服务协议
    RelativeLayout rl_service;
    //隐私协议
    RelativeLayout rl_privacy;
    //注销账户
    RelativeLayout rl_households;
    private String versionName;
    private String downloadAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        initView();
    }

    private void initView() {
        rl_info= (RelativeLayout) findViewById(R.id.rl_info);
        rl_info.setOnClickListener(this);
        rl_account= (RelativeLayout) findViewById(R.id.rl_account);
        rl_account.setOnClickListener(this);
        clearCache_rl= (RelativeLayout) findViewById(R.id.clearCache_rl);
        clearCache_rl.setOnClickListener(this);
        rl_service= (RelativeLayout) findViewById(R.id.rl_service);
        rl_service.setOnClickListener(this);
        rl_privacy= (RelativeLayout) findViewById(R.id.rl_privacy);
        rl_privacy.setOnClickListener(this);
        aboutUs_rl= (RelativeLayout) findViewById(R.id.aboutUs_rl);
        aboutUs_rl.setOnClickListener(this);
        onlyWifi_st= (Switch) findViewById(R.id.onlyWifi_st);
        onlyWifi_st.setOnClickListener(this);
        sw_msg= (Switch) findViewById(R.id.switch_msg);
        sw_msg.setOnClickListener(this);
        lessonStart_st= (Switch) findViewById(R.id.lessonStart_st);
        lessonStart_st.setOnClickListener(this);
        existLogin_bt= (Button) findViewById(R.id.existLogin_bt);
        existLogin_bt.setOnClickListener(this);
        img_back= (ImageView) findViewById(R.id.myChengJiBack_iv);
        img_back.setOnClickListener(this);
        showCache_tv= (TextView) findViewById(R.id.showCache_tv);
        textVersion= (TextView) findViewById(R.id.curenntClass);
        rl_households= (RelativeLayout) findViewById(R.id.rl_households);
        rl_households.setOnClickListener(this);
        textVersion.setText("v"+getAppVersionName(ResetActivity.this));
        String totalCacheSize = null;
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(ResetActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showCache_tv.setText(totalCacheSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.myChengJiBack_iv:
                finish();
                break;
            //退出登录
            case R.id.existLogin_bt:
                SharedPreferences tokenDb1 = getSharedPreferences("tokenDb", MODE_PRIVATE);
                boolean isLogin = tokenDb1.getBoolean("isLogin", false);
                if (isLogin == false) {
                    Toast.makeText(ResetActivity.this, "用户还未登录", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
                    SharedPreferences.Editor edit = tokenDb.edit();
                    edit.putBoolean("isLogin", false);
                    edit.putBoolean("wechatLogin", false);
                    edit.putInt("isMechanism", 0);
                    edit.apply();
                    Intent intent = new Intent();
                    intent.putExtra("isLogin", false);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(ResetActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(66);
                    ResetActivity.this.finish();

                    UMShareAPI umShareAPI = DemoApplication.getInstance().getUmShareAPI();
                    umShareAPI.deleteOauth(ResetActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, new DefaultAuthListener() {
                        public void onDone() {

                        }

                        public void onStart(SHARE_MEDIA share_media) {

                        }
                    });
                }
                break;
            //wifi设置
            case R.id.onlyWifi_st:
                if (onlyWifi_st.isChecked()) {//选中
                    SharedPreferences sp = getSharedPreferences("wifiDb", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("WifiSwitch", true);
                    edit.apply();
                } else {
                    SharedPreferences sp = getSharedPreferences("wifiDb", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("WifiSwitch", false);
                    edit.apply();
                }
                break;
            //缓存部分
            case R.id.clearCache_rl:
                DataCleanManager.clearAllCache(ResetActivity.this);
                Toast.makeText(ResetActivity.this, "清除缓存成功", Toast.LENGTH_SHORT).show();
                try {
                    String totalCacheSize = DataCleanManager.getTotalCacheSize(ResetActivity.this);
                    showCache_tv.setText(totalCacheSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            //关于我们
            case R.id.aboutUs_rl:
                Intent intent = new Intent(ResetActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
                //个人信息
            case R.id.rl_info:
                startActivity(new Intent(ResetActivity.this,EditMessageActivity.class));
                break;
                //服务协议
            case R.id.rl_service:
                startActivity(new Intent(ResetActivity.this,ServiceActivity.class));
                break;
                //隐私协议
            case R.id.rl_privacy:
                startActivity(new Intent(ResetActivity.this,PrivacyActivity.class));
                break;
                //账户安全
            case R.id.rl_account:
                startActivity(new Intent(ResetActivity.this,AccountSecurityActivity.class));
                break;
            case R.id.rl_households://注销账户
                AlertDialog.Builder dialog = new AlertDialog.Builder(ResetActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("确定注销账号吗？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getLogout();
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }


    public void appVersionOnClick(View view) {
        MyLessonInterface aClass = RetrofitUtils.getInstance().createClass(MyLessonInterface.class);
        aClass.getAppVersionData(1).enqueue(new Callback<AppVersionBean>() {

            @Override
            public void onResponse(Call<AppVersionBean> call, Response<AppVersionBean> response) {
                int code = response.code();
                if (code == 200) {
                    AppVersionBean body = response.body();
                    if (body != null) {
                        String code1 = body.getCode();
                        try {
                            //此app版本
                            versionName = getVersionName(ResetActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (code1.equals("00000")) {
                            List<AppVersionBean.DataEntity> data = body.getData();
                            if (data != null) {
                                int size = data.size();
                                if (size > 0) {
                                    AppVersionBean.DataEntity dataEntity = data.get(size - 1);
                                    //上一个版本
                                    String verNum = dataEntity.getVerNum();
                                    //下载地址
                                    downloadAdd = dataEntity.getDownloadAdd();
                                    // versionName="1";
                                    if (!verNum.equals(versionName)) {//版本不相同去下载
                                        //initAppVersionDialog();
                                        initAppVersionDialog1();
                                    }else{
                                        ToastUtils.showLongInfo("当前是最新版本");
                                    }

                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AppVersionBean> call, Throwable t) {

            }
        });

    }

    private void initAppVersionDialog1() {

        View view = View.inflate(ResetActivity.this, R.layout.app_update_pop, null);
        TextView cancel = view.findViewById(R.id.myquestion_cancel);
        TextView downLoad_tv = view.findViewById(R.id.downLoadNow_tv);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        //设置动画,就是style里创建的那个j
        window.setAnimationStyle(R.style.take_photo_anim);
        window.showAsDropDown(view, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        window.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        window.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        cancel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        cancel.getPaint().setAntiAlias(true);//抗锯齿

        downLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去下载
                Uri uri = Uri.parse(downloadAdd);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();

            }
        });

    }

    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    abstract class DefaultAuthListener implements UMAuthListener {
        public abstract void onDone();

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            onDone();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            onDone();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            onDone();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**
     * 返回当前程序版本号
     */
    public static String getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            // versionName = pi.versionName;
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode + "";
    }
    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName=null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    private void getLogout(){
       SharedPreferences sharedPreferences =getSharedPreferences("tokenDb", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        MyLessonInterface aClass = RetrofitUtils.getInstance().createClass(MyLessonInterface.class);
        aClass.getLogout(token).enqueue(new Callback<LogoutBean>() {
            @Override
            public void onResponse(Call<LogoutBean> call, Response<LogoutBean> response) {
                if(response !=null && response.body()!=null){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        boolean data = response.body().isData();
                        if(data){
                            Toast.makeText(ResetActivity.this,"注销成功",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ResetActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LogoutBean> call, Throwable t) {
                Toast.makeText(ResetActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
