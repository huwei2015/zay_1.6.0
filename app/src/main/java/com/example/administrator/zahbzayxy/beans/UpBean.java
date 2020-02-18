package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2020-01-17.
 * Time 13:55.
 * 上传正式
 */
public class UpBean {
    private String code;
    private String errMsg;
    private OneCunBean data;

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

    public OneCunBean getData() {
        return data;
    }

    public void setData(OneCunBean data) {
        this.data = data;
    }

    public static class OneCunBean implements Serializable{
        private String photoUrl;

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }
}
