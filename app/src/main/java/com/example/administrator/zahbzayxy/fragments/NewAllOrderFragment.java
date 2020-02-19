package com.example.administrator.zahbzayxy.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.administrator.zahbzayxy.adapters.NBMyAllOrderAdapter;
import com.example.administrator.zahbzayxy.beans.NBMyAllOrderBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.interfaceserver.NewMyOrderInterface;
import com.example.administrator.zahbzayxy.utils.IsLogin;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
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
public class NewAllOrderFragment extends Fragment{
    private PullToRefreshListView nbMyAllOrder_lv;
    private View view;
    NBMyAllOrderAdapter adapter;
    private String token;
    Context context;
    RelativeLayout rl_empty;
    LinearLayout ll_list;
    private List<NBMyAllOrderBean.DataEntity.RowsEntity> totalList = new ArrayList<>();
    private ProgressBarLayout mLoadingBar;
    private int pager = 1;
    TextView tv_msg;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public NewAllOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_order, container, false);
        initView();
        initPullToRefreshListView();
        Log.e("codecode", "000000000");
        return view;
    }

    private void initPullToRefreshListView() {
        totalList.clear();
        adapter = new NBMyAllOrderAdapter(totalList, context, token);
        nbMyAllOrder_lv.setAdapter(adapter);
        nbMyAllOrder_lv.setMode(PullToRefreshBase.Mode.BOTH);
        initDownloadData(pager);
        nbMyAllOrder_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                totalList.clear();
                pager = 1;
                initDownloadData(pager);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager++;
                initDownloadData(pager);

            }
        });

    }

    private void initDownloadData(int pager) {
        showLoadingBar(false);
        NewMyOrderInterface aClass = RetrofitUtils.getInstance().createClass(NewMyOrderInterface.class);
        aClass.getAllOrderData(pager, 10, token).enqueue(new Callback<NBMyAllOrderBean>() {
            @Override
            public void onResponse(Call<NBMyAllOrderBean> call, Response<NBMyAllOrderBean> response) {
                String s = new Gson().toJson(response.body());
                Log.e("allOrderFragment", s);

                NBMyAllOrderBean body = response.body();
                hideLoadingBar();
                Log.e("adapter1", "1111111111");
                if (body != null) {
                    String code = body.getCode();
                    Log.e("codecode", code);
                    if (code.equals("00003")) {
                        Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("isLogin", false);
                        edit.commit();
                    } else if (IsLogin.dbIsLogin(context) == false) {
                        Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("00000")) {
                        NBMyAllOrderBean.DataEntity data = body.getData();
                        if (data != null) {
                            List<NBMyAllOrderBean.DataEntity.RowsEntity> rows = data.getRows();
                            // totalList.clear();
                            totalList.addAll(rows);
                            adapter.notifyDataSetChanged();

                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        String errMsg = body.getErrMsg();
                        Toast.makeText(context, "" + errMsg, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<NBMyAllOrderBean> call, Throwable t) {

            }
        });
        if (nbMyAllOrder_lv.isRefreshing()) {
            nbMyAllOrder_lv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    nbMyAllOrder_lv.onRefreshComplete();

                }
            }, 1000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (totalList.size() > 0) {
            totalList.clear();
            initDownloadData(pager);
        }
    }

    private void initView() {
        nbMyAllOrder_lv = view.findViewById(R.id.nbMyAllOrder_lv);
        mLoadingBar = view.findViewById(R.id.nb_allOrder_load_bar_layout);
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        ll_list = view.findViewById(R.id.ll_list);
        tv_msg = view.findViewById(R.id.tv_msg);
        token = tokenDb.getString("token", "");
        Log.e("setUserNameToken", token);
    }

    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

}
