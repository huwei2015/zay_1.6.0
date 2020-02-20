package com.example.administrator.zahbzayxy.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;


public class LoadingDialog extends BaseDialog {

    private Dialog mDialog;
    private ProgressBarLayout mBarLayout;

    public LoadingDialog(Context context) {
        super(context);
        mDialog = initBuilder();
        initView();
    }

    private Dialog initView() {
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

    @Override
    public void dismiss() {
        if(mDialog != null){
            mDialog.dismiss();
        }
    }

    @Override
    public void cancel() {
        if(mDialog != null){
            mDialog.cancel();
        }
    }

}
