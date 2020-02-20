package com.example.administrator.zahbzayxy.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.zahbzayxy.R;


public abstract class BaseDialog {
    protected Dialog mBuilder;
    protected Context mContext;

    protected BaseDialog(Context context) {
        this.mContext = context;
    }

    protected Dialog initBuilder() {
        mBuilder = new Dialog(mContext, R.style.Dialog_Fullscreen);
        Window window = mBuilder.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        return mBuilder;
    }

    public void setOnKeyListener(final DialogInterface.OnKeyListener onKeyListener) {
        if (mBuilder != null) {
            mBuilder.setOnKeyListener(onKeyListener);
        }
    }

    public boolean isShowing() {
        return mBuilder.isShowing();
    }

    protected abstract void dismiss();

    protected abstract void cancel();
}
