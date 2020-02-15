package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TestWaitPayDataBean;
import com.example.administrator.zahbzayxy.beans.WeChatPayBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.Constants;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.zfbutils.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayUiActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView testPrice_tv;
    private CheckBox zfb_cb,weChat_cb,myMoney_cb,studyCard_cb;
    private String testPrice;
    private String orderNumber;
    private String token;
    int postion=0;
    private String data;
    private Button queRenPay_bt;
    private  WeChatPayBean.DataBean dataWeChat;
    private ImageView back_iv;
    private TextView studyCard_tv;
    private RelativeLayout studyCard_rl,zfb_rl,wechat_rl,haveMoney_rl;
    private TextView suggetBuy_tv;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String code = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if ("9000".equals(code)) {
                    Toast.makeText(PayUiActivity.this, "支付成功啦", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(PayUiActivity.this,PaySuccessActivity.class);
                    intent.putExtra("orderPrice",testPrice);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(PayUiActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    private int quesLibId;
    private boolean isLessonOrder;
    private int isMechanism;
    private int isApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*msgApi = WXAPIFactory.createWXAPI(PayUiActivity.this, null);
        String appid = "wx0dff7a26ddc49530";
        // 将该app注册到微信
        msgApi.registerApp(appid);*/
        setContentView(R.layout.activity_pay_ui);
        inntView();
        initOnPayListenner();
        initStudyCard();
        //getPayData();
    }
    private void initStudyCard() {
        studyCard_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = Integer.valueOf(quesLibId);
                if (integer!=null) {
                        Intent intent = new Intent(PayUiActivity.this, UseStudyCardActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("quesLibId",quesLibId);
                        bundle.putString("orderNumber",orderNumber);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

            }
        });
    }

    private void getPayData() {
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestWaitPayData(orderNumber,token).enqueue(new Callback<TestWaitPayDataBean>() {
            @Override
            public void onResponse(Call<TestWaitPayDataBean> call, Response<TestWaitPayDataBean> response) {
                Log.e("payOrderNumaaa", orderNumber);
                TestWaitPayDataBean body = response.body();
                if (body != null) {
                    Object errMsg1 = body.getErrMsg();
                    if (errMsg1==null) {
                        data = body.getData();
                        initZfbPay();
                    } else {
                        Toast.makeText(PayUiActivity.this, ""+errMsg1, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<TestWaitPayDataBean> call, Throwable t) {
               // Log.e("payWaitfailue", t.getMessage());
            }
        });
    }

    private void initOnPayListenner() {
        queRenPay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zfb_cb.isChecked()){
                    //支付宝支付
                    getPayData();
                }else if (weChat_cb.isChecked()){//微信支付
                    initWeChatPay();
                }else if (myMoney_cb.isChecked()){
                    //余额支付
                 //   initAccountPay();
                    Intent intent=new Intent(PayUiActivity.this,MyAccountPayActivity.class);
                    intent.putExtra("orderNum",orderNumber);
                    intent.putExtra("orderPrice",testPrice);
                    startActivity(intent);

                }else if (studyCard_cb.isChecked()){
                    Intent intent = new Intent(PayUiActivity.this, UseStudyCardActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("quesLibId",quesLibId);
                    bundle.putString("orderNumber",orderNumber);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }


    private void initWeChatPay() {
        Map<String,String>weChatPayMap=new HashMap<>();
        weChatPayMap.put("ordernum",orderNumber);
        weChatPayMap.put("token",token);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestWeChatPayData(weChatPayMap).enqueue(new Callback<WeChatPayBean>() {
            @Override
            public void onResponse(Call<WeChatPayBean> call, Response<WeChatPayBean> response) {
                WeChatPayBean body = response.body();
                if (body != null) {
                    Object errMsg1 = body.getErrMsg();
                   if (errMsg1 == null) {
                        dataWeChat = body.getData();
                        weChatPay();
                    } else {

                        Toast.makeText(PayUiActivity.this, ""+errMsg1, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<WeChatPayBean> call, Throwable t) {
               // Log.e("onfailureweChatPay",t.getMessage());

            }
        });

    }
    private void weChatPay() {
        if (isWXAppInstalledAndSupported()==false){
            Toast.makeText(PayUiActivity.this, "请安装微信客户端", Toast.LENGTH_SHORT).show();
        }else {
            final IWXAPI msgApi;
            String appid = dataWeChat.getAppid();
            msgApi = WXAPIFactory.createWXAPI(PayUiActivity.this, appid);
            // 将该app注册到微信
            msgApi.registerApp(appid);
            PayReq request = new PayReq();
            request.appId = dataWeChat.getAppid();
            request.partnerId = dataWeChat.getPartnerid();
            request.prepayId = dataWeChat.getPrepayid();
            request.packageValue = dataWeChat.getPackageX();
            request.nonceStr = dataWeChat.getNoncestr();
            request.timeStamp = dataWeChat.getTimestamp();
            request.sign = dataWeChat.getSign();
            boolean issuccess = msgApi.sendReq(request);
          //  Toast.makeText(PayUiActivity.this, issuccess + "", Toast.LENGTH_SHORT).show();

        }
}

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.zfb_cb:
                if (isChecked) {
                    weChat_cb.setChecked(false);
                    myMoney_cb.setChecked(false);
                    studyCard_cb.setChecked(false);
                }
                break;

            case R.id.weChat_cb:
                if (isChecked) {
                    zfb_cb.setChecked(false);
                    myMoney_cb.setChecked(false);
                    studyCard_cb.setChecked(false);
                }

                break;
            case R.id.myMoney_cb:
                if (isChecked){
                    zfb_cb.setChecked(false);
                    weChat_cb.setChecked(false);
                    studyCard_cb.setChecked(false);
                }
                break;
            case R.id.studyCard_cb:
                if (isChecked){
                    zfb_cb.setChecked(false);
                    weChat_cb.setChecked(false);
                    myMoney_cb.setChecked(false);
                }
                break;
            default:
                break;

        }

    }

    private void initZfbPay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    final String orderInfo = data;
                    //构造支付类的对象
                    PayTask task = new PayTask(PayUiActivity.this);
                    //参数1:订单信息 参数2:
                    Map<String, String> result = task.payV2(orderInfo, true);
                    //发送结果
                    Message message = handler.obtainMessage();
                    message.obj = result;
                    message.what = 0;
                    handler.sendMessageAtTime(message, 0);
                }
        }).start();
    }

    private void inntView() {
        quesLibId = getIntent().getIntExtra("quesLibId",0);
        isApply = getIntent().getIntExtra("isApply",0);
        back_iv= (ImageView) findViewById(R.id.payui_back);
        queRenPay_bt= (Button) findViewById(R.id.quRenPay_bt);
        zfb_cb= (CheckBox) findViewById(R.id.zfb_cb);
        weChat_cb= (CheckBox) findViewById(R.id.weChat_cb);
        myMoney_cb= (CheckBox) findViewById(R.id.myMoney_cb);
        studyCard_cb= (CheckBox) findViewById(R.id.studyCard_cb);
        testPrice = getIntent().getStringExtra("testPrice");
        testPrice_tv= (TextView) findViewById(R.id.testPrice_tv);
        studyCard_tv= (TextView) findViewById(R.id.studyCard_tv);
        isLessonOrder = getIntent().getBooleanExtra("isLessonOrder",false);
        studyCard_rl= (RelativeLayout) findViewById(R.id.studyCard_rl);
        zfb_rl= (RelativeLayout) findViewById(R.id.zfb_rl);
        wechat_rl= (RelativeLayout) findViewById(R.id.weChat_rl);
        haveMoney_rl= (RelativeLayout) findViewById(R.id.haveMoney_rl);
        suggetBuy_tv= (TextView) findViewById(R.id.suggestBuyTest_tv);

        if (isLessonOrder==true){
            studyCard_rl.setVisibility(View.INVISIBLE);
        }else {
            studyCard_rl.setVisibility(View.VISIBLE);
        }
Log.e("orderPrice",testPrice+"");

        if (!TextUtils.isEmpty(testPrice)) {
            testPrice_tv.setText(testPrice + "元");
        }

        orderNumber = getIntent().getStringExtra("orderNumber");
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        if(isApply==1){//报名支付
            isMechanism = 0;
        }else {
            isMechanism = tokenDb.getInt("isMechanism", 0);
        }
        Log.e("orderNumber,token",orderNumber+","+token+","+isMechanism);

        if (isMechanism!=0){
            wechat_rl.setVisibility(View.GONE);
            zfb_rl.setVisibility(View.GONE);
            haveMoney_rl.setVisibility(View.GONE);
            suggetBuy_tv.setVisibility(View.VISIBLE);
        }else {
            wechat_rl.setVisibility(View.VISIBLE);
            zfb_rl.setVisibility(View.VISIBLE);
            haveMoney_rl.setVisibility(View.VISIBLE);
            suggetBuy_tv.setVisibility(View.GONE);
        }

        Double aDouble = Double.valueOf(testPrice);

        if (aDouble<=0.00){
            zfb_rl.setVisibility(View.GONE);
            wechat_rl.setVisibility(View.GONE);
        }


        zfb_cb.setOnCheckedChangeListener(this);
        weChat_cb.setOnCheckedChangeListener(this);
        myMoney_cb.setOnCheckedChangeListener(this);
        studyCard_cb.setOnCheckedChangeListener(this);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private boolean isWXAppInstalledAndSupported() {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(Constants.APP_ID);

        boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled();
        return sIsWXAppInstalledAndSupported;
    }

}



