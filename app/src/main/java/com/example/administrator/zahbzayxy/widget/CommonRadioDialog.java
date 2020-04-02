package com.example.administrator.zahbzayxy.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.CommonRadioDialogAdapter;
import com.example.administrator.zahbzayxy.beans.LoginBean;
import com.example.administrator.zahbzayxy.utils.CustomLinearLayoutManager;

import java.util.List;

/**
 * Created by huwei.
 * Data 2020-03-30.
 * Time 20:33.
 */
public class CommonRadioDialog extends Dialog implements CommonRadioDialogAdapter.OnItemClickListener{
    private Context mContext;
    private ImageView img_back;
    private List<LoginBean.DataList> dialogModels;
    private RecyclerView mRecyclerView;
    private CommonRadioDialogAdapter dialogAdapter;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int postion);
        void onClose(View view);
    }
    public  CommonRadioDialog(Context context, List<LoginBean.DataList> dialogModels) {
        this(context, R.style.AlertActivity_AlertStyle);
        this.dialogModels=dialogModels;
    }

    protected CommonRadioDialog(Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        mRecyclerView = findViewById(R.id.recyclerview);
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dismiss();
                onItemClickListener.onClose(v);
            }
        });
        //recycleview初始化
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        dialogAdapter = new CommonRadioDialogAdapter(mContext,dialogModels);
        dialogAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(dialogAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        onItemClickListener.onClick(dialogModels.get(position).getPlatformId());
    }
}
