package com.example.administrator.zahbzayxy;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.baidu.idl.face.platform.LivenessTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/5/31 0031.
 */
public class ExitApplication extends Application {
    private List<Activity> list = new ArrayList<Activity>();
    private static ExitApplication ea;

    public static List<LivenessTypeEnum> livenessList = new ArrayList<>();
    public static boolean isLivenessRandom = false;

    private ExitApplication() {

    }

    public static ExitApplication getInstance() {
        if (null == ea) {
            ea = new ExitApplication();
        }
        return ea;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void exit(Context context) {
        for (Activity activity : list) {
            activity.finish();
        }
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
