package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ShowImgActivity extends BaseActivity {

    public static final String SHOW_IMG_FILE_NAME_KEY = "showImgFileNameKey";
    public static final String SHOW_IMG_FILE_URL_KEY = "showImgFileUrlKey";

    private ImageView mImg, mBackImg;
    private TextView mTitleTv;
    private String mFileName, mFileUrl;
    private ProgressBarLayout mLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img);
        initView();
        initData();
        initEvent();
    }

    private void initView(){
        mBackImg = (ImageView) findViewById(R.id.show_img_back_img);
        mImg = (ImageView) findViewById(R.id.show_img_view);
        mTitleTv = (TextView) findViewById(R.id.show_img_title_tv);
        mLoading = (ProgressBarLayout) findViewById(R.id.show_img_loading_layout);
    }

    private void initData(){
        Intent intent = getIntent();
        if (intent == null) {
            ToastUtils.showShortInfo("文件加载失败");
            finish();
            return;
        }
        mFileName = intent.getStringExtra(SHOW_IMG_FILE_NAME_KEY);
        mFileUrl = intent.getStringExtra(SHOW_IMG_FILE_URL_KEY);
        if (TextUtils.isEmpty(mFileName) || TextUtils.isEmpty(mFileUrl)) {
            ToastUtils.showLongInfo("文件加载失败，请检查");
            finish();
            return;
        }
        mTitleTv.setText(mFileName);
        mLoading.setVisibility(View.VISIBLE);
        Picasso.with(ShowImgActivity.this).load(mFileUrl).placeholder(R.mipmap.icon_big).into(mImg, new Callback() {
            @Override
            public void onSuccess() {
                mLoading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                mLoading.setVisibility(View.GONE);
            }
        });
    }

    private void initEvent() {
        mBackImg.setOnClickListener(v -> finish());
    }
}
