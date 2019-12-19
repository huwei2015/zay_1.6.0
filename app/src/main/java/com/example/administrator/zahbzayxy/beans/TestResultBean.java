package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/22 0022.
 */
public  class TestResultBean {


    /**
     * quesLibId : 1
     * paperName : xxxxxx
     * examBeginTime : 2017-01-0112: 00: 00
     * assignTime : 2017-01-0112: 00: 00
     * totalScore : 56
     * correctRate : 50
     * examDetails : [{"questionId":2,"questionType":2,"userAnswerIds":"1,2","isRight":0},{"questionId":2,"questionType":2,"userAnswerIds":"1","isRight":1}]
     */

    private int quesLibId;
    private String paperName;
    private String examBeginTime;
    private String assignTime;
    private int totalScore;
    private int correctRate;

     int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    /**
     * questionId : 2
     * questionType : 2
     * userAnswerIds : 1,2
     * isRight : 0
     */

    private List<ExamDetailsBean> examDetails;




    public int getQuesLibId() {
        return quesLibId;
    }

    public void setQuesLibId(int quesLibId) {
        this.quesLibId = quesLibId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getExamBeginTime() {
        return examBeginTime;
    }

    public void setExamBeginTime(String examBeginTime) {
        this.examBeginTime = examBeginTime;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(int correctRate) {
        this.correctRate = correctRate;
    }

    public List<ExamDetailsBean> getExamDetails() {
        return examDetails;
    }

    public void setExamDetails(List<ExamDetailsBean> examDetails) {
        this.examDetails = examDetails;
    }




    public static  class ExamDetailsBean {
        int tag;

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        private int questionId;
        private int questionType;
        private String userAnswerIds;
        private int isRight;

        private int location;

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        private int size;


        public int getQuestionId() {
            return questionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public int getQuestionType() {
            return questionType;
        }

        public void setQuestionType(int questionType) {
            this.questionType = questionType;
        }

        public String getUserAnswerIds() {
            return userAnswerIds;
        }

        public void setUserAnswerIds(String userAnswerIds) {
            this.userAnswerIds = userAnswerIds;
        }

        public int getIsRight() {
            return isRight;
        }

        public void setIsRight(int isRight) {
            this.isRight = isRight;
        }


    }
}
