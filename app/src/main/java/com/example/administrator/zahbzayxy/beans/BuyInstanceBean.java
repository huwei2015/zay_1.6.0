package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/4/21 0021.
 */
public class BuyInstanceBean {


    /**
     * code : 47887
     * data : {"orderNumber":"测试内容4ric","pirce":72348}
     * erromsg : 测试内容l614
     */

    private String code;
    /**
     * orderNumber : 测试内容4ric
     * pirce : 72348
     */

    private DataBean data;
    private String erromsg;

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

    public String getErromsg() {
        return erromsg;
    }

    public void setErromsg(String erromsg) {
        this.erromsg = erromsg;
    }

    public static class DataBean {
        private String orderNumber;
        private String price;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getPrice() {
            return price;
        }

        public void setPirce(String price) {
            this.price=price;
        }
    }
}
