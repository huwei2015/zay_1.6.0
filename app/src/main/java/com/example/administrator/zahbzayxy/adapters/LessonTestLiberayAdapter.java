package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LessonAttachTestBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/2/9 0009.
 */
public class LessonTestLiberayAdapter extends BaseAdapter{
    Context context;
    List<LessonAttachTestBean.DataBean> list;
    LayoutInflater inflater;
    public LessonTestLiberayAdapter(Context context, List<LessonAttachTestBean.DataBean> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }


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
        TestLiberaryViewHold viewHold;
        if (convertView==null){
            viewHold=new TestLiberaryViewHold();
            convertView= inflater.inflate(R.layout.item_testliberay_layout,parent,false);
            viewHold.test_name= (TextView) convertView.findViewById(R.id.testName_tv);
            viewHold.test_date= (TextView) convertView.findViewById(R.id.testDate_tv);
            convertView.setTag(viewHold);
        }
        else {
            viewHold = (TestLiberaryViewHold) convertView.getTag();
        }
        LessonAttachTestBean.DataBean dataBean = list.get(position);
        viewHold.test_name.setText(dataBean.getQuesLibName());
       viewHold.test_date.setText(dataBean.getUpdateTime());

      /*  viewHold.testAndBuy_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TestQustionActivity.class);
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }
    static class TestLiberaryViewHold{
        TextView test_name,test_date;
        Button testAndBuy_bt;

    }
}
