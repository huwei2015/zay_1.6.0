package com.example.administrator.zahbzayxy.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;

/**
 * Created by ${ZWJ} on 2017/5/4 0004.
 */
public class ProgressBarLayout extends RelativeLayout {
    private View view;
    private ProgressBar imgProgress;
    private TextView tvProgress;
    private ScaleAnimation animation;

    public ProgressBarLayout(Context context) {
        super(context);
        addProgressView(context);
    }

    public ProgressBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        addProgressView(context);
    }

    private void addProgressView(Context context) {
        view = View.inflate(context, R.layout.progress_bar_layout, null);
        imgProgress = view.findViewById(R.id.imgProgress);
        tvProgress = view.findViewById(R.id.tvProgress);
        addView(view);
    }

    public void setShowContent(String content) {
        if (!TextUtils.isEmpty(content) && tvProgress != null) {
            tvProgress.setText(content);
        }
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }
}
