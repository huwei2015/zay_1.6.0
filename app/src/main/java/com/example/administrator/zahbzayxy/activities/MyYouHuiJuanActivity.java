package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;
import com.example.administrator.zahbzayxy.fragments.HaveUsedFragment;
import com.example.administrator.zahbzayxy.fragments.NoHaveUseFragment;
import com.example.administrator.zahbzayxy.fragments.OutOfDateFragment;
import com.example.administrator.zahbzayxy.fragments.UsingFragment;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.zahbzayxy.R.id.myYouhuiJuan_tab;

public class MyYouHuiJuanActivity extends BaseActivity {
    private ViewPager myYouHuiJuan_vp;
    private TabLayout myYouHuiJuan_tab;
    private List<String> myYouHuiJuanTabList;
    private List<Fragment>myYouHuiJuanVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_you_hui_juan);
        initView();
        initViewPagerAndTable();
    }

    private void initViewPagerAndTable() {
        myYouHuiJuanTabList=new ArrayList<>();
        myYouHuiJuanVPList=new ArrayList<>();
        myYouHuiJuanTabList.add("未使用");
        myYouHuiJuanTabList.add("使用中");
        myYouHuiJuanTabList.add("已使用");
        myYouHuiJuanTabList.add("已过期");
        NoHaveUseFragment noHaveUseFragment=new NoHaveUseFragment();
        UsingFragment usingFragment=new UsingFragment();
        HaveUsedFragment haveUsedFragment=new HaveUsedFragment();
        OutOfDateFragment outOfDateFragment=new OutOfDateFragment();
        myYouHuiJuanVPList.add(noHaveUseFragment);
        myYouHuiJuanVPList.add(usingFragment);
        myYouHuiJuanVPList.add(haveUsedFragment);
        myYouHuiJuanVPList.add(outOfDateFragment);
        fragmentManager=getSupportFragmentManager();
        pagerAdapter=new LessonFragmentPageAdapter(fragmentManager,myYouHuiJuanVPList,myYouHuiJuanTabList);
        myYouHuiJuan_vp.setAdapter(pagerAdapter);
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(0)));
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(1)));
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(2)));
        myYouHuiJuan_tab.addTab(myYouHuiJuan_tab.newTab().setText(myYouHuiJuanTabList.get(3)));
        myYouHuiJuan_tab.setupWithViewPager(myYouHuiJuan_vp);
    }

    private void initView() {
        myYouHuiJuan_vp= (ViewPager) findViewById(R.id.myYouHuiJuan_vp);
        myYouHuiJuan_tab= (TabLayout) findViewById(myYouhuiJuan_tab);
    }

    public void myYouHuiJuanBackOnClick(View view) {
        finish();
    }
}
