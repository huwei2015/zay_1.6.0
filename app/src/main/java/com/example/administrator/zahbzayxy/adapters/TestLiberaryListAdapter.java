package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.TestDetailActivity;
import com.example.administrator.zahbzayxy.beans.TestSecondListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/2/15 0015.
 */
public class TestLiberaryListAdapter extends BaseAdapter {
    Context context;
    List<TestSecondListBean.DataEntity.QuesLibsEntity> list;
    LayoutInflater inflater;
    private String quesLibName;

    public TestLiberaryListAdapter(Context context, List<TestSecondListBean.DataEntity.QuesLibsEntity> list) {
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
            viewHold.item_ly= (RelativeLayout) convertView.findViewById(R.id.item_ly);
            viewHold.test_name= (TextView) convertView.findViewById(R.id.testName_tv);
            viewHold.test_list_iv= (ImageView) convertView.findViewById(R.id.test_list_iv);
            viewHold.testNum_list_tv= (TextView) convertView.findViewById(R.id.testNum_list_tv);
            viewHold.testList_price_tv= (TextView) convertView.findViewById(R.id.testList_price_tv);
            viewHold.test_date= (TextView) convertView.findViewById(R.id.testDate_tv);

            convertView.setTag(viewHold);
        }
        else {
            viewHold = (TestLiberaryViewHold) convertView.getTag();
        }
        TestSecondListBean.DataEntity.QuesLibsEntity quesLibsEntity = list.get(position);
        viewHold.test_name.setText(quesLibsEntity.getQuesLibName());
        String updateTime = quesLibsEntity.getUpdateTime();
        String imagePath = quesLibsEntity.getImagePath();
        if (!TextUtils.isEmpty(imagePath)){
            Picasso.with(context).load(imagePath).placeholder(R.mipmap.loading_png).into(viewHold.test_list_iv);
        }

        if (!TextUtils.isEmpty(updateTime)) {
            String spStr[] = updateTime.split(" ");
            String s = spStr[0];
            viewHold.test_date.setText(s);
        }


        final String sPrice = quesLibsEntity.getSPrice();
        if (sPrice!=null) {
            if (sPrice.equals("0.00")) {
                viewHold.testList_price_tv.setText("免费");
                viewHold.testList_price_tv.setTextColor(context.getResources().getColor(R.color.greenRightTv));
            } else {
                viewHold.testList_price_tv.setText("¥" + sPrice);
                viewHold.testList_price_tv.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
            }
        }
        final int quesCount = quesLibsEntity.getQuesCount();
        viewHold.testNum_list_tv.setText(quesCount+"题");









        final int id = list.get(position).getId();
        quesLibName = list.get(position).getQuesLibName();
        viewHold.item_ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TestDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("quesLibId",id);
                bundle.putInt("quesCount",quesCount);
                bundle.putString("quesLibName",quesLibName);
                bundle.putString("sPrice",sPrice);
                intent.putExtras(bundle);
                context.startActivity(intent);
                //((Activity)context).finish();
            }
        });
        return convertView;
    }
    static class TestLiberaryViewHold{
        RelativeLayout item_ly;
        TextView test_name,test_date,testNum_list_tv,testList_price_tv;
        ImageView test_list_iv;
    }
}
