package com.example.administrator.zahbzayxy.interfaceserver;

import com.example.administrator.zahbzayxy.beans.LessonAttachTestBean;
import com.example.administrator.zahbzayxy.beans.LessonGroupBean;
import com.example.administrator.zahbzayxy.beans.LessonNavigationBean;
import com.example.administrator.zahbzayxy.beans.LessonSecondGridViewBean;
import com.example.administrator.zahbzayxy.beans.LessonThiredBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${ZWJ} on 2017/3/15 0015.
 */
public interface LessonGroupInterface {

    static final String groupLessonPath="CourseController/courseCateList";

    static final String groupCourseListPath="CourseController/courseList?";



    static final String lessonAttachTestPath="quesLib/getCourseRelatedQuesLib?";
    //我的课程一级页面
    @GET(value=groupLessonPath)
    Call<LessonGroupBean> getLessonGroupData();

    //我的课程二级列表展示的接口
    @GET(value=groupCourseListPath)
    Call<LessonSecondGridViewBean> getCourseListData(@Query("pageNo") Integer pageNo,@Query("pageSize") Integer pageSize,
                                                     @Query("parentCateId") Integer parentCateId, @Query("subCateId") Integer subCateId);


    static final String groupCourseDetailPath="CourseController/courseDesc?";
    @GET(value=groupCourseDetailPath)
    Call<LessonThiredBean> getLessonDetailData(@Query("courseId")Integer courseId);

    @GET(value=lessonAttachTestPath)
    Call<LessonAttachTestBean> getLessonAttachTestData(@Query("courseId")Integer courseId);


//我的课程导航栏内容
    static final String lessonNavigationPath="CourseController/courseCateList_v1";
    @GET(value=lessonNavigationPath)
    Call<LessonNavigationBean> getLessonNavigationData(@Query("token") String token);


    //我的课程二级列表内容
    @GET(value=lessonNavigationPath)
    Call<LessonNavigationBean> getLessonExpandedData(@Query("token")String token,@Query("courseId")Integer courseId);

}
