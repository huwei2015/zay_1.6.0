package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.LessonSecondActivity;
import com.example.administrator.zahbzayxy.beans.LessonNavigationBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/1/10 0010.
 */
public class LessonExpandLvAdapter extends BaseExpandableListAdapter {
   private List<LessonNavigationBean.DataBean.ChildBean>groupList;
    LayoutInflater mInflater;
    ExpandableListView mexpanded_lv;

    Context context;

    public LessonExpandLvAdapter(List<LessonNavigationBean.DataBean.ChildBean> groupList, Context context, ExpandableListView expand_lv) {

        this.groupList = groupList;
        this.context = context;
        mInflater=LayoutInflater.from(context);
        this.mexpanded_lv=expand_lv;
    }
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
       // return groupList.get(groupPosition).getChildList().get(childPosition);]
        return  groupList.get(groupPosition).getChild().get(childPosition);
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
        if (convertView == null) {
            mViewChild = new LessonGroupViewHold();
            convertView = mInflater.inflate(
                    R.layout.item_group_expande_layout,null);
            mViewChild.textView = (TextView) convertView
                    .findViewById(R.id.channel_group_name);
            mViewChild.expanded_iv= (ImageView) convertView.findViewById(R.id.expanded_iv);
            mViewChild.ti_iv= (ImageView) convertView.findViewById(R.id.ti);
            convertView.setTag(mViewChild);
        } else {
            mViewChild = (LessonGroupViewHold) convertView.getTag();
        }
        mViewChild.ti_iv.setVisibility(View.GONE);
        mViewChild.textView.setTextColor(Color.BLACK);
        mViewChild.textView.setText(groupList.get(groupPosition).getCateName());
        mViewChild.textView.setBackgroundColor(Color.WHITE);
      //  MarginUtils.setMargins(mViewChild.textView,200,200,200,200);
        //判断isExpanded就可以控制是按下还是关闭，同时更换图片
        if(isExpanded){//展开
           mViewChild.expanded_iv.setBackgroundResource(R.mipmap.close);

          //  mexpanded_lv.setDividerHeight(0);
         //   mexpanded_lv.setDivider(null);
        }else{//关闭
            mViewChild.expanded_iv.setBackgroundResource(R.mipmap.add);
          //  mexpanded_lv.setDividerHeight(20);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String zCatName = null;
        int size = groupList.get(groupPosition).getChild().size();
       // List<LessonNavigationBean.DataBean.ChildBean.ChildBean1> childList = groupList.get(groupPosition).getChild();
        //返回的子课程名称
        final int cateId=groupList.get(groupPosition).getCateId();
        final List<String>tagnames=new ArrayList<>();
        for (int i=0;i<size;i++){
             zCatName=groupList.get(groupPosition).getChild().get(i).getCateName();
            tagnames.add(zCatName);

        }
        final TagFlowLayout tagFlowLayout=new TagFlowLayout(context);
        tagFlowLayout.setAdapter(new TagAdapter(tagnames) {
            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        tagFlowLayout, false);
                tv.setText(o+"");
                return tv;
            }
        });
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
               String zCatName1=groupList.get(groupPosition).getChild().get(position).getCateName();
               int zCatId1=groupList.get(groupPosition).getChild().get(position).getCateId();
                Intent intent=new Intent(context, LessonSecondActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("zCatId", zCatId1);
                bundle.putInt("cateId",cateId);
                bundle.putString("zCateName", zCatName1);
                Log.e("idddddd","zCatId."+zCatId1+",,catId.."+cateId+",,zCateName."+zCatName1);
                intent.putExtras(bundle);
                context.startActivity(intent);
                return true;
            }
        });
        return tagFlowLayout;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
   LessonGroupViewHold mViewChild;
    static class LessonGroupViewHold{
        TextView textView ;
        ImageView expanded_iv, ti_iv;

    }

}

