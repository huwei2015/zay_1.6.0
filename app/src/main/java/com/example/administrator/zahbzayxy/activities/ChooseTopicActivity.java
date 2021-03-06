package com.example.administrator.zahbzayxy.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;
import com.example.administrator.zahbzayxy.fragments.ChooseNoThroughFragment;
import com.example.administrator.zahbzayxy.fragments.ChooseThroughFragment;
import com.example.administrator.zahbzayxy.fragments.OnLineCourseFragment;
import com.example.administrator.zahbzayxy.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-01.
 * Time 12:40.
 * 添加题库
 */
public class ChooseTopicActivity extends BaseActivity implements  ChooseNoThroughFragment.OnParamsClickListener,ChooseThroughFragment.OnParamsClickListener {
    private ViewPager learnViewPager;
    private TabLayout learnTabLayout;
    private List<String> learnTabList;
    private List<Fragment>learnVPList;
    private LessonFragmentPageAdapter pagerAdapter;
    private ImageView img_back;
    private String question_id;//题库id
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        initView();
        initViewPagerAndTable();
    }

    private void initView() {
        question_id=getIntent().getStringExtra("id");
        learnTabLayout= (TabLayout) findViewById(R.id.chooseTop_tab);
        learnViewPager= (ViewPager) findViewById(R.id.chooseTop_vp);
        img_back= (ImageView) findViewById(R.id.myChengJiBack_iv);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViewPagerAndTable() {
        learnTabList=new ArrayList<>();
        learnVPList=new ArrayList<>();
        learnTabList.add("未过期");
        learnTabList.add("已过期");
        ChooseNoThroughFragment chooseNoThroughFragment=new ChooseNoThroughFragment();
        ChooseThroughFragment chooseThroughFragment=new ChooseThroughFragment();
        chooseNoThroughFragment.setParamse(Integer.parseInt(question_id));//传值
        chooseNoThroughFragment.setOnParamsClickListener(this);
        chooseThroughFragment.setParamse(Integer.parseInt(question_id));
        chooseThroughFragment.setOnParamsClickListener(this);
        learnVPList.add(chooseNoThroughFragment);
        learnVPList.add(chooseThroughFragment);
        fragmentManager=getSupportFragmentManager();
        pagerAdapter=new LessonFragmentPageAdapter(fragmentManager,learnVPList,learnTabList);
        learnViewPager.setAdapter(pagerAdapter);
        learnTabLayout.addTab(learnTabLayout.newTab().setText(learnTabList.get(0)));
        learnTabLayout.addTab(learnTabLayout.newTab().setText(learnTabList.get(1)));
        learnTabLayout.setupWithViewPager(learnViewPager);
    }

    @Override
    public void onClick(int userLibId) {
        Intent intent = new Intent();
        intent.putExtra("userLibId",userLibId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
