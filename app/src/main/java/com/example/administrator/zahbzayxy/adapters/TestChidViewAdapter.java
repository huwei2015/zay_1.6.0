package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TestNavigationBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/27 0027.
 */
public class TestChidViewAdapter extends BaseAdapter {
    List<TestNavigationBean.DataBean.ChildBean.ChildBean1> list;
    private Context context;
    LayoutInflater inflater;
    int pCateId;

    public TestChidViewAdapter(List<TestNavigationBean.DataBean.ChildBean.ChildBean1> list, Context context,Integer pId) {
        this.list= list;
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.pCateId=pId;
    }

    @Override
    public int getCount() {
        int size = 0;
        if (list!=null) {
             size= list.size();
        }
        return size>0?size:0 ;
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
        chidViewHold chidViewHold;
        if (convertView==null){
            chidViewHold=new chidViewHold();
            convertView=inflater.inflate(R.layout.item_test_chid_layout,parent,false);
            chidViewHold.gridText_tv= (TextView) convertView.findViewById(R.id.chidGrid_tv);

            convertView.setTag(chidViewHold);
        }else {
            chidViewHold= (TestChidViewAdapter.chidViewHold) convertView.getTag();
        }
        final String cateName = list.get(position).getCateName();
        chidViewHold.gridText_tv.setText(cateName);

        return convertView;
    }




    static class chidViewHold{
        TextView gridText_tv;

    }
}
