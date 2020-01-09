package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.StayAuthorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 16:37.
 * 待授权
 */
public class StayAuthorAdapter extends RecyclerView.Adapter<StayAuthorAdapter.StayAuthorViewHodler>{
    Context mContext;
    private List<StayAuthorBean.StayAuthBeanList> stayAuthorLists;

    public StayAuthorAdapter(Context mContext, List<StayAuthorBean.StayAuthBeanList> stayAuthorLists) {
        this.mContext = mContext;
        this.stayAuthorLists = stayAuthorLists;
    }
    public StayAuthorAdapter(Context context) {
        mContext=context;
        stayAuthorLists = new ArrayList<>();
    }

    public void setList(List<StayAuthorBean.StayAuthBeanList> stayAuthorLists){
        this.stayAuthorLists= stayAuthorLists;
        notifyDataSetChanged();
    }
    public void addList(List<StayAuthorBean.StayAuthBeanList> stayAuthorLists){
        this.stayAuthorLists = stayAuthorLists;
        notifyDataSetChanged();
    }
    @Override
    public StayAuthorAdapter.StayAuthorViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StayAuthorAdapter.StayAuthorViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_stayauthor, parent, false));
    }

    @Override
    public void onBindViewHolder(StayAuthorViewHodler holder, final int position) {
        holder.order_num.setText(stayAuthorLists.get(position).getOrderNumber());
        holder.title.setText(stayAuthorLists.get(position).getContent());
    }
    @Override
    public int getItemCount() {
        return stayAuthorLists !=null ? stayAuthorLists.size():0;
    }

    static class StayAuthorViewHodler extends RecyclerView.ViewHolder{
        private TextView order_num;
        private TextView title;
        private TextView tv_author;
        public StayAuthorViewHodler(View itemView) {
            super(itemView);
            order_num=itemView.findViewById(R.id.order_num);
            title=itemView.findViewById(R.id.title);
            tv_author=itemView.findViewById(R.id.tv_author);
        }
    }
}
