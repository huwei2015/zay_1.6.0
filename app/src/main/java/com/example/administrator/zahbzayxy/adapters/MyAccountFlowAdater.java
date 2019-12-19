package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.MyAccountFlowBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/5/5 0005.
 */
public class MyAccountFlowAdater extends BaseAdapter {
    private List<MyAccountFlowBean.DataBean.AccountFlowsBean>list;
    private Context context;
    private LayoutInflater inflater;

    public MyAccountFlowAdater(List<MyAccountFlowBean.DataBean.AccountFlowsBean> list, Context context) {
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
        PayDetailViewHold viewHold;
        if (convertView==null){
            viewHold=new PayDetailViewHold();
            convertView=inflater.inflate(R.layout.item_mymoney_detail,parent,false);
            viewHold.moneyDetail_tv= (TextView) convertView.findViewById(R.id.payDetail_tv);
            viewHold.payDate_tv= (TextView) convertView.findViewById(R.id.payDate_tv);
            viewHold.moneyDetail_iv= (ImageView) convertView.findViewById(R.id.payDetail_iv);
            convertView.setTag(viewHold);
        }else {
            viewHold= (PayDetailViewHold) convertView.getTag();
        }
        MyAccountFlowBean.DataBean.AccountFlowsBean accountFlowsBean = list.get(position);
        int tradeType = accountFlowsBean.getTradeType();
        String amount = accountFlowsBean.getAmount();
        String createTime = accountFlowsBean.getCreateTime();
        if (tradeType==0){//	0:支出；1：充值
            viewHold.moneyDetail_tv.setText("消费:"+amount);
            viewHold.moneyDetail_iv.setImageResource(R.mipmap.account_close);

        }else {
            viewHold.moneyDetail_tv.setText("充值:"+amount);
            viewHold.moneyDetail_iv.setImageResource(R.mipmap.acccont_add);

        }
        viewHold.payDate_tv.setText(createTime);
        return convertView;
    }
  static class PayDetailViewHold{
      TextView moneyDetail_tv,payDate_tv;
      ImageView moneyDetail_iv;

  }
}
