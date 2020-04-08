package com.example.administrator.zahbzayxy.interfacecommit;

import com.example.administrator.zahbzayxy.beans.AuthStateBean;
import com.example.administrator.zahbzayxy.beans.AutoFaceBean;
import com.example.administrator.zahbzayxy.beans.HasAuthorBean;
import com.example.administrator.zahbzayxy.beans.MyAccountFlowBean;
import com.example.administrator.zahbzayxy.beans.MyAmountBean;
import com.example.administrator.zahbzayxy.beans.NotPassBean;
import com.example.administrator.zahbzayxy.beans.NotThroughBean;
import com.example.administrator.zahbzayxy.beans.OneCunBean;
import com.example.administrator.zahbzayxy.beans.PUserHeadPhotoBean;
import com.example.administrator.zahbzayxy.beans.PayCardBean;
import com.example.administrator.zahbzayxy.beans.SignBean;
import com.example.administrator.zahbzayxy.beans.StayAuthorBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.beans.TimeData;
import com.example.administrator.zahbzayxy.beans.UpBean;
import com.example.administrator.zahbzayxy.beans.UpdateBean;
import com.example.administrator.zahbzayxy.beans.UserCenter;
import com.example.administrator.zahbzayxy.beans.UserInfoBean;
import com.example.administrator.zahbzayxy.beans.UserInfoResetBean;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.vo.UploadInfo;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by ${ZWJ} on 2017/3/10 0010.
 */
public interface UserInfoInterface {
    //获取最初的用户信息
    static final String getUserInfoPath="/userCenter/getUserInfo";
    @GET(value = getUserInfoPath)
    Call<UserInfoBean> getUserInfoData(@Query("token") String token);

    //更新用户信息
    static final String updataUserInfoPath="/userCenter/updateInfo";

    /* @GET(value =updataUserInfoPath)
    Call<UserInfoResetBean>getUpdateUserInfoData(@Query("token") String token
             , @Query("updateInfo") String updateInfo, @Query("updateType") Integer updateType);
*/
    //更新用户信息
      @FormUrlEncoded
      @POST(value =updataUserInfoPath)
     Call<UserInfoResetBean>getUpdateUserInfoData(@FieldMap Map<String,Object>editMessage);
   //上传用户头像
    static final String setUserPhotoPath="/userCenter/uploadPhoto";
    @Multipart
    @POST(value = setUserPhotoPath)
    Call<PUserHeadPhotoBean>getUserPhotoData(@Part MultipartBody.Part photo, @Query("token") String token);
    //上传人脸识别图片
    @Multipart
    @POST(value = Constant.UPLOAD_PORTRAIT_URL)
    Call<UploadInfo>uploadPortrait(@Part MultipartBody.Part photo, @Query("token") String token);
    //一寸照片上传
    static final String updateOneCun = "userCenter/uploadOneInchPhoto";
    @Multipart
    @POST(value = updateOneCun)
    Call<OneCunBean> updatePhoto(@Query("token") String token,
                                 @Part MultipartBody.Part oneInchPhoto);
    //学历证书上传
    static final String certificate = "/userCenter/uploadEduCer";
    @Multipart
    @POST(value = certificate)
    Call<UpBean> getCertificateData(@Part MultipartBody.Part eduCer,
                                    @Query("token") String token);
    //上传身份正面
    static final String uploadIdCardFront ="/userCenter/uploadIdCardFront";
    @Multipart
    @POST(value = uploadIdCardFront)
    Call<UpBean> getIdCardFrontData(@Part MultipartBody.Part idCardFront,
                               @Query("token") String token);

    //上传身份反面
    static final String uploadIdCardBack ="/userCenter/uploadIdCardBack";
    @Multipart
    @POST(value = uploadIdCardBack)
    Call<UpBean> getIdCardBackData(@Part MultipartBody.Part idCardBack,
                               @Query("token") String token);

    //附件上传图片
    static final String updateFile = "user/attachment/upload";
    @Multipart
    @POST(value = updateFile)
    Call<UpdateBean> updateFile(@Query("token") String token,
                                @Part MultipartBody.Part oneInchPhoto);

    //账户余额
    static final String getMyAmmountPath="user/account/balance";
    @GET(value =getMyAmmountPath)
    Call<MyAmountBean>getMyAmmountData(@Query("token") String token);

