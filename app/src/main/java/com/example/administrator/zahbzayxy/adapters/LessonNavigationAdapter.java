package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LessonNavigationBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/6/6 0006.
 */
public class LessonNavigationAdapter extends BaseAdapter {
    List<LessonNavigationBean.DataBean>list;
    Context context;
    LayoutInflater inflater;
    private int selectorPos;
    public LessonNavigationAdapter(List<LessonNavigationBean.DataBean> list, Context context) {
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

        LessonViewHold viewHold=null;
        LessonNavigationBean.DataBean dataBean = list.get(position);
        if (convertView==null){
            viewHold=new LessonViewHold();
            convertView=inflater.inflate(R.layout.item_lesson_navigation_layout,parent,false);
            viewHold.name_navigation= (TextView) convertView.findViewById(R.id.navigationName_lesson_tv);
            convertView.setTag(viewHold);

        }else {
           viewHold= (LessonViewHold) convertView.getTag();
        }
        String centerName = dataBean.getCenterName();
        if (!TextUtils.isEmpty(centerName)) {
            viewHold.name_navigation.setText(centerName);
            if (selectorPos==position){
                convertView.setBackgroundColor(Color.WHITE);
            }else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.light_Gray));
            }
        }

        return convertView;
    }
    static class LessonViewHold{
        TextView name_navigation;
    }
}
