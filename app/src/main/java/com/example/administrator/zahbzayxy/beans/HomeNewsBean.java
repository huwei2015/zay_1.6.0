package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/20 0020.
 */
public class HomeNewsBean {

    /**
     * code : 00000
     * errMsg : null
     * data : [{"updateDate":"2017-04-20","title":"中安学院安全在线教育","jumpUrl":"http://app.zayxy.com/static/html/news1.html"},{"updateDate":"2017-04-20","title":"金属非金属矿山系列课程赴长沙录制完成","jumpUrl":"http://app.zayxy.com/static/html/news0.html"},{"updateDate":"2017-04-20","title":"整合全国优秀师资，打造精品课程","jumpUrl":"http://app.zayxy.com/static/html/news2.html"}]
     */

    private String code;
    private Object errMsg;
    /**
     * updateDate : 2017-04-20
     * title : 中安学院安全在线教育
     * jumpUrl : http://app.zayxy.com/static/html/news1.html
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
        private String updateDate;
        private String title;
        private String jumpUrl;

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }
    }
}
