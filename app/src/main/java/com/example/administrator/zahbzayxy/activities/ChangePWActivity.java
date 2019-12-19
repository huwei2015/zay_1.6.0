package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.UserInfoResetBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.BinaryCastUtils;
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

public class ChangePWActivity extends BaseActivity implements View.OnClickListener{
    String token;
    String mPassWord;
   ImageView changePW_back_iv;
    private Button pWQD_bt;
    String onePW,twoPw;
    EditText oldPw_et,newPw_et,newAginPw_et;
    private byte[] mDigest;
    private byte[] digest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);
        initView();
        initToken();
    }

    private void initToken() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        mPassWord=tokenDb.getString("passWord","");

    }

    private void initView() {
        changePW_back_iv= (ImageView) findViewById(R.id.changePW_back_iv);
        changePW_back_iv.setOnClickListener(this);
        pWQD_bt= (Button) findViewById(R.id.pWQD_bt);
        pWQD_bt.setOnClickListener(this);
       oldPw_et= (EditText) findViewById(R.id.oldPW_et);
        newPw_et= (EditText) findViewById(R.id.newPW_et);
        newAginPw_et= (EditText) findViewById(R.id.aginNewPW_et);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changePW_back_iv:
                finish();
                break;
            case R.id.pWQD_bt:
                try {
                    String newAgainPw = newAginPw_et.getText().toString();
                    String newPw_tv = newPw_et.getText().toString();
                    String oldPW = oldPw_et.getText().toString();
                    if (newPw_tv.equals(newAgainPw)){
                        initNewPW(oldPW,newPw_tv);
                    }else {
                        Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void initNewPW(String oldPw,String newPw) throws JSONException {
       
      
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            mDigest = messageDigest.digest(oldPw.getBytes("utf-8"));
            digest = messageDigest.digest(newPw.getBytes("utf-8"));
            Log.e("bytepassword",mDigest.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String oldPWJson = BinaryCastUtils.parseByte2HexStr(mDigest);
        String newPWJson = BinaryCastUtils.parseByte2HexStr(digest);
        JSONObject obj=new JSONObject();
        obj.put("oldPwd",oldPWJson);
        obj.put("newPwd",newPWJson);
        String sJson = obj.toString();
        final Map<String, Object> editMessage=new HashMap<>();
        editMessage.put("token",token);
        editMessage.put("updateInfo",sJson);
        editMessage.put("updateType",7);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
            @Override
            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                if (response!=null){
                    UserInfoResetBean body = response.body();
                    if (body != null) {
                        Object errMsg = body.getErrMsg();
                        if (errMsg==null){
                            boolean data = body.isData();
                            if (data==true){
                                Toast.makeText(ChangePWActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }else {
                            Toast.makeText(ChangePWActivity.this, ""+errMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
                Toast.makeText(ChangePWActivity.this,"请求服务器失败"+t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}
