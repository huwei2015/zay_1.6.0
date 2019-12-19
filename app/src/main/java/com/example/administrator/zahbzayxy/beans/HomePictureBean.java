package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/20 0020.
 */
public class HomePictureBean {

    /**
     * code : 00000
     * errMsg : null
     * data : [{"imageUrl":"http://app.zayxy.com/static/image/news00.png","jumpUrl":"http://app.zayxy.com/static/html/news0.html"},{"imageUrl":"http://app.zayxy.com/static/image/news01.png","jumpUrl":"http://app.zayxy.com/static/html/news1.html"},{"imageUrl":"http://app.zayxy.com/static/image/news02.png","jumpUrl":"http://app.zayxy.com/static/html/news2.html"}]
     */

    private String code;
    private Object errMsg;
    /**
     * imageUrl : http://app.zayxy.com/static/image/news00.png
     * jumpUrl : http://app.zayxy.com/static/html/news0.html
     */

    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String imageUrl;
        private String jumpUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }
    }
}
