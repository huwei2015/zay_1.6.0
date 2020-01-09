package com.example.administrator.zahbzayxy.myinterface;

import com.example.administrator.zahbzayxy.beans.AppVersionBean;
import com.example.administrator.zahbzayxy.beans.HomeLessonClassBean;
import com.example.administrator.zahbzayxy.beans.LogoutBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ${ZWJ} on 2017/7/18 0018.
 */
public interface MyLessonInterface {
  String homeLessonDatailPath="v1/CourseController/courseFirstCateList";
    @GET(value=homeLessonDatailPath)
    Call<HomeLessonClassBean> getHomeLessonClassData();
   String appVersionPath="version/list";
    @GET(value=appVersionPath)
    Call<AppVersionBean> getAppVersionData(@Query("appType") Integer appType);
    //注销账号
    String logout = "users/security/logout";
    @POST(value = logout)
    Call<LogoutBean> getLogout(@Query("token")String token);
}
