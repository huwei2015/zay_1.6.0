package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 15:47.
 * 已授权
 */
public class HasAuthorBean {
    private String code;
    private String errMsg;
    private HasAuthorList data;

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

    public HasAuthorList getData() {
        return data;
    }

    public void setData(HasAuthorList data) {
        this.data = data;
    }

    public static class HasAuthorList implements Serializable {
        private List<HasAuthBeanList> orderList;
        private int totalPage;
        private int currentPage;
        private int totalCount;

        public List<HasAuthBeanList> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<HasAuthBeanList> orderList) {
            this.orderList = orderList;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }
    public static class HasAuthBeanList implements Serializable{
        private int id;
        private String orderNumber;
        private int orderStatus;
        private String orderStatusStr;
        private int orderType;
        private String orderTypeStr;
        private String createTime;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusStr() {
            return orderStatusStr;
        }

        public void setOrderStatusStr(String orderStatusStr) {
            this.orderStatusStr = orderStatusStr;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public String getOrderTypeStr() {
            return orderTypeStr;
        }

        public void setOrderTypeStr(String orderTypeStr) {
            this.orderTypeStr = orderTypeStr;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
