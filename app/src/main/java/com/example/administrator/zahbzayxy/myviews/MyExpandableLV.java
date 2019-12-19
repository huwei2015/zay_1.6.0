package com.example.administrator.zahbzayxy.myviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by ${ZWJ} on 2017/2/6 0006.
 */
public class MyExpandableLV extends ExpandableListView {
    public MyExpandableLV(Context context) {
        super(context);
    }
    public MyExpandableLV(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int h = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, h);
    }

}
