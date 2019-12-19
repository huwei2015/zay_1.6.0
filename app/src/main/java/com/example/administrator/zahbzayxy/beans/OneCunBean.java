package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2019/8/8.
 * Time 15:27.
 * Description.
 */
public class OneCunBean {
    private String code;
    private String errMsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OneCunBean{" +
                "code='" + code + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable{
        private String photoUrl;

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "photoUrl='" + photoUrl + '\'' +
                    '}';
        }
    }

}
