package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MySignAdapter;
import com.example.administrator.zahbzayxy.beans.SignBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 09:52.
 * 我的报名
 */
public class MySignUpActivity extends BaseActivity implements View.OnClickListener, PullToRefreshListener, MySignAdapter.onItemClickListener {
    private PullToRefreshRecyclerView recyclerView;
    private ImageView myChengJiBack_iv;
    private MySignAdapter mySignAdapter;
    private List<SignBean.SignListBean> signListBeanList = new ArrayList<>();
    private int currPage = 1;
    private int PageSize = 10;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_sign_up);
        initView();
        initData();
    }

    private void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getSignData(currPage, PageSize, token).enqueue(new Callback<SignBean>() {
            @Override
            public void onResponse(Call<SignBean> call, Response<SignBean> response) {
                    if(response !=null && response.body() !=null){
                        String code = response.body().getCode();
                        if(code.equals("00000")){
                            signListBeanList = response.body().getApplyList();
                            if (currPage == 1){
                                mySignAdapter.setList(signListBeanList);
                            }else{
                                mySignAdapter.addList(signListBeanList);
                            }
                        }
                    }
            }

            @Override
            public void onFailure(Call<SignBean> call, Throwable t) {
                String message = t.getMessage();
                ToastUtils.showInfo(message,5000);
            }
        });
    }

    private void initView() {
        recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recyclerview);
        myChengJiBack_iv = (ImageView) findViewById(R.id.myChengJiBack_iv);
        myChengJiBack_iv.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
        //主动触发下拉刷新操作
        recyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(this, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setEmptyView(emptyView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myChengJiBack_iv:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshComplete();
                currPage = 1;
                initData();
                recyclerView.setLoadingMoreEnabled(true);
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLoadMoreComplete();
                if (signListBeanList.size() < PageSize) {
                    Toast.makeText(MySignUpActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    recyclerView.setLoadingMoreEnabled(false);
                    return;
                }
                currPage++;
                initData();
            }
        }, 2000);
    }

    //item点击事件
    @Override
    public void onClick(View view, int position) {

    }
}
