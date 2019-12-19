package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/1 0001.
 */
public class PersonMyTestBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"quesLibs":[{"quesLibName":"测试题库12","quesCount":48,"oPrice":"100.00","sPrice":"0.01","quesLibImageUrl":"http://192.168.1.234/group1/M00/00/00/wKgB6lihcWOAaoIfAAIbCObcUKA029.jpg","quesLibId":14},{"quesLibName":"测试题库22","quesCount":36,"oPrice":"100.00","sPrice":"0.01","quesLibImageUrl":"http://192.168.1.234/group1/M00/00/00/wKgB6lihcWOAaoIfAAIbCObcUKA029.jpg","quesLibId":15}],"totalPage":1,"isLastPage":true,"pageSize":10,"currentPage":1,"totalRecord":2,"isFirstPage":true}
     */

    private String code;
    private Object errMsg;
    /**
     * quesLibs : [{"quesLibName":"测试题库12","quesCount":48,"oPrice":"100.00","sPrice":"0.01","quesLibImageUrl":"http://192.168.1.234/group1/M00/00/00/wKgB6lihcWOAaoIfAAIbCObcUKA029.jpg","quesLibId":14},{"quesLibName":"测试题库22","quesCount":36,"oPrice":"100.00","sPrice":"0.01","quesLibImageUrl":"http://192.168.1.234/group1/M00/00/00/wKgB6lihcWOAaoIfAAIbCObcUKA029.jpg","quesLibId":15}]
     * totalPage : 1
     * isLastPage : true
     * pageSize : 10
     * currentPage : 1
     * totalRecord : 2
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
         * quesLibName : 测试题库12
         * quesCount : 48
         * oPrice : 100.00
         * sPrice : 0.01
         * quesLibImageUrl : http://192.168.1.234/group1/M00/00/00/wKgB6lihcWOAaoIfAAIbCObcUKA029.jpg
         * quesLibId : 14
         */

        private List<QuesLibsBean> quesLibs;

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

        public List<QuesLibsBean> getQuesLibs() {
            return quesLibs;
        }

        public void setQuesLibs(List<QuesLibsBean> quesLibs) {
            this.quesLibs = quesLibs;
        }

        public static class QuesLibsBean {
            private String quesLibName;
            private int quesCount;
            private String oPrice;
            private String sPrice;
            private String quesLibImageUrl;
            private int quesLibId;

            public String getQuesLibName() {
                return quesLibName;
            }

            public void setQuesLibName(String quesLibName) {
                this.quesLibName = quesLibName;
            }

            public int getQuesCount() {
                return quesCount;
            }

            public void setQuesCount(int quesCount) {
                this.quesCount = quesCount;
            }

            public String getOPrice() {
                return oPrice;
            }

            public void setOPrice(String oPrice) {
                this.oPrice = oPrice;
            }

            public String getSPrice() {
                return sPrice;
            }

            public void setSPrice(String sPrice) {
                this.sPrice = sPrice;
            }

            public String getQuesLibImageUrl() {
                return quesLibImageUrl;
            }

            public void setQuesLibImageUrl(String quesLibImageUrl) {
                this.quesLibImageUrl = quesLibImageUrl;
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
