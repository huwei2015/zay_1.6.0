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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.AllMyYouHuiJuanAdapter;
import com.example.administrator.zahbzayxy.beans.YouHuiJuanBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoHaveUseFragment extends Fragment {
    @BindView(R.id.noHavaUse_lv)
    ListView noHaveUse_lv;
    Context context;
    View view;
    RelativeLayout rl_empty;
    LinearLayout ll_list;
    List<YouHuiJuanBean.DataEntity>totalList=new ArrayList<>();
    AllMyYouHuiJuanAdapter adapter;
    private String token;
    private Unbinder mUnbinder;
    TextView tv_msg;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_no_have_use, container, false);
        rl_empty=view.findViewById(R.id.rl_empty_layout);
        tv_msg=view.findViewById(R.id.tv_msg);
        ll_list= view.findViewById(R.id.ll_list);
        mUnbinder=ButterKnife.bind(this,view);

        getSP();
        initDownLoadData();

        return view;
    }

    private void initDownLoadData() {
        adapter=new AllMyYouHuiJuanAdapter(totalList,context);
        noHaveUse_lv.setAdapter(adapter);
        //Toast.makeText(context, "aaaaa", Toast.LENGTH_SHORT).show();
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getMyYouHuiJuanData(0,token).enqueue(new Callback<YouHuiJuanBean>() {
            @Override
            public void onResponse(Call<YouHuiJuanBean> call, Response<YouHuiJuanBean> response) {
                int code = response.code();
                if (code==200){
                    YouHuiJuanBean body = response.body();
                    if (body!=null){
                        String code1 = body.getCode();
                        if (code1.equals("00000") && body.getData().size() > 0){
                            isVisible(true);
                            List<YouHuiJuanBean.DataEntity> data = body.getData();
                            totalList.clear();
                            totalList.addAll(data);
                            adapter.notifyDataSetChanged();

                        }else if (code1.equals("00003")){
                            isVisible(false);
                            Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin",false);
                            edit.commit();
                        }
                        else {
                            String errMsg = body.getErrMsg();
                            isVisible(false);
                            if (!TextUtils.isEmpty(errMsg)) {
                                Toast.makeText(context, "" + errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<YouHuiJuanBean> call, Throwable t) {

            }
        });
    }
    public void getSP() {
        SharedPreferences tokenDb =context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
        token = tokenDb.getString("token","");
        Log.e("setUserNameToken",token);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder!=null){
            mUnbinder.unbind();

        }
    }
    private void isVisible(boolean flag){
        if(flag){
            ll_list.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            ll_list.setVisibility(View.GONE);
            tv_msg.setText("暂无未使用的优惠券");
        }
    }

}
