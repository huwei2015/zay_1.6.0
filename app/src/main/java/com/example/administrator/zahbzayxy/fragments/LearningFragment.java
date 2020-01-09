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

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-19.
 * Time 11:15.
 * 学习
 */
public class LearningFragment extends Fragment {
    private ViewPager learnViewPager;
    private TabLayout learnTabLayout;
    private List<String> learnTabList;
    private List<Fragment>learnVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    FragmentManager fragmentManager;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_learning,container,false);
        initView();
        initViewPagerAndTable();
        return view;
    }
    private void initView() {
        learnTabLayout=view.findViewById(R.id.learn_tab);
        learnViewPager=view.findViewById(R.id.learn_vp);
    }
    private void initViewPagerAndTable() {
        learnTabList=new ArrayList<>();
        learnVPList=new ArrayList<>();
        learnTabList.add("在线课");
        learnTabList.add("线下课");
        learnTabList.add("离线课");
        OnLineCourseFragment allOrderFragment=new OnLineCourseFragment();
        OnLineCourseFragment allOrderFragment1=new OnLineCourseFragment();
        OnLineCourseFragment allOrderFragment2=new OnLineCourseFragment();
        allOrderFragment.setLearnType(0);
        allOrderFragment1.setLearnType(1);
        allOrderFragment2.setLearnType(2);
        learnVPList.add(allOrderFragment);
        learnVPList.add(allOrderFragment1);
        learnVPList.add(allOrderFragment2);
        fragmentManager=getChildFragmentManager();
        pagerAdapter=new LessonFragmentPageAdapter(fragmentManager,learnVPList,learnTabList);
        learnViewPager.setAdapter(pagerAdapter);
        learnTabLayout.addTab(learnTabLayout.newTab().setText(learnTabList.get(0)));
        learnTabLayout.addTab(learnTabLayout.newTab().setText(learnTabList.get(1)));
        learnTabLayout.addTab(learnTabLayout.newTab().setText(learnTabList.get(2)));
        learnTabLayout.setupWithViewPager(learnViewPager);
    }
}
