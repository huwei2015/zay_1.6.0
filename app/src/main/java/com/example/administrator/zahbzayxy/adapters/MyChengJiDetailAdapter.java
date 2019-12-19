package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ResultActivity;
import com.example.administrator.zahbzayxy.activities.TestDetailActivity;
import com.example.administrator.zahbzayxy.beans.NewMyChengJiListBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/19 0019.
 */
public class MyChengJiDetailAdapter extends BaseAdapter {
    private List<NewMyChengJiListBean.DataEntity.ExamScoresEntity>list;
    private Context context;

    private LayoutInflater inflater;
    PopupWindow popUpWindow1;
    private int quesLibId;

    public MyChengJiDetailAdapter(List<NewMyChengJiListBean.DataEntity.ExamScoresEntity> list, Context context,int quesLibId) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.quesLibId=quesLibId;

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
        ViewHold viewHold=null;
        if (convertView==null){
            viewHold=new ViewHold();
            convertView=inflater.inflate(R.layout.item_mychengji_detail_layout,parent,false);
            viewHold.testName_tv= (TextView) convertView.findViewById(R.id.testName_tv);
            viewHold.useTime_tv= (TextView) convertView.findViewById(R.id.test_useTime_tv);
            viewHold.grade_tv= (TextView) convertView.findViewById(R.id.grade_tv);
            viewHold.date_tv= (TextView) convertView.findViewById(R.id.testDate_tv);
            viewHold.testDetail_rl= (RelativeLayout) convertView.findViewById(R.id.testDetail_rl);
            viewHold.testTotalScore_tv= (TextView) convertView.findViewById(R.id.test_total_tv);
            convertView.setTag(viewHold);

        }else{
            viewHold = (ViewHold) convertView.getTag();
        }
        NewMyChengJiListBean.DataEntity.ExamScoresEntity examScoresEntity = list.get(position);
        String totalScore = examScoresEntity.getTotalScore();
        String paperName = examScoresEntity.getPaperName();
        final double examScoreId = examScoresEntity.getExamScoreId();
        final int userLibId = examScoresEntity.getUserLibId();
        String libPackageName = examScoresEntity.getLibPackageName();
        final Integer isShowDetail = examScoresEntity.getIsShowDetail();


        //试卷总分数
        int libScore = examScoresEntity.getLibScore();

        Integer integer = Integer.valueOf(libScore);
        if (integer!=null){
            viewHold.testTotalScore_tv.setText("/满分"+integer+"分");

        }
        String scoreDate = examScoresEntity.getScoreDate();
        String useTime = examScoresEntity.getUseTime();
        if (!TextUtils.isEmpty(scoreDate)){
            viewHold.date_tv.setText(scoreDate);
        }
        if (!TextUtils.isEmpty(paperName)){
            viewHold.testName_tv.setText(paperName);
        }
        if (!TextUtils.isEmpty(libPackageName)){
            viewHold.useTime_tv.setText(libPackageName);
        }
        if(!TextUtils.isEmpty(totalScore)){
            viewHold.grade_tv.setText(String.valueOf(totalScore)+"分");
        }
//        if (Integer.valueOf(totalScore)!=null){
//            viewHold.grade_tv.setText(totalScore+"分");
//        }
        viewHold.testDetail_rl.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("isShowDeatin",isShowDetail+"");
            if (isShowDetail==1){
                Intent intent=new Intent(context, ResultActivity.class);
                Log.e("examScoreId",examScoreId+"1111");
                Bundle bundle=new Bundle();
                bundle.putDouble("examScoreId",examScoreId);
                bundle.putInt("userLibId",userLibId);
                Log.e("getIdResult",userLibId+"1111");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }else {
               // initPopUpWindow();
                initPopUpWindow1();
            }

        }

//        private void initPopUpWindow() {
//
//            final View popView = LayoutInflater.from(context).inflate(R.layout.pop_taocan_layout, null, false);
//            TextView downLoadNow_tv= (TextView) popView.findViewById(R.id.downloadNow_tv);
//            TextView noDownLoadNow_tv= (TextView) popView.findViewById(R.id.noDownloadNow_tv);
//            ImageView cha_iv= (ImageView) popView.findViewById(R.id.cha_iv);
//            downLoadNow_tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //去购买,跳到题库详情
//                    Intent intent=new Intent(context, TestDetailActivity.class);
//                    Bundle bundle=new Bundle();
//                    bundle.putInt("quesLibId",quesLibId);
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                    if (popUpWindow1.isShowing()) {
//                        popUpWindow1.dismiss();
//                    }
//
//                }
//            });
//            noDownLoadNow_tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (popUpWindow1.isShowing()){
//                        popUpWindow1.dismiss();
//                    }
//                }
//            });
//            cha_iv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (popUpWindow1.isShowing()){
//                        popUpWindow1.dismiss();
//                    }
//                }
//            });
//            popUpWindow1 = new PopupWindow(popView, 700, 700, true);
//            // popUpWindow1.setTouchable(true);
//            // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
//            //popUpWindow1.setOutsideTouchable(true);
//            popUpWindow1.setAnimationStyle(R.style.take_photo_anim);
//            // 配合 点击外部区域消失使用 否则 没有效果
//            ColorDrawable colorDrawable=new ColorDrawable(context.getResources().getColor(R.color.white));
//            popUpWindow1.setBackgroundDrawable(colorDrawable);
//            popUpWindow1.showAtLocation(popView, Gravity.CENTER, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        }
    });
        return convertView;
    }

    private void initPopUpWindow1() {
        View view = View.inflate(context, R.layout.pop_taocan_layout, null);
        TextView cancel = (TextView) view.findViewById(R.id.myquestion_cancel);
        TextView downLoad_tv= (TextView) view.findViewById(R.id.downLoadNow_tv);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        //设置动画,就是style里创建的那个j
        window.setAnimationStyle(R.style.take_photo_anim);
        window.showAsDropDown(view, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        window.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        window.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        cancel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        cancel.getPaint().setAntiAlias(true);//抗锯齿

        downLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去购买,跳到题库详情
                Intent intent=new Intent(context, TestDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("quesLibId",quesLibId);
                intent.putExtras(bundle);
                context.startActivity(intent);
                window.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
    }

    private static class ViewHold {
        private TextView testName_tv,useTime_tv,grade_tv,date_tv ,testTotalScore_tv;
        private RelativeLayout testDetail_rl;
    }

}
