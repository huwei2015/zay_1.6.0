package com.example.administrator.zahbzayxy.beans;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 14:06.
 * 考试未通过bean
 */
public class NotPassBean {
    private String code;
    private String errMsg;
    private NotPassListBean data;

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

    public NotPassListBean getData() {
        return data;
    }

    public void setData(NotPassListBean data) {
        this.data = data;
    }

    public static class NotPassListBean implements Serializable{
        private NotDataBean qLibs;

        public NotDataBean getqLibs() {
            return qLibs;
        }

        public void setqLibs(NotDataBean qLibs) {
            this.qLibs = qLibs;
        }
    }
    public static class NotDataBean implements Serializable{
        private int currentPage;
        private int pageSize;
        private int totalPage;
        private int totalRecord;
        private boolean isFirstPage;
        private boolean isLastPage;
        private List<NotListData> data;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public boolean isFirstPage() {
            return isFirstPage;
        }

        public void setFirstPage(boolean firstPage) {
            isFirstPage = firstPage;
        }

        public boolean isLastPage() {
            return isLastPage;
        }

        public void setLastPage(boolean lastPage) {
            isLastPage = lastPage;
        }

        public List<NotListData> getData() {
            return data;
        }

        public void setData(List<NotListData> data) {
            this.data = data;
        }
    }
    public static class NotListData implements Serializable{
        private int isLimitCount;
         private int quesLibPackageId;
         private String quesLibName;
         private String imagePath;
         private int timeInvalid;
         private int passScore;
         private int totalScore;
         private int examShow;
         private int userExamNum;
         private int  id;
         private String packageName;
         private String endTime;
         private int quesLibExamNum;
         private int quesLibId;
         private String isExam;
         private int trainType;
         private int classCerStatus;

        public String getIsExam() {
            return isExam;
        }

        public void setIsExam(String isExam) {
            this.isExam = isExam;
        }

        public int getTrainType() {
            return trainType;
        }

        public void setTrainType(int trainType) {
            this.trainType = trainType;
        }

        public int getClassCerStatus() {
            return classCerStatus;
        }

        public void setClassCerStatus(int classCerStatus) {
            this.classCerStatus = classCerStatus;
        }

        public int getIsLimitCount() {
            return isLimitCount;
        }

        public void setIsLimitCount(int isLimitCount) {
            this.isLimitCount = isLimitCount;
        }

        public int getQuesLibPackageId() {
            return quesLibPackageId;
        }

        public void setQuesLibPackageId(int quesLibPackageId) {
            this.quesLibPackageId = quesLibPackageId;
        }

        public String getQuesLibName() {
            return quesLibName;
        }

        public void setQuesLibName(String quesLibName) {
            this.quesLibName = quesLibName;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getTimeInvalid() {
            return timeInvalid;
        }

        public void setTimeInvalid(int timeInvalid) {
            this.timeInvalid = timeInvalid;
        }

        public int getPassScore() {
            return passScore;
        }

        public void setPassScore(int passScore) {
            this.passScore = passScore;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public int getExamShow() {
            return examShow;
        }

        public void setExamShow(int examShow) {
            this.examShow = examShow;
        }

        public int getUserExamNum() {
            return userExamNum;
        }

        public void setUserExamNum(int userExamNum) {
            this.userExamNum = userExamNum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getQuesLibExamNum() {
            return quesLibExamNum;
        }

        public void setQuesLibExamNum(int quesLibExamNum) {
            this.quesLibExamNum = quesLibExamNum;
        }

        public int getQuesLibId() {
            return quesLibId;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }
    }
}
