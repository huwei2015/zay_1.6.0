package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

/**
 * Created by Administrator
 * on 2019/7/2
 * description:联系我们
 */
public class ContacActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contac);
        initView();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.exam_archives_back);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exam_archives_back:
                this.finish();
                break;
        }
    }
}
