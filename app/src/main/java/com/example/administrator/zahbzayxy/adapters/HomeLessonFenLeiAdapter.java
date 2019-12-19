package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.zahbzayxy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ${ZWJ} on 2017/9/7 0007.
 */
public class HomeLessonFenLeiAdapter extends BaseAdapter {
    private List<Map<String,Object>>list=new ArrayList();
    private Context context;
    LayoutInflater inflater;

    public HomeLessonFenLeiAdapter(List<Map<String,Object>>list, Context context) {
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
        MyViewHodler hodler = null;
        if(convertView==null){
            hodler=new MyViewHodler();
            convertView=inflater.inflate(R.layout.item_home_leson_fenlei, null);
            hodler.image=(ImageView) convertView.findViewById(R.id.home_lesson_fenLei_iv);
            convertView.setTag(hodler);
        }else{
            hodler=(MyViewHodler) convertView.getTag();
        }
      //  Map<String, Object> stringObjectMap = list.get(position);
        int size = list.size();
        hodler.image.setImageResource((Integer) list.get(position).get("img"));
        return convertView ;
    }
    class MyViewHodler{
        ImageView image;
    }
}
