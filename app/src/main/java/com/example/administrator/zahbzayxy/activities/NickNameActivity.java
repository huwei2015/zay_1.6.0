package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.UserInfoResetBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NickNameActivity extends BaseActivity implements View.OnClickListener {
    ImageView nickName_back_iv;
    Button nickName_qD_bt;
    EditText nickName_et;
    String token="";
    private SharedPreferences tokenDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        initView();
        initToken();

    }

    private void initToken() {
        tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");

    }

    private void initView() {
        nickName_back_iv= (ImageView) findViewById(R.id.nickName_back_iv);
        nickName_qD_bt= (Button) findViewById(R.id.qDNickName_bt);
        nickName_et= (EditText) findViewById(R.id.nickName_et);
        nickName_back_iv.setOnClickListener(this);
        nickName_qD_bt.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nickName_back_iv:
                finish();
                break;
            case R.id.qDNickName_bt:
                final String nickName = nickName_et.getText().toString();
                final JSONObject param = new JSONObject();
                try {
                    param.put("nickName",nickName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String nickNameJson=param.toString();

                Map<String, Object> editMessage=new HashMap<>();
                editMessage.put("token",token);
                editMessage.put("updateInfo",nickNameJson);
                editMessage.put("updateType",0);
                UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
                Call<UserInfoResetBean> updateUserInfoData = aClass.getUpdateUserInfoData(editMessage);
                updateUserInfoData.enqueue(new Callback<UserInfoResetBean>() {
                    @Override
                    public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                        if (response != null) {
                            int code = response.code();
                            UserInfoResetBean body = response.body();
                            if (body != null) {
                                boolean data = body.isData();
                                if (data == true) {
                                    Toast.makeText(NickNameActivity.this, "昵称修改成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("nickName", nickName);
                                    SharedPreferences userInfo = getSharedPreferences("userInfoDb", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = userInfo.edit();
                                    edit.putString("nickName", nickName);
                                    edit.commit();
                                    NickNameActivity.this.setResult(RESULT_OK, intent);
                                    NickNameActivity.this.finish();




                                } else {
                                    Toast.makeText(NickNameActivity.this, "请稍后重新修改", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
                        Toast.makeText(NickNameActivity.this,"请求服务器失败",Toast.LENGTH_SHORT).show();

                    }
                });

                break;
        }

    }
}
