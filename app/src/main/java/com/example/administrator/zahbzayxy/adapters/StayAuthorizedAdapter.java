package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.StayAuthorizedBean;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-10-29.
 * Time 09:57.
 * 待授权
 */
public class StayAuthorizedAdapter extends RecyclerView.Adapter<StayAuthorizedAdapter.StayAuthorizedViewHodler> {
    private Context mContext;
    private List<StayAuthorizedBean.StayAuthorizedList> stayAuthorizedBeanList;

    @Override
    public StayAuthorizedViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StayAuthorizedViewHodler(LayoutInflater.from(mContext).inflate(R.layout.item_stayauthorized, parent, false));
    }

    @Override
    public void onBindViewHolder(StayAuthorizedViewHodler holder, int position) {
        holder.tv_orderNum.setText(stayAuthorizedBeanList.get(position).getOrder_number());
        holder.tv_orderContent.setText(stayAuthorizedBeanList.get(position).getOrder_content());
        holder.tv_authorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了授权",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stayAuthorizedBeanList != null ? stayAuthorizedBeanList.size() : 0;
    }

    static class StayAuthorizedViewHodler extends RecyclerView.ViewHolder {
        private TextView tv_orderNum, tv_orderContent,tv_authorization;

        public StayAuthorizedViewHodler(View itemView) {
            super(itemView);
            tv_orderNum = itemView.findViewById(R.id.tv_number);
            tv_orderContent = itemView.findViewById(R.id.tv_content);
            tv_authorization=itemView.findViewById(R.id.tv_authorization);
        }
    }
}
