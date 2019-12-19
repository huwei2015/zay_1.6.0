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
import com.example.administrator.zahbzayxy.adapters.HasAuthorAdapter;
import com.example.administrator.zahbzayxy.adapters.StayAuthorAdapter;
import com.example.administrator.zahbzayxy.beans.HasAuthorBean;
import com.example.administrator.zahbzayxy.beans.StayAuthorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-10-25.
 * Time 16:25.
 * 未授权
 */
public class StayAuthorizedFragment  extends Fragment implements PullToRefreshListener,View.OnClickListener{
    private PullToRefreshRecyclerView recyclerView;
    View view;
    private StayAuthorAdapter stayAuthorAdapter;
    private List<StayAuthorBean.StayAuthorList> stayAuthorLists =new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_un_authorization,container,false);
        initView();
        return view;
    }

    private void initView() {
        recyclerView=view.findViewById(R.id.pull_recycleview);
        for (int i =0; i< 8; i++){
            StayAuthorBean.StayAuthorList stayBean = new StayAuthorBean.StayAuthorList();
            stayBean.setOrder_num("112233445566778899");
            stayBean.setTitle("企业负责人-烟花爆竹企业主要负责人(无解析版)");
            stayAuthorLists.add(stayBean);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //初始化adapter
        stayAuthorAdapter = new StayAuthorAdapter(getActivity(), stayAuthorLists);
        //添加数据源
        recyclerView.setAdapter(stayAuthorAdapter);
        recyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        recyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        recyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(this);
        recyclerView.setLoadMoreResource(R.drawable.account);
        //主动触发下拉刷新操作
//        recyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setEmptyView(emptyView);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
