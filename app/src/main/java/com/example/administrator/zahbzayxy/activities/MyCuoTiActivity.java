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
import android.widget.TextView;
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

/**
 *我的-我的错题
 */
public class MyCuoTiActivity extends BaseActivity {
   PullToRefreshListView myError_plv;
    String token;
    ImageView back_iv;
    int pager=1;
     private List<PCuoTiJiLuBean.DataBean.ErrorRecordQuesLibsBean>totalList=new ArrayList<>();
    private MyCuoTiJiLuAdapter adapter;
    private RelativeLayout rl_empty;
    private LinearLayout ll_list;
    private TextView tv_msg;
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
                if (response!=null && response.body() !=null) {
                    PCuoTiJiLuBean body = response.body();
                    String errMsg = (String) response.body().getErrMsg();
                    if (body != null) {
                        if (body.getCode().equals("00003")) {
                            isVisible(false);
                            Toast.makeText(MyCuoTiActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            Toast.makeText(MyCuoTiActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (body.getCode().equals("00000")) {// {"code":"00000","errMsg":null,"data":{"errorRecordQuesLibs":[{"errorRecordCount":9,"quesLibName":"烟花爆竹生产单位主要负责人","packageId":1,"packageName":"考试题库","userLibId":3506,"quesLibId":30},{"errorRecordCount":28,"quesLibName":"特种作业人员-复训-煤矿井下电气作业","packageId":1,"packageName":"考试题库","userLibId":3512,"quesLibId":266},{"errorRecordCount":9,"quesLibName":"《安全生产技术》模拟试题三","packageId":13,"packageName":"测试关系sql","userLibId":5269,"quesLibId":320},{"errorRecordCount":4,"quesLibName":"企业主要负责人-初训-烟花爆竹经营单位（无解析版）","packageId":1,"packageName":"考试题库","userLibId":5278,"quesLibId":412},{"errorRecordCount":10,"quesLibName":"企业主要负责人-初训-烟花爆竹经营单位（无解析版）","packageId":2,"packageName":"模考题库","userLibId":5268,"quesLibId":412},{"errorRecordCount":25,"quesLibName":"企业主要负责人-初训-烟花爆竹生产单位","packageId":1,"packageName":"考试题库","userLibId":5280,"quesLibId":413},{"errorRecordCount":12,"quesLibName":"企业主要负责人-初训-烟花爆竹生产单位","packageId":2,"packageName":"模考题库","userLibId":5271,"quesLibId":413},{"errorRecordCount":21,"quesLibName":"企业主要负责人-初训-烟花爆竹生产单位","packageId":3,"packageName":"独家解析题库","userLibId":5426,"quesLibId":413},{"errorRecordCount":7,"quesLibName":"主要负责人-复训-煤炭生产经营单位","packageId":2,"packageName":"模考题库","userLibId":3509,"quesLibId":426},{"errorRecordCount":8,"quesLibName":"测试新增题库","packageId":1,"packageName":"考试题库","userLibId":5279,"quesLibId":660}],"totalPage":1,"isLastPage":true,"pageSize":10,"currentPage":1,"totalRecord":10,"isFirstPage":true}}
                            List<PCuoTiJiLuBean.DataBean.ErrorRecordQuesLibsBean> errorRecordQuesLibs = body.getData().getErrorRecordQuesLibs();
                            isVisible(true);
                            // totalList.clear();
                            totalList.addAll(errorRecordQuesLibs);
                            adapter.notifyDataSetChanged();
                        } else if (body.getCode().equals("99999")) {
                            isVisible(false);
                            Toast.makeText(MyCuoTiActivity.this, errMsg, Toast.LENGTH_SHORT).show();
                            return;
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
                Toast.makeText(MyCuoTiActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

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
        tv_msg= (TextView) findViewById(R.id.tv_msg);
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
            tv_msg.setText("暂无错题数据");
        }
    }
}
