package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 16:40.
 */
public class StayAuthorBean {
    private String code;
    private String errMsg;
    private StayAuthorList data;

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

    public StayAuthorList getData() {
        return data;
    }

    public void setData(StayAuthorList data) {
        this.data = data;
    }

    public static class StayAuthorList implements Serializable{
        private String order_num;
        private String title;

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
