package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.NotPassBean;

import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 13:58.
 * 考试未通过adapter
 */
public class NotPassAdapter extends RecyclerView.Adapter<NotPassAdapter.NotPassViewHodler>{
    private Context mContext;
    private List<NotPassBean.NotPassListBean> notPassListBeans;

    public NotPassAdapter(Context mContext, List<NotPassBean.NotPassListBean> notPassListBeans) {
        this.mContext = mContext;
        this.notPassListBeans = notPassListBeans;
    }

    @Override
    public NotPassViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotPassAdapter.NotPassViewHodler(LayoutInflater.from(mContext).inflate(R.layout.item_not_pass,parent,false));
    }

    @Override
    public void onBindViewHolder(NotPassViewHodler holder, final int position) {
        holder.title.setText(notPassListBeans.get(position).getTitle());
        holder.time.setText(notPassListBeans.get(position).getTime());
        holder.exam_account.setText(notPassListBeans.get(position).getAccount());
        holder.state.setText(notPassListBeans.get(position).getState());
        holder.exam_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了考试记录"+position,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notPassListBeans !=null ? notPassListBeans.size() : 0;
    }

    static class NotPassViewHodler extends RecyclerView.ViewHolder{
        ImageView img_pic;
        TextView title,state,time,exam_record,exam_account;
        public NotPassViewHodler(View itemView) {
            super(itemView);
            img_pic=itemView.findViewById(R.id.img_icon);
            title= itemView.findViewById(R.id.tv_title);
            time=itemView.findViewById(R.id.tv_time);
            exam_account=itemView.findViewById(R.id.tv_exam_account);
            exam_record=itemView.findViewById(R.id.tv_exam_recode);
            state=itemView.findViewById(R.id.tv_state);
        }
    }
}
