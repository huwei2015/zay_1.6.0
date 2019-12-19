package com.example.administrator.zahbzayxy.beans;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 10:50.
 * 我的报名bean
 */
public class SignBean {
    private String code;
    private String errMsg;
    private SignListBean data;

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

    public SignListBean getData() {
        return data;
    }

    public void setData(SignListBean data) {
        this.data = data;
    }

    public static class SignListBean{
        private String title;
        private String time;

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
    }
}
