package com.example.administrator.zahbzayxy.interfacecommit;

import android.app.Person;

import com.example.administrator.zahbzayxy.beans.MyAccountFlowBean;
import com.example.administrator.zahbzayxy.beans.MyAmountBean;
import com.example.administrator.zahbzayxy.beans.OneCunBean;
import com.example.administrator.zahbzayxy.beans.PUserHeadPhotoBean;
import com.example.administrator.zahbzayxy.beans.PayCardBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
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

    //附件上传图片
    static final String updateFile = "user/attachment/upload";
    @Multipart
    @POST(value = updateFile)
    Call<OneCunBean> updateFile(@Query("token") String token,
                                @Part MultipartBody.Part oneInchPhoto);

    //账户余额
    static final String getMyAmmountPath="user/account/balance";
    @GET(value =getMyAmmountPath)
    Call<MyAmountBean>getMyAmmountData(@Query("token") String token);

    //查看用户错题记录
    static final String accountFlowPath = "user/account/flow";
    @GET(value = accountFlowPath)
    Call<MyAccountFlowBean> MyAccountFlowData(@Query("currentPage") Integer currentPage, @Query("pageSize") Integer pageSize, @Query("token") String token);
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
}
