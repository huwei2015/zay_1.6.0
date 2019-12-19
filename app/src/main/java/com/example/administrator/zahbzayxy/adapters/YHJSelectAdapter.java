package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.YouHuiJuanListBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2018/4/26 0026.
 */

public class YHJSelectAdapter extends BaseAdapter {
    List<YouHuiJuanListBean.DataEntity.CouponListEntity>list;
    Context context;
    LayoutInflater inflater;

    public YHJSelectAdapter(List<YouHuiJuanListBean.DataEntity.CouponListEntity> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size()>0?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        YouHuiJuanListBean.DataEntity.CouponListEntity couponListEntity = list.get(position);
        String couponName = couponListEntity.getCouponName();
        if (!TextUtils.isEmpty(couponName)) {
            holder.tv_name.setText(couponName);
        }
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView tv_name;
    }
}
