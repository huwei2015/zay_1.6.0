package com.example.administrator.zahbzayxy.beans;

/**
 * Created by huwei.
 * Data 2019-12-13.
 * Time 11:18.
 */
public class TimeData {
    private String posttime;
    private String title;
    public TimeData(String posttime,String title) {
        this.title = title;
        this.posttime = posttime;
    }
    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
