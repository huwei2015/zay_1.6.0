package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.NotPassAdapter;
import com.example.administrator.zahbzayxy.beans.NotPassBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.example.administrator.zahbzayxy.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 13:36.
 * 考试已经通过
 */
public class AlreadyFragment extends Fragment {
    private NotPassAdapter alreadyAdapter;
    private List<NotPassBean.NotListData> notPassListBeans = new ArrayList<>();
    private View view;
    private int currentPage =1;
    private int PageSize = 10;
    private String token;
    private Context mContext;
    private RelativeLayout rl_empty;
    TextView tv_msg;
    LinearLayout ll_list;
    private boolean isVisible;
    private boolean mLoadView = false;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsHasData = true;
    private boolean mIsLoading;
    private LoadingDialog mLoading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_already,container,false);
        mLoading = new LoadingDialog(mContext);
        mLoading.setShowText("加载中...");
        initView();
        mLoadView = true;
        mIsHasData = true;
        mRefreshLayout.setRefreshing(true);
        initEvent();
        initData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        if (isVisibleToUser && mLoadView) {
            initView();
            mLoadView = true;
            currentPage = 1;
            mIsHasData = true;
            notPassListBeans.clear();
            alreadyAdapter.setList(notPassListBeans);
            mRefreshLayout.setRefreshing(true);
            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initData(){
        if (!isVisible) return;
        SharedPreferences sharedPreferences =mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getExamData(currentPage,PageSize,1,token).enqueue(new Callback<NotPassBean>() {
            @Override
            public void onResponse(Call<NotPassBean> call, Response<NotPassBean> response) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                if(response !=null && response.body() !=null && response.body().getData() != null && response.body().getData().getqLibs() != null){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        emptyLayout(true);
                        List<NotPassBean.NotListData> data = response.body().getData().getqLibs().getData();
                        if (currentPage == 1) {
                            if (data == null || data.size() == 0) {
                                emptyLayout(false);
                                return;
                            }
                            notPassListBeans = data;
                        } else {
                            if (data == null || data.size() < PageSize) {
                                mIsHasData = false;
                            }
                            notPassListBeans.addAll(data);
                        }
                        alreadyAdapter.setList(notPassListBeans);
                        return;
                    }
                }
                emptyLayout(false);
            }

            @Override
            public void onFailure(Call<NotPassBean> call, Throwable t) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                emptyLayout(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        mRefreshLayout = view.findViewById(R.id.pass_refresh_layout);
        mRecyclerView = view.findViewById(R.id.pass_recycler_view);
        Utils.setRefreshViewColor(mRefreshLayout);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        SharedPreferences sharedPreferences =mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        rl_empty= view.findViewById(R.id.rl_empty_layout);//空页面
        ll_list = view.findViewById(R.id.ll_list);
        tv_msg = view.findViewById(R.id.tv_msg);
        //初始化adapter
        alreadyAdapter = new NotPassAdapter(getActivity(), notPassListBeans);
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mRecyclerView.setAdapter(alreadyAdapter);
    }

    private void closeSwipeRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 1;
            mIsHasData = true;
            mIsLoading = true;
            initData();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 没有更多的数据了
                if (!mIsHasData) return;
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == alreadyAdapter.getItemCount() && !mIsLoading) {
                    mLoading.show();
                    currentPage++;
                    mIsLoading = true;
                    initData();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void emptyLayout(boolean isVisible){
        if(isVisible){
            mRefreshLayout.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
            tv_msg.setText("暂无已通过数据");
        }
    }
}
