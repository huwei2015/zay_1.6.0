package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.myviews.ImageRadiusView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-07.
 * Time 08:51.
 * 学习在线adapter
 */
public class MyLearnOnlineCourseAdapter extends RecyclerView.Adapter<MyLearnOnlineCourseAdapter.OnLineCourseViewHodler>{
    private Context mContext;
    private List<OnlineCourseBean.UserCoursesBean> onLineListBeans;
    private OnLearnOnlineItemClickListener mOnItemClickListener;

    public MyLearnOnlineCourseAdapter(Context mContext, List<OnlineCourseBean.UserCoursesBean> onLineListBeans) {
        if (onLineListBeans == null) onLineListBeans = new ArrayList<>();
        this.mContext = mContext;
        this.onLineListBeans = onLineListBeans;
    }

    public void setData(List<OnlineCourseBean.UserCoursesBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        this.onLineListBeans = data;
        notifyDataSetChanged();
    }

    @Override
    public MyLearnOnlineCourseAdapter.OnLineCourseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyLearnOnlineCourseAdapter.OnLineCourseViewHodler(LayoutInflater.from(mContext).inflate(R.layout.activity_my_course,parent,false));
    }

    @Override
    public void onBindViewHolder(MyLearnOnlineCourseAdapter.OnLineCourseViewHodler holder, int position) {
        OnlineCourseBean.UserCoursesBean coursesBean = onLineListBeans.get(position);
        if (coursesBean == null) return;

        holder.tv_title.setText(coursesBean.getCourseName());
        holder.tv_time.setText("到期时间：" + coursesBean.getEndDate());
        String percent = coursesBean.getLearnTimePercent();
        if ("100%".equals(percent) || "100.00%".equals(percent)) {
            holder.tv_state.setText("已完成");
            holder.tv_state.setTextColor(Color.rgb(153, 153, 153));
        } else {
            holder.tv_state.setText("学习至：" + percent);
            holder.tv_state.setTextColor(Color.rgb(243, 92, 85));
        }

        Picasso.with(mContext).load(coursesBean.getImagePath()).placeholder(R.mipmap.loading_png).into(holder.pMyLesson_img);

        holder.mItemLayout.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return onLineListBeans !=null ? onLineListBeans.size() : 0;
    }

    static class OnLineCourseViewHodler extends RecyclerView.ViewHolder{
        private ImageRadiusView pMyLesson_img;
        private TextView tv_title,tv_time,tv_state;
        private RelativeLayout mItemLayout;
        public OnLineCourseViewHodler(View itemView) {
            super(itemView);
            pMyLesson_img=itemView.findViewById(R.id.pMyLesson_img);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_state=itemView.findViewById(R.id.tv_state);
            mItemLayout = itemView.findViewById(R.id.medium_layout);
        }
    }

    public void setOnLearnOnlineItemClickListener(OnLearnOnlineItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnLearnOnlineItemClickListener{
        void onClick(int position);
    }
}
