package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.PMyTiKuAdapter;
import com.example.administrator.zahbzayxy.beans.PersonTiKuListBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTiKuActivity extends BaseActivity {
   PullToRefreshListView pMyTiKu_lv;
    private ImageView back_iv;
    List<PersonTiKuListBean.DataEntity.QuesLibsEntity>totalList=new ArrayList<>();
    private String token;
    private PMyTiKuAdapter adapter;
    int pager=1;
    private ProgressBarLayout mLoadingBar;
    private boolean isPause=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ti_ku);
        initView();
       // showLoadingBar(false);
        initPullToRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("pause","resume");
        if (isPause){
            downLoadData(pager);
            isPause=false;
            Log.e("pause","resume"+1111);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  isPause=true;
        Log.e("pause","pause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        isPause=true;
        Log.e("pause","stop");
    }

    private void initPullToRefresh() {
        downLoadData(pager);
        pMyTiKu_lv.setMode(PullToRefreshBase.Mode.BOTH);
        pMyTiKu_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                totalList.clear();
                pager=1;
                downLoadData(pager);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager++;
                downLoadData(pager);
            }
        });

    }

    private void downLoadData(int pager) {
        getSP();
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyTiKuData(pager,10,token).enqueue(new Callback<PersonTiKuListBean>() {
            @Override
            public void onResponse(Call<PersonTiKuListBean> call, Response<PersonTiKuListBean> response) {
                PersonTiKuListBean body = response.body();
                hideLoadingBar();
                if (body!=null) {
                    if (body.getCode().equals("00003")) {
                        Toast.makeText(MyTiKuActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("isLogin", false);
                        edit.commit();

                    } else if (dbIsLogin() == false) {
                        Toast.makeText(MyTiKuActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                    } else if (body.getErrMsg() == null) {
                        PersonTiKuListBean.DataEntity data = body.getData();
                        if (data != null) {
                            totalList.clear();
                            List<PersonTiKuListBean.DataEntity.QuesLibsEntity> quesLibs = data.getQuesLibs();
                            totalList.addAll(quesLibs);
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        String errMsg = body.getErrMsg();
                        Toast.makeText(MyTiKuActivity.this, errMsg+"", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonTiKuListBean> call, Throwable t) {

            }
        });
      pMyTiKu_lv.postDelayed(new Runnable() {
          @Override
          public void run() {
              if (pMyTiKu_lv.isRefreshing()){
                  pMyTiKu_lv.onRefreshComplete();
              }
          }
      },1000);
    }
    public Boolean dbIsLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }

    }
    private void initView() {
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.load_bar_layout_evaluating);
        back_iv= (ImageView) findViewById(R.id.pMyTikuBack_iv);
        pMyTiKu_lv= (PullToRefreshListView) findViewById(R.id.pMyTiKu_lv);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter=new PMyTiKuAdapter(totalList,MyTiKuActivity.this);
        pMyTiKu_lv.setAdapter(adapter);
    }

    public void getSP() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        Log.e("setUserNameToken",token);
    }
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

    //添加题库
    public void addTikuOnClick(View view) {
        Intent intent=new Intent(MyTiKuActivity.this, MainActivity.class);
        intent.putExtra("moreTiKu","moreTiKu");
        startActivity(intent);
        finish();
       // EventBus.getDefault().post(6);

    }

    public void shareOnClick(View view) {
        Intent intent=new Intent(MyTiKuActivity.this,ResultActivity.class);
        startActivity(intent);
    }
}
