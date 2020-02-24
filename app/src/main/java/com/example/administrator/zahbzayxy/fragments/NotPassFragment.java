package com.example.administrator.zahbzayxy.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.activities.TestContentActivity1;
import com.example.administrator.zahbzayxy.adapters.NotPassAdapter;
import com.example.administrator.zahbzayxy.beans.NotPassBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.example.administrator.zahbzayxy.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 13:33.
 * 考试正式未通过
 */
public class NotPassFragment extends Fragment implements NotPassAdapter.onItemClickListener {
    private View view;
    private NotPassAdapter notPassAdapter;
    private List<NotPassBean.NotListData> notPassListBeans = new ArrayList<>();
    private int currentPage = 1;
    private int PageSize = 10;
    private String token;
    private Context mContext;
    private RelativeLayout rl_empty;
    TextView tv_msg;
    LinearLayout ll_list;
    private boolean isVisible;
    private boolean mLoadView = false;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsHasData = true;
    private boolean mIsLoading;
    private LoadingDialog mLoading;
    int user_id;
    boolean data;
    int quesLibId;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_not_pass, container, false);
        mLoading = new LoadingDialog(mContext);
        mLoading.setShowText("加载中...");
        initView();
        mLoadView = true;
        mIsHasData = true;
        mRefreshLayout.setRefreshing(true);
        initEvent();
        initData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        if (isVisibleToUser && mLoadView) {
            initView();
            mLoadView = true;
            currentPage = 1;
            mIsHasData = true;
            notPassListBeans.clear();
            notPassAdapter.setList(notPassListBeans);
            mRefreshLayout.setRefreshing(true);
            initData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initData() {
        if (!isVisible) return;
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        userInfoInterface.getExamData(currentPage, PageSize, 0, token).enqueue(new Callback<NotPassBean>() {
            @Override
            public void onResponse(Call<NotPassBean> call, Response<NotPassBean> response) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                if (response != null && response.body() != null && response.body().getData() != null) {
                    NotPassBean.NotPassListBean data1 = response.body().getData();
                    List<NotPassBean.NotListData> listData = null;
                    if (data1 != null) {
                        NotPassBean.NotDataBean notDataBean = data1.getqLibs();
                        if (notDataBean != null) {
                            listData = notDataBean.getData();
                        }
                    }
                    if (currentPage == 1 && listData != null && listData.size() == 0) {
                        emptyLayout(false);
                    } else {
                        emptyLayout(true);
                    }
                    if (listData == null) {
                        return;
                    }
                    String code = response.body().getCode();
                    List<NotPassBean.NotListData> data = response.body().getData().getqLibs().getData();
                    if (code.equals("00000")) {
                        emptyLayout(true);
                        if (currentPage == 1) {
                            if (data == null || data.size() == 0) {
                                emptyLayout(false);
                                return;
                            }
                            notPassListBeans = data;
                        } else {
                            if (data == null || data.size() < PageSize) {
                                mIsHasData = false;
                            }
                            notPassListBeans.addAll(data);
                        }
                        notPassAdapter.setList(notPassListBeans);
                        return;
                    }
                }
                emptyLayout(false);
            }

            @Override
            public void onFailure(Call<NotPassBean> call, Throwable t) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                emptyLayout(false);
            }
        });
    }

    private void initView() {
        mRefreshLayout = view.findViewById(R.id.no_pass_refresh_layout);
        mRecyclerView = view.findViewById(R.id.no_pass_recycler_view);
        Utils.setRefreshViewColor(mRefreshLayout);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        rl_empty = view.findViewById(R.id.rl_empty_layout);//空页面
        ll_list = view.findViewById(R.id.ll_list);
        tv_msg = view.findViewById(R.id.tv_msg);
//        //初始化adapter
        notPassAdapter = new NotPassAdapter(getActivity(), notPassListBeans);
        notPassAdapter.setOnItemClickListener(this);
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mRecyclerView.setAdapter(notPassAdapter);
    }

    private void closeSwipeRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 1;
            mIsHasData = true;
            mIsLoading = true;
            initData();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 没有更多的数据了
                if (!mIsHasData) return;
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == notPassAdapter.getItemCount() && !mIsLoading) {
                    mLoading.show();
                    currentPage++;
                    mIsLoading = true;
                    initData();
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void emptyLayout(boolean isVisible) {
        if (isVisible) {
            mRefreshLayout.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        } else {
            rl_empty.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
            tv_msg.setText("暂无未通过数据");
        }
    }

    @Override
    public void onItemclick(int position) {
        user_id = notPassListBeans.get(position).getId();
        quesLibId=notPassListBeans.get(position).getQuesLibId();
        isPerfectPersonInfo(user_id,quesLibId);
    }

    private AlertDialog upLoadAlertDialog;

    private void showUploadDialog() {

        if (upLoadAlertDialog != null) {
            upLoadAlertDialog.dismiss();
        }
        upLoadAlertDialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("请先完善个人信息，再进行考试")
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mContext.startActivity(new Intent(mContext, EditMessageActivity.class));
                        dialog.dismiss();
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        upLoadAlertDialog.show();
    }

    private void isPerfectPersonInfo(int user_id,int queslib_id) {
        PersonGroupInterfac personGroupInterfac = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        personGroupInterfac.getPersonExam(token, user_id).enqueue(new Callback<PersonInfo>() {
            @Override
            public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        data = (boolean) response.body().getData();
                        if (!data) {
                            Intent intent = new Intent(mContext, TestContentActivity1.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("quesLibId", queslib_id);
                            bundle.putInt("userLibId",user_id);//正式考试题库id
                            bundle.putInt("examType", 0);
                            intent.putExtras(bundle);
                            mContext.startActivity(intent);
                        } else {
                            showUploadDialog();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<PersonInfo> call, Throwable t) {

            }
        });
    }
}
