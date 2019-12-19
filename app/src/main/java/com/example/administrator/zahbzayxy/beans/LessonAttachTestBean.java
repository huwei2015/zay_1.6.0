package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/24 0024.
 */
public class LessonAttachTestBean {


    /**
     * code : 00000
     * errMsg : null
     * data : [{"quesLibName":"测试题库12","updateTime":"2017-02-17 11:12:31","id":14,"oPrice":100,"sPrice":0.01},{"quesLibName":"测试题库22","updateTime":"2017-02-17 11:13:03","id":15,"oPrice":100,"sPrice":0.01},{"quesLibName":"测试题库33","updateTime":"2017-02-17 11:13:25","id":16,"oPrice":70,"sPrice":0}]
     */

    private String code;
    private Object errMsg;
    /**
     * quesLibName : 测试题库12
     * updateTime : 2017-02-17 11:12:31
     * id : 14
     * oPrice : 100
     * sPrice : 0.01
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
        private String quesLibName;
        private String updateTime;
        private int id;
        private int oPrice;
        private double sPrice;

        public String getQuesLibName() {
            return quesLibName;
        }

        public void setQuesLibName(String quesLibName) {
            this.quesLibName = quesLibName;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOPrice() {
            return oPrice;
        }

        public void setOPrice(int oPrice) {
            this.oPrice = oPrice;
        }

        public double getSPrice() {
            return sPrice;
        }

        public void setSPrice(double sPrice) {
            this.sPrice = sPrice;
        }
    }
}
