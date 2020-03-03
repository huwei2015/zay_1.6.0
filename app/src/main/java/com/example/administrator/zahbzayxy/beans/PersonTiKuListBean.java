package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/23 0023.
 */
public class PersonTiKuListBean {


    /**
     * errMsg : null
     * data : {"isLastPage":true,"pageSize":10,"currentPage":1,"quesLibs":[{"examNum":-4,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":25,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":14},{"examNum":4973,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":26,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"原题库","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":27},{"examNum":5000,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":27,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":0},{"examNum":5000,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":28,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":0},{"examNum":5000,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":29,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":0},{"examNum":5000,"oPrice":"12.00","sPrice":"12.00","quesCount":679,"quesLibId":88,"userLibId":40,"quesLibName":"煤炭生产经营单位（地质地测安全管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_3-AN3XfAAMygPOGf44853.jpg","examUsedNum":0}],"totalRecord":6,"totalPage":1,"isFirstPage":true}
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
         * quesLibs : [{"examNum":-4,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":25,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":14},{"examNum":4973,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":26,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"原题库","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":27},{"examNum":5000,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":27,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":0},{"examNum":5000,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":28,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":0},{"examNum":5000,"oPrice":"12.00","sPrice":"12.00","quesCount":851,"quesLibId":90,"userLibId":29,"quesLibName":"煤炭生产经营单位（安全生产管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg","examUsedNum":0},{"examNum":5000,"oPrice":"12.00","sPrice":"12.00","quesCount":679,"quesLibId":88,"userLibId":40,"quesLibName":"煤炭生产经营单位（地质地测安全管理人员）","packageName":"套餐A","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmH_3-AN3XfAAMygPOGf44853.jpg","examUsedNum":0}]
         * totalRecord : 6
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
             * examNum : -4
             * oPrice : 12.00
             * sPrice : 12.00
             * quesCount : 851
             * quesLibId : 90
             * userLibId : 25
             * quesLibName : 煤炭生产经营单位（安全生产管理人员）
             * packageName : 套餐A
             * quesLibImageUrl : http://192.168.2.234//group2/M00/00/07/eDdWmlmH_7GAFN76AAMpAS-x0eQ388.jpg
             * examUsedNum : 14
             */
            private int examNum;
            private String oPrice;
            private String sPrice;
            private int quesCount;
            private int quesLibId;
            private int userLibId;
            private String quesLibName;
            private String packageName;
            private String quesLibImageUrl;
            private int examUsedNum;
            private int packageId;
            private int state;
            private String msg_cont;
            private int limitSign;

            public int getLimitSign() {
                return limitSign;
            }

            public void setLimitSign(int limitSign) {
                this.limitSign = limitSign;
            }

            public String getMsg_cont() {
                return msg_cont;
            }

            public void setMsg_cont(String msg_cont) {
                this.msg_cont = msg_cont;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public void setPackageId(int packageId) {
                this.packageId = packageId;
            }

            //剩余天数
            private int RemainingTime;
            //0不带解析显示为不限次数；1带解析显示剩余次数
            private int isParsing;

            public int getRemainingTime() {
                return RemainingTime;
            }

            public void setRemainingTime(int remainingTime) {
                RemainingTime = remainingTime;
            }
            public int getPackageId() {
                return packageId;
            }
            public int isParsing() {
                return isParsing;
            }

            public void setParsing(int parsing) {
                isParsing = parsing;
            }


            public void setExamNum(int examNum) {
                this.examNum = examNum;
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

            public void setQuesLibId(int quesLibId) {
                this.quesLibId = quesLibId;
            }

            public void setUserLibId(int userLibId) {
                this.userLibId = userLibId;
            }

            public void setQuesLibName(String quesLibName) {
                this.quesLibName = quesLibName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public void setQuesLibImageUrl(String quesLibImageUrl) {
                this.quesLibImageUrl = quesLibImageUrl;
            }

            public void setExamUsedNum(int examUsedNum) {
                this.examUsedNum = examUsedNum;
            }

            public int getExamNum() {
                return examNum;
            }

            public String getOPrice() {
                return oPrice;
            }

            public String getSPrice() {
                return sPrice;
            }

            public int getQuesCount() {
                return quesCount;
            }

            public int getQuesLibId() {
                return quesLibId;
            }

            public int getUserLibId() {
                return userLibId;
            }

            public String getQuesLibName() {
                return quesLibName;
            }

            public String getPackageName() {
                return packageName;
            }

            public String getQuesLibImageUrl() {
                return quesLibImageUrl;
            }

            public int getExamUsedNum() {
                return examUsedNum;
            }
        }
    }
}
