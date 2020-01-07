package com.example.administrator.zahbzayxy.beans;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 14:45.
 */
public class AlreadyBean {
    private String code;
    private String errMsg;
    private AlreadyListBean data;

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

    public AlreadyListBean getData() {
        return data;
    }

    public void setData(AlreadyListBean data) {
        this.data = data;
    }

    public static class AlreadyListBean implements Serializable {
        private ImageView imageView;
        private String title;
        private String time;
        private String account;
        private String state;
        private String record;

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
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

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRecord() {
            return record;
        }

        public void setRecord(String record) {
            this.record = record;
        }
    }
}
