package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/17 0017.
 */
public class TestDetailBean {


    /**
     * errMsg : null
     * data : {"id":42,"packageImage":"192.168.2.233:80/static/image/packageDesc.png","packages":[{"price":"15","packageName":"套餐A","packageId":2,"packageDesc":"套餐A: 试题+答案"},{"price":"30","packageName":"套餐B","packageId":3,"packageDesc":"套餐B: 试题+答案+解析"},{"price":"5","packageName":"测试","packageId":6,"packageDesc":"奥术大师多"}],"quesLibName":"低压电工作业","quesLibImageUrl":"http://192.168.2.234//group2/M00/00/07/eDdWmlmIABmAChtDAAMe2um7Aps164.jpg","updateTime":"2017-12-25 17:45:10","quesLibDesc":"<p class=\"MsoNormal\"><span>&nbsp; &nbsp; &nbsp; 本模考题库是<\/span><span>低压电工作业人员<\/span><span><font face=\"微软雅黑\">初次考取<\/font>\u201c特种作业操作证\u201d参加安全生产知识上机考试的模考题库，严格按照有关安全生产培训大纲、考核标准和技术规范性文件等规定的考核要求，覆盖<\/span><span>低压电工作业人员<\/span><span>考试科目的国家级题库和省级题库的全部考核要点与重点内容，包括<\/span><span>判断题、单选题和多选题等<\/span><span>题型，紧贴考试大纲，组卷规则、题型设置、难易程度等与正式上机考试高度匹配。<\/span><span><o:p><\/o:p><\/span><\/p><p class=\"MsoNormal\"><span><br><\/span><\/p><p class=\"MsoNormal\"><span>&nbsp; &nbsp; &nbsp; 根据参加考试人员的实际使用需求，本模考题库提供了模拟练习、模拟考试、错题再练、试题解析等多种实用功能，同时支持电脑、智能手机和平板电脑等多种终端，方便随时随地使用，确保参加考试人员通过充分地练习，获得考前复习和模拟考试的最佳效果，顺利通过正式上机考试。<\/span><span><o:p><\/o:p><\/span><\/p>"}
     * code : 00000
     */
    private String errMsg;
    private DataEntity data;
    private String code;

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public DataEntity getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public class DataEntity {
        /**
         * id : 42
         * packageImage : 192.168.2.233:80/static/image/packageDesc.png
         * packages : [{"price":"15","packageName":"套餐A","packageId":2,"packageDesc":"套餐A: 试题+答案"},{"price":"30","packageName":"套餐B","packageId":3,"packageDesc":"套餐B: 试题+答案+解析"},{"price":"5","packageName":"测试","packageId":6,"packageDesc":"奥术大师多"}]
         * quesLibName : 低压电工作业
         * quesLibImageUrl : http://192.168.2.234//group2/M00/00/07/eDdWmlmIABmAChtDAAMe2um7Aps164.jpg
         * updateTime : 2017-12-25 17:45:10
         * quesLibDesc : <p class="MsoNormal"><span>&nbsp; &nbsp; &nbsp; 本模考题库是</span><span>低压电工作业人员</span><span><font face="微软雅黑">初次考取</font>“特种作业操作证”参加安全生产知识上机考试的模考题库，严格按照有关安全生产培训大纲、考核标准和技术规范性文件等规定的考核要求，覆盖</span><span>低压电工作业人员</span><span>考试科目的国家级题库和省级题库的全部考核要点与重点内容，包括</span><span>判断题、单选题和多选题等</span><span>题型，紧贴考试大纲，组卷规则、题型设置、难易程度等与正式上机考试高度匹配。</span><span><o:p></o:p></span></p><p class="MsoNormal"><span><br></span></p><p class="MsoNormal"><span>&nbsp; &nbsp; &nbsp; 根据参加考试人员的实际使用需求，本模考题库提供了模拟练习、模拟考试、错题再练、试题解析等多种实用功能，同时支持电脑、智能手机和平板电脑等多种终端，方便随时随地使用，确保参加考试人员通过充分地练习，获得考前复习和模拟考试的最佳效果，顺利通过正式上机考试。</span><span><o:p></o:p></span></p>
         */
        private int id;
        private String packageImage;
        private List<PackagesEntity> packages;
        private String quesLibName;
        private String quesLibImageUrl;
        private String updateTime;
        private String quesLibDesc;

        public void setId(int id) {
            this.id = id;
        }

        public void setPackageImage(String packageImage) {
            this.packageImage = packageImage;
        }

        public void setPackages(List<PackagesEntity> packages) {
            this.packages = packages;
        }

        public void setQuesLibName(String quesLibName) {
            this.quesLibName = quesLibName;
        }

        public void setQuesLibImageUrl(String quesLibImageUrl) {
            this.quesLibImageUrl = quesLibImageUrl;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setQuesLibDesc(String quesLibDesc) {
            this.quesLibDesc = quesLibDesc;
        }

        public int getId() {
            return id;
        }

        public String getPackageImage() {
            return packageImage;
        }

        public List<PackagesEntity> getPackages() {
            return packages;
        }

        public String getQuesLibName() {
            return quesLibName;
        }

        public String getQuesLibImageUrl() {
            return quesLibImageUrl;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getQuesLibDesc() {
            return quesLibDesc;
        }

        public class PackagesEntity {
            /**
             * price : 15
             * packageName : 套餐A
             * packageId : 2
             * packageDesc : 套餐A: 试题+答案
             */
            private String price;
            private String packageName;
            private int packageId;
            private String packageDesc;

            public void setPrice(String price) {
                this.price = price;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public void setPackageId(int packageId) {
                this.packageId = packageId;
            }

            public void setPackageDesc(String packageDesc) {
                this.packageDesc = packageDesc;
            }

            public String getPrice() {
                return price;
            }

            public String getPackageName() {
                return packageName;
            }

            public int getPackageId() {
                return packageId;
            }

            public String getPackageDesc() {
                return packageDesc;
            }
        }
    }
}
