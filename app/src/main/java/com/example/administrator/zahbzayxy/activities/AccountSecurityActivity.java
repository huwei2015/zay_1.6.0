package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

/**
 * Created by huwei.
 * Data 2019-12-18.
 * Time 15:22.
 * 账户安全
 */
public class AccountSecurityActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back_editMessage;
    private RelativeLayout rl_phone,rl_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        initView();
    }

    private void initView() {
        back_editMessage= (ImageView) findViewById(R.id.back_editMessage);
        back_editMessage.setOnClickListener(this);
        rl_phone= (RelativeLayout) findViewById(R.id.rl_phone);
        rl_phone.setOnClickListener(this);
        rl_pwd= (RelativeLayout) findViewById(R.id.rl_pwd);
        rl_pwd.setOnClickListener(this);
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
        }
    }
}
