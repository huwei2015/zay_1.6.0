package com.example.administrator.zahbzayxy.interfaceserver;

import com.example.administrator.zahbzayxy.beans.NBMyAllOrderBean;
import com.example.administrator.zahbzayxy.beans.NBOrderDetailBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ${ZWJ} on 2017/7/11 0011.
 */
public interface NewMyOrderInterface {

    //全部订单
    static final String allOrderPath="user/order/list";
    @GET(value=allOrderPath)
    Call<NBMyAllOrderBean> getAllOrderData(@Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize, @Query("token") String token);

    @GET(value=allOrderPath)
    Call<ResponseBody> getAllOrderData1(@Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize, @Query("token") String token);

    //是否支付订单
    @GET(value=allOrderPath)
    Call<NBMyAllOrderBean>payStatusOrderData(@Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize,
                                             @Query("token") String token, @Query("payStatus") Integer payStatus);

    //已取消订单
    @GET(value=allOrderPath)
    Call<NBMyAllOrderBean>haveCancleOrderData(@Query("pageNo") Integer pageNo, @Query("pageSize") Integer pageSize,
                                              @Query("token") String token, @Query("orderStatus") Integer orderStatus);

    //订单详情
    static final String orderDetailPath="user/order/detail";
    @GET(value=orderDetailPath)
    Call<NBOrderDetailBean> getOrderDetailData(@Query("orderId") Integer orderId, @Query("token") String token);



}
