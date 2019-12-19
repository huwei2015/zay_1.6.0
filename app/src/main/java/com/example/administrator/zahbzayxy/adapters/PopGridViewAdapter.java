package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.SaveUserErrorPrcticeBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/2/20 0020.
 */
public class PopGridViewAdapter extends BaseAdapter {
    List<SaveUserErrorPrcticeBean.ErrorQuesBean> list;
    Context context;
    LayoutInflater inflater;

    public PopGridViewAdapter(List<SaveUserErrorPrcticeBean.ErrorQuesBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.resultadapter_item,parent,false);
        TextView tv = (TextView) view.findViewById(R.id.result_tv);
        tv.setText((position+1)+"");
        tv.setTextColor(Color.BLACK);
        int isRight = list.get(position).getIsRight();
        if (isRight == 1) {
            tv.setBackgroundResource(R.drawable.shape_wrong);
            tv.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
        }else if (isRight == 2) {
            tv.setBackgroundResource(R.drawable.shape_right);
            tv.setTextColor(context.getResources().getColor(R.color.greenRightTv));
        }else {
            tv.setBackgroundResource(R.drawable.shape_no_done);
            tv.setTextColor(context.getResources().getColor(R.color.gray_tv));

        }
        return view;
    }

    }




