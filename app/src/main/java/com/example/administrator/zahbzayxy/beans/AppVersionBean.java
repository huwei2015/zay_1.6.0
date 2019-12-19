package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/22 0022.
 */
public class AppVersionBean {


    /**
     * errMsg : null
     * data : [{"isForce":0,"id":2,"verInfo":"哈哈哈哈哈哈哈啊哈","verNum":"1.0.0","downloadAdd":"www.baidu.com","packageSize":"1.11"}]
     * code : 00000
     */
    private String errMsg;
    private List<DataEntity> data;
    private String code;

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public class DataEntity {
        /**
         * isForce : 0
         * id : 2
         * verInfo : 哈哈哈哈哈哈哈啊哈
         * verNum : 1.0.0
         * downloadAdd : www.baidu.com
         * packageSize : 1.11
         */
        private int isForce;
        private int id;
        private String verInfo;
        private String verNum;
        private String downloadAdd;
        private String packageSize;

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setVerInfo(String verInfo) {
            this.verInfo = verInfo;
        }

        public void setVerNum(String verNum) {
            this.verNum = verNum;
        }

        public void setDownloadAdd(String downloadAdd) {
            this.downloadAdd = downloadAdd;
        }

        public void setPackageSize(String packageSize) {
            this.packageSize = packageSize;
        }

        public int getIsForce() {
            return isForce;
        }

        public int getId() {
            return id;
        }

        public String getVerInfo() {
            return verInfo;
        }

        public String getVerNum() {
            return verNum;
        }

        public String getDownloadAdd() {
            return downloadAdd;
        }

        public String getPackageSize() {
            return packageSize;
        }
    }
}
