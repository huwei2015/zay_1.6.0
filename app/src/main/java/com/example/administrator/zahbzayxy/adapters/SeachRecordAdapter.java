package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;

import java.util.List;


/**
 * Created by yi.huangxing on 17/12/13.类描述:
 */

public class SeachRecordAdapter extends BaseRecycleAdapter<String> {
    public SeachRecordAdapter(List<String> datas, Context mContext) {
        super(datas, mContext);
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {

        TextView textView= (TextView) holder.getView(R.id.tv_record);

        textView.setText(datas.get(position));

        holder.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=mRvItemDeleteOnclickListener){
                    mRvItemDeleteOnclickListener.RvDeleteItemOnclick(position);
                }
            }
        });
        holder.getView(R.id.search_history_rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索内容详情页
                if (null!=mRvItemOnclickListener){
                    mRvItemOnclickListener.RvItemOnclick(datas.get(position)+"");
                }
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.search_item;
    }
}
