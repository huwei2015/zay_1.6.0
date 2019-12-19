package com.example.administrator.zahbzayxy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.MySignUpActivity;
import com.example.administrator.zahbzayxy.adapters.HasAuthorAdapter;
import com.example.administrator.zahbzayxy.adapters.MySignAdapter;
import com.example.administrator.zahbzayxy.beans.HasAuthorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-10-25.
 * Time 16:25.
 * 已授权
 */
public class HasAuthorizationFragment extends Fragment implements PullToRefreshListener,View.OnClickListener{
    private PullToRefreshRecyclerView recyclerView;
    private View view;
    private HasAuthorAdapter hasAuthorAdapter;
    private List<HasAuthorBean.HasAuthorList> hasAuthorListList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_authorization, container, false);
        initView();
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.pull_recycleview);
        for (int i =0; i< 8; i++){
            HasAuthorBean.HasAuthorList hasAuthorList = new HasAuthorBean.HasAuthorList();
            hasAuthorList.setOrder_num("112233445566778899");
            hasAuthorList.setTitle("企业负责人-烟花爆竹企业主要负责人(无解析版)");
            hasAuthorListList.add(hasAuthorList);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //初始化adapter
        hasAuthorAdapter = new HasAuthorAdapter(getActivity(), hasAuthorListList);
        //添加数据源
        recyclerView.setAdapter(hasAuthorAdapter);
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
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
