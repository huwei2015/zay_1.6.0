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
import com.example.administrator.zahbzayxy.beans.NotThroughBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 13:58.
 * 考试未通过adapter
 */
public class NotPassAdapter extends RecyclerView.Adapter<NotPassAdapter.NotPassViewHodler>{
    private Context mContext;
    private List<NotPassBean.NotListData> notPassListBeans;

    public NotPassAdapter(Context mContext, List<NotPassBean.NotListData> notPassListBeans) {
        this.mContext = mContext;
        this.notPassListBeans = notPassListBeans;
    }

    @Override
    public NotPassViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotPassAdapter.NotPassViewHodler(LayoutInflater.from(mContext).inflate(R.layout.item_not_pass,parent,false));
    }
    public void setList(List<NotPassBean.NotListData> notPassListBeans) {
        this.notPassListBeans = notPassListBeans;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(NotPassViewHodler holder, int position) {
        holder.title.setText(notPassListBeans.get(position).getQuesLibName());
        holder.time.setText(notPassListBeans.get(position).getEndTime());
        holder.lib_account.setText(String.valueOf(notPassListBeans.get(position).getQuesLibExamNum()));
        holder.exam_account.setText(String.valueOf(notPassListBeans.get(position).getUserExamNum()));
        if(notPassListBeans.get(position).getIsLimitCount() == 0){//无限次
            holder.tv_account.setText("无限次");
            holder.tv_account.setVisibility(View.VISIBLE);
            holder.lib_account.setVisibility(View.GONE);
            holder.tv_xie.setVisibility(View.GONE);
            holder.exam_account.setVisibility(View.GONE);

        }else if(notPassListBeans.get(position).getQuesLibExamNum() >= notPassListBeans.get(position).getUserExamNum()){//可以去考试

        }else if(notPassListBeans.get(position).getQuesLibExamNum() < notPassListBeans.get(position).getUserExamNum()){//不可以去考试

        }
        holder.exam_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了考试记录"+position,Toast.LENGTH_LONG).show();
            }
        });
        Picasso.with(mContext).load(notPassListBeans.get(position).getImagePath()).placeholder(R.mipmap.loading_png).into(holder.img_pic);

    }

    @Override
    public int getItemCount() {
        return notPassListBeans !=null ? notPassListBeans.size() : 0;
    }

    static class NotPassViewHodler extends RecyclerView.ViewHolder{
        ImageView img_pic;
        TextView title,state,time,exam_record,exam_account,lib_account,tv_xie,tv_account;
        public NotPassViewHodler(View itemView) {
            super(itemView);
            img_pic=itemView.findViewById(R.id.img_icon);
            title= itemView.findViewById(R.id.tv_title);
            time=itemView.findViewById(R.id.tv_time);
            exam_account=itemView.findViewById(R.id.tv_exam_account);//已考次数
            lib_account = itemView.findViewById(R.id.lib_exam_account);//考试总次数
            exam_record=itemView.findViewById(R.id.tv_exam_recode);
            state=itemView.findViewById(R.id.tv_state);
            tv_xie=itemView.findViewById(R.id.tv_xie);
            tv_account=itemView.findViewById(R.id.tv_account);
        }
    }
}
