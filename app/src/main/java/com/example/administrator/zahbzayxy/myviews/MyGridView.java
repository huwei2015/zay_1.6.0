package com.example.administrator.zahbzayxy.myviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by ${ZWJ} on 2017/1/4 0004.
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec,i);
    }
}
