package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class PMyLessonBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"dividePrice":"课时收费标准：1-16学时 12元/学时;16学时以上 10元/学时","courseList":[{"beginDate":1491557859000,"courseName":"金属非金属矿山安全生产管理人员培训课程（地下矿-初训）","userCourseId":19,"totalHours":54,"endDate":1492249059000,"logo":"/file_upload/course/logo/2016/9/ee9a5251-39de-43c7-9773-19cec6f8b148.jpg","coruseId":186}]}
     */

    private String code;
    private Object errMsg;
    /**
     * dividePrice : 课时收费标准：1-16学时 12元/学时;16学时以上 10元/学时
     * courseList : [{"beginDate":1491557859000,"courseName":"金属非金属矿山安全生产管理人员培训课程（地下矿-初训）","userCourseId":19,"totalHours":54,"endDate":1492249059000,"logo":"/file_upload/course/logo/2016/9/ee9a5251-39de-43c7-9773-19cec6f8b148.jpg","coruseId":186}]
     */

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
        /**
         * beginDate : 1491557859000
         * courseName : 金属非金属矿山安全生产管理人员培训课程（地下矿-初训）
         * userCourseId : 19
         * totalHours : 54
         * endDate : 1492249059000
         * logo : /file_upload/course/logo/2016/9/ee9a5251-39de-43c7-9773-19cec6f8b148.jpg
         * coruseId : 186
         */

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
            private String beginDate;
            private String courseName;
            private int userCourseId;
            private int totalHours;
            private String endDate;
            private String logo;
            private int coruseId;

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

            public int getUserCourseId() {
                return userCourseId;
            }

            public void setUserCourseId(int userCourseId) {
                this.userCourseId = userCourseId;
            }

            public int getTotalHours() {
                return totalHours;
            }

            public void setTotalHours(int totalHours) {
                this.totalHours = totalHours;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getCoruseId() {
                return coruseId;
            }

            public void setCoruseId(int coruseId) {
                this.coruseId = coruseId;
            }
        }
    }
}
