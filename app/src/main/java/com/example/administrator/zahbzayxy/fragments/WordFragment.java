package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.AllFileAdapter;
import com.example.administrator.zahbzayxy.beans.AllFileBean;
import com.example.administrator.zahbzayxy.interfaceserver.AllFileInterface;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-24.
 * Time 14:10.
 * wordFragemnt
 */
public class WordFragment extends Fragment implements PullToRefreshListener {
    private View view;
    private String token;
    Context mContext;
    ProgressBarLayout mLoadingBar;
    RelativeLayout rl_empty;
    TextView tv_msg;
    PullToRefreshRecyclerView pullToRefreshRecyclerView;
    AllFileAdapter allFileAdapter;
    LinearLayout ll_list;
    List<AllFileBean.AllFileListBean> allFileListBeanList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_word,container,false);
        initView();
        initPullToRefreshListView();
        return view;
    }
    private void initView() {
        mLoadingBar = view.findViewById(R.id.nb_allOrder_load_bar_layout);
        pullToRefreshRecyclerView = view.findViewById(R.id.word_recyclerView);
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        tv_msg = view.findViewById(R.id.tv_msg);
        ll_list = view.findViewById(R.id.ll_list);
        token = tokenDb.getString("token", "");

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        //初始化adapter
        allFileAdapter = new AllFileAdapter(mContext, allFileListBeanList);
//        //添加数据源
        pullToRefreshRecyclerView.setAdapter(allFileAdapter);
        pullToRefreshRecyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        pullToRefreshRecyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        pullToRefreshRecyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        pullToRefreshRecyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        pullToRefreshRecyclerView.setPullToRefreshListener(this);
        //主动触发下拉刷新操作
//        pullToRefreshRecyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(mContext, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        pullToRefreshRecyclerView.setEmptyView(emptyView);
    }
    private void initPullToRefreshListView() {
        showLoadingBar(false);
        AllFileInterface allFileInterface = RetrofitUtils.getInstance().createClass(AllFileInterface.class);
        allFileInterface.getAllFileData(1,10,2,token).enqueue(new Callback<AllFileBean>() {
            @Override
            public void onResponse(Call<AllFileBean> call, Response<AllFileBean> response) {
                if(response !=null && response.body() !=null){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        isVisible(true);
                        hideLoadingBar();
                        List<AllFileBean.AllFileListBean> data = response.body().getData().getData();
                        if(data.size() > 0) {
                            allFileAdapter.setList(data);
                        }else{
                            isVisible(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AllFileBean> call, Throwable t) {

            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

    private void isVisible(boolean flag) {
        if (flag) {
            ll_list.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        } else {
            rl_empty.setVisibility(View.VISIBLE);
            ll_list.setVisibility(View.GONE);
            tv_msg.setText("暂无word文档");
        }
    }
}