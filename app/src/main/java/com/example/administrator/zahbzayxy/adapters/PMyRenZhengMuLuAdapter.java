package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.PMyRenZhengDetailActivity;
import com.example.administrator.zahbzayxy.activities.ZhengShuDetailActivity;
import com.example.administrator.zahbzayxy.beans.PMyRenZhengMuLuBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/13 0013.
 */
public class PMyRenZhengMuLuAdapter extends BaseAdapter {
    private List<PMyRenZhengMuLuBean.DataBean.CerListBean>list;
    private Context context;
    LayoutInflater inflater;
    public PMyRenZhengMuLuAdapter(List<PMyRenZhengMuLuBean.DataBean.CerListBean> list, Context context) {
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
        MyRenZhengViewHold viewHold;

        if (convertView==null){
            viewHold=new MyRenZhengViewHold();
           convertView= inflater.inflate(R.layout.item_myrenzheng_colum_layout,parent,false);
            viewHold.courseName_tv= convertView.findViewById(R.id.pRenZhengName_tv);//标题
            viewHold.lessonNum_tv=  convertView.findViewById(R.id.lessonNum_tv);//取证时间
            viewHold.status_bt= convertView.findViewById(R.id.tv_to_view);//查看证书
            convertView.setTag(viewHold);
        }
        else {
           viewHold= (MyRenZhengViewHold) convertView.getTag();
        }
        PMyRenZhengMuLuBean.DataBean.CerListBean cerListBean = list.get(position);
        viewHold.courseName_tv.setText(cerListBean.getCertName());
        viewHold.lessonNum_tv.setText(cerListBean.getCerPassDate());
        final int userCourseId = cerListBean.getUserCourseId();
        final int userCardId = cerListBean.getUserCardId();
        final String cerModel = cerListBean.getCerModel();//正式显示横竖屏
        final int certId = cerListBean.getCertId();//证书id
        final int trainType = cerListBean.getTrainType();//如果是0是跳转之前的证书
        if(cerListBean.getTrainType() == 0){
            viewHold.courseName_tv.setText("中安云学院合格证书");
        }
//        viewHold.passNum_tv.setText("达标学时:"+cerListBean.getPassHours());
//        viewHold.haveStudy_tv.setText("已学学时:"+cerListBean.getLearnHours());
//        final int userCourseId = cerListBean.getUserCourseId();
//        int cerStatus = cerListBean.getCerStatus();
//        if (cerStatus==1){
//            viewHold.status_bt.setText("查看认证");

            viewHold.status_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(trainType == 0){//如果等于0,跳转到旧的证书页面
                        Log.i("dfdfd","huwei======="+userCardId);
                        Intent intent = new Intent(context, PMyRenZhengDetailActivity.class);
                        intent.putExtra("userCourseId", userCourseId);
                        context.startActivity(intent);
                        return;
                    }
                    Intent intent = new Intent(context, ZhengShuDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("userCourseId", userCourseId);
                    bundle.putInt("userCardId",userCardId);
                    bundle.putInt("certId",certId);
                    bundle.putString("cerModel",cerModel);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        return convertView;
    }
    static class MyRenZhengViewHold{
        TextView courseName_tv,lessonNum_tv,passNum_tv,haveStudy_tv;
        Button status_bt;
    }
}
