package com.example.administrator.zahbzayxy.interfacecommit;
import com.example.administrator.zahbzayxy.beans.ExamBean;
import com.example.administrator.zahbzayxy.beans.IsShowAgreement;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiBean;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiListBean;
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
import com.example.administrator.zahbzayxy.beans.SaveArgeement;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.beans.YouHuiJuanBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
/**
 * Created by Administrator on 2017/4/3.
 */

public interface PersonGroupInterfac {
    //我的课程购买列表
    static final String myLessonPath="CourseController/myCourseList";
    @GET(value =myLessonPath)
    Call<PMyLessonBean> getPMyLessonData(@Query("pageNo") Integer pageNo,
                                         @Query("pageSize") Integer pageSize, @Query("token") String token);
    //我的课程播放目录列表

    static final String myLessonPlayPath="CoursePlayController/CoursePlayList";
    @GET(value =myLessonPlayPath)
    Call<PMyLessonPlayBean> getPMyLessonPlayData(@Query("courseId") Integer courseId,
                                                 @Query("token") String token,
                                                 @Query("userCourseId") Integer userCourseId);
    //获取免费课程目录列表
    static final String freePlayPath = "CoursePlayController/freeCourseplay";
    @POST(value = freePlayPath)
    Call<PMyLessonPlayBean> getFreePlayData(@Query("courseId") Integer courseId,
                                            @Query("courseType") Integer courseType,
                                            @Query("token") String token);

    //记录我的课程播放时长
    static final String myLessonPlayTimePath="CoursePlayController/writePlayRecord";
    @GET(value =myLessonPlayTimePath)
    Call<PLessonPlayTimeBean> getPMyLessonPlayTimeData(@Query("playTime") Integer playTime,
                                                       @Query("sectionId") Integer sectionId,
                                                       @Query("token") String token,
                                                       @Query("userCourseId")Integer userCourseId );

    //我的课程订单
   // static final String myLessonOrderPath="shopOrder/myCourseOrderList";
    static final String myLessonOrderPath="user/order/list";
    @GET(value =myLessonOrderPath)
    Call<PMyLessonOrderBean> getMyLessonOrderData(@Query("pageNo") Integer pageNo,
                                                  @Query("pageSize") Integer pageSize,
                                                  @Query("token") String token,
                                                  @Query("orderType")Integer 	orderType);
    //取消订单
    static final String myLessonOrderQuXiaoPath="userCenter/cancelOrder";
    @GET(value =myLessonOrderQuXiaoPath)
    Call<SuccessBean> getMyLessonOrderQuXiaoData(@Query("orderNumber")String orderNumber,
                                                 @Query("token") String token);
    //删除订单
    static final String myLessonOrderDeletePath="userCenter/deleteOrder";
    @GET(value =myLessonOrderDeletePath)
    Call<SuccessBean> getMyLessonOrderDeleteData(@Query("orderNumber")String orderNumber,
                                                 @Query("token") String token);
    //我的题库订单
    static final String myTestOrderPath="userCenter/quesLibOrderPage";
    @GET(value =myTestOrderPath)
    Call<PMyTestOrderBean> getMyTestOrderData(@Query("pageNo") Integer pageNo,
                                              @Query("pageSize") Integer pageSize,
                                              @Query("token") String token);

    //我的题库
    static final String myTiKuPath="userCenter/quesLibPage";
    @GET(value =myTiKuPath)
    Call<PersonTiKuListBean> getMyTiKuData(@Query("currentPage") Integer currentPage,
                                           @Query("pageSize") Integer pageSize,
                                           @Query("token") String token);

    //我的认证列表
    static final String myRenZhengPath="CourseController/myCerList";
    @GET(value =myRenZhengPath)
    Call<PMyRenZhengMuLuBean> getMyRenZhengColumData(@Query("pageNo") Integer pageNo,
                                                     @Query("pageSize") Integer pageSize,
                                                     @Query("token") String token);
    //我的认证详情
    static final String myRenZhengDetailPath="CourseController/myCertification";
    @GET(value =myRenZhengDetailPath)
    Call<PMyRenZhengDetailBean> getMyRenZhengDetailData(@Query("token") String token,
                                                        @Query("userCourseId")Integer userCourseId);

    //我的考试成绩列表
    static final String myTestGradePath1="userExam/examScorePage";
    @GET(value =myTestGradePath1)
    Call<PMyTestGradBean> getMyTiKuGradeData(@Query("currentPage") Integer currentPage,
                                             @Query("pageSize") Integer pageSize,
                                             @Query("token") String token);


    //我的课程购买列表
    static final String newMyChengJiPath="userExam/score/Ranking";
    @GET(value =newMyChengJiPath)
    Call<NewMyChengJiBean> getNewMyChengJiData(@Query("token") String token,
                                               @Query("examType") int examType);


    //新的我的考试成绩列表
    static final String myTestGradePath="userExam/libExamScorePage";
    @GET(value =myTestGradePath)
    Call<NewMyChengJiListBean> getMyTiKuGradeData(@Query("currentPage") Integer currentPage,
                                                  @Query("pageSize") Integer pageSize,
                                                  @Query("token") String token,
                                                  @Query("libId")Integer libId,
                                                  @Query("examType") int examType);


//wodeyouihuijuan
    String newMyYouHuiJuanPath="userCenter/userCoupon";
    @FormUrlEncoded
    @POST(value =newMyYouHuiJuanPath )
    Call<YouHuiJuanBean>getMyYouHuiJuanData(@Field("status")Integer status,
                                            @Field("token")String token);

    String orderIsOutOfDatePath="shopOrder/payLibCouponCheck";
    @FormUrlEncoded
    @POST(value =orderIsOutOfDatePath)
    Call<OrderIsOutOfDateBean>getOrderIsOutOfDateData(@Field("orderNumber")String orderNumber,
                                                      @Field("token")String token);

    //我的考试列表
    String myExamlist ="userCenter/myExamList";
    @POST(value =myExamlist)
    Call<ExamBean> getExamList(@Query("token") String token,
                               @Query("currentPage") int currentPage,
                               @Query("pageSize") int pageSize);

    //是否完善个人信息接口看视频
    String isPersonInfo = "/CourseController/isNeedPerfectUserInfo";
    @POST(value = isPersonInfo)
    Call<PersonInfo> getPersonInfo(@Query("token") String token,
                                   @Query("userCourseId") int userCourseId);

    //是否完善个人信息
    String isPersonExam = "/CourseController/isNeedPerfectUserInfo";
    @POST(value = isPersonExam)
    Call<PersonInfo> getPersonExam(@Query("token") String token,
                                   @Query("userQuesLibId") int userQuesLibId );

    //是否展示人脸采集协议
    String isShow ="/face/agreement/isShow";
    @POST(value = isShow)
    Call<IsShowAgreement> isShowAgreement(@Query("userCourseId") int userCourseId,
                                          @Query("token") String token);

    //保存人脸采集协议
    String saveAgreement ="/face/agreement/save";
    @POST(value = saveAgreement)
    Call<SaveArgeement> getSaveAgreement(@Query("userCourseId") int userCourseId,
                                         @Query("token") String token);

    //自动采集人脸上传
//    String autoFace =" /verify/face";
//    @Multipart
//    @POST(value = autoFace)
//    Call<SaveArgeement> getAutoFace(@Query("sectionId") int sectionId,
//                       @Query("userCourseId") int userCourseId,
//                       @Query("playTime") int playTime,
//                       @Part MultipartBody.Part autoFace);
}