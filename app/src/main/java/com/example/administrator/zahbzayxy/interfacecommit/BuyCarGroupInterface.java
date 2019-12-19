package com.example.administrator.zahbzayxy.interfacecommit;

import com.example.administrator.zahbzayxy.beans.BuyCarListBean;
import com.example.administrator.zahbzayxy.beans.BuyInstanceBean;
import com.example.administrator.zahbzayxy.beans.LessonPriceBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.beans.TestSubmitOrderBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ${ZWJ} on 2017/3/26 0026.
 */
public interface BuyCarGroupInterface {
     String buyCarAddCourse = "shopCart/addCourse?";
    String buyCarListCourse = "shopCart/coursePage?";
     String buyCarRemoveCourse = "shopCart/removeCourse?";
     String buyCarGetPrice = "shopOrder/getCoursePrice?";
     String submitLessonOrderPath = "shopOrder/submitCourseOrder";
     String buyInstancePath = "shopOrder/buyCourseOrder";

    //添加购物车课程
    @GET(value = buyCarAddCourse)
    Call<SuccessBean> buyCarAddCourseData(@Query("mainCourseId") Integer mainCourseId, @Query("subCourseIds") String[] subCourseIds, @Query("token") String token);

    //购物车列表展示
    @GET(value = buyCarListCourse)
    Call<BuyCarListBean> buyCarListData(@Query("currentPage") Integer currentPage, @Query("pageSize") Integer pageSize, @Query("token") String token);

    //删除购物车条目
    @FormUrlEncoded
    @POST(value = buyCarRemoveCourse)
    //Call<SuccessBean> buyCarDeleteData(@Query("courseIds") String courseIds, @Query("token") String token);
    Call<SuccessBean> buyCarDeleteData(@FieldMap Map<String,String>deleteBuyCar);


    //获取提交订单时的课程价格
    @FormUrlEncoded
    @POST(value = buyCarGetPrice)
    Call<LessonPriceBean>buyCarGetPriceData(@FieldMap Map<String,String>buyCarPrice);
    //提交课程订单
    @FormUrlEncoded
    @POST(value = submitLessonOrderPath)
   // Call<TestSubmitOrderBean> commitLessonOrderData(@Query("List") JSONArray List, @Query("price") String price, @Query("token") String token);
    Call<TestSubmitOrderBean> commitLessonOrderData(@FieldMap Map<String,String>getLessonOrderNum);
    //立即购买
    @GET(value = buyInstancePath)
    Call<BuyInstanceBean> getBuyInstanceData(@Query("courseId") Integer courseId, @Query("token") String token);
}