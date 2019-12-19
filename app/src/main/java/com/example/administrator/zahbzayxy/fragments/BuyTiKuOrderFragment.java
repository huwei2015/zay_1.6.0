package com.example.administrator.zahbzayxy.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.PMyTestOrderAdapter;
import com.example.administrator.zahbzayxy.beans.PMyTestOrderBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyTiKuOrderFragment extends Fragment {
    private PullToRefreshListView pMyTestOrder_plv;
    private  View view;
    private Context context;
    private String token;
    private int pager=1;
    private List<PMyTestOrderBean.DataBean.QuesLibOrdersBean>totalList=new ArrayList<>();
    private PMyTestOrderAdapter adapter;
    public BuyTiKuOrderFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_buy_ti_ku, container, false);
        initView();
        getSP();
        adapter=new PMyTestOrderAdapter(totalList,context,token);
        pMyTestOrder_plv.setAdapter(adapter);
        initPullToRefereshLv();
        return view;
    }
    private void initPullToRefereshLv() {
        pMyTestOrder_plv.setMode(PullToRefreshBase.Mode.BOTH);
        downLoadData(pager);
        pMyTestOrder_plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyTestOrderData(pager,10,token).enqueue(new Callback<PMyTestOrderBean>() {
            @Override
            public void onResponse(Call<PMyTestOrderBean> call, Response<PMyTestOrderBean> response) {
               PMyTestOrderBean body = response.body();
                String s = new Gson().toJson(body);
                Log.e("testOrderBody",s);

                if(body.getCode().equals("00003")){
                    Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = context.getSharedPreferences("tokenDb",context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("isLogin",false);
                    edit.commit();
                }else if (dbIsLogin()==false){
                    Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                }

               else if (body!=null&&body.getErrMsg()==null){
                    List<PMyTestOrderBean.DataBean.QuesLibOrdersBean> quesLibOrders = body.getData().getQuesLibOrders();
                    totalList.clear();
                    totalList.addAll(quesLibOrders);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<PMyTestOrderBean> call, Throwable t) {
                Toast.makeText(context,"连接服务器失败",Toast.LENGTH_SHORT).show();

            }
        });
        if(pMyTestOrder_plv.isRefreshing()){
            //刷新获取数据时候，时间太短，就会出现该问题
            pMyTestOrder_plv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pMyTestOrder_plv.onRefreshComplete();
                }
            },1000);
        }
    }

    private boolean dbIsLogin() {
        SharedPreferences sharedPreferences =context.getSharedPreferences("tokenDb",context.MODE_APPEND);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }
    }

    private void initView() {
       pMyTestOrder_plv= (PullToRefreshListView) view.findViewById(R.id.myTiKuDingDan_plv);
    }
    public void getSP() {
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_APPEND);
        token = tokenDb.getString("token","");

    }

    @Override
    public void onResume() {
        super.onResume();
        downLoadData(pager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
