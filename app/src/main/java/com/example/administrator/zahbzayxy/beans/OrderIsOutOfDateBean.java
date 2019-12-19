package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2018/4/26 0026.
 */

public class OrderIsOutOfDateBean {

    /**
     * data : {"overdue":75747}
     * resCode : 测试内容88o6
     * resMsg : 测试内容263r
     */
    private DataEntity data;
    private String resCode;
    private String resMsg;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public DataEntity getData() {
        return data;
    }

    public String getResCode() {
        return resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public class DataEntity {
        /**
         * overdue : 75747
         */
        private int overdue;

        public void setOverdue(int overdue) {
            this.overdue = overdue;
        }

        public int getOverdue() {
            return overdue;
        }
    }
}
