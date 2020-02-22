package com.example.administrator.zahbzayxy.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.EditMessageActivity;
import com.example.administrator.zahbzayxy.activities.FaceRecognitionActivity;
import com.example.administrator.zahbzayxy.adapters.LearnOfflineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.LearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.MyLearnOfflineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.MyLearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.MyOfflineTitleAdapter;
import com.example.administrator.zahbzayxy.adapters.MyOnLineTitleAdapter;
import com.example.administrator.zahbzayxy.adapters.OnLineTitleAdapter;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import com.example.administrator.zahbzayxy.beans.OfflineCourseLearnBean;
import com.example.administrator.zahbzayxy.beans.OnTransitionTextListener;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.beans.PersonInfo;
import com.example.administrator.zahbzayxy.ccvideo.MediaPlayActivity;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.ColorBar;
import com.example.administrator.zahbzayxy.utils.Constant;
import com.example.administrator.zahbzayxy.utils.FaceRecognitionUtils;
import com.example.administrator.zahbzayxy.utils.FixedIndicatorView;
import com.example.administrator.zahbzayxy.utils.NetworkUtils;
import com.example.administrator.zahbzayxy.utils.NumberFormatUtils;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.StringUtil;
import com.example.administrator.zahbzayxy.utils.ThreadPoolUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.example.administrator.zahbzayxy.vo.UserInfo;
import com.example.administrator.zahbzayxy.widget.LoadingDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyOnLineManager {

    private Context mContext;
    private FixedIndicatorView mFixedIndicatorView;
    private List<LearnNavigationBean.LearnListBean> mLearnList = new ArrayList<>();
    private MyOnLineTitleAdapter mTitleAdapter;
    private MyOfflineTitleAdapter mTitleOfflineAdapter;
    private MyLearnOnlineCourseAdapter mCourseAdapter;
    private MyLearnOfflineCourseAdapter mOffLineAdapter;
    private List<OnlineCourseBean.UserCoursesBean> mCoursesList = new ArrayList<>();
    private List<OnlineCourseBean.UserCoursesBean> mOneWeekList = new ArrayList<>();
    private List<OnlineCourseBean.UserCoursesBean> mBeforeList = new ArrayList<>();

    private List<OfflineCourseLearnBean.UserCoursesBean> mOfflineList = new ArrayList<>();
    private List<OfflineCourseLearnBean.UserCoursesBean> mOfflineNewList = new ArrayList<>();
    private List<OfflineCourseLearnBean.UserCoursesBean> mOfflineMoreList = new ArrayList<>();
    private int mPage = 1;
    private int mPosition = 0;
    private int mIsAchieve = 0;
    private int mCourseType = 0;
    private TextView tv_msg;
    private RelativeLayout rl_empty;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private LoadingDialog mLoading;
    private boolean mLoadingData = false;
    private boolean mIsHasData = true;
    private boolean mIsLoading;
    private Call<OfflineCourseLearnBean> mOffLineCall;
    private Call<OnlineCourseBean> mOnLineCall;

    public MyOnLineManager(Context context, FixedIndicatorView fixedIndicatorView, View view) {
        this.mContext = context;
        this.mFixedIndicatorView = fixedIndicatorView;
        this.mRefreshLayout = view.findViewById(R.id.my_on_line_refresh_layout);
        this.mRecyclerView = view.findViewById(R.id.my_on_line_recycler_view);
        Utils.setRefreshViewColor(mRefreshLayout);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLoading = new LoadingDialog(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mTitleAdapter = new MyOnLineTitleAdapter(mContext, mLearnList, mFixedIndicatorView);
        mTitleOfflineAdapter = new MyOfflineTitleAdapter(mContext, mLearnList, mFixedIndicatorView);
        mCourseAdapter = new MyLearnOnlineCourseAdapter(mContext, mCoursesList);
        mOffLineAdapter = new MyLearnOfflineCourseAdapter(mContext, mOfflineList);
        setItemClick(mTitleAdapter);
        setItemClick(mTitleOfflineAdapter);
        initEvent();
    }

    public void setEmptyView(RelativeLayout emptyView){
        rl_empty = emptyView;
    }

    private boolean mLoad = false;
    private void setView() {
        if (mLoad) return;
        mLoad = true;
        tv_msg = rl_empty.findViewById(R.id.tv_msg);
        if (mCourseType == 0) {
            mRecyclerView.setAdapter(mCourseAdapter);
        } else {
            mRecyclerView.setAdapter(mOffLineAdapter);
        }
    }

    private void isVisible(boolean flag) {
        if (flag) {
            mRefreshLayout.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        } else {
            rl_empty.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
            tv_msg.setText("暂无课程信息");
        }
    }

    private void initEvent(){
        mCourseAdapter.setOnLearnOnlineItemClickListener(position -> {
            // 在线课点击事件处理
            OnlineCourseBean.UserCoursesBean coursesBean = mCoursesList.get(position);
            if (coursesBean != null) {
                boolean isPlay = coursesBean.isPlay();
                if (!isPlay) {
                    ToastUtils.showLongInfo(coursesBean.getMsg_cont() + "");
                    return;
                }
                int userCourseId = coursesBean.getUserCourseId();
                int courseId = coursesBean.getMainCourseId();
                isPerfectPersonInfo(userCourseId, courseId);
            }
        });

        mOffLineAdapter.setOnLearnOfflineItemClickListener(position -> {
            // 线下课点击事件处理
//            OfflineCourseLearnBean.UserCoursesBean coursesBean = mOfflineList.get(position);
//            int userCourseId = coursesBean.getUserCourseId();
//            int courseId = coursesBean.getCourseId();
//            isPerfectPersonInfo(userCourseId, courseId);
        });
    }

    private void initLoadingEvent(){
        mRefreshLayout.setOnRefreshListener(() -> {
            mLoadingData = true;
            mLoadType = 1;
            mPage = 1;
            clearList();
            mIsHasData = true;
            setCourseList(mPosition, mIsAchieve);
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 没有更多的数据了
                if (!mIsHasData) return;
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int itemCount = 0;
                if (mCourseType == 0) {
                    itemCount = mCourseAdapter.getItemCount();
                } else {
                    itemCount = mOffLineAdapter.getItemCount();
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == itemCount && !mIsLoading) {
                    mLoading.show();
                    mIsLoading = true;
                    mLoadType = 2;
                    mPage++;
                    setCourseList(mPosition, mIsAchieve);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void clearList(){
        mCoursesList.clear();
        mBeforeList.clear();
        mOneWeekList.clear();

        mOfflineList.clear();
        mOfflineNewList.clear();
        mOfflineMoreList.clear();
        if (mCourseType == 0) {
            mCourseAdapter.notifyDataSetChanged();
        } else {
            mOffLineAdapter.notifyDataSetChanged();
        }
    }

    private int mLoadType = 0;

    /**
     * 加载数据
     * @param type 0，在线课程  1，线下课程
     */
    public void loadDAta(int type) {
        mCourseType = type;
        setView();
        initLoadingEvent();
        mPosition = 0;

        mLoadType = 0;
        mPage = 1;
        clearList();
        mLoading.show();
        mLoadingData = true;
        initNavigationData();

    }

    private void initNavigationData() {
        if (mCourseType == 0) {
            loadOnLineTitleData();
        } else {
            loadOffLineTitleData();
        }
    }

    private void loadOffLineTitleData(){
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        Log.i("token", token);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getOffLinTitle(0, token).enqueue(new Callback<LearnNavigationBean>() {
            @Override
            public void onResponse(Call<LearnNavigationBean> call, Response<LearnNavigationBean> response) {
                if (response != null && response.body() != null && response.body().getData() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        mLearnList = response.body().getData().getData();
                        if (mLearnList == null || mLearnList.size() == 0) {
                            isVisible(false);
                            hindLoading();
                            return;
                        } else {
                            isVisible(true);
                        }
                        setTitle();
                        setCourseList(mPosition, 0);
                        return;
                    }
                }
                isVisible(false);
                hindLoading();
            }

            @Override
            public void onFailure(Call<LearnNavigationBean> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                isVisible(false);
                hindLoading();
            }
        });
    }

    private void loadOnLineTitleData(){
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getLearnNavigationData(0, token).enqueue(new Callback<LearnNavigationBean>() {
            @Override
            public void onResponse(Call<LearnNavigationBean> call, Response<LearnNavigationBean> response) {
                if (response != null && response.body() != null && response.body().getData() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        mLearnList = response.body().getData().getData();
                        if (mLearnList == null || mLearnList.size() == 0) {
                            isVisible(false);
                            hindLoading();
                            return;
                        } else {
                            isVisible(true);
                        }
                        setTitle();
                        setCourseList(mPosition, 0);
                        return;
                    }
                }
                isVisible(false);
                hindLoading();
            }

            @Override
            public void onFailure(Call<LearnNavigationBean> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                isVisible(false);
                hindLoading();
            }
        });
    }

    private void setTitle() {
        if (mCourseType == 0) {
            mTitleAdapter.setData(mLearnList);
            mFixedIndicatorView.setAdapter(mTitleAdapter);
        }else{
            mTitleOfflineAdapter.setData(mLearnList);
            mFixedIndicatorView.setAdapter(mTitleOfflineAdapter);
        }
        mFixedIndicatorView.setScrollBar(new ColorBar(mContext, mContext.getResources().getColor(R.color.transparent), 6));//设置选中下划线

        float unSelectSize = 14;
        float selectSize = unSelectSize * 1.1f;
        int selectColor = mContext.getResources().getColor(R.color.lightBlue);
        int unSelectColor = mContext.getResources().getColor(R.color.black);
        mFixedIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        mFixedIndicatorView.setCurrentItem(0, true);
    }

    private void setCourseList(int position, int isAchieve) {
        if (mLearnList == null || mLearnList.size() == 0){
            hindLoading();
            return;
        }
        mIsAchieve = isAchieve;
        mPosition = position;
        if (mCourseType == 0) {
            onLineCourseList(position, isAchieve);
        } else {
            offLineCourseList(position, isAchieve);
        }
    }

    private void offLineCourseList(int position, int isAchieve) {
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        int cateId = NumberFormatUtils.parseInt(mLearnList.get(position).getCateId());

        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        mOffLineCall = aClass.getOffLineCourseList(mPage, 10, cateId, isAchieve, token);
        mOffLineCall.enqueue(new Callback<OfflineCourseLearnBean>() {
            @Override
            public void onResponse(Call<OfflineCourseLearnBean> call, Response<OfflineCourseLearnBean> response) {
                hindLoading();
                if (response != null && response.body() != null  && response.body().getData() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        List<OfflineCourseLearnBean.UserCoursesBean> beanList = response.body().getData().getUserCourses();
                        if (mPage > 1 && (beanList == null || beanList.size() == 0)) {
                            mIsHasData = false;
                            ToastUtils.showShortInfo("数据加载完毕");
                            mPage--;
                            mLoadingData = false;
                            return;
                        }
                        if (mPage == 1 && (beanList == null || beanList.size() == 0)) {
                            isVisible(false);
                            mLoadingData = false;
                            mLoading.dismiss();
                            return;
                        } else {
                            isVisible(true);
                        }
                        beanList = setOfflineDataList(beanList);
                        mOfflineList.clear();
                        mOfflineList.addAll(beanList);
                        mOffLineAdapter.setData(mOfflineList);
                        if (mPage == 1) {
                            mRecyclerView.scrollToPosition(0);
                        }
                        return;
                    }
                }
                isVisible(false);
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void onFailure(Call<OfflineCourseLearnBean> call, Throwable t) {
                String message = t.getMessage();
                hindLoading();
                if (!("Canceled".equals(message) || "Socket closed".equals(message))) {
                    isVisible(false);
                    if (mPage > 1) {
                        mPage--;
                    }
                    ToastUtils.showLongInfo( "网络异常");
                }
            }
        });
    }

    private void onLineCourseList(int position, int isAchieve){
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        int cateId = NumberFormatUtils.parseInt(mLearnList.get(position).getCateId());

        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        mOnLineCall = aClass.getOnLineCourseList(mPage, 10, cateId, isAchieve, token);
        mOnLineCall.enqueue(new Callback<OnlineCourseBean>() {
            @Override
            public void onResponse(Call<OnlineCourseBean> call, Response<OnlineCourseBean> response) {
                hindLoading();
                if (response != null && response.body() != null && response.body().getData() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        List<OnlineCourseBean.UserCoursesBean> beanList = response.body().getData().getUserCourses();
                        Log.i("beanList", beanList.toString());
                        if (mPage > 1 && (beanList == null || beanList.size() == 0)) {
                            mIsHasData = false;
                            ToastUtils.showShortInfo("数据加载完毕");
                            mPage--;
                            return;
                        }
                        if (mPage == 1 && (beanList == null || beanList.size() == 0)) {
                            isVisible(false);
                            return;
                        } else {
                            isVisible(true);
                        }
                        beanList = setDataList(beanList);
                        mCoursesList.clear();
                        mCoursesList.addAll(beanList);
                        mCourseAdapter.setData(mCoursesList);
                        if (mPage == 1) {
                            mRecyclerView.scrollToPosition(0);
                        }
                        return;
                    }
                }
                isVisible(false);
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void onFailure(Call<OnlineCourseBean> call, Throwable t) {
                hindLoading();
                String message = t.getMessage();
                if (!("Canceled".equals(message) || "Socket closed".equals(message))) {
                    isVisible(false);
                    if (mPage > 1) {
                        mPage--;
                    }
                    ToastUtils.showLongInfo("网络异常");
                }
            }
        });
    }

    private List<OnlineCourseBean.UserCoursesBean> setDataList(List<OnlineCourseBean.UserCoursesBean> data) {
        if (data == null || data.size() == 0) return mCoursesList;

        for (OnlineCourseBean.UserCoursesBean bean : data) {
            if (bean != null) {
                int timeState = bean.getTimeState();
                if (timeState == 0) {
                    mOneWeekList.add(bean);
                } else {
                    mBeforeList.add(bean);
                }
            }
        }

        List<OnlineCourseBean.UserCoursesBean> list = new ArrayList<>();
        list.addAll(mOneWeekList);
        list.addAll(mBeforeList);
        return list;
    }

    private List<OfflineCourseLearnBean.UserCoursesBean> setOfflineDataList(List<OfflineCourseLearnBean.UserCoursesBean> data) {
        if (data == null || data.size() == 0) return mOfflineList;

        for (OfflineCourseLearnBean.UserCoursesBean bean : data) {
            if (bean != null) {
                int isNew = bean.getIsNew();
                if (isNew == 0) {
                    mOfflineMoreList.add(bean);
                } else {
                    mOfflineNewList.add(bean);
                }
            }
        }

        List<OfflineCourseLearnBean.UserCoursesBean> list = new ArrayList<>();
        list.addAll(mOfflineNewList);
        list.addAll(mOfflineMoreList);
        return list;
    }

    private void hindLoading() {
        mLoadingData = false;
        mRefreshLayout.setRefreshing(false);
        mLoading.dismiss();
    }

    private void setItemClick(MyOnLineTitleAdapter adapter) {
        adapter.setOnItemClickListener((View clickItemView, int position) -> {
            if (mPosition == position) return;
            mPage = 1;
            if (mOnLineCall != null) {
                mOnLineCall.cancel();
            }
            clearList();
            mLoadingData = true;
            mLoading.show();
            mIsHasData = true;
            setCourseList(position, mIsAchieve);
        });
    }

    private void setItemClick(MyOfflineTitleAdapter adapter) {
        adapter.setOnItemClickListener((View clickItemView, int position) -> {
            if (mPosition == position) return;
            mPage = 1;
            if (mOffLineCall != null) {
                mOffLineCall.cancel();
            }
            clearList();
            mLoadingData = true;
            mLoading.show();
            mIsHasData = true;
            setCourseList(position, mIsAchieve);
        });
    }


    private void isPerfectPersonInfo(int userCourse_Id, int coruse_Id) {
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        PersonGroupInterfac personGroupInterfac = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        personGroupInterfac.getPersonInfo(token, userCourse_Id).enqueue(new Callback<PersonInfo>() {
            @Override
            public void onResponse(Call<PersonInfo> call, Response<PersonInfo> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        boolean data = (boolean) response.body().getData();
                        if (!data) {
                            //获取用户信息
                            ExecutorService threadPool = ThreadPoolUtils.getThreadPoolExecutor();
                            GetUserInfoRunnable task = new GetUserInfoRunnable(mContext, userCourse_Id, coruse_Id, token, mHandler);
                            threadPool.submit(task);
                        } else {
                            showUploadDialog();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<PersonInfo> call, Throwable t) {
                String msg= t.getMessage();
                Toast.makeText(mContext,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private android.app.AlertDialog upLoadAlertDialog;

    private void showUploadDialog() {

        if (upLoadAlertDialog != null) {
            upLoadAlertDialog.dismiss();
        }
        upLoadAlertDialog = new android.app.AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("请先完善个人信息，再进行学习")
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mContext.startActivity(new Intent(mContext, EditMessageActivity.class));
                        dialog.dismiss();
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        upLoadAlertDialog.show();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
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

        //wifi相关内容
        //先检查一下设置提醒按钮wifi开关是否打开,
        SharedPreferences wifiDb = mContext.getSharedPreferences("wifiDb", MODE_PRIVATE);
        boolean wifiSwitch = wifiDb.getBoolean("WifiSwitch", false);
        if (wifiSwitch == true) {//如果打开时要检查当前手机的WiFi是否打开和可用，如果不可用或者没打开要提示一下
            boolean wifiEnabled = NetworkUtils.getWifiEnabled(mContext);
            if (wifiEnabled == true) {
                if (mWhat == 0) {
                    gotoMediaPlayActivity(userCourseId, courseId, token);
                } else if (mWhat == 2) {
                    gotoFaceRecognitionActivity(userCourseId, courseId, token);
                }
            } else if (wifiEnabled == false) {
                new AlertDialog.Builder(mContext).setTitle("播放提示框").setMessage("当前为非WiFi环境，确定播放？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mWhat == 0) {
                                    gotoMediaPlayActivity(userCourseId, courseId, token);
                                } else if (mWhat == 2) {
                                    gotoFaceRecognitionActivity(userCourseId, courseId, token);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        } else {
            if (mWhat == 0) {
                gotoMediaPlayActivity(userCourseId, courseId, token);
            } else if (mWhat == 2) {
                gotoFaceRecognitionActivity(userCourseId, courseId, token);
            }
        }
    }

    /**
     * 前往人脸识别页面
     *
     * @param userCourseId
     * @param coruseId
     * @param token
     */
    private void gotoFaceRecognitionActivity(int userCourseId, int coruseId, String token) {
        //初始化人脸识别SDK
        FaceRecognitionUtils.initContrastFaceRecognition(mContext);
        Intent intent = new Intent(mContext, FaceRecognitionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", coruseId);
        bundle.putString("token", token);
        bundle.putBoolean("isLocalPlay", false);
        bundle.putInt("rootIn", 1);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    /**
     * 前往视频播放页面
     *
     * @param userCourseId
     * @param coruseId
     * @param token
     */
    private void gotoMediaPlayActivity(int userCourseId, int coruseId, String token) {
        Intent intent = new Intent(mContext, MediaPlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", coruseId);
        bundle.putString("token", token);
        bundle.putBoolean("isLocalPlay", false);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    static class GetUserInfoRunnable implements Runnable {
        Context mContext;
        int needVerify;
        String faceUrl;
        int mUserCourseId;
        int mCourseId;
        String mToken;
        Handler runHandler;

        GetUserInfoRunnable(Context context, int userCourseId, int coruseId, String token, Handler handler) {
            mContext = context;
            mUserCourseId = userCourseId;
            mCourseId = coruseId;
            mToken = token;
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
