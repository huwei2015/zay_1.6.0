package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

public class PaySuccessActivity extends BaseActivity {
    TextView payMoney_tv;
    Button dingDanDetai_bt,home_bt;
    private String orderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        initView();
    }

    private void initView() {
        payMoney_tv= (TextView) findViewById(R.id.account_payMoney_tv);
        dingDanDetai_bt= (Button) findViewById(R.id.accountPay_bt);
        home_bt= (Button) findViewById(R.id.accont_home_bt);
        orderPrice = getIntent().getStringExtra("orderPrice");
        if (!TextUtils.isEmpty(orderPrice)){
            payMoney_tv.setText("支付金额"+orderPrice+"元");
        }
    }
    public void detailOnClick(View view) {
        Intent intent=new Intent(PaySuccessActivity.this,NewMyOrderActivity.class);
        startActivity(intent);
    }
    public void homeOnClick(View view) {
        Intent intent1=new Intent(PaySuccessActivity.this, MainActivity.class);
        startActivity(intent1);
    }
}
