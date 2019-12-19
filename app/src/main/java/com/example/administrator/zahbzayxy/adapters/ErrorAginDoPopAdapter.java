package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.GridItem;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;
import com.example.administrator.zahbzayxy.stickheadgv.StickyGridHeadersSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/28 0028.
 */
public class ErrorAginDoPopAdapter extends BaseAdapter implements
        StickyGridHeadersSimpleAdapter {
    private List<GridItem> list = new ArrayList<GridItem>();
    Context context;
    LayoutInflater mInflater;
    private MyRecyclerView myRecyclerView;
    private PopupWindow popupWindow;
    private AginDoErrorAdapter adapter;
    private int numSize;
    private TextView dijige;

    public ErrorAginDoPopAdapter(List<GridItem> list, Context context, MyRecyclerView recyclerView, PopupWindow popupWindow, AginDoErrorAdapter adapter, Integer size, TextView dijige) {
        this.list = list;
        this.context = context;
        mInflater=LayoutInflater.from(context);
        this.myRecyclerView=recyclerView;
        this.popupWindow=popupWindow;
        this.adapter=adapter;
        this.numSize=size;
        this.dijige=dijige;
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

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder;

        if(convertView ==null) {

            mViewHolder =new ViewHolder();

            convertView =mInflater.inflate(R.layout.grid_item,parent, false);

            mViewHolder.tv_griditem= (TextView) convertView.findViewById(R.id.tv_griditem);

            convertView.setTag(mViewHolder);

        }else{

            mViewHolder = (ViewHolder) convertView.getTag();

        }

        String path =list.get(position).getPath();
        String[] split = path.split(",");
        final String s = split[0];
        String s1 = split[1];

        mViewHolder.tv_griditem.setText(s);//gridItem
        if (s1.equals("2")){//作对
            mViewHolder.tv_griditem.setBackgroundResource(R.drawable.shape_right);
            mViewHolder.tv_griditem.setTextColor(context.getResources().getColor(R.color.lightBlue));

        }else if (s1.equals("1")){//做错
            mViewHolder.tv_griditem.setBackgroundResource(R.drawable.shape_wrong);
            mViewHolder.tv_griditem.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));

        }else{//没做
            mViewHolder.tv_griditem.setBackgroundResource(R.drawable.shape_no_done);
            mViewHolder.tv_griditem.setTextColor(context.getResources().getColor(R.color.testBlack));

        }

        mViewHolder.tv_griditem.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Integer integer = Integer.valueOf(s);
                myRecyclerView.scrollToPosition(integer-1);
                adapter.notifyDataSetChanged();
                dijige.setText((integer)+"/"+numSize);
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }

        });

        return convertView;

    }

    @Override

    public View getHeaderView(final int position, View convertView, ViewGroup parent) {

        HeaderViewHolder mHeaderHolder;

        if(convertView ==null) {

            mHeaderHolder =new HeaderViewHolder();

            convertView =mInflater.inflate(R.layout.header,parent, false);

            mHeaderHolder.mTextView= (TextView) convertView.findViewById(R.id.header);

            convertView.setTag(mHeaderHolder);

        }else{
            mHeaderHolder = (HeaderViewHolder) convertView.getTag();
        }
        mHeaderHolder.mTextView.setText(list.get(position).getName());



        return convertView;

    }

    public static class ViewHolder {

        public TextView tv_griditem;

    }

    public static class HeaderViewHolder {

        public TextView mTextView;

    }

    @Override

    public long getHeaderId(int position) {

        return list.get(position).getSection() ;

    }



}


