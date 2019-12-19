package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

/**
 * Created by huwei.
 * Data 2019-12-18.
 * Time 10:12.
 * 服务协议
 */
public class ServiceActivity extends BaseActivity implements View.OnClickListener{
    private ImageView exam_archives_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        exam_archives_back= (ImageView) findViewById(R.id.exam_archives_back);
        exam_archives_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exam_archives_back:
                finish();
                break;
        }
    }
}
