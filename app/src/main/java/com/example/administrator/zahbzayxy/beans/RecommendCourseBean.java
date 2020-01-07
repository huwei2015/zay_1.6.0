package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class RecommendCourseBean {

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
        private String dividePrice;

        private List<CourseListBean> courseList;

        public String getDividePrice() {
            return dividePrice;
        }

        public void setDividePrice(String dividePrice) {
            this.dividePrice = dividePrice;
        }

        public List<CourseListBean> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseListBean> courseList) {
            this.courseList = courseList;
        }

        public static class CourseListBean {
            private int courseId;
            private String courseName;
            private String putawayStatus;
            private String putawayStatusName;
            private int platformId;
            private int totalHours;
            private String updateTime;
            private String imagePath;
            private int is_trailers;
            private String salePrice;
            private String originalPrice;

            private int courseId1;
            private String courseName1;
            private String putawayStatus1;
            private String putawayStatusName1;
            private int platformId1;
            private int totalHours1;
            private String updateTime1;
            private String imagePath1;
            private int is_trailers1;
            private String salePrice1;
            private String originalPrice1;

            public void setOriginalPrice(String originalPrice) {
                this.originalPrice = originalPrice;
            }

            public void setSalePrice(String salePrice) {
                this.salePrice = salePrice;
            }

            public String getOriginalPrice() {
                return originalPrice;
            }

            public String getSalePrice() {
                return salePrice;
            }

            public int getIs_trailers() {
                return is_trailers;
            }

            public void setIs_trailers(int is_trailers) {
                this.is_trailers = is_trailers;
            }

            public int getCourseId() {
                return courseId;
            }

            public int getPlatformId() {
                return platformId;
            }

            public int getTotalHours() {
                return totalHours;
            }

            public String getCourseName() {
                return courseName;
            }



            public String getImagePath() {
                return imagePath;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }



            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public void setPlatformId(int platformId) {
                this.platformId = platformId;
            }

            public String getPutawayStatus() {
                return putawayStatus;
            }

            public String getPutawayStatusName() {
                return putawayStatusName;
            }

            public void setPutawayStatus(String putawayStatus) {
                this.putawayStatus = putawayStatus;
            }

            public void setPutawayStatusName(String putawayStatusName) {
                this.putawayStatusName = putawayStatusName;
            }

            public void setTotalHours(int totalHours) {
                this.totalHours = totalHours;
            }

            public int getCourseId1() {
                return courseId1;
            }

            public void setCourseId1(int courseId1) {
                this.courseId1 = courseId1;
            }

            public String getCourseName1() {
                return courseName1;
            }

            public void setCourseName1(String courseName1) {
                this.courseName1 = courseName1;
            }

            public String getPutawayStatus1() {
                return putawayStatus1;
            }

            public void setPutawayStatus1(String putawayStatus1) {
                this.putawayStatus1 = putawayStatus1;
            }

            public String getPutawayStatusName1() {
                return putawayStatusName1;
            }

            public void setPutawayStatusName1(String putawayStatusName1) {
                this.putawayStatusName1 = putawayStatusName1;
            }

            public int getPlatformId1() {
                return platformId1;
            }

            public void setPlatformId1(int platformId1) {
                this.platformId1 = platformId1;
            }

            public int getTotalHours1() {
                return totalHours1;
            }

            public void setTotalHours1(int totalHours1) {
                this.totalHours1 = totalHours1;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateTime1() {
                return updateTime1;
            }

            public void setUpdateTime1(String updateTime1) {
                this.updateTime1 = updateTime1;
            }

            public String getImagePath1() {
                return imagePath1;
            }

            public void setImagePath1(String imagePath1) {
                this.imagePath1 = imagePath1;
            }

            public int getIs_trailers1() {
                return is_trailers1;
            }

            public void setIs_trailers1(int is_trailers1) {
                this.is_trailers1 = is_trailers1;
            }

            public String getSalePrice1() {
                return salePrice1;
            }

            public void setSalePrice1(String salePrice1) {
                this.salePrice1 = salePrice1;
            }

            public String getOriginalPrice1() {
                return originalPrice1;
            }

            public void setOriginalPrice1(String originalPrice1) {
                this.originalPrice1 = originalPrice1;
            }
        }
    }
}
