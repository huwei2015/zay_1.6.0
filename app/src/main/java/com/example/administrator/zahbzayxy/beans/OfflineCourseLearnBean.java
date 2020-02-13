package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 10:16.
 * 在线课bean
 */
public class OfflineCourseLearnBean {
    private String code;
    private String errMsg;
    private OnLineListBean data;

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

    public OnLineListBean getData() {
        return data;
    }

    public void setData(OnLineListBean data) {
        this.data = data;
    }

    public static class OnLineListBean implements Serializable{
        private int totalPage;
        private List<UserCoursesBean> userCourses;
        private boolean isLastPage;
        private int pageSize;
        private int currentPage;
        private int totalRecord;
        private boolean isFirstPage;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<UserCoursesBean> getUserCourses() {
            return userCourses;
        }

        public void setUserCourses(List<UserCoursesBean> userCourses) {
            this.userCourses = userCourses;
        }

        public boolean isLastPage() {
            return isLastPage;
        }

        public void setLastPage(boolean lastPage) {
            isLastPage = lastPage;
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

        public boolean isFirstPage() {
            return isFirstPage;
        }

        public void setFirstPage(boolean firstPage) {
            isFirstPage = firstPage;
        }
    }

    public static class UserCoursesBean implements Serializable{
        private String courseName;
        private String studyTime;
        private int courseHours;
        private int learnTimePercent;
        private int userCourseId;
        private String imagePath;
        private int state;
        private String classAddress;
        private int platformId;
        private int isNew;
        private String classTime;
        private int isSign;
        private int courseId;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getStudyTime() {
            return studyTime;
        }

        public void setStudyTime(String studyTime) {
            this.studyTime = studyTime;
        }

        public int getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(int courseHours) {
            this.courseHours = courseHours;
        }

        public int getLearnTimePercent() {
            return learnTimePercent;
        }

        public void setLearnTimePercent(int learnTimePercent) {
            this.learnTimePercent = learnTimePercent;
        }

        public int getUserCourseId() {
            return userCourseId;
        }

        public void setUserCourseId(int userCourseId) {
            this.userCourseId = userCourseId;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getClassAddress() {
            return classAddress;
        }

        public void setClassAddress(String classAddress) {
            this.classAddress = classAddress;
        }

        public int getPlatformId() {
            return platformId;
        }

        public void setPlatformId(int platformId) {
            this.platformId = platformId;
        }

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }

        public String getClassTime() {
            return classTime;
        }

        public void setClassTime(String classTime) {
            this.classTime = classTime;
        }

        public int getIsSign() {
            return isSign;
        }

        public void setIsSign(int isSign) {
            this.isSign = isSign;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        @Override
        public String toString() {
            return "UserCoursesBean{" +
                    "courseName='" + courseName + '\'' +
                    ", studyTime='" + studyTime + '\'' +
                    ", courseHours=" + courseHours +
                    ", learnTimePercent=" + learnTimePercent +
                    ", userCourseId=" + userCourseId +
                    ", imagePath='" + imagePath + '\'' +
                    ", state=" + state +
                    ", classAddress='" + classAddress + '\'' +
                    ", platformId=" + platformId +
                    ", isNew=" + isNew +
                    ", classTime='" + classTime + '\'' +
                    ", isSign=" + isSign +
                    ", courseId=" + courseId +
                    '}';
        }
    }
}
