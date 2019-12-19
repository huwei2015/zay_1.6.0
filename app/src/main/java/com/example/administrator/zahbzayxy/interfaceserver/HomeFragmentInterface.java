package com.example.administrator.zahbzayxy.interfaceserver;

import com.example.administrator.zahbzayxy.beans.HomeNewsBean;
import com.example.administrator.zahbzayxy.beans.HomePictureBean;
import com.example.administrator.zahbzayxy.beans.NewLessonBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ${ZWJ} on 2017/3/9 0009.
 */
public interface HomeFragmentInterface {
    //首页轮播图
      String homePicturePath="index/carousel/picture";
    @GET(value = homePicturePath)
    Call<HomePictureBean>getHomePictureData();
    //首页新闻

      String homeNewsPath="index/news";
    @GET(value = homeNewsPath)
    Call<HomeNewsBean>getHomeNewsData();



//首页课程
  String newLessonPath="CourseController/indexCourseList";
    @GET(value=newLessonPath)
Call<NewLessonBean> getNewLessonData();
}
