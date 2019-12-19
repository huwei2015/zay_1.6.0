package com.example.administrator.zahbzayxy.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.PMyRenZhengMuLuAdapter;
import com.example.administrator.zahbzayxy.beans.PMyRenZhengMuLuBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//我的认证
public class MyRenZhengActivity extends BaseActivity {
    ImageView back_iv;
    PullToRefreshListView pMyRenZheng_plv;
    List<PMyRenZhengMuLuBean.DataBean.CerListBean>totalList=new ArrayList<>();
    PMyRenZhengMuLuAdapter adapter;
    private RelativeLayout rl_empty;
    private String token;
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
        adapter=new PMyRenZhengMuLuAdapter(totalList,this);
        pMyRenZheng_plv.setAdapter(adapter);
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyRenZhengColumData(pager,pageSize,token).enqueue(new Callback<PMyRenZhengMuLuBean>() {
            @Override
            public void onResponse(Call<PMyRenZhengMuLuBean> call, Response<PMyRenZhengMuLuBean> response) {
                if (response != null) {
                    PMyRenZhengMuLuBean body = response.body();
                    if (body != null && body.getData().getCerList().size() > 0) {
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
                        } else if (response.body().getErrMsg() == null) {
                            initVisible(true);
                            List<PMyRenZhengMuLuBean.DataBean.CerListBean> cerList = response.body().getData().getCerList();
                            totalList.addAll(cerList);
                            adapter.notifyDataSetChanged();
                        } else if (code.equals("99999")) {
                            initVisible(false);
                            Toast.makeText(MyRenZhengActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        initVisible(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<PMyRenZhengMuLuBean> call, Throwable t) {
                initVisible(false);
                Toast.makeText(MyRenZhengActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
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
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initVisible(boolean isVisible){
        if(isVisible){
            pMyRenZheng_plv.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            pMyRenZheng_plv.setVisibility(View.GONE);
        }
    }
}
