package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.PayUiActivity;
import com.example.administrator.zahbzayxy.beans.PMyLessonOrderBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/4/3.
 */

public class PMyLessonOrderAdapter extends BaseAdapter {
    List<PMyLessonOrderBean.DataBean>list;
    Context context;
    LayoutInflater layoutInflater;
    String token;
    int weiZhi;
    MyLessonDingDanViewHold myViewHold = null;
    public PMyLessonOrderAdapter(List<PMyLessonOrderBean.DataBean> list, Context context,String token) {
        this.token=token;
        this.list = list;
        this.context = context;
        layoutInflater=layoutInflater.from(context);
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
        weiZhi=position;
        if (convertView==null){
            myViewHold=new MyLessonDingDanViewHold();
            convertView=layoutInflater.inflate(R.layout.item_mylesson_dingdan_layout,parent,false);
            myViewHold.orderNum_tv= (TextView) convertView.findViewById(R.id.pMyLessonOrderNum_tv);
            myViewHold.orderStatus_tv= (TextView) convertView.findViewById(R.id.lessonOrderStatus_tv);
            myViewHold.orderLessonName_tv= (TextView) convertView.findViewById(R.id.pMyLessonName_tv);
            myViewHold.orderPrice_tv= (TextView) convertView.findViewById(R.id.pMyLessonOrderPrice_tv);
            myViewHold.oderCreateTime_tv= (TextView) convertView.findViewById(R.id.pMyLessonOrderBeginTime_tv);
            myViewHold.lesson_iv= (ImageView) convertView.findViewById(R.id.pMyLessonOrder_iv);
            myViewHold.quXiao_bt= (Button) convertView.findViewById(R.id.pMyLessonOrderQuXiao_bt);
            myViewHold.pay_bt= (Button) convertView.findViewById(R.id.pMyLessonOrderziFu_bt);
            myViewHold.delete_bt= (Button) convertView.findViewById(R.id.pMyLessonOrderDelete_bt);
            myViewHold.content_rl= (RelativeLayout) convertView.findViewById(R.id.content_myLesonOrder_rl);
            convertView.setTag(myViewHold);
        }else {
         myViewHold = (MyLessonDingDanViewHold) convertView.getTag();
        }

        PMyLessonOrderBean.DataBean dataBean = list.get(position);
        List<PMyLessonOrderBean.DataBean.CourseListBean> courseList = list.get(position).getCourseList();
        final String orderNumber = dataBean.getOrderNumber();
        final String payMoney = dataBean.getPayMoney();
        myViewHold.orderPrice_tv.setText("单价"+payMoney);
        myViewHold.orderNum_tv.setText(orderNumber);
        //点击立即购买时的点击事件
        myViewHold.pay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PayUiActivity.class);
                intent.putExtra("orderNumber",orderNumber);
                intent.putExtra("testPrice",payMoney);
                context.startActivity(intent);
            }
        });
        int size = courseList.size();
        myViewHold.orderLessonName_tv.setText(courseList.get(0).getCourseName());
       myViewHold.oderCreateTime_tv.setText(dataBean.getCreatTime());
        String courseLogo = courseList.get(0).getCourseLogo();
        if (!TextUtils.isEmpty(courseLogo)){
            Picasso.with(context).load(courseLogo).placeholder(R.mipmap.loading_png).into(myViewHold.lesson_iv);
        }
        int payStatus = dataBean.getPayStatus();
        int orderStatus = dataBean.getOrderStatus();

        if (payStatus==1){
            myViewHold.orderStatus_tv.setText("已支付");
            myViewHold.quXiao_bt.setVisibility(View.GONE);
           myViewHold.pay_bt.setVisibility(View.GONE);
           myViewHold.delete_bt.setVisibility(View.VISIBLE);
            myViewHold.delete_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除订单
                    deletOrder();
                }

                private void deletOrder() {
                    PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                    aClass.getMyLessonOrderDeleteData(orderNumber,token).enqueue(new Callback<SuccessBean>() {
                        @Override
                        public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                            SuccessBean body = response.body();
                            if (body!=null&&body.getErrMsg()==null){
                                boolean data = body.getData();
                                if (data==true){
                                    Toast.makeText(context,"删除订单成功",Toast.LENGTH_SHORT).show();
                                    Log.e("postionposition",position+"");
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(context,"删除订单失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<SuccessBean> call, Throwable t) {
                            Toast.makeText(context,"网络问题，请检查网络状态",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }if (payStatus==0){
           myViewHold.orderStatus_tv.setText("未支付");
           myViewHold.quXiao_bt.setVisibility(View.VISIBLE);
           myViewHold.pay_bt.setVisibility(View.VISIBLE);
            myViewHold.delete_bt.setVisibility(View.GONE);
        }if (orderStatus==2){
            myViewHold.orderStatus_tv.setText("已取消");
            myViewHold.quXiao_bt.setVisibility(View.GONE);
            myViewHold.pay_bt.setVisibility(View.GONE);
            myViewHold.delete_bt.setVisibility(View.VISIBLE);
            myViewHold.delete_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                    aClass.getMyLessonOrderDeleteData(orderNumber,token).enqueue(new Callback<SuccessBean>() {
                        @Override
                        public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                            SuccessBean body = response.body();
                            if (body!=null&&body.getErrMsg()==null){
                                boolean data = body.getData();
                                if (data==true){
                                    Toast.makeText(context,"删除订单成功",Toast.LENGTH_SHORT).show();
                                    Log.e("postionposition",position+"");
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }
                                else {
                                    Toast.makeText(context,"删除订单失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<SuccessBean> call, Throwable t) {
                            Toast.makeText(context,"网络问题，请检查网络状态",Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });
        }
        //解决复用
        initFuYong();
        final MyLessonDingDanViewHold finalMyViewHold = myViewHold;
        myViewHold.quXiao_bt.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             //取消订单
             quXiaoOrder();
         }
         private void quXiaoOrder() {
             PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
             aClass.getMyLessonOrderQuXiaoData(orderNumber,token).enqueue(new Callback<SuccessBean>() {
                 @Override
                 public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                     SuccessBean body = response.body();
                     if (body!=null&&body.getErrMsg()==null){
                         boolean data = body.getData();
                         if (data==true){
                             Toast.makeText(context,"取消订单成功",Toast.LENGTH_SHORT).show();
                             finalMyViewHold.orderStatus_tv.setText("已取消");
                             list.get(position).setTag(1);
                            // myViewHold.orderStatus_tv.setEnabled(true);
                             finalMyViewHold.quXiao_bt.setVisibility(View.GONE);
                             finalMyViewHold.pay_bt.setVisibility(View.GONE);
                             finalMyViewHold.delete_bt.setVisibility(View.VISIBLE);
                             finalMyViewHold.delete_bt.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {
                                     //删除订单
                                     deleteOrder();
                                 }
                                 private void deleteOrder() {
                                     PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                                     aClass.getMyLessonOrderDeleteData(orderNumber,token).enqueue(new Callback<SuccessBean>() {
                                         @Override
                                         public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                                             SuccessBean body = response.body();
                                             if (body!=null&&body.getErrMsg()==null){
                                                 boolean data = body.getData();
                                                 if (data==true){
                                                     Toast.makeText(context,"删除订单成功",Toast.LENGTH_SHORT).show();
                                                     Log.e("postionposition",position+"");
                                                     list.remove(position);
                                                     notifyDataSetChanged();
                                                 }
                                                 else {
                                                     Toast.makeText(context,"删除订单失败",Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         }
                                         @Override
                                         public void onFailure(Call<SuccessBean> call, Throwable t) {
                                             Toast.makeText(context,"网络问题，请检查网络状态",Toast.LENGTH_SHORT).show();

                                         }
                                     });

                                 }

                             });

                         }

                     }
                 }

                 @Override
                 public void onFailure(Call<SuccessBean> call, Throwable t) {
                     Toast.makeText(context,"网络问题，请检查网络状态",Toast.LENGTH_SHORT).show();
                 }
             });
         }

     });

        return convertView;

    }

    private void initFuYong(){
        if (list.get(weiZhi).getTag()==1){
            myViewHold.orderStatus_tv.setText("已取消");
            myViewHold.quXiao_bt.setVisibility(View.GONE);
            myViewHold.pay_bt.setVisibility(View.GONE);
            myViewHold.delete_bt.setVisibility(View.VISIBLE);
        }
    }

    static class MyLessonDingDanViewHold{
        TextView orderNum_tv,orderStatus_tv,orderLessonName_tv,orderPrice_tv,oderCreateTime_tv,orderIfSuccess_tv;
        ImageView lesson_iv;
        Button quXiao_bt,pay_bt,delete_bt;
        RelativeLayout content_rl;
    }
}
