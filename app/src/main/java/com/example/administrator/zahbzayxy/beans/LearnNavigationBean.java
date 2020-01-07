package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-07.
 * Time 13:05.
 * 在线课导航
 */
public class LearnNavigationBean {
    private String code;
    private String errMsg;
    private LearnNavigationData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public LearnNavigationData getData() {
        return data;
    }

    public void setData(LearnNavigationData data) {
        this.data = data;
    }

    public static class LearnNavigationData{
        private List<LearnListBean> data;

        public List<LearnListBean> getData() {
            return data;
        }

        public void setData(List<LearnListBean> data) {
            this.data = data;
        }
    }
    public static class LearnListBean{
        private String cateId;
        private String cateName;

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public String getCateId() {
            return cateId;
        }

        public void setCateId(String cateId) {
            this.cateId = cateId;
        }
    }
}
