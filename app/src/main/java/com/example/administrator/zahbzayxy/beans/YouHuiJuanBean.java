package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2018/4/25 0025.
 */

public class YouHuiJuanBean {

    /**
     * code : 00000
     * data : [{"amount":10,"couponName":"题库代金券","createTime":null,"updaterId":null,"couponType":null,"expiredDate":1527162258000,"creatorId":null,"updateTime":null,"id":35,"userId":1900,"status":2}]
     * errMsg : null
     */
    private String code;
    private List<DataEntity> data;
    private String errMsg;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public class DataEntity {
        /**
         * amount : 10.0
         * couponName : 题库代金券
         * createTime : null
         * updaterId : null
         * couponType : null
         * expiredDate : 1527162258000
         * creatorId : null
         * updateTime : null
         * id : 35
         * userId : 1900
         * status : 2
         */
        private double amount;
        private String couponName;
        private String createTime;
        private String updaterId;
        private String couponType;
        private long expiredDate;
        private String creatorId;
        private String updateTime;
        private int id;
        private int userId;
        private Integer peroid;

        private int status;

        public Integer getPeroid() {
            return peroid;
        }

        public void setPeroid(Integer peroid) {
            this.peroid = peroid;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setUpdaterId(String updaterId) {
            this.updaterId = updaterId;
        }

        public void setCouponType(String couponType) {
            this.couponType = couponType;
        }

        public void setExpiredDate(long expiredDate) {
            this.expiredDate = expiredDate;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getAmount() {
            return amount;
        }

        public String getCouponName() {
            return couponName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdaterId() {
            return updaterId;
        }

        public String getCouponType() {
            return couponType;
        }

        public long getExpiredDate() {
            return expiredDate;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public int getId() {
            return id;
        }

        public int getUserId() {
            return userId;
        }

        public int getStatus() {
            return status;
        }
    }
}
