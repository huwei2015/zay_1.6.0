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
import com.example.administrator.zahbzayxy.beans.OfflineCourseLearnBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 线下课adapter
 */
public class LearnOfflineCourseAdapter extends RecyclerView.Adapter<LearnOfflineCourseAdapter.OnLineCourseViewHodler>{
    private Context mContext;
    private List<OfflineCourseLearnBean.UserCoursesBean> onLineListBeans;
    private OnLearnOfflineItemClickListener mOnItemClickListener;

    public LearnOfflineCourseAdapter(Context mContext, List<OfflineCourseLearnBean.UserCoursesBean> onLineListBeans) {
        if (onLineListBeans == null) onLineListBeans = new ArrayList<>();
        this.mContext = mContext;
        this.onLineListBeans = onLineListBeans;
    }

    public void setData(List<OfflineCourseLearnBean.UserCoursesBean> data) {
        if (data == null) {
            data = new ArrayList<>();
        }

        this.onLineListBeans = data;
        notifyDataSetChanged();
    }

    @Override
    public LearnOfflineCourseAdapter.OnLineCourseViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LearnOfflineCourseAdapter.OnLineCourseViewHodler(LayoutInflater.from(mContext).inflate(R.layout.activity_week,parent,false));
    }

    @Override
    public void onBindViewHolder(LearnOfflineCourseAdapter.OnLineCourseViewHodler holder, int position) {
        OfflineCourseLearnBean.UserCoursesBean coursesBean = onLineListBeans.get(position);
        if (coursesBean == null) return;
        int timeState = coursesBean.getIsNew();
        if (position == 0) {
            holder.mStatusLayout.setVisibility(View.VISIBLE);
            holder.mStatusTv.setText(timeState == 1?"最新开课" : "更多");
            holder.mStateImg.setImageResource(timeState == 0?R.mipmap.one_week:R.mipmap.before_time);
        } else {
            int preState = onLineListBeans.get(position - 1).getIsNew();
            if (preState == timeState) {
                holder.mStatusLayout.setVisibility(View.GONE);
            } else {
                holder.mStatusLayout.setVisibility(View.VISIBLE);
                holder.mStatusTv.setText(timeState == 1?"最新开课" : "更多");
                holder.mStateImg.setImageResource(timeState == 0?R.mipmap.one_week:R.mipmap.before_time);
            }
        }

        holder.tv_title.setText(coursesBean.getCourseName());
        holder.tv_time.setText("开课时间：" + coursesBean.getClassTime());
        holder.tv_state.setText("上课地点：" + coursesBean.getClassAddress());

        // 是否认证   0：待认证   1：已认证
        int state = coursesBean.getState();
        if (state == 0) {
            holder.mConfirmTv.setText("学习进度：待认证");
            holder.mConfirmTv.setTextColor(Color.rgb(243, 92, 85));
        } else {
            holder.mConfirmTv.setTextColor(Color.rgb(44, 119, 243));
            holder.mConfirmTv.setText("学习进度：已认证");
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
        private ImageView pMyLesson_img, mStateImg;
        private TextView tv_title,tv_time,tv_state, mStatusTv, mConfirmTv;
        private LinearLayout mStatusLayout;
        private RelativeLayout mItemLayout;
        public OnLineCourseViewHodler(View itemView) {
            super(itemView);
            pMyLesson_img=itemView.findViewById(R.id.pMyLesson_img);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_state=itemView.findViewById(R.id.tv_state);
            mStatusLayout = itemView.findViewById(R.id.on_line_item_type_layout);
            mStatusTv = itemView.findViewById(R.id.on_line_item_type_tv);
            mStateImg = itemView.findViewById(R.id.on_line_item_type_img);
            mItemLayout = itemView.findViewById(R.id.medium_layout);
            mConfirmTv = itemView.findViewById(R.id.tv_confirm);
            mConfirmTv.setVisibility(View.VISIBLE);
        }
    }

    public void setOnLearnOfflineItemClickListener(OnLearnOfflineItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnLearnOfflineItemClickListener{
        void onClick(int position);
    }
}
