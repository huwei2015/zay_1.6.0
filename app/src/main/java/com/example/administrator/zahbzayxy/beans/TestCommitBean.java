package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/12/15 0015.
 */
public class TestCommitBean {

    /**
     * data : {"defeatNum":0,"examScoreId":176,"ranking":16}
     * code : 00000
     */
    private DataEntity data;
    private String code;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public class DataEntity {
        /**
         * defeatNum : 0.0
         * examScoreId : 176.0
         * ranking : 16.0
         */
        private double defeatNum;
        private double examScoreId;
        private double ranking;

        public void setDefeatNum(double defeatNum) {
            this.defeatNum = defeatNum;
        }

        public void setExamScoreId(double examScoreId) {
            this.examScoreId = examScoreId;
        }

        public void setRanking(double ranking) {
            this.ranking = ranking;
        }

        public double getDefeatNum() {
            return defeatNum;
        }

        public double getExamScoreId() {
            return examScoreId;
        }

        public double getRanking() {
            return ranking;
        }
    }
}
