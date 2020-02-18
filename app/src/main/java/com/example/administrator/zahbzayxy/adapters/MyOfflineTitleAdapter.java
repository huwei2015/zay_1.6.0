package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LearnNavigationBean;
import com.example.administrator.zahbzayxy.utils.DisplayUtil;
import com.example.administrator.zahbzayxy.utils.Indicator;

import java.util.ArrayList;
import java.util.List;

public class MyOfflineTitleAdapter extends Indicator.IndicatorAdapter {

    private List<LearnNavigationBean.LearnListBean> mDataList;
    private Indicator mIndicator;
    private Context mContext;
    private OnLineTitleItemClickListener mItemClickListener;

    public MyOfflineTitleAdapter(Context context, List<LearnNavigationBean.LearnListBean> dataList, Indicator indicator) {
        super();
        if (dataList == null) dataList = new ArrayList<>();
        this.mDataList = dataList;
        this.mIndicator = indicator;
        this.mContext = context;
    }

    public void setData(List<LearnNavigationBean.LearnListBean> dataList) {
        if (dataList == null) mDataList = new ArrayList<>();
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_top, parent, false);
        }
        TextView textView = (TextView) convertView;
        //用了固定宽度可以避免TextView文字大小变化，tab宽度变化导致tab抖动现象
        textView.setWidth(DisplayUtil.dipToPix(mContext, 80));
        String centerName = mDataList.get(position).getCateName();
        textView.setText(centerName);

        mIndicator.setOnIndicatorItemClickListener((View clickItemView, int viewPosition) -> {
            if (mItemClickListener != null) {
                mItemClickListener.onClick(clickItemView, viewPosition);
            }
            return false;
        });

        return convertView;
    }

    public void setOnItemClickListener(OnLineTitleItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public interface OnLineTitleItemClickListener {
        void onClick(View clickItemView, int position);
    }
}
