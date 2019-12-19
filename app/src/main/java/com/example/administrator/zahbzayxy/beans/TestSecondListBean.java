package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/17 0017.
 */
public class TestSecondListBean {


    /**
     * errMsg : null
     * data : {"isLastPage":true,"pageSize":10,"currentPage":1,"quesLibs":[{"id":135,"oPrice":"0.00","sPrice":"0.00","quesCount":0,"quesLibName":"生产经营单位（除高危行业）","updateTime":"2017-08-11 11:43:03","imagePath":"http://192.168.2.234//group2/M00/00/06/eDdWmll2oW2Ad-3GAAL2T2gvfbc526.png"}],"totalRecord":1,"totalPage":1,"isFirstPage":true}
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
         * isLastPage : true
         * pageSize : 10
         * currentPage : 1
         * quesLibs : [{"id":135,"oPrice":"0.00","sPrice":"0.00","quesCount":0,"quesLibName":"生产经营单位（除高危行业）","updateTime":"2017-08-11 11:43:03","imagePath":"http://192.168.2.234//group2/M00/00/06/eDdWmll2oW2Ad-3GAAL2T2gvfbc526.png"}]
         * totalRecord : 1
         * totalPage : 1
         * isFirstPage : true
         */
        private boolean isLastPage;
        private int pageSize;
        private int currentPage;
        private List<QuesLibsEntity> quesLibs;
        private int totalRecord;
        private int totalPage;
        private boolean isFirstPage;

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setQuesLibs(List<QuesLibsEntity> quesLibs) {
            this.quesLibs = quesLibs;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setIsFirstPage(boolean isFirstPage) {
            this.isFirstPage = isFirstPage;
        }

        public boolean isIsLastPage() {
            return isLastPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public List<QuesLibsEntity> getQuesLibs() {
            return quesLibs;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public class QuesLibsEntity {
            /**
             * id : 135
             * oPrice : 0.00
             * sPrice : 0.00
             * quesCount : 0
             * quesLibName : 生产经营单位（除高危行业）
             * updateTime : 2017-08-11 11:43:03
             * imagePath : http://192.168.2.234//group2/M00/00/06/eDdWmll2oW2Ad-3GAAL2T2gvfbc526.png
             */
            private int id;
            private String oPrice;
            private String sPrice;
            private int quesCount;
            private String quesLibName;
            private String updateTime;
            private String imagePath;

            public void setId(int id) {
                this.id = id;
            }

            public void setOPrice(String oPrice) {
                this.oPrice = oPrice;
            }

            public void setSPrice(String sPrice) {
                this.sPrice = sPrice;
            }

            public void setQuesCount(int quesCount) {
                this.quesCount = quesCount;
            }

            public void setQuesLibName(String quesLibName) {
                this.quesLibName = quesLibName;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public int getId() {
                return id;
            }

            public String getOPrice() {
                return oPrice;
            }

            public String getSPrice() {
                return (sPrice!=null)?sPrice:"";
            }

            public int getQuesCount() {
                return quesCount;
            }

            public String getQuesLibName() {
                return quesLibName;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public String getImagePath() {
                return imagePath;
            }
        }
    }
}
