package com.example.administrator.zahbzayxy.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.PMyRenZhengMuLuAdapter;
import com.example.administrator.zahbzayxy.beans.PMyRenZhengMuLuBean;
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

//我的证书
public class MyRenZhengActivity extends BaseActivity {
    ImageView back_iv;
    PullToRefreshListView pMyRenZheng_plv;
    List<PMyRenZhengMuLuBean.DataBean.CerListBean>totalList=new ArrayList<>();
    PMyRenZhengMuLuAdapter adapter;
    private RelativeLayout rl_empty;
    private String token;
    private TextView tv_msg;
    private ProgressBarLayout mLoadingBar;
    int pager=1;
    int pageSize =10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ren_zheng);
        initView();
        initPullToRefreshLv();
    }

    private void initPullToRefreshLv() {
//        pMyRenZheng_plv.setMode(PullToRefreshBase.Mode.BOTH);
        downLoadData(pager);
       pMyRenZheng_plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        showLoadingBar(false);
        adapter=new PMyRenZhengMuLuAdapter(totalList,this);
        pMyRenZheng_plv.setAdapter(adapter);
        SharedPreferences sharedPreferences =getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyRenZhengColumData(pager,pageSize,token).enqueue(new Callback<PMyRenZhengMuLuBean>() {
            @Override
            public void onResponse(Call<PMyRenZhengMuLuBean> call, Response<PMyRenZhengMuLuBean> response) {
                if (response != null) {
                    hideLoadingBar();
                    PMyRenZhengMuLuBean body = response.body();
                    if (body != null && body.getData() != null) {
                        String code = body.getCode();
                        if (code.equals("00003")) {
                            initVisible(false);
                            Toast.makeText(MyRenZhengActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();

                        } else if (dbIsLogin() == false) {
                            initVisible(false);
                            Toast.makeText(MyRenZhengActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            initVisible(true);
                            List<PMyRenZhengMuLuBean.DataBean.CerListBean> cerList = response.body().getData().getCerList();
                            if (pager == 1) {
                                if (cerList == null || cerList.size() == 0) {
                                    initVisible(false);
                                    return;
                                }
                            }
                            if (cerList != null) {
                                totalList.addAll(cerList);
                                adapter.notifyDataSetChanged();
                            }
                            return;
                        } else if (code.equals("99999")) {
                            initVisible(false);
                            Toast.makeText(MyRenZhengActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                initVisible(false);
            }

            @Override
            public void onFailure(Call<PMyRenZhengMuLuBean> call, Throwable t) {
                hideLoadingBar();
                initVisible(false);
                Toast.makeText(MyRenZhengActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        if (pMyRenZheng_plv.isRefreshing()){
            pMyRenZheng_plv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pMyRenZheng_plv.onRefreshComplete();
                }
            },1000);
        }
    }

    private boolean dbIsLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }
    }

    private void initView() {
        back_iv= (ImageView) findViewById(R.id.pMyRenZhengBack_iv);
        pMyRenZheng_plv= (PullToRefreshListView) findViewById(R.id.pMyRenZhengMuLu_plv);
        rl_empty= (RelativeLayout) findViewById(R.id.rl_empty_layout);
        tv_msg= (TextView) findViewById(R.id.tv_msg);
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.progressBar);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }


    private void initVisible(boolean isVisible){
        if(isVisible){
            pMyRenZheng_plv.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            pMyRenZheng_plv.setVisibility(View.GONE);
            tv_msg.setText("暂无证书信息");
        }
    }
}
