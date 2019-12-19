package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/4/13 0013.
 */
public class PMyRenZhengDetailBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"courseHours":null,"courseName":null,"cerSelectionNames":"测试章节13/n测试章节12/n测试章节11/n测试章节10/n测试章节9/n测试章节8/n测试章节7/n测试章节6/n测试章节5/n测试章节4/n测试章节3/n测试章节2/n测试章节1/n","cerCode":null,"idCard":"412828199312202429","userName":"userName_tv"}
     */

    private String code;
    private Object errMsg;
    /**
     * courseHours : null
     * courseName : null
     * cerSelectionNames : 测试章节13/n测试章节12/n测试章节11/n测试章节10/n测试章节9/n测试章节8/n测试章节7/n测试章节6/n测试章节5/n测试章节4/n测试章节3/n测试章节2/n测试章节1/n
     * cerCode : null
     * idCard : 412828199312202429
     * userName : userName_tv
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
        private Integer courseHours;
        private String courseName;
        private String cerSelectionNames;
        private String cerCode;
        private String idCard;
        private String userName;

        public Integer getCourseHours() {
            return courseHours;
        }

        public void setCourseHours(Integer courseHours) {
            this.courseHours = courseHours;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCerSelectionNames() {
            return cerSelectionNames;
        }

        public void setCerSelectionNames(String cerSelectionNames) {
            this.cerSelectionNames = cerSelectionNames;
        }

        public String getCerCode() {
            return cerCode;
        }

        public void setCerCode(String cerCode) {
            this.cerCode = cerCode;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
