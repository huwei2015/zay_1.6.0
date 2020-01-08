package com.example.administrator.zahbzayxy.manager.showFile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.administrator.zahbzayxy.activities.ShowImgActivity;
import com.example.administrator.zahbzayxy.myinterface.ShowFileInter;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

public class ShowFileImgImpl implements ShowFileInter {
    private Context mContext;

    public ShowFileImgImpl(Context context) {
        this.mContext = context;
    }


    @Override
    public void openFile(String fileName, String url) {
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showLongInfo("图片加载失败");
            return;
        }
        Intent intent = new Intent(mContext, ShowImgActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ShowImgActivity.SHOW_IMG_FILE_NAME_KEY, fileName);
        bundle.putString(ShowImgActivity.SHOW_IMG_FILE_URL_KEY, url);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void closeOpen() {
    }
}
