package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.TestLiberaryListActivity;
import com.example.administrator.zahbzayxy.beans.TestNavigationBean;
import com.example.administrator.zahbzayxy.myviews.MyGridView;

import java.util.List;

/**
 * Created by Administrator on 2017/1/15 0015.
 */
public class TestExpandedAdapter extends BaseExpandableListAdapter {
   private List<TestNavigationBean.DataBean.ChildBean>list;
    private Context context;
    LayoutInflater mInflater;

    public TestExpandedAdapter(List<TestNavigationBean.DataBean.ChildBean>list,Context context) {
      this.list=list;
        this.context = context;
        mInflater=LayoutInflater.from(context);
    }


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
       // return list.get(groupPosition).getSubCates().get(childPosition);
        return list.get(groupPosition).getChild().get(childPosition);
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(
                   R.layout.item_group_expande_layout,parent,false);
       TextView textView = (TextView) convertView
                   .findViewById(R.id.channel_group_name);
           ImageView expanded_iv= (ImageView) convertView.findViewById(R.id.expanded_iv);
        ViewChild mViewChild=null;
//        if (convertView == null) {
//            mViewChild = new ViewChild();
//            convertView = mInflater.inflate(
//                    R.layout.item_group_expande_layout,parent,false);
//            mViewChild.textView = (TextView) convertView
//                    .findViewById(R.id.channel_group_name);
//            mViewChild.expanded_iv= (ImageView) convertView.findViewById(R.id.expanded_iv);
//            convertView.setTag(mViewChild);
//        } else {
//            mViewChild = (ViewChild) convertView.getTag();
//        }

        TestNavigationBean.DataBean.ChildBean childBean = list.get(groupPosition);
        final String cateName = childBean.getCateName();
      //  mViewChild.textView.setText(cateName);
        textView.setText(cateName);
        final int cateId = childBean.getCateId();
        int size = list.size();
        for (int i=0;i<size;i++){
            List<TestNavigationBean.DataBean.ChildBean.ChildBean1> child1 = list.get(i).getChild();
            if (child1==null){
                final int finalI = i;
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TestLiberaryListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("pCateId", cateId);
                        bundle.putInt("subCateId", cateId);
                        bundle.putString("cateName", cateName);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        Log.e("iiiii", finalI +"");
                    }
                });
            }
        }


        if(isExpanded){
           expanded_iv.setBackgroundResource(R.mipmap.close);

        }else{
            expanded_iv.setBackgroundResource(R.mipmap.add);
        }
        return  convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.e("postion",groupPosition+","+childPosition);

        ChidViewHold viewHold=null;
        if (convertView==null){
            viewHold=new ChidViewHold();
            convertView=mInflater.inflate(R.layout.item_test_chid_grid__layout,parent,false);
            viewHold.gridView= (GridView) convertView.findViewById(R.id.test_chid_gv);
            convertView.setTag(viewHold);

        }else {
           viewHold= (ChidViewHold) convertView.getTag();
        }
        final List<TestNavigationBean.DataBean.ChildBean.ChildBean1> subCates = list.get(groupPosition).getChild();

        MyGridView gridView=new MyGridView(context);
        gridView.setNumColumns(2);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(10);
        final int pCateId = list.get(groupPosition).getCateId();

        //if (subCates!=null) {
            viewHold.gridView.setAdapter(new TestChidViewAdapter(subCates, context, pCateId));
      //  }
        viewHold.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestNavigationBean.DataBean.ChildBean.ChildBean1 childBean1 = subCates.get(position);
                if (childBean1 != null) {
                    int cateId = childBean1.getCateId();
                    String cateName = childBean1.getCateName();
                    if (Integer.valueOf(cateId) != null && !TextUtils.isEmpty(cateName)) {
                        Intent intent = new Intent(context, TestLiberaryListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("pCateId", pCateId);
                        bundle.putInt("subCateId", cateId);
                        bundle.putString("cateName", cateName);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            }
        });
      //  }

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    static class ViewChild {
        TextView textView;
        ImageView expanded_iv;

    }
    static class  ChidViewHold{
        GridView gridView;
    }

}
