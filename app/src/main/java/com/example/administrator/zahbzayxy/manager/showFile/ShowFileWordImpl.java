package com.example.administrator.zahbzayxy.manager.showFile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.manager.DownloadManager;
import com.example.administrator.zahbzayxy.myinterface.ShowFileInter;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.example.administrator.zahbzayxy.utils.Utils;

import java.io.File;

public class ShowFileWordImpl implements ShowFileInter {

    private Activity mActivity;
    private DownloadManager mDownload;
    private ProgressBarLayout mLoading;

    public ShowFileWordImpl(Activity activity) {
        this.mActivity = activity;
    }

    public void setLoadingView(ProgressBarLayout loadingView) {
        this.mLoading = loadingView;
        mLoading.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void openFile(String fileName, String url) {
        if (TextUtils.isEmpty(fileName) || TextUtils.isEmpty(url)) {
            ToastUtils.showLongInfo("获取服务端文件失败");
            return;
        }
        String fileNameEnd = url.substring(url.lastIndexOf("."));
        fileName = Utils.md5Decode32(url) + fileNameEnd;
        if (mDownload == null) mDownload = new DownloadManager(mActivity, url, fileName);

        mDownload.setOnDownloadListener(new DownloadManager.DownloadFileListener() {
            @Override
            public void onStart() {
                if (mLoading != null) {
                    mLoading.show();
                }
            }

            @Override
            public void onSuccess(String filePath) {
                if (mLoading != null) {
                    mActivity.runOnUiThread(() -> mLoading.hide());
                }
                mActivity.runOnUiThread(() -> {
                    if (TextUtils.isEmpty(filePath)) {
                        ToastUtils.showLongInfo("获取服务器文件失败");
                        return;
                    }
                    try {
                        mActivity.startActivity(getWordFileIntent(filePath));
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.showLongInfo("未查询到打开该文件的应用");
                    }
                });
            }

            @Override
            public void download(int progress) {
            }

            @Override
            public void onFailed() {
                if (mLoading != null) {
                    mActivity.runOnUiThread(() -> mLoading.hide());
                }
                ToastUtils.showLongInfo("文件获取失败");
            }

        });
        mDownload.download();
    }

    // android获取一个用于打开Word文件的intent
    public Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".fileprovider", new File(param));
        } else {
            uri = Uri.fromFile(new File(param));

        }
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }


    @Override
    public void closeOpen() {
        if (mLoading != null) {
            mLoading.hide();
        }
        if (mDownload != null) {
            mDownload.cancelDownload();
        }
    }
}
