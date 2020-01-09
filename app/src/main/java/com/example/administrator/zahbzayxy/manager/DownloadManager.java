package com.example.administrator.zahbzayxy.manager;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class DownloadManager {

    private String mDownloadUrl;
    private String mFilePath;
    private String mFileName;
    private DownloadFileListener mListener;
    private Context mContext;
    private boolean mCancel;


    public DownloadManager(Context context, String downloadUrl, String fileName) {
        this(context, downloadUrl, fileName,null);
    }

    public DownloadManager(Context context, String downloadUrl, String fileName, DownloadFileListener listener) {
        this.mDownloadUrl = downloadUrl;
        this.mListener = listener;
        this.mContext = context;
        this.mFileName = fileName;
    }

    public void download() {
        if (mListener != null) mListener.onStart();
        if (TextUtils.isEmpty(mDownloadUrl)) {
            if (mListener != null) mListener.onFailed();
            return;
        }
        if (fileExit()) {
            if (mListener != null) mListener.onSuccess(filePath() + mFileName);
            return;
        }
        new Thread(() -> {
            try {
                URL url = new URL(mDownloadUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                // 下载超时时间
                con.setReadTimeout(1000*60);
                con.setConnectTimeout(1000*30);
                con.setRequestProperty("Charset", "UTF-8");
                con.setRequestMethod("GET");
                if (con.getResponseCode() == 200) {
                    InputStream is = con.getInputStream();//获取输入流
                    FileOutputStream fileOutputStream = null;//文件输出流
                    String localFilePath = filePath() + mFileName;
                    if (is != null) {
                        fileOutputStream = new FileOutputStream(localFilePath);//指定文件保存路径，代码看下一步
                        byte[] buf = new byte[1024];
                        int ch;
                        while ((ch = is.read(buf)) != -1) {
                            if (mCancel) break;
                            fileOutputStream.write(buf, 0, ch);//将获取到的流写入文件中
                        }
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    if (mListener != null) mListener.onSuccess(localFilePath);
                } else {
                    if (mListener != null) mListener.onFailed();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (mListener != null) mListener.onFailed();
            }
        }).start();

    }

    private boolean fileExit() {
        String filePath = filePath() + mFileName;
        File file = new File(filePath);
        return file.exists();
    }


    public String filePath() {
        String filePath = "";
        if (TextUtils.isEmpty(mFilePath)) {
            filePath = getDiskCacheDir(mContext) + "/";
        }
        return filePath;
    }

    public String getDiskCacheDir(Context context){
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }


    public void setOnDownloadListener(DownloadFileListener listener) {
        this.mListener = listener;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFileName(String fileName) {
        this.mFileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.mFilePath = filePath;
    }

    public void cancelDownload() {
        this.mCancel = true;
    }

    public interface DownloadFileListener {
        void onStart();

        void onSuccess(String filePath);

        void download(int progress);

        void onFailed();
    }

}
