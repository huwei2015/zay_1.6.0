package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/9 0009.
 */
public class SaveUserErrorPrcticeBean {

    /**
     * errorQues : [{"errorAnswerIds":"1,2","questionId":1,"questionType":1},{"errorAnswerIds":"1,2","questionId":1,"questionType":1}]
     * quesLibId : 1
     */

    private int quesLibId;
    /**
     * errorAnswerIds : 1,2
     * questionId : 1
     * questionType : 1
     */

    private List<ErrorQuesBean> errorQues;

    public int getQuesLibId() {
        return quesLibId;
    }

    public void setQuesLibId(int quesLibId) {
        this.quesLibId = quesLibId;
    }

    public List<ErrorQuesBean> getErrorQues() {
        return errorQues;
    }

    public void setErrorQues(List<ErrorQuesBean> errorQues) {
        this.errorQues = errorQues;
    }

    public static class ErrorQuesBean {
        private String errorAnswerIds;
        private int questionId;
        private int questionType;

        private int location;

        public String getUserAnswerIds() {
            return userAnswerIds;
        }

        public void setUserAnswerIds(String userAnswerIds) {
            this.userAnswerIds = userAnswerIds;
        }

        private String userAnswerIds;
        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }
        public int getIsRight() {
            return isRight;
        }

        public void setIsRight(int isRight) {
            this.isRight = isRight;
        }

        private int isRight;

        public String getErrorAnswerIds() {
            return errorAnswerIds;
        }

        public void setErrorAnswerIds(String errorAnswerIds) {
            this.errorAnswerIds = errorAnswerIds;
        }

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
    }
}
