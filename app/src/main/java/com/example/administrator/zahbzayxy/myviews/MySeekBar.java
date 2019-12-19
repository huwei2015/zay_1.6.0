package com.example.administrator.zahbzayxy.myviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lijie on 2017/6/28.
 */

public class MySeekBar extends android.support.v7.widget.AppCompatSeekBar {
    public MySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // return super.onTouchEvent(event);
        return false;
    }

}
