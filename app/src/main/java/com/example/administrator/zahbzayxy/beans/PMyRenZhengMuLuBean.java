package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/13 0013.
 */
public class PMyRenZhengMuLuBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"cerList":[{"courseName":"烟花爆竹经营单位主要负责人培训课程（全学时-初训）","userCourseId":22,"cerStatus":0,"totalHours":48,"learnHours":48,"passHours":48}]}
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

    @Override
    public String toString() {
        return "PMyRenZhengMuLuBean{" +
                "code='" + code + '\'' +
                ", errMsg=" + errMsg +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable {
        /**
         * courseName : 烟花爆竹经营单位主要负责人培训课程（全学时-初训）
         * userCourseId : 22
         * cerStatus : 0
         * totalHours : 48
         * learnHours : 48
         * passHours : 48
         */

        private List<CerListBean> cerList;

        public List<CerListBean> getCerList() {
            return cerList;
        }

        public void setCerList(List<CerListBean> cerList) {
            this.cerList = cerList;
        }

        public static class CerListBean {
            private String courseName;
            private int userCourseId;
            private int cerstatus;
            private int trainType;
            private int totalHours;
            private String learnHours;
            private int passHours;
            private String certName;
            private int userCardId;
            private String cerPassDate;
            private String cerModel;
            private int certId;

            public int getTrainType() {
                return trainType;
            }

            public void setTrainType(int trainType) {
                this.trainType = trainType;
            }

            public String getCerModel() {
                return cerModel;
            }

            public void setCerModel(String cerModel) {
                this.cerModel = cerModel;
            }

            public int getCertId() {
                return certId;
            }

            public void setCertId(int certId) {
                this.certId = certId;
            }

            public int getCerstatus() {
                return cerstatus;
            }

            public void setCerstatus(int cerstatus) {
                this.cerstatus = cerstatus;
            }

            public String getCertName() {
                return certName;
            }

            public void setCertName(String certName) {
                this.certName = certName;
            }

            public int getUserCardId() {
                return userCardId;
            }

            public void setUserCardId(int userCardId) {
                this.userCardId = userCardId;
            }

            public String getCerPassDate() {
                return cerPassDate;
            }

            public void setCerPassDate(String cerPassDate) {
                this.cerPassDate = cerPassDate;
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

            public int getCerStatus() {
                return cerstatus;
            }

            public void setCerStatus(int cerStatus) {
                this.cerstatus = cerStatus;
            }

            public int getTotalHours() {
                return totalHours;
            }

            public void setTotalHours(int totalHours) {
                this.totalHours = totalHours;
            }

            public String getLearnHours() {
                return learnHours;
            }

            public void setLearnHours(String learnHours) {
                this.learnHours = learnHours;
            }

            public int getPassHours() {
                return passHours;
            }

            public void setPassHours(int passHours) {
                this.passHours = passHours;
            }

            @Override
            public String toString() {
                return "CerListBean{" +
                        "courseName='" + courseName + '\'' +
                        ", userCourseId=" + userCourseId +
                        ", cerstatus=" + cerstatus +
                        ", totalHours=" + totalHours +
                        ", learnHours='" + learnHours + '\'' +
                        ", passHours=" + passHours +
                        '}';
            }
        }
    }
}
