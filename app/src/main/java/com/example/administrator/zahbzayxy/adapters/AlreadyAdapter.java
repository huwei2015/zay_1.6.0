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
import com.example.administrator.zahbzayxy.beans.AlreadyBean;

import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 14:43.
 * 已通过adapter
 */
public class AlreadyAdapter extends RecyclerView.Adapter<AlreadyAdapter.NotPassViewHodler>{
    private Context mContext;
    private List<AlreadyBean.AlreadyListBean> alreadyListBeans;

    public AlreadyAdapter(Context mContext, List<AlreadyBean.AlreadyListBean> alreadyListBeans) {
        this.mContext = mContext;
        this.alreadyListBeans = alreadyListBeans;
    }

    @Override
    public NotPassViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlreadyAdapter.NotPassViewHodler(LayoutInflater.from(mContext).inflate(R.layout.item_alread,parent,false));
    }

    @Override
    public void onBindViewHolder(NotPassViewHodler holder, final int position) {
        holder.title.setText(alreadyListBeans.get(position).getTitle());
        holder.time.setText(alreadyListBeans.get(position).getTime());
        holder.exam_account.setText(alreadyListBeans.get(position).getAccount());
        holder.exam_record.setText(alreadyListBeans.get(position).getRecord());
        holder.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了考试入口"+position,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return alreadyListBeans !=null ? alreadyListBeans.size() :0;
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
