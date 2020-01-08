package com.example.administrator.zahbzayxy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.NotPassAdapter;
import com.example.administrator.zahbzayxy.beans.NotPassBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 13:33.
 * 考试未通过
 */
public class NotPassFragment extends Fragment implements PullToRefreshListener {
    private View view;
    private NotPassAdapter notPassAdapter;
    private PullToRefreshRecyclerView refreshRecyclerView;
    private List<NotPassBean.NotPassListBean> notPassListBeans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_not_pass,container,false);
        initView();
        return view;
    }

    private void initView() {
        refreshRecyclerView=view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        for (int i =0; i < 6; i++){
            NotPassBean.NotPassListBean notPassListBean = new NotPassBean.NotPassListBean();
            notPassListBean.setTitle("特种作业人员-初训-煤矿井下爆破作业");
            notPassListBean.setTime("到期时间:2019.12.21");
            notPassListBean.setAccount("考试次数：1/2");
            notPassListBean.setState("入口已关闭");
            notPassListBeans.add(notPassListBean);
        }
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
        //主动触发下拉刷新操作
        refreshRecyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        refreshRecyclerView.setEmptyView(emptyView);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
