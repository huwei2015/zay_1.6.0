package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.myviews.ImageRadiusView;

import java.util.List;


/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class Lv1CateAdapter extends RecyclerView.Adapter<Lv1CateAdapter.ViewHolder> {

    private List<CourseCatesBean.DataBean.Cates> lists;
    private Context context;

    public Lv1CateAdapter(List<CourseCatesBean.DataBean.Cates> list, Context context){
        this.lists=list;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView recyText;
        public ViewHolder(View itemView) {
            super(itemView);
            recyText =  itemView.findViewById(R.id.recyTV);
            recyText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Drawable drawableLeft = context.getResources().getDrawable(
                            R.mipmap.catelv1_sel);
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawableLeft);
                    ((TextView) v).setTextColor(context.getResources().getColor(R.color.shikan_text_color));
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
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

}


