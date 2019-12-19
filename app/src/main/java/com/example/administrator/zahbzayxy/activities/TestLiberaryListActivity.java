package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.TestLiberaryListAdapter;
import com.example.administrator.zahbzayxy.beans.TestSecondListBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestLiberaryListActivity extends BaseActivity {
    private int currentPage=1,pageSize=10;
   private PullToRefreshListView testLiberaryList_lv;
    private TestLiberaryListAdapter adapter;
    private List<TestSecondListBean.DataEntity.QuesLibsEntity>totalList=new ArrayList<>();
    private int pCateId;
    private int subCateId;
    private ImageView finish_iv;
    private TextView testListName_tv;
    private String cateName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_liberary_list);
        initView();
        initPullToRefreshListView();
    }
    private void initView() {
        pCateId = getIntent().getIntExtra("pCateId", 0);
        subCateId = getIntent().getIntExtra("subCateId", 0);
        cateName = getIntent().getStringExtra("cateName");
        finish_iv= (ImageView) findViewById(R.id.testLiberyList_back);
        Log.e("FId",pCateId+"...."+subCateId);
        testLiberaryList_lv= (PullToRefreshListView) findViewById(R.id.testLiberyList_lv);
        testListName_tv= (TextView) findViewById(R.id.testListName_tv);
        adapter=new TestLiberaryListAdapter(TestLiberaryListActivity.this,totalList);
        testLiberaryList_lv.setAdapter(adapter);
        finish_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!TextUtils.isEmpty(cateName)){
            testListName_tv.setText(cateName);
        }
    }
    private void initPullToRefreshListView() {
        testLiberaryList_lv.setMode(PullToRefreshBase.Mode.BOTH);
        testLiberaryList_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                totalList.clear();
                currentPage=1;
                downLoadData(currentPage);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                ++currentPage;
                downLoadData(currentPage);
            }
        });
        downLoadData(currentPage);
    }
    private void downLoadData(int currentPage) {
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        Call<TestSecondListBean> testListData = aClass.getTestListData(pCateId,pageSize,currentPage,subCateId);
        testListData.enqueue(new Callback<TestSecondListBean>() {
            @Override
            public void onResponse(Call<TestSecondListBean> call, Response<TestSecondListBean> response) {
                TestSecondListBean body = response.body();
                //Log.e("jsonsssssssss",s);
                if (response != null && body != null) {
                    String code = body.getCode();
                    if (code.equals("99999")) {
                        Toast.makeText(TestLiberaryListActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("00000")) {
                        List<TestSecondListBean.DataEntity.QuesLibsEntity> quesLibs = body.getData().getQuesLibs();
                        if (quesLibs != null) {
                            totalList.addAll(quesLibs);
                            int size = quesLibs.size();
                            Log.e("aasize sss", String.valueOf(size));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<TestSecondListBean> call, Throwable t) {
                String message = t.getMessage();
                Log.e("meeessagw",message);
            }
        });
        testLiberaryList_lv.postDelayed(new Runnable() {
            @Override
            public void run() {
                testLiberaryList_lv.onRefreshComplete();
            }
        },1000);

    }
}
