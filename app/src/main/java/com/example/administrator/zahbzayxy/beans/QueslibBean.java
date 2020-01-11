package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class QueslibBean {

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

        private List<QueslibListBean> queslibList;

        public String getDividePrice() {
            return dividePrice;
        }

        public void setDividePrice(String dividePrice) {
            this.dividePrice = dividePrice;
        }

        public List<QueslibListBean> getQueslibList() {
            return queslibList;
        }

        public void setQueslibList(List<QueslibListBean> queslibList) {
            this.queslibList = queslibList;
        }

        public static class QueslibListBean {
            private int id;
            private String quesLibName;
            private String imagePath;
            private String quesCount;
            private int oPrice;
            private int sPrice;
            private String updateTime;
            private int isRecommend;
            private int isNew;
            private int isFree;

            private int id1;
            private String quesLibName1;
            private String imagePath1;
            private String quesCount1;
            private int oPrice1;
            private int sPrice1;
            private String updateTime1;
            private int isRecommend1;
            private int isNew1;
            private int isFree1;

            public int getIsFree() {
                return isFree;
            }

            public void setIsFree(int isFree) {
                this.isFree = isFree;
            }

            public int getIsFree1() {
                return isFree1;
            }

            public void setIsFree1(int isFree1) {
                this.isFree1 = isFree1;
            }

            public int getIsNew() {
                return isNew;
            }

            public void setIsNew(int isNew) {
                this.isNew = isNew;
            }

            public int getIsNew1() {
                return isNew1;
            }

            public void setIsNew1(int isNew1) {
                this.isNew1 = isNew1;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getQuesLibName() {
                return quesLibName;
            }

            public void setQuesLibName(String quesLibName) {
                this.quesLibName = quesLibName;
            }

            public String getImagePath() {
                return imagePath;
            }

            public void setImagePath(String imagePath) {
                this.imagePath = imagePath;
            }

            public String getQuesCount() {
                return quesCount;
            }

            public void setQuesCount(String quesCount) {
                this.quesCount = quesCount;
            }

            public int getoPrice() {
                return oPrice;
            }

            public void setoPrice(int oPrice) {
                this.oPrice = oPrice;
            }

            public int getsPrice() {
                return sPrice;
            }

            public void setsPrice(int sPrice) {
                this.sPrice = sPrice;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(int isRecommend) {
                this.isRecommend = isRecommend;
            }

            public int getId1() {
                return id1;
            }

            public void setId1(int id1) {
                this.id1 = id1;
            }

            public String getQuesLibName1() {
                return quesLibName1;
            }

            public void setQuesLibName1(String quesLibName1) {
                this.quesLibName1 = quesLibName1;
            }

            public String getImagePath1() {
                return imagePath1;
            }

            public void setImagePath1(String imagePath1) {
                this.imagePath1 = imagePath1;
            }

            public String getQuesCount1() {
                return quesCount1;
            }

            public void setQuesCount1(String quesCount1) {
                this.quesCount1 = quesCount1;
            }

            public int getoPrice1() {
                return oPrice1;
            }

            public void setoPrice1(int oPrice1) {
                this.oPrice1 = oPrice1;
            }

            public int getsPrice1() {
                return sPrice1;
            }

            public void setsPrice1(int sPrice1) {
                this.sPrice1 = sPrice1;
            }

            public String getUpdateTime1() {
                return updateTime1;
            }

            public void setUpdateTime1(String updateTime1) {
                this.updateTime1 = updateTime1;
            }

            public int getIsRecommend1() {
                return isRecommend1;
            }

            public void setIsRecommend1(int isRecommend1) {
                this.isRecommend1 = isRecommend1;
            }
        }
    }
}
