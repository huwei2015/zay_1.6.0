package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.TestContentActivity1;
import com.example.administrator.zahbzayxy.activities.TestDetailActivity;
import com.example.administrator.zahbzayxy.beans.PersonTiKuListBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/7 0007.
 */
public class PMyTiKuAdapter extends BaseAdapter {
    private List<PersonTiKuListBean.DataEntity.QuesLibsEntity>list;
    private Context context;
    private LayoutInflater inflater;
    PopupWindow popUpWindow1;

    public PMyTiKuAdapter(List<PersonTiKuListBean.DataEntity.QuesLibsEntity> list, Context context) {
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
        PMyTiKuViewHold viewHold=null;
        if (convertView==null){
            viewHold=new PMyTiKuViewHold();
            convertView=inflater.inflate(R.layout.item_pmytiku_layout,parent,false);
            viewHold.testName_tv=  convertView.findViewById(R.id.pMyTiKuName_tv);
            viewHold.taocanTypa_tv=  convertView.findViewById(R.id.tiku_taocanType_tv);
            viewHold.haveTest_tv=  convertView.findViewById(R.id.haveTestNum_tv);
            viewHold.noTest_tv=  convertView.findViewById(R.id.noTestNum_tv);
            viewHold.goTest_tv=  convertView.findViewById(R.id.pKaoshi_bt);

            convertView.setTag(viewHold);
        }else {
            viewHold = (PMyTiKuViewHold) convertView.getTag();
        }
        PersonTiKuListBean.DataEntity.QuesLibsEntity quesLibsEntity = list.get(position);
        final int userLibId = quesLibsEntity.getUserLibId();
        final int quesLibId = quesLibsEntity.getQuesLibId();

        final String quesLibName = quesLibsEntity.getQuesLibName();
        if (!TextUtils.isEmpty(quesLibName)){
            viewHold.testName_tv.setText(quesLibsEntity.getQuesLibName());
        }
        String packageName = quesLibsEntity.getPackageName();
        if (!TextUtils.isEmpty(packageName)){
            viewHold.taocanTypa_tv.setText(packageName);
        }
        int examUsedNum = quesLibsEntity.getExamUsedNum();
        Integer integer = Integer.valueOf(examUsedNum);
        if (integer!=null){
            viewHold.haveTest_tv.setText("已考次数:"+integer);
        }
        final int examNum = quesLibsEntity.getExamNum();
        Integer integer1 = Integer.valueOf(examNum);
        if (integer1!=null){
            viewHold.noTest_tv.setText("未考次数:"+examNum);
        }
        //点击进入考试模式
        viewHold.goTest_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (examNum>0) {
                    Intent intent = new Intent(context, TestContentActivity1.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("quesLibId", quesLibId);
                    bundle.putInt("userLibId", userLibId);
                    bundle.putInt("examType",1);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else {//弹框提醒不可以考试

                    initPopUpWindow1();
                }
            }

            private void initPopUpWindow1() {
                View view = View.inflate(context, R.layout.test_pop_layout, null);
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
        });

        return convertView;
    }
    static class PMyTiKuViewHold{
        TextView testName_tv,taocanTypa_tv,haveTest_tv,noTest_tv,goTest_tv;

    }
}
