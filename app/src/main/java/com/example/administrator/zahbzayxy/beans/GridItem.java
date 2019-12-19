package com.example.administrator.zahbzayxy.beans;

public class GridItem {
	private String path;
	private String time;
	private int section;
	private String name;

	private int isRight;

	public int getIsRight() {
		return isRight;
	}

	public void setIsRight(int isRight) {
		this.isRight = isRight;
	}

	public GridItem(String path, String time, int section, String name,int isRight) {
		super();
		this.path = path;
		this.time = time;
		this.section = section;
		this.name = name;
		this.isRight=isRight;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
