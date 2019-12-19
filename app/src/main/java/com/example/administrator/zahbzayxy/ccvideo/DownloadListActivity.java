package com.example.administrator.zahbzayxy.ccvideo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DownloadListActivity extends BaseActivity {
    @BindView(R.id.download_tab)
    TabLayout download_tab;
    @BindView(R.id.download_vp)
    ViewPager download_vp;
    @BindView(R.id.nb_order_return)
    ImageView img_back;
    private LessonFragmentPageAdapter pageAdapter;
    private List<String> titlesList=new ArrayList<>();
    private List<Fragment>fragmentList=new ArrayList<>();
    FragmentManager fragmentManager;
    private Unbinder bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        bind = ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titlesList.add("已完成");
        titlesList.add("下载中");
        DownloadedFragment downloadedFragment=new DownloadedFragment();
        DownloadingFragment downloadingFragment=new DownloadingFragment();
        fragmentList.add(downloadedFragment);
        fragmentList.add(downloadingFragment);
        fragmentManager=getSupportFragmentManager();
        pageAdapter=new LessonFragmentPageAdapter(fragmentManager,fragmentList,titlesList);
        download_vp.setAdapter(pageAdapter);
        download_tab.addTab(download_tab.newTab().setText(titlesList.get(0)));
        download_tab.addTab(download_tab.newTab().setText(titlesList.get(1)));
        download_tab.setupWithViewPager(download_vp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind!=null){
            bind.unbind();
        }
    }
}
