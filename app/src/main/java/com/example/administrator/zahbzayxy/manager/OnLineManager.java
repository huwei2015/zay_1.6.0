package com.example.administrator.zahbzayxy.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LearnOfflineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.LearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.OnLineTitleAdapter;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import com.example.administrator.zahbzayxy.beans.OfflineCourseLearnBean;
import com.example.administrator.zahbzayxy.beans.OnTransitionTextListener;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.ColorBar;
import com.example.administrator.zahbzayxy.utils.FixedIndicatorView;
import com.example.administrator.zahbzayxy.utils.NumberFormatUtils;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnLineManager implements PullToRefreshListener {

    private Context mContext;
    private FixedIndicatorView mFixedIndicatorView;
    private PullToRefreshRecyclerView mRefreshRecyclerView;
    private List<LearnNavigationBean.LearnListBean> mLearnList = new ArrayList<>();
    private ProgressBarLayout mLoadingBar;
    private OnLineTitleAdapter mTitleAdapter;
    private LearnOnlineCourseAdapter mCourseAdapter;
    private LearnOfflineCourseAdapter mOffLineAdapter;
    private CheckBox mFilterCb;
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
    private View emptyView;
    private TextView tv_msg;


    public OnLineManager(Context context, FixedIndicatorView fixedIndicatorView, PullToRefreshRecyclerView refreshRecyclerView, CheckBox filterCb) {
        this.mContext = context;
        this.mFixedIndicatorView = fixedIndicatorView;
        this.mRefreshRecyclerView = refreshRecyclerView;
        this.mFilterCb = filterCb;
        mTitleAdapter = new OnLineTitleAdapter(mContext, mLearnList, mFixedIndicatorView);
        mCourseAdapter = new LearnOnlineCourseAdapter(mContext, mCoursesList);
        mOffLineAdapter = new LearnOfflineCourseAdapter(mContext, mOfflineList);
        setItemClick(mTitleAdapter);
        mFilterCb.setChecked(false);
        initEvent();
    }

    private boolean mLoad = false;
    private void setView() {
        if (mLoad) return;
        mLoad = true;
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (mCourseType == 0) {
            mRefreshRecyclerView.setAdapter(mCourseAdapter);
        } else {
            mRefreshRecyclerView.setAdapter(mOffLineAdapter);
        }
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
        emptyView = View.inflate(mContext, R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv_msg = emptyView.findViewById(R.id.tv_msg);
        mRefreshRecyclerView.setEmptyView(emptyView);

        mFilterCb.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            showLoadingBar(true);
            mPage = 1;
            clearList();
            mRefreshRecyclerView.setLoadingMoreEnabled(true);
            setCourseList(mPosition, isChecked ? 1 : 0);
        });
    }

    private void isVisible(boolean flag) {
        if (flag) {
            mRefreshRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
            mRefreshRecyclerView.setVisibility(View.GONE);
            tv_msg.setText("暂无课程信息");
        }
    }

    private void initEvent(){
        mCourseAdapter.setOnLearnOnlineItemClickListener(position -> {
            // 在线课点击事件处理
        });

        mOffLineAdapter.setOnLearnOfflineItemClickListener(position -> {
            // 线下课点击事件处理
        });
    }

    public void setLoadingView(ProgressBarLayout loadingView) {
        this.mLoadingBar = loadingView;
    }

    private void clearList(){
        mCoursesList.clear();
        mBeforeList.clear();
        mOneWeekList.clear();

        mOfflineList.clear();
        mOfflineNewList.clear();
        mOfflineMoreList.clear();
    }

    private int mLoadType = 0;

    /**
     * 加载数据
     * @param type 0，在线课程  1，线下课程
     */
    public void loadDAta(int type) {
        mCourseType = type;
        mPosition = 0;

        mLoadType = 0;
        mPage = 1;
        clearList();
        mRefreshRecyclerView.setLoadingMoreEnabled(true);
//        mFilterCb.setChecked(false);
        setView();
        initNavigationData();
        if (mCourseType == 0) {
            showLoadingBar(true);
        }

    }

    private void initNavigationData() {
        if (mCourseType == 0) {
            mCoursesList.clear();
            mBeforeList.clear();
            mOneWeekList.clear();
            loadOnLineTitleData();
        } else {
            mOfflineList.clear();
            mOfflineNewList.clear();
            mOfflineMoreList.clear();
            loadOffLineTitleData();
        }
    }

    private void loadOffLineTitleData(){
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        Log.i("token", token);
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getOffLinTitle(mFilterCb.isChecked()?1:0, token).enqueue(new Callback<LearnNavigationBean>() {
            @Override
            public void onResponse(Call<LearnNavigationBean> call, Response<LearnNavigationBean> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        mLearnList = response.body().getData().getData();
                        if (mLearnList == null || mLearnList.size() == 0) {
                            isVisible(false);
                            return;
                        } else {
                            isVisible(true);
                        }
                        setTitle();
                        setCourseList(mPosition, mFilterCb.isChecked()?1:0);
                    }
                }
            }

            @Override
            public void onFailure(Call<LearnNavigationBean> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                hideLoadingBar();
            }
        });
    }

    private void loadOnLineTitleData(){
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getLearnNavigationData(mFilterCb.isChecked()?1:0, token).enqueue(new Callback<LearnNavigationBean>() {
            @Override
            public void onResponse(Call<LearnNavigationBean> call, Response<LearnNavigationBean> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        mLearnList = response.body().getData().getData();
                        if (mLearnList == null || mLearnList.size() == 0) {
                            hideLoadingBar();
                            isVisible(false);
                            return;
                        } else {
                            isVisible(true);
                        }
                        setTitle();
                        setCourseList(mPosition, mFilterCb.isChecked()?1:0);
                        return;
                    }
                }
                hideLoadingBar();
            }

            @Override
            public void onFailure(Call<LearnNavigationBean> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
                hideLoadingBar();
            }
        });
    }

    private void setTitle() {
        mTitleAdapter.setData(mLearnList);
        mFixedIndicatorView.setAdapter(mTitleAdapter);
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
            hideLoadingBar();
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
        aClass.getOffLineCourseList(mPage, 10, cateId, isAchieve, token).enqueue(new Callback<OfflineCourseLearnBean>() {
            @Override
            public void onResponse(Call<OfflineCourseLearnBean> call, Response<OfflineCourseLearnBean> response) {
                hindLoading();
                hideLoadingBar();
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        List<OfflineCourseLearnBean.UserCoursesBean> beanList = response.body().getData().getUserCourses();
                        if (mPage > 1 && (beanList == null || beanList.size() == 0)) {
                            mRefreshRecyclerView.setLoadingMoreEnabled(false);
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
                        beanList = setOfflineDataList(beanList);
                        mOfflineList.clear();
                        mOfflineList.addAll(beanList);
                        mOffLineAdapter.setData(mOfflineList);
                        if (mPage == 1) {
                            mRefreshRecyclerView.scrollToPosition(0);
                        }
                        return;
                    }
                }
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void onFailure(Call<OfflineCourseLearnBean> call, Throwable t) {
                hindLoading();
                hideLoadingBar();
                if (mPage > 1) {
                    mPage--;
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onLineCourseList(int position, int isAchieve){
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        int cateId = NumberFormatUtils.parseInt(mLearnList.get(position).getCateId());

        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getOnLineCourseList(mPage, 10, cateId, isAchieve, token).enqueue(new Callback<OnlineCourseBean>() {
            @Override
            public void onResponse(Call<OnlineCourseBean> call, Response<OnlineCourseBean> response) {
                hindLoading();
                hideLoadingBar();
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        List<OnlineCourseBean.UserCoursesBean> beanList = response.body().getData().getUserCourses();
                        Log.i("beanList", beanList.toString());
                        if (mPage > 1 && (beanList == null || beanList.size() == 0)) {
                            mRefreshRecyclerView.setLoadingMoreEnabled(false);
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
                            mRefreshRecyclerView.scrollToPosition(0);
                        }
                        return;
                    }
                }
                if (mPage > 1) {
                    mPage--;
                }
            }

            @Override
            public void onFailure(Call<OnlineCourseBean> call, Throwable t) {
                hindLoading();
                if (mPage > 1) {
                    mPage--;
                }
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
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
        if (mLoadType == 1) {
            mRefreshRecyclerView.setRefreshComplete();
        } else if (mLoadType == 2) {
            mRefreshRecyclerView.setLoadMoreComplete();
        }
    }

    private void setItemClick(OnLineTitleAdapter adapter) {
        adapter.setOnItemClickListener((View clickItemView, int position) -> {
            if (mPosition == position) return;
            mPage = 1;
            clearList();
            mRefreshRecyclerView.setLoadingMoreEnabled(true);
            setCourseList(position, mIsAchieve);
        });
    }

    private void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : mContext.getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    private void hideLoadingBar() {
        mLoadingBar.hide();
    }

    @Override
    public void onRefresh() {
        mLoadType = 1;
        mPage = 1;
        clearList();
        mRefreshRecyclerView.setLoadingMoreEnabled(true);
        setCourseList(mPosition, mIsAchieve);
    }

    @Override
    public void onLoadMore() {
        mLoadType = 2;
        mPage++;
        setCourseList(mPosition, mIsAchieve);
    }
}
