package com.example.administrator.zahbzayxy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
 * Time 13:10.
 * 正在考试
 */
public class FormalExamFragment extends Fragment {
    private ViewPager exam_vp;
    private TabLayout exam_tab;
    private List<String> examTabList;
    private List<Fragment>examVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    FragmentManager fragmentManager;
    private View view;
    private boolean isVisible;
    private boolean mLoadView = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_formalexam,container,false);
        initView();
        mLoadView = true;
        initViewPagerAndTable();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i("======refresh=====", "formal exam fragment isVisibleToUser = " + isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser && mLoadView) {
            initViewPagerAndTable();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView() {
        exam_vp= view.findViewById(R.id.exam_vp);
        exam_tab= view.findViewById(R.id.exam_tab);
        exam_tab.setSelectedTabIndicatorHeight(0);//隐藏下划线
    }

    public void loadData(){
        initViewPagerAndTable();
    }

    private void initViewPagerAndTable() {
        if (!isVisible) return;
        examTabList=new ArrayList<>();
        examVPList=new ArrayList<>();
        examTabList.add("未通过");
        examTabList.add("已通过");
        NotPassFragment fileAllFragment=new NotPassFragment();
        AlreadyFragment noHaveUseFragment=new AlreadyFragment();
        examVPList.add(fileAllFragment);
        examVPList.add(noHaveUseFragment);
        fragmentManager=getChildFragmentManager();
        pagerAdapter=new LessonFragmentPageAdapter(fragmentManager,examVPList,examTabList);
        exam_vp.setAdapter(pagerAdapter);
        exam_tab.addTab(exam_tab.newTab().setText(examTabList.get(0)));
        exam_tab.addTab(exam_tab.newTab().setText(examTabList.get(1)));
        exam_tab.setupWithViewPager(exam_vp);
    }
}
