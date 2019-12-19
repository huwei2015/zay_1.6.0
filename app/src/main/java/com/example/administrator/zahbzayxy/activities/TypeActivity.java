package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
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
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-10-21.
 * Time 13:56.
 * 工种名称
 */
public class TypeActivity extends BaseActivity {
    private ImageView backName_iv;
    private EditText nameSet_et;
    private Button queDingName_bt;
    private String token;
    private String workTypeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_layout);
        initView();
        initToken();
        initOnClick();
    }
    private void initToken() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        Log.e("setUserNameToken", token);
    }
    private void initOnClick() {
        queDingName_bt.setOnClickListener(new View.OnClickListener() {
            //用户输入的姓名
            //  userName_tv =nameSet_et.getText().toString();

            @Override
            public void onClick(View v) {

                final JSONObject param = new JSONObject();
                try {
                    workTypeName = nameSet_et.getText().toString();
                    param.put("workTypeName", workTypeName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String userNameJson = param.toString();
                int num = 11;
                Map<String, Object> editMessage = new HashMap<>();
                editMessage.put("token", token);
                editMessage.put("updateInfo", userNameJson);
                editMessage.put("updateType", num);
                UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
                Call<UserInfoResetBean> updateUserNameData = userInfoInterface.getUpdateUserInfoData(editMessage);
                updateUserNameData.enqueue(new Callback<UserInfoResetBean>() {
                    @Override
                    public void onResponse(Call<UserInfoResetBean> call, Response<UserInfoResetBean> response) {
                        UserInfoResetBean body = response.body();
                        if (body != null) {
                            boolean data = body.isData();
                            Log.e("dataaaaaaaaaaaa", String.valueOf(data));
                            if (data == true) {
                                Toast.makeText(TypeActivity.this, "工种名称修改成功", Toast.LENGTH_SHORT).show();
                                //把姓名回传到用户中心
                                Intent intent = new Intent(TypeActivity.this, EditMessageActivity.class);
                                intent.putExtra("workTypeName", workTypeName);
                                setResult(RESULT_OK, intent);
                                TypeActivity.this.finish();
                                EventBus.getDefault().post(workTypeName);
                            } else {
                                Toast.makeText(TypeActivity.this, "修改失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(TypeActivity.this, "请求服务器失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfoResetBean> call, Throwable t) {
                        Toast.makeText(TypeActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void initView() {
        backName_iv = (ImageView) findViewById(R.id.backName_iv);
        nameSet_et = (EditText) findViewById(R.id.nameSet_et);
        queDingName_bt= (Button) findViewById(R.id.queDingName_bt);
        backName_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
