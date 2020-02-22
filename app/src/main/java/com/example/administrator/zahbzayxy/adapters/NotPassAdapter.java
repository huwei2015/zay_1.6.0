package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ExamRecordActivity;
import com.example.administrator.zahbzayxy.activities.MyExamActivity;
import com.example.administrator.zahbzayxy.activities.TestContentActivity1;
import com.example.administrator.zahbzayxy.beans.NotPassBean;
import com.example.administrator.zahbzayxy.beans.NotThroughBean;
import com.example.administrator.zahbzayxy.beans.TimeData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 13:58.
 * 考试未通过adapter
 */
public class NotPassAdapter extends RecyclerView.Adapter<NotPassAdapter.NotPassViewHodler>{
    private Context mContext;
    private List<NotPassBean.NotListData> notPassListBeans;

    public NotPassAdapter(Context mContext, List<NotPassBean.NotListData> notPassListBeans) {
        this.mContext = mContext;
        this.notPassListBeans = notPassListBeans;
    }

    @Override
    public NotPassViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotPassViewHodler(LayoutInflater.from(mContext).inflate(R.layout.item_not_pass,parent,false));
    }
    public void setList(List<NotPassBean.NotListData> notPassListBeans) {
        this.notPassListBeans = notPassListBeans;
        notifyDataSetChanged();
    }
    public void addList(List<NotPassBean.NotListData> notPassListBeans) {
        this.notPassListBeans.addAll(notPassListBeans);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NotPassViewHodler holder, int position) {
        holder.title.setText(notPassListBeans.get(position).getQuesLibName());
        holder.time.setText(notPassListBeans.get(position).getEndTime());
        holder.exam_account.setText(String.valueOf(notPassListBeans.get(position).getQuesLibExamNum()));
        holder.lib_account.setText(String.valueOf(notPassListBeans.get(position).getUserExamNum()));
        if(notPassListBeans.get(position).getQuesLibExamNum() == 0){//无限次
            holder.tv_account.setText("无限次");
            holder.tv_account.setVisibility(View.VISIBLE);
            holder.lib_account.setVisibility(View.GONE);//已考数据
            holder.tv_xie.setVisibility(View.GONE);//斜线
            holder.exam_account.setVisibility(View.GONE);//总考试次数
            holder.examEnterImg.setVisibility(View.VISIBLE);//按钮显示
            holder.state.setVisibility(View.GONE); //入口关闭
        }
        if(notPassListBeans.get(position).getIsExam().equals("0")){//考试入口开启
            holder.examEnterImg.setVisibility(View.VISIBLE);
            holder.state.setVisibility(View.GONE);
            holder.lib_account.setVisibility(View.VISIBLE);
            holder.tv_xie.setVisibility(View.VISIBLE);
            holder.exam_account.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);
            holder.state.setVisibility(View.GONE);
            holder.tv_account.setVisibility(View.GONE);
        }else if(notPassListBeans.get(position).getIsExam().equals("1")){//不可以去考试
            holder.examEnterImg.setVisibility(View.GONE);
            holder.state.setVisibility(View.VISIBLE);
            holder.lib_account.setVisibility(View.VISIBLE);
            holder.tv_xie.setVisibility(View.VISIBLE);
            holder.exam_account.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);
            holder.state.setVisibility(View.VISIBLE);
            holder.tv_account.setVisibility(View.GONE);
        }else if(notPassListBeans.get(position).getIsExam().equals("2")){//不能考试，可以查看记录
            holder.examEnterImg.setVisibility(View.GONE);
            holder.lib_account.setVisibility(View.VISIBLE);
            holder.tv_xie.setVisibility(View.VISIBLE);
            holder.exam_account.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);
            holder.state.setVisibility(View.VISIBLE);
            holder.state.setText("已通过考试");
            holder.state.setTextColor(mContext.getResources().getColor(R.color.text_yellow));
            holder.tv_account.setVisibility(View.GONE);
        }else if(notPassListBeans.get(position).getIsExam().equals("3")){//不可以考试但是可看记录(过期，次数用尽)
            holder.examEnterImg.setVisibility(View.GONE);
            holder.lib_account.setVisibility(View.VISIBLE);
            holder.tv_xie.setVisibility(View.VISIBLE);
            holder.exam_account.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);
            holder.state.setVisibility(View.VISIBLE);
            holder.tv_account.setVisibility(View.GONE);
        }
        holder.exam_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ExamRecordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("examType", 0);
                bundle.putInt("libId", notPassListBeans.get(position).getQuesLibId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        holder.examEnterImg.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, TestContentActivity1.class);
            Bundle bundle = new Bundle();
            bundle.putInt("quesLibId", notPassListBeans.get(position).getQuesLibId());
            bundle.putInt("userLibId", notPassListBeans.get(position).getId());//正式考试题库id
            bundle.putInt("examType", 0);
            intent.putExtras(bundle);
            mContext.startActivity(intent);



        });

        Picasso.with(mContext).load(notPassListBeans.get(position).getImagePath()).placeholder(R.mipmap.loading_png).into(holder.img_pic);

    }

    @Override
    public int getItemCount() {
        return notPassListBeans !=null ? notPassListBeans.size() : 0;
    }

    static class NotPassViewHodler extends RecyclerView.ViewHolder{
        ImageView img_pic, examEnterImg;
        TextView title,state,time,exam_record,exam_account,lib_account,tv_xie,tv_account;
        public NotPassViewHodler(View itemView) {
            super(itemView);
            img_pic=itemView.findViewById(R.id.img_icon);
            title= itemView.findViewById(R.id.tv_title);
            time=itemView.findViewById(R.id.tv_time);
            exam_account=itemView.findViewById(R.id.tv_exam_account);//考试总次数
            lib_account = itemView.findViewById(R.id.lib_exam_account);//已考次数
            exam_record=itemView.findViewById(R.id.tv_exam_recode);
            state=itemView.findViewById(R.id.tv_state);
            tv_xie=itemView.findViewById(R.id.tv_xie);
            tv_account=itemView.findViewById(R.id.tv_account);
            examEnterImg = itemView.findViewById(R.id.exam_item_enter_img);
        }
    }
}
