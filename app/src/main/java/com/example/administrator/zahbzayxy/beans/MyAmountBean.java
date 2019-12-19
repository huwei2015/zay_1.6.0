package com.example.administrator.zahbzayxy.beans;
/**
 * Created by ${ZWJ} on 2017/5/8 0008.
 */
public class MyAmountBean {
    /**
     * code : 测试内容89cj
     * data : {"amount":1}
     * errMsg : 测试内容1by3
     */

    private String code;
    /**
     * amount : 1
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
        private String amount;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
