package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.BinaryCastUtils;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountPayActivity extends BaseActivity {
    private String token;
    private String orderNum;
    private EditText payPw_et;
    private String payPw_tv;
    private byte[] mDigest;
    Button queRenPay_bt;
    private String orderPrice;
    private ImageView back_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account_pay);
        initView();
        initSP();
    }
//点击确认支付时
    public void queRenPayOnclick(View view) {
        initAccountPay();
    }

    private void initAccountPay() {
        payPw_tv = payPw_et.getText().toString();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            mDigest = messageDigest.digest(payPw_tv.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String payPW= BinaryCastUtils.parseByte2HexStr(mDigest);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Map<String,String>payMap=new HashMap<>();
        payMap.put("orderNumber",orderNum);
        payMap.put("payPassword",payPW);
        payMap.put("token",token);

        aClass.getAccoutPayData(payMap).enqueue(new Callback<SuccessBean>() {
            @Override
            public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                SuccessBean body = response.body();
                if (body!=null){
                    String code = body.getCode();
                    if (code.equals("00031")){
                        Toast.makeText(MyAccountPayActivity.this, "余额不足", Toast.LENGTH_SHORT).show();

                    }else if (code.equals("00032")){
                        Toast.makeText(MyAccountPayActivity.this, "密码错误", Toast.LENGTH_SHORT).show();

                    }else if (code.equals("00000")){
                        boolean data = body.getData();
                        if (data==true){
                            Toast.makeText(MyAccountPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MyAccountPayActivity.this,PaySuccessActivity.class);
                            if (!TextUtils.isEmpty(orderPrice)){
                                intent.putExtra("orderPrice",orderPrice);
                            }
                            startActivity(intent);
                        }else {
                            Toast.makeText(MyAccountPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Object errMsg = body.getErrMsg();
                        Toast.makeText(MyAccountPayActivity.this, errMsg.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessBean> call, Throwable t) {

            }
        });
    }


    private void initView() {
        orderNum = getIntent().getStringExtra("orderNum");
        orderPrice = getIntent().getStringExtra("orderPrice");
        payPw_et= (EditText) findViewById(R.id.payPW_et);
        queRenPay_bt= (Button) findViewById(R.id.accountPay_bt);
    }

    private void initSP() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        back_iv= (ImageView) findViewById(R.id.back_accountPay_iv);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
