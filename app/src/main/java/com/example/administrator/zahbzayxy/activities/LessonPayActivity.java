package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

public class LessonPayActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener{

    private TextView testPrice_tv;
    private CheckBox zfb_cb;
    private CheckBox weChat_cb;
    private double testPrice;
    private String orderNumber;
    private String token;
    int postion=0;
    private String data;
    private Button queRenPay_bt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_pay);
        initView();
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            //支付宝支付
            case R.id.zfb_cb:
                weChat_cb.setChecked(false);
                break;

            //微信支付
            case R.id.weChat_cb:
                zfb_cb.setChecked(false);
                break;
            default:
                break;

        }

    }
    private void initView() {
        queRenPay_bt= (Button) findViewById(R.id.quRenPay_bt);
        zfb_cb= (CheckBox) findViewById(R.id.zfb_cb);
        weChat_cb= (CheckBox) findViewById(R.id.weChat_cb);
        testPrice = getIntent().getDoubleExtra("testPrice",0.0);
        testPrice_tv= (TextView) findViewById(R.id.testPrice_tv);
        if (!TextUtils.isEmpty(String.valueOf(testPrice))) {
            testPrice_tv.setText(testPrice + "元");
        }
        orderNumber = getIntent().getStringExtra("orderNumber");
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        Log.e("orderNumber,token",orderNumber+","+token);
        zfb_cb.setOnCheckedChangeListener(this);
        weChat_cb.setOnCheckedChangeListener(this);
    }
}
