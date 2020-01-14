package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2020-01-13.
 * Time 14:09.
 * 考试里面模拟考试导航bean
 */
public class SimulationBean {
    private String code;
    private String errMsg;
    private SimulationList data;

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

    public SimulationList getData() {
        return data;
    }

    public void setData(SimulationList data) {
        this.data = data;
    }

    public static class SimulationList implements Serializable{
        private String cateName;
         private int id;
         private String parentId;
         private String platformId;
         private String catePath;
         private String putawayStatus;
         private String isDelete;
         private String createTime;
         private String creatorId;
         private String updaterId;

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }

        public String getCatePath() {
            return catePath;
        }

        public void setCatePath(String catePath) {
            this.catePath = catePath;
        }

        public String getPutawayStatus() {
            return putawayStatus;
        }

        public void setPutawayStatus(String putawayStatus) {
            this.putawayStatus = putawayStatus;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getUpdaterId() {
            return updaterId;
        }

        public void setUpdaterId(String updaterId) {
            this.updaterId = updaterId;
        }
    }
}
