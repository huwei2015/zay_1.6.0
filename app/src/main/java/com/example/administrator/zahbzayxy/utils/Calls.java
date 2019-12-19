package com.example.administrator.zahbzayxy.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2016/10/25.
 */
public class Calls {
    public static final String HEADER_NAME = "http://app.dingdangpai.com/";
    public static final String USER_ID = "userId";
    public static final String AUTHORIZATION = "http://app.dingdangpai.com/account/third/login/";
    /**
     * 微信登录的授权信
     */
    public static final String WECHAT_TOKEN = "cd_9PnMFy6haP_-2xveYBuUI4dkoXsLif44zK0Y7cAYS1pxfUWGYes9LpaYkrwijhSwrKkwjZ5CpXYTkdCdqLaqoXVQlnwbKV7-D8e_H77k";
    public static final String WECHAT_UID = "oKlrbt5dlktV4xxkB_xClwxqzKXo";
    public static final String WECHAT_EXPIRESIN = "7200";
    /**
     * qq登录的授权信
     */
    public static final String QQ_TOKEN = "AF30F2DC82E7D3F2719DA7C59722DDEB";
    public static final String QQ_UID = "34BC2380B70C4596DE3734393EABF453";
    public static final String QQ_EXPIRESIN = "7776000";

    /**
     * 总的搜索接口
     */
    public static final String SEARCH = "http://app.dingdangpai.com/search/all?query=";
    public static String getSearch(String path) {
        String strUTF8 = null;
        try {
            strUTF8 = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "http://app.dingdangpai.com/search/all?query=" + strUTF8;

    }

    /**搜索的派又
     * @param path
     * @return
     */
    public static String getUser(String path) {
        String strUTF8 = null;
        try {
            strUTF8 = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "http://app.dingdangpai.com/user/search?pageNo=1&pageSize=10&query=" + strUTF8;

    }
}
