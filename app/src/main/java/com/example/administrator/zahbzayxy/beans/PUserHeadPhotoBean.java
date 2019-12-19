package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/4/16 0016.
 */
public class PUserHeadPhotoBean {

    /**
     * code : 测试内容k502
     * data : {"photoUrl":"测试内容315h"}
     * errMsg : 测试内容fvqd
     */

    private String code;
    /**
     * photoUrl : 测试内容315h
     */

    private DataBean data;
    private String errMsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static class DataBean {
        private String photoUrl;

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }
}
