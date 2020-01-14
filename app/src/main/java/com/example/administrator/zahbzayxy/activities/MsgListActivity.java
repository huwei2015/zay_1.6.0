package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MsgAdapter;
import com.example.administrator.zahbzayxy.adapters.MySignAdapter;
import com.example.administrator.zahbzayxy.beans.SignBean;
import com.example.administrator.zahbzayxy.beans.TimeData;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.TimeComparator;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-13.
 * Time 11:13.
 */
public class MsgListActivity extends BaseActivity implements View.OnClickListener, PullToRefreshListener,MsgAdapter.onClickItemListener{
    private PullToRefreshRecyclerView recyclerView;
    private ImageView exam_archives_back;
    private TextView tab_unread_message;
    private ProgressBarLayout mLoadingBar;
    private List<TimeData.MsgList> msgLists = new ArrayList<>();
    private int  currenPage =1;
    private int PageSize =10;
    private String token;
    MsgAdapter adapter;
    TextView tv_msg;
    RelativeLayout rl_empty;
    LinearLayout ll_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        initView();
        initData();
    }
    private void initView() {
        String messageNum = getIntent().getStringExtra("messageNum");
        recyclerView= (PullToRefreshRecyclerView) findViewById(R.id.recyclerview);
        exam_archives_back= (ImageView) findViewById(R.id.exam_archives_back);
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        Log.i("hw","hw============="+token);
        exam_archives_back.setOnClickListener(this);
        tab_unread_message= (TextView) findViewById(R.id.tab_unread_message);
        if(!TextUtils.isEmpty(messageNum)){
            int msg_count = Integer.parseInt(messageNum);
            if(msg_count > 0){
                tab_unread_message.setVisibility(View.VISIBLE);
                tab_unread_message.setText(messageNum);
            }
        }
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty_layout);
        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.my_file_loading_layout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //初始化adapter
        adapter = new MsgAdapter(MsgListActivity.this, msgLists);
        adapter.setOnClickItemListener(this);
        //添加数据源
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        recyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        recyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(MsgListActivity.this);
        //主动触发下拉刷新操作
        recyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(this, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setEmptyView(emptyView);
    }
    private void initData() {
        mLoadingBar.setShowContent("加载中");
        mLoadingBar.setVisibility(View.VISIBLE);
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getSystemMsg(currenPage,PageSize,3,token,"yes").enqueue(new Callback<TimeData>() {
            @Override
            public void onResponse(Call<TimeData> call, Response<TimeData> response) {
                    if(response !=null && response.body()!=null ){
                        if (currenPage == 1 && response.body().getData().getAllTopAnnounceList().size() == 0) {
                            emptyLayout(false);
                        } else {
                            emptyLayout(true);
                        }
                        String code = response.body().getCode();
                        if(code.equals("00000")){
                            mLoadingBar.setVisibility(View.GONE);
                            msgLists = response.body().getData().getAllTopAnnounceList();
                            if(currenPage ==1 ){
                                adapter.setList(msgLists);
                            }else{
                                adapter.addList(msgLists);
                            }
                        }else{
                            emptyLayout(false);
                        }
                    }
            }

            @Override
            public void onFailure(Call<TimeData> call, Throwable t) {
                mLoadingBar.setVisibility(View.GONE);
                ToastUtils.showInfo(t.getMessage(),5000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exam_archives_back:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshComplete();
                currenPage = 1;
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
                if (msgLists.size() < PageSize) {
                    Toast.makeText(MsgListActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    recyclerView.setLoadingMoreEnabled(false);
                    return;
                }
                currenPage++;
                initData();
            }
        }, 2000);
    }
    private void emptyLayout(boolean isVisible){
        if(isVisible){
            recyclerView.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            tv_msg.setText("暂无消息");
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(MsgListActivity.this,H5MsgDetailActivity.class);
        intent.putExtra("id",String.valueOf(msgLists.get(position).getId()));
        intent.putExtra("type","msg");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
