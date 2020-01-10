package com.example.administrator.zahbzayxy.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ChooseTopicActivity;
import com.example.administrator.zahbzayxy.activities.QueslibActivity;
import com.example.administrator.zahbzayxy.adapters.LearnNavigationAdapter;
import com.example.administrator.zahbzayxy.adapters.LearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.TestNavigationAdapter;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import com.example.administrator.zahbzayxy.beans.OnTransitionTextListener;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.beans.TestNavigationBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.manager.OnLineManager;
import com.example.administrator.zahbzayxy.utils.ColorBar;
import com.example.administrator.zahbzayxy.utils.DisplayUtil;
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
    private TextView tv_addTopic;
    private List<OnlineCourseBean.OnLineListBean> onLineListBeanList= new ArrayList<>();
    private int mLearnType = 0;
    private OnLineManager mOnLineManager;
    private boolean mIsShow;
    private CheckBox mFilterCb;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mIsShow = true;
        view=inflater.inflate(R.layout.fragment_online_course,container,false);
        fixedIndicatorView =view.findViewById(R.id.singleTab_fixedIndicatorView);
        mLoadingBar= view.findViewById(R.id.load_bar_layout_evaluating);
        recyclerview =view.findViewById(R.id.recyclerview);
        tv_addTopic=view.findViewById(R.id.tv_chooseTopic);//选择题库
        mFilterCb = view.findViewById(R.id.on_line_filter_course_check);
        tv_addTopic.setOnClickListener(this);
        img_add=view.findViewById(R.id.img_add);//添加题库
        img_add.setOnClickListener(this);
        mOnLineManager = new OnLineManager(context, fixedIndicatorView, recyclerview, mFilterCb);
        mOnLineManager.setLoadingView(mLoadingBar);
        loadData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        loadData();
    }

    private void loadData() {
        if (getUserVisibleHint() && mIsShow) {
            if (mLearnType == 0) {
                mOnLineManager.loadDAta();
            }
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
