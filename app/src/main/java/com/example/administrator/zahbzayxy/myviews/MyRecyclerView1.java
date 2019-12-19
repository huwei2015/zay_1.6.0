package com.example.administrator.zahbzayxy.myviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by ${ZWJ} on 2017/12/25 0025.
 */
public class MyRecyclerView1 extends RecyclerView {
    public MyRecyclerView1(Context context) {
        super(context);
    }
    public MyRecyclerView1(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int i = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, i);
    }
}
