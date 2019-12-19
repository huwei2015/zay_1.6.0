package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/19 0019.
 */
public class NewMyChengJiListBean {


    /**
     * errMsg : null
     * data : {"isLastPage":false,"pageSize":10,"currentPage":1,"totalRecord":11,"totalPage":2,"isFirstPage":true,"examScores":[{"totalScore":0,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":185,"useTime":"0:0:12"},{"totalScore":4,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":24,"useTime":"0:6:23"},{"totalScore":3,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":21,"useTime":"0:0:24"},{"totalScore":4,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":20,"useTime":"0:0:26"},{"totalScore":3,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":19,"useTime":"0:0:23"},{"totalScore":5,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":18,"useTime":"0:0:25"},{"totalScore":0,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":17,"useTime":"0:28:19"},{"totalScore":3,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":16,"useTime":"0:0:27"},{"totalScore":0,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":15,"useTime":"0:0:59"},{"totalScore":0,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":14,"useTime":"0:0:22"}]}
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
         * isLastPage : false
         * pageSize : 10
         * currentPage : 1
         * totalRecord : 11
         * totalPage : 2
         * isFirstPage : true
         * examScores : [{"totalScore":0,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":185,"useTime":"0:0:12"},{"totalScore":4,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":24,"useTime":"0:6:23"},{"totalScore":3,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":21,"useTime":"0:0:24"},{"totalScore":4,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":20,"useTime":"0:0:26"},{"totalScore":3,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":19,"useTime":"0:0:23"},{"totalScore":5,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":18,"useTime":"0:0:25"},{"totalScore":0,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":17,"useTime":"0:28:19"},{"totalScore":3,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":16,"useTime":"0:0:27"},{"totalScore":0,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":15,"useTime":"0:0:59"},{"totalScore":0,"paperName":"煤炭生产经营单位（一通三防安全管理人员）培训课程","examScoreId":14,"useTime":"0:0:22"}]
         */
        private boolean isLastPage;
        private int pageSize;
        private int currentPage;
        private int totalRecord;
        private int totalPage;
        private boolean isFirstPage;
        private List<ExamScoresEntity> examScores;

        public void setIsLastPage(boolean isLastPage) {
            this.isLastPage = isLastPage;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
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

        public void setExamScores(List<ExamScoresEntity> examScores) {
            this.examScores = examScores;
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

        public int getTotalRecord() {
            return totalRecord;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public boolean isIsFirstPage() {
            return isFirstPage;
        }

        public List<ExamScoresEntity> getExamScores() {
            return examScores;
        }

        public class ExamScoresEntity {
            /**
             * totalScore : 0
             * paperName : 煤炭生产经营单位（一通三防安全管理人员）培训课程
             * examScoreId : 185
             * useTime : 0:0:12
             */
            private String totalScore;
            private String paperName;
            private double examScoreId;
            private String useTime;

            private String libPackageName;

            private Integer isShowDetail;

            private int libScore;

            private String scoreDate;

            public String getScoreDate() {
                return scoreDate;
            }

            public void setScoreDate(String scoreDate) {
                this.scoreDate = scoreDate;
            }

            public int getLibScore() {
                return libScore;
            }

            public void setLibScore(int libScore) {
                this.libScore = libScore;
            }

            public Integer getIsShowDetail() {
                return isShowDetail;
            }
            public void setIsShowDetail(Integer isShowDetail) {
                this.isShowDetail = isShowDetail;
            }
            public String getLibPackageName() {
                return libPackageName;
            }

            public void setLibPackageName(String libPackageName) {
                this.libPackageName = libPackageName;
            }

            private int userLibId;

            public int getUserLibId() {
                return userLibId;
            }

            public void setUserLibId(int userLibId) {
                this.userLibId = userLibId;
            }

            public void setTotalScore(String totalScore) {
                this.totalScore = totalScore;
            }

            public void setPaperName(String paperName) {
                this.paperName = paperName;
            }

            public void setExamScoreId(int examScoreId) {
                this.examScoreId = examScoreId;
            }

            public void setUseTime(String useTime) {
                this.useTime = useTime;
            }

            public String getTotalScore() {
                return totalScore;
            }

            public String getPaperName() {
                return paperName;
            }

            public double getExamScoreId() {
                return examScoreId;
            }

            public String getUseTime() {
                return useTime;
            }
        }
    }
}
