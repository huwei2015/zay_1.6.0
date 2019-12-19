package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.newtest.TopicController;
import com.example.administrator.zahbzayxy.newtest.TopicFragmentCallBacks;

public class TestNewActivity extends AppCompatActivity {
    private ViewPager test_vp;
    private TopicController tc;
    private TopicFragmentCallBacks topicFragmentCallBacks;
    private int size=5;
    private int mode;
    private int subClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new);
        initView();
    }

    private void initView() {
        test_vp= (ViewPager) findViewById(R.id.test_pager);
        tc = new TopicController(this, mode, subClass);
        topicFragmentCallBacks = getTopicFragmentCallBacks();
        test_vp.setAdapter(getPagerAdapter());
    }
    private FragmentPagerAdapter getPagerAdapter() {
        FragmentPagerAdapter fpa = tc.getPagerAdapter(
                getSupportFragmentManager(), topicFragmentCallBacks);
        fpa.notifyDataSetChanged();
        return fpa;
    }
    private TopicFragmentCallBacks getTopicFragmentCallBacks() {
        return new TopicFragmentCallBacks() {

            @Override
            public void snapToScreen(int position) {
                // TODO Auto-generated method stub
                test_vp.setCurrentItem(position);
            }
        };
    }

    public void rightButton(View view) {
        int page = test_vp.getCurrentItem();
        test_vp.setCurrentItem(page+1);
        if (page==size-1){
            Toast.makeText(TestNewActivity.this, "已经是最后一题了", Toast.LENGTH_SHORT).show();
        }


    }

    public void leftButton(View view) {
        int page = test_vp.getCurrentItem();
        test_vp.setCurrentItem(page-1);
        if (page==0){
            Toast.makeText(TestNewActivity.this, "已经是第一题了", Toast.LENGTH_SHORT).show();
        }


    }

}
