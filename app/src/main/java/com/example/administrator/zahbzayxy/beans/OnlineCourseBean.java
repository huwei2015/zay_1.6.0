package com.example.administrator.zahbzayxy.beans;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-20.
 * Time 10:16.
 * 在线课bean
 */
public class OnlineCourseBean {
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
        private boolean play;
        private String beginDate;
        private String courseName;
        private String learnTimePercent;
        private int timeState;
        private int userCourseId;
        private String endDate;
        private int totalHours;
        private int currPlaySelectionId;
        private String imagePath;
        private int mainCourseId;
        private String msg_cont;

        public boolean isPlay() {
            return play;
        }

        public void setPlay(boolean play) {
            this.play = play;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getLearnTimePercent() {
            return learnTimePercent;
        }

        public void setLearnTimePercent(String learnTimePercent) {
            this.learnTimePercent = learnTimePercent;
        }

        public int getTimeState() {
            return timeState;
        }

        public void setTimeState(int timeState) {
            this.timeState = timeState;
        }

        public int getUserCourseId() {
            return userCourseId;
        }

        public void setUserCourseId(int userCourseId) {
            this.userCourseId = userCourseId;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getTotalHours() {
            return totalHours;
        }

        public void setTotalHours(int totalHours) {
            this.totalHours = totalHours;
        }

        public int getCurrPlaySelectionId() {
            return currPlaySelectionId;
        }

        public void setCurrPlaySelectionId(int currPlaySelectionId) {
            this.currPlaySelectionId = currPlaySelectionId;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public int getMainCourseId() {
            return mainCourseId;
        }

        public void setMainCourseId(int mainCourseId) {
            this.mainCourseId = mainCourseId;
        }

        public String getMsg_cont() {
            return msg_cont;
        }

        public void setMsg_cont(String msg_cont) {
            this.msg_cont = msg_cont;
        }

        @Override
        public String toString() {
            return "UserCoursesBean{" +
                    "play=" + play +
                    ", beginDate='" + beginDate + '\'' +
                    ", courseName='" + courseName + '\'' +
                    ", learnTimePercent='" + learnTimePercent + '\'' +
                    ", timeState=" + timeState +
                    ", userCourseId=" + userCourseId +
                    ", endDate='" + endDate + '\'' +
                    ", totalHours=" + totalHours +
                    ", currPlaySelectionId=" + currPlaySelectionId +
                    ", imagePath='" + imagePath + '\'' +
                    ", mainCourseId=" + mainCourseId +
                    ", msg_cont='" + msg_cont + '\'' +
                    '}';
        }
    }
}
