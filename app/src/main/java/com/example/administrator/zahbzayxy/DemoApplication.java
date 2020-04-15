package com.example.administrator.zahbzayxy;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.bokecc.sdk.mobile.drm.DRMServer;
import com.example.administrator.zahbzayxy.ccvideo.DataSet;
import com.example.administrator.zahbzayxy.ccvideo.MyObjectBox;
import com.example.administrator.zahbzayxy.utils.CrashHandler;
import com.example.administrator.zahbzayxy.utils.SPUtils;

import io.objectbox.BoxStore;

/**
 * Created by ${ZWJ} on 2017/4/12 0012.
 */
public class DemoApplication extends MultiDexApplication {

    private DRMServer drmServer;
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
        if (instance == null) {
            instance = this;
        }

        boxStore = MyObjectBox.builder().androidContext(this).build();
        DataSet.init(boxStore);

        token = SPUtils.getString(this, SPUtils.Key.TOKEN);


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
    }
}