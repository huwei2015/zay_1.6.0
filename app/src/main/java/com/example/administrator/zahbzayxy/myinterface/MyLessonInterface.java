package com.example.administrator.zahbzayxy.myinterface;

import com.example.administrator.zahbzayxy.beans.AppVersionBean;
import com.example.administrator.zahbzayxy.beans.HomeLessonClassBean;

import retrofit2.Call;
import retrofit2.http.GET;
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
}
