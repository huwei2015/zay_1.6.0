package com.example.administrator.zahbzayxy.interfacecommit;

import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.ExamBean;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiBean;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiListBean;
import com.example.administrator.zahbzayxy.beans.OfflineCourseBean;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.beans.OrderIsOutOfDateBean;
import com.example.administrator.zahbzayxy.beans.PLessonPlayTimeBean;
import com.example.administrator.zahbzayxy.beans.PMyLessonBean;
import com.example.administrator.zahbzayxy.beans.PMyLessonOrderBean;
import com.example.administrator.zahbzayxy.beans.PMyLessonPlayBean;
import com.example.administrator.zahbzayxy.beans.PMyRenZhengDetailBean;
import com.example.administrator.zahbzayxy.beans.PMyRenZhengMuLuBean;
import com.example.administrator.zahbzayxy.beans.PMyTestGradBean;
import com.example.administrator.zahbzayxy.beans.PMyTestOrderBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
import com.example.administrator.zahbzayxy.beans.PersonTiKuListBean;
import com.example.administrator.zahbzayxy.beans.QueslibBean;
import com.example.administrator.zahbzayxy.beans.RecommendCourseBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.beans.YouHuiJuanBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<OnlineCourseBean> onlineCourseList(@Query("pageNo") Integer pageNo,
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

    //课程推荐列表
    static final String recommendCourseListPath="data/course/listByPage";
    @GET(value = recommendCourseListPath)
    Call<RecommendCourseBean> recommendCourseList(@Query("pageNo") Integer pageNo,
                                                  @Query("pageSize") Integer pageSize,
                                                  @Query("isTrailers") Integer isTrailers,
                                                  @Query("isNew") Integer isNew,
                                                  @Query("token") String token);
}