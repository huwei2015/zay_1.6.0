package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;
import com.example.administrator.zahbzayxy.fragments.SearchJungleListFragment;
import com.example.administrator.zahbzayxy.fragments.SearchListSingleFragment;
import com.example.administrator.zahbzayxy.fragments.SearchMultiListFragment;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

    //搜索结果
public class SearchListActivity extends BaseActivity {
    Unbinder unbinder;
    List<Fragment> fragments = new ArrayList<>();
    SearchListSingleFragment searchListSingleFragment;
    SearchMultiListFragment searchMultiListFragment;
    SearchJungleListFragment searchJungleListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        unbinder = ButterKnife.bind(this);
        initViewPager();

    }


    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("单选");
        titles.add("多选");
        titles.add("判断");
        for (int i = 1; i <= titles.size(); i++) {
            if (i == 1) {//单选
                fragments.add(new SearchListSingleFragment());
            } else if (i == 2) {//多选
                fragments.add(new SearchMultiListFragment());
            } else if (i == 3) {//判断
                fragments.add(new SearchJungleListFragment());
            }

        }
        LessonFragmentPageAdapter adatper = new LessonFragmentPageAdapter(getSupportFragmentManager(), fragments, titles);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adatper);
        viewPager.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager关联起来。
        final XTabLayout tabLayout = (XTabLayout) findViewById(R.id.xTablayout);
        tabLayout.setupWithViewPager(viewPager);
        //给TabLayout设置适配器
        tabLayout.setupWithViewPager(viewPager);
    }


    @OnClick({R.id.close_searchResult_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_searchResult_iv:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
