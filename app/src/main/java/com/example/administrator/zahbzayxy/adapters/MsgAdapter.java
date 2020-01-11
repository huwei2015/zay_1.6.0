package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.adapter.ViewHolder;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TimeData;
import com.example.administrator.zahbzayxy.utils.DensityUtil;
import com.example.administrator.zahbzayxy.utils.TimeFormat;

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
        this.data =data;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.msg_content,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_title.setText(data.get(position).getTitle());
        holder.tv_time.setText(data.get(position).getNewCreateTime());
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
        private TextView tv_title,tv_time;
        private RelativeLayout rl_item;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            rl_item=itemView.findViewById(R.id.rl_item);
        }
    }

}
