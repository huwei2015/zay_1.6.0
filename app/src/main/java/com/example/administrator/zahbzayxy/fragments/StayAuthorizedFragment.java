package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.StayAuthorAdapter;
import com.example.administrator.zahbzayxy.beans.StayAuthorBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
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
 * 未授权
 */
public class StayAuthorizedFragment extends Fragment implements View.OnClickListener {
    View view;
    String token;
    private Context mContext;
    private StayAuthorAdapter stayAuthorAdapter;
    private List<StayAuthorBean.StayAuthBeanList> stayAuthorLists =new ArrayList<>();
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    LinearLayout ll_list;
    RelativeLayout rl_empty;
    TextView tv_msg;
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
        view = inflater.inflate(R.layout.fragment_un_authorization, container, false);
        mLoading = new LoadingDialog(mContext);
        mLoading.setShowText("加载中...");
        initView();
        initData();
        return view;
    }

    private void initData() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getUnAuthData(token, 1, currentPage, pageSize, null).enqueue(new Callback<StayAuthorBean>() {
            @Override
            public void onResponse(Call<StayAuthorBean> call, Response<StayAuthorBean> response) {
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
                        List<StayAuthorBean.StayAuthBeanList> orderList = response.body().getData().getOrderList();
                        if(currentPage == 1) {
                            stayAuthorLists = orderList;
                            stayAuthorAdapter.setList(stayAuthorLists);
                            if (stayAuthorLists.size() < pageSize) {
                                mIsHasData = false;
                            }
                        } else {
                            if (orderList == null || orderList.size() == 0) {
                                mIsHasData = false;
                                ToastUtils.showShortInfo("没有更多数据了");
                            } else {
                                if (orderList.size() < pageSize) {
                                    mIsHasData = false;
                                    ToastUtils.showShortInfo("数据加载完毕");
                                }
                                stayAuthorLists.addAll(orderList);
                                stayAuthorAdapter.setList(stayAuthorLists);
                            }
                        }
                    } else {
                        ToastUtils.showShortInfo(response.body().getErrMsg());
                    }
                }else{
                    if (currentPage == 1){
                        isVisible(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<StayAuthorBean> call, Throwable t) {
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
        ll_list = view.findViewById(R.id.ll_list);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        //初始化adapter
        stayAuthorAdapter = new StayAuthorAdapter(getActivity(), stayAuthorLists);
        mRecyclerView.setAdapter(stayAuthorAdapter);
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        initEvent();
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == stayAuthorAdapter.getItemCount() && !mIsLoading) {
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

    }

    @Override
    public void onResume() {
        super.onResume();
//        initView();
//        initData();
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
}
