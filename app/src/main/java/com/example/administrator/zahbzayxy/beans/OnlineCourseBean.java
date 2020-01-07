package com.example.administrator.zahbzayxy.beans;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 10:16.
 * 在线课bean
 */
public class OnlineCourseBean {
    private String code;
    private String errMsg;
    private OnLineListBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public OnLineListBean getData() {
        return data;
    }

    public void setData(OnLineListBean data) {
        this.data = data;
    }

    public static class OnLineListBean implements Serializable{
        private ImageView img_icon;
        private String title;
        private String time;
        private String state;

        public ImageView getImg_icon() {
            return img_icon;
        }

        public void setImg_icon(ImageView img_icon) {
            this.img_icon = img_icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
