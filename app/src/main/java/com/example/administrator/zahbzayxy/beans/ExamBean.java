package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019/8/5.
 * Time 15:24.
 * Description.我的考试
 */
public class ExamBean {
    private String code;
    private String errMsg;
    private ExamListBean data;

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

    public ExamListBean getData() {
        return data;
    }

    public void setData(ExamListBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ExamBean{" +
                "code='" + code + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ExamListBean implements Serializable{
        private List<QuesLibsBean> quesLibs;
        private int pageSize;
        private int currentPage;

        public List<QuesLibsBean> getQuesLibs() {
            return quesLibs;
        }

        public void setQuesLibs(List<QuesLibsBean> quesLibs) {
            this.quesLibs = quesLibs;
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

        @Override
        public String toString() {
            return "ExamListBean{" +
                    "quesLibs=" + quesLibs +
                    ", pageSize=" + pageSize +
                    ", currentPage=" + currentPage +
                    '}';
        }
    }
    public static class QuesLibsBean implements Serializable{
        private int userQuesLibId;
        private int classCerStatus;
        private int userCourseId;
        private String quesLibName;
        private int trainType;
        private int userCardId;
        private int userExamNum;
        private String endTime;
        private String isExam;
        private int quesLibExamNum;
        private int quesLibId;

        public int getQuesLibId() {
            return quesLibId;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }

        public int getUserQuesLibId() {
            return userQuesLibId;
        }

        public void setUserQuesLibId(int userQuesLibId) {
            this.userQuesLibId = userQuesLibId;
        }

        public int getClassCerStatus() {
            return classCerStatus;
        }

        public void setClassCerStatus(int classCerStatus) {
            this.classCerStatus = classCerStatus;
        }

        public int getUserCourseId() {
            return userCourseId;
        }

        public void setUserCourseId(int userCourseId) {
            this.userCourseId = userCourseId;
        }

        public String getQuesLibName() {
            return quesLibName;
        }

        public void setQuesLibName(String quesLibName) {
            this.quesLibName = quesLibName;
        }

        public int getTrainType() {
            return trainType;
        }

        public void setTrainType(int trainType) {
            this.trainType = trainType;
        }

        public int getUserCardId() {
            return userCardId;
        }

        public void setUserCardId(int userCardId) {
            this.userCardId = userCardId;
        }

        public int getUserExamNum() {
            return userExamNum;
        }

        public void setUserExamNum(int userExamNum) {
            this.userExamNum = userExamNum;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getIsExam() {
            return isExam;
        }

        public void setIsExam(String isExam) {
            this.isExam = isExam;
        }

        public int getQuesLibExamNum() {
            return quesLibExamNum;
        }

        public void setQuesLibExamNum(int quesLibExamNum) {
            this.quesLibExamNum = quesLibExamNum;
        }

        @Override
        public String toString() {
            return "QuesLibsBean{" +
                    "userQuesLibId=" + userQuesLibId +
                    ", classCerStatus=" + classCerStatus +
                    ", userCourseId=" + userCourseId +
                    ", quesLibName='" + quesLibName + '\'' +
                    ", trainType=" + trainType +
                    ", userCardId=" + userCardId +
                    ", userExamNum=" + userExamNum +
                    ", endTime='" + endTime + '\'' +
                    ", isExam='" + isExam + '\'' +
                    ", quesLibExamNum=" + quesLibExamNum +
                    ", quesLibId=" + quesLibId +
                    '}';
        }
    }

}
