package com.example.administrator.zahbzayxy.manager;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.OffLineCourseLearnAdapter;
import com.example.administrator.zahbzayxy.ccvideo.ConfigUtil;
import com.example.administrator.zahbzayxy.ccvideo.DownloadController;
import com.example.administrator.zahbzayxy.ccvideo.DownloaderWrapper;

import java.util.ArrayList;

public class OffLineCourseManager implements DownloadController.Observer, PullToRefreshListener {

    private Context mContext;
    private PullToRefreshRecyclerView mRefreshRecyclerView;
    //下载中列表
    private ArrayList<DownloaderWrapper> downloadingList = new ArrayList<>();
    //下载完成列表
    private ArrayList<DownloaderWrapper> downloadedList = new ArrayList<>();
    private int downloadingCount = 0;
    private ArrayList<DownloaderWrapper> mOffLineList = new ArrayList<>();
    private OffLineCourseLearnAdapter mAdapter;
    private View mEmptyView;
    private TextView tv_msg;


    public OffLineCourseManager(Context context, PullToRefreshRecyclerView refreshRecyclerView) {
        this.mContext = context;
        this.mRefreshRecyclerView = refreshRecyclerView;
        mAdapter = new OffLineCourseLearnAdapter(mContext, mOffLineList);

    }

    public void initData(){
        initView();
        downloadedList = DownloadController.downloadedList;
        downloadingList = DownloadController.downloadingList;
        if (downloadedList != null) {
            mOffLineList.addAll(downloadedList);
        }
        if (downloadingList != null) {
            mOffLineList.addAll(downloadingList);
        }
        mAdapter.setData(mOffLineList);
        if (mOffLineList.size() == 0) {
            isVisible(false);
        } else {
            isVisible(true);
        }
    }

    private void isVisible(boolean flag) {
        if (flag) {
            mRefreshRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mRefreshRecyclerView.setVisibility(View.GONE);
            tv_msg.setText("暂无课程信息");
        }
    }

    private void initView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRefreshRecyclerView.setAdapter(mAdapter);
        mRefreshRecyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        mRefreshRecyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        mRefreshRecyclerView.setLoadingMoreEnabled(true);
        //是否开启上拉刷新
        mRefreshRecyclerView.setPullRefreshEnabled(true);
        //设置刷新回调
        mRefreshRecyclerView.setPullToRefreshListener(this);
        //主动触发下拉刷新操作
//        mRefreshRecyclerView.onRefresh();
        //设置EmptyView
        mEmptyView = View.inflate(mContext, R.layout.layout_empty_view, null);
        mEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv_msg = mEmptyView.findViewById(R.id.tv_msg);
        mRefreshRecyclerView.setEmptyView(mEmptyView);
        mRefreshRecyclerView.setOnCreateContextMenuListener(onCreateContextMenuListener);
    }

    ContextMenu contextMenu;
    View.OnCreateContextMenuListener onCreateContextMenuListener = new View.OnCreateContextMenuListener() {
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            contextMenu = menu;
            menu.setHeaderTitle("操作");
            menu.add(ConfigUtil.DOWNLOADING_MENU_GROUP_ID, 0, 0, "删除");
        }
    };

    @Override
    public void update() {
        ((Activity)mContext).runOnUiThread(() -> {
            updateListView();

            //为防止出现删除提示框展示的时候，新的下载视频完成，导致删除错误的bug，故当有新的下载完成时，取消删除对话框
            int currentDownloadingCount = DownloadController.downloadingList.size();
            if (currentDownloadingCount < downloadingCount) {
                downloadingCount = currentDownloadingCount;
                if (contextMenu != null) {
                    contextMenu.close();
                }
            }
        });

    }

    private void updateListView(){

    }

    public void onResume(){
        DownloadController.attach(this);
        downloadingCount = DownloadController.downloadingList.size();
    }

    public void onPause(){
        DownloadController.detach(this);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
