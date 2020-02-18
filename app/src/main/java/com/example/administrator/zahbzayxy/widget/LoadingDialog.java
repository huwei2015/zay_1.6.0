package com.example.administrator.zahbzayxy.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;


public class LoadingDialog {

    private Dialog mDialog;
    private Context mContext;
    private ProgressBarLayout mBarLayout;

    public LoadingDialog(Context context) {
        this.mContext = context;
        mDialog = initBuilder();
    }

    private Dialog initBuilder() {
        mDialog = new Dialog(mContext);
        mDialog = new Dialog(mContext, R.style.Dialog_Fullscreen);
        Window window = mDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        mDialog.setContentView(R.layout.mine_dialog_loading_dialog);
        mBarLayout = mDialog.findViewById(R.id.loading_dialog_loading_view);
        return mDialog;
    }

    public void show(){
        if(((Activity)mContext).isFinishing()) return;
        try {
            mDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setShowText(String showText){
        mBarLayout.setShowContent(TextUtils.isEmpty(showText)?"":showText);
    }

    public boolean isShow(){
        if(mDialog != null){
            return mDialog.isShowing();
        }
        return false;
    }

    public void dismiss() {
        if(mDialog != null){
            mDialog.dismiss();
        }
    }

    public void cancel() {
        if(mDialog != null){
            mDialog.cancel();
        }
    }

}
