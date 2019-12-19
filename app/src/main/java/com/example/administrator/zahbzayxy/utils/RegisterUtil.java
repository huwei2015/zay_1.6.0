package com.example.administrator.zahbzayxy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ${ZWJ} on 2017/3/9 0009.
 */
public class RegisterUtil {
    /**
     * 判断电话号码是否符合格式.
     *
     * @param inputText the input text
     * @return true, if is phone
     */
    public static boolean isPhone(String inputText) {
        Pattern p = Pattern.compile("^1[3456789]\\d{9}$");
        Matcher m = p.matcher(inputText);
        return m.matches();
    }
}