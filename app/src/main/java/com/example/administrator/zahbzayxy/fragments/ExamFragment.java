package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
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
 * Data 2019-12-20.
 * Time 11:47.
 * 考试fragment
 */
public class ExamFragment extends Fragment {
    private ViewPager examViewPager;
    private TabLayout examTabLayout;
    private View view;
    private List<String> examTabList;
    private List<Fragment>examVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    FragmentManager fragmentManager;
    Context context;
    SimulationFragment allOrderFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exam,container,false);
        initView();
        initViewPagerAndTable();
        return view;
    }
    private void initView() {
        examTabLayout=view.findViewById(R.id.exam_tab);
        examViewPager=view.findViewById(R.id.exam_vp);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && allOrderFragment != null) {
            allOrderFragment.loadData();
        }
    }

    private void initViewPagerAndTable() {
        examTabList=new ArrayList<>();
        examVPList=new ArrayList<>();
        examTabList.add("模拟考试");
        examTabList.add("正在考试");
        allOrderFragment=new SimulationFragment();
        FormalExamFragment allOrderFragment1=new FormalExamFragment();
        examVPList.add(allOrderFragment);
        examVPList.add(allOrderFragment1);
        fragmentManager=getChildFragmentManager();
        pagerAdapter=new LessonFragmentPageAdapter(fragmentManager,examVPList,examTabList);
        examViewPager.setAdapter(pagerAdapter);
        examTabLayout.addTab(examTabLayout.newTab().setText(examTabList.get(0)));
        examTabLayout.addTab(examTabLayout.newTab().setText(examTabList.get(1)));
        examTabLayout.setupWithViewPager(examViewPager);
    }
}
