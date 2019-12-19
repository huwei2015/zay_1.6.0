package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.PayUiActivity;
import com.example.administrator.zahbzayxy.beans.PMyTestOrderBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ${ZWJ} on 2017/4/7 0007.
 */
public class PMyTestOrderAdapter extends BaseAdapter {
    private List<PMyTestOrderBean.DataBean.QuesLibOrdersBean> list;
    private Context context;
    LayoutInflater inflater;
    private String token;
    private int weiZhi;
    MyTestOrderViewHold viewHold=null;
    public PMyTestOrderAdapter(List<PMyTestOrderBean.DataBean.QuesLibOrdersBean> list, Context context,String token) {
        this.list = list;
        this.context = context;
        this.token=token;
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

        if (convertView==null){
            viewHold=new MyTestOrderViewHold();
            convertView= inflater.inflate(R.layout.item_pmytest_order_layout,parent,false);
            viewHold.orderNum_tv= (TextView) convertView.findViewById(R.id.pMyTestOrderNum_tv);
            viewHold.orderTestName_tv= (TextView) convertView.findViewById(R.id.pMyTestName_tv);
            viewHold.orderPrice_tv= (TextView) convertView.findViewById(R.id.pMyTestOrderPrice_tv);
            viewHold.test_iv= (ImageView) convertView.findViewById(R.id.pMyTestOrder_iv);
            viewHold.payStatus_tv= (TextView) convertView.findViewById(R.id.testPayStatus_tv);
            viewHold.oderCreateTime_tv= (TextView) convertView.findViewById(R.id.pMyTestOrderCreateTime_tv);
            viewHold.quXiao_bt= (Button) convertView.findViewById(R.id.pMyTestOrderQuXiao_bt);
            viewHold.pay_bt= (Button) convertView.findViewById(R.id.pMyTestOrderziFu_bt);
            viewHold.delete_bt= (Button) convertView.findViewById(R.id.pMyTestOrderDelete_bt);
            convertView.setTag(viewHold);

        }
        else {
            viewHold = (MyTestOrderViewHold) convertView.getTag();
        }
        weiZhi=position;
        //解决复用
        initFuYong();
        PMyTestOrderBean.DataBean.QuesLibOrdersBean quesLibOrdersBean = list.get(position);
       final String orderNumber = quesLibOrdersBean.getOrderNumber();
        final String payMoney = quesLibOrdersBean.getPayMoney();
        viewHold.orderNum_tv.setText(orderNumber);
        viewHold.orderTestName_tv.setText(quesLibOrdersBean.getQuesLibName());
        viewHold.orderPrice_tv.setText("单价:"+quesLibOrdersBean.getPayMoney());
        viewHold.oderCreateTime_tv.setText(quesLibOrdersBean.getCreateTime());
        String quesLibImageUrl = quesLibOrdersBean.getQuesLibImageUrl();
        if (!TextUtils.isEmpty(quesLibImageUrl)){
            Picasso.with(context).load(quesLibImageUrl).placeholder(R.mipmap.loading_png).into(viewHold.test_iv);
        }
        //支付状态
        int payStatus = quesLibOrdersBean.getPayStatus();
        int orderStatus = quesLibOrdersBean.getOrderStatus();
        if (payStatus==1){
            viewHold.payStatus_tv.setText("已支付");
            viewHold.quXiao_bt.setVisibility(View.GONE);
            viewHold.pay_bt.setVisibility(View.GONE);
            viewHold.delete_bt.setVisibility(View.VISIBLE);

            viewHold.delete_bt.setOnClickListener(new View.OnClickListener() {
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
                                    list.remove(getItem(position));
                                    notifyDataSetChanged();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<SuccessBean> call, Throwable t) {

                        }
                    });

                }
            });


        }if (payStatus==0){
            viewHold.payStatus_tv.setText("未支付");
            viewHold.quXiao_bt.setVisibility(View.VISIBLE);
            viewHold.pay_bt.setVisibility(View.VISIBLE);
            viewHold.delete_bt.setVisibility(View.GONE);
        }if (orderStatus==2){
            viewHold.payStatus_tv.setText("已取消");
            viewHold.quXiao_bt.setVisibility(View.GONE);
            viewHold.pay_bt.setVisibility(View.GONE);
            viewHold.delete_bt.setVisibility(View.VISIBLE);
            viewHold.delete_bt.setOnClickListener(new View.OnClickListener() {
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
                                    list.remove(getItem(position));
                                    notifyDataSetChanged();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<SuccessBean> call, Throwable t) {

                        }
                    });

                }
            });
        }
        //点击支付按钮时跳去支付界面
        viewHold.pay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PayUiActivity.class);
                intent.putExtra("orderNumber",orderNumber);
                intent.putExtra("token",token);
                intent.putExtra("testPrice",payMoney);
                context.startActivity(intent);
            }
        });
        //点击取消订单时
        final MyTestOrderViewHold finalViewHold = viewHold;
        viewHold.quXiao_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                aClass.getMyLessonOrderQuXiaoData(orderNumber,token).enqueue(new Callback<SuccessBean>() {
                    @Override
                    public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                        SuccessBean body = response.body();
                        if (body!=null&&body.getErrMsg()==null){
                            boolean data = body.getData();
                            if (data==true){
                                Toast.makeText(context,"取消订单成功",Toast.LENGTH_SHORT).show();
                                //取消订单成功时，取消订单和去支付订单变为不可见，删除订单变为可见
                               finalViewHold.quXiao_bt.setVisibility(View.GONE);
                                finalViewHold.pay_bt.setVisibility(View.GONE);
                                finalViewHold.delete_bt.setVisibility(View.VISIBLE);

                                //删除订单
                                finalViewHold.delete_bt.setOnClickListener(new View.OnClickListener() {
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
                                                        list.remove(getItem(position));
                                                        notifyDataSetChanged();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<SuccessBean> call, Throwable t) {

                                            }
                                        });
                                    }
                                });
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessBean> call, Throwable t) {

                    }
                });
            }
        });
        return convertView;
    }
    private void initFuYong(){
        if (list.get(weiZhi).getTag()==1){

            viewHold.payStatus_tv.setText("已取消");
            viewHold.quXiao_bt.setVisibility(View.GONE);
            viewHold.pay_bt.setVisibility(View.GONE);
            viewHold.delete_bt.setVisibility(View.VISIBLE);
        }
    }

    static class MyTestOrderViewHold {
        TextView orderNum_tv, payStatus_tv, orderTestName_tv, orderPrice_tv, oderCreateTime_tv, orderIfSuccess_tv;
        ImageView test_iv;
        Button quXiao_bt, pay_bt, delete_bt;

    }
}