package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.example.administrator.zahbzayxy.vo.UserInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private Context mContext;
    private String token;
    private int  currenPage =1;
    private int pageSize =10;
    private List<NotThroughBean.THrougListData> notPassListBeans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_no_through,container,false);
        initView();
        initData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    private void initData(){
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getQuestionData(currenPage,pageSize,250,"0",token).enqueue(new Callback<NotThroughBean>() {
            @Override
            public void onResponse(Call<NotThroughBean> call, Response<NotThroughBean> response) {
                        if(response !=null && response.body() !=null){
                            String code = response.body().getCode();
                            if(code.equals("00000")){
                                notPassListBeans =response.body().getData().getData();
                            }
                        }
            }

            @Override
            public void onFailure(Call<NotThroughBean> call, Throwable t) {
                ToastUtils.showInfo(t.getMessage(),5000);
            }
        });
    }
    private void initView() {
        SharedPreferences sharedPreferences =mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        pullToRefreshRecyclerView=view.findViewById(R.id.pull_recycleview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

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
