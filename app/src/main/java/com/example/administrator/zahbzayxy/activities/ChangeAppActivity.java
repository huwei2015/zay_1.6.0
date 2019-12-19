package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

/**
 * Created by ${ZWJ} on 2017/7/7 0007.
 */
public class ChangeAppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_app);
    }
  //点击进入中安于教育app
    public void zAYOnclick(View view) {
        RetrofitUtils.setBaseUrl("http://192.168.2.233");
        Intent intent=new Intent(ChangeAppActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
  //点击进入宁波app
    public void nBOnClick(View view) {
        RetrofitUtils.setBaseUrl("http://app.zayxy.com");
        Intent intent=new Intent(ChangeAppActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
