package com.example.administrator.zahbzayxy.manager.showFile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.zahbzayxy.activities.ShowPdfActivity;
import com.example.administrator.zahbzayxy.myinterface.ShowFileInter;

public class ShowFilePdfImpl implements ShowFileInter {

    private Activity mActivity;

    public ShowFilePdfImpl(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void openFile(String fileName, String url) {
        Intent intent = new Intent(mActivity, ShowPdfActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ShowPdfActivity.SHOW_PDF_FILE_NAME_KEY, fileName);
        bundle.putString(ShowPdfActivity.SHOW_PDF_FILE_URL_KEY, url);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }

    @Override
    public void closeOpen() {

    }
}
