package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/7/12 0012.
 */
public class NBOrderDetailBean {


    /**
     * errMsg : null
     * data : {"createTime":"2018-01-02 15:37:21","detail":[{"child":["【课程一】危险化学品储存单位主要负责人培训课程（初训）","【课程二】危险化学品储存单位主要负责人培训课程（初训）","【课程三】危险化学品储存单位主要负责人培训课程（初训）","【课程四】危险化学品储存单位主要负责人培训课程（初训）","【课程五】危险化学品储存单位主要负责人培训课程（初训）","【课程六】危险化学品储存单位主要负责人培训课程（初训）","【课程七】危险化学品储存单位主要负责人培训课程（初训）","【课程八】危险化学品储存单位主要负责人培训课程（初训）"],"logo":"group2/M00/00/0B/eDdWmlnPDsqASNCRAAFDi7bWDHo905.jpg","name":"危险化学品储存单位主要负责人培训课程（初训）"}],"shopbill":null,"orderType":5,"updateTime":null,"companyPhone":"400-036-0619","companyAccount":"9115 0154 8000 1119 9","creatorId":509,"billStatus":0,"bankName":"上海浦东发展银行","companyName":"中安华邦（北京）安全生产技术研究院股份有限公司","orderStatus":0,"id":1952,"isDelete":0,"payMoney":360,"payStatus":0,"orderNumber":"1514878641838","userId":509,"payType":1,"userPhone":null,"updaterId":0}
     * code : 00000
     */
    private String errMsg;
    private DataEntity data;
    private String code;

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public DataEntity getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public class DataEntity {
        /**
         * createTime : 2018-01-02 15:37:21
         * detail : [{"child":["【课程一】危险化学品储存单位主要负责人培训课程（初训）","【课程二】危险化学品储存单位主要负责人培训课程（初训）","【课程三】危险化学品储存单位主要负责人培训课程（初训）","【课程四】危险化学品储存单位主要负责人培训课程（初训）","【课程五】危险化学品储存单位主要负责人培训课程（初训）","【课程六】危险化学品储存单位主要负责人培训课程（初训）","【课程七】危险化学品储存单位主要负责人培训课程（初训）","【课程八】危险化学品储存单位主要负责人培训课程（初训）"],"logo":"group2/M00/00/0B/eDdWmlnPDsqASNCRAAFDi7bWDHo905.jpg","name":"危险化学品储存单位主要负责人培训课程（初训）"}]
         * shopbill : null
         * orderType : 5
         * updateTime : null
         * companyPhone : 400-036-0619
         * companyAccount : 9115 0154 8000 1119 9
         * creatorId : 509
         * billStatus : 0
         * bankName : 上海浦东发展银行
         * companyName : 中安华邦（北京）安全生产技术研究院股份有限公司
         * orderStatus : 0
         * id : 1952
         * isDelete : 0
         * payMoney : 360.0
         * payStatus : 0
         * orderNumber : 1514878641838
         * userId : 509
         * payType : 1
         * userPhone : null
         * updaterId : 0
         */
        private String createTime;
        private List<DetailEntity> detail;
        private String shopbill;
        private int orderType;
        private String updateTime;
        private String companyPhone;
        private String companyAccount;
        private int creatorId;
        private int billStatus;
        private String bankName;
        private String companyName;
        private int orderStatus;
        private int id;
        private int isDelete;
        private double payMoney;
        private int payStatus;
        private String orderNumber;
        private int userId;
        private int payType;
        private String userPhone;
        private int updaterId;

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setDetail(List<DetailEntity> detail) {
            this.detail = detail;
        }

        public void setShopbill(String shopbill) {
            this.shopbill = shopbill;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setCompanyPhone(String companyPhone) {
            this.companyPhone = companyPhone;
        }

        public void setCompanyAccount(String companyAccount) {
            this.companyAccount = companyAccount;
        }

        public void setCreatorId(int creatorId) {
            this.creatorId = creatorId;
        }

        public void setBillStatus(int billStatus) {
            this.billStatus = billStatus;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public void setPayMoney(double payMoney) {
            this.payMoney = payMoney;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public void setUpdaterId(int updaterId) {
            this.updaterId = updaterId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public List<DetailEntity> getDetail() {
            return detail;
        }

        public String getShopbill() {
            return shopbill;
        }

        public int getOrderType() {
            return orderType;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getCompanyPhone() {
            return companyPhone;
        }

        public String getCompanyAccount() {
            return companyAccount;
        }

        public int getCreatorId() {
            return creatorId;
        }

        public int getBillStatus() {
            return billStatus;
        }

        public String getBankName() {
            return bankName;
        }

        public String getCompanyName() {
            return companyName;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public int getId() {
            return id;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public double getPayMoney() {
            return payMoney;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public int getUserId() {
            return userId;
        }

        public int getPayType() {
            return payType;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public int getUpdaterId() {
            return updaterId;
        }

        public class DetailEntity {
            /**
             * child : ["【课程一】危险化学品储存单位主要负责人培训课程（初训）","【课程二】危险化学品储存单位主要负责人培训课程（初训）","【课程三】危险化学品储存单位主要负责人培训课程（初训）","【课程四】危险化学品储存单位主要负责人培训课程（初训）","【课程五】危险化学品储存单位主要负责人培训课程（初训）","【课程六】危险化学品储存单位主要负责人培训课程（初训）","【课程七】危险化学品储存单位主要负责人培训课程（初训）","【课程八】危险化学品储存单位主要负责人培训课程（初训）"]
             * logo : group2/M00/00/0B/eDdWmlnPDsqASNCRAAFDi7bWDHo905.jpg
             * name : 危险化学品储存单位主要负责人培训课程（初训）
             */
            private List<String> child;
            private String logo;
            private String name;
            private int libId;
            private int libPackageId;

            public int getLibId() {
                return libId;
            }

            public void setLibId(int libId) {
                this.libId = libId;
            }

            public int getLibPackageId() {
                return libPackageId;
            }

            public void setLibPackageId(int libPackageId) {
                this.libPackageId = libPackageId;
            }

            private String packageName;

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public void setChild(List<String> child) {
                this.child = child;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getChild() {
                return child;
            }

            public String getLogo() {
                return logo;
            }

            public String getName() {
                return name;
            }
        }
    }
}
