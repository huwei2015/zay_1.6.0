package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.AllFileBean;

import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-24.
 * Time 11:30.
 * 全部附件adpater
 */
public class AllFileAdapter extends RecyclerView.Adapter<AllFileAdapter.AllFileViewHodler> {
    private Context mContext;
    private List<AllFileBean.AllFileListBean> allFileListBeans;

    private onItemClickListener onItemClickListener;
    private onDelClickListener onDelClickListener;

    public void setOnDelClickListener(onDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }

    public void setOnItemCilkLiener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
        void OnItemCilck(View view, int position);
    }
    public interface onDelClickListener{
        void onDelClick(View view, int position);
    }

    public AllFileAdapter(Context mContext, List<AllFileBean.AllFileListBean> allFileListBeans) {
        this.mContext = mContext;
        this.allFileListBeans = allFileListBeans;
    }

    public void setList(List<AllFileBean.AllFileListBean> allFileListBeans) {
        this.allFileListBeans = allFileListBeans;
        notifyDataSetChanged();
    }

    public void addList(List<AllFileBean.AllFileListBean> allFileListBeans) {
        this.allFileListBeans.addAll(allFileListBeans);
        notifyDataSetChanged();
    }


    @Override
    public AllFileViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AllFileAdapter.AllFileViewHodler(LayoutInflater.from(mContext).inflate(R.layout.fragment_all_file_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AllFileViewHodler holder, final int position) {
       holder.tv_name.setText(allFileListBeans.get(position).getAttaName());
       holder.tv_time.setText(allFileListBeans.get(position).getCreateTime());
       holder.tv_size.setText(allFileListBeans.get(position).getAttaSize());
       if(allFileListBeans.get(position).getAttaFormat().equals("1")){
           holder.logo.setBackground(mContext.getResources().getDrawable(R.mipmap.img));
       }else if(allFileListBeans.get(position).getAttaFormat().equals("2")){
           holder.logo.setBackground(mContext.getResources().getDrawable(R.mipmap.word));
       }else if(allFileListBeans.get(position).getAttaFormat().equals("3")){
           holder.logo.setBackground(mContext.getResources().getDrawable(R.mipmap.excel));
       }else if(allFileListBeans.get(position).getAttaFormat().equals("4")){
           holder.logo.setBackground(mContext.getResources().getDrawable(R.mipmap.pdf));
       }
       holder.ll_itme.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onItemClickListener.OnItemCilck(v,position);
           }
       });
       holder.ll_del.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onDelClickListener.onDelClick(v,position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return allFileListBeans != null ? allFileListBeans.size() : 0;
    }

    static class AllFileViewHodler extends RecyclerView.ViewHolder {
        private ImageView logo;
        private TextView tv_name, tv_time, tv_size;
        private LinearLayout ll_itme,ll_del;
        public AllFileViewHodler(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.img_file_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_size = itemView.findViewById(R.id.tv_size);
            ll_del=itemView.findViewById(R.id.ll_del);
            ll_itme=itemView.findViewById(R.id.ll_itme);
        }
    }
}
