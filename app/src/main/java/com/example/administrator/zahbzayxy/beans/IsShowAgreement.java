package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2020-03-30.
 * Time 11:29.
 * 是否展示人脸采集协议
 */
public class IsShowAgreement {
    private String code;
    private String errMsg;
    private Agreement data;

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

    public Agreement getData() {
        return data;
    }

    public void setData(Agreement data) {
        this.data = data;
    }

    public static class Agreement implements Serializable{
        private boolean show;

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }
    }
}
