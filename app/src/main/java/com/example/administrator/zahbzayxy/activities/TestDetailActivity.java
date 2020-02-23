package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TestDetailBean;
import com.example.administrator.zahbzayxy.beans.TestIsBuyBean;
import com.example.administrator.zahbzayxy.beans.TestSubmitOrderBean;
import com.example.administrator.zahbzayxy.beans.YouHuiJuanListBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.DateUtil;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 免费考试
 */
public class TestDetailActivity extends BaseActivity {
    private int quesLibId;
    private ImageView testDetail_iv,back_testDetail_iv;
    private TextView testDetailName_tv,testDetailPrice_tv,tiku_retroduce_tv,testLiberayTitle_tv;
    private String token;
    private RelativeLayout haveBuy_ly,noBuy_ly;
    private Button testBuyNow_bt,test_bt;
    private String quesLibImageUrl;
    private String quesLibName;
    private String sPrice;
    private ProgressBarLayout mLoadingBar;
    private Button taoCanA_bt,taoCanB_bt,taoCanC_bt;
    private int quesLibPackageId;
    private ImageView taocan_chart_iv;
    private String packageImage;
    View popView;
    PopupWindow popupWindow;
    private Boolean isOrder=false;
    private int isMechanism;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_liberary_detail);
        initView();
        initFinish();
        initTestDetailHeadView();
        initBuy();
        try {
            initHaveBuy();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void initBuy() {
        testBuyNow_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbIsLogin()==false){
                    Toast.makeText(TestDetailActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                }else {

                    initSubmitTestOrder();

                }
            }
        });
    }

    private void initView() {
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.load_bar_layout_evaluating);
        sPrice = getIntent().getStringExtra("sPrice");
        back_testDetail_iv= (ImageView) findViewById(R.id.back_testDetail_iv);
        testDetail_iv= (ImageView) findViewById(R.id.testLiberaryBg_iv);
        testDetailName_tv= (TextView) findViewById(R.id.testDetailName_tv);
        testDetailPrice_tv= (TextView) findViewById(R.id.price_tv);
        haveBuy_ly= (RelativeLayout) findViewById(R.id.haveBuy_layout);
        noBuy_ly= (RelativeLayout) findViewById(R.id.noBuy_layout);
        tiku_retroduce_tv= (TextView) findViewById(R.id.tiku_introduce_tv);
        testBuyNow_bt= (Button) findViewById(R.id.testBuyNow_bt);
        testLiberayTitle_tv= (TextView) findViewById(R.id.testLiberaryTitle_tv);
        taocan_chart_iv= (ImageView) findViewById(R.id.taoCan_chart_iv);

        quesLibId = getIntent().getIntExtra("quesLibId",0);
        quesLibName = getIntent().getStringExtra("quesLibName");
        Log.e("quesLibsId",String.valueOf(quesLibId));

        taoCanA_bt= (Button) findViewById(R.id.taocanA_bt);
        taoCanB_bt= (Button) findViewById(R.id.taocanB_bt);
        taoCanC_bt= (Button) findViewById(R.id.taocanC_bt);
        test_bt= (Button) findViewById(R.id.goTest_bt);

    }

    //判断是否购买该题库
    private void initHaveBuy() throws UnsupportedEncodingException {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        isMechanism = tokenDb.getInt("isMechanism", 0);
        token = tokenDb.getString("token","");
        String encodeToken = URLEncoder.encode(token, "utf-8");
        Log.e("tokenaaaaaaa",encodeToken);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestIsBuyData(quesLibId,token).enqueue(new Callback<TestIsBuyBean>() {
            @Override
            public void onResponse(Call<TestIsBuyBean> call, Response<TestIsBuyBean> response) {
                TestIsBuyBean body = response.body();
                    if (response != null & body != null) {
                        String code = body.getCode();
                                    if (code.equals("99999")){
                                        Toast.makeText(TestDetailActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                                    }else if (code.equals("00003")){
                                        Toast.makeText(TestDetailActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                                        SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                                        SharedPreferences.Editor edit = sp.edit();
                                        edit.putBoolean("isLogin",false);
                                        edit.commit();
                                        Log.e("isLogin","1111");
                                        testBuyNow_bt.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(TestDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }else if (dbIsLogin()==false){
                                        Toast.makeText(TestDetailActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                                        Log.e("isLogin","1111");
                                        testBuyNow_bt.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(TestDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }else if (code.equals("00000")){//当用户登陆时
                                        boolean isBuy = body.getData().isIsBuy();
                                        if (!TextUtils.isEmpty(sPrice)) {
                                            if (sPrice.equals("0.00")) {//当为免费测试时把考试和练习界面变为可见
                                                noBuy_ly.setVisibility(View.VISIBLE);
                                              //  haveBuy_ly.setVisibility(View.VISIBLE);
                                                Log.e("isBuy", "11111");
                                            }
                                        }
                                        else if (isBuy==true){//当购买后也把考试和练习界面变为可见
                                            noBuy_ly.setVisibility(View.VISIBLE);
                                           // haveBuy_ly.setVisibility(View.VISIBLE);
                                            Log.e("isBuy","2222");
                                        }else if (isBuy==false){
                                            noBuy_ly.setVisibility(View.VISIBLE);
                                        //    haveBuy_ly.setVisibility(View.GONE);
                                            Log.e("isBuy","2222");
                                        }
                                }
            }
            }
            @Override
            public void onFailure(Call<TestIsBuyBean> call, Throwable t) {

            }
        });

    }


    private void initTestDetailHeadView() {
        showLoadingBar(false);

        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        Call<TestDetailBean> testDetailData = aClass.getTestDetailData(quesLibId);
        testDetailData.enqueue(new Callback<TestDetailBean>() {

            @Override
            public void onResponse(Call<TestDetailBean> call, Response<TestDetailBean> response) {
                TestDetailBean body = response.body();
                if (response!=null&&body!=null){
                    String code = body.getCode();
                     if (code.equals("00000")) {
                         hideLoadingBar();
                         TestDetailBean.DataEntity data = body.getData();
                        quesLibName = data.getQuesLibName();
                        quesLibImageUrl = data.getQuesLibImageUrl();
                         packageImage = data.getPackageImage();

                         if (!TextUtils.isEmpty(packageImage)){
                             Picasso.with(TestDetailActivity.this).load(packageImage).placeholder(R.mipmap.ab_chart).into(taocan_chart_iv);
                         }
                         final List<TestDetailBean.DataEntity.PackagesEntity> packages = data.getPackages();
                         int size1 = packages.size();
                         List<Integer>a=new ArrayList<Integer>();
                         for (int i=0;i<size1;i++){
                             int packageId = packages.get(i).getPackageId();
                             //packageId没有等于3的致灰
                             a.add(packageId);
                         }
                         final boolean contains = a.contains(3);


                         if (size1>0){//显示套餐
                             int size = packages.size();
                             taoCanA_bt.setText(packages.get(0).getPackageName());
                             taoCanB_bt.setText("独家解析题库");
                             if (size==1){
                                 taoCanB_bt.setVisibility(View.INVISIBLE);
                                 taoCanC_bt.setVisibility(View.GONE);
                                 final String price = packages.get(0).getPrice();
                                 taoCanA_bt.setVisibility(View.VISIBLE);

                                 taoCanB_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                 taoCanC_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                 testDetailPrice_tv.setText(price);
                                 quesLibPackageId=packages.get(0).getPackageId();
                                 String packageName = packages.get(0).getPackageName();
                                 if (!TextUtils.isEmpty(packageName)) {
                                     taoCanA_bt.setText(packageName);
                                 }
                                 sPrice=price;

                                 taoCanB_bt.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         if (contains == false) {
                                             taoCanB_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                             taoCanA_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_taocan));
                                             taoCanC_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                             initTaoCanDialog();
                                         }
                                     }
                                 });

                             }if (size==3){
                                 taoCanA_bt.setVisibility(View.VISIBLE);
                                 taoCanB_bt.setVisibility(View.VISIBLE);
                                 taoCanC_bt.setVisibility(View.VISIBLE);
                                 String packageName = packages.get(0).getPackageName();
                                 if (!TextUtils.isEmpty(packageName)) {
                                     taoCanA_bt.setText(packageName);
                                 }
                                 String packageName1 = packages.get(1).getPackageName();
                                 if (!TextUtils.isEmpty(packageName1)) {
                                     taoCanB_bt.setText(packageName1);
                                 }
                                 String packageName2 = packages.get(2).getPackageName();
                                 if (!TextUtils.isEmpty(packageName2)) {
                                     taoCanC_bt.setText(packageName2);
                                 }
                                 final String price = packages.get(0).getPrice();
                                 final String price1 = packages.get(1).getPrice();
                                 final String price2 = packages.get(2).getPrice();
                                 testDetailPrice_tv.setText(price);
                                 quesLibPackageId=packages.get(0).getPackageId();
                                 taoCanA_bt.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         taoCanB_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                         taoCanA_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_taocan));
                                         taoCanC_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                         testDetailPrice_tv.setText(price);
                                         quesLibPackageId=packages.get(0).getPackageId();
                                         Log.e("packageId",quesLibPackageId+",AAa");
                                         sPrice=price;
                                     }
                                 });

                                 taoCanB_bt.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         taoCanB_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_taocan));
                                         taoCanA_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                         taoCanC_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                         testDetailPrice_tv.setText(price1);
                                         quesLibPackageId=packages.get(1).getPackageId();
                                         Log.e("packageId",quesLibPackageId+",BBa");

                                         sPrice=price1;
                                     }
                                 });
                                 taoCanC_bt.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         taoCanC_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_taocan));
                                         taoCanA_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                         taoCanB_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                         testDetailPrice_tv.setText(price2);
                                         quesLibPackageId=packages.get(2).getPackageId();
                                         sPrice=price2;

                                     }
                                 });

                             }
                            if (size==2){
                                taoCanA_bt.setVisibility(View.VISIBLE);
                                taoCanB_bt.setVisibility(View.VISIBLE);
                                taoCanB_bt.setText(packages.get(1).getPackageName());
                                taoCanC_bt.setVisibility(View.GONE);
                                String packageName = packages.get(0).getPackageName();
                                if (!TextUtils.isEmpty(packageName)) {
                                    taoCanA_bt.setText(packageName);
                                }
                                String packageName1 = packages.get(1).getPackageName();
                                if (!TextUtils.isEmpty(packageName1)) {
                                    taoCanB_bt.setText(packageName1);
                                }
                                final String price = packages.get(0).getPrice();
                                final String price1 = packages.get(1).getPrice();
                                quesLibPackageId=packages.get(0).getPackageId();
                                Log.e("testId",quesLibId+","+quesLibPackageId);
                                sPrice=price;
                                testDetailPrice_tv.setText(price);
                                taoCanA_bt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        taoCanB_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                        taoCanA_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_taocan));
                                        testDetailPrice_tv.setText(price);
                                        quesLibPackageId=packages.get(0).getPackageId();
                                        sPrice=price;
                                    }
                                });

                                taoCanB_bt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                            taoCanB_bt.setBackgroundDrawable(getResources().getDrawable(R.mipmap.bg_taocan));
                                            taoCanA_bt.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_line_gray_ranctangle));
                                            testDetailPrice_tv.setText(price1);
                                            sPrice = price1;
                                        quesLibPackageId=packages.get(1).getPackageId();
                                    }
                                });

                            }

                         }else {
                             //不显示套餐
                             taoCanA_bt.setVisibility(View.GONE);
                             taoCanB_bt.setVisibility(View.GONE);
                             taoCanC_bt.setVisibility(View.GONE);
                         }


                         String quesLibDesc = data.getQuesLibDesc();
                        if (sPrice != null
                              ) {

                            if (!sPrice.equals("0.00")){
                               if (size1>0){
                                   String price = packages.get(0).getPrice();
                                   testDetailPrice_tv.setText(price);
                                   testDetailPrice_tv.setTextColor(getResources().getColor(R.color.yellowbgLing));
                               }else {
                                   testDetailPrice_tv.setText("￥"+sPrice);
                                   testDetailPrice_tv.setTextColor(getResources().getColor(R.color.yellowbgLing));
                               }

                            }else {
                                testDetailPrice_tv.setText("免费");
                                testDetailPrice_tv.setTextColor(getResources().getColor(R.color.greenRightTv));
                                haveBuy_ly.setVisibility(View.VISIBLE);
                                noBuy_ly.setVisibility(View.INVISIBLE);
                           }
                        }
                         if (!TextUtils.isEmpty(quesLibName)) {
                             testDetailName_tv.setText(quesLibName);
                         }

                         Picasso.with(TestDetailActivity.this).load(quesLibImageUrl).placeholder(R.mipmap.loading_png).into(testDetail_iv);
                         String s = Html.fromHtml(quesLibDesc).toString();
                         if (!TextUtils.isEmpty(s)) {
                             tiku_retroduce_tv.setText(s);
                         }
                    }

                }
            }

            @Override
            public void onFailure(Call<TestDetailBean> call, Throwable t) {
                Log.e("testDetailfalse",t.getMessage());

            }
        });
    }

    private void initTaoCanDialog() {

        popView = LayoutInflater.from(TestDetailActivity.this).inflate(R.layout.pop_test_taocan_layout, null, false);
        TextView queDing_tv= (TextView) popView.findViewById(R.id.queDing_tv);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        popupWindow.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        popupWindow.setOutsideTouchable(true);
        // 配合 点击外部区域消失使用 否则 没有效果
        popupWindow.showAtLocation(popView, Gravity.CENTER, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

       queDing_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private boolean dbIsLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }
    }


    //点击进入练习模式
    public void practiceOnclick(View view) {
        Intent intent=new Intent(this,TestPracticeAcivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("quesLibId",quesLibId);
        Log.e("aaaaaaaaaquslibslid",quesLibId+"");
        intent.putExtras(bundle);
        startActivity(intent);

    }
//点击进入考试模式
    public void testOnclick(View view) {
        String startTime = DateUtil.getNow(DateUtil.DEFAULTPATTERN);
        Log.e("currentTime",startTime);
        Intent intent=new Intent(this,TestContentActivity1.class);
      //  Intent intent=new Intent(this,TestNewActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("beginTime",startTime);
        bundle.putString("quesLibName",quesLibName);
        bundle.putInt("quesLibId",quesLibId);
        if (sPrice.equals("0.00")){
            bundle.putBoolean("isFreeTest",true);
        }
        intent.putExtras(bundle);
        boolean b = dbIsLogin();
        if (b==false) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        }else {
            startActivity(intent);
        }
    }
    private void initFinish() {
        back_testDetail_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //点击购买
    public void testBuyOnClick(View view) {

    }
    private void initSubmitTestOrder() {

        testBuyNow_bt.setEnabled(false);
        boolean enabled = testBuyNow_bt.isEnabled();
        Log.e("testBuy",""+ enabled);
        TestGroupInterface aClass1 = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass1.getYouhuiJuanData(quesLibId,quesLibPackageId,token).enqueue(new Callback<YouHuiJuanListBean>() {
            @Override
            public void onResponse(Call<YouHuiJuanListBean> call, Response<YouHuiJuanListBean> response) {
                if (response!=null){
                    int code = response.code();
                    if (code==200){
                        YouHuiJuanListBean body = response.body();
                        if (body!=null){
                            String code1 = body.getCode();
                            if (code1.equals("00000")){
                                YouHuiJuanListBean.DataEntity data = body.getData();
                                List<YouHuiJuanListBean.DataEntity.CouponListEntity> couponList = data.getCouponList();
                                if (couponList!=null){//有优惠卷
                                 Intent intent=new Intent(TestDetailActivity.this,YouHuiJuanListActivity.class);
                                 Bundle bundle=new Bundle();
                                 bundle.putInt("quesLibId",quesLibId);
                                 bundle.putInt("quesLibPackageId",quesLibPackageId);
                                 intent.putExtras(bundle);
                                 startActivity(intent);
                                 testBuyNow_bt.setEnabled(true);
                                 finish();
                                  //  Log.e("packageId",quesLibPackageId+",gggga"+quesLibId);
                                }else {//无优惠卷
                                 initSubmitTestOrderNew();
                                }

                            }else {
                                String errMsg = body.getErrMsg();
                                if (!TextUtils.isEmpty(errMsg)){
                                    Toast.makeText(TestDetailActivity.this,errMsg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

            }
            @Override
            public void onFailure(Call<YouHuiJuanListBean> call, Throwable t) {

            }
        });



    }

    private void initSubmitTestOrderNew() {
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestSubmitOrderData(null,quesLibId,quesLibPackageId,token).enqueue(new Callback<TestSubmitOrderBean>() {
            private String orderNumber;
            @Override
            public void onResponse(Call<TestSubmitOrderBean> call, Response<TestSubmitOrderBean> response) {
                TestSubmitOrderBean body = response.body();
                Log.e("packageId",quesLibPackageId+",gggga"+quesLibId);
                if (response!=null&&body!=null) {
                    String code = body.getCode();
                 if (code.equals("00000")) {
                        TestSubmitOrderBean.DataBean data = body.getData();
                        orderNumber = data.getOrderNumber();
                        Intent intent = new Intent(TestDetailActivity.this, PayUiActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("testPrice", String.valueOf(sPrice));
                        bundle.putInt("quesLibId",quesLibId);
                        bundle.putString("orderNumber", orderNumber);
                        bundle.putInt("isMechanism",isMechanism);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        testBuyNow_bt.setEnabled(true);
                         boolean enabled = testBuyNow_bt.isEnabled();
                       Log.e("testBuy","111,"+ enabled);
                    }else {
                     Object errMsg = body.getErrMsg();
                     if (errMsg!=null){
                         Toast.makeText(TestDetailActivity.this, ""+errMsg, Toast.LENGTH_SHORT).show();
                     }
                 }
                }
            }

            @Override
            public void onFailure(Call<TestSubmitOrderBean> call, Throwable t) {
                Toast.makeText(TestDetailActivity.this,"提交订单失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
       testBuyNow_bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               initSubmitTestOrder();
           }
       });
    }

    @Override
    protected void onPause() {
        super.onPause();
        testBuyNow_bt.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow!=null){
            if (popupWindow.isShowing()){
                popupWindow.dismiss();
            }
        }
    }
}
