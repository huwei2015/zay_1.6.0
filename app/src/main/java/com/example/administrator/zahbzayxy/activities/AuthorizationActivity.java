package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.fragments.HasAuthorizationFragment;
import com.example.administrator.zahbzayxy.fragments.StayAuthorizedFragment;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

/**
 * Created by huwei.
 * Data 2019-10-24.
 * Time 17:29.
 * 授权管理
 */
public class AuthorizationActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_authorization, tv_un_authorization;
    private ImageView exam_archives_back;
    private Fragment HasAuthorizationFragment;
    private Fragment StayAuthorizedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_manager);
        initView();
    }

    private void initView() {
        tv_authorization = (TextView) findViewById(R.id.tv_authorization);
        tv_un_authorization = (TextView) findViewById(R.id.tv_un_authorization);
        exam_archives_back = (ImageView) findViewById(R.id.exam_archives_back);
        exam_archives_back.setOnClickListener(this);
        tv_authorization.setOnClickListener(this);
        tv_un_authorization.setOnClickListener(this);
        //默认选中颜色
        tv_authorization.setSelected(true);
        HasAuthorizationFragment = new HasAuthorizationFragment();
        StayAuthorizedFragment = new StayAuthorizedFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, HasAuthorizationFragment).add(R.id.fragment_content, StayAuthorizedFragment).show(HasAuthorizationFragment).hide(StayAuthorizedFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exam_archives_back:
                finish();
                break;
            case R.id.tv_authorization:
                tv_authorization.setSelected(true);
                tv_authorization.setTextColor(getResources().getColor(R.color.white));
                tv_un_authorization.setTextColor(getResources().getColor(R.color.text_bg));
                tv_un_authorization.setSelected(false);
                getSupportFragmentManager().beginTransaction().show(HasAuthorizationFragment).hide(StayAuthorizedFragment).commit();
                break;
            case R.id.tv_un_authorization:
                tv_un_authorization.setSelected(true);
                tv_authorization.setSelected(false);
                tv_un_authorization.setTextColor(getResources().getColor(R.color.white));
                tv_authorization.setTextColor(getResources().getColor(R.color.text_bg));
                getSupportFragmentManager().beginTransaction().show(StayAuthorizedFragment).hide(HasAuthorizationFragment).commit();
                break;
        }
    }
}
