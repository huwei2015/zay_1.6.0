package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/17 0017.
 */
public class TestGradAnalyseBean {


    /**
     * errMsg : null
     * data : {"totalScore":1,"examBeginTime":"2017-12-15 18:18:58","quesLibId":92,"correctRate":1,"paperName":"ç\u0085¤ç\u009f¿å®\u0089å\u0085¨ç\u009b\u0091å¯\u009fäººå\u0091\u0098","isShowParsing":0,"defeatNum":1,"assignTime":"2017-12-15 18:19:01","shareUrl":"http://192.168.2.234//group2/M00/00/06/wKgC6lpCA12ANQ1fAADCha4Bu0E913.jpg","isShowPaper":1,"examDetails":[{"questionId":51033,"isRight":0,"userAnswerIds":"112981","questionType":1},{"questionId":51057,"isRight":1,"userAnswerIds":"113054","questionType":1}],"ranking":15}
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

    public static class DataEntity {
        /**
         * totalScore : 1
         * examBeginTime : 2017-12-15 18:18:58
         * quesLibId : 92
         * correctRate : 1
         * paperName : ç¤ç¿å®å¨çå¯äººå
         * isShowParsing : 0
         * defeatNum : 1
         * assignTime : 2017-12-15 18:19:01
         * shareUrl : http://192.168.2.234//group2/M00/00/06/wKgC6lpCA12ANQ1fAADCha4Bu0E913.jpg
         * isShowPaper : 1
         * examDetails : [{"questionId":51033,"isRight":0,"userAnswerIds":"112981","questionType":1},{"questionId":51057,"isRight":1,"userAnswerIds":"113054","questionType":1}]
         * ranking : 15
         */
        private double totalScore;
        private String examBeginTime;
        private int userLibId;

        private int quesLibId;

        public int getUserLibId() {
            return userLibId;
        }

        public void setUserLibId(int userLibId) {
            this.userLibId = userLibId;
        }
        private int correctRate;
        private String paperName;
        private int isShowParsing;
        private int defeatNum;
        private String assignTime;
        private String shareUrl;
        private int isShowPaper;
        private double RemainingTime;

        public double getRemainingTime() {
            return RemainingTime;
        }

        public void setRemainingTime(double remainingTime) {
            RemainingTime = remainingTime;
        }

        private int wrongNum;

        public int getWrongNum() {
            return wrongNum;
        }

        public void setWrongNum(int wrongNum) {
            this.wrongNum = wrongNum;
        }

        public int getRightNum() {
            return rightNum;
        }

        public void setRightNum(int rightNum) {
            this.rightNum = rightNum;
        }

        private int rightNum;


        private List<ExamDetailsEntity> examDetails;
        private int ranking;

        private int spareTime;

        public int getSpareTime() {
            return spareTime;
        }

        public void setSpareTime(int spareTime) {
            this.spareTime = spareTime;
        }

        public void setTotalScore(double totalScore) {
            this.totalScore = totalScore;
        }

        public void setExamBeginTime(String examBeginTime) {
            this.examBeginTime = examBeginTime;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }

        public void setCorrectRate(int correctRate) {
            this.correctRate = correctRate;
        }

        public void setPaperName(String paperName) {
            this.paperName = paperName;
        }

        public void setIsShowParsing(int isShowParsing) {
            this.isShowParsing = isShowParsing;
        }

        public void setDefeatNum(int defeatNum) {
            this.defeatNum = defeatNum;
        }

        public void setAssignTime(String assignTime) {
            this.assignTime = assignTime;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public void setIsShowPaper(int isShowPaper) {
            this.isShowPaper = isShowPaper;
        }

        public void setExamDetails(List<ExamDetailsEntity> examDetails) {
            this.examDetails = examDetails;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public double getTotalScore() {
            return totalScore;
        }

        public String getExamBeginTime() {
            return examBeginTime;
        }

        public int getQuesLibId() {
            return quesLibId;
        }

        public int getCorrectRate() {
            return correctRate;
        }

        public String getPaperName() {
            return paperName;
        }

        public int getIsShowParsing() {
            return isShowParsing;
        }

        public int getDefeatNum() {
            return defeatNum;
        }

        public String getAssignTime() {
            return assignTime;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public int getIsShowPaper() {
            return isShowPaper;
        }

        public List<ExamDetailsEntity> getExamDetails() {
            return examDetails;
        }

        public int getRanking() {
            return ranking;
        }

        public  static class ExamDetailsEntity {
            /**
             * questionId : 51033
             * isRight : 0
             * userAnswerIds : 112981
             * questionType : 1
             */
            private int questionId;
            private int isRight;
            private String userAnswerIds;
            private int questionType;
            private double score;
            private List<ExamDetailsEntity> children;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public List<ExamDetailsEntity> getChildren() {
                return children;
            }

            public void setChildren(List<ExamDetailsEntity> children) {
                this.children = children;
            }

            public int getLocation() {
                return location;
            }

            public void setLocation(int location) {
                this.location = location;
            }

            private int location;
            public void setQuestionId(int questionId) {
                this.questionId = questionId;
            }

            public void setIsRight(int isRight) {
                this.isRight = isRight;
            }

            public void setUserAnswerIds(String userAnswerIds) {
                this.userAnswerIds = userAnswerIds;
            }

            public void setQuestionType(int questionType) {
                this.questionType = questionType;
            }

            public int getQuestionId() {
                return questionId;
            }

            public int getIsRight() {
                return isRight;
            }

            public String getUserAnswerIds() {
                return userAnswerIds;
            }

            public int getQuestionType() {
                return questionType;
            }
        }
    }
}
