package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PayCardBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 学习卡
 */
public class UseStudyCardActivity extends BaseActivity {
    private ImageView back_iv;
    private Button queRenPay_bt;
    private EditText studyCardNum_et,studyCardPassword_et;
    private String studyCardNum;
    private int quesLibId;
    private String token;
    private String orderNumber;
    private String studyCardPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_study_card);
        initView();
        initPay();
    }

    private void initPay() {
        quesLibId = getIntent().getIntExtra("quesLibId", 0);
        orderNumber = getIntent().getStringExtra("orderNumber");
        Log.e("quesLibIdzzzz",quesLibId+","+"orderNum:"+orderNumber);
        queRenPay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studyCardNum = studyCardNum_et.getText().toString();
                studyCardPassWord = studyCardPassword_et.getText().toString();
                Map<String, String> cardNum = new HashMap<>();
                cardNum .put("cardCode",studyCardNum);
                cardNum .put("orderNumber",orderNumber);
                cardNum.put("cardPwd",studyCardPassWord);
                cardNum .put("token", token);
                if (!TextUtils.isEmpty(studyCardNum)){
                    UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
                    aClass.studyCard(cardNum).enqueue(new Callback<PayCardBean>() {
                        @Override
                        public void onResponse(Call<PayCardBean> call, Response<PayCardBean> response) {
                            PayCardBean body1 = response.body();
                            if (body1 != null) {
                                String s = new Gson().toJson(body1);
                                int code = response.code();
                                Log.e("codestudyCard", "code1" + code);
                                if (code == 200) {
                                    PayCardBean body = response.body();
                                    if (body != null) {
                                        String code1 = body.getCode();
                                        String s1 = body.toString();
                                        Log.e("codestudyCard", code1 + ",body:" + s1);
                                        if (code1.equals("00000")) {
                                            Toast.makeText(UseStudyCardActivity.this, "使用成功", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(UseStudyCardActivity.this,MyTiKuActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(UseStudyCardActivity.this, body.getErrMsg().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PayCardBean> call, Throwable t) {
                            Toast.makeText(UseStudyCardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


                }else {
                    Toast.makeText(UseStudyCardActivity.this, "请输入学习卡卡号", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initView() {
        back_iv= (ImageView) findViewById(R.id.back_studyCardPay_iv);
        queRenPay_bt= (Button) findViewById(R.id.studyCardPay_bt);
        studyCardNum_et= (EditText) findViewById(R.id.studyCardNum_et);
        studyCardPassword_et= (EditText) findViewById(R.id.studyCardPassword_et);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
