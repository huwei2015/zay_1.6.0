package com.example.administrator.zahbzayxy.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author tu-mengting
 * @category 字符串操作方法类
 * @date 2019/04/04
 */
public class StringUtil {

    /**
     * 将异常转化成字符串
     * @param e
     * @return
     */
	public static String getExceptionMessage(Exception e) {

        String ret;

        try {
            if (null == e) {
                return "";
            }
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            ret = sw.toString();
        } catch (Exception ee) {
            ret = "";
        }

        return ret;
    }
}
