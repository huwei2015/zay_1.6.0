package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/3/17 0017.
 */
public class TestIsBuyBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"isBuy":true,"quesLibId":14}
     */

    private String code;
    private Object errMsg;
    /**
     * isBuy : true
     * quesLibId : 14
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
        private boolean isBuy;
        private int quesLibId;

        public boolean isIsBuy() {
            return isBuy;
        }

        public void setIsBuy(boolean isBuy) {
            this.isBuy = isBuy;
        }

        public int getQuesLibId() {
            return quesLibId;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }
    }
}
