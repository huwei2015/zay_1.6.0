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
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.NotPassAdapter;
import com.example.administrator.zahbzayxy.beans.NotPassBean;
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
 * Time 13:36.
 * 考试已经通过
 */
public class AlreadyFragment extends Fragment implements PullToRefreshListener {
    private PullToRefreshRecyclerView refreshRecyclerView;
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
        view=inflater.inflate(R.layout.fragment_already,container,false);
        initView();
        mLoadView = true;
        initData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        if (isVisibleToUser && mLoadView) {
            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initData(){
        if (!isVisible) return;
        showLoadingBar(false);
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getExamData(currentPage,PageSize,1,token).enqueue(new Callback<NotPassBean>() {
            @Override
            public void onResponse(Call<NotPassBean> call, Response<NotPassBean> response) {
                hideLoadingBar();
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
                                refreshRecyclerView.setLoadingMoreEnabled(false);
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
                hideLoadingBar();
                emptyLayout(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        SharedPreferences sharedPreferences =mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        refreshRecyclerView= view.findViewById(R.id.recycle);
        rl_empty= view.findViewById(R.id.rl_empty_layout);//空页面
        ll_list = view.findViewById(R.id.ll_list);
        tv_msg = view.findViewById(R.id.tv_msg);
        mLoadingBar = view.findViewById(R.id.nb_allOrder_load_bar_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //初始化adapter
        alreadyAdapter = new NotPassAdapter(getActivity(), notPassListBeans);
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
        refreshRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshRecyclerView.setRefreshComplete();
                currentPage = 1;
                refreshRecyclerView.setLoadingMoreEnabled(true);
                initData();
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
            tv_msg.setText("暂无已通过数据");
        }
    }
}
