package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2019-12-23.
 * Time 15:17.
 * 个人中心userCenter
 */
public class UserCenter {
    private String code;
    private String errMsg;
    private userCenterData data;

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

    public userCenterData getData() {
        return data;
    }

    public void setData(userCenterData data) {
        this.data = data;
    }

    public static class userCenterData implements Serializable{
        private String userName;
        private String phone;
        private String photoUrl;
        private int userId;
        private int  type;
        private Double amount;
        private int couponNum;
        private int orderNum;
        private int shopCarNum;
        private String userLevelStr;
        private int messageNum;
        private boolean agreementAdmin;
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public int getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(int couponNum) {
            this.couponNum = couponNum;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public int getShopCarNum() {
            return shopCarNum;
        }

        public void setShopCarNum(int shopCarNum) {
            this.shopCarNum = shopCarNum;
        }

        public String getUserLevelStr() {
            return userLevelStr;
        }

        public void setUserLevelStr(String userLevelStr) {
            this.userLevelStr = userLevelStr;
        }

        public int getMessageNum() {
            return messageNum;
        }

        public void setMessageNum(int messageNum) {
            this.messageNum = messageNum;
        }

        public boolean isAgreementAdmin() {
            return agreementAdmin;
        }

        public void setAgreementAdmin(boolean agreementAdmin) {
            this.agreementAdmin = agreementAdmin;
        }
    }
}
