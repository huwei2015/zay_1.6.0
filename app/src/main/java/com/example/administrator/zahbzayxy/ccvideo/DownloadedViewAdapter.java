package com.example.administrator.zahbzayxy.ccvideo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.zahbzayxy.R;

import java.util.List;


public class DownloadedViewAdapter extends BaseAdapter {

	private List<DownloaderWrapper> downloadInfos;

	private Context context;

	public DownloadedViewAdapter(Context context, List<DownloaderWrapper> downloadInfos){
		this.context = context;
		this.downloadInfos = downloadInfos;
	}

	@Override
	public int getCount() {
		return downloadInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return downloadInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DownloaderWrapper wrapper = downloadInfos.get(position);
		ViewHolder holder = null;

		if (convertView == null) {
			LinearLayout layout = (LinearLayout) View.inflate(context, R.layout.downloaded_single_layout, null);
			convertView = layout;
			TextView titleView = (TextView) layout.findViewById(R.id.downloaded_title);

			holder = new ViewHolder();
			holder.titleView = titleView;

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.titleView.setText(wrapper.getDownloadInfo().getName());

		return convertView;
	}

	public class ViewHolder {
		public TextView titleView;
	}
}
