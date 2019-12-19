package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.AginDoErrorActivity;
import com.example.administrator.zahbzayxy.activities.PLookCuoTiActivity;
import com.example.administrator.zahbzayxy.beans.PCuoTiJiLuBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/12 0012.
 */
public class MyCuoTiJiLuAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<PCuoTiJiLuBean.DataBean.ErrorRecordQuesLibsBean>list;
    int errorNum;
    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public MyCuoTiJiLuAdapter(Context context, List<PCuoTiJiLuBean.DataBean.ErrorRecordQuesLibsBean> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ErrorJiLuViewHold viewHold=null;
        if (convertView==null){
            viewHold=new ErrorJiLuViewHold();
            convertView=inflater.inflate(R.layout.item_mycuotijilu_layout,parent,false);
            viewHold.testName_tv= (TextView) convertView.findViewById(R.id.pErrorTestName_tv);
            viewHold.errorNum_tv= (TextView) convertView.findViewById(R.id.pErrorNum_tv);
            viewHold.look_bt= (TextView) convertView.findViewById(R.id.lookError_bt);
            viewHold.doError_bt= (TextView) convertView.findViewById(R.id.doError_bt);
            viewHold.cuoTiTaoCanType_tv= (TextView) convertView.findViewById(R.id.cuoTi_taocanType_tv);
            convertView.setTag(viewHold);
        }else {
            viewHold= (ErrorJiLuViewHold) convertView.getTag();
        }

        PCuoTiJiLuBean.DataBean.ErrorRecordQuesLibsBean errorRecordQuesLibsBean = list.get(position);
        String quesLibName = errorRecordQuesLibsBean.getQuesLibName();
        if (!TextUtils.isEmpty(quesLibName)) {
            viewHold.testName_tv.setText(quesLibName);
        }
        //显示错题个数
        String s = String.valueOf(errorRecordQuesLibsBean.getErrorRecordCount());
        if (!TextUtils.isEmpty(s)) {
            viewHold.errorNum_tv.setText(s + "道错题");
        }

        String packageName = errorRecordQuesLibsBean.getPackageName();
        if (!TextUtils.isEmpty(packageName)) {
            viewHold.cuoTiTaoCanType_tv.setText(packageName);
        }

        final int quesLibId = errorRecordQuesLibsBean.getQuesLibId();
        final int userLibId = errorRecordQuesLibsBean.getUserLibId();
        final int packageId = errorRecordQuesLibsBean.getPackageId();
        //查看错题
        viewHold.look_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PLookCuoTiActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("quesLibId",quesLibId);
                bundle.putInt("userLibId",userLibId);
                bundle.putInt("packageId",packageId);
                Log.e("qusLibsId",String.valueOf(quesLibId)+",1111,"+userLibId+","+packageId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


        viewHold.doError_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,AginDoErrorActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("quesLibId",quesLibId);
                Log.e("qusLibsId",String.valueOf(quesLibId));
                bundle.putInt("userLibId",userLibId);
                bundle.putInt("packageId",packageId);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });



        return convertView;
    }
    static class ErrorJiLuViewHold{
        TextView testName_tv,errorNum_tv,look_bt,doError_bt,cuoTiTaoCanType_tv;
    }
}
