package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ${ZWJ} on 2018/8/11 0011.
 */

public abstract class ListViewAdapter<T>extends BaseAdapter {
    //为了让子类访问，于是将属性设置为protected
     protected Context mContext;
     protected List<T> mDatas;
    protected LayoutInflater mInflater;
     private int layoutId; //不同的ListView的item布局肯能不同，所以要把布局单独提取出来

           public ListViewAdapter(Context context, List<T> datas, int layoutId) {
             this.mContext = context;
                mInflater = LayoutInflater.from(context);
              this.mDatas = datas;
            this.layoutId = layoutId;
            }

            @Override
    public int getCount() {
                return mDatas.size();
           }

          @Override
    public T getItem(int position) {
              return mDatas.get(position);
           }

            @Override
    public long getItemId(int position) {
           return position;
           }

          @Override
     public View getView(int position, View convertView, ViewGroup parent) {
              //初始化ViewHolder,使用通用的ViewHolder，一行代码就搞定ViewHolder的初始化咯
             ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);//layoutId就是单个item的布局

               convert(holder, getItem(position));
              return holder.getConvertView(); //这一行的代码要注意了
            }

         //将convert方法公布出去
           public abstract void convert(ViewHolder holder, T t);



    public static class ViewHolder {

             private SparseArray<View> mViews;
            private int mPosition;
            private View mConvertView;

             public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
                    this.mPosition = position;
                     this.mViews = new SparseArray<View>();

                   mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);

                     mConvertView.setTag(this);

                }

             public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
                    if (convertView == null) {
                             return new ViewHolder(context, parent, layoutId, position);
                        } else {
                            ViewHolder holder = (ViewHolder) convertView.getTag();
                             holder.mPosition = position; //即使ViewHolder是复用的，但是position记得更新一下
                             return holder;
                        }
                 }

           /*
39     通过viewId获取控件
40      */
             //使用的是泛型T,返回的是View的子类
             public <T extends View> T getView(int viewId) {
                     View view = mViews.get(viewId);

                    if (view == null) {
                            view = mConvertView.findViewById(viewId);
                             mViews.put(viewId, view);
                         }

                     return (T) view;
                }

             public View getConvertView() {
                     return mConvertView;
                 }

         }
}
