package com.example.administrator.zahbzayxy.utils;

/**
 * Created by ${ZWJ} on 2018/5/4 0004.
 */

public class UUID {
    public static String getUUID(){
        java.util.UUID uuid= java.util.UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
}
