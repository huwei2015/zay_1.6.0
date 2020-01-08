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
import com.example.administrator.zahbzayxy.adapters.AlreadyAdapter;
import com.example.administrator.zahbzayxy.beans.AlreadyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 13:36.
 * 考试已经通过
 */
public class AlreadyFragment extends Fragment implements PullToRefreshListener {
    private PullToRefreshRecyclerView refreshRecyclerView;
    private AlreadyAdapter alreadyAdapter;
    private List<AlreadyBean.AlreadyListBean> alreadyListBeanList = new ArrayList<>();
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_already,container,false);
        initView();
        return view;
    }

    private void initView() {
        refreshRecyclerView= view.findViewById(R.id.recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        for (int i =0; i < 6; i++){
            AlreadyBean.AlreadyListBean alreadyListBean = new AlreadyBean.AlreadyListBean();
            alreadyListBean.setTitle("特种作业人员-初训-煤矿井下爆破作业");
            alreadyListBean.setTime("到期时间:2019.12.21");
            alreadyListBean.setAccount("考试次数：1/2");
            alreadyListBean.setRecord("考试记录》");
            alreadyListBeanList.add(alreadyListBean);
        }
//        //初始化adapter
        alreadyAdapter = new AlreadyAdapter(getActivity(), alreadyListBeanList);
//        //添加数据源
        refreshRecyclerView.setAdapter(alreadyAdapter);
        refreshRecyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        refreshRecyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        refreshRecyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        refreshRecyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        refreshRecyclerView.setPullToRefreshListener(this);
//        refreshRecyclerView.setLoadMoreResource(R.drawable.account);
        //主动触发下拉刷新操作
//        refreshRecyclerView.onRefresh();
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
