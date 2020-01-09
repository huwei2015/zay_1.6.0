package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.adapter.ViewHolder;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TimeData;
import com.example.administrator.zahbzayxy.utils.DensityUtil;
import com.example.administrator.zahbzayxy.utils.TimeFormat;

import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-13.
 * Time 11:20.
 */
public class MsgAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<TimeData> data;

    public MsgAdapter(Context mContext, List<TimeData> data) {
        this.mContext = mContext;
        this.data = data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolderTitle extends RecyclerView.ViewHolder {
        private TextView tv_title;
        public ViewHolderTitle(View itemView) {
            super(itemView);
        }
    }

}
