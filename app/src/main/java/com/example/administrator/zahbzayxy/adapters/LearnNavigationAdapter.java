package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-07.
 * Time 13:40.
 */
public class LearnNavigationAdapter extends RecyclerView.Adapter<LearnNavigationAdapter.LearnNavigationViewHodler> {
    private Context mContext;
    private List<LearnNavigationBean.LearnListBean> list;
    private int selectorPos;

    //设置ListView选中项位置
    public void setSelectorPos(int pos){
        this.selectorPos=pos;
    }
    public LearnNavigationAdapter(Context mContext, List<LearnNavigationBean.LearnListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public LearnNavigationViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LearnNavigationAdapter.LearnNavigationViewHodler(LayoutInflater.from(mContext).inflate(R.layout.item_lesson_navigation_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(LearnNavigationViewHodler holder, int position) {
        holder.testNavigation_tv.setText(list.get(position).getCateName());
        if(selectorPos == position){
            holder.testNavigation_tv.setTextColor(mContext.getResources().getColor(R.color.lightBlue));
        }
        if(selectorPos !=position){
            holder.testNavigation_tv.setTextColor(mContext.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return list !=null ? list.size():0;
    }


//    List<LearnNavigationBean.LearnListBean> list;
//    Context context;
//    LayoutInflater inflater;
//    private int selectorPos;
//    public LearnNavigationAdapter(List<LearnNavigationBean.LearnListBean> list, Context context) {
//        this.list = list;
//        this.context = context;
//        inflater= LayoutInflater.from(context);
//    }
//    //设置ListView选中项位置
//    public void setSelectorPos(int pos){
//        this.selectorPos=pos;
//    }
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        TestNavigationAdapter.TestViewHold viewHold;
//        if (convertView==null){
//            viewHold=new TestNavigationAdapter.TestViewHold();
//            convertView=inflater.inflate(R.layout.item_lesson_navigation_layout,parent,false);
//            viewHold.testNavigation_tv=  convertView.findViewById(R.id.navigationName_lesson_tv);
//            convertView.setTag(viewHold);
//        }else {
//            viewHold= (TestNavigationAdapter.TestViewHold) convertView.getTag();
//        }
//        String centerName =list.get(position).getCateName();
//        if (!TextUtils.isEmpty(centerName)){
//            viewHold.testNavigation_tv.setText(centerName);
//            if (selectorPos==position){
//                // convertView.setBackgroundColor(context.getResources().getColor(R.color.lightBlue));
//                viewHold.testNavigation_tv.setTextColor(context.getResources().getColor(R.color.lightBlue));
//            } if(selectorPos!=position){
//                viewHold.testNavigation_tv.setTextColor(context.getResources().getColor(R.color.black));
//            }
//        }
//        return convertView;
//    }
//    static class TestViewHold{
//        TextView testNavigation_tv;
//    }


    static class LearnNavigationViewHodler extends RecyclerView.ViewHolder{
            TextView testNavigation_tv;
        public LearnNavigationViewHodler(View itemView) {
            super(itemView);
            testNavigation_tv= itemView.findViewById(R.id.navigationName_lesson_tv);
        }
    }



}
