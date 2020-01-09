package com.example.administrator.zahbzayxy.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.MsgAdapter;
import com.example.administrator.zahbzayxy.beans.TimeData;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.TimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-13.
 * Time 11:13.
 */
public class MsgListActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private ImageView exam_archives_back;
    private TextView tab_unread_message;
    //存储列表数据
    List<TimeData> list = new ArrayList<>();
    MsgAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        initView();
        initData();
    }
    private void initView() {
        String messageNum = getIntent().getStringExtra("messageNum");
        recyclerView= (RecyclerView) findViewById(R.id.activity_rlview);
        exam_archives_back= (ImageView) findViewById(R.id.exam_archives_back);
        exam_archives_back.setOnClickListener(this);
        tab_unread_message= (TextView) findViewById(R.id.tab_unread_message);
        if(!TextUtils.isEmpty(messageNum)){
            int msg_count = Integer.parseInt(messageNum);
            if(msg_count > 0){
                tab_unread_message.setVisibility(View.VISIBLE);
                tab_unread_message.setText(messageNum);
            }
        }



        // 将数据按照时间排序
        TimeComparator comparator = new TimeComparator();
        Collections.sort(list, comparator);
        // recyclerview绑定适配器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MsgAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
    private void initData() {
        list.add(new TimeData("20170710", "中安华邦做客央视《超越》栏目中安华邦做客央视《超越》栏目中安华邦做客央视《超越》栏目"));
        list.add(new TimeData("20140709", "我是多数据模块第一个数据"));
        list.add(new TimeData("20140709", "我是多数据模块第二个数据"));
        list.add(new TimeData("20140708", "我是多数据模块第三个数据"));
        list.add(new TimeData("20140709", "我是多数据模块第一个数据"));
        list.add(new TimeData("20140708", "我是多数据模块第二个数据"));
        list.add(new TimeData("20140708", "我是多数据模块第三个数据"));
        list.add(new TimeData("20140706", "我是最后一个数据"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exam_archives_back:
                finish();
                break;
        }
    }
}
