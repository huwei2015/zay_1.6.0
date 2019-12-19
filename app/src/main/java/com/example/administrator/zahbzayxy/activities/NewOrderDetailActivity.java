package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.NBOrderDetailBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.interfaceserver.NewMyOrderInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewOrderDetailActivity extends BaseActivity {

    private TextView zhuLessonName_tv,createTime_tv,orderStatus_tv,payMethod_tv,accont_tv,accountNum_tv,phone_tv;
    private LinearLayout ziLesson_layout,zhuLessonName_ll,orderStatus_ll;
    private String token;
    private int orderId;
    private TextView orderDetailType_tv;
    private ImageView lessonOrder_iv;
    private TextView ziKeDaoHang_tv;
    private TextView orderDeatilNum_tv,priceOrderDetail_tv;
    private TextView deleteOrder_tv,cancleOrder_tv,goPayOrder_tv,buyAgin_tv;
    String orderNumber;
    double payMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nborder_detail);
        initView();
        downLoadData();
        initOrder();
    }


    private void downLoadData() {
        NewMyOrderInterface aClass = RetrofitUtils.getInstance().createClass(NewMyOrderInterface.class);
        Log.e("orderId",orderId+"");
        if (String.valueOf(orderId)!=null&&!TextUtils.isEmpty(token)) {
            aClass.getOrderDetailData(orderId, token).enqueue(new Callback<NBOrderDetailBean>() {
                private int orderType;

                @Override
                public void onResponse(Call<NBOrderDetailBean> call, Response<NBOrderDetailBean> response) {
                    NBOrderDetailBean body = response.body();
                    if (body != null) {
                        String code = body.getCode();
                        if (code.equals("99999")) {
                            Toast.makeText(NewOrderDetailActivity.this, "网络繁忙", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            NBOrderDetailBean.DataEntity data = body.getData();
                            if (data != null) {
                                orderType = data.getOrderType();
                                payMoney= data.getPayMoney();
                                priceOrderDetail_tv.setText("价格:"+"¥"+payMoney);
                                orderNumber= data.getOrderNumber();
                                if (!TextUtils.isEmpty(orderNumber)){
                                    orderDeatilNum_tv.setText("单号:"+orderNumber);
                                }

                                if (orderType==5){//课程订单
                                    orderDetailType_tv.setText("课程订单");
                                    lessonOrder_iv.setVisibility(View.VISIBLE);
                                    List<NBOrderDetailBean.DataEntity.DetailEntity> detail = data.getDetail();
                                    if (detail!=null){
                                        int size = detail.size();
                                        if (size>0){
                                            String logo = detail.get(0).getLogo();
                                            if (!TextUtils.isEmpty(logo)) {
                                                Picasso.with(NewOrderDetailActivity.this).load(logo).placeholder(R.mipmap.loading_png).into(lessonOrder_iv);
                                            }
                                        }
                                    }
                                }else if (orderType==8){//题库订单
                                    orderDetailType_tv.setText("题库订单");
                                    lessonOrder_iv.setVisibility(View.GONE);
                                    ziKeDaoHang_tv.setVisibility(View.GONE);
                                }
                                List<NBOrderDetailBean.DataEntity.DetailEntity> detail = data.getDetail();
                                if (detail.size() > 0) {
                                    String name = detail.get(0).getName();
                                    Log.e("detail", detail + "");
                                    zhuLessonName_tv.setText(name);
                                }if (detail.size()<=0){
                                    zhuLessonName_ll.setVisibility(View.GONE);
                                    orderStatus_ll.setVisibility(View.GONE);
                                    ziLesson_layout.setVisibility(View.GONE);

                                }
                                String createTime = data.getCreateTime();
                                createTime_tv.setText("时间:" + createTime);

                                int orderStatus = data.getOrderStatus();

                                if (orderStatus == 1) {
                                    orderStatus_tv.setText("支付成功");
                                    cancleOrder_tv.setVisibility(View.GONE);
                                    goPayOrder_tv.setVisibility(View.GONE);
                                    buyAgin_tv.setVisibility(View.GONE);
                                }else {
                                    if (orderStatus==0) {
                                        orderStatus_tv.setText("待支付");
                                        cancleOrder_tv.setVisibility(View.VISIBLE);
                                      //  goPayOrder_tv.setVisibility(View.VISIBLE);
                                        buyAgin_tv.setVisibility(View.GONE);
                                    }else {//已取消
                                        orderStatus_tv.setText("已取消");
                                        cancleOrder_tv.setVisibility(View.GONE);
                                        goPayOrder_tv.setVisibility(View.GONE);
                                        buyAgin_tv.setVisibility(View.GONE);
                                    }
                                }
                                int payType = data.getPayType();
                                if (String.valueOf(payType) != null) {
                                    if (payType == 0) {
                                        payMethod_tv.setText("支付方式:线下支付");

                                    }
                                    if (payType == 1) {
                                        payMethod_tv.setText("支付方式:线上支付");

                                    }
                                    if (payType == 2) {
                                        payMethod_tv.setText("支付方式:网银");


                                    }
                                    if (payType == 3) {
                                        payMethod_tv.setText("支付方式:支付宝");


                                    }
                                    if (payType == 4) {
                                        payMethod_tv.setText("支付方式:微信支付");


                                    }
                                    if (payType == 5) {
                                        payMethod_tv.setText("支付方式:账户余额");

                                    }
                                }
                                String companyAccount = data.getCompanyAccount();
                                String companyPhone = data.getCompanyPhone();
                                String bankName = data.getBankName();
                                if (!TextUtils.isEmpty(bankName)) {
                                    accountNum_tv.setText("开户行:" + bankName);
                                }
                                if (!TextUtils.isEmpty(companyAccount)) {
                                    accont_tv.setText("账号:" + companyAccount);

                                }
                                if (!TextUtils.isEmpty(companyPhone)) {
                                    phone_tv.setText("咨询电话:" + companyPhone);

                                }
                                List<List<String>> allChild=new ArrayList<>();
                                if (detail.size() > 0) {
                                    ziLesson_layout.setVisibility(View.VISIBLE);
                                    for (int i=0;i<detail.size();i++){
                                        List<String> child = detail.get(i).getChild();
                                        allChild.add(child);
                                    }
                                    int size1 = allChild.size();
                                    if (size1>0) {
                                        for (int i = 0; i < size1; i++) {
                                            List<String> strings1 = allChild.get(i);
                                            if (strings1!=null) {
                                                int size = strings1.size();
                                                for (int j = 0; j < size; j++) {
                                                    String s1 = strings1.get(j);
                                                    TextView textView = new TextView(NewOrderDetailActivity.this);
                                                    textView.setText(s1);
                                                    textView.setPadding(30, 7, 30, 7);
                                                    ziLesson_layout.addView(textView);
                                                }
                                            }
                                        }
                                    }else {
                                        ziLesson_layout.setVisibility(View.GONE);
                                        zhuLessonName_ll.setVisibility(View.GONE);
                                        orderStatus_ll.setVisibility(View.GONE);

                                    }



                                }
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<NBOrderDetailBean> call, Throwable t) {

                }
            });
        }
    }

    private void initView() {
        zhuLessonName_tv= (TextView) findViewById(R.id.nb_zhuLessonName_tv);
        createTime_tv= (TextView) findViewById(R.id.nb_orderDetail_creatTime_tv);
        orderStatus_tv= (TextView) findViewById(R.id.nb_orderDetail_orderStatus_tv);
        payMethod_tv= (TextView) findViewById(R.id.nb_payMethod_tv);
        accont_tv= (TextView) findViewById(R.id.nb_account_tv);
        accountNum_tv= (TextView) findViewById(R.id.nb_accountNum_tv);
        phone_tv= (TextView) findViewById(R.id.nb_ziXunPhone_tv);
        token=getIntent().getStringExtra("token");
        orderId = getIntent().getIntExtra("orderId",0);
        zhuLessonName_ll= (LinearLayout) findViewById(R.id.lessonDetail_top_ll);
        ziLesson_layout= (LinearLayout) findViewById(R.id.nb_orderDetail_ziLessonName_ll);
        orderStatus_ll= (LinearLayout) findViewById(R.id.orderCreteAndStatus_ll);
        orderDetailType_tv= (TextView) findViewById(R.id.orderDetailType_tv);
        lessonOrder_iv= (ImageView) findViewById(R.id.zhuLesson_iv);
        ziKeDaoHang_tv= (TextView) findViewById(R.id.zike_daohang_tv);
        orderDeatilNum_tv= (TextView) findViewById(R.id.orderNum_Detail_tv);
        priceOrderDetail_tv= (TextView) findViewById(R.id.orderDetail_price_tv);
        deleteOrder_tv= (TextView) findViewById(R.id.delete_order_detail_tv);
        cancleOrder_tv= (TextView) findViewById(R.id.cancleOrder_detail_tv);
        goPayOrder_tv= (TextView) findViewById(R.id.goPay_orderDetail_tv);
        buyAgin_tv= (TextView) findViewById(R.id.buyAgin_orderDetail_tv);


    }

    public void myOrderDetailOnClick(View view) {
        finish();
    }
    private void initOrder() {
        deleteOrder_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                aClass.getMyLessonOrderDeleteData(orderNumber, token).enqueue(new Callback<SuccessBean>() {
                    @Override
                    public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                        SuccessBean body = response.body();
                        if (body != null && body.getErrMsg() == null) {
                            boolean data = body.getData();
                            if (data == true) {
                                Toast.makeText(NewOrderDetailActivity.this, "删除订单成功", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(NewOrderDetailActivity.this, "删除订单失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessBean> call, Throwable t) {

                    }
                });
            }
        });

cancleOrder_tv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyLessonOrderQuXiaoData(orderNumber,token).enqueue(new Callback<SuccessBean>() {
            @Override
            public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                SuccessBean body = response.body();
                String code= body.getCode();
                boolean data = body.getData();
                if (data == true) {
                    Toast.makeText(NewOrderDetailActivity.this, "取消订单成功", Toast.LENGTH_SHORT).show();
                   cancleOrder_tv.setVisibility(View.GONE);
                   goPayOrder_tv.setVisibility(View.GONE);
                    //buyAgin_tv.setVisibility(View.VISIBLE);

                }else if (code.equals("99999")){
                    Toast.makeText(NewOrderDetailActivity.this, "系统异常", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(NewOrderDetailActivity.this, "取消订单失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuccessBean> call, Throwable t) {
                Toast.makeText(NewOrderDetailActivity.this, "网络问题，请检查网络状态", Toast.LENGTH_SHORT).show();
            }
        });
    }
});
        buyAgin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrderDetailActivity.this, PayUiActivity.class);
                intent.putExtra("orderNumber", orderNumber);
                intent.putExtra("testPrice", String.valueOf(payMoney));
                startActivity(intent);
            }
        });
        goPayOrder_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewOrderDetailActivity.this, PayUiActivity.class);
                intent.putExtra("orderNumber", orderNumber);
                intent.putExtra("testPrice", String.valueOf(payMoney));
                startActivity(intent);
            }
        });

    }




}

