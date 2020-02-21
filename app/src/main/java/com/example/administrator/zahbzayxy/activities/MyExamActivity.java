package com.example.administrator.zahbzayxy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
public class MyExamActivity extends BaseActivity implements View.OnClickListener, PullToRefreshListener, ExamAdapter.OnItemClickListener {
    private ImageView img_back;
    private PullToRefreshRecyclerView recyclerView;
    private ExamAdapter examAdapter;
    private List<ExamBean.QuesLibsBean> examBeanList = new ArrayList<>();
    private ProgressBarLayout mLoadingBar;
    private String token;
    private int currentPage = 1;
    private int pageSize = 10;
    int userQuesLibId;
    boolean data;
    private RelativeLayout rl_empty;
    private TextView tv_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exam);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getExamList();
    }

    private void getExamList() {
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getExamList(token, currentPage, pageSize).enqueue(new Callback<ExamBean>() {
            @Override
            public void onResponse(Call<ExamBean> call, Response<ExamBean> response) {
                if (response != null && response.body() != null && response.body().getData() != null && response.body().getData().getQuesLibs() != null) {
                    if (currentPage == 1 && response.body().getData().getQuesLibs().size() == 0) {
                        emptyLayout(false);
                    } else {
                        emptyLayout(true);
                    }
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        emptyLayout(true);
                        examBeanList = response.body().getData().getQuesLibs();
                        for (int i = 0; i < examBeanList.size(); i++) {
                            //我的考试需要用上
                            userQuesLibId=examBeanList.get(i).getUserQuesLibId();
                            isPerfectPersonInfo();
                        }
                        if (currentPage == 1) {
                            examAdapter.setList(examBeanList);
                        } else {
                            examAdapter.addList(examBeanList);
                        }
                    }
                }else{
                    emptyLayout(false);
                }
            }

            @Override
            public void onFailure(Call<ExamBean> call, Throwable t) {
                emptyLayout(false);
            }
        });
    }

    private void isPerfectPersonInfo() {
        PersonGroupInterfac personGroupInterfac = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        personGroupInterfac.getPersonExam(token, userQuesLibId).enqueue(new Callback<PersonInfo>() {
            @Override
            public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        data = (boolean) response.body().getData();
                        Log.i("dfdfd", "zahb======data===" + data);
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonInfo> call, Throwable t) {

            }
        });
    }


    private void initView() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        img_back = (ImageView) findViewById(R.id.nb_order_return);
        rl_empty= (RelativeLayout) findViewById(R.id.rl_empty_layout);//空页面
        mLoadingBar = (ProgressBarLayout) findViewById(R.id.nb_allOrder_load_bar_layout);
        img_back.setOnClickListener(this);
        recyclerView = (PullToRefreshRecyclerView) findViewById(R.id.recycle);
        tv_msg= (TextView) findViewById(R.id.tv_msg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        //初始化adapter
        examAdapter = new ExamAdapter(MyExamActivity.this, examBeanList);
        examAdapter.setOnItemClickListener(this);
//        //添加数据源
        recyclerView.setAdapter(examAdapter);
        recyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        recyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        recyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(MyExamActivity.this);
        //主动触发下拉刷新操作
        recyclerView.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(this, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setEmptyView(emptyView);
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
                getExamList();
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
                if (examBeanList.size() < pageSize) {
                    Toast.makeText(MyExamActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                    recyclerView.setLoadingMoreEnabled(false);
                    return;
                }
                currentPage++;
                getExamList();
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

    @Override
    public void onItemClick(View view, int position) {
        if(!data) {
            Intent intent = new Intent(MyExamActivity.this, TestContentActivity1.class);
            Bundle bundle = new Bundle();
            bundle.putInt("quesLibId", examBeanList.get(position).getQuesLibId());
            bundle.putInt("userLibId", examBeanList.get(position).getUserQuesLibId());
            bundle.putInt("examType", 0);
            intent.putExtras(bundle);
            MyExamActivity.this.startActivity(intent);
        }else{
            showUploadDialog();
        }
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
            recyclerView.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            tv_msg.setText("暂无考试数据");
        }
    }
}
