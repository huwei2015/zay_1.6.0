package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/7 0007.
 */
public class PMyTestOrderBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"totalPage":1,"isLastPage":true,"pageSize":10,"quesLibOrders":[{"orderNumber":"1490769570100","payMoney":"0.10","quesLibName":"测试题库33","createTime":"2017-03-29 14:39:30","orderStatus":1,"quesLibImageUrl":"http://192.168.1.234/group1/M00/00/00/wKgB6lihcWOAaoIfAAIbCObcUKA029.jpg","payStatus":1,"quesLibId":16}],"currentPage":1,"totalRecord":1,"isFirstPage":true}
     */

    private String code;
    private Object errMsg;
    /**
     * totalPage : 1
     * isLastPage : true
     * pageSize : 10
     * quesLibOrders : [{"orderNumber":"1490769570100","payMoney":"0.10","quesLibName":"测试题库33","createTime":"2017-03-29 14:39:30","orderStatus":1,"quesLibImageUrl":"http://192.168.1.234/group1/M00/00/00/wKgB6lihcWOAaoIfAAIbCObcUKA029.jpg","payStatus":1,"quesLibId":16}]
     * currentPage : 1
     * totalRecord : 1
     * isFirstPage : true
     */

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int totalPage;
        private boolean isLastPage;
        private int pageSize;
        private int currentPage;
        private int totalRecord;
        private boolean isFirstPage;
        /**
         * orderNumber : 1490769570100
         * payMoney : 0.10
         * quesLibName : 测试题库33
         * createTime : 2017-03-29 14:39:30
         * orderStatus : 1
         * quesLibImageUrl : http://192.168.1.234/group1/M00/00/00/wKgB6lihcWOAaoIfAAIbCObcUKA029.jpg
         * payStatus : 1
         * quesLibId : 16
         */

        private List<QuesLibOrdersBean> quesLibOrders;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public List<QuesLibOrdersBean> getQuesLibOrders() {
            return quesLibOrders;
        }

        public void setQuesLibOrders(List<QuesLibOrdersBean> quesLibOrders) {
            this.quesLibOrders = quesLibOrders;
        }

        public static class QuesLibOrdersBean {
            private String orderNumber;
            private String payMoney;
            private String quesLibName;
            private String createTime;
            private int orderStatus;
            private String quesLibImageUrl;
            private int payStatus;
            private int quesLibId;

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            int tag;


            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getPayMoney() {
                return payMoney;
            }

            public void setPayMoney(String payMoney) {
                this.payMoney = payMoney;
            }

            public String getQuesLibName() {
                return quesLibName;
            }

            public void setQuesLibName(String quesLibName) {
                this.quesLibName = quesLibName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(int orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getQuesLibImageUrl() {
                return quesLibImageUrl;
            }

            public void setQuesLibImageUrl(String quesLibImageUrl) {
                this.quesLibImageUrl = quesLibImageUrl;
            }

            public int getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(int payStatus) {
                this.payStatus = payStatus;
            }

            public int getQuesLibId() {
                return quesLibId;
            }

            public void setQuesLibId(int quesLibId) {
                this.quesLibId = quesLibId;
            }
        }
    }
}
