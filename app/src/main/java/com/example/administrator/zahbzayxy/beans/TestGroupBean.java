package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/16 0016.
 */
public class TestGroupBean {


    /**
     * code : 00000
     * errMsg : null
     * data : [{"id":39,"cateName":"测试题库分类1","subCates":[{"id":43,"cateName":"测试题库分类1-子分类1"},{"id":44,"cateName":"测试题库分类1-子分类2"},{"id":45,"cateName":"测试题库分类1-子分类3"}]},{"id":40,"cateName":"测试题库分类2","subCates":[{"id":46,"cateName":"测试题库分类2-子分类1"},{"id":47,"cateName":"测试题库分类2-子分类2"}]},{"id":41,"cateName":"测试题库分类3","subCates":[{"id":48,"cateName":"测试题库分类3-子分类1"},{"id":49,"cateName":"测试题库分类3-子分类2"}]},{"id":42,"cateName":"测试题库分类4","subCates":[{"id":50,"cateName":"测试题库分类4-子分类1"},{"id":51,"cateName":"测试题库分类4-子分类2"}]}]
     */

    private String code;
    private Object errMsg;
    /**
     * id : 39
     * cateName : 测试题库分类1
     * subCates : [{"id":43,"cateName":"测试题库分类1-子分类1"},{"id":44,"cateName":"测试题库分类1-子分类2"},{"id":45,"cateName":"测试题库分类1-子分类3"}]
     */

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
        private int id;
        private String cateName;
        /**
         * id : 43
         * cateName : 测试题库分类1-子分类1
         */

        private List<SubCatesBean> subCates;

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

        public List<SubCatesBean> getSubCates() {
            return subCates;
        }

        public void setSubCates(List<SubCatesBean> subCates) {
            this.subCates = subCates;
        }

        public static class SubCatesBean {
            private int id;
            private String cateName;

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
        }
    }
}
