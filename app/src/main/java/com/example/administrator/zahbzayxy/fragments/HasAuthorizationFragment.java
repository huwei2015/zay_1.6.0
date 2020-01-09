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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.MySignUpActivity;
import com.example.administrator.zahbzayxy.adapters.HasAuthorAdapter;
import com.example.administrator.zahbzayxy.adapters.MySignAdapter;
import com.example.administrator.zahbzayxy.beans.AuthStateBean;
import com.example.administrator.zahbzayxy.beans.HasAuthorBean;
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
 * Data 2019-10-25.
 * Time 16:25.
 * 已授权
 */
public class HasAuthorizationFragment extends Fragment implements PullToRefreshListener,View.OnClickListener,HasAuthorAdapter.OnItemClickListener{
    private PullToRefreshRecyclerView recyclerView;
    private View view;
    private String token;
    Context mContext;
    private HasAuthorAdapter hasAuthorAdapter;
    ProgressBarLayout mLoadingBar;
    LinearLayout ll_list;
    RelativeLayout rl_empty;
    TextView tv_msg;
    String orderNumber;
    private List<HasAuthorBean.HasAuthBeanList> hasAuthorListList = new ArrayList<>();
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
        view = inflater.inflate(R.layout.fragment_authorization, container, false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getAuthData(token,4,currentPage,pageSize,null).enqueue(new Callback<HasAuthorBean>() {
            @Override
            public void onResponse(Call<HasAuthorBean> call, Response<HasAuthorBean> response) {
                if(response !=null & response.body() !=null && response.body().getData().getOrderList().size() > 0){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        isVisible(true);
                        hasAuthorListList = response.body().getData().getOrderList();
                        for (int i = 0; i< hasAuthorListList.size(); i++){
                            orderNumber = hasAuthorListList.get(i).getOrderNumber();
                        }
                        if(currentPage == 1){
                            hasAuthorAdapter.setList(hasAuthorListList);
                        }else{
                            hasAuthorAdapter.addList(hasAuthorListList);
                        }

                    }
                }else {
                    isVisible(false);
                }
            }

            @Override
            public void onFailure(Call<HasAuthorBean> call, Throwable t) {
                String msg = t.getMessage();
                Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
                isVisible(false);
            }
        });
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.pull_recycleview);
        tv_msg = view.findViewById(R.id.tv_msg);
        mLoadingBar=view.findViewById(R.id.nb_allOrder_load_bar_layout);
        ll_list = view.findViewById(R.id.ll_list);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //初始化adapter
        hasAuthorAdapter = new HasAuthorAdapter(getActivity(), hasAuthorListList);
        hasAuthorAdapter.setOnItemClickListener(this);
        //添加数据源
        recyclerView.setAdapter(hasAuthorAdapter);
        recyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        recyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        recyclerView.setPullRefreshEnabled(true);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(this);
        recyclerView.setLoadMoreResource(R.drawable.account);
        //主动触发下拉刷新操作
        recyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setEmptyView(emptyView);
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
    public void onResume() {
        super.onResume();
        initView();
        initData();
    }

    @Override
    public void onLoadMore() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLoadMoreComplete();
                if (hasAuthorListList.size() < pageSize) {
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
    public void onClick(View v) {
        switch (v.getId()){
        }
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
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }


    private void getStateData(){
        showLoadingBar(false);
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getStateData(token,orderNumber).enqueue(new Callback<AuthStateBean>() {
            @Override
            public void onResponse(Call<AuthStateBean> call, Response<AuthStateBean> response) {
                if(response !=null && response.body() !=null){
                    String code=response.body().getCode();
                    if(code.equals("00000")){
                        boolean data = response.body().isData();
                        if(data){
                            hideLoadingBar();
                            initData();
                            Toast.makeText(mContext,"授权成功",Toast.LENGTH_LONG).show();
                            hasAuthorAdapter.notifyDataSetChanged();
                        }
                    }else if(code.equals("00097")){
                        Toast.makeText(mContext,response.body().getErrMsg(),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthStateBean> call, Throwable t) {

            }
        });
    }
    //item 点击了
    @Override
    public void onItemClick(View view, int position) {
        getStateData();
        Toast.makeText(mContext,"点击了",Toast.LENGTH_LONG).show();
    }
}
