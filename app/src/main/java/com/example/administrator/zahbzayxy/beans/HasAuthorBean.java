package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 15:47.
 * 已授权
 */
public class HasAuthorBean {
    private String code;
    private String errMsg;
    private HasAuthorList data;

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

    public HasAuthorList getData() {
        return data;
    }

    public void setData(HasAuthorList data) {
        this.data = data;
    }

    public static class HasAuthorList implements Serializable {
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
