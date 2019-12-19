package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/9/8 0008.
 */
public class HomeLessonClassBean {


    /**
     * code : 00000
     * data : [{"courses":[{"courseName":"注册消防工程师考试技巧","courseHours":1,"teacher":"无,","courseImagePath":"http://120.55.73.36/group2/M00/00/02/eDdWmlkB6-qACMpzAABF71spbmw728.jpg","id":989},{"courseName":"注册消防工程师考试分析","courseHours":1,"teacher":"孙志明,","courseImagePath":"http://120.55.73.36/group2/M00/00/04/eDdWmlk07QeAGJblAAEWGX96_58435.jpg","id":1038},{"courseName":"注册消防工程师考试\u201c39998\u201d学习法","courseHours":1,"teacher":"孙志明,","courseImagePath":"http://120.55.73.36/group2/M00/00/04/eDdWmlk09DuAa9D7AAEWGX96_58985.jpg","id":1047},{"courseName":"注册消防工程师考试应试技巧","courseHours":1,"teacher":"孙志明,","courseImagePath":"http://120.55.73.36/group2/M00/00/04/eDdWmlk09Y-AWbV4AAEWGX96_58425.jpg","id":1049}],"cateId":404,"cateName":"消防工程师"},{"courses":[{"courseName":"导学课","courseHours":1,"teacher":"赵秋生,","courseImagePath":"http://120.55.73.36/group2/M00/00/05/eDdWmlle98mAc-y8AAB7Phd740M053.jpg","id":1243},{"courseName":"导学课","courseHours":1,"teacher":"张景钢,","courseImagePath":"http://120.55.73.36/group2/M00/00/06/eDdWmlle-cmAILSzAACGGDj7jIo643.jpg","id":1244},{"courseName":"导学课","courseHours":1,"teacher":"张景钢,","courseImagePath":"http://120.55.73.36/group2/M00/00/06/eDdWmlle-7qAFEm5AACGRM0ujjg969.jpg","id":1245},{"courseName":"导学课","courseHours":1,"teacher":"田冬梅,","courseImagePath":"http://120.55.73.36/group2/M00/00/06/eDdWmlle_E-ARs1IAACHNfdE1Qw097.jpg","id":1246}],"cateId":406,"cateName":"安全工程师"},{"courses":[{"courseName":"危险化学品经营单位主要负责人培训课程（初训）","courseHours":48,"teacher":"牛宝云,张俊杰,刘杰,","courseImagePath":"http://120.55.73.36/group2/M00/00/00/eDdWmlj4dV6AG0xMAADI2lOwXZU889.jpg","id":166},{"courseName":"危险化学品经营单位安全管理人员培训课程（初训）","courseHours":48,"teacher":"牛宝云,张俊杰,刘杰,","courseImagePath":"http://120.55.73.36/group2/M00/00/00/eDdWmlj4dV6AKRSxAADkjBAfMic504.jpg","id":167},{"courseName":"危险化学品生产单位安全管理人员培训课程（初训）","courseHours":56,"teacher":"牛宝云,张俊杰,刘杰,","courseImagePath":"http://120.55.73.36/group2/M00/00/00/eDdWmlj4dV6AdVMOAAEDw3fgsTg940.jpg","id":168},{"courseName":"危险化学品生产单位主要负责人培训课程（初训）","courseHours":56,"teacher":"牛宝云,张俊杰,刘杰,","courseImagePath":"http://120.55.73.36/group2/M00/00/00/eDdWmlj4dV6AHqiIAAC7Icjkhfg597.jpg","id":169}],"cateId":407,"cateName":"三岗人员"}]
     * errMsg : null
     */
    private String code;
    private List<DataEntity> data;
    private String errMsg;

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public class DataEntity {
        /**
         * courses : [{"courseName":"注册消防工程师考试技巧","courseHours":1,"teacher":"无,","courseImagePath":"http://120.55.73.36/group2/M00/00/02/eDdWmlkB6-qACMpzAABF71spbmw728.jpg","id":989},{"courseName":"注册消防工程师考试分析","courseHours":1,"teacher":"孙志明,","courseImagePath":"http://120.55.73.36/group2/M00/00/04/eDdWmlk07QeAGJblAAEWGX96_58435.jpg","id":1038},{"courseName":"注册消防工程师考试\u201c39998\u201d学习法","courseHours":1,"teacher":"孙志明,","courseImagePath":"http://120.55.73.36/group2/M00/00/04/eDdWmlk09DuAa9D7AAEWGX96_58985.jpg","id":1047},{"courseName":"注册消防工程师考试应试技巧","courseHours":1,"teacher":"孙志明,","courseImagePath":"http://120.55.73.36/group2/M00/00/04/eDdWmlk09Y-AWbV4AAEWGX96_58425.jpg","id":1049}]
         * cateId : 404
         * cateName : 消防工程师
         */
        private List<CoursesEntity> courses;
        private int cateId;
        private String cateName;

        public void setCourses(List<CoursesEntity> courses) {
            this.courses = courses;
        }

        public void setCateId(int cateId) {
            this.cateId = cateId;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public List<CoursesEntity> getCourses() {
            return courses;
        }

        public int getCateId() {
            return cateId;
        }

        public String getCateName() {
            return cateName;
        }

        public class CoursesEntity {
            /**
             * courseName : 注册消防工程师考试技巧
             * courseHours : 1
             * teacher : 无,
             * courseImagePath : http://120.55.73.36/group2/M00/00/02/eDdWmlkB6-qACMpzAABF71spbmw728.jpg
             * id : 989
             */
            private String courseName;
            private int courseHours;
            private String teacher;
            private String courseImagePath;
            private int id;

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public void setCourseHours(int courseHours) {
                this.courseHours = courseHours;
            }

            public void setTeacher(String teacher) {
                this.teacher = teacher;
            }

            public void setCourseImagePath(String courseImagePath) {
                this.courseImagePath = courseImagePath;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCourseName() {
                return courseName;
            }

            public int getCourseHours() {
                return courseHours;
            }

            public String getTeacher() {
                return teacher;
            }

            public String getCourseImagePath() {
                return courseImagePath;
            }

            public int getId() {
                return id;
            }
        }
    }
}
