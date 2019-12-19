package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.BuyCarListBean;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2017/3/30.
 */
public class CarAdapter extends BaseAdapter {
    List<BuyCarListBean.DataBean.CoursesBean> list;
    Context context;
    LayoutInflater inflater;
    public CarAdapter(List<BuyCarListBean.DataBean.CoursesBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_buycar_delete_layout,parent,false);
            holder = new ViewHolder();
            holder.zhuRB = convertView.findViewById(R.id.zhuRb);
            holder.zhuTV = convertView.findViewById(R.id.zhuTitle);
            holder.zPrice_tv=  convertView.findViewById(R.id.zPrice_tv);
            holder.ziLL =  convertView.findViewById(R.id.ziLinearLayout);
            holder.buyCar_iv=  convertView.findViewById(R.id.buyCar_iv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        String courseImage = list.get(position).getCourseImage();
        if (courseImage!=null){
            Picasso.with(context).load(courseImage).placeholder(R.mipmap.loading_png).into(holder.buyCar_iv);
        }

        String courseName = list.get(position).getCourseName();
        if (courseName!=null) {
            holder.zhuTV.setText(courseName);
        }
        double sPrice = list.get(position).getSPrice();
        holder.zPrice_tv.setText(String.valueOf(sPrice)+"元");
        final List<BuyCarListBean.DataBean.CoursesBean.SubCoursesBean> ziList = this.list.get(position).getSubCourses();
        holder.ziLL.removeAllViews();

        for (int i = 0;i<ziList.size();++i){
            CheckBox rb = new CheckBox(context);
            rb.setText(ziList.get(i).getCourseName());
            rb.setButtonDrawable(R.drawable.rb_buycar_selector2);
            holder.ziLL.addView(rb);
            final int finalI = i;
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ziList.get(finalI).getTag1()==0){
                        ziList.get(finalI).setTag1(1);
                    }else {
                        ziList.get(finalI).setTag1(0);
                    }
                }
            });
        }
        //处理复用
        fuYong(holder,position);
        final ViewHolder finalHolder1 = holder;
        holder.zhuRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getTag()==0){
                    list.get(position).setTag(1);
                    for (int k=0;k<ziList.size();++k){
                        ziList.get(k).setTag1(1);
                    }
                    for (int k=0;k<ziList.size();++k){
                        CheckBox child = (CheckBox) finalHolder1.ziLL.getChildAt(k);
                        child.setChecked(true);
                        child.setEnabled(false);
                    }
                }else {
                    list.get(position).setTag(0);
                    for (int k=0;k<ziList.size();++k){
                        ziList.get(k).setTag1(0);
                    }
                    for (int k=0;k<ziList.size();++k){
                        CheckBox child = (CheckBox) finalHolder1.ziLL.getChildAt(k);
                        child.setChecked(false);
                        child.setEnabled(true);
                    }
                }

            }
        });
        return convertView;
    }

    static class ViewHolder{
        CheckBox zhuRB;
        LinearLayout ziLL;
        TextView zhuTV,zPrice_tv;
        ImageView buyCar_iv;
    }

    private void fuYong(ViewHolder holder, int position) {
        for (int i=0;i<list.size();++i){
            if (list.get(position).getTag()==1){
                holder.zhuRB.setChecked(true);
            }else {
                holder.zhuRB.setChecked(false);
            }
            List<BuyCarListBean.DataBean.CoursesBean.SubCoursesBean> ziList = this.list.get(position).getSubCourses();
            for (int j =0;j<ziList.size();++j){
                CheckBox child = (CheckBox) holder.ziLL.getChildAt(j);
                if (ziList.get(j).getTag1()==1){
                    child.setChecked(true);
                }else {
                    child.setChecked(false);
                }
            }
        }
    }


}
