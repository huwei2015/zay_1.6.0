package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MySignAdapter;
import com.example.administrator.zahbzayxy.beans.SignBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
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
    private TextView tv_msg;
    RelativeLayout rl_empty;
    LinearLayout ll_list;
    private ProgressBarLayout mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_sign_up);
        initView();
    }

    private void initData() {
       showLoadingBar(false);
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getSignData(currPage, PageSize, token).enqueue(new Callback<SignBean>() {
            @Override
            public void onResponse(Call<SignBean> call, Response<SignBean> response) {
                if (response != null && response.body() != null && response.body().getApplyList().size() > 0) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        hideLoadingBar();
                        signListBeanList = response.body().getApplyList();
                        if (currPage == 1) {
                           mySignAdapter.setList(signListBeanList);
                        }else{
                            mySignAdapter.addList(signListBeanList);
                        }
                    }
                } else {
                        isVisible(false);
                }
            }

            @Override
            public void onFailure(Call<SignBean> call, Throwable t) {
                hideLoadingBar();
                String message = t.getMessage();
                ToastUtils.showInfo(message,5000);
            }
        });
    }

    private void initView() {
        recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recyclerview);
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.my_file_loading_layout);
        rl_empty= (RelativeLayout) findViewById(R.id.rl_empty_layout);
        ll_list= (LinearLayout) findViewById(R.id.ll_list);
        tv_msg= (TextView) findViewById(R.id.tv_msg);
        myChengJiBack_iv = (ImageView) findViewById(R.id.myChengJiBack_iv);
        myChengJiBack_iv.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //初始化adapter
        mySignAdapter = new MySignAdapter(MySignUpActivity.this, signListBeanList);
        mySignAdapter.setOnItemClickListener(this);
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
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    //item点击事件
    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(MySignUpActivity.this,H5MsgDetailActivity.class);
        intent.putExtra("activityId",String.valueOf(signListBeanList.get(position).getActivityId()));
        intent.putExtra("type","sign");
        startActivity(intent);
    }
    private void isVisible(boolean flag){
        if(flag){
            ll_list.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            ll_list.setVisibility(View.GONE);
            tv_msg.setText("暂无报名信息");
        }
    }
}
