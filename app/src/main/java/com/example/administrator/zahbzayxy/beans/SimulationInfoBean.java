package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

public class SimulationInfoBean implements Serializable {

    private String code;
    private String errMsg;
    private SimulationDataBean data;

    public static class SimulationDataBean {
        private QuesLib quesLib;
        private List<StatScore> statScore;
        private int passNum;

        public void setQuesLib(QuesLib quesLib) {
            this.quesLib = quesLib;
        }
        public QuesLib getQuesLib() {
            return quesLib;
        }

        public void setStatScore(List<StatScore> statScore) {
            this.statScore = statScore;
        }
        public List<StatScore> getStatScore() {
            return statScore;
        }

        public void setPassNum(int passNum) {
            this.passNum = passNum;
        }
        public int getPassNum() {
            return passNum;
        }

        @Override
        public String toString() {
            return "SimulationDataBean{" +
                    "quesLib=" + quesLib +
                    ", statScore=" + statScore +
                    ", passNum=" + passNum +
                    '}';
        }
    }

    public static class StatScore {
        private String assignTime;
        private int totalScore;

        public void setAssignTime(String assignTime) {
            this.assignTime = assignTime;
        }
        public String getAssignTime() {
            return assignTime;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }
        public int getTotalScore() {
            return totalScore;
        }

        @Override
        public String toString() {
            return "StatScore{" +
                    "assignTime='" + assignTime + '\'' +
                    ", totalScore=" + totalScore +
                    '}';
        }
    }

    public static class QuesLib {

        private String packageName;
        private String quesLibName;
        private int passScore;
        private int viewScoreLine;
        private int quesLibId;
        private int quesLibPackageId;

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }
        public String getPackageName() {
            return packageName;
        }

        public void setQuesLibName(String quesLibName) {
            this.quesLibName = quesLibName;
        }
        public String getQuesLibName() {
            return quesLibName;
        }

        public void setPassScore(int passScore) {
            this.passScore = passScore;
        }
        public int getPassScore() {
            return passScore;
        }

        public void setViewScoreLine(int viewScoreLine) {
            this.viewScoreLine = viewScoreLine;
        }
        public int getViewScoreLine() {
            return viewScoreLine;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }
        public int getQuesLibId() {
            return quesLibId;
        }

        public void setQuesLibPackageId(int quesLibPackageId) {
            this.quesLibPackageId = quesLibPackageId;
        }
        public int getQuesLibPackageId() {
            return quesLibPackageId;
        }

        @Override
        public String toString() {
            return "QuesLib{" +
                    "packageName='" + packageName + '\'' +
                    ", quesLibName='" + quesLibName + '\'' +
                    ", passScore=" + passScore +
                    ", viewScoreLine=" + viewScoreLine +
                    ", quesLibId=" + quesLibId +
                    ", quesLibPackageId=" + quesLibPackageId +
                    '}';
        }
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setData(SimulationDataBean data) {
        this.data = data;
    }

    public SimulationDataBean getData() {
        return data;
    }


    @Override
    public String toString() {
        return "SimulationInfoBean{" +
                "code='" + code + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
