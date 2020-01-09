package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-07.
 * Time 08:51.
 * 学习在线adapter
 */
public class LearnOnlineCourseAdapter extends RecyclerView.Adapter<LearnOnlineCourseAdapter.OnLineCourseViewHodler>{
    private Context mContext;
    private List<OnlineCourseBean.OnLineListBean> onLineListBeans;

    public LearnOnlineCourseAdapter(Context mContext, List<OnlineCourseBean.OnLineListBean> onLineListBeans) {
        if (onLineListBeans == null) onLineListBeans = new ArrayList<>();
        this.mContext = mContext;
        this.onLineListBeans = onLineListBeans;
    }

    public void setData(List<OnlineCourseBean.OnLineListBean> data) {
        if (data == null) data = new ArrayList<>();
        this.onLineListBeans = data;
        notifyDataSetChanged();
    }

    @Override
    public LearnOnlineCourseAdapter.OnLineCourseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LearnOnlineCourseAdapter.OnLineCourseViewHodler(LayoutInflater.from(mContext).inflate(R.layout.activity_week,parent,false));
    }

    @Override
    public void onBindViewHolder(LearnOnlineCourseAdapter.OnLineCourseViewHodler holder, int position) {
        holder.pMyLesson_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.mipmap.lesson_name1));
        holder.tv_title.setText(onLineListBeans.get(position).getTitle());
        holder.tv_time.setText(onLineListBeans.get(position).getTime());
        holder.tv_state.setText(onLineListBeans.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return onLineListBeans !=null ? onLineListBeans.size() : 0;
    }

    static class OnLineCourseViewHodler extends RecyclerView.ViewHolder{
        private ImageView pMyLesson_img;
        private TextView tv_title,tv_time,tv_state;
        public OnLineCourseViewHodler(View itemView) {
            super(itemView);
            pMyLesson_img=itemView.findViewById(R.id.pMyLesson_img);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_state=itemView.findViewById(R.id.tv_state);
        }
    }
}
