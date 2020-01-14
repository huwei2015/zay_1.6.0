package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei.
 * Data 2020-01-02.
 * Time 15:26.
 * 学习未通过
 */
public class NotThroughBean {
    private String code;
    private String errMsg;
    private ThrougListBean data;

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

    public ThrougListBean getData() {
        return data;
    }

    public void setData(ThrougListBean data) {
        this.data = data;
    }

    public static class ThrougListBean implements Serializable{
        private int currentPage;
        private int pageSize;
        private int totalPage;
        private int totalRecord;
        private boolean isFirstPage;
        private boolean isLastPage;
        private List<THrougListData> data;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalRecord() {
            return totalRecord;
        }

        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }

        public boolean isFirstPage() {
            return isFirstPage;
        }

        public void setFirstPage(boolean firstPage) {
            isFirstPage = firstPage;
        }

        public boolean isLastPage() {
            return isLastPage;
        }

        public void setLastPage(boolean lastPage) {
            isLastPage = lastPage;
        }

        public List<THrougListData> getData() {
            return data;
        }

        public void setData(List<THrougListData> data) {
            this.data = data;
        }
    }

    public static class THrougListData implements Serializable{
        private int viewScoreLine;
        private String quesLibName;
        private int quesLibPackageId;
        private String packageName;
        private int passScore;
        private int quesLibId;

        public int getViewScoreLine() {
            return viewScoreLine;
        }

        public void setViewScoreLine(int viewScoreLine) {
            this.viewScoreLine = viewScoreLine;
        }

        public String getQuesLibName() {
            return quesLibName;
        }

        public void setQuesLibName(String quesLibName) {
            this.quesLibName = quesLibName;
        }

        public int getQuesLibPackageId() {
            return quesLibPackageId;
        }

        public void setQuesLibPackageId(int quesLibPackageId) {
            this.quesLibPackageId = quesLibPackageId;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getPassScore() {
            return passScore;
        }

        public void setPassScore(int passScore) {
            this.passScore = passScore;
        }

        public int getQuesLibId() {
            return quesLibId;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }
    }
}
