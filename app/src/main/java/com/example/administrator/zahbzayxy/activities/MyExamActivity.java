package com.example.administrator.zahbzayxy.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.ExamAdapter;
import com.example.administrator.zahbzayxy.beans.ExamBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.example.administrator.zahbzayxy.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019/8/5.
 * Time 14:30.
 * Description.我的考试
 */
public class MyExamActivity extends BaseActivity implements View.OnClickListener, ExamAdapter.OnItemClickListener {
    private ImageView img_back;
    private ExamAdapter examAdapter;
    private List<ExamBean.QuesLibsBean> examBeanList = new ArrayList<>();
    private String token;
    private int currentPage = 1;
    private int pageSize = 10;
    int userQuesLibId;
    int quesLibId;
    boolean data;
    private RelativeLayout rl_empty;
    private TextView tv_msg;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsHasData = true;
    private boolean mIsLoading;
    private LoadingDialog mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exam);
        mLoading = new LoadingDialog(MyExamActivity.this);
        mLoading.setShowText("加载中...");
        initView();
        getExamList();
    }


    private void getExamList() {
        mLoading.show();
        SharedPreferences sharedPreferences =getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getExamList(token, currentPage, pageSize).enqueue(new Callback<ExamBean>() {
            @Override
            public void onResponse(Call<ExamBean> call, Response<ExamBean> response) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                if (response != null && response.body() != null && response.body().getData() != null && response.body().getData().getQuesLibs() != null) {
                    if (currentPage == 1 && response.body().getData().getQuesLibs().size() == 0) {
                        emptyLayout(false);
                    } else {
                        emptyLayout(true);
                    }
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        emptyLayout(true);
                        List<ExamBean.QuesLibsBean> beanList = response.body().getData().getQuesLibs();
                        if (currentPage == 1) {
                            if (beanList == null || beanList.size() == 0) {
                                emptyLayout(false);
                            }
                            examBeanList.clear();
                            examBeanList.addAll(beanList);
                            examAdapter.setList(examBeanList);
                        } else {
                            if (beanList == null || beanList.size() == 0) {
                                Toast.makeText(MyExamActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                                mIsHasData = false;
                                return;
                            }
                            examBeanList.addAll(beanList);
                            examAdapter.setList(examBeanList);
                        }
                        return;
                    }
                }
                emptyLayout(false);

            }

            @Override
            public void onFailure(Call<ExamBean> call, Throwable t) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                emptyLayout(false);
            }
        });
    }

    private void isPerfectPersonInfo(int userQuesLibId,int quesLibId) {
        PersonGroupInterfac personGroupInterfac = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        personGroupInterfac.getPersonExam(token, userQuesLibId).enqueue(new Callback<PersonInfo>() {
            @Override
            public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        data = (boolean) response.body().getData();
                        if(!data) {
                            Intent intent = new Intent(MyExamActivity.this, TestContentActivity1.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("quesLibId", quesLibId);
                            bundle.putInt("userLibId", userQuesLibId);
                            bundle.putInt("examType", 0);
                            intent.putExtras(bundle);
                            MyExamActivity.this.startActivity(intent);
                        }else{
                            showUploadDialog();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonInfo> call, Throwable t) {

            }
        });
    }


    private void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_exam_refresh_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_exam_recycler_view);
        Utils.setRefreshViewColor(mRefreshLayout);
        mLayoutManager = new LinearLayoutManager(MyExamActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        img_back = (ImageView) findViewById(R.id.nb_order_return);
        rl_empty= (RelativeLayout) findViewById(R.id.rl_empty_layout);//空页面
        img_back.setOnClickListener(this);
        tv_msg= (TextView) findViewById(R.id.tv_msg);
//        //初始化adapter
        examAdapter = new ExamAdapter(MyExamActivity.this, examBeanList);
        examAdapter.setOnItemClickListener(this);
        //设置EmptyView
        View emptyView = View.inflate(this, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mRecyclerView.setAdapter(examAdapter);

        mIsHasData = true;
        mRefreshLayout.setRefreshing(false);
        initEvent();
    }

    private void closeSwipeRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nb_order_return:
                this.finish();
                break;
        }
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 1;
            mIsHasData = true;
            mIsLoading = true;
            getExamList();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 没有更多的数据了
                if (!mIsHasData) return;
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == examAdapter.getItemCount() && !mIsLoading) {
                    mLoading.show();
                    currentPage++;
                    mIsLoading = true;
                    getExamList();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        userQuesLibId =examBeanList.get(position).getUserQuesLibId();
        quesLibId =examBeanList.get(position).getQuesLibId();
        isPerfectPersonInfo(userQuesLibId,quesLibId);
    }

    private AlertDialog upLoadAlertDialog;

    private void showUploadDialog() {

        if (upLoadAlertDialog != null) {
            upLoadAlertDialog.dismiss();
        }
        upLoadAlertDialog = new AlertDialog.Builder(MyExamActivity.this)
                .setTitle("提示")
                .setMessage("请先完善个人信息，再进行考试")
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyExamActivity.this,EditMessageActivity.class));
                        dialog.dismiss();
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        upLoadAlertDialog.show();
    }
    private void emptyLayout(boolean isVisible){
        if(isVisible){
            mRefreshLayout.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
            tv_msg.setText("暂无考试数据");
        }
    }
}
