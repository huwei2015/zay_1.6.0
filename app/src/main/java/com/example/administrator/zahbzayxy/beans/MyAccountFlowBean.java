package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/5/8 0008.
 */
public class MyAccountFlowBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"accountFlows":[{"amount":"10.00","createTime":"2017-05-08 11:52:24","id":1,"tradeType":0},{"amount":"1998.00","createTime":"2017-05-08 15:01:27","id":2,"tradeType":1},{"amount":"2998.00","createTime":"2017-05-08 15:16:09","id":3,"tradeType":1},{"amount":"50.00","createTime":"2017-05-08 15:16:50","id":4,"tradeType":1},{"amount":"518.00","createTime":"2017-05-08 15:19:10","id":5,"tradeType":1}],"totalPage":1,"isLastPage":true,"pageSize":10,"currentPage":1,"totalRecord":5,"isFirstPage":true}
     */

    private String code;
    private Object errMsg;
    /**
     * accountFlows : [{"amount":"10.00","createTime":"2017-05-08 11:52:24","id":1,"tradeType":0},{"amount":"1998.00","createTime":"2017-05-08 15:01:27","id":2,"tradeType":1},{"amount":"2998.00","createTime":"2017-05-08 15:16:09","id":3,"tradeType":1},{"amount":"50.00","createTime":"2017-05-08 15:16:50","id":4,"tradeType":1},{"amount":"518.00","createTime":"2017-05-08 15:19:10","id":5,"tradeType":1}]
     * totalPage : 1
     * isLastPage : true
     * pageSize : 10
     * currentPage : 1
     * totalRecord : 5
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
         * amount : 10.00
         * createTime : 2017-05-08 11:52:24
         * id : 1
         * tradeType : 0
         */

        private List<AccountFlowsBean> accountFlows;

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

        public List<AccountFlowsBean> getAccountFlows() {
            return accountFlows;
        }

        public void setAccountFlows(List<AccountFlowsBean> accountFlows) {
            this.accountFlows = accountFlows;
        }

        public static class AccountFlowsBean {
            private String amount;
            private String createTime;
            private int id;
            private int tradeType;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTradeType() {
                return tradeType;
            }

            public void setTradeType(int tradeType) {
                this.tradeType = tradeType;
            }
        }
    }
}
