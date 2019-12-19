package com.example.administrator.zahbzayxy.interfacecommit;

import com.example.administrator.zahbzayxy.beans.LoginBean;
import com.example.administrator.zahbzayxy.beans.PhoneCodeBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ChenDandan on 2017/3/10.
 */
public interface LoginService {

    String LOGIN_URL = "users/security/login?";

    //   登陆
    @GET(value = LOGIN_URL)
    Call<LoginBean> login(@Query("deviceNumber") String deviceNumber, @Query("password") String password, @Query("phone") String phone);


    String WECHAT_LOGIN_URL = "v1/users/security/wechatUidLogin?";

    //   微信登陆
    @GET(value = WECHAT_LOGIN_URL)
    Call<LoginBean> weChatLogin( @Query("uid") String uid);


    String BIND_WECHAT_CODE= "v1/users/security/bindWechatUidVerCode?";

    //  绑定微信验证码
    @GET(value = BIND_WECHAT_CODE)
    Call<PhoneCodeBean> bindingWechatCode(@Query("phone") String phone);

    String BIND_WECHAT_UID= "v1/users/security/bindWechatUid?";
    //  绑定微信验证码
    @GET(value = BIND_WECHAT_UID)
    Call<LoginBean> bindingWechatUid(@Query("password") String password,@Query("phone") String phone,@Query("uid") String uid,@Query("verCode") String verCode);

}
