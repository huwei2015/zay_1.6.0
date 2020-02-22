package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ChooseTopicActivity;
import com.example.administrator.zahbzayxy.activities.QueslibActivity;
import com.example.administrator.zahbzayxy.adapters.LearnNavigationAdapter;
import com.example.administrator.zahbzayxy.adapters.LearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.MyLearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.MyOnLineTitleAdapter;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.manager.MyOffLineCourseManager;
import com.example.administrator.zahbzayxy.manager.MyOnLineManager;
import com.example.administrator.zahbzayxy.manager.OffLineCourseManager;
import com.example.administrator.zahbzayxy.manager.OnLineManager;
import com.example.administrator.zahbzayxy.utils.FixedIndicatorView;
import com.example.administrator.zahbzayxy.utils.NumberFormatUtils;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-19.
 * Time 16:28.
 * 学习在线课
 */
public class MyOnLineCourseFragment extends Fragment{
    private View view;
    private FixedIndicatorView fixedIndicatorView;
    private Context context;
    private String token;
    private ProgressBarLayout mLoadingBar;
    private TextView tv_msg;
    private int mLearnType = 0;
    private MyOnLineManager mOnLineManager;
    private MyOffLineCourseManager mOffLineManager;
    private View mOneView,on_line_view_one;
    private RelativeLayout rl_empty;
    private boolean mLoadView = false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my_online_course,container,false);
        fixedIndicatorView =view.findViewById(R.id.singleTab_fixedIndicatorView);
        mLoadingBar= view.findViewById(R.id.load_bar_layout_evaluating);
        on_line_view_one=view.findViewById(R.id.on_line_view_one);//分割线
        mOneView = view.findViewById(R.id.on_line_view_one);
        rl_empty=view.findViewById(R.id.rl_empty_layout);//空布局
        tv_msg=view.findViewById(R.id.tv_msg);//空布局显示文字
        mOnLineManager = new MyOnLineManager(context, fixedIndicatorView, view);
        mOffLineManager = new MyOffLineCourseManager(context, view);
        mOnLineManager.setEmptyView(rl_empty);
        mOffLineManager.setEmptyView(rl_empty);
        mOffLineManager.setFragment(MyOnLineCourseFragment.this);
        loadData();
        mLoadView = true;
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        if (isVisibleToUser && mLoadView) {
            loadData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }
    private boolean isVisible;

    public void loadData() {
        if (!isVisible) return;
        if (mLearnType == 0 && mOnLineManager != null) {
            mOnLineManager.loadDAta(0);

            on_line_view_one.setVisibility(View.VISIBLE);
        } else if (mLearnType == 1 && mOnLineManager != null){
            mOnLineManager.loadDAta(1);

        }
    }

    public void setLearnType(int learnType) {
        this.mLearnType = learnType;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLearnType == 2) {
            mOffLineManager.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLearnType == 2) {
            mOffLineManager.onPause();
        }
    }


    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }
}
