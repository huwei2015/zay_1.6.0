package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;
import com.example.administrator.zahbzayxy.fragments.NewAllOrderFragment;
import com.example.administrator.zahbzayxy.fragments.NewHaveCancleOrderFragment;
import com.example.administrator.zahbzayxy.fragments.NewHavePayOrderFragment;
import com.example.administrator.zahbzayxy.fragments.NewWaitPayOrderFragment;
import com.example.administrator.zahbzayxy.fragments.UserFragment;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 */
public class NewMyOrderActivity extends BaseActivity {
    private ViewPager nBOrderViewPager;
    private TabLayout nBOrderTabLayout;
    private List<String> nbTabList;
    private List<Fragment>nbOrderVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_my_order);
        initView();
        initViewPagerAndTable();
    }
    private void initViewPagerAndTable() {
        nbTabList=new ArrayList<>();
        nbOrderVPList=new ArrayList<>();
        nbTabList.add("全部订单");
        nbTabList.add("已支付");
        nbTabList.add("待支付");
        nbTabList.add("已取消");
        NewAllOrderFragment allOrderFragment=new NewAllOrderFragment();
        NewHavePayOrderFragment havePayOrderFragment=new NewHavePayOrderFragment();
        NewWaitPayOrderFragment waitPayOrderFragment=new NewWaitPayOrderFragment();
        NewHaveCancleOrderFragment haveCancleFragment=new NewHaveCancleOrderFragment();
        nbOrderVPList.add(allOrderFragment);
        nbOrderVPList.add(havePayOrderFragment);
        nbOrderVPList.add(waitPayOrderFragment);
        nbOrderVPList.add(haveCancleFragment);
        fragmentManager=getSupportFragmentManager();
        pagerAdapter=new LessonFragmentPageAdapter(fragmentManager,nbOrderVPList,nbTabList);
        nBOrderViewPager.setAdapter(pagerAdapter);
        nBOrderTabLayout.addTab(nBOrderTabLayout.newTab().setText(nbTabList.get(0)));
        nBOrderTabLayout.addTab(nBOrderTabLayout.newTab().setText(nbTabList.get(1)));
        nBOrderTabLayout.addTab(nBOrderTabLayout.newTab().setText(nbTabList.get(2)));
        nBOrderTabLayout.addTab(nBOrderTabLayout.newTab().setText(nbTabList.get(3)));
        nBOrderTabLayout.setupWithViewPager(nBOrderViewPager);
    }

    private void initView() {
        nBOrderViewPager= (ViewPager) findViewById(R.id.nB_order_vp);
        nBOrderTabLayout= (TabLayout) findViewById(R.id.nB_order_tab);

    }

    //点击返回
    public void myOrderBackOnClick(View view) {
        EventBus.getDefault().post(UserFragment.FLUSH_USER_INFO_MINE_PAGE);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        EventBus.getDefault().post(UserFragment.FLUSH_USER_INFO_MINE_PAGE);
        return super.onKeyDown(keyCode, event);
    }
}
