package com.example.administrator.zahbzayxy.beans;

/**
 * Created by huwei.
 * Data 2019-10-29.
 * Time 10:05.
 * 待授权bean
 */
public class StayAuthorizedBean {
    private String code;
    private String errMsg;
    private StayAuthorizedList data;

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

    public StayAuthorizedList getData() {
        return data;
    }

    public void setData(StayAuthorizedList data) {
        this.data = data;
    }

    public static class StayAuthorizedList{
        private String order_number;
        private String order_content;

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public String getOrder_content() {
            return order_content;
        }

        public void setOrder_content(String order_content) {
            this.order_content = order_content;
        }
    }
}
