package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.myviews.CateTextView;
import com.example.administrator.zahbzayxy.myviews.ImageRadiusView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class Lv1CateAdapter extends RecyclerView.Adapter<Lv1CateAdapter.ViewHolder> {

    private List<CourseCatesBean.DataBean.Cates> lists;
    private Context context;
    private RecyclerView gundongRV;
    List<CateTextView> ctvs=new ArrayList<CateTextView>();
    private Integer cateId=0;
    private OnClickListener mOnClickListener;


    public interface OnClickListener {
        public void setSelectedNum(int num);
    }

    public Lv1CateAdapter(List<CourseCatesBean.DataBean.Cates> list, Context context,RecyclerView gundongRV){
        this.lists=list;
        this.context=context;
        this.gundongRV=gundongRV;
        mOnClickListener = (OnClickListener) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CateTextView recyText;
        public ViewHolder(View itemView) {
            super(itemView);
            recyText =  itemView.findViewById(R.id.recyTV);
            recyText.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            ctvs.add(recyText);
            recyText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String txt=((CateTextView) v).getStr();
                    if("0".equals(txt)){
                        for (int i = 0; i < ctvs.size(); i++) {
                            CateTextView cv=ctvs.get(i);
                            cv.setStr("0");
                            cv.setText(cv.getText());
                            cv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                            cv.setTextColor(context.getResources().getColor(R.color.zx_text_color));
                        }
                        Drawable drawableLeft = context.getResources().getDrawable(
                                R.mipmap.catelv1_sel);
                        ((CateTextView) v).setStr("1");
                        ((CateTextView) v).setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLeft);
                        ((CateTextView) v).setTextColor(context.getResources().getColor(R.color.shikan_text_color));
                        cateId=((CateTextView) v).getDataId();
                        mOnClickListener.setSelectedNum(cateId);
                    }else{
                        ((CateTextView) v).setStr("0");
                        ((CateTextView) v).setText(((CateTextView) v).getText());
                        ((CateTextView) v).setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        ((CateTextView) v).setTextColor(context.getResources().getColor(R.color.zx_text_color));
                        mOnClickListener.setSelectedNum(0);
                    }
                }
            });

        }
    }

    @Override
    public Lv1CateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Lv1CateAdapter.ViewHolder holder = new Lv1CateAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cates, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(Lv1CateAdapter.ViewHolder holder, int position) {
        holder.recyText.setText(lists.get(position).getCateName());
        holder.recyText.setStr("0");
        holder.recyText.setDataId(lists.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

}


