package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.ExamBean;
import com.example.administrator.zahbzayxy.beans.SignBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 10:34.
 * 我的报名adapter
 */
public class MySignAdapter extends RecyclerView.Adapter<MySignAdapter.SignViewHodler>{
    private Context mContext;
    private List<SignBean.SignListBean> signListBeans;
    private onItemClickListener onItemClickListener;
    public interface onItemClickListener{
       void onClick(View view,int position);
    }

    public void setOnItemClickListener(MySignAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MySignAdapter(Context mContext, List<SignBean.SignListBean> signListBeans) {
        this.mContext = mContext;
        this.signListBeans = signListBeans;
    }

    public void setList(List<SignBean.SignListBean> signListBeans) {
        this.signListBeans = signListBeans;
        notifyDataSetChanged();
    }

    public void addList(List<SignBean.SignListBean> signListBeans) {
        this.signListBeans.addAll(signListBeans);
        notifyDataSetChanged();
    }



    @Override
    public SignViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignViewHodler(LayoutInflater.from(mContext).inflate(R.layout.sign_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SignViewHodler holder, final int position) {
        holder.tv_sign_title.setText(signListBeans.get(position).getActivityName());
        holder.tv_time.setText(signListBeans.get(position).getApplyTime());
       holder.tv_detail.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onItemClickListener.onClick(v,position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return signListBeans !=null ? signListBeans.size():0;
    }

    class SignViewHodler extends RecyclerView.ViewHolder{
        private TextView tv_sign_title;
        private TextView tv_time;
        private TextView tv_detail;
        public SignViewHodler(View itemView) {
            super(itemView);
            tv_sign_title=itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_detail=itemView.findViewById(R.id.tv_detail);
        }
    }
}
