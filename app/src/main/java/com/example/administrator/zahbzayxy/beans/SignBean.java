package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-17.
 * Time 10:50.
 * 我的报名bean
 */
public class SignBean {
    private String code;
    private List<SignListBean> applyList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SignListBean> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<SignListBean> applyList) {
        this.applyList = applyList;
    }

    public static class SignListBean implements Serializable {
        private int activityId;
        private String activityName;
        private int applyId;
        private String applyTime;

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public int getApplyId() {
            return applyId;
        }

        public void setApplyId(int applyId) {
            this.applyId = applyId;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }
    }
}
