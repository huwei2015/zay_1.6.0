package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LessonThiredBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/2/6 0006.
 */
public class ThirdLessonSecondEXAdapter extends BaseExpandableListAdapter {
   private List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean.ChapterlistBean>list;

    LayoutInflater mInflater;
    Context context;

    public ThirdLessonSecondEXAdapter(List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean.ChapterlistBean> list, Context context) {
       this.list=list;
        this.context = context;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
       // return list.get(groupPosition).getChapterlist().get(childPosition);
        return list.get(groupPosition).getSectionList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view=mInflater.inflate(R.layout.item_expanded_iv_layout,parent,false);
        TextView lessonName_tv= (TextView) view.findViewById(R.id.ziLessonName_tv);
        ImageView expanded_iv= (ImageView) view.findViewById(R.id.ziLesson_iv);
        String chapterName = list.get(groupPosition).getChapterName();
        if (!TextUtils.isEmpty(chapterName)){
            lessonName_tv.setText(chapterName);
        }
        if (isExpanded){
            expanded_iv.setImageResource(R.mipmap.open_up);
        }else {
            expanded_iv.setImageResource(R.mipmap.close_down);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

       // tv.setText(list.get(groupPosition).getSectionList().get(childPosition).getSelectionName());
        List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean.ChapterlistBean.SectionListBean> sectionList = list.get(groupPosition).getSectionList();
        int size = sectionList.size();

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10,10,10,10);
        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i=0;i<size;i++){
            TextView tv = new TextView(context);
            tv.setTextSize(14);
            tv.setLayoutParams(lp);
            tv.setPadding(15,10,15,10);
            tv.setText(sectionList.get(i).getSelectionName());
            linearLayout.addView(tv);
        }
        return linearLayout;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
