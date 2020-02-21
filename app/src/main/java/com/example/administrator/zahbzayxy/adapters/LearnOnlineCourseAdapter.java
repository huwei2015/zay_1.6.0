package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ShowImgActivity;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.squareup.picasso.Picasso;

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
    private List<OnlineCourseBean.UserCoursesBean> onLineListBeans;
    private OnLearnOnlineItemClickListener mOnItemClickListener;

    public LearnOnlineCourseAdapter(Context mContext, List<OnlineCourseBean.UserCoursesBean> onLineListBeans) {
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

//    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }

    @Override
    public LearnOnlineCourseAdapter.OnLineCourseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LearnOnlineCourseAdapter.OnLineCourseViewHodler(LayoutInflater.from(mContext).inflate(R.layout.activity_week,parent,false));
    }

    @Override
    public void onBindViewHolder(LearnOnlineCourseAdapter.OnLineCourseViewHodler holder, int position) {
        OnlineCourseBean.UserCoursesBean coursesBean = onLineListBeans.get(position);
        if (coursesBean == null) return;
        int timeState = coursesBean.getTimeState();
        if (position == 0) {
            holder.mStatusLayout.setVisibility(View.VISIBLE);
            holder.mStatusTv.setText(timeState == 0?"一周内" : "更早");
            holder.mStateImg.setImageResource(timeState == 0?R.mipmap.one_week:R.mipmap.before_time);
        } else {
            int preState = onLineListBeans.get(position - 1).getTimeState();
            if (preState == timeState) {
                holder.mStatusLayout.setVisibility(View.GONE);
            } else {
                holder.mStatusLayout.setVisibility(View.VISIBLE);
                holder.mStatusTv.setText(timeState == 0?"一周内" : "更早");
                holder.mStateImg.setImageResource(timeState == 0?R.mipmap.one_week:R.mipmap.before_time);
            }
        }

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
        holder.mFinishTv.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return onLineListBeans !=null ? onLineListBeans.size() : 0;
    }

    static class OnLineCourseViewHodler extends RecyclerView.ViewHolder{
        private ImageView pMyLesson_img, mStateImg;
        private TextView tv_title,tv_time,tv_state, mStatusTv, mFinishTv;
        private LinearLayout mStatusLayout;
        private RelativeLayout mItemLayout;
        public OnLineCourseViewHodler(View itemView) {
            super(itemView);
            pMyLesson_img=itemView.findViewById(R.id.pMyLesson_img);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_state=itemView.findViewById(R.id.tv_state);
            mStatusLayout = itemView.findViewById(R.id.on_line_item_type_layout);
            mItemLayout = itemView.findViewById(R.id.medium_layout);
            mStatusTv = itemView.findViewById(R.id.on_line_item_type_tv);
            mStateImg = itemView.findViewById(R.id.on_line_item_type_img);
            mFinishTv = itemView.findViewById(R.id.tv_confirm);
        }
    }

    public void setOnLearnOnlineItemClickListener(OnLearnOnlineItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnLearnOnlineItemClickListener{
        void onClick(int position);
    }
}
