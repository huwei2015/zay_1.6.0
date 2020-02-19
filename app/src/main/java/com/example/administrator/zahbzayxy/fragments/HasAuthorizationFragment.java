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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.MySignUpActivity;
import com.example.administrator.zahbzayxy.adapters.HasAuthorAdapter;
import com.example.administrator.zahbzayxy.adapters.MySignAdapter;
import com.example.administrator.zahbzayxy.beans.AuthStateBean;
import com.example.administrator.zahbzayxy.beans.HasAuthorBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.example.administrator.zahbzayxy.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-10-25.
 * Time 16:25.
 * 已授权
 */
public class HasAuthorizationFragment extends Fragment implements View.OnClickListener,HasAuthorAdapter.OnItemClickListener{
    private View view;
    private String token;
    Context mContext;
    private HasAuthorAdapter hasAuthorAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    ProgressBarLayout mLoadingBar;
    LinearLayout ll_list;
    RelativeLayout rl_empty;
    TextView tv_msg;
    String orderNumber;
    private List<HasAuthorBean.HasAuthBeanList> hasAuthorListList = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 10;
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
        view = inflater.inflate(R.layout.fragment_authorization, container, false);
        mLoading = new LoadingDialog(mContext);
        mLoading.setShowText("加载中...");
        initView();
        return view;
    }

    private void initData() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getAuthData(token,4,currentPage,pageSize,null).enqueue(new Callback<HasAuthorBean>() {
            @Override
            public void onResponse(Call<HasAuthorBean> call, Response<HasAuthorBean> response) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                if(response !=null && response.body() !=null && response.body().getData() != null){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        if (currentPage == 1 && response.body().getData().getOrderList().size() == 0) {
                            isVisible(false);
                        } else {
                            isVisible(true);
                        }
                        List<HasAuthorBean.HasAuthBeanList> hasAuthBeanLists = response.body().getData().getOrderList();
                        if(currentPage == 1) {
                            hasAuthorListList.clear();
                            hasAuthorListList.addAll(hasAuthBeanLists);
                            hasAuthorAdapter.setList(hasAuthorListList);
                            if (hasAuthorListList.size() < pageSize) {
                                mIsHasData = false;
                            }
                        } else {
                            if (hasAuthBeanLists == null || hasAuthBeanLists.size() == 0) {
                                mIsHasData = false;
                                ToastUtils.showShortInfo("没有更多数据了");
                            } else {
                                if (hasAuthBeanLists.size() < pageSize) {
                                    mIsHasData = false;
                                    ToastUtils.showShortInfo("数据加载完毕");
                                }
                                hasAuthorListList.addAll(hasAuthBeanLists);
                                hasAuthorAdapter.setList(hasAuthorListList);
                            }
                        }
                    } else {
                        ToastUtils.showShortInfo(response.body().getErrMsg());
                    }
                } else {
                    if (currentPage == 1){
                        isVisible(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<HasAuthorBean> call, Throwable t) {
                String msg = t.getMessage();
                Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
                isVisible(false);
                closeSwipeRefresh();
                mIsLoading = false;
                mLoading.dismiss();
            }
        });
    }

    private void initView() {
        mRefreshLayout = view.findViewById(R.id.author_data_refresh_layout);
        mRecyclerView = view.findViewById(R.id.author_data_recycler_view);
        Utils.setRefreshViewColor(mRefreshLayout);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        tv_msg = view.findViewById(R.id.tv_msg);
        mLoadingBar=view.findViewById(R.id.nb_allOrder_load_bar_layout);
        ll_list = view.findViewById(R.id.ll_list);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        //初始化adapter
        hasAuthorAdapter = new HasAuthorAdapter(getActivity(), hasAuthorListList);
        hasAuthorAdapter.setOnItemClickListener(this);
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mRecyclerView.setAdapter(hasAuthorAdapter);
        initEvent();
        mRefreshLayout.setRefreshing(true);
        initData();
    }


    private void initEvent(){
        mRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 1;
            mIsHasData = true;
            initData();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 没有更多的数据了
                if (!mIsHasData) return;
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == hasAuthorAdapter.getItemCount() && !mIsLoading) {
                    Log.i("======load=====","加载下一页");
                    mLoading.show();
                    currentPage++;
                    initData();
                    mIsLoading = true;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void closeSwipeRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
    private void isVisible(boolean flag) {
        if (flag) {
            ll_list.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        } else {
            rl_empty.setVisibility(View.VISIBLE);
            ll_list.setVisibility(View.GONE);
            tv_msg.setText("暂无授权信息");
        }
    }
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }


    private void getStateData(int position){
        if (position >= hasAuthorListList.size()) {
            return;
        }
        HasAuthorBean.HasAuthBeanList bean = hasAuthorListList.get(position);
        if (bean == null) {
            Toast.makeText(mContext,"数据异常",Toast.LENGTH_LONG).show();
            return;
        }
        orderNumber = bean.getOrderNumber();
        showLoadingBar(false);
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getStateData(token,orderNumber).enqueue(new Callback<AuthStateBean>() {
            @Override
            public void onResponse(Call<AuthStateBean> call, Response<AuthStateBean> response) {
                hideLoadingBar();
                if(response !=null && response.body() !=null){
                    String code=response.body().getCode();
                    if(code.equals("00000")){
                        boolean data = response.body().isData();
                        if(data){
                            initData();
                            Toast.makeText(mContext,"授权成功",Toast.LENGTH_LONG).show();
                            hasAuthorAdapter.notifyDataSetChanged();
                        }
                    }else if(code.equals("00097")){
                        Toast.makeText(mContext,response.body().getErrMsg(),Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mContext,response.body().getErrMsg(),Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext,"网络异常",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthStateBean> call, Throwable t) {
                hideLoadingBar();
                Toast.makeText(mContext,"网络异常",Toast.LENGTH_LONG).show();
            }
        });
    }
    //item 点击了
    @Override
    public void onItemClick(View view, int position) {
        getStateData(position);
    }
}
