package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.example.administrator.zahbzayxy.adapters.NotPassAdapter;
import com.example.administrator.zahbzayxy.beans.NotPassBean;
import com.example.administrator.zahbzayxy.beans.NotThroughBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 13:33.
 * 考试正式未通过
 */
public class NotPassFragment extends Fragment implements PullToRefreshListener {
    private View view;
    private NotPassAdapter notPassAdapter;
    private PullToRefreshRecyclerView refreshRecyclerView;
    private List<NotPassBean.NotListData> notPassListBeans = new ArrayList<>();
    private int currentPage =1;
    private int PageSize = 10;
    private String token;
    private Context mContext;
    private RelativeLayout rl_empty;
    TextView tv_msg;
    LinearLayout ll_list;
    private ProgressBarLayout mLoadingBar;
    private boolean isVisible;
    private boolean mLoadView = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_not_pass,container,false);
        initView();
        mLoadView = true;
        initData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        if (isVisibleToUser && mLoadView) {
            initView();
            mLoadView = true;
            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initData(){
        if (!isVisible) return;
        showLoadingBar(false);
        SharedPreferences sharedPreferences =mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getExamData(currentPage,PageSize,0,token).enqueue(new Callback<NotPassBean>() {
            @Override
            public void onResponse(Call<NotPassBean> call, Response<NotPassBean> response) {
                hideLoadingBar();
                    if(response !=null && response.body() !=null){
                        NotPassBean.NotPassListBean data1 = response.body().getData();
                        List<NotPassBean.NotListData> listData = null;
                        if (data1 != null) {
                            NotPassBean.NotDataBean notDataBean = data1.getqLibs();
                            if (notDataBean != null) {
                                listData = notDataBean.getData();
                            }
                        }
                        if (currentPage == 1 && listData != null && listData.size() == 0) {
                            emptyLayout(false);
                        } else {
                            emptyLayout(true);
                        }
                        if (listData == null) {
                            return;
                        }
                        String code = response.body().getCode();
                        List<NotPassBean.NotListData> data = response.body().getData().getqLibs().getData();
                        if(code.equals("00000")){
                            emptyLayout(true);
                            if (currentPage == 1) {
                                if (data == null || data.size() == 0) {
                                    emptyLayout(false);
                                    return;
                                }
                                notPassListBeans = data;
                            } else {
                                if (data == null || data.size() < PageSize) {
                                    refreshRecyclerView.setLoadingMoreEnabled(false);
                                }
                                notPassListBeans.addAll(data);
                            }
                            notPassAdapter.setList(notPassListBeans);
                            return;
                        }else{
                            hideLoadingBar();
                            emptyLayout(false);
                        }
                    }
            }

            @Override
            public void onFailure(Call<NotPassBean> call, Throwable t) {
                hideLoadingBar();
            }
        });
    }
    private void initView() {
        SharedPreferences sharedPreferences =mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        refreshRecyclerView=view.findViewById(R.id.recyclerview);
        rl_empty= view.findViewById(R.id.rl_empty_layout);//空页面
        ll_list = view.findViewById(R.id.ll_list);
        tv_msg = view.findViewById(R.id.tv_msg);
        mLoadingBar = view.findViewById(R.id.nb_allOrder_load_bar_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        //初始化adapter
        notPassAdapter = new NotPassAdapter(getActivity(), notPassListBeans);
//        //添加数据源
        refreshRecyclerView.setAdapter(notPassAdapter);
        refreshRecyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        refreshRecyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        refreshRecyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        refreshRecyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        refreshRecyclerView.setPullToRefreshListener(this);
//        //主动触发下拉刷新操作
//        refreshRecyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        refreshRecyclerView.setEmptyView(emptyView);
    }

    @Override
    public void onRefresh() {
        refreshRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshRecyclerView.setRefreshComplete();
                currentPage = 1;
                initData();
                refreshRecyclerView.setLoadingMoreEnabled(true);
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        refreshRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshRecyclerView.setLoadMoreComplete();
                if (notPassListBeans.size() < PageSize) {
                    Toast.makeText(getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    refreshRecyclerView.setLoadingMoreEnabled(false);
                    return;
                }
                currentPage++;
                initData();
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

    private void emptyLayout(boolean isVisible){
        if(isVisible){
            refreshRecyclerView.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            refreshRecyclerView.setVisibility(View.GONE);
            tv_msg.setText("暂无未通过数据");
        }
    }
}
