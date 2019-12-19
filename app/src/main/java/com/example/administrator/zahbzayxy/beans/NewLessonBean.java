package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/9 0009.
 */
public class NewLessonBean {


    /**
     * code : 00000
     * errMsg : null
     * data : {"hotList":[{"courseName":"zikecheng11","teacher":"","logo":"","courseId":274,"courseHour":8},{"courseName":"ceshi111","teacher":"","logo":"","courseId":273,"courseHour":12},{"courseName":"ceshi","teacher":"","logo":"","courseId":272,"courseHour":5},{"courseName":"huiceshi","teacher":"","logo":"","courseId":271,"courseHour":10}],"newList":[{"courseName":"zikecheng11","teacher":null,"logo":"","courseId":274,"courseHour":8},{"courseName":"ceshi111","teacher":null,"logo":"","courseId":273,"courseHour":12},{"courseName":"ceshi","teacher":null,"logo":"","courseId":272,"courseHour":5},{"courseName":"huiceshi","teacher":null,"logo":"","courseId":271,"courseHour":10}]}
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
        /**
         * courseName : zikecheng11
         * teacher :
         * logo :
         * courseId : 274
         * courseHour : 8
         */

        private List<HotListBean> hotList;
        /**
         * courseName : zikecheng11
         * teacher : null
         * logo :
         * courseId : 274
         * courseHour : 8
         */

        private List<NewListBean> newList;

        public List<HotListBean> getHotList() {
            return hotList;
        }

        public void setHotList(List<HotListBean> hotList) {
            this.hotList = hotList;
        }

        public List<NewListBean> getNewList() {
            return newList;
        }

        public void setNewList(List<NewListBean> newList) {
            this.newList = newList;
        }

        public static class HotListBean {
            private String courseName;
            private String teacher;
            private String logo;
            private int courseId;
            private int courseHour;

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public String getTeacher() {
                return teacher;
            }

            public void setTeacher(String teacher) {
                this.teacher = teacher;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public int getCourseHour() {
                return courseHour;
            }

            public void setCourseHour(int courseHour) {
                this.courseHour = courseHour;
            }
        }

        public static class NewListBean {
            private String courseName;
            private String teacher;
            private String logo;
            private int courseId;
            private int courseHour;

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public String getTeacher() {
                return teacher;
            }

            public void setTeacher(String teacher) {
                this.teacher = teacher;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public int getCourseHour() {
                return courseHour;
            }

            public void setCourseHour(int courseHour) {
                this.courseHour = courseHour;
            }
        }
    }
}
