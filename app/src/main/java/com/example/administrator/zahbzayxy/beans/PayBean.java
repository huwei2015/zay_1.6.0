package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class PayBean {
    int tag;
    int id;
    String title;
    List<ZiBean> list;

    public PayBean(int tag, int id, String title, List<ZiBean> list) {
        this.tag = tag;
        this.id = id;
        this.title = title;
        this.list = list;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ZiBean> getList() {
        return list;
    }

    public void setList(List<ZiBean> list) {
        this.list = list;
    }

    public static class ZiBean{
        int tag;
        int id;
        String ziTitle;

        public ZiBean(int tag, int id, String ziTitle) {
            this.tag = tag;
            this.id = id;
            this.ziTitle = ziTitle;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getZiTitle() {
            return ziTitle;
        }

        public void setZiTitle(String ziTitle) {
            this.ziTitle = ziTitle;
        }
    }
}
