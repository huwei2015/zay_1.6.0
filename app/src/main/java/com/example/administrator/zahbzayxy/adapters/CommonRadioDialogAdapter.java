package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LoginBean;

import java.util.List;

/**
 * Created by huwei.
 * Data 2020-03-30.
 * Time 20:38.
 */
public class CommonRadioDialogAdapter extends RecyclerView.Adapter<CommonRadioDialogAdapter.CommonRadioDialogViewHodel> {
    private Context mContext;
    private List<LoginBean.DataList> modelList;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
    public void setOnItemClickListener(CommonRadioDialogAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CommonRadioDialogAdapter(Context mContext, List<LoginBean.DataList> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @Override
    public CommonRadioDialogViewHodel onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonRadioDialogViewHodel(LayoutInflater.from(mContext).inflate(R.layout.item_dialog, parent, false));
    }

    @Override
    public void onBindViewHolder(CommonRadioDialogViewHodel holder,final int position) {
        holder.hintText.setText(modelList.get(position).getPlatformName());
        holder.rb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(v, position);
            }
        });
        holder.rl_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;
    }

    class CommonRadioDialogViewHodel extends RecyclerView.ViewHolder {
        private  RadioButton rb_check;
        private  TextView hintText;
        private RelativeLayout rl_onclick;
//        private final ImageView imgback;

        public CommonRadioDialogViewHodel(View itemView) {
            super(itemView);
            hintText = itemView.findViewById(R.id.tv_name);
            rb_check = itemView.findViewById(R.id.rb_check);
            rl_onclick=itemView.findViewById(R.id.rl_onclick);
        }
    }
}
