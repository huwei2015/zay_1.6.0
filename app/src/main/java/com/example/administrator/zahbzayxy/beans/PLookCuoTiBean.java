package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/14 0014.
 */
public class PLookCuoTiBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"errorRecords":[{"questionId":35798,"errorAnswerIds":"74805"},{"questionId":35794,"errorAnswerIds":"74793"},{"questionId":35796,"errorAnswerIds":"74799"}],"quesLibId":16}
     */

    private String code;
    private Object errMsg;
    /**
     * errorRecords : [{"questionId":35798,"errorAnswerIds":"74805"},{"questionId":35794,"errorAnswerIds":"74793"},{"questionId":35796,"errorAnswerIds":"74799"}]
     * quesLibId : 16
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

    public static class DataBean {
        private int quesLibId;
        /**
         * questionId : 35798
         * errorAnswerIds : 74805
         */

        private List<ErrorRecordsBean> errorRecords;

        public int getQuesLibId() {
            return quesLibId;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }

        public List<ErrorRecordsBean> getErrorRecords() {
            return errorRecords;
        }

        public void setErrorRecords(List<ErrorRecordsBean> errorRecords) {
            this.errorRecords = errorRecords;
        }

        public static class ErrorRecordsBean {
            private int questionId;
            private String errorAnswerIds;

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            int tag;

            public int getQuestionId() {
                return questionId;
            }

            public void setQuestionId(int questionId) {
                this.questionId = questionId;
            }

            public String getErrorAnswerIds() {
                return errorAnswerIds;
            }

            public void setErrorAnswerIds(String errorAnswerIds) {
                this.errorAnswerIds = errorAnswerIds;
            }
        }
    }
}