    //查看用户错题记录
    static final String accountFlowPath = "user/account/flow";
    @GET(value = accountFlowPath)
    Call<MyAccountFlowBean> MyAccountFlowData(@Query("currentPage") Integer currentPage,
                                              @Query("pageSize") Integer pageSize,
                                              @Query("token") String token);
   //账户余额支付
    static final String accountPayPath = "accountPay/toPay";

//    @FormUrlEncoded
//    @POST(value = accountPayPath)
//    Call<SuccessBean>getAccoutPayData(@Query("orderNumber") String orderNumber
//            , @Query("payPassword") String payPassword, @Query("token") String token);

    @FormUrlEncoded
    @POST(value = accountPayPath)
    Call<SuccessBean>getAccoutPayData(@FieldMap Map<String,String>yu);

    static final String renZhengPath = "CourseController/user/courseCerPrint2";
    @FormUrlEncoded
    @POST(value = renZhengPath)
    Call<ResponseBody> downloadFile(@FieldMap Map<String,Object> saveScore);

//学习卡
    static final String studyCardPath = "accountPay/learncardPay";
    @FormUrlEncoded
    @POST(value = studyCardPath)
    Call<PayCardBean> studyCard(@FieldMap Map<String,String>cardNum);
    //Call<SuccessBean> studyCard(@Query("cardCode") String cardCode,@Query("orderNumber") String orderNumber, @Query("token") String token);

    //个人中心
    static final String userCenter = "userCenter";
    @POST(value = userCenter)
    Call<UserCenter> getUserCenter(@Query("token") String token);

    //授权列表
    static final String auth = "order/auth/list";
    @POST(value = auth)
    Call<HasAuthorBean> getAuthData(@Query("token") String token,
                                    @Query("orderStatus") int orderStatus,
                                    @Query("pageNo") int pageNo,
                                    @Query("pageSize") int pageSize,
                                    @Query("orderNumber") String orderNumber);

    //授权列表
    static final String un_auth = "order/auth/list";
    @POST(value = un_auth)
    Call<StayAuthorBean> getUnAuthData(@Query("token") String token,
                                       @Query("orderStatus") int orderStatus,
                                       @Query("pageNo") int pageNo,
                                       @Query("pageSize") int pageSize,
                                       @Query("orderNumber") String orderNumber);

    //授权状态接口
    static final String auth_state = "order/auth";
    @POST(value = auth_state)
    Call<AuthStateBean> getStateData(@Query("token") String token, @Query("orderNumber") String orderNumber);

    //系统消息
    static final String system_msg= "/announcement/announcementByPage";
    @GET(value = system_msg)
    Call<TimeData> getSystemMsg(@Query("pageNo") int pageNo,
                                @Query("pageSize") int pageSize,
                                @Query("classifyId") int classifyId,
                                @Query("token") String token,
                                @Query("sysMsglist") String sysMsglist);

    //我的报名
    static final String my_sign ="/data/usercenter/apply/list";
    @GET(value = my_sign)
    Call<SignBean> getSignData(@Query("pageNo") int pageNo,
                               @Query("pageSize") int pageSize,
                               @Query("token") String token);

    //模拟题库选择
    static final String simulation_question ="/data/usercenter/queslib/queslib_listmock";
    @POST(value = simulation_question)
    Call<NotThroughBean> getQuestionData(@Query("pageNo") int pageNo,
                                         @Query("pageSize") int pageSize,//套餐名称
                                         @Query("cateId") int cateId,//题库id
                                         @Query("isTimeEnd") String isTimeEnd,
                                         @Query("token") String token);
    //考试-正式考试
    static final String exam_formal="/data/usercenter/queslib/queslib_listexam";
    @POST(value = exam_formal)
    Call<NotPassBean> getExamData(@Query("pageNo") int pageNo,
                                  @Query("pageSize") int pageSize,
                                  @Query("isScorePass") int isScorePass,
                                  @Query("token") String token);

    //采集人脸接口
    static final String auto_face ="/verify/face";
    @Multipart
    @POST(value = auto_face)
    Call<AutoFaceBean> getAutoFace(@Query("sectionId") int sectionId,
                                   @Query("userCourseId") int userCourseId,
                                   @Query("playTime") int playTime,
                                   @Query("token") String token,
                                   @Part MultipartBody.Part recognitionImg);
}
