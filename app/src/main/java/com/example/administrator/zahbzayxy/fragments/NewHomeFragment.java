package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ScanQRCodeActivity;
import com.example.administrator.zahbzayxy.adapters.HomeFragmentAdapter;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * HYY 导航 20200203
 */
public class NewHomeFragment extends Fragment {
    private ViewPager homeViewPager;
    private TabLayout homeTabLayout;
    private View view;
    private List<String> homeTabList;
    private List<Fragment>homeVPList;
    private HomeFragmentAdapter pagerAdapter;
    FragmentManager fragmentManager;

    private ImageView scanCodeIV;
    private RelativeLayout indexNav;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_nav,container,false);
        initView();
        initViewPagerAndTable();
        return view;
    }

    private void initView() {
        homeTabLayout=view.findViewById(R.id.home_tab);
        homeViewPager=view.findViewById(R.id.home_vp);
        scanCodeIV=view.findViewById(R.id.scanCode);
        scanCodeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(view.getContext(), ScanQRCodeActivity.class);
                Bundle bundle=new Bundle();
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        indexNav=view.findViewById(R.id.indexNav);
    }
    private void initViewPagerAndTable() {
        homeTabList=new ArrayList<>();
        homeVPList=new ArrayList<>();
        homeTabList.add("首页");
        homeTabList.add("在线课");
        homeTabList.add("线下课");
        homeTabList.add("直播课");
        homeTabList.add("题库");
        homeTabList.add("书籍");
        NavIndexFragment nv1=new NavIndexFragment();
        nv1.setNavIndexFragment(indexNav);
        NavOnlineCourseFragment nv2=new NavOnlineCourseFragment();
        NavOfflineCourseFragment nv3=new NavOfflineCourseFragment();
        NavLiveCourseFragment nv4=new NavLiveCourseFragment();
        NavQueslibFragment nv5=new NavQueslibFragment();
        NavBooksFragment nv6=new NavBooksFragment();
        homeVPList.add(nv1);
        homeVPList.add(nv2);
        homeVPList.add(nv3);
        homeVPList.add(nv4);
        homeVPList.add(nv5);
        homeVPList.add(nv6);
        fragmentManager=getChildFragmentManager();
        pagerAdapter=new HomeFragmentAdapter(fragmentManager,homeVPList,homeTabList);
        homeViewPager.setAdapter(pagerAdapter);
        homeTabLayout.addTab(homeTabLayout.newTab().setText(homeTabList.get(0)));
        homeTabLayout.addTab(homeTabLayout.newTab().setText(homeTabList.get(1)));
        homeTabLayout.addTab(homeTabLayout.newTab().setText(homeTabList.get(2)));
        homeTabLayout.addTab(homeTabLayout.newTab().setText(homeTabList.get(3)));
        homeTabLayout.addTab(homeTabLayout.newTab().setText(homeTabList.get(4)));
        homeTabLayout.addTab(homeTabLayout.newTab().setText(homeTabList.get(5)));
        homeTabLayout.setupWithViewPager(homeViewPager);
        homeTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}







