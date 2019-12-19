package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MyCuoTiJiLuAdapter;
import com.example.administrator.zahbzayxy.beans.PCuoTiJiLuBean;
import com.example.administrator.zahbzayxy.event.ErrorDataEvent;
import com.example.administrator.zahbzayxy.interfacecommit.PracticeInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MyCuoTiActivity extends BaseActivity {
   PullToRefreshListView myError_plv;
    String token;
    ImageView back_iv;
    int pager=1;
     private List<PCuoTiJiLuBean.DataBean.ErrorRecordQuesLibsBean>totalList=new ArrayList<>();
    private MyCuoTiJiLuAdapter adapter;
    private RelativeLayout rl_empty;
    private LinearLayout ll_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cuo_ti);
        //注册eventBus
        EventBus.getDefault().register(this);
        initView();
        //
        adapter=new MyCuoTiJiLuAdapter(this,totalList);
        myError_plv.setAdapter(adapter);

        initPullToRefershLv();
    }

    private void initPullToRefershLv() {
//        myError_plv.setMode(PullToRefreshBase.Mode.BOTH);
        downLoadData(pager);
        myError_plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        PracticeInterface aClass = RetrofitUtils.getInstance().createClass(PracticeInterface.class);
        aClass.lookErrorData(pager,10,token).enqueue(new Callback<PCuoTiJiLuBean>() {
            @Override
            public void onResponse(Call<PCuoTiJiLuBean> call, Response<PCuoTiJiLuBean> response) {
                if (response!=null) {
                    PCuoTiJiLuBean body = response.body();
                    if (body != null && body.getData().getErrorRecordQuesLibs().size() > 0) {
                        if (body.getCode().equals("00003")) {
                            isVisible(false);
                            Toast.makeText(MyCuoTiActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            Toast.makeText(MyCuoTiActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (body.getErrMsg() == null) {
                            List<PCuoTiJiLuBean.DataBean.ErrorRecordQuesLibsBean> errorRecordQuesLibs = body.getData().getErrorRecordQuesLibs();
                            isVisible(true);
                            // totalList.clear();
                            totalList.addAll(errorRecordQuesLibs);
                            adapter.notifyDataSetChanged();
                        } else if (body.getCode().equals("99999")) {
                            isVisible(false);
                            Toast.makeText(MyCuoTiActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        Log.i("zahb=========","接口请求成功，没有返回数据");
                        isVisible(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<PCuoTiJiLuBean> call, Throwable t) {
                isVisible(false);
//                Toast.makeText(MyCuoTiActivity.this,"网络问题",Toast.LENGTH_SHORT).show();

            }
        });
        if (myError_plv.isRefreshing()){
            myError_plv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myError_plv.onRefreshComplete();
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
        myError_plv= (PullToRefreshListView) findViewById(R.id.pMyError_plv);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token","");
        Log.e("danWeiToken",token);
        back_iv= (ImageView) findViewById(R.id.myCuoTiBack_iv);
        rl_empty= (RelativeLayout) findViewById(R.id.rl_empty_layout);
        ll_list= (LinearLayout) findViewById(R.id.ll_list);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
    //把错题个数传回来，没用到
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventErrorData(ErrorDataEvent data){
        if (!TextUtils.isEmpty(String.valueOf(data.getMsg()))) {
           totalList.clear();
            downLoadData(1);
        }
        }

    @Override
    protected void onResume() {//在此方法中可以在删除错题个数以后刚返回此界面就可以更新删除后的错题个数
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus

    }
    private void isVisible(boolean flag){
        if(flag){
            ll_list.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            ll_list.setVisibility(View.GONE);
        }
    }
}
