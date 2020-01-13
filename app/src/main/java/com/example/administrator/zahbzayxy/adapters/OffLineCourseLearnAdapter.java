package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bokecc.sdk.mobile.download.Downloader;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.ccvideo.DownloaderWrapper;

import java.util.ArrayList;
import java.util.List;

public class OffLineCourseLearnAdapter extends RecyclerView.Adapter<OffLineCourseLearnAdapter.OffLineCourseViewHolder> {

    private List<DownloaderWrapper> downloadInfos;
    private LayoutInflater mInflater;

    private Context context;

    public OffLineCourseLearnAdapter(Context context, List<DownloaderWrapper> downloadInfos) {
        if (downloadInfos == null) downloadInfos = new ArrayList<>();
        this.context = context;
        this.downloadInfos = downloadInfos;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(List<DownloaderWrapper> data) {
        if (data == null) data = new ArrayList<>();
        this.downloadInfos = data;
        notifyDataSetChanged();
    }

    @Override
    public OffLineCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.download_single_layout, parent, false);
        return new OffLineCourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OffLineCourseViewHolder holder, int position) {
        DownloaderWrapper wrapper = downloadInfos.get(position);

        holder.titleView.setText(wrapper.getDownloadInfo().getName());
        holder.statusView.setText(getStatusStr(wrapper.getStatus()) + "");

        if (wrapper.getStatus() == Downloader.DOWNLOAD) {
            holder.speedView.setText(wrapper.getSpeed(context));
            holder.progressView.setText(wrapper.getDownloadProgressText(context));
            holder.downloadProgressBar.setProgress((int) wrapper.getDownloadProgressBarValue());
        } else {
            holder.speedView.setText("");
            holder.progressView.setText(wrapper.getDownloadProgressText(context));
            holder.downloadProgressBar.setProgress((int) wrapper.getDownloadProgressBarValue());
        }
    }

    @Override
    public int getItemCount() {
        return downloadInfos.size();
    }

    private String getStatusStr(int status) {
        String statusStr = null;
        switch (status) {
            case Downloader.WAIT:
                statusStr = "等待中";
                break;
            case Downloader.DOWNLOAD:
                statusStr = "下载中";
                break;
            case Downloader.PAUSE:
                statusStr = "已暂停";
                break;
            case Downloader.FINISH:
                statusStr = "已完成";
                break;
        }

        return statusStr;
    }

    class OffLineCourseViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView statusView;
        TextView speedView;
        TextView progressView;
        ProgressBar downloadProgressBar;

        public OffLineCourseViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.download_title);
            statusView = (TextView) itemView.findViewById(R.id.download_status);
            speedView = (TextView) itemView.findViewById(R.id.download_speed);
            progressView = (TextView) itemView.findViewById(R.id.download_progress);
            downloadProgressBar = (ProgressBar) itemView.findViewById(R.id.download_progressBar);
            downloadProgressBar.setMax(100);
        }
    }

}

