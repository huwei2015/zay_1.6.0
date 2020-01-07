package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2020-01-02.
 * Time 15:26.
 * 学习未通过
 */
public class NotThroughBean {
    private String code;
    private String errMsg;
    private ThrougListBean data;

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

    public ThrougListBean getData() {
        return data;
    }

    public void setData(ThrougListBean data) {
        this.data = data;
    }

    public static class ThrougListBean implements Serializable{
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
