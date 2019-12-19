package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.db.DbDao;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.BaseRecycleAdapter;
import com.example.administrator.zahbzayxy.adapters.SeachRecordAdapter;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

    //搜题宝
public class SearchTestActivity extends BaseActivity {
    Unbinder unbinder;
    @BindView(R.id.btn_serarch)
     Button mbtn_serarch;
    @BindView(R.id.et_search)
    EditText met_search;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private SeachRecordAdapter mAdapter;
    private DbDao mDbDao;
    private int quesLibId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_test);
        unbinder= ButterKnife.bind(this);
        quesLibId = getIntent().getIntExtra("quesLibId", 0);
        Log.e("qusLibId",quesLibId+"");
        initViews();
    }
    @OnClick({R.id.close_search_iv,R.id.tv_deleteAll,R.id.btn_serarch})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.close_search_iv:
                finish();
                break;
            case R.id.tv_deleteAll:
                mDbDao.deleteData();
                mAdapter.updata(mDbDao.queryData(""));
                break;
            case R.id.btn_serarch:
                initSearch();
                break;
        }
    }

    private void initSearch() {
        if (met_search.getText().toString().trim().length() != 0){
           // boolean hasData = mDbDao.hasData(met_search.getText().toString().trim());
           // if (!hasData){
                mDbDao.insertData(met_search.getText().toString().trim());
//            }else {
//                Toast.makeText(SearchTestActivity.this, "该内容已在历史记录中", Toast.LENGTH_SHORT).show();
//            }
            //
            mAdapter.updata(mDbDao.queryData(""));
            gotoSearchListActivity(met_search.getText().toString());
        }else {
            Toast.makeText(SearchTestActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
        }

    }

    private void initViews() {
        mDbDao =new DbDao(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter =new SeachRecordAdapter(mDbDao.queryData(""),this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setmRvItemDeleteOnclickListener(new BaseRecycleAdapter.RvItemDeleteOnclickListener() {
            @Override
            public void RvDeleteItemOnclick(int position) {
                mDbDao.delete(mDbDao.queryData("").get(position));

                mAdapter.updata(mDbDao.queryData(""));
            }
        });

        mAdapter.setmRvItemOnclickListener(new BaseRecycleAdapter.RvItemOnclickListener() {
            @Override
            public void RvItemOnclick(String keyword) {
               // Toast.makeText(SearchTestActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                gotoSearchListActivity(keyword);
            }
        });


    }

    private void gotoSearchListActivity(String keyword) {
        Intent intent=new Intent(SearchTestActivity.this,SearchListActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("quesLibId",quesLibId);
        bundle.putString("keyword",keyword);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder!=null){
            unbinder.unbind();
        }
    }
}
