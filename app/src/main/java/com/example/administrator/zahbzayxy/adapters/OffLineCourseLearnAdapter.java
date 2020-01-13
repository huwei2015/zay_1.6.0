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
    private OnDownLoadedItemClickListener mDownloadedListener;
    private OnDownLoadingItemClickListener mDownloadingListener;

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
    public int getItemViewType(int position) {
        int type = 0;
        if (downloadInfos != null && downloadInfos.size() > 0 && position < downloadInfos.size()) {
            DownloaderWrapper wrapper = downloadInfos.get(position);
            // 1、下载中 2、已完成
            if (wrapper.getType() == 1) {
                type = 1;
            } else {
                type = 0;
            }
        }
        return type;
    }

    @Override
    public OffLineCourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = mInflater.inflate(R.layout.downloaded_single_layout, parent, false);
        } else {
            view = mInflater.inflate(R.layout.download_single_layout, parent, false);
        }
        return new OffLineCourseViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(OffLineCourseViewHolder holder, int position) {
        DownloaderWrapper wrapper = downloadInfos.get(position);
        int type = wrapper.getType();

        if (position == 0) {
            holder.typeLayout.setVisibility(View.VISIBLE);
        } else if (position < downloadInfos.size()) {
            int preType = downloadInfos.get(position - 1).getType();
            if (preType == type) {
                holder.typeLayout.setVisibility(View.GONE);
            } else {
                holder.typeLayout.setVisibility(View.VISIBLE);
            }
        } else {
            holder.typeLayout.setVisibility(View.GONE);
        }


        if (type == 1) {
            holder.titleView.setText(wrapper.getDownloadInfo().getName());
            holder.statusView.setText(getStatusStr(wrapper.getStatus()) + "");
            holder.typeTv.setText("下载中");
            if (wrapper.getStatus() == Downloader.DOWNLOAD) {
                holder.speedView.setText(wrapper.getSpeed(context));
                holder.progressView.setText(wrapper.getDownloadProgressText(context));
                holder.downloadProgressBar.setProgress((int) wrapper.getDownloadProgressBarValue());
            } else {
                holder.speedView.setText("");
                holder.progressView.setText(wrapper.getDownloadProgressText(context));
                holder.downloadProgressBar.setProgress((int) wrapper.getDownloadProgressBarValue());
            }
            holder.mDownloadingRootLayout.setOnClickListener(v -> {
                if (mDownloadingListener != null) {
                    mDownloadingListener.onClick(position);
                }
            });
        } else {
            holder.titleView.setText(wrapper.getDownloadInfo().getName());
            holder.typeTv.setText("已完成");
            holder.mDownedRootLayout.setOnClickListener(v -> {
                if (mDownloadedListener != null) {
                    mDownloadedListener.onClick(position);
                }
            });
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
        LinearLayout typeLayout;
        TextView typeTv;
        View downedLineView, downingLineView;
        private LinearLayout mDownedRootLayout, mDownloadingRootLayout;

        public OffLineCourseViewHolder(View itemView, int type) {
            super(itemView);

            // 0、已完成  1、下载中
            if (type == 0) {
                titleView = itemView.findViewById(R.id.downloaded_title);
                typeLayout = itemView.findViewById(R.id.off_line_course_type_layout);
                typeTv = itemView.findViewById(R.id.off_line_course_type_tv);
                mDownedRootLayout = itemView.findViewById(R.id.off_line_downloaded_layout);
                downedLineView = itemView.findViewById(R.id.downloaded_line_view);
                downedLineView.setVisibility(View.VISIBLE);
            } else {
                titleView = (TextView) itemView.findViewById(R.id.download_title);
                statusView = (TextView) itemView.findViewById(R.id.download_status);
                speedView = (TextView) itemView.findViewById(R.id.download_speed);
                progressView = (TextView) itemView.findViewById(R.id.download_progress);
                downloadProgressBar = (ProgressBar) itemView.findViewById(R.id.download_progressBar);
                mDownloadingRootLayout = itemView.findViewById(R.id.off_line_downloading_layout);
                typeLayout = itemView.findViewById(R.id.off_line_course_type_layout);
                typeTv = itemView.findViewById(R.id.off_line_course_type_tv);
                downingLineView = itemView.findViewById(R.id.downloading_line_view);
                downingLineView.setVisibility(View.VISIBLE);
                downloadProgressBar.setMax(100);
            }
        }
    }


    public void setOnDownLoadedItemClickListener(OnDownLoadedItemClickListener listener) {
        this.mDownloadedListener = listener;
    }

    public void setOnDownLoadingItemClickListener(OnDownLoadingItemClickListener listener) {
        this.mDownloadingListener = listener;
    }

    public interface OnDownLoadedItemClickListener{
        void onClick(int position);
    }

    public interface OnDownLoadingItemClickListener{
        void onClick(int position);
    }

}

