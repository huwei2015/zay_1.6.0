package com.example.administrator.zahbzayxy.beans;

/**
 * Created by Administrator on 2017/1/14 0014.
 */
public class HotNewsBean {
    private String title;
    private String date;

    public HotNewsBean(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HotNewsBean{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
