package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2019/7/9.
 * Time 13:53.
 * Description.
 */
public class ExamResultBean {
    private String code;
    private String errMsg;
    private ResultBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public ResultBean getData() {
        return data;
    }

    public void setData(ResultBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Test{" +
                "code='" + code + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ResultBean implements Serializable {
        private boolean isPassed;
        private String examDate;
        private String idCard;
        private String examName;
        private Double examScore;
        private String passMsg;
        private String userName;
        private int userLibId;

        public boolean isPassed() {
            return isPassed;
        }

        public void setPassed(boolean passed) {
            isPassed = passed;
        }

        public String getExamDate() {
            return examDate;
        }

        public void setExamDate(String examDate) {
            this.examDate = examDate;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }

        public double getExamScore() {
            return examScore;
        }

        public void setExamScore(double examScore) {
            this.examScore = examScore;
        }

        public String getPassMsg() {
            return passMsg;
        }

        public void setPassMsg(String passMsg) {
            this.passMsg = passMsg;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserLibId() {
            return userLibId;
        }

        public void setUserLibId(int userLibId) {
            this.userLibId = userLibId;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "isPassed=" + isPassed +
                    ", examDate='" + examDate + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", examName='" + examName + '\'' +
                    ", examScore=" + examScore +
                    ", passMsg='" + passMsg + '\'' +
                    ", userName='" + userName + '\'' +
                    ", userLibId=" + userLibId +
                    '}';
        }
    }
}
