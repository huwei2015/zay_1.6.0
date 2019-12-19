package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by ${ZWJ} on 2017/3/10 0010.
 */
public class UserInfoBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"photoUrl":null,"unit":null,"gender":1,"phone":"17743563592","nickName":"17743563592","idCard":null,"station":null,"userName":null}
     */

    private String code;
    private Object errMsg;
    /**
     * photoUrl : null
     * unit : null
     * gender : 1
     * phone : 17743563592
     * nickName : 17743563592
     * idCard : null
     * station : null
     * userName : null
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

    public static class DataBean implements Serializable {
//                 "oneInchPhoto":"",
//                 "gender":1,
//                 "unitName":"",
//                 "idCard":"111111111111111111",
//                 "quarters":"",
//                 "industry":"",
//                 "userName":"13693238926",
//                 "type":0,
//                 "photoUrl":"http://120.55.73.36//group2/M00/00/3F/eDdJJF1JNkeAZsT0AAA1FkJdO4A282.jpg",
//                 "phone":"13693238926",
//                 "needVerify":1,
//                 "facePath":"http://120.55.73.36//group2/M00/00/3F/eDdJJF1JGWyAGSblAAE_jlpigZQ561.jpg",
//                 "intervalTime":"30"


        private String oneInchPhoto; //一寸照片
        private String unitName;//单位
        private int gender;
        private Object idCard;
        private String quarters;
        private String industry;
        private Object userName;
        private int type;
        private Object photoUrl;
        private Object unit;
        private String phone;
        private int needVerify;
        private String facePath;
        private String intervalTime;
        private int educationalLevel;//文化程度
        private String nickName;
        private Object station;
        private String occupaName;//职业名称
        private String workTypeName;//工种名称
        private String occupaSkillLevel;//职业技能等级

        public String getOccupaName() {
            return occupaName;
        }

        public void setOccupaName(String occupaName) {
            this.occupaName = occupaName;
        }

        public String getWorkTypeName() {
            return workTypeName;
        }

        public void setWorkTypeName(String workTypeName) {
            this.workTypeName = workTypeName;
        }

        public String getOccupaSkillLevel() {
            return occupaSkillLevel;
        }

        public void setOccupaSkillLevel(String occupaSkillLevel) {
            this.occupaSkillLevel = occupaSkillLevel;
        }
        public int getEducationalLevel() {
            return educationalLevel;
        }

        public void setEducationalLevel(int educationalLevel) {
            this.educationalLevel = educationalLevel;
        }

        public String getOneInchPhoto() {
            return oneInchPhoto;
        }

        public void setOneInchPhoto(String oneInchPhoto) {
            this.oneInchPhoto = oneInchPhoto;
        }

        public String getIntervalTime() {
            return intervalTime;
        }

        public void setIntervalTime(String intervalTime) {
            this.intervalTime = intervalTime;
        }

        public int getNeedVerify() {
            return needVerify;
        }

        public void setNeedVerify(int needVerify) {
            this.needVerify = needVerify;
        }

        public String getFacePath() {
            return facePath;
        }

        public void setFacePath(String facePath) {
            this.facePath = facePath;
        }

        public String getQuarters() {
            return quarters;
        }

        public void setQuarters(String quarters) {
            this.quarters = quarters;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(Object photoUrl) {
            this.photoUrl = photoUrl;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getIdCard() {
            return idCard;
        }

        public void setIdCard(Object idCard) {
            this.idCard = idCard;
        }

        public Object getStation() {
            return station;
        }

        public void setStation(Object station) {
            this.station = station;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }
    }
}
