package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.SignBean;

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

    public MySignAdapter(Context mContext, List<SignBean.SignListBean> signListBeans) {
        this.mContext = mContext;
        this.signListBeans = signListBeans;
    }

    @Override
    public SignViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SignViewHodler holder, final int position) {
        holder.tv_sign_title.setText(signListBeans.get(position).getTitle());
        holder.tv_time.setText(signListBeans.get(position).getTime());
        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了"+position,Toast.LENGTH_LONG).show();
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
