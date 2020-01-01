package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class OfflineCoursePOBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"dividePrice":"课时收费标准：1-16学时 12元/学时;16学时以上 10元/学时","courseList":[{"beginDate":1491557859000,"courseName":"金属非金属矿山安全生产管理人员培训课程（地下矿-初训）","userCourseId":19,"totalHours":54,"endDate":1492249059000,"logo":"/file_upload/course/logo/2016/9/ee9a5251-39de-43c7-9773-19cec6f8b148.jpg","coruseId":186}]}
     */

    private String code;
    private Object errMsg;

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private CourseBean course;

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public static class CourseBean {
            private int courseId;
            private String courseName;
            private String putawayStatus;
            private String putawayStatusName;
            private int platformId;
            private int totalHours;
            private String updateTime;
            private String imagePath;
            private int isTrailers;
            private String salePrice;
            private String originalPrice;
            private int isRecommend;
            private String courseDesc;
            private String teacherNames;
            private int courseHours;


            public int getCourseHours() {
                return courseHours;
            }

            public void setCourseHours(int courseHours) {
                this.courseHours = courseHours;
            }

            public String getTeacherNames() {
                return teacherNames;
            }

            public void setTeacherNames(String teacherNames) {
                this.teacherNames = teacherNames;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public String getPutawayStatus() {
                return putawayStatus;
            }

            public void setPutawayStatus(String putawayStatus) {
                this.putawayStatus = putawayStatus;
            }

            public String getPutawayStatusName() {
                return putawayStatusName;
            }

            public void setPutawayStatusName(String putawayStatusName) {
                this.putawayStatusName = putawayStatusName;
            }

            public int getPlatformId() {
                return platformId;
            }

            public void setPlatformId(int platformId) {
                this.platformId = platformId;
            }

            public int getTotalHours() {
                return totalHours;
            }

            public void setTotalHours(int totalHours) {
                this.totalHours = totalHours;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public int getIsTrailers() {
                return isTrailers;
            }

            public void setIsTrailers(int isTrailers) {
                this.isTrailers = isTrailers;
            }

            public String getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(String salePrice) {
                this.salePrice = salePrice;
            }

            public String getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(String originalPrice) {
                this.originalPrice = originalPrice;
            }

            public int getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(int isRecommend) {
                this.isRecommend = isRecommend;
            }

            public String getCourseDesc() {
                return courseDesc;
            }

            public void setCourseDesc(String courseDesc) {
                this.courseDesc = courseDesc;
            }
        }

    }
}
