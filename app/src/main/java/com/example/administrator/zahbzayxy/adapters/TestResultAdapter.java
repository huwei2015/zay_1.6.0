package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.GridItem;
import com.example.administrator.zahbzayxy.stickheadgv.StickyGridHeadersSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/28 0028.
 */
public class TestResultAdapter extends BaseAdapter implements
        StickyGridHeadersSimpleAdapter {

    private List<GridItem> list = new ArrayList<GridItem>();
    private LayoutInflater mInflater;

    private Context mContext;

    public TestResultAdapter(List<GridItem> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        mInflater=LayoutInflater.from(mContext);
    }
    @Override
    public long getHeaderId(int position) {
        return list.get(position).getSection();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder mHeaderHolder;

        if(convertView ==null) {

            mHeaderHolder =new HeaderViewHolder();

            convertView =mInflater.inflate(R.layout.header,parent, false);

            mHeaderHolder.mTextView= convertView.findViewById(R.id.header);

            convertView.setTag(mHeaderHolder);

        }else{
            mHeaderHolder = (HeaderViewHolder) convertView.getTag();
        }
        mHeaderHolder.mTextView.setText(list.get(position).getName());
        return convertView;

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
        ViewHolder mViewHolder;

        if(convertView ==null) {

            mViewHolder =new ViewHolder();

            convertView =mInflater.inflate(R.layout.grid_item,parent, false);

            mViewHolder.tv_griditem= (TextView) convertView.findViewById(R.id.tv_griditem);

            convertView.setTag(mViewHolder);

        }else{

            mViewHolder = (ViewHolder) convertView.getTag();

        }

        GridItem gridItem = list.get(position);
        String path1 = list.get(position).getPath();
        String[] split = path1.split(",");
        final String s = split[0];
        String s1 = split[1];
        if (s1.equals("1")){
            mViewHolder.tv_griditem.setBackgroundResource(R.mipmap.right_test1);
        }else {
            mViewHolder.tv_griditem.setBackgroundResource(R.mipmap.wrong_test1);
        }
        mViewHolder.tv_griditem.setText(s);//gridItem


        return convertView;
    }
    public static class HeaderViewHolder {

        public TextView mTextView;

    }
    public static class ViewHolder {

        public TextView tv_griditem;

    }
}
