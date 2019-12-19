package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ExamResultActivity;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019/8/22.
 * Time 17:27.
 * Description.考试记录adapter
 */
public class ExamRecordAdapter extends RecyclerView.Adapter<ExamRecordAdapter.ExamRecordViewHodler> {
    private Context mContext;
    private List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> examScoresEntities;

    public ExamRecordAdapter(Context mContext, List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> examScoresEntities) {
        this.mContext = mContext;
        this.examScoresEntities = examScoresEntities;
    }
    public ExamRecordAdapter(Context context) {
        mContext = context;
        examScoresEntities = new ArrayList<>();
    }

    public void setList(List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> examScoresEntities) {
        this.examScoresEntities = examScoresEntities;
        notifyDataSetChanged();
    }

    public void addList(List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> examScoresEntities) {
        this.examScoresEntities.addAll(examScoresEntities);
        notifyDataSetChanged();
    }
    @Override
    public ExamRecordViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExamRecordAdapter.ExamRecordViewHodler(LayoutInflater.from(mContext).inflate(R.layout.item_exam_record, parent, false));
    }

    @Override
    public void onBindViewHolder(ExamRecordViewHodler holder, int position) {
        holder.paperName.setText(examScoresEntities.get(position).getPaperName());
        holder.libScore.setText("/满分"+String.valueOf(examScoresEntities.get(position).getLibScore()));
        holder.scoreDate.setText(examScoresEntities.get(position).getScoreDate());
        holder.totalScore.setText(examScoresEntities.get(position).getTotalScore());
        final Integer detail = examScoresEntities.get(position).getIsShowDetail();
        final int userLibId = examScoresEntities.get(position).getUserLibId();
        final double examScoreId = examScoresEntities.get(position).getExamScoreId();
        holder.testDetail_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detail == 1){
                    Intent intent=new Intent(mContext, ExamResultActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putDouble("examScoreId",examScoreId);
                    bundle.putInt("userLibId",userLibId);
                    Log.e("getIdResult",userLibId+"1111");
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }else if(detail == 0){
                    Toast.makeText(mContext,"暂无考试详情",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return examScoresEntities != null ? examScoresEntities.size() : 0;
    }

    static class ExamRecordViewHodler extends RecyclerView.ViewHolder {
        private TextView libScore;//考试分数
        private TextView paperName;//企业名称
        private TextView libPackageName;//题库名称
        private TextView totalScore;//总分数
        private TextView scoreDate;//考试时间
        private RelativeLayout testDetail_rl;

        public ExamRecordViewHodler(View itemView) {
            super(itemView);
            paperName = itemView.findViewById(R.id.tv_paper_name);
            totalScore = itemView.findViewById(R.id.grade_tv);
            scoreDate = itemView.findViewById(R.id.testDate_tv);
            testDetail_rl = itemView.findViewById(R.id.testDetail_rl);
            libScore = itemView.findViewById(R.id.test_total_tv);
            testDetail_rl=itemView.findViewById(R.id.testDetail_rl);
        }
    }
}
