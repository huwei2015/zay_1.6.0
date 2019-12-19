package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.LessonThiredActivity;
import com.example.administrator.zahbzayxy.beans.HomeLessonClassBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ${ZWJ} on 2018/1/17 0017.
 */
public class HomeLessonClassSecondAdapter extends BaseAdapter {
    List<HomeLessonClassBean.DataEntity.CoursesEntity> list;
    Context context;
    LayoutInflater inflater;

    public HomeLessonClassSecondAdapter(List<HomeLessonClassBean.DataEntity.CoursesEntity> list, Context context) {
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

        if (convertView==null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.item_lesson_second_gv_layout, parent, false);
            viewHold.lessonName_iv = (ImageView) convertView.findViewById(R.id.lessonName_iv);
            viewHold.lessonName_tv = (TextView) convertView.findViewById(R.id.lessonName_tv);
            viewHold.lessonNum_tv = (TextView) convertView.findViewById(R.id.lessonNum_tv);
            viewHold.lessonTeacher = (TextView) convertView.findViewById(R.id.lesssonTeacher_tv);
            convertView.setTag(viewHold);

        }else{
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeLessonClassBean.DataEntity.CoursesEntity coursesBean = list.get(position);
        String courseImagePath = coursesBean.getCourseImagePath();
        int courseHours = coursesBean.getCourseHours();
        String teacher = coursesBean.getTeacher();
        String courseName = coursesBean.getCourseName();
        if (!TextUtils.isEmpty(courseImagePath)){
            Picasso.with(context).load(courseImagePath).placeholder(R.mipmap.loading_png).into(viewHold.lessonName_iv);
        }
        viewHold.lessonNum_tv.setText(String.valueOf(courseHours)+"学时");
        viewHold.lessonTeacher.setText("讲师:"+teacher);
       viewHold.lessonName_tv.setText(courseName);
        final int id = coursesBean.getId();
        viewHold.lessonName_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, LessonThiredActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("courseId",id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
static class ViewHold{
    ImageView lessonName_iv;
    TextView lessonName_tv,lessonNum_tv,lessonTeacher;
}
}
