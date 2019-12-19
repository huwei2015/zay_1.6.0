package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PMyTestGradBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/11 0011.
 */
public class PMyTestGradAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<PMyTestGradBean.DataBean.ExamScoresBean>list;
    public PMyTestGradAdapter(Context context, List<PMyTestGradBean.DataBean.ExamScoresBean> list) {
        this.context = context;
        this.list = list;
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
        MyGradViewHold viewHold=null;
        if (convertView==null){
            viewHold=new MyGradViewHold();
            convertView=inflater.inflate(R.layout.item_pmytest_grad_layout,parent,false);
            viewHold.name_tv= (TextView) convertView.findViewById(R.id.pGrad_testName_tv);
            viewHold.time_tv= (TextView) convertView.findViewById(R.id.pSumTime_tv);
            viewHold.score_tv= (TextView) convertView.findViewById(R.id.ptotalScore_tv);
            convertView.setTag(viewHold);
        }else {
            viewHold= (MyGradViewHold) convertView.getTag();
        }
        PMyTestGradBean.DataBean.ExamScoresBean examScoresBean = list.get(position);
        viewHold.name_tv.setText(examScoresBean.getPaperName());
        String useTime = examScoresBean.getUseTime();
        Log.e("userTime",useTime);
        viewHold.time_tv.setText(useTime);
        viewHold.score_tv.setText(String.valueOf(examScoresBean.getTotalScore()));
        return convertView;
    }
    static class MyGradViewHold{
        TextView name_tv,time_tv,score_tv;
    }
}
