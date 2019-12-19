package com.example.administrator.zahbzayxy.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.zahbzayxy.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        StatusBarCompat.compat(BaseActivity.this, getResources().getColor(R.color.bg));

       // RefWatcher refWatcher = DemoApplication.getRefWatcher(BaseActivity.this);
      //  refWatcher.watch(this);
    }
}
