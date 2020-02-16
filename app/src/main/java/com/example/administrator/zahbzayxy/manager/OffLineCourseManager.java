package com.example.administrator.zahbzayxy.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.activities.FaceRecognitionForOfflineActivity;
import com.example.administrator.zahbzayxy.adapters.OffLineCourseLearnAdapter;
import com.example.administrator.zahbzayxy.ccvideo.ConfigUtil;
import com.example.administrator.zahbzayxy.ccvideo.DownloadController;
import com.example.administrator.zahbzayxy.ccvideo.DownloaderWrapper;
import com.example.administrator.zahbzayxy.ccvideo.MediaPlayActivity;
import com.example.administrator.zahbzayxy.fragments.OnLineCourseFragment;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.FaceRecognitionUtils;
import com.example.administrator.zahbzayxy.utils.NetworkUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.example.administrator.zahbzayxy.vo.UserInfo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

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
    private RelativeLayout mEmptyView;
    private TextView tv_msg;
    private OnLineCourseFragment mFragment;


    public OffLineCourseManager(Context context, PullToRefreshRecyclerView refreshRecyclerView) {
        this.mContext = context;
        this.mRefreshRecyclerView = refreshRecyclerView;
        mAdapter = new OffLineCourseLearnAdapter(mContext, mOffLineList);
        mRefreshRecyclerView.setAdapter(mAdapter);
        initEvent();
    }

    public void setEmptyView(RelativeLayout emptyView){
        mEmptyView = emptyView;
    }

    public void setFragment(OnLineCourseFragment fragment) {
        this.mFragment = fragment;
    }

    public void initData() {
        initView();
        downloadedList = DownloadController.downloadedList;
        downloadingList = DownloadController.downloadingList;
        mOffLineList.clear();
        if (downloadingList != null) {
            downloadingList = setList(downloadingList, 1);
            mOffLineList.addAll(downloadingList);
        }
        if (downloadedList != null) {
            downloadedList = setList(downloadedList, 2);
            mOffLineList.addAll(downloadedList);
        }
        mAdapter.setData(mOffLineList);
        if (mOffLineList.size() == 0) {
            isVisible(false);
        } else {
            isVisible(true);
        }
    }

    private int pos = 0;
    private String videoId;

    private void initEvent() {
        mAdapter.setOnDownLoadedItemClickListener(position -> {
            // 已完成
            if (position >= mOffLineList.size()) return;
            pos = position;
            DownloaderWrapper wrapper = mOffLineList.get(position);
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
            boolean isConnected = NetworkUtils.isConnected(mContext);
            if (!isConnected) {
                Intent intent = new Intent(mContext, MediaPlayActivity.class);
                intent.putExtra("videoId", wrapper.getDownloadInfo().getTitle());
                intent.putExtra("isLocalPlay", true);
                intent.putExtra("currentPosition", sharedPreferences.getInt("currentPosition" + wrapper.getDownloadInfo().getTitle(), 0));
                mContext.startActivity(intent);
            } else {
                String token = sharedPreferences.getString("token", "");
                int userCourseId = wrapper.getDownloadInfo().getUserCourseId();
                int coruseId = wrapper.getDownloadInfo().getCoruseId();
                videoId = wrapper.getDownloadInfo().getVideoId();
                ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
                GetUserInfoRunnable task = new GetUserInfoRunnable(mContext, userCourseId, coruseId, token, videoId, handler);
                threadPool.submit(task);
            }
        });

        mAdapter.setOnDownLoadingItemClickListener(position -> {
            // 下载中
            DownloadController.parseItemClick(position);
            updateList();
        });
    }

    private void updateList() {
        downloadedList = DownloadController.downloadedList;
        downloadingList = DownloadController.downloadingList;
        setListView();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            gotoVideo(msg);
        }
    };

    /**
     * 画面跳转
     *
     * @param msg
     */
    private void gotoVideo(Message msg) {
        final int mWhat = msg.what;
        if (mWhat == 1) {
            //需要人脸识别且照片未上传
            showUploadDialog();
            return;
        } else if (mWhat == 3) {
            Toast.makeText(mContext, "认证失败", Toast.LENGTH_SHORT).show();
            return;
        } else if (mWhat == 4) {
            Toast.makeText(mContext, "连接失败", Toast.LENGTH_SHORT).show();
            return;
        }

        final int userCourseId = msg.getData().getInt("userCourseId", 0);
        final int courseId = msg.getData().getInt("coruseId", 0);
        final String token = msg.getData().getString("token");
        if (mWhat == 0) {
            //播放不需要参数
            gotoMediaPlayActivity(userCourseId, courseId, token);
        } else if (mWhat == 2) {
            gotoFaceRecognitionActivity(userCourseId, courseId, videoId, token);
        }
    }

    /**
     * 前往视频播放页面
     *
     * @param userCourseId
     * @param coruseId
     * @param token
     */
    private void gotoMediaPlayActivity(int userCourseId, int coruseId, String token) {
        if (pos >= mOffLineList.size()) return;
        Intent intent = new Intent(mContext, MediaPlayActivity.class);
        intent.putExtra("videoId", videoId);
//        intent.putExtra("isLocalPlay", true);
//        intent.putExtra("isTimes", true);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
        DownloaderWrapper wrapper = mOffLineList.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", coruseId);
        bundle.putInt("selectId", wrapper.getDownloadInfo().getSectionId());
        bundle.putString("token", token);
        bundle.putBoolean("isLocalPlay", true);
        intent.putExtra("currentPosition", sharedPreferences.getInt("currentPosition" + wrapper.getDownloadInfo().getTitle(), 0));
        bundle.putInt("selectionId", wrapper.getDownloadInfo().getSectionId());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 前往人脸识别页面
     *
     * @param userCourseId
     * @param coruseId
     * @param token        离线人脸
     */
    private void gotoFaceRecognitionActivity(int userCourseId, int coruseId, String videoId, String token) {
        if (pos >= mOffLineList.size()) return;
        //初始化人脸识别SDK
        FaceRecognitionUtils.initContrastFaceRecognition(mContext);
        Intent intent = new Intent(mContext, FaceRecognitionForOfflineActivity.class);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("tokenDb", Context.MODE_PRIVATE);
        DownloaderWrapper wrapper = mOffLineList.get(pos);
        Bundle bundle = new Bundle();
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", coruseId);
        bundle.putInt("selectId", wrapper.getDownloadInfo().getSectionId());
        bundle.putString("token", token);
        bundle.putBoolean("isLocalPlay", false);
        bundle.putInt("rootIn", 1);
        bundle.putString("videoId", videoId);
        bundle.putInt("currentPosition", sharedPreferences.getInt("currentPosition" + wrapper.getDownloadInfo().getTitle(), 0));
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 人脸识别对话框
     */
    private AlertDialog upLoadAlertDialog;

    private void showUploadDialog() {

        if (upLoadAlertDialog != null) {
            upLoadAlertDialog.dismiss();
        }
        upLoadAlertDialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage(R.string.upload_portrait_prompt)
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mContext.startActivity(new Intent(mContext, EditMessageActivity.class));
                        return;
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        if (mFragment.isAdded() && mContext != null && !mFragment.getActivity().isFinishing()) {
            upLoadAlertDialog.show();
        }
    }


    private ArrayList<DownloaderWrapper> setList(ArrayList<DownloaderWrapper> list, int type) {
        for (int i = 0; i < list.size(); i++) {
            DownloaderWrapper wrapper = list.get(i);
            wrapper.setType(type);
            list.set(i, wrapper);
        }
        return list;
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

    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRefreshRecyclerView.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        mRefreshRecyclerView.displayLastRefreshTime(true);
        //是否开启上拉加载
        mRefreshRecyclerView.setLoadingMoreEnabled(false);
        //是否开启上拉刷新
        mRefreshRecyclerView.setPullRefreshEnabled(false);
        //设置刷新回调
        mRefreshRecyclerView.setPullToRefreshListener(this);
        //主动触发下拉刷新操作
//        mRefreshRecyclerView.onRefresh();
        tv_msg = mEmptyView.findViewById(R.id.tv_msg);
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
        ((Activity) mContext).runOnUiThread(() -> {
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

    private int cancel = 0;

    private void updateListView() {
        downloadedList = DownloadController.downloadedList;
        downloadingList = DownloadController.downloadingList;
        if ((downloadingList == null || downloadingList.size() == 0)) {
            if (cancel != 0) {
                setListView();
                cancel = 0;
            }
            return;
        } else {
            cancel++;
        }
        setListView();
    }

    private void setListView() {
        mOffLineList.clear();
        if (downloadingList != null) {
            downloadingList = setList(downloadingList, 1);
            mOffLineList.addAll(downloadingList);
        }
        if (downloadedList != null) {
            downloadedList = setList(downloadedList, 2);
            mOffLineList.addAll(downloadedList);
        }
        mAdapter.setData(mOffLineList);
        if (mOffLineList.size() == 0) {
            isVisible(false);
        } else {
            isVisible(true);
        }
    }

    public void onResume() {
        DownloadController.attach(this);
        downloadingCount = DownloadController.downloadingList.size();
    }

    public void onPause() {
        DownloadController.detach(this);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    static class GetUserInfoRunnable implements Runnable {
        Context mContext;
        int needVerify;
        String faceUrl;
        int mUserCourseId;
        int mCourseId;
        String mToken;
        String videoId;
        Handler runHandler;

        GetUserInfoRunnable(Context context, int userCourseId, int coruseId, String token, String videoId, Handler handler) {
            mContext = context;
            mUserCourseId = userCourseId;
            mCourseId = coruseId;
            mToken = token;
            videoId = videoId;
            runHandler = handler;
        }

        @Override
        public void run() {
            Message message = runHandler.obtainMessage();
            try {
                mToken = mToken == null ? "" : mToken;
                //通过OKHttp访问后台接口
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add(Constant.TOKEN_PARAM, mToken)
                        .build();
                Request request = new Request.Builder()
                        .url(Constant.GET_USER_INFO_URL)
                        .post(requestBody)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                // 将json字符串转化成对应数据类
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(responseData, UserInfo.class);
                if (Constant.SUCCESS_CODE.equals(userInfo.getCode())) {
                    saveUsrInfo(userInfo);
                    needVerify = userInfo.getData().getNeedVerify();
                    faceUrl = userInfo.getData().getFacePath();
                    faceUrl = faceUrl == null ? "" : faceUrl;
                    checkResult(message, needVerify, faceUrl, mUserCourseId, mCourseId, mToken);
                } else {
                    message.what = 3;
                }
                message.sendToTarget();
            } catch (Exception e) {
                message.what = 4;
                message.sendToTarget();
                StringUtil.getExceptionMessage(e);
            }
        }

        private void checkResult(Message msg, int needVerify, String faceUrl, int userCourseId, int coruseId, String token) {

            if (needVerify == 0) {
                msg.what = 0;
            } else if (needVerify == 1 && "".equals(faceUrl)) {
                msg.what = 1;
            } else {
                msg.what = 2;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("userCourseId", userCourseId);
            bundle.putInt("coruseId", coruseId);
            bundle.putString("token", token);
//            bundle.putBoolean("isLocalPlay",false);
            msg.setData(bundle);

        }

        private void saveUsrInfo(UserInfo userInfo) {
            try {
                SharedPreferences sp = mContext.getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt(Constant.IS_NEED_VERIFY_KEY, userInfo.getData().getNeedVerify());
                edit.putString(Constant.INTERVAL_TIME_KEY, userInfo.getData().getIntervalTime());
                edit.putString(Constant.PORTRAIT_URL_KEY, userInfo.getData().getFacePath());
                edit.commit();
            } catch (Exception e) {
                Log.e("saveUsrInfo", StringUtil.getExceptionMessage(e));
            }
        }
    }
}
