package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.NotThroughBean;
import com.example.administrator.zahbzayxy.beans.TimeData;
import com.example.administrator.zahbzayxy.utils.TextAndPictureUtil;

import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-02.
 * Time 15:48.
 * 学习未通过
 */
public class NotThrougAdapter extends RecyclerView.Adapter<NotThrougAdapter.ViewHodler>{
    private Context mcontext;
    private List<NotThroughBean.THrougListData> througListBeans;
    private OnItemClickListener onItemClickListener;

    private  int mPosition;
    private boolean flag=false;

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public NotThrougAdapter(Context mcontext, List<NotThroughBean.THrougListData> througListBeans) {
        this.mcontext = mcontext;
        this.througListBeans = througListBeans;
    }

    public void setList(List<NotThroughBean.THrougListData> througListBeans) {
        this.througListBeans = througListBeans;
        notifyDataSetChanged();
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotThrougAdapter.ViewHodler(LayoutInflater.from(mcontext).inflate(R.layout.item_fragment_no_through,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position) {
        holder.title.setText(TextAndPictureUtil.getTextCssStyle(mcontext," "+througListBeans.get(position).getPackageName()+" ",througListBeans.get(position).getQuesLibName()));
        holder.title.setMovementMethod(LinkMovementMethod.getInstance());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=true;
                onItemClickListener.onItemClick(v,position);
                setmPosition(position);
                notifyDataSetChanged();
            }
        });

        if (position == getmPosition() && flag) {
            holder.selLL.setBackground(mcontext.getResources().getDrawable(R.drawable.item_bg));
        }else{
            holder.selLL.setBackground(mcontext.getResources().getDrawable(R.drawable.item_bg_nosel));
        }

    }

    @Override
    public int getItemCount() {
        return througListBeans !=null ? througListBeans.size() : 0;
    }

    static class ViewHodler extends RecyclerView.ViewHolder{
        private TextView title;
        private LinearLayout selLL;
        public ViewHodler(View itemView) {
            super(itemView);
            title =itemView.findViewById(R.id.title);
            selLL=itemView.findViewById(R.id.selLL);
        }
    }
}
