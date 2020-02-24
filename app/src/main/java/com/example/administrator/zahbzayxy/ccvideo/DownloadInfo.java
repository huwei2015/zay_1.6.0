package com.example.administrator.zahbzayxy.ccvideo;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class DownloadInfo {

	@Id
	private long id;
	
	private String videoId;
	
	private String title;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	private long start;

	private long end;
	
	private int status;

	private Date createTime;
	
	private int definition;
	private int userCourseId;

	private int sectionId;

	private int coruseId;
	private String imagePath;

	public int getUserCourseId() {
		return userCourseId;
	}

	public void setUserCourseId(int userCourseId) {
		this.userCourseId = userCourseId;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public int getCoruseId() {
		return coruseId;
	}

	public void setCoruseId(int coruseId) {
		this.coruseId = coruseId;
	}

	public DownloadInfo() {
	}

	public DownloadInfo(String videoId, String title,String name, int status, long start, long end, Date createTime,int userCourseId,int sectionId,int coruseId, String imagePath) {
		this.videoId = videoId;
		this.title = title;
		this.name=name;
		this.status = status;
		this.createTime = createTime;
		this.definition = -1;
		this.start = start;
		this.end = end;
		this.userCourseId = userCourseId;
		this.sectionId =sectionId;
		this.coruseId = coruseId;
		this.imagePath = imagePath;
	}
	
	public DownloadInfo(String videoId, String title, String name,int status, long start, long end, Date createTime, int definition,int userCourseId,int sectionId,int coruseId, String imagePath) {
		this(videoId, title,name, status, start, end, createTime,userCourseId,sectionId,coruseId, imagePath);
		this.definition = definition;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public int getDefinition() {
		return definition;
	}

	public void setDefinition(int definition) {
		this.definition = definition;
	}
	
	public long getStart() {
		return start;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public DownloadInfo setStart(long start) {
		this.start = start;
		return this;
	}

	public long getEnd() {
		return end;
	}

	public DownloadInfo setEnd(long end) {
		this.end = end;
		return this;
	}

}
