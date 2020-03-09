package com.example.administrator.zahbzayxy.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huwei on 2017/11/13.
 * 验证
 */

public class MatchUtil {
    /**
     * 手机号的正则
     * "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
     */
    public static final String MATCH_MOBILE = "[1][34578]\\d{9}";

    /**
     * 正则表达式:验证密码(不包含特殊字符)
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     *18位数字身份证验证表达式
     */
    public static final String REGEX_IDCARDENTITY="(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    /**
     * 姓名校验表达式
     */
    public static final String MATCH_NAME="^[\\u4E00-\\u9FA5]{2,}(?:(·|.)[\\u4E00-\\u9FA5]{2,})*$";
    /**
     * 港澳通行证表达式
     */
    public static final String MATCH_MACAU="^[HMChmc]{1}([0-9]{10}|[0-9]{8})$";
    /**
     * 台湾通行证
     */
    public static final String MATCh_COMPATRIOTS = "^[a-zA-Z][0-9]{9}$";
    /**
     * 回乡证
     */
    public static final String MACTH_RETURNCARD = "(H|M)(\\d{10})$";
    /**
     * 护照
     */
    public static final String MATCh_PASSPORT = "^([a-zA-z]|[0-9]){9}$";
    /**
     * 验证手机号是否符合规则
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileRight(String mobile) {
        // String reg = "^[1]([358][0-9]{1}|30|59|58|88|89)[0-9]{8}$";
        Pattern p = Pattern.compile(MATCH_MOBILE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 校验密码
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验身份证
     * @param idcardentity
     * @return
     */
    public static boolean isIdcradentity(String idcardentity){
        return Pattern.matches(REGEX_IDCARDENTITY,idcardentity);
    }

    /**
     * 港澳通信证
     * @param macaupass
     * @return
     */
    public static boolean isMacaupass (String macaupass){
        return Pattern.matches(MATCH_MACAU,macaupass);
    }

    /**
     * 台湾通行证
     * @param tanwan_pass
     * @return
     */
    public static boolean isTanwan(String tanwan_pass){
        return Pattern.matches(MATCh_COMPATRIOTS,tanwan_pass);
    }

    /**
     * 回乡证
     * @param returnCard
     * @return
     */
    public static boolean isReturnCard(String returnCard){
        return Pattern.matches(MACTH_RETURNCARD,returnCard);
    }

    /**
     * 护照
     * @param passport
     * @return
     */
    public static boolean isPassPoRt(String passport){
        return Pattern.matches(MATCh_PASSPORT,passport);
    }
    /**
     * 姓名校验规则
     * @param name
     * @return
     */
    public static boolean isUsername(String name){
        return Pattern.matches(MATCH_NAME,name);
    }
    /**
     * 数字除法
     * 默认保留10位小数
     *
     * @param s1
     * @param s2
     * @param decimalNum 小数位数
     * @return
     */
    public static String divide(String s1, String s2, int decimalNum) {
        if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2)) {
            //noinspection HardCodedStringLiteral
            return "params error";
        }
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        String bigDecimal = b1.divide(b2, decimalNum, BigDecimal.ROUND_HALF_EVEN).toString();
        return FormatUtil.subZeroAndDot(bigDecimal);
    }

    /**
     * 数字除法,可设置取舍模式
     *
     * @param s1
     * @param s2
     * @param decimalNum
     * @param roundMode  取舍的模式 {@link BigDecimal}
     * @return
     */
    public static String divide(String s1, String s2, int decimalNum, int roundMode) {
        if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2)) {
            //noinspection HardCodedStringLiteral
            return "params error";
        }
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        String bigDecimal = b1.divide(b2, decimalNum, roundMode).toString();
        return FormatUtil.subZeroAndDot(bigDecimal);
    }
}
