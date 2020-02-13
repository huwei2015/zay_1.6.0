package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.os.Trace;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TimeData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-13.
 * Time 11:20.
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private Context mContext;
    private List<TimeData.MsgList> data;
    private onClickItemListener onClickItemListener;
    public interface onClickItemListener{
        void onClick(View view,int position);
    }

    public void setOnClickItemListener(MsgAdapter.onClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public MsgAdapter(Context mContext, List<TimeData.MsgList> data) {
        this.mContext = mContext;
        this.data = data;
    }
    public void setList(List<TimeData.MsgList> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addList(List<TimeData.MsgList> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.msg_content,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        String str=data.get(position).getPeriod();//日期
        if(position>0){
            String str2=data.get(position-1).getPeriod();
            if(str.equals(str2)){
                holder.tv_time.setVisibility(View.GONE);
                holder.tvDot.setBackground(null);
                itemHolder.tvBcLine.setVisibility(View.VISIBLE);
            }else{
                holder.tv_time.setVisibility(View.VISIBLE);
                holder.tv_time.setText(str);
                holder.tvDot.setBackground(mContext.getResources().getDrawable(R.drawable.timelline_dot_normal));
                itemHolder.tvBcLine.setVisibility(View.GONE);
            }
        }else{
            holder.tv_time.setText(str);
            holder.tv_time.setVisibility(View.VISIBLE);
            holder.tvDot.setBackground(mContext.getResources().getDrawable(R.drawable.timelline_dot_normal));
            itemHolder.tvBcLine.setVisibility(View.GONE);
        }
        if (position == 0) {
            // 第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            itemHolder.tvBcLine.setVisibility(View.GONE);
        }else{
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
        }
        holder.tv_title.setText(data.get(position).getTitle());
        if(data.get(position).isyRead() == true){
            holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.price_color));
        }else{
            holder.tv_title.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size():0;
    }

   static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title,tv_time,tvTopLine,tvDot,tvBcLine;
        private RelativeLayout rl_item;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            rl_item=itemView.findViewById(R.id.rl_item);
            tvTopLine=itemView.findViewById(R.id.tvTopLine);
            tvDot=itemView.findViewById(R.id.tvDot);
            tvBcLine=itemView.findViewById(R.id.tvBcLine);
        }

    }

}
