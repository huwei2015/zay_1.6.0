package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.HomeLessonClassBean;
import com.example.administrator.zahbzayxy.myviews.MyGridView;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/9/8 0008.
 */
public class HomeLessonClassAdapter extends BaseAdapter {
    private List<HomeLessonClassBean.DataEntity>list;
    private Context context;
    LayoutInflater inflater;

    public HomeLessonClassAdapter(List<HomeLessonClassBean.DataEntity> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
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
        ViewHold viewHold=null;
        if (convertView==null){
            viewHold=new ViewHold();
            convertView=inflater.inflate(R.layout.item_home_lesson_class1,parent,false);
            viewHold.zyfzr_tv= (TextView) convertView.findViewById(R.id.zyfzr_tv1);
            viewHold.lessonDeatil_gv= (MyGridView) convertView.findViewById(R.id.home_item_classDetail_gv);
            convertView.setTag(viewHold);
        }else{
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeLessonClassBean.DataEntity dataBean = list.get(position);
        String cateName = dataBean.getCateName();
        viewHold.zyfzr_tv.setText(cateName);
        List<HomeLessonClassBean.DataEntity.CoursesEntity> courses = dataBean.getCourses();
        if (courses.size()>0) {
            HomeLessonClassSecondAdapter adapter=new HomeLessonClassSecondAdapter(courses,context);
            viewHold.lessonDeatil_gv.setAdapter(adapter);
        }
        return convertView;
    }
   private static class ViewHold{
        TextView zyfzr_tv;
       MyGridView lessonDeatil_gv;
    }

}
