package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.zahbzayxy.beans.TestContentBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/21 0021.
 */
public class TestPopAnserCardAdapter extends BaseAdapter {
    List<TestContentBean.DataBean.QuesDataBean>list;
    Context context;
    LayoutInflater inflater;

    public TestPopAnserCardAdapter(List<TestContentBean.DataBean.QuesDataBean> list, Context context) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
