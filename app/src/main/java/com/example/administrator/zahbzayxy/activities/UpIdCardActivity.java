package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

/**
 * Created by huwei.
 * Data 2020-01-17.
 * Time 10:07.
 * 上传身份证正反面
 */
public class UpIdCardActivity extends BaseActivity {
    private Button btn_photo,btn_reverse;
    private ImageView img_photo,img_reverse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_idcard);
        initView();
    }

    private void initView() {
        btn_reverse= (Button) findViewById(R.id.btn_reverse);//反面
        btn_photo= (Button) findViewById(R.id.btn_photo);//正面
        img_photo= (ImageView) findViewById(R.id.img_photo);//正面图片
        img_reverse= (ImageView) findViewById(R.id.img_reverse);//反面图片
    }
}
