package com.example.administrator.zahbzayxy.vo;

/**
 * 用户信息接口返回数据类
 */

public class UserInfo {

    /**
     * code : 00000
     * errMsg :
     * data : {"needVerify":0,"intervalTime":"","facePath":""}
     */

    private String code;
    private String errMsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "code='" + code + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * needVerify : 0
         * intervalTime :
         * facePath :
         */

        private int needVerify;
        private String intervalTime;
        private String facePath;

        public int getNeedVerify() {
            return needVerify;
        }

        public void setNeedVerify(int needVerify) {
            this.needVerify = needVerify;
        }

        public String getIntervalTime() {
            return intervalTime;
        }

        public void setIntervalTime(String intervalTime) {
            this.intervalTime = intervalTime;
        }

        public String getFacePath() {
            return facePath;
        }

        public void setFacePath(String facePath) {
            this.facePath = facePath;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "needVerify=" + needVerify +
                    ", intervalTime='" + intervalTime + '\'' +
                    ", facePath='" + facePath + '\'' +
                    '}';
        }
    }
}
