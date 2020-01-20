package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.NotThroughBean;
import com.example.administrator.zahbzayxy.beans.TimeData;

import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-02.
 * Time 15:48.
 * 学习未通过
 */
public class NotThrougAdapter extends RecyclerView.Adapter<NotThrougAdapter.ViewHodler>{
    private Context mcontext;
    private List<NotThroughBean.THrougListData> througListBeans;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public NotThrougAdapter(Context mcontext, List<NotThroughBean.THrougListData> througListBeans) {
        this.mcontext = mcontext;
        this.througListBeans = througListBeans;
    }

    public void setList(List<NotThroughBean.THrougListData> througListBeans) {
        this.througListBeans = througListBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotThrougAdapter.ViewHodler(LayoutInflater.from(mcontext).inflate(R.layout.item_fragment_no_through,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position) {
        holder.title.setText(througListBeans.get(position).getQuesLibName());
        holder.tv_type.setText(througListBeans.get(position).getPackageName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return througListBeans !=null ? througListBeans.size() : 0;
    }

    static class ViewHodler extends RecyclerView.ViewHolder{
        private TextView title,tv_type;
        public ViewHodler(View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.title);
            tv_type=itemView.findViewById(R.id.tv_title);
        }
    }
}
