package com.example.administrator.zahbzayxy.myviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.administrator.zahbzayxy.myinterface.UpPx;

/**
 * Created by ${ZWJ} on 2017/2/16 0016.
 */
public class MyRecyclerView extends RecyclerView {
    int w;
    UpPx up;
    double x2 = 0;
    double x1 = 0;

    public void setUp(UpPx up) {
        this.up = up;
    }

    public MyRecyclerView(Context context) {
        super(context);
        w = context.getResources().getDisplayMetrics().widthPixels;
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        w=context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = e.getX();
                Log.e("============","=========="+x1);
                break;
            case MotionEvent.ACTION_UP:
                x2 = e.getX();
                Log.e("===============", "====="+x2);
                if(x2-x1>0&&x2-x1>w/8){
                   // up.upPx(1);//左
                    up.upPx(1);
                }else if(x1-x2>0&&x1-x2>w/8){
                    up.upPx(2);//右
                }else{
                    up.upPx(3);//不动
                }
                break;
        }
        return super.onTouchEvent(e);

    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int i = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, i);
    }

//解决ScrollView与RecyclerView横向滚动时的事件冲突

  /*  private float lastX, lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        boolean intercept = super.onInterceptTouchEvent(e);

        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = e.getX();
                lastY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 只要横向大于竖向，就拦截掉事件。
                float slopX = Math.abs(e.getX() - lastX);
                float slopY = Math.abs(e.getY() - lastY);
                //  Log.log("slopX=" + slopX + ", slopY="  + slopY);
                if( slopX >= slopY){
                    requestDisallowInterceptTouchEvent(true);
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        // Log.log("intercept"+e.getAction()+"=" + intercept);
        return intercept;
    }
*/
}
