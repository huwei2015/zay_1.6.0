package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-24.
 * Time 11:34.
 * 全部附件bean
 */
public class AllFileBean {
    private String code;
    private String errMsg;
    private AllDataBean data;

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

    public AllDataBean getData() {
        return data;
    }

    public void setData(AllDataBean data) {
        this.data = data;
    }

    public static class AllDataBean implements Serializable{
        private String msg;
        private String code;
        private List<AllFileListBean> data;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<AllFileListBean> getData() {
            return data;
        }

        public void setData(List<AllFileListBean> data) {
            this.data = data;
        }
    }


   public  static class AllFileListBean implements Serializable{
        private String attaSize;
        private String createTime;
        private String attaFormat;
        private String attaName;
        private String updateTime;
        private String id;
        private String attaPath;

       public String getAttaPath() {
           return attaPath;
       }

       public void setAttaPath(String attaPath) {
           this.attaPath = attaPath;
       }

       public String getAttaSize() {
            return attaSize;
        }

        public void setAttaSize(String attaSize) {
            this.attaSize = attaSize;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAttaFormat() {
            return attaFormat;
        }

        public void setAttaFormat(String attaFormat) {
            this.attaFormat = attaFormat;
        }

        public String getAttaName() {
            return attaName;
        }

        public void setAttaName(String attaName) {
            this.attaName = attaName;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
   }
}
