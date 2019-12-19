package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;

/**
 * Created by ${ZWJ} on 2017/6/5 0005.
 */
public class ProgressBarLayoutFace extends RelativeLayout {
    private View view;
    private ProgressBarLayoutFace imgProgress;
    private TextView tvProgress;
    private ScaleAnimation animation;


    public ProgressBarLayoutFace(Context context) {
        super(context);
        addProgressView(context);
    }

    public ProgressBarLayoutFace(Context context, AttributeSet attrs) {
        super(context, attrs);
        addProgressView(context);
    }
    private void addProgressView(Context context){
        view= View.inflate(context, R.layout.progress_face, null);
        imgProgress=(ProgressBarLayoutFace)view.findViewById(R.id.imgProgress);
        tvProgress=(TextView)view.findViewById(R.id.tvProgress);
        addView(view);
    }
    public void show(){
        setVisibility(VISIBLE);
    }
    public void hide(){
        setVisibility(GONE);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }

}
