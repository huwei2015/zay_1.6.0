package com.example.administrator.zahbzayxy.manager;

import android.app.Activity;
import android.util.Log;

import com.example.administrator.zahbzayxy.manager.showFile.ShowFileExcelImpl;
import com.example.administrator.zahbzayxy.manager.showFile.ShowFileImgImpl;
import com.example.administrator.zahbzayxy.manager.showFile.ShowFilePdfImpl;
import com.example.administrator.zahbzayxy.manager.showFile.ShowFileWordImpl;
import com.example.administrator.zahbzayxy.myinterface.ShowFileInter;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;

public class ShowFileManager {

    public static final int SHOW_FILE_IMG = 1;
    public static final int SHOW_FILE_WORD = 2;
    public static final int SHOW_FILE_EXCEL = 3;
    public static final int SHOW_FILE_PDF = 4;

    private int mFileType;
    private ShowFileInter mShowFile;
    private Activity mActivity;
    private ProgressBarLayout mLoading;

    public ShowFileManager(Activity activity){
        this(activity, SHOW_FILE_IMG);
    }

    public ShowFileManager(Activity activity, int fileType){
        this.mFileType = fileType;
        this.mActivity = activity;
        setShowFile();
    }

    public void openFile(String fileName, String url){
        if (mFileType < 1) mFileType = SHOW_FILE_IMG;
        openFile(fileName, url, mFileType);
    }

    public void setLoadingView(ProgressBarLayout loadingView) {
        this.mLoading = loadingView;
    }

    public void openFile(String fileName, String url, int fileType){
        this.mFileType = fileType;
        switch (mFileType) {
            case SHOW_FILE_IMG:
                mShowFile = null;
                mShowFile = new ShowFileImgImpl(mActivity);
                mShowFile.openFile(fileName, url);
                break;
            case SHOW_FILE_WORD:
                mShowFile = null;
                mShowFile = new ShowFileWordImpl(mActivity);
                ((ShowFileWordImpl)mShowFile).setLoadingView(mLoading);
                mShowFile.openFile(fileName, url);
                break;
            case SHOW_FILE_EXCEL:
                mShowFile = null;
                mShowFile = new ShowFileExcelImpl(mActivity);
                ((ShowFileExcelImpl)mShowFile).setLoadingView(mLoading);
                mShowFile.openFile(fileName, url);
                break;
            case SHOW_FILE_PDF:
                mShowFile = null;
                mShowFile = new ShowFilePdfImpl(mActivity);
                mShowFile.openFile(fileName, url);
                break;
        }
    }

    public void closeOpen() {
        if (mShowFile != null) mShowFile.closeOpen();
    }

    public void setFileType(int fileType) {
        this.mFileType = fileType;
    }

    private void setShowFile() {
        switch (mFileType) {
            case SHOW_FILE_IMG:
                break;
            case SHOW_FILE_WORD:
                break;
            case SHOW_FILE_EXCEL:
                break;
            case SHOW_FILE_PDF:
                mShowFile = null;
                mShowFile = new ShowFilePdfImpl(mActivity);
                break;
        }
    }

}
