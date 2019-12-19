package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.SearchTestDetailAdapter;
import com.example.administrator.zahbzayxy.beans.SearchTestBean;
import com.example.administrator.zahbzayxy.myinterface.UpPx;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearcTestDetailActivity extends BaseActivity {
    Unbinder mUnBinder;
    @BindView(R.id.search_test_detail_rv)
    MyRecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private int position;
    private int size;
    List<SearchTestBean.DataBean> totalList;
    SearchTestDetailAdapter searchTestDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searc_test_detail);
        mUnBinder= ButterKnife.bind(this);
        initView();
        initRecyClerView();
    }

    private void initView() {
        totalList = (List<SearchTestBean.DataBean>) getIntent().getSerializableExtra("searchTestList");
        position = getIntent().getIntExtra("searchListPostion", 0);
        size=totalList.size();

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        searchTestDetailAdapter=new SearchTestDetailAdapter(totalList,SearcTestDetailActivity.this);
        recyclerView.setAdapter(searchTestDetailAdapter);
        Log.e("currentPostion",position+"");
        recyclerView.scrollToPosition(position);
        searchTestDetailAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.search_test_detail_close_iv,R.id.search_test_detail_left_iv,R.id.search_test_detail_right_iv,R.id.search_test_detail_back_list_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.search_test_detail_close_iv:
                finish();
                break;
            case R.id.search_test_detail_left_iv:
                toLeft();
                break;
            case R.id.search_test_detail_right_iv:
                toRight();
                break;
            case R.id.search_test_detail_back_list_iv:
                finish();
                break;
                default:
                    break;

        }
    }

    private void toRight() {
        if (size>0) {
            //右
            if (position <= size - 1) {
                position = position + 1;
               recyclerView.scrollToPosition(position);
            }
        }
    }

    private void toLeft() {
        if (size>0) {
            if (position >= 0) {
                Log.e("leftPostion",position+"");
                //左
                position = position - 1;
                recyclerView.scrollToPosition(position);
                searchTestDetailAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initRecyClerView() {
        recyclerView.setUp(new UpPx() {
            @Override
            public void upPx(int tag) {
                if (tag==1){
                    //左
                    if(position>=0){
                        position= position-1;
                        recyclerView.scrollToPosition(position);
                       searchTestDetailAdapter.notifyDataSetChanged();
                    }

                }else if (tag==2){
                    //右
                    if(position<=size-1){
                        position = position+1;
                       recyclerView.scrollToPosition(position);
                        searchTestDetailAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder!=null){
            mUnBinder.unbind();
        }
    }
}
