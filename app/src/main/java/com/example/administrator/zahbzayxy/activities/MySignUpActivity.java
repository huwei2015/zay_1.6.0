package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MySignAdapter;
import com.example.administrator.zahbzayxy.beans.SignBean;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 09:52.
 * 我的报名
 */
public class MySignUpActivity extends BaseActivity implements View.OnClickListener, PullToRefreshListener{
    private PullToRefreshRecyclerView recyclerView;
    private ImageView myChengJiBack_iv;
    private MySignAdapter mySignAdapter;
    private List<SignBean.SignListBean> signListBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_sign_up);
        initView();
    }

    private void initView() {
        recyclerView= (PullToRefreshRecyclerView) findViewById(R.id.recyclerview);
        myChengJiBack_iv = (ImageView) findViewById(R.id.myChengJiBack_iv);
        myChengJiBack_iv.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        for(int i =0; i < 10; i++){
            SignBean.SignListBean signBean = new SignBean.SignListBean();
            signBean.setTitle("2019年注册消防工程师培训一班报名");
            signBean.setTime("2019.09.12");
            signListBeanList.add(signBean);
        }
        //初始化adapter
        mySignAdapter = new MySignAdapter(MySignUpActivity.this, signListBeanList);
       //添加数据源
        recyclerView.setAdapter(mySignAdapter);
        recyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        recyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        recyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(MySignUpActivity.this);
        recyclerView.setLoadMoreResource(R.drawable.account);
        //主动触发下拉刷新操作
//        recyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(this, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setEmptyView(emptyView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myChengJiBack_iv:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
