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

public class StickyGridAdapter extends BaseAdapter implements
        StickyGridHeadersSimpleAdapter {

    private List<GridItem> list = new ArrayList<GridItem>();
    private LayoutInflater mInflater;

    private Context mContext;
    private MyRecyclerView myRecyclerView;
    private PopupWindow popupWindow;
    private TestContentAdapter adapter;
    private int numSize;
    private TextView dijige;

    public StickyGridAdapter(Context context, List list, MyRecyclerView recyclerView,PopupWindow popupWindow,TestContentAdapter adapter,Integer size,TextView dijige) {
        this.mContext=context;
        this.list= list;
        mInflater= LayoutInflater.from(context);
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
        if (s1.equals("1")){//已做
            mViewHolder.tv_griditem.setBackgroundResource(R.drawable.shape_right);
            mViewHolder.tv_griditem.setTextColor(mContext.getResources().getColor(R.color.lightBlue));

        }else {//未做
            mViewHolder.tv_griditem.setBackgroundResource(R.drawable.shape_no_done);
            mViewHolder.tv_griditem.setTextColor(mContext.getResources().getColor(R.color.gray_tv));

        }

        mViewHolder.tv_griditem.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if (adapter == null) return;
                Integer integer = Integer.valueOf(s);
                adapter.setChildPosition(-1);
                adapter.setKeChildPosition(-1);
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
