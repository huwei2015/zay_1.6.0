package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ExamRecordActivity;
import com.example.administrator.zahbzayxy.beans.ExamBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019/8/5.
 * Time 15:26.
 * Description.我的考试adapter
 */
public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHodler> {
    private Context mContext;
    private List<ExamBean.QuesLibsBean> examBeanList;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        //每条点击事件
        void onItemClick(View view, int position);
    }

    public ExamAdapter(Context mContext, List<ExamBean.QuesLibsBean> examBeanList) {
        this.mContext = mContext;
        this.examBeanList = examBeanList;
    }

    public ExamAdapter(Context context) {
        mContext = context;
        examBeanList = new ArrayList<>();
    }

    public void setList(List<ExamBean.QuesLibsBean> examBeanList) {
        this.examBeanList = examBeanList;
        notifyDataSetChanged();
    }

    public void addList(List<ExamBean.QuesLibsBean> examBeanList) {
        this.examBeanList.addAll(examBeanList);
        notifyDataSetChanged();
    }

    @Override
    public ExamViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExamViewHodler(LayoutInflater.from(mContext).inflate(R.layout.my_exam_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ExamViewHodler holder, final int position) {
        holder.title.setText(examBeanList.get(position).getQuesLibName());
        holder.tv_time.setText(examBeanList.get(position).getEndTime());
        holder.tv_number.setText(String.valueOf(examBeanList.get(position).getUserExamNum()));
        holder.tv_total.setText(String.valueOf(examBeanList.get(position).getQuesLibExamNum()));
        if (examBeanList.get(position).getQuesLibExamNum() == 0) {
            holder.tv_account.setText("无限次");
            holder.tv_account.setVisibility(View.VISIBLE);
            holder.tv_total.setVisibility(View.GONE);
            holder.tv_xie.setVisibility(View.GONE);
            holder.tv_number.setVisibility(View.GONE);
            holder.exam_record.setBackground(mContext.getResources().getDrawable(R.mipmap.sel));
//            holder.exam.setBackground(mContext.getResources().getDrawable(R.mipmap.sel));
        }
        if (examBeanList.get(position).getIsExam().equals("0")) {//可以去考试，可看记录
            holder.exam.setText("去考试");
            holder.exam_record.setText("考试记录");
            holder.exam.setBackground(mContext.getResources().getDrawable(R.mipmap.sel));
            holder.exam_record.setBackground(mContext.getResources().getDrawable(R.mipmap.sel));
        } else if (examBeanList.get(position).getIsExam().equals("1")) {//不可以考试且没记录，按钮置灰(课程未完成)
            holder.exam.setText("去考试");
            holder.exam_record.setText("考试记录");
            holder.exam_record.setBackground(mContext.getResources().getDrawable(R.mipmap.unsel));
            holder.exam.setBackground(mContext.getResources().getDrawable(R.mipmap.unsel));
            holder.exam.setEnabled(false);
            holder.exam_record.setEnabled(false);

        } else if (examBeanList.get(position).getIsExam().equals("2")) {//合格可看记录
            holder.exam.setText("合格");
            holder.exam.setEnabled(false);
            holder.exam_record.setText("考试记录");
            holder.exam_record.setBackground(mContext.getResources().getDrawable(R.mipmap.sel));

        } else if (examBeanList.get(position).getIsExam().equals("3")) { // 不可以考试但是可看记录(过期，次数用尽)
            holder.exam.setText("去考试");
            holder.exam.setEnabled(false);
            holder.exam_record.setText("考试记录");
            holder.exam_record.setBackground(mContext.getResources().getDrawable(R.mipmap.sel));
        }
        holder.exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position);
//                Intent intent = new Intent(mContext, TestContentActivity1.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("quesLibId", examBeanList.get(position).getQuesLibId());
//                bundle.putInt("userLibId", examBeanList.get(position).getUserQuesLibId());
//                bundle.putInt("examType", 0);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
            }
        });
        holder.exam_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, NewMyChengJiActivity.class);
                Intent intent = new Intent(mContext, ExamRecordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("examType", 0);
                bundle.putInt("libId", examBeanList.get(position).getQuesLibId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return examBeanList != null ? examBeanList.size() : 0;
    }

    static class ExamViewHodler extends RecyclerView.ViewHolder {
        private TextView title, tv_time, exam, exam_record, tv_number, tv_total, tv_xie, tv_account;

        public ExamViewHodler(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_exam);
            tv_time = itemView.findViewById(R.id.tv_time);
            exam = itemView.findViewById(R.id.exam);
            exam_record = itemView.findViewById(R.id.tv_exam_record);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_total = itemView.findViewById(R.id.tv_total);
            tv_xie = itemView.findViewById(R.id.tv_xie);
            tv_account = itemView.findViewById(R.id.tv_account);
        }
    }
}
