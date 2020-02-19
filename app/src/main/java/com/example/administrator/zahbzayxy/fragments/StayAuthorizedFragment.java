package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.administrator.zahbzayxy.adapters.StayAuthorAdapter;
import com.example.administrator.zahbzayxy.beans.StayAuthorBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

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
public class StayAuthorizedFragment extends Fragment implements PullToRefreshListener, View.OnClickListener {
    private PullToRefreshRecyclerView recyclerView;
    View view;
    String token;
    private Context mContext;
    private StayAuthorAdapter stayAuthorAdapter;
    private List<StayAuthorBean.StayAuthBeanList> stayAuthorLists =new ArrayList<>();
    LinearLayout ll_list;
    RelativeLayout rl_empty;
    TextView tv_msg;
    private int currentPage = 1;
    private int pageSize = 10;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_un_authorization, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getUnAuthData(token, 1, currentPage, pageSize, null).enqueue(new Callback<StayAuthorBean>() {
            @Override
            public void onResponse(Call<StayAuthorBean> call, Response<StayAuthorBean> response) {
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
                                recyclerView.setLoadingMoreEnabled(false);
                            }
                        } else {
                            if (orderList == null || orderList.size() == 0) {
                                recyclerView.setLoadingMoreEnabled(false);
                                ToastUtils.showShortInfo("没有更多数据了");
                            } else {
                                if (orderList.size() < pageSize) {
                                    recyclerView.setLoadingMoreEnabled(false);
                                    ToastUtils.showShortInfo("数据加载完毕");
                                } else {
                                    stayAuthorLists.addAll(orderList);
                                    stayAuthorAdapter.setList(stayAuthorLists);
                                }
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
            }
        });
    }

    private void initView() {
        tv_msg = view.findViewById(R.id.tv_msg);
        ll_list = view.findViewById(R.id.ll_list);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        recyclerView = view.findViewById(R.id.pull_recycleview);
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //初始化adapter
        stayAuthorAdapter = new StayAuthorAdapter(getActivity(), stayAuthorLists);
        recyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        recyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        recyclerView.setPullRefreshEnabled(true);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(this);
        //添加数据源
        recyclerView.setAdapter(stayAuthorAdapter);
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
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshComplete();
                currentPage = 1;
                initData();
                recyclerView.setLoadingMoreEnabled(true);
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLoadMoreComplete();
                if (stayAuthorLists.size() < pageSize) {
                    Toast.makeText(mContext, "没有更多数据", Toast.LENGTH_SHORT).show();
                    recyclerView.setLoadingMoreEnabled(false);
                    return;
                }
                currentPage++;
                initData();
            }
        }, 2000);
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
