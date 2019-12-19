package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.HasAuthorBean;

import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 15:39.
 * 已授权adapter
 */
public class HasAuthorAdapter extends RecyclerView.Adapter<HasAuthorAdapter.HasAuthorViewHodler>{
    private Context mContext;
    private List<HasAuthorBean.HasAuthorList> hasAuthorLists;

    public HasAuthorAdapter(Context mContext, List<HasAuthorBean.HasAuthorList> hasAuthorLists) {
        this.mContext = mContext;
        this.hasAuthorLists = hasAuthorLists;
    }

    @Override
    public HasAuthorViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HasAuthorViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hasauthor, parent, false));
    }

    @Override
    public void onBindViewHolder(HasAuthorViewHodler holder, final int position) {
        holder.order_num.setText(hasAuthorLists.get(position).getOrder_num());
        holder.title.setText(hasAuthorLists.get(position).getTitle());
        holder.tv_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了"+position,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return hasAuthorLists !=null ? hasAuthorLists.size():0;
    }

    static class HasAuthorViewHodler extends RecyclerView.ViewHolder{
        private TextView order_num;
        private TextView title;
        private TextView tv_author;
        public HasAuthorViewHodler(View itemView) {
            super(itemView);
            order_num=itemView.findViewById(R.id.order_num);
            title=itemView.findViewById(R.id.title);
            tv_author=itemView.findViewById(R.id.tv_author);
        }
    }
}
