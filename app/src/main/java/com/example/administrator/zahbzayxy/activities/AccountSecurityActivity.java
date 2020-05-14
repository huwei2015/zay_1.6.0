package com.example.administrator.zahbzayxy.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LogoutBean;
import com.example.administrator.zahbzayxy.myinterface.MyLessonInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-18.
 * Time 15:22.
 * 账户安全
 */
public class AccountSecurityActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back_editMessage;
    private RelativeLayout rl_phone,rl_pwd,rl_households;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        initView();
    }

    private void initView() {
        back_editMessage= findViewById(R.id.back_editMessage);
        back_editMessage.setOnClickListener(this);
        rl_phone= findViewById(R.id.rl_phone);
        rl_phone.setOnClickListener(this);
        rl_pwd= findViewById(R.id.rl_pwd);
        rl_pwd.setOnClickListener(this);
        rl_households=findViewById(R.id.rl_households);
        rl_households.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_editMessage:
                this.finish();
                break;
            case R.id.rl_phone:
                Intent intent = new Intent(AccountSecurityActivity.this,SetPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_pwd:
                startActivity(new Intent(AccountSecurityActivity.this,ChangePWActivity.class));
                break;
            case R.id.rl_households:
                AlertDialog.Builder dialog = new AlertDialog.Builder(AccountSecurityActivity.this);
                dialog.setTitle("确定注销账号吗?");
                dialog.setMessage("注销后您的学习记录、购买记录、个人信息将全部清空，是否继续注销账号?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getLogout();
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }
    private void getLogout(){
        SharedPreferences sharedPreferences =getSharedPreferences("tokenDb", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        MyLessonInterface aClass = RetrofitUtils.getInstance().createClass(MyLessonInterface.class);
        aClass.getLogout(token).enqueue(new Callback<LogoutBean>() {
            @Override
            public void onResponse(Call<LogoutBean> call, Response<LogoutBean> response) {
                if(response !=null && response.body()!=null){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        boolean data = response.body().isData();
                        if(data){
                            Toast.makeText(AccountSecurityActivity.this,"注销成功",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AccountSecurityActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LogoutBean> call, Throwable t) {
                Toast.makeText(AccountSecurityActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
