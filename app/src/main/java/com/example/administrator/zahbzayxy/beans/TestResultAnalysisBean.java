package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/15 0015.
 */
public class TestResultAnalysisBean {


    /**
     * errMsg : null
     * data : {"totalScore":1,"examBeginTime":"2017-12-15 18:18:58","quesLibId":92,"correctRate":1,"paperName":"煤矿安全监察人员","defeatNum":1,"assignTime":"2017-12-15 18:19:01","examDetails":[{"questionId":51033,"isRight":0,"userAnswerIds":"112981","questionType":1},{"questionId":51057,"isRight":1,"userAnswerIds":"113054","questionType":1}],"ranking":15}
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
         * totalScore : 1
         * examBeginTime : 2017-12-15 18:18:58
         * quesLibId : 92
         * correctRate : 1
         * paperName : 煤矿安全监察人员
         * defeatNum : 1
         * assignTime : 2017-12-15 18:19:01
         * examDetails : [{"questionId":51033,"isRight":0,"userAnswerIds":"112981","questionType":1},{"questionId":51057,"isRight":1,"userAnswerIds":"113054","questionType":1}]
         * ranking : 15
         */
        private int totalScore;
        private String examBeginTime;
        private int quesLibId;
        private int correctRate;
        private String paperName;
        private int defeatNum;
        private String assignTime;
        private List<ExamDetailsEntity> examDetails;
        private int ranking;

        public void setTotalScore(int totalScore) {
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

        public void setDefeatNum(int defeatNum) {
            this.defeatNum = defeatNum;
        }

        public void setAssignTime(String assignTime) {
            this.assignTime = assignTime;
        }

        public void setExamDetails(List<ExamDetailsEntity> examDetails) {
            this.examDetails = examDetails;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public int getTotalScore() {
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

        public int getDefeatNum() {
            return defeatNum;
        }

        public String getAssignTime() {
            return assignTime;
        }

        public List<ExamDetailsEntity> getExamDetails() {
            return examDetails;
        }

        public int getRanking() {
            return ranking;
        }

        public class ExamDetailsEntity {
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
