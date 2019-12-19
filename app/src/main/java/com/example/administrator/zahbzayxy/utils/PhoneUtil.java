package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by ChenDandan on 2017/3/10.
 */
public class PhoneUtil {

    /**
     * 获取设备序列号
     */
    public static String getDeviceId(Context context) {
        String deviceId = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = "0000000000";
        }
        return deviceId;
    }
}
