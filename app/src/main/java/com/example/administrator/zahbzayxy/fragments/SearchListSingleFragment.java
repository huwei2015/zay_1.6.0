package com.example.administrator.zahbzayxy.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.SearcTestDetailActivity;
import com.example.administrator.zahbzayxy.adapters.SearchListAdapter;
import com.example.administrator.zahbzayxy.beans.SearchTestBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.myviews.CustomDecoration;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchListSingleFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.searchList_rv)
    RecyclerView searchList_rv;
    @BindView(R.id.searchList_smr)
    SmartRefreshLayout searchList_smr;
    private int quesLibId;
    String token;
    private String keyword;
    List<SearchTestBean.DataBean> totalList=new ArrayList<>();
    SearchListAdapter searchListAdapter;
    int pager;
    Context mContext;
    View view;
    private int testType;

    public SearchListSingleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_search_list, container, false);
        unbinder= ButterKnife.bind(this,view);
        initView();
        return view;
    }
    private void initView() {
        quesLibId = getActivity().getIntent().getIntExtra("quesLibId", 0);
        keyword = getActivity().getIntent().getStringExtra("keyword");
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = tokenDb.getString("token","");

        searchList_rv.setLayoutManager(new LinearLayoutManager(mContext));
        //解决数据加载不完的问题
        searchList_rv.setNestedScrollingEnabled(false);
        searchList_rv.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        searchList_rv.setFocusable(false);
        searchListAdapter=new SearchListAdapter(totalList,mContext);
        searchList_rv.setAdapter(searchListAdapter);

        //添加自定义分割线
        searchList_rv.addItemDecoration(new CustomDecoration(mContext, CustomDecoration.VERTICAL_LIST,
                R.drawable.recyclerview_decoration, 0));

        searchList_smr.setEnableRefresh(false);
        pager++;
        downLoadData(pager,false);
        searchList_smr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pager++;
                downLoadData(pager,false);
            }
        });
        searchList_smr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pager=1;
                downLoadData(pager,true);
            }
        });



        searchListAdapter.setOnItemClickListener(new SearchListAdapter.OnItemClickListener() {
            @Override
            public void onItemOnClick(int position) {
                Intent intent=new Intent(mContext, SearcTestDetailActivity.class);
                intent.putExtra("searchTestList", (Serializable) totalList);
                intent.putExtra("searchListPostion",position);
                startActivity(intent);

            }
        });

    }

    private void downLoadData(int pager,final boolean isPull) {
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getSearchTestResultPath(token,keyword,quesLibId,1,10,pager).enqueue(new Callback<SearchTestBean>() {
            @Override
            public void onResponse(Call<SearchTestBean> call, Response<SearchTestBean> response) {
                if (response!=null){
                    SearchTestBean body = response.body();
                    if (body!=null){
                        Object errMsg = body.getErrMsg();
                        if (errMsg==null){
                            List<SearchTestBean.DataBean> data = body.getData();
                      //      totalList.clear();
                            totalList.addAll(data);
                            searchListAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(mContext, ""+errMsg, Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (isPull) {
                        searchList_smr.finishRefresh();
                    } else {
                        searchList_smr.finishLoadMore();
                    }

                }

            }

            @Override
            public void onFailure(Call<SearchTestBean> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
