package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/11 0011.
 */
public class PCuoTiJiLuBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"errorRecordQuesLibs":[{"errorRecordCount":1,"quesLibName":null,"quesLibId":0},{"errorRecordCount":3,"quesLibName":"测试题库33","quesLibId":16}],"totalPage":1,"isLastPage":true,"pageSize":10,"currentPage":1,"totalRecord":2,"isFirstPage":true}
     */

    private String code;
    private Object errMsg;
    /**
     * errorRecordQuesLibs : [{"errorRecordCount":1,"quesLibName":null,"quesLibId":0},{"errorRecordCount":3,"quesLibName":"测试题库33","quesLibId":16}]
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
         * errorRecordCount : 1
         * quesLibName : null
         * quesLibId : 0
         */

        private List<ErrorRecordQuesLibsBean> errorRecordQuesLibs;

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

        public List<ErrorRecordQuesLibsBean> getErrorRecordQuesLibs() {
            return errorRecordQuesLibs;
        }

        public void setErrorRecordQuesLibs(List<ErrorRecordQuesLibsBean> errorRecordQuesLibs) {
            this.errorRecordQuesLibs = errorRecordQuesLibs;
        }

        public static class ErrorRecordQuesLibsBean {
            private int errorRecordCount;
            private String quesLibName;
            private int quesLibId;
            private String packageName;

            private int userLibId;

            private int packageId;

            public int getPackageId() {
                return packageId;
            }

            public void setPackageId(int packageId) {
                this.packageId = packageId;
            }

            public int getUserLibId() {
                return userLibId;
            }

            public void setUserLibId(int userLibId) {
                this.userLibId = userLibId;
            }
            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }



            public int getErrorRecordCount() {
                return errorRecordCount;
            }

            public void setErrorRecordCount(int errorRecordCount) {
                this.errorRecordCount = errorRecordCount;
            }

            public String getQuesLibName() {
                return quesLibName;
            }

            public void setQuesLibName(String quesLibName) {
                this.quesLibName = quesLibName;
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
