package com.example.administrator.zahbzayxy;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.bokecc.sdk.mobile.drm.DRMServer;
import com.example.administrator.zahbzayxy.ccvideo.DataSet;
import com.example.administrator.zahbzayxy.ccvideo.MyObjectBox;
import com.example.administrator.zahbzayxy.utils.CrashHandler;
import com.example.administrator.zahbzayxy.utils.SPUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import io.objectbox.BoxStore;

/**
 * Created by ${ZWJ} on 2017/4/12 0012.
 */
public class DemoApplication extends MultiDexApplication {

    private DRMServer drmServer;
    private UMShareAPI umShareAPI;
    public static DemoApplication instance;

    private BoxStore boxStore;

    private String token;
    public static Context context;


    @Override
    public void onCreate() {
        startDRMServer();
        super.onCreate();
        //HYY崩溃日志监控 并保存到后台
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        initUmengShare();
        if (instance == null) {
            instance = this;
        }

        boxStore = MyObjectBox.builder().androidContext(this).build();
        DataSet.init(boxStore);

        token = SPUtils.getString(this, SPUtils.Key.TOKEN);


    }

    private void initUmengShare() {
        umShareAPI = UMShareAPI.get(this);
        // Config. = false;;
        Config.DEBUG = false;
        Config.isJumptoAppStore = true;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public static DemoApplication getInstance() {
        return instance;
    }

    public UMShareAPI getUmShareAPI() {
        return umShareAPI;
    }


    public BoxStore getBoxStore() {
        return boxStore;
    }



    // 启动DRMServer
    public void startDRMServer() {
        if (drmServer == null) {
            drmServer = new DRMServer();
            drmServer.setRequestRetryCount(20);
        }

        try {
            drmServer.start();
            setDrmServerPort(drmServer.getPort());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "启动解密服务失败，请检查网络限制情况", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onTerminate() {
        if (drmServer != null) {
            drmServer.stop();
        }
        super.onTerminate();
    }

    private int drmServerPort;

    public int getDrmServerPort() {
        return drmServerPort;
    }

    public void setDrmServerPort(int drmServerPort) {
        this.drmServerPort = drmServerPort;
    }

    public DRMServer getDRMServer() {
        return drmServer;
    }



    {
        //微信
        //c65edc0749c84c505c5a686e4ac6ca53
        PlatformConfig.setWeixin("wx0dff7a26ddc49530", "c65edc0749c84c505c5a686e4ac6ca53");
       // PlatformConfig.setWeixin("wxdf45b0a4e53ed1dd", "c65edc0749c84c505c5a686e4ac6ca53");
        //新浪微博(第三个参数为回调地址)


        PlatformConfig.setSinaWeibo("2490795475", "0db73383054e3a51e061407b4de2dea8", "http://sns.whalecloud.com/sina2/callback");
        //QQ
        PlatformConfig.setQQZone("1106091830", "SKld8JidHFJYWOBi");
    }
}