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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PhoneCodeBean;
import com.example.administrator.zahbzayxy.beans.UserInfoResetBean;
import com.example.administrator.zahbzayxy.interfacecommit.RegisterInterface;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.BinaryCastUtils;
import com.example.administrator.zahbzayxy.utils.RegisterUtil;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPhoneActivity extends BaseActivity implements View.OnClickListener{

   private ImageView phoneBack_iv;
    //输入的新手机号
    private String newPhone_tv;
    private EditText newPhone_et,newPoneGetCode_et,cPPW_et;
    private Button newPhoneGetCode_bt,newPhoneCommit_bt;
    private static final int CODE_MESSAGE_WHAT = 1;
    private static final int TIME_CODE = 60; // 每隔60s可重新获取验证码
    private int mCode_time = TIME_CODE; // 当前剩余秒数
    private String token;
    private String mpassWord;
    private String dataCode;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==CODE_MESSAGE_WHAT){
                if (mCode_time==0){
                    mCode_time=TIME_CODE;
                    newPhoneGetCode_bt.setEnabled(true);
                    newPhoneGetCode_bt.setText("获取验证码");

                }
                else {
                    newPhoneGetCode_bt.setText("还剩"+ mCode_time + "秒");
                    mCode_time--;
                    mHandler.sendEmptyMessageDelayed(CODE_MESSAGE_WHAT,1000);
                    return true;
                }

            }
            return false;
        }
    }){

    };
    private byte[] mDigest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone);
        initView();
        initToken();
    }

    private void initToken() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        mpassWord=tokenDb.getString("passWord","");
    }

    private void initView() {

        phoneBack_iv= (ImageView) findViewById(R.id.pC_back_iv);
        newPhone_et= (EditText) findViewById(R.id.newPhone_et);
        newPhoneGetCode_bt= (Button) findViewById(R.id.newPhoneGetCode_bt);
        newPoneGetCode_et= (EditText) findViewById(R.id.cPPW_et);
        cPPW_et= (EditText) findViewById(R.id.cPPW_et);
        newPhoneCommit_bt= (Button) findViewById(R.id.phoneChangCommit_bt);
        phoneBack_iv.setOnClickListener(this);
        newPhoneGetCode_bt
                .setOnClickListener(this);
        newPhoneCommit_bt.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pC_back_iv:
                SetPhoneActivity.this.finish();
                break;
            //修改手机获取验证码
            case R.id.newPhoneGetCode_bt:
               getCode();

                break;
            //修改手机号的提交按钮
            case R.id.phoneChangCommit_bt:
                try {
                    newPhoneCommit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void newPhoneCommit() throws JSONException {
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        final String cPPw_tv = cPPW_et.getText().toString();

        //要进行密码加密
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            try {
                mDigest = messageDigest.digest(cPPw_tv.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String sPW = BinaryCastUtils.parseByte2HexStr(mDigest);
        Log.e("LoginMd5Password",sPW);
       //final String sPW = mDigest.toString();
        Log.e("sPw",mDigest+",,,,"+cPPw_tv);
        JSONObject mObject=new JSONObject();
        mObject.put("phone",newPhone_tv);
        mObject.put("verCode",dataCode);
        mObject.put("password",sPW);
        String sJson = mObject.toString();
        Map<String, Object> editMessage=new HashMap<>();
        editMessage.put("token",token);
        editMessage.put("updateInfo",sJson);
        editMessage.put("updateType",4);
        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
            @Override
            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                UserInfoResetBean body = response.body();
                if (body!=null){
                    Object errMsg = body.getErrMsg();
                    if (errMsg==null){
                        boolean data = body.isData();
                        if (data==true){

                            Toast.makeText(SetPhoneActivity.this,"修改新手机号成功",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent();
                            intent.putExtra("newPhone",newPhone_tv);
                            setResult(RESULT_OK,intent);
                            finish();
                    }

                    }else {
                        Toast.makeText(SetPhoneActivity.this, ""+errMsg, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {

                    Toast.makeText(SetPhoneActivity.this,"网络问题，请检查网络",Toast.LENGTH_LONG).show();

            }
        });

    }

    //获取验证码
    private void getCode() {
       newPhone_tv= newPhone_et.getText().toString();
        if (!TextUtils.isEmpty(newPhone_tv)){
            if (RegisterUtil.isPhone(newPhone_tv)) {
               newPhoneGetCode_bt.setEnabled(false);
              //  mDelayHandler.sendEmptyMessage(CODE_MESSAGE_WHAT);
                mHandler.sendEmptyMessage(CODE_MESSAGE_WHAT);
                //请求验证码
                requestCode();
            } else {
                Toast.makeText(SetPhoneActivity.this, "请检查手机号是否正确！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SetPhoneActivity.this, "请输入手机号！", Toast.LENGTH_SHORT).show();
        }
        }

    private void requestCode() {
        RegisterInterface aClass = RetrofitUtils.getInstance().createClass(RegisterInterface.class);
        Call<PhoneCodeBean> registerVerCode = aClass.getChangePhoneCode(newPhone_tv,token);
        registerVerCode.enqueue(new Callback<PhoneCodeBean>() {
            @Override
            public void onResponse(Call<PhoneCodeBean> call, Response<PhoneCodeBean> response) {
                if (response!=null) {
                    PhoneCodeBean body = response.body();
                    if (body != null) {
                        Object errMsg = body.getErrMsg();
                        if (errMsg == null) {
                            dataCode = body.getData();
                        } else {
                            Toast.makeText(SetPhoneActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<PhoneCodeBean> call, Throwable t) {
                Toast.makeText(SetPhoneActivity.this, "获取验证码失败，请检查网络！", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

