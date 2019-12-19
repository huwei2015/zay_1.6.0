package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TestNavigationBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/6/6 0006.
 */
public class TestNavigationAdapter extends BaseAdapter {
    List<TestNavigationBean.DataBean>list;
    Context context;
    LayoutInflater inflater;
    private int selectorPos;
    public TestNavigationAdapter(List<TestNavigationBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }
    //设置ListView选中项位置
    public void setSelectorPos(int pos){
        this.selectorPos=pos;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestViewHold viewHold;
        if (convertView==null){
            viewHold=new TestViewHold();
            convertView=inflater.inflate(R.layout.item_lesson_navigation_layout,parent,false);
            viewHold.testNavigation_tv= (TextView) convertView.findViewById(R.id.navigationName_lesson_tv);
            convertView.setTag(viewHold);
        }else {
            viewHold= (TestViewHold) convertView.getTag();
        }
        String centerName = list.get(position).getCenterName();
        if (!TextUtils.isEmpty(centerName)){
            viewHold.testNavigation_tv.setText(centerName);
            if (selectorPos==position){
               // convertView.setBackgroundColor(context.getResources().getColor(R.color.lightBlue));
                viewHold.testNavigation_tv.setTextColor(context.getResources().getColor(R.color.lightBlue));
            } if(selectorPos!=position){
                viewHold.testNavigation_tv.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
        return convertView;
    }
    static class TestViewHold{
        TextView testNavigation_tv;
    }
}
