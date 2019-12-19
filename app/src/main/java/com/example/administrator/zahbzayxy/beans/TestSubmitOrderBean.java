package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/3/29 0029.
 */
public class TestSubmitOrderBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"orderNumber":"1490769570100"}
     */

    private String code;
    private Object errMsg;
    /**
     * orderNumber : 1490769570100
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String orderNumber;

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        private float price;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }
    }
}
