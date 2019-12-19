package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.YouHuiJuanBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2018/4/25 0025.
 */

public class AllMyYouHuiJuanAdapter extends BaseAdapter {
    List<YouHuiJuanBean.DataEntity>list;
    Context context;
    LayoutInflater layoutInflater;

    public AllMyYouHuiJuanAdapter(List<YouHuiJuanBean.DataEntity> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position>0?position:0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.item_myyouhuijuan_layout,parent,false);
            viewHold=new ViewHold();
            viewHold.testName_tv= (TextView) convertView.findViewById(R.id.yhj_testName_tv);
            viewHold.useStatus_tv= (TextView) convertView.findViewById(R.id.yhj_useStatus_tv);
            viewHold.useDate_tv= (TextView) convertView.findViewById(R.id.yhj_useDate_tv);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ViewHold) convertView.getTag();
        }
        YouHuiJuanBean.DataEntity dataEntity = list.get(position);
        if (dataEntity!=null){
            String couponName = dataEntity.getCouponName();
            if (!TextUtils.isEmpty(couponName)){
                viewHold.testName_tv.setText(couponName);
            }
            Integer peroid = dataEntity.getPeroid();
            if (peroid!=null){
                viewHold.useDate_tv.setText("有效期"+peroid+"天");

            }

            int status = dataEntity.getStatus();
            if (Integer.valueOf(status)!=null){
                if (status==0){
                    viewHold.useStatus_tv.setText("未使用");
                    viewHold.useStatus_tv.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                }else if (status==1){
                    viewHold.useStatus_tv.setText("使用中");
                    viewHold.useStatus_tv.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                }else if (status==2){
                    viewHold.useStatus_tv.setText("已使用");
                }else if (status==3){
                    viewHold.useStatus_tv.setText("已过期");
                }
            }
        }
        return convertView;

    }
    static class ViewHold{
        ImageView youHuiJuan_iv;
        TextView testName_tv,useDate_tv,useStatus_tv;

    }
}
