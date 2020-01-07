package com.example.administrator.zahbzayxy.beans;

import java.util.Collections;
import java.util.List;

/**
 * 课程分类
 */
public class CourseCatesBean {

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

        private List<Cates> cates;

        public List<Cates> getCates() {
            return cates;
        }

        public void setCates(List<Cates> courseList) {
            this.cates = courseList;
        }

        public static class Cates {
            private int id;
            private String cateName;
            private String parentId;
            private String catePath;
            private List<Cates> childs = Collections.emptyList();
            private int platformId;
            private int resourceType;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCateName() {
                return cateName;
            }

            public void setCateName(String cateName) {
                this.cateName = cateName;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getCatePath() {
                return catePath;
            }

            public void setCatePath(String catePath) {
                this.catePath = catePath;
            }

            public List<Cates> getChilds() {
                return childs;
            }

            public void setChilds(List<Cates> childs) {
                this.childs = childs;
            }

            public int getPlatformId() {
                return platformId;
            }

            public void setPlatformId(int platformId) {
                this.platformId = platformId;
            }

            public int getResourceType() {
                return resourceType;
            }

            public void setResourceType(int resourceType) {
                this.resourceType = resourceType;
            }
        }
    }
}
