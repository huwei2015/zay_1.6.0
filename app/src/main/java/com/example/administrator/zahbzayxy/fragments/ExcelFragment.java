package com.example.administrator.zahbzayxy.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.AllFileAdapter;
import com.example.administrator.zahbzayxy.beans.AllFileBean;
import com.example.administrator.zahbzayxy.beans.FileDelBean;
import com.example.administrator.zahbzayxy.interfaceserver.AllFileInterface;
import com.example.administrator.zahbzayxy.manager.ShowFileManager;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.example.administrator.zahbzayxy.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-24.
 * Time 14:22.
 */
public class ExcelFragment extends Fragment {
    private View view;
    private String token;
    Context mContext;
    ProgressBarLayout mLoadingBar;
    RelativeLayout rl_empty;
    TextView tv_msg;
    AllFileAdapter allFileAdapter;
    LinearLayout ll_list;
    List<AllFileBean.AllFileListBean> allFileListBeanList = new ArrayList<>();
    String del_id;
    private ShowFileManager mShowFile;
    private int currentPage = 1;
    private int pageSize = 10;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsHasData = true;
    private boolean mIsLoading;
    private LoadingDialog mLoading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_excel,container,false);
        mLoading = new LoadingDialog(mContext);
        mLoading.setShowText("加载中...");
        mShowFile = new ShowFileManager((Activity) mContext);
        initView();
        mRefreshLayout.setRefreshing(true);
        initEvent();
        initPullToRefreshListView();
        return view;
    }
    private void initView() {
        mRefreshLayout = view.findViewById(R.id.excel_file_refresh_layout);
        mRecyclerView = view.findViewById(R.id.excel_file_recycler_view);
        Utils.setRefreshViewColor(mRefreshLayout);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mLoadingBar = view.findViewById(R.id.nb_allOrder_load_bar_layout);
        mShowFile.setLoadingView(mLoadingBar);
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        tv_msg = view.findViewById(R.id.tv_msg);
        ll_list = view.findViewById(R.id.ll_list);
        token = tokenDb.getString("token", "");

//        //初始化adapter
        allFileAdapter = new AllFileAdapter(mContext, allFileListBeanList);
        //设置EmptyView
        View emptyView = View.inflate(mContext, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mRecyclerView.setAdapter(allFileAdapter);
    }
    private void initPullToRefreshListView() {
        AllFileInterface allFileInterface = RetrofitUtils.getInstance().createClass(AllFileInterface.class);
        allFileInterface.getAllFileData(currentPage,pageSize,3,token).enqueue(new Callback<AllFileBean>() {
            @Override
            public void onResponse(Call<AllFileBean> call, Response<AllFileBean> response) {
                closeSwipeRefresh();
                mLoading.dismiss();
                mIsLoading = false;
                if(response !=null && response.body() !=null){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        if (currentPage == 1 && response.body().getData().getData().size() == 0) {
                            isVisible(false);
                        } else {
                            isVisible(true);
                        }
                        List<AllFileBean.AllFileListBean> data = response.body().getData().getData();
                        if(currentPage == 1) {
                            allFileListBeanList.clear();
                            allFileListBeanList.addAll(data);
                            allFileAdapter.setList(allFileListBeanList);
                            if (allFileListBeanList.size() < pageSize) {
                                mIsHasData = false;
                            }
                        } else {
                            if (data == null || data.size() == 0) {
                                mIsHasData = false;
                            }
                            allFileListBeanList.addAll(data);
                            allFileAdapter.setList(allFileListBeanList);

                        }
                    } else {
                        ToastUtils.showShortInfo(response.body().getErrMsg());
                    }
                } else {
                    if (currentPage == 1){
                        isVisible(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<AllFileBean> call, Throwable t) {
                currentPage--;
                ToastUtils.showShortInfo("网络异常");
                mLoading.dismiss();
            }
        });
    }

    private void initEvent() {
        allFileAdapter.setOnDelClickListener((View view, int position) -> {
            del_id= allFileListBeanList.get(position).getId();
            Log.i("zahb","zahb================"+del_id);
            //删除文件接口
            getDelData();
        });

        allFileAdapter.setOnItemCilkLiener((View view, int position) -> {
            mShowFile.setFileType(ShowFileManager.SHOW_FILE_EXCEL);
            String filePath = allFileListBeanList.get(position).getAttaPath();
            mShowFile.openFile(allFileListBeanList.get(position).getAttaName(), filePath);
        });

        mRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 1;
            mIsHasData = true;
            initPullToRefreshListView();
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 没有更多的数据了
                if (!mIsHasData) return;
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == allFileAdapter.getItemCount() && !mIsLoading) {
                    mLoading.show();
                    currentPage++;
                    initPullToRefreshListView();
                    mIsLoading = true;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void closeSwipeRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    public void getDelData(){
        final AlertDialog.Builder builder= new AlertDialog.Builder(mContext);
        builder.setTitle("确定删除吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDelete();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private void toDelete() {
        final AllFileInterface allFileInterface = RetrofitUtils.getInstance().createClass(AllFileInterface.class);
        allFileInterface.getDelData(del_id,token).enqueue(new Callback<FileDelBean>() {
            @Override
            public void onResponse(Call<FileDelBean> call, Response<FileDelBean> response) {
                if(response !=null && response.body() !=null){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        allFileAdapter.notifyDataSetChanged();
                        initPullToRefreshListView();
                    } else {
                        ToastUtils.showLongInfo("文件删除失败");
                    }
                }

            }

            @Override
            public void onFailure(Call<FileDelBean> call, Throwable t) {
                ToastUtils.showLongInfo("文件删除失败");
            }
        });
    }

    private void isVisible(boolean flag) {
        if (flag) {
            ll_list.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        } else {
            rl_empty.setVisibility(View.VISIBLE);
            ll_list.setVisibility(View.GONE);
            tv_msg.setText("暂无excel文档");
        }
    }
}
