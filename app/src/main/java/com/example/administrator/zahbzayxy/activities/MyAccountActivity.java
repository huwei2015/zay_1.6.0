package com.example.administrator.zahbzayxy.activities;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MyAccountFlowAdater;
import com.example.administrator.zahbzayxy.beans.MyAccountFlowBean;
import com.example.administrator.zahbzayxy.beans.MyAmountBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountActivity extends BaseActivity {
    PullToRefreshListView money_plv;
    private String token;
    TextView myAccount_tv;
    int pager;
    private List<MyAccountFlowBean.DataBean.AccountFlowsBean>totalList=new ArrayList<>();
    private MyAccountFlowAdater adater;
    ImageView back_iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        initView();
        initSP();
        initAccount();//获得余额
        initPullToRefreshListView();
    }

    private void initAccount() {
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        aClass.getMyAmmountData(token).enqueue(new Callback<MyAmountBean>() {
            @Override
            public void onResponse(Call<MyAmountBean> call, Response<MyAmountBean> response) {
                MyAmountBean body = response.body();
                if (body!=null){
                    String code = body.getCode();
                    if(code.equals("00003")){
                        Toast.makeText(MyAccountActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("isLogin",false);
                        edit.commit();
                    }else if (dbIsLogin()==false){
                        Toast.makeText(MyAccountActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();

                    }else if (code.equals("00000")){
                        MyAmountBean.DataBean data = body.getData();
                        if (data!=null){
                            String amount = data.getAmount();
                            if (!TextUtils.isEmpty(amount)){
                                myAccount_tv.setText("余额:"+amount);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyAmountBean> call, Throwable t) {

            }
        });
    }

    private boolean dbIsLogin() {
        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_APPEND);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }
    }

    private void initSP() {
        @SuppressLint("WrongConstant") SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_APPEND);
        token = tokenDb.getString("token","");
    }

    private void initPullToRefreshListView() {
        money_plv.setMode(PullToRefreshBase.Mode.BOTH);
//        money_plv.setMode(PullToRefreshBase.Mode.PULL_FROM_START); //禁止加载
        money_plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                totalList.clear();
                pager=1;
                accountFlowData(pager);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager++;
                accountFlowData(pager);
            }
        });
        accountFlowData(pager);
    }
    private void accountFlowData(int pager) {
        adater=new MyAccountFlowAdater(totalList,MyAccountActivity.this);
        money_plv.setAdapter(adater);
        UserInfoInterface aClass = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        aClass.MyAccountFlowData(pager,10,token).enqueue(new Callback<MyAccountFlowBean>() {
            @Override
            public void onResponse(Call<MyAccountFlowBean> call, Response<MyAccountFlowBean> response) {
                MyAccountFlowBean body = response.body();
                if (body!=null){
                    String code = body.getCode();
                    if(code.equals("00003")){
                        Toast.makeText(MyAccountActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("isLogin",false);
                        edit.commit();
                    }else if (dbIsLogin()==false){
                        Toast.makeText(MyAccountActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                    }else if (code.equals("00000")){
                        MyAccountFlowBean.DataBean data = body.getData();
                        List<MyAccountFlowBean.DataBean.AccountFlowsBean> accountFlows = data.getAccountFlows();
                        totalList.addAll(accountFlows);
                        adater.notifyDataSetChanged();
                    }
                }else{
//                    isVisible(false);
                }
            }
            @Override
            public void onFailure(Call<MyAccountFlowBean> call, Throwable t) {
//                isVisible(false);
            }
        });
        if (money_plv.isRefreshing()){
            money_plv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    money_plv.onRefreshComplete();

                }
            },1000);

        }
    }


    private void initView() {
        money_plv= (PullToRefreshListView) findViewById(R.id.myMoney_plv);
        myAccount_tv= (TextView) findViewById(R.id.myAccount_tv);
        back_iv= (ImageView) findViewById(R.id.myAccount_iv);
//        rl_empty= (RelativeLayout) findViewById(R.id.rl_empty_layout);
//        ll_list= (LinearLayout) findViewById(R.id.ll_list);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
//    private void isVisible(boolean flag){
//        if(flag){
//            ll_list.setVisibility(View.VISIBLE);
//            rl_empty.setVisibility(View.GONE);
//        }else{
//            rl_empty.setVisibility(View.VISIBLE);
//            ll_list.setVisibility(View.GONE);
//        }
//    }
}