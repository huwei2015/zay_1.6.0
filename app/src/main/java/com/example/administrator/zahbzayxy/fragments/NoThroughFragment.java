package com.example.administrator.zahbzayxy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.NotThrougAdapter;
import com.example.administrator.zahbzayxy.beans.NotThroughBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-02.
 * Time 11:40.
 * 未通过
 */
public class NoThroughFragment extends Fragment implements PullToRefreshListener,NotThrougAdapter.OnItemClickListener{
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    private NotThrougAdapter througAdapter;
    private View view;
    private List<NotThroughBean.ThrougListBean> notPassListBeans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_no_through,container,false);
        initView();
        return view;
    }

    private void initView() {
        pullToRefreshRecyclerView=view.findViewById(R.id.pull_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        for (int i =0; i < 10; i++){
            NotThroughBean.ThrougListBean througListBean = new NotThroughBean.ThrougListBean();
            througListBean.setTitle("主要负责人-初训-煤炭生产经营单位(董事长-总经理)");
            notPassListBeans.add(througListBean);
        }
//        //初始化adapter
        througAdapter = new NotThrougAdapter(getActivity(), notPassListBeans);
        througAdapter.setOnItemClickListener(this);
//        //添加数据源
        pullToRefreshRecyclerView.setAdapter(througAdapter);
        pullToRefreshRecyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        pullToRefreshRecyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        pullToRefreshRecyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        pullToRefreshRecyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        pullToRefreshRecyclerView.setPullToRefreshListener(this);
//        refreshRecyclerView.setLoadMoreResource(R.drawable.account);
        //主动触发下拉刷新操作
//        pullToRefreshRecyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        pullToRefreshRecyclerView.setEmptyView(emptyView);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    //Item点击事件
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(),"点击了"+position,Toast.LENGTH_LONG).show();
    }
}
