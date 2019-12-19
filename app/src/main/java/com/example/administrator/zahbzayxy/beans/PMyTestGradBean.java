package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/11 0011.
 */
public class PMyTestGradBean {


    /**
     * code : 00000
     * errMsg : null
     * data : {"examScores":[{"paperName":"aa","useTime":10,"totalScore":8},{"paperName":"aa","useTime":0,"totalScore":2},{"paperName":"aa","useTime":0,"totalScore":4},{"paperName":"aa","useTime":0,"totalScore":3},{"paperName":"aa","useTime":3,"totalScore":3},{"paperName":"aa","useTime":0,"totalScore":2},{"paperName":"aa","useTime":0,"totalScore":4},{"paperName":"aa","useTime":0,"totalScore":3},{"paperName":"aa","useTime":0,"totalScore":4},{"paperName":"aa","useTime":0,"totalScore":6}],"totalPage":9,"isLastPage":null,"pageSize":10,"currentPage":1,"totalRecord":89,"isFirstPage":true}
     */

    private String code;
    private Object errMsg;
    /**
     * examScores : [{"paperName":"aa","useTime":10,"totalScore":8},{"paperName":"aa","useTime":0,"totalScore":2},{"paperName":"aa","useTime":0,"totalScore":4},{"paperName":"aa","useTime":0,"totalScore":3},{"paperName":"aa","useTime":3,"totalScore":3},{"paperName":"aa","useTime":0,"totalScore":2},{"paperName":"aa","useTime":0,"totalScore":4},{"paperName":"aa","useTime":0,"totalScore":3},{"paperName":"aa","useTime":0,"totalScore":4},{"paperName":"aa","useTime":0,"totalScore":6}]
     * totalPage : 9
     * isLastPage : null
     * pageSize : 10
     * currentPage : 1
     * totalRecord : 89
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
        private Object isLastPage;
        private int pageSize;
        private int currentPage;
        private int totalRecord;
        private boolean isFirstPage;
        /**
         * paperName : aa
         * useTime : 10
         * totalScore : 8
         */

        private List<ExamScoresBean> examScores;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public Object getIsLastPage() {
            return isLastPage;
        }

        public void setIsLastPage(Object isLastPage) {
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

        public List<ExamScoresBean> getExamScores() {
            return examScores;
        }

        public void setExamScores(List<ExamScoresBean> examScores) {
            this.examScores = examScores;
        }

        public static class ExamScoresBean {
            private String paperName;
            private String useTime;
            private int totalScore;

            public String getPaperName() {
                return paperName;
            }

            public void setPaperName(String paperName) {
                this.paperName = paperName;
            }

            public String getUseTime() {
                return useTime;
            }

            public void setUseTime(String useTime) {
                this.useTime = useTime;
            }

            public int getTotalScore() {
                return totalScore;
            }

            public void setTotalScore(int totalScore) {
                this.totalScore = totalScore;
            }
        }
    }
}
