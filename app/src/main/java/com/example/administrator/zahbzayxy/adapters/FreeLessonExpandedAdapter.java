package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PMyLessonPlayBean;
import com.example.administrator.zahbzayxy.myinterface.MyInterface;
import com.example.administrator.zahbzayxy.myviews.MyExpandableLV;

import java.util.List;

/**
 * Created by huwei.
 * Data 2020-03-13.
 * Time 18:15.
 */
public class FreeLessonExpandedAdapter extends BaseExpandableListAdapter {
    public FreeLesonSecondExPandedAdapter currentAdapter;//记录当前选中的适配器
    public int currentGroupPosition = 0;//记录当前选中的适配器位置
    public int currentChildPosition = 0;//记录当前选中的适配器位置
    public int currentRootPosition = 0;//最顶层位置
    LayoutInflater mInflater;
    Context context;
    MyInterface.ItemClickedListener itemClickedListener;
    List<PMyLessonPlayBean.DataBean.ChildCourseListBean> list;
    public int getSelectionId;
    private int courseId;
    private String userCourseId;
    private String mImagePath;

    public FreeLessonExpandedAdapter(MyInterface.ItemClickedListener itemClickedListener, Context context, List<PMyLessonPlayBean.DataBean.ChildCourseListBean> list, int selectionId, int courseId, String userCourseId, String imagePath) {
        this.context = context;
        this.list = list;
        this.itemClickedListener = itemClickedListener;
        mInflater = LayoutInflater.from(context);
        getSelectionId = selectionId;
        this.courseId = courseId;
        this.userCourseId = userCourseId;
        this.mImagePath = imagePath;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.e("gxj-child-count", "size-" + list.get(groupPosition).getChapterList().size() + "");
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getChapterList().get(childPosition);
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
        View view = mInflater.inflate(R.layout.item_plesson_expanded_layout, parent, false);
        //子课程学时
        TextView zLessonNum_tv = (TextView) view.findViewById(R.id.zLessonNum_tv);
        PMyLessonPlayBean.DataBean.ChildCourseListBean childCourseListBean = list.get(groupPosition);

        int courseHours = childCourseListBean.getChildCourseHours();
        ImageView group_navegation_iv = (ImageView) view.findViewById(R.id.group_lesson_navagation_iv);

        zLessonNum_tv.setText("本课程" + courseHours + "学时");
        //groupName
        TextView tv1 = (TextView) view.findViewById(R.id.lesson_group_name_tv);
        //购买
        tv1.setText(childCourseListBean.getChildCourseName());

        if (isExpanded) {//展开
            group_navegation_iv.setImageResource(R.mipmap.open_up);
        } else {
            group_navegation_iv.setImageResource(R.mipmap.close_down);
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MyExpandableLV listView = new MyExpandableLV(context);
        final List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean> chapterList = list.get(groupPosition).getChapterList();
        final FreeLesonSecondExPandedAdapter adapter = new FreeLesonSecondExPandedAdapter(listView, groupPosition, this, new MyInterface.ItemClickedListener() {
            @Override
            public void onMyItemClickedListener(String vidioId, int videoIndex, int selectionId, double playPercent, String lessonName, int selectionIdack, int startPlaytTime, List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> list) {
                //切换视频点击事件
                itemClickedListener.onMyItemClickedListener(vidioId, videoIndex, selectionId, playPercent, lessonName, selectionIdack, startPlaytTime, list);
            }
        }, chapterList, context, getSelectionId, courseId, userCourseId, mImagePath);
        listView.setGroupIndicator(null);
        listView.setAdapter(adapter);
        return listView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
