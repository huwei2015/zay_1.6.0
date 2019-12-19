package com.example.administrator.zahbzayxy.interfacecommit;

import com.example.administrator.zahbzayxy.beans.ModifyPWBean;
import com.example.administrator.zahbzayxy.beans.PhoneCodeBean;
import com.example.administrator.zahbzayxy.beans.PhoneExistBean;
import com.example.administrator.zahbzayxy.beans.RegisterBackBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${ZWJ} on 2017/3/7 0007.
 */
public interface RegisterInterface {

    static final String checkPhonePath="users/security/chkphone?";
    static final String registerVerCodePath ="users/security/registerVerCode?";
    static final String forgetVerCodePath ="users/security/repwdVerCode?";
    static final String registerPath="users/security/registerUser?";
    static final String modifyPWPath="users/security/repwd?";

    //   检查手机号是否存在
    @GET(value = checkPhonePath)
    Call<PhoneExistBean> checkPhoneIsExist(@Query("phone") String phone);


    //   注册获取短信验证码
    @GET(value = registerVerCodePath)
    Call<PhoneCodeBean> getRegisterVerCode(@Query("phone") String phone);

    //   注册
    @GET(value = registerPath)
    Call<RegisterBackBean> register(@Query("phone") String phone, @Query("password") String password, @Query("regSource") int regSource, @Query("verCode") String verCode);


    //   忘记密码获取短信验证码
    @GET(value = forgetVerCodePath)
    Call<PhoneCodeBean> getForgetPwVerCode(@Query("phone") String phone);


    //   修改密码
    @GET(value = modifyPWPath)
    Call<ModifyPWBean> modifyPw(@Query("password") String password,@Query("phone") String phone,@Query("verCode") String verCode);

    //   修改手机号获取短信验证码
    static final String changePhonePath="/userCenter/updatePhoneVerCode?";
    @GET(value = changePhonePath)
    Call<PhoneCodeBean> getChangePhoneCode(@Query("phone") String phone,@Query("token")String token);


}


