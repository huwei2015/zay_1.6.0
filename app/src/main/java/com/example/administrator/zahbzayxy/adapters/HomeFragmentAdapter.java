package com.example.administrator.zahbzayxy.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 首页适配器
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment>fragments;
    List<String>titles;

    public HomeFragmentAdapter(FragmentManager fm, List<Fragment>list, List<String>titles) {
        super(fm);
        this.fragments=list;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
