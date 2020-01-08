package com.example.administrator.zahbzayxy.interfaceserver;

import com.example.administrator.zahbzayxy.beans.AllHaveDoTestBean;
import com.example.administrator.zahbzayxy.beans.ExamResultBean;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import com.example.administrator.zahbzayxy.beans.PrictaceErrorBean;
import com.example.administrator.zahbzayxy.beans.SearchTestBean;
import com.example.administrator.zahbzayxy.beans.TestCommitBean;
import com.example.administrator.zahbzayxy.beans.TestContentBean;
import com.example.administrator.zahbzayxy.beans.TestDetailBean;
import com.example.administrator.zahbzayxy.beans.TestGradAnalyseBean;
import com.example.administrator.zahbzayxy.beans.TestIsBuyBean;
import com.example.administrator.zahbzayxy.beans.TestNavigationBean;
import com.example.administrator.zahbzayxy.beans.TestPracticeBean;
import com.example.administrator.zahbzayxy.beans.TestSecondListBean;
import com.example.administrator.zahbzayxy.beans.TestSubmitOrderBean;
import com.example.administrator.zahbzayxy.beans.TestWaitPayDataBean;
import com.example.administrator.zahbzayxy.beans.WeChatPayBean;
import com.example.administrator.zahbzayxy.beans.YouHuiJuanListBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ${ZWJ} on 2017/3/16 0016.
 */
public interface TestGroupInterface {
    static final String groupTestPath="quesLib/cateTree_v1";
    static final String testClassPath="quesLib/quesLibPage";
    static final String testDetailPath="quesLib/quesLibDetail";
    static final String testIsBuyPath="quesLib/userIsBuy";
    static final String testContentPath="quesLib/examPaper_v1";
    static final String saveExamScorePath="userExam/saveExamScore";
    static final String testPracticePath="ques/syncQues";
    static final String testSubmitOrderPath="shopOrder/submitQuesLibOrder";
    String youHuiJuanListPath="shopOrder/quesLibOrderDetail";
    static final String testWaitPayDataPath="alipay/prePay";
    static final String weChatPayPath="wxpay/prePay";
    static final String tiKuDingDanPath="userCenter/quesLibOrderPage";
    static final String lookErrorPath="userExam/errorRecords";

   /* //我的题库一级页面
    @GET(value = groupTestPath)
    Call<TestGroupBean> getTestGroupData();*/
    //我的题库二级列表展示的接口
    @GET(value=testClassPath)
    Call<TestSecondListBean> getTestListData( @Query("pCateId") Integer pCateId,
                                             @Query("pageSize") Integer pageSize,@Query("currentPage") Integer currentPage, @Query("subCateId") Integer subCateId);
    @GET(value = testDetailPath)
    Call<TestDetailBean> getTestDetailData(@Query("quesLibId") Integer quesLibId);
    @GET(value=testIsBuyPath)
    Call<TestIsBuyBean>getTestIsBuyData(@Query("quesLibId")Integer quesLibId,@Query("token")String token);

    @GET(value=testContentPath)
    Call<TestContentBean>getTestContentData(@Query("userLibId")Integer userLibId,
                                            @Query("quesLibId")Integer quesLibId,
                                            @Query("token")String token,
                                            @Query("examType") int examType);

//保存用户成绩
    @FormUrlEncoded
    @POST(value=saveExamScorePath)
    Call<TestCommitBean>getExamScoreData1(@FieldMap Map<String,String> saveScore);
//查看错题
    @GET(value=testPracticePath)
    Call<TestPracticeBean> getTestPracticeData(@Query("quesLibId")Integer quesLibId, @Query("token")String token);


    //查看错题新packageId
    @GET(value=lookErrorPath)
    Call<PrictaceErrorBean> getTestPracticeData1(@Query("quesLibId")Integer quesLibId,@Query("userLibId")Integer userLibId,@Query("libPackageId")Integer libPackageId
            , @Query("token")String token);
//提交题库订单
    @GET(value=testSubmitOrderPath)
    Call<TestSubmitOrderBean> getTestSubmitOrderData(@Query("couponId")Integer 	couponId,@Query("quesLibId")Integer quesLibId,@Query("quesLibPackageId")Integer	quesLibPackageId, @Query("token")String token);

    //支付宝
    @POST(value=testWaitPayDataPath)
    Call<TestWaitPayDataBean> getTestWaitPayData(@Query("ordernum")String ordernum, @Query("token")String token);
//微信支付
    @FormUrlEncoded
    @POST(value=weChatPayPath)
    //Call<WeChatPayBean> getTestWeChatPayData(@Query("ordernum")String ordernum, @Query("token")String token);
    Call<WeChatPayBean> getTestWeChatPayData(@FieldMap Map<String,String>weChatPayMap);


    //题库订单
    @GET(value=tiKuDingDanPath)
    Call<TestSecondListBean> getTestDingDanData(
            @Query("currentPage") Integer currentPage,@Query("pageSize") Integer pageSize, @Query("token") String token);


    static final String groupTestPath1="quesLib/cateTree";
    //我的题库导航界面
    @GET(value = groupTestPath)
    Call<TestNavigationBean> getTestNavigationData(@Query("token") String token);
    //我的题库一级页面
    @GET(value = groupTestPath)
    Call<TestNavigationBean> getTestGroupData(@Query("token")String token,@Query("courseId")Integer courseId);

    static final String testResultAnalyslisPath="userExam/examScoreAnalysis_v1";
    //考试试卷结果分析页
    @GET(value=testResultAnalyslisPath)
    Call<TestGradAnalyseBean> getTestAnalyslisPath(@Query("examScoreId")Integer examScoreId, @Query("token")String token);



    static final String testResultLookPath="/userExam/examScoreQues";
    //考试试卷结果分析页
    @GET(value=testResultLookPath)
    Call<AllHaveDoTestBean> getTestResultPath(@Query("examScoreId")Integer examScoreId, @Query("token")String token);

    //获取优惠卷
    @FormUrlEncoded
    @POST(value=youHuiJuanListPath)
    Call<YouHuiJuanListBean> getYouhuiJuanData(@Field("quesLibId") Integer quesLibId, @Field("quesLibPackageId") Integer quesLibPackageId, @Field("token") String token);



    static final String searchTestPath="/esearch/get";
    //考试试卷结果分析页
    @GET(value=searchTestPath)
    Call<SearchTestBean> getSearchTestResultPath(@Query("token")String token,
                                                 @Query("keyWord")String keyWord,
                                                 @Query("quesLibId")int quesLibId,
                                                 @Query("type")int type ,
                                                 @Query("pageSize")int pageSize,
                                                 @Query("currentPage")int currentPage);

    //考试结果
    static final String getResult = "userExam/examResult";
    @POST(value = getResult)
    Call<ExamResultBean> getExamResult(@Query("token") String token, @Query("examScoreId") int examScoreId);

    //学习导航
    static final String LearnNavigationData = "/data/usercenter/course/cate_online";
    @POST(value = LearnNavigationData)
    Call<LearnNavigationBean> getLearnNavigationData(@Query("isAchieve") Integer isAchieve,
                                                     @Query("token") String token);
}
