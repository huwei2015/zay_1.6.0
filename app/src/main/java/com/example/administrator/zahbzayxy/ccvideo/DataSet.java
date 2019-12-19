package com.example.administrator.zahbzayxy.ccvideo;

import java.util.List;

import io.objectbox.BoxStore;

public class DataSet {
	private static DownloadDBHelper downloadDBHelper;
	public static void init(BoxStore boxStore){
		downloadDBHelper = new DownloadDBHelper(boxStore);
	}

	public static void saveDownloadData(){
		downloadDBHelper.saveDownloadData();
	}

	public static List<DownloadInfo> getDownloadInfos(){
		return downloadDBHelper.getDownloadInfos();
	}

	public static boolean hasDownloadInfo(String title){
		return downloadDBHelper.hasDownloadInfo(title);
	}

	public static DownloadInfo getDownloadInfo(String title){
		return downloadDBHelper.getDownloadInfo(title);
	}

	public static void addDownloadInfo(DownloadInfo downloadInfo){
		downloadDBHelper.addDownloadInfo(downloadInfo);
	}

	public static void removeDownloadInfo(DownloadInfo downloadInfo){
		downloadDBHelper.removeDownloadInfo(downloadInfo);
	}

	public static void updateDownloadInfo(DownloadInfo downloadInfo){
		downloadDBHelper.updateDownloadInfo(downloadInfo);
	}
}
