package com.example.administrator.zahbzayxy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.NewOrderDetailActivity;
import com.example.administrator.zahbzayxy.activities.PayUiActivity;
import com.example.administrator.zahbzayxy.beans.NBMyAllOrderBean;
import com.example.administrator.zahbzayxy.beans.OrderIsOutOfDateBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by ${ZWJ} on 2017/7/11 0011.
 */
public class NBMyAllOrderAdapter extends BaseAdapter {
    private List<NBMyAllOrderBean.DataEntity.RowsEntity> list;
    LayoutInflater inflater;
    Context context;
    private String token;
    private int weiZhi;
    NbAllOrderViewHold viewHold=null;
    String logo;
    public NBMyAllOrderAdapter(List<NBMyAllOrderBean.DataEntity.RowsEntity> list, Context context, String token) {
        this.list = list;
        this.context = context;
        this.token=token;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        NBMyAllOrderBean.DataEntity.RowsEntity rowsEntity = list.get(position);

        weiZhi = position;
        String orderNumber = null;
        if (convertView == null) {
            viewHold = new NbAllOrderViewHold();
            convertView = inflater.inflate(R.layout.item_nb_myallorder1, parent, false);
            viewHold.orderType_tv = convertView.findViewById(R.id.nb_orderType_tv);
            viewHold.payStatus_tv= convertView.findViewById(R.id.orderStatus_tv);
            viewHold.orderName_tv= convertView.findViewById(R.id.orderName_tv);
            viewHold.orderNum_tv = convertView.findViewById(R.id.myOrderNum_tv);
            viewHold.orderPrice_tv= convertView.findViewById(R.id.order_payMoney_tv);
            viewHold.goOrderDetail_tv= convertView.findViewById(R.id.go_order_detail_tv);
            viewHold.cancleOrder_bt= convertView.findViewById(R.id.cancleOrder_bt);
            viewHold.goPay_bt= convertView.findViewById(R.id.goPay_tv);
            viewHold.aginBuy_bt= convertView.findViewById(R.id.aginBuy_tv);
            viewHold.delete_order_iv= convertView.findViewById(R.id.delete_order_iv);
            viewHold.lessonOrder_iv= convertView.findViewById(R.id.lesson_order_iv);
            viewHold.cancle_goPay_rl= convertView.findViewById(R.id.cancle_goPay_rl);
            viewHold.allPay_rl= convertView.findViewById(R.id.allPay_rl);
            viewHold.taocan_line_iv= convertView.findViewById(R.id.taocan_iv);
            viewHold.taocanType_tv= convertView.findViewById(R.id.taocan_type_order_tv);
            viewHold.orderDetail_ll= convertView.findViewById(R.id.orderDetail_ll);
            convertView.setTag(viewHold);

        } else {
            viewHold = (NbAllOrderViewHold) convertView.getTag();

        }
         final int orderType = rowsEntity.getOrderType();
        final String orderTypeName = rowsEntity.getOrderTypeName();
        if (!String.valueOf(orderType).isEmpty()) {
            if (orderType == 5) {
                viewHold.orderType_tv.setText("课程订单");
                viewHold.lessonOrder_iv.setVisibility(View.VISIBLE);
                viewHold.taocan_line_iv.setVisibility(View.GONE);
                viewHold.taocanType_tv.setVisibility(View.GONE);
                List<NBMyAllOrderBean.DataEntity.RowsEntity.DetailEntity> detail = rowsEntity.getDetail();
                int size = detail.size();
                if (size>0) {
                    logo= detail.get(0).getLogo();
                }

            } else if (orderType == 8) {
                viewHold.orderType_tv.setText("题库订单");
                viewHold.lessonOrder_iv.setVisibility(View.GONE);
                viewHold.taocan_line_iv.setVisibility(View.VISIBLE);
                viewHold.taocanType_tv.setVisibility(View.VISIBLE);
            } else if (orderType == 10) {
                viewHold.orderType_tv.setText("套餐订单");
                viewHold.lessonOrder_iv.setVisibility(View.GONE);
                viewHold.taocan_line_iv.setVisibility(View.VISIBLE);
                viewHold.taocanType_tv.setVisibility(View.VISIBLE);
            }
            else if (orderType == 9) {
                viewHold.orderType_tv.setText("充值订单");
                viewHold.lessonOrder_iv.setVisibility(View.GONE);
            }
            if(!orderTypeName.isEmpty()){
                viewHold.orderType_tv.setText(orderTypeName);
            }
        }



        int payStatus = rowsEntity.getPayStatus();
        if (Integer.valueOf(payStatus)!=null){
            if (payStatus==1){
                viewHold.payStatus_tv.setText("已支付");
                viewHold.allPay_rl.setVisibility(View.GONE);
            } else {
                viewHold.payStatus_tv.setText("待支付");
                viewHold.allPay_rl.setVisibility(View.VISIBLE);
                viewHold.aginBuy_bt.setVisibility(View.GONE);
                viewHold.cancle_goPay_rl.setVisibility(View.VISIBLE);
            }
        }


        int orderStatus = rowsEntity.getOrderStatus();
        if (Integer.valueOf(orderStatus)!=null){
            //已取消
            if (orderStatus==2){

               /* viewHold.goPay_bt.setVisibility(View.GONE);
                viewHold.cancle_bt.setVisibility(View.GONE);
                viewHold.delete_bt.setVisibility(View.VISIBLE);*/
                viewHold.payStatus_tv.setText("已取消");
                viewHold.cancle_goPay_rl.setVisibility(View.GONE);
              //  viewHold.aginBuy_bt.setVisibility(View.VISIBLE);
            }if (orderStatus==0){//待支付
                viewHold.aginBuy_bt.setVisibility(View.GONE);
                viewHold.cancle_goPay_rl.setVisibility(View.VISIBLE);
                viewHold.goPay_bt.setVisibility(View.VISIBLE);
            }


            /*else{
                viewHold.aginBuy_bt.setVisibility(View.VISIBLE);
                viewHold.cancle_goPay_rl.setVisibility(View.GONE);
            }*/
        }

        List<NBMyAllOrderBean.DataEntity.RowsEntity.DetailEntity> detail = rowsEntity.getDetail();
        int size = detail.size();
        if (size>0){
            String name = detail.get(0).getName();
            viewHold.orderName_tv.setText(name);
            String packageName = detail.get(0).getPackageName();
            if (!TextUtils.isEmpty(packageName)){
                viewHold.taocanType_tv.setText(packageName);
            }
        }

         orderNumber = rowsEntity.getOrderNumber();
         viewHold.orderNum_tv.setText("订单号:"+orderNumber);
        if (!TextUtils.isEmpty(logo)){
            Picasso.with(context).load(logo).placeholder(R.mipmap.default_ic).into(viewHold.lessonOrder_iv);
        }

        final BigDecimal payMoney = rowsEntity.getPayMoney();
        viewHold.orderPrice_tv.setText("¥" + payMoney + "");

        final String finalOrderNumber = orderNumber;
        viewHold.delete_order_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("提示");
                dialog.setMessage("确定要删除这条记录吗？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleOrder();
                    }
                }).show();
            }
            private void deleOrder() {
                PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                aClass.getMyLessonOrderDeleteData(finalOrderNumber, token).enqueue(new Callback<SuccessBean>() {
                    @Override
                    public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                        SuccessBean body = response.body();
                        if (body != null && body.getErrMsg() == null) {
                            boolean data = body.getData();
                            if (data == true) {
                                Toast.makeText(context, "删除订单成功", Toast.LENGTH_SHORT).show();
                                list.remove(getItem(position));
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "删除订单失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessBean> call, Throwable t) {
                        Toast.makeText(context, "网络问题，请检查网络状态", Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });

        //查看订单详情
        final int id = rowsEntity.getId();
        viewHold.goOrderDetail_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                bundle.putInt("orderId", id);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

        viewHold.orderDetail_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", token);
                bundle.putInt("orderId", id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        final String finalOrderNumber1 = orderNumber;
        viewHold.goPay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderType==8) {
                    initOderStatus();
                }else {
                    initGoPay();
                }

            }

            private void initOderStatus() {
                PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                aClass.getOrderIsOutOfDateData(finalOrderNumber1,token).enqueue(new Callback<OrderIsOutOfDateBean>() {
                    @Override
                    public void onResponse(Call<OrderIsOutOfDateBean> call, Response<OrderIsOutOfDateBean> response) {
                        if (response!=null){
                            int code = response.code();
                            if (code==200){
                                OrderIsOutOfDateBean body = response.body();
                                if (body!=null){
                                        OrderIsOutOfDateBean.DataEntity data = body.getData();
                                        if (data!=null) {
                                            int overdue = data.getOverdue();
                                            if (overdue == 1) {
                                                Toast.makeText(context, "该订单已过期请重新下单", Toast.LENGTH_SHORT).show();
                                            } else {
                                                initGoPay();
                                            }
                                        }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<OrderIsOutOfDateBean> call, Throwable t) {

                    }
                });

            }
            private void initGoPay() {
                Intent intent = new Intent(context, PayUiActivity.class);
                intent.putExtra("orderNumber", finalOrderNumber1);
                intent.putExtra("testPrice", String.valueOf(payMoney));
                Bundle bundle=new Bundle();
                if (orderType==5) {
                    bundle.putBoolean("isLessonOrder", true);
                }
                Log.e("isLessonOrder","1111"+orderType);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


        final String finalOrderNumber2 = orderNumber;
        initFuYong();
        final NbAllOrderViewHold myOrderViewHold = viewHold;
        myOrderViewHold.cancleOrder_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleOrder();
            }

            private void cancleOrder() {
                PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
                aClass.getMyLessonOrderQuXiaoData(finalOrderNumber2,token).enqueue(new Callback<SuccessBean>() {
                    @Override
                    public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                        SuccessBean body = response.body();
                        String code= body.getCode();
                        boolean data = body.getData();
                        if (data == true) {
                            Toast.makeText(context, "取消订单成功", Toast.LENGTH_SHORT).show();
                            myOrderViewHold.payStatus_tv.setText("已取消");
                            list.get(weiZhi).setTag(1);
                            myOrderViewHold.cancle_goPay_rl.setVisibility(View.GONE);
                           // myOrderViewHold.aginBuy_bt.setVisibility(View.VISIBLE);

                        }else if (code.equals("99999")){
                            Toast.makeText(context, "系统异常", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context, "取消订单失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessBean> call, Throwable t) {
                        Toast.makeText(context, "网络问题，请检查网络状态", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });

        return convertView;
    }
    private void initFuYong(){
        if (list.get(weiZhi).getTag()==1){
            viewHold.cancle_goPay_rl.setVisibility(View.GONE);
           // viewHold.aginBuy_bt.setVisibility(View.VISIBLE);
        }
    }


    private static class NbAllOrderViewHold{
        private TextView orderType_tv,payStatus_tv,orderName_tv,orderNum_tv,orderPrice_tv,
                goOrderDetail_tv,cancleOrder_bt,goPay_bt,aginBuy_bt;

        private ImageView delete_order_iv,lessonOrder_iv;
        private RelativeLayout cancle_goPay_rl,allPay_rl;
        private LinearLayout taocan_line_iv,orderDetail_ll;
        private TextView taocanType_tv;

    }
}
