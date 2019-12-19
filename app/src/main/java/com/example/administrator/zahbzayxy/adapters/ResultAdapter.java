package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TestGradAnalyseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public class ResultAdapter extends BaseAdapter {
  List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity>list;
    Context mContext;
    LayoutInflater inflater;

    public ResultAdapter(List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
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
        TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = list.get(position);
        int isRight = examDetailsEntity.getIsRight();

        //  isRight = list.get(position).getIsRight();
            if (isRight == 0) {
                tv.setBackgroundResource(R.mipmap.wrong_test1);
            } else if (isRight== 1) {
                tv.setBackgroundResource(R.mipmap.right_test1);

        }else {
                tv.setBackgroundResource(R.mipmap.wrong_test1);
        }

        return view;
    }
}
