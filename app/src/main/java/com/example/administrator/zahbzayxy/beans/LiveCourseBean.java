package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class LiveCourseBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"dividePrice":"课时收费标准：1-16学时 12元/学时;16学时以上 10元/学时","courseList":[{"beginDate":1491557859000,"courseName":"金属非金属矿山安全生产管理人员培训课程（地下矿-初训）","userCourseId":19,"totalHours":54,"endDate":1492249059000,"logo":"/file_upload/course/logo/2016/9/ee9a5251-39de-43c7-9773-19cec6f8b148.jpg","coruseId":186}]}
     */

    private String code;
    private Object errMsg;

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int webinar_id;
        private String subject;
        private String topics;
        private int verify;
        private String desc;
        private int status;
        private int pv;
        private String user_id;
        private String thumb;
        private String start_time;
        private int isNew;
        private String start_time_ymd;

        private int webinar_id1;
        private String subject1;
        private String topics1;
        private int verify1;
        private String desc1;
        private int status1;
        private int pv1;
        private String user_id1;
        private String thumb1;
        private String start_time1;
        private int isNew1;
        private String start_time_ymd1;

        public String getStart_time_ymd() {
            return start_time_ymd;
        }

        public void setStart_time_ymd(String start_time_ymd) {
            this.start_time_ymd = start_time_ymd;
        }

        public String getStart_time_ymd1() {
            return start_time_ymd1;
        }

        public void setStart_time_ymd1(String start_time_ymd1) {
            this.start_time_ymd1 = start_time_ymd1;
        }

        public int getWebinar_id() {
            return webinar_id;
        }

        public void setWebinar_id(int webinar_id) {
            this.webinar_id = webinar_id;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTopics() {
            return topics;
        }

        public void setTopics(String topics) {
            this.topics = topics;
        }

        public int getVerify() {
            return verify;
        }

        public void setVerify(int verify) {
            this.verify = verify;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public int getIsNew() {
            return isNew;
        }

        public void setIsNew(int isNew) {
            this.isNew = isNew;
        }

        public int getWebinar_id1() {
            return webinar_id1;
        }

        public void setWebinar_id1(int webinar_id1) {
            this.webinar_id1 = webinar_id1;
        }

        public String getSubject1() {
            return subject1;
        }

        public void setSubject1(String subject1) {
            this.subject1 = subject1;
        }

        public String getTopics1() {
            return topics1;
        }

        public void setTopics1(String topics1) {
            this.topics1 = topics1;
        }

        public int getVerify1() {
            return verify1;
        }

        public void setVerify1(int verify1) {
            this.verify1 = verify1;
        }

        public String getDesc1() {
            return desc1;
        }

        public void setDesc1(String desc1) {
            this.desc1 = desc1;
        }

        public int getStatus1() {
            return status1;
        }

        public void setStatus1(int status1) {
            this.status1 = status1;
        }

        public int getPv1() {
            return pv1;
        }

        public void setPv1(int pv1) {
            this.pv1 = pv1;
        }

        public String getUser_id1() {
            return user_id1;
        }

        public void setUser_id1(String user_id1) {
            this.user_id1 = user_id1;
        }

        public String getThumb1() {
            return thumb1;
        }

        public void setThumb1(String thumb1) {
            this.thumb1 = thumb1;
        }

        public String getStart_time1() {
            return start_time1;
        }

        public void setStart_time1(String start_time1) {
            this.start_time1 = start_time1;
        }

        public int getIsNew1() {
            return isNew1;
        }

        public void setIsNew1(int isNew1) {
            this.isNew1 = isNew1;
        }
    }
}
