package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2018/4/25 0025.
 */

public class YouHuiJuanListBean {

    /**
     * code : 00000
     * data : {"quesLibPackageName":"模考题库","quesLibPackagePrice":15,"quesLibPackageId":2,"quesLibName":"金属冶炼（有色金属冶炼<除铜、铝、铅、锌之外其他>）","couponList":[{"amount":5,"couponName":"题库代金券","createTime":null,"updaterId":null,"couponType":null,"expiredDate":1527234216000,"creatorId":null,"updateTime":null,"id":46,"userId":1911,"status":0}],"quesCount":null,"quesLibId":159}
     * errMsg : null
     */
    private String code;
    private DataEntity data;
    private String errMsg;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public class DataEntity {
        /**
         * quesLibPackageName : 模考题库
         * quesLibPackagePrice : 15.0
         * quesLibPackageId : 2
         * quesLibName : 金属冶炼（有色金属冶炼<除铜、铝、铅、锌之外其他>）
         * couponList : [{"amount":5,"couponName":"题库代金券","createTime":null,"updaterId":null,"couponType":null,"expiredDate":1527234216000,"creatorId":null,"updateTime":null,"id":46,"userId":1911,"status":0}]
         * quesCount : null
         * quesLibId : 159
         */
        private String quesLibPackageName;
        private double quesLibPackagePrice;
        private int quesLibPackageId;
        private String quesLibName;
        private List<CouponListEntity> couponList;
        private String quesCount;
        private int quesLibId;

        public void setQuesLibPackageName(String quesLibPackageName) {
            this.quesLibPackageName = quesLibPackageName;
        }

        public void setQuesLibPackagePrice(double quesLibPackagePrice) {
            this.quesLibPackagePrice = quesLibPackagePrice;
        }

        public void setQuesLibPackageId(int quesLibPackageId) {
            this.quesLibPackageId = quesLibPackageId;
        }

        public void setQuesLibName(String quesLibName) {
            this.quesLibName = quesLibName;
        }

        public void setCouponList(List<CouponListEntity> couponList) {
            this.couponList = couponList;
        }

        public void setQuesCount(String quesCount) {
            this.quesCount = quesCount;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }

        public String getQuesLibPackageName() {
            return quesLibPackageName;
        }

        public double getQuesLibPackagePrice() {
            return quesLibPackagePrice;
        }

        public int getQuesLibPackageId() {
            return quesLibPackageId;
        }

        public String getQuesLibName() {
            return quesLibName;
        }

        public List<CouponListEntity> getCouponList() {
            return couponList;
        }

        public String getQuesCount() {
            return quesCount;
        }

        public int getQuesLibId() {
            return quesLibId;
        }

        public class CouponListEntity {
            /**
             * amount : 5.0
             * couponName : 题库代金券
             * createTime : null
             * updaterId : null
             * couponType : null
             * expiredDate : 1527234216000
             * creatorId : null
             * updateTime : null
             * id : 46
             * userId : 1911
             * status : 0
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
            private int status;

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
}
