package com.example.administrator.zahbzayxy.interfacecommit;

import com.alibaba.fastjson.JSONObject;
import com.example.administrator.zahbzayxy.beans.BookBean;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.LiveCourseBean;
import com.example.administrator.zahbzayxy.beans.OfflineCourseBean;
import com.example.administrator.zahbzayxy.beans.OfflineCoursePOBean;
import com.example.administrator.zahbzayxy.beans.AllOnlineCourseBean;
import com.example.administrator.zahbzayxy.beans.QueslibBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * HYY 首页接口
 */

public interface IndexInterface {

    //获取在线课分类
    static final String classifyPath="data/course/getOnlineClassify";
    @GET(value = classifyPath)
    Call<CourseCatesBean> getCourseCates(@Query("token") String token);


    //在线课列表
    static final String onlineCourseListPath="data/course/listOnlineCourse";
    @GET(value = onlineCourseListPath)
    Call<AllOnlineCourseBean> onlineCourseList(@Query("pageNo") Integer pageNo,
                                               @Query("pageSize") Integer pageSize,
                                               @Query("token") String token,
                                               @Query("cateId") Integer cateId,
                                               @Query("isRecommend") Integer isRecommend,
                                               @Query("isTrailers") Integer isTrailers,
                                               @Query("isNew") Integer isNew,
                                               @Query("dataFormat") Integer dataFormat
                                            );

    //获取线下课分类
    static final String offlineClassifyPath="data/course/getOfflineClassify";
    @GET(value = offlineClassifyPath)
    Call<CourseCatesBean> getOfflineCourseCates(@Query("token") String token);


    //线下课列表
    static final String offlineCourseListPath="data/course/listOfflineCourse";
    @GET(value = offlineCourseListPath)
    Call<OfflineCourseBean> offlineCourseList(@Query("pageNo") Integer pageNo,
                                              @Query("pageSize") Integer pageSize,
                                              @Query("token") String token,
                                              @Query("cateId") Integer cateId,
                                              @Query("isRecommend") Integer isRecommend,
                                              @Query("isTrailers") Integer isTrailers,
                                              @Query("isNew") Integer isNew,
                                              @Query("dataFormat") Integer dataFormat
    );

    static final String getOfflineCourseDetail="data/course/getOfflineCourseDetail?";
    @GET(value=getOfflineCourseDetail)
    Call<OfflineCoursePOBean> getOfflineCourseDetail(@Query("courseId")Integer courseId);


    //获取题库分类
    static final String queslibClassifyPath="data/queslib/getQueslibClassify";
    @GET(value = queslibClassifyPath)
    Call<CourseCatesBean> getQueslibCates(@Query("token") String token);


    //题库列表
    static final String queslibListPath="data/queslib/listQueslib";
    @GET(value = queslibListPath)
    Call<QueslibBean> queslibList(@Query("pageNo") Integer pageNo,
                                  @Query("pageSize") Integer pageSize,
                                  @Query("token") String token,
                                  @Query("cateId") Integer cateId,
                                  @Query("isRecommend") Integer isRecommend,
                                  @Query("isFree") Integer isFree,
                                  @Query("isNew") Integer isNew,
                                  @Query("dataFormat") Integer dataFormat
    );



    //获取书籍分类
    static final String bookClassifyPath="data/book/getBookClassify";
    @GET(value = bookClassifyPath)
    Call<CourseCatesBean> getBookCates(@Query("token") String token);


    //书籍列表
    static final String booListPath="data/book/listBook";
    @GET(value = booListPath)
    Call<BookBean> bookList(@Query("pageNo") Integer pageNo,
                            @Query("pageSize") Integer pageSize,
                            @Query("token") String token,
                            @Query("cateId") Integer cateId,
                            @Query("isNew") Integer isNew,
                            @Query("dataFormat") Integer dataFormat
    );


    //直播课列表
    static final String liveCourseListPath="CourseController/getLiveList";
    @GET(value = liveCourseListPath)
    Call<LiveCourseBean> liveCourseList(@Query("pageNo") Integer pageNo,
                                        @Query("pageSize") Integer pageSize,
                                        @Query("token") String token,
                                        @Query("status") String status,
                                        @Query("dataFormat") Integer dataFormat
    );


    //签到接口
    static final String signInPath="signin/save/info";
    @GET(value = signInPath)
    Call<JSONObject> saveSignIn(@Query("courseId") Integer courseId,
                                @Query("token") String token
    );
}