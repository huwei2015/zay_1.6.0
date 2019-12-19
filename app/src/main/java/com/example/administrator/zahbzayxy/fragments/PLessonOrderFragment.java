package com.example.administrator.zahbzayxy.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.PMyLessonOrderAdapter;
import com.example.administrator.zahbzayxy.beans.PMyLessonOrderBean;
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
public class PLessonOrderFragment extends Fragment {
    private Context context;
    PullToRefreshListView myLessonDingDan_plv;
    private  View view;
    private List<PMyLessonOrderBean.DataBean> totalList=new ArrayList<>();
    static String token;
    private PMyLessonOrderAdapter adapter;
    private int pager=1;
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    public PLessonOrderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_buy_lesson, container, false);
       initView();
       getSP();

       initPulltoRefresh();
        return view;
    }
    private void initPulltoRefresh() {
        adapter=new PMyLessonOrderAdapter(totalList,context,token);
        myLessonDingDan_plv.setAdapter(adapter);
        myLessonDingDan_plv.setMode(PullToRefreshBase.Mode.BOTH);
        initDownLoadData(pager);
        myLessonDingDan_plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                totalList.clear();
                pager=1;
                initDownLoadData(pager);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager++;
                initDownLoadData(pager);

            }
        });

    }
    private void initDownLoadData(int pager) {

        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyLessonOrderData(pager,10,token,5).enqueue(new Callback<PMyLessonOrderBean>() {
            @Override
            public void onResponse(Call<PMyLessonOrderBean> call, Response<PMyLessonOrderBean> response) {
                PMyLessonOrderBean body = response.body();
                String s = new Gson().toJson(body.toString());
                Log.e("myLessonDingdanaaa",s);
                if(body.getCode().equals("00003")){
                    Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = context.getSharedPreferences("tokenDb",context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("isLogin",false);
                    edit.commit();

                }else if (dbIsLogin()==false){
                    Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                }
               else if (response!=null&&body!=null&&body.getErrMsg()==null){
                    List<PMyLessonOrderBean.DataBean> data = body.getData();
                    if (!TextUtils.isEmpty(data.toString())){
                     //   totalList.clear();
                        totalList.addAll(data);
                        adapter.notifyDataSetChanged();
                    }

                }
            }
            @Override
            public void onFailure(Call<PMyLessonOrderBean> call, Throwable t) {
            }
        });
        if (myLessonDingDan_plv.isRefreshing()){
            myLessonDingDan_plv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myLessonDingDan_plv.onRefreshComplete();
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
        myLessonDingDan_plv= (PullToRefreshListView) view.findViewById(R.id.myLessonDingDan_lv);
    }
    public void getSP() {
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_APPEND);
        token = tokenDb.getString("token","");
    }

    @Override
    public void onResume() {
        super.onResume();
       // initDownLoadData(pager);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
