package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ChooseTopicActivity;
import com.example.administrator.zahbzayxy.activities.QueslibActivity;
import com.example.administrator.zahbzayxy.adapters.LearnNavigationAdapter;
import com.example.administrator.zahbzayxy.adapters.LearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.manager.OffLineCourseManager;
import com.example.administrator.zahbzayxy.manager.OnLineManager;
import com.example.administrator.zahbzayxy.utils.FixedIndicatorView;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-19.
 * Time 16:28.
 * 学习在线课
 */
public class OnLineCourseFragment extends Fragment implements PullToRefreshListener,View.OnClickListener{
    private View view;
    private FixedIndicatorView fixedIndicatorView;
    private Context context;
    private String token;
    private ImageView img_add;
    private LearnNavigationAdapter adapter;
    private ProgressBarLayout mLoadingBar;
    private List<LearnNavigationBean.LearnListBean>navigationList=new ArrayList<>();
    private LearnOnlineCourseAdapter learnOnlineCourseAdapter;
    private PullToRefreshRecyclerView recyclerview;
    private TextView tv_addTopic,on_line_filter_course_check;
    private List<OnlineCourseBean.OnLineListBean> onLineListBeanList= new ArrayList<>();
    private int mLearnType = 0;
    private OnLineManager mOnLineManager;
    private OffLineCourseManager mOffLineManager;
    private CheckBox mFilterCb;
    private View mOneView,on_line_view_one;
    private RelativeLayout mSelectLayout;
    private boolean mLoadView = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_online_course,container,false);
        fixedIndicatorView =view.findViewById(R.id.singleTab_fixedIndicatorView);
        mLoadingBar= view.findViewById(R.id.load_bar_layout_evaluating);
        recyclerview =view.findViewById(R.id.recyclerview);
        tv_addTopic=view.findViewById(R.id.tv_chooseTopic);//选择题库
        on_line_filter_course_check=view.findViewById(R.id.on_line_filter_course_check);//过滤
        on_line_view_one=view.findViewById(R.id.on_line_view_one);//分割线
        mFilterCb = view.findViewById(R.id.on_line_filter_course_check);
        mOneView = view.findViewById(R.id.on_line_view_one);
        mSelectLayout = view.findViewById(R.id.on_line_select_layout);
        tv_addTopic.setOnClickListener(this);
        img_add=view.findViewById(R.id.img_add);//添加题库
        img_add.setOnClickListener(this);
            mOnLineManager = new OnLineManager(context, fixedIndicatorView, recyclerview, mFilterCb);
            mOffLineManager = new OffLineCourseManager(context, recyclerview);
        mOnLineManager.setLoadingView(mLoadingBar);
        mOffLineManager.setFragment(OnLineCourseFragment.this);
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
            tv_addTopic.setVisibility(View.GONE);
//            on_line_filter_course_check.setVisibility(View.GONE);
//            img_add.setVisibility(View.GONE);
//            on_line_view_one.setVisibility(View.GONE);
        } else if (mLearnType == 1 && mOnLineManager != null){
            mOnLineManager.loadDAta(1);
            tv_addTopic.setVisibility(View.GONE);
            img_add.setVisibility(View.GONE);
        } else if (mLearnType == 2 && mOffLineManager != null) {
            fixedIndicatorView.setVisibility(View.GONE);
            mOneView.setVisibility(View.GONE);
            mSelectLayout.setVisibility(View.GONE);
            recyclerview.setLoadingMoreEnabled(false);
            recyclerview.setPullRefreshEnabled(false);
            mOffLineManager.initData();
        }
    }

    public void setLearnType(int learnType) {
        this.mLearnType = learnType;
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_chooseTopic://选择题库
                startActivity(new Intent(getActivity(), ChooseTopicActivity.class));
                break;
            case R.id.img_add://添加题库
                startActivity(new Intent(getActivity(), QueslibActivity.class));
                break;
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
