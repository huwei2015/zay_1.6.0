package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.ExamRecordAdapter;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiListBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019/8/22.
 * Time 17:08.
 * Description. 考试记录
 */
public class ExamRecordActivity extends BaseActivity implements View.OnClickListener, PullToRefreshListener {
    private ImageView img_back;
    private ExamRecordAdapter examRecordAdapter;
    private PullToRefreshRecyclerView recyclerView;
    private RelativeLayout rl_empty;
    private String token;
    private int examType;
    private int libId;
    private int currentPage = 1;
    private int pageSize = 10;
    List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> examScoresEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_record);
        initView();
        initListViewData();
    }

    private void initView() {
        examType = getIntent().getIntExtra("examType", 0);
        libId = getIntent().getIntExtra("libId", 0);
        img_back = (ImageView) findViewById(R.id.nb_order_return);
        rl_empty= (RelativeLayout) findViewById(R.id.rl_empty_layout);//空页面
        img_back.setOnClickListener(this);
        recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        //初始化adapter
        examRecordAdapter = new ExamRecordAdapter(ExamRecordActivity.this, examScoresEntities);
//        //添加数据源
        recyclerView.setAdapter(examRecordAdapter);
        recyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        recyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        recyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(ExamRecordActivity.this);
        recyclerView.setLoadMoreResource(R.drawable.account);
        //主动触发下拉刷新操作
        recyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(this, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setEmptyView(emptyView);
    }

    private void initListViewData() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyTiKuGradeData(currentPage, pageSize, token, libId, examType).enqueue(new Callback<NewMyChengJiListBean>() {
            @Override
            public void onResponse(Call<NewMyChengJiListBean> call, Response<NewMyChengJiListBean> response) {
                if (response != null && response.body() != null && response.body().getData() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        if (currentPage == 1 && response.body().getData().getExamScores().size() == 0) {
                            isVisible(false);
                        } else {
                            isVisible(true);
                        }
                        List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> dataList = response.body().getData().getExamScores();
                        if(currentPage == 1) {
                            examScoresEntities = dataList;
                            examRecordAdapter.setList(examScoresEntities);
                            if (examScoresEntities.size() < pageSize) {
                                recyclerView.setLoadingMoreEnabled(false);
                            }
                        } else {
                            if (dataList == null || dataList.size() == 0) {
                                recyclerView.setLoadingMoreEnabled(false);
                                ToastUtils.showShortInfo("没有更多数据了");
                            } else {
                                if (dataList.size() < pageSize) {
                                    recyclerView.setLoadingMoreEnabled(false);
                                    ToastUtils.showShortInfo("数据加载完毕");
                                } else {
                                    examScoresEntities.addAll(dataList);
                                    examRecordAdapter.setList(examScoresEntities);
                                }
                            }
                        }
                    } else {
                        ToastUtils.showShortInfo(response.body().getErrMsg());
                    }
                } else {
                    if (currentPage == 1){
                        isVisible(false);
                    }
                }

            }

            @Override
            public void onFailure(Call<NewMyChengJiListBean> call, Throwable t) {
                isVisible(false);
                Toast.makeText(ExamRecordActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nb_order_return:
                this.finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setRefreshComplete();
                currentPage = 1;
                initListViewData();
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
                if (examScoresEntities.size() < pageSize) {
                    Toast.makeText(ExamRecordActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    recyclerView.setLoadingMoreEnabled(false);
                    return;
                }
                currentPage++;
                initListViewData();
            }
        }, 2000);
    }

    private void isVisible(boolean flag) {
        if (flag) {
            recyclerView.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        } else {
            rl_empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
