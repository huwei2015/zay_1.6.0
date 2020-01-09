package com.example.administrator.zahbzayxy.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LearnNavigationAdapter;
import com.example.administrator.zahbzayxy.adapters.LearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.OnLineTitleAdapter;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import com.example.administrator.zahbzayxy.beans.OnTransitionTextListener;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.fragments.SimulationFragment;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.ColorBar;
import com.example.administrator.zahbzayxy.utils.FixedIndicatorView;
import com.example.administrator.zahbzayxy.utils.Indicator;
import com.example.administrator.zahbzayxy.utils.NumberFormatUtils;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnLineManager {

    private Context mContext;
    private FixedIndicatorView mFixedIndicatorView;
    private PullToRefreshRecyclerView mRefreshRecyclerView;
    private List<LearnNavigationBean.LearnListBean> mLearnList = new ArrayList<>();
    private ProgressBarLayout mLoadingBar;
    private OnLineTitleAdapter mTitleAdapter;
    private LearnOnlineCourseAdapter mCourseAdapter;
    private List<OnlineCourseBean.OnLineListBean> onLineListBeanList= new ArrayList<>();
    private int mPage = 1;


    public OnLineManager(Context context, FixedIndicatorView fixedIndicatorView, PullToRefreshRecyclerView refreshRecyclerView) {
        this.mContext = context;
        this.mFixedIndicatorView = fixedIndicatorView;
        this.mRefreshRecyclerView = refreshRecyclerView;
        mTitleAdapter = new OnLineTitleAdapter(mContext, mLearnList, mFixedIndicatorView);
        mCourseAdapter = new LearnOnlineCourseAdapter(mContext, onLineListBeanList);
        setItemClick(mTitleAdapter);
    }

    public void setLoadingView(ProgressBarLayout loadingView) {
        this.mLoadingBar = loadingView;
    }

    public void loadDAta() {
        showLoadingBar(true);
        initNavigationData();
    }

    private void initNavigationData() {
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getLearnNavigationData(0, token).enqueue(new Callback<LearnNavigationBean>() {
            @Override
            public void onResponse(Call<LearnNavigationBean> call, Response<LearnNavigationBean> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        mLearnList = response.body().getData().getData();
                        mLearnList.addAll(mLearnList);
                        setTitle();
//                        setCourseList(0, 0);
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

    private void setTitle() {
        mTitleAdapter.setData(mLearnList);
        mFixedIndicatorView.setAdapter(mTitleAdapter);

        float unSelectSize = 14;
        float selectSize = unSelectSize * 1.1f;
        int selectColor = mContext.getResources().getColor(R.color.lightBlue);
        int unSelectColor = mContext.getResources().getColor(R.color.black);
        mFixedIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        mFixedIndicatorView.setCurrentItem(0, true);
    }

    private void setCourseList(int position, int isAchieve) {
//        showLoadingBar(true);
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        int cateId = NumberFormatUtils.parseInt(mLearnList.get(position).getCateId());

        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getOnLineCourseList(mPage, 10, cateId, isAchieve, token).enqueue(new Callback<OnlineCourseBean>() {
            @Override
            public void onResponse(Call<OnlineCourseBean> call, Response<OnlineCourseBean> response) {
                hideLoadingBar();
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        List<OnlineCourseBean.UserCoursesBean> beanList = response.body().getData().getUserCourses();
                    }
                }
            }

            @Override
            public void onFailure(Call<OnlineCourseBean> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setItemClick(OnLineTitleAdapter adapter) {
        adapter.setOnItemClickListener((View clickItemView, int position) -> {
//            setCourseList(position, 0);
        });
    }

    private void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : mContext.getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    private void hideLoadingBar() {
        mLoadingBar.hide();
    }

}
