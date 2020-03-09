package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.administrator.zahbzayxy.utils.MatchUtil;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

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

public class SetUserIdActivity extends BaseActivity implements View.OnClickListener {
    Button queDing_ID_bt;
    ImageView id_back_iv;
    String token,mPassWord;
    EditText personId_et,password_et;
    private String personId_tv;
    private String passWord_tv;
    private byte[] mDigest;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_id);
        initView();
        initToken();
    }

    private void initToken() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        mPassWord=tokenDb.getString("passWord","");
        Log.e("getpaaasssWw",mPassWord+"token"+token);
    }

    private void initView() {
        personId_et= (EditText) findViewById(R.id.idSet_et);
        password_et= (EditText) findViewById(R.id.pWSet_et);
        id_back_iv= (ImageView) findViewById(R.id.Id_back_iv);
        queDing_ID_bt = (Button) findViewById(R.id.queDingId_bt);
        id_back_iv.setOnClickListener(this);
        queDing_ID_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Id_back_iv:
                finish();
                break;
            case R.id.queDingId_bt://确认
                initCommitId();

            break;
        }
    }
    private void initCommitId() {
        personId_tv = personId_et.getText().toString().trim();
        if(!MatchUtil.isIdcradentity(personId_tv) && !MatchUtil.isMacaupass(personId_tv) &&
                !MatchUtil.isTanwan(personId_tv) && !MatchUtil.isReturnCard(personId_tv) &&
                !MatchUtil.isPassPoRt(personId_tv)){
            ToastUtils.showLongInfo("证件不正确，请重新输入");
            return;
        }
        passWord_tv = password_et.getText().toString().trim();
        if(TextUtils.isEmpty(passWord_tv)){
            ToastUtils.showLongInfo("密码不能为空");
            return;
        }
        Log.e("idpasswordget",passWord_tv);
        //要进行密码加密
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            mDigest = messageDigest.digest(passWord_tv.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sJM = BinaryCastUtils.parseByte2HexStr(mDigest);
        Log.e("IdMd5idPw",sJM);
        //Md5idCode: 96E79218965EB72C92A549DD5A330112
        JSONObject object=new JSONObject();
        try {
           object.put("idCard", this.personId_tv);
           object.put("password",sJM);

        } catch (JSONException e) {
            e.printStackTrace();
        }
       final String sJson = object.toString();
        Map<String, Object> editMessage=new HashMap<>();
        editMessage.put("token",token);
        editMessage.put("updateInfo",sJson);
        editMessage.put("updateType",3);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
        updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
            @Override
            public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                int code = response.code();
                Log.e("idCode",String.valueOf(code));
                UserInfoResetBean body = response.body();
                if (body!=null){

                    Object errMsg = body.getErrMsg();
                    if (errMsg==null) {
                        boolean data = body.isData();
                        if (data == true) {
                            Toast.makeText(SetUserIdActivity.this, "修改身份证号成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("personId", personId_tv);
                            setResult(RESULT_OK, intent);
                            SetUserIdActivity.this.finish();
                        }
                    }else {
                        Toast.makeText(SetUserIdActivity.this, ""+errMsg, Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
                Toast.makeText(SetUserIdActivity.this,"网路请求失败",Toast.LENGTH_SHORT).show();
                Log.e("requstIdididi",t.getMessage());
            }
        });

    }
}
