package com.example.administrator.zahbzayxy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的课程
 */
public class MyCouseFragment extends Fragment {
    private ViewPager learnViewPager;
    private TabLayout learnTabLayout;
    private List<String> learnTabList;
    private List<Fragment>learnVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    FragmentManager fragmentManager;
    private View view;
    private TextView goBackPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my_learning,container,false);
        initView();
        initViewPagerAndTable();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && learnVPList != null && learnVPList.size() > 0) {
            // 当前fragment展示的时候
            for (Fragment fragment : learnVPList){
                if (fragment == null) return;
                OnLineCourseFragment mf = (OnLineCourseFragment) fragment;
                mf.loadData();
            }
        }
    }

    private void initView() {
        learnTabLayout=view.findViewById(R.id.learn_tab);
        learnViewPager=view.findViewById(R.id.learn_vp);
        goBackPage=view.findViewById(R.id.goBackPage);
        goBackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onParamsClickListener.onClick();
            }
        });
    }
    private void initViewPagerAndTable() {
        learnTabList=new ArrayList<>();
        learnVPList=new ArrayList<>();
        learnTabList.add("在线课学习");
        learnTabList.add("线下课学习");
        MyOnLineCourseFragment allOrderFragment=new MyOnLineCourseFragment();
        MyOnLineCourseFragment allOrderFragment1=new MyOnLineCourseFragment();
        allOrderFragment.setLearnType(0);
        allOrderFragment1.setLearnType(1);
        learnVPList.add(allOrderFragment);
        learnVPList.add(allOrderFragment1);
        fragmentManager=getChildFragmentManager();
        pagerAdapter=new LessonFragmentPageAdapter(fragmentManager,learnVPList,learnTabList);
        learnViewPager.setAdapter(pagerAdapter);
        learnTabLayout.addTab(learnTabLayout.newTab().setText(learnTabList.get(0)));
        learnTabLayout.addTab(learnTabLayout.newTab().setText(learnTabList.get(1)));
        learnTabLayout.setupWithViewPager(learnViewPager);
    }


    //设置接口的方法
    public void setOnParamsClickListener(MyCouseFragment.OnParamsClickListener onParamsClickListener){
        this.onParamsClickListener = onParamsClickListener;
    }
    //定义变量
    private MyCouseFragment.OnParamsClickListener onParamsClickListener;
    /**
     * 当title被点击时，将title传递出去
     */
    //定义接口
    public interface OnParamsClickListener {
        void onClick();
    }
}
