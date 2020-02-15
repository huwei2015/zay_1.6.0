package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/15 0015.
 */
public class AllHaveDoTestBean {


    /**
     * errMsg : null
     * data : {"quesLibId":92,"examScoreId":172,"quesDetails":[{"content":"生产经营单位安全生产的分级管理,是把生产经营单位从上到下分为决策层、管理层和()。","opts":[{"content":"基础层","questionId":51033,"optIndex":1,"isRightAnswer":0},{"content":"经营层","questionId":51033,"optIndex":2,"isRightAnswer":0},{"content":"执行层","questionId":51033,"optIndex":3,"isRightAnswer":1}],"diffType":1,"questionId":51033,"answerResult":{"isRight":0,"userAnswerIds":"112981"},"parsing":"","quesType":1},{"content":"根据《尾矿库安全监督管理规定》,直接从事尾矿库放矿、筑坝、巡坝、排洪和排渗设施操作的作业人员必须取得(),方可上岗作业。","opts":[{"content":"中级以上专业技术资格证书","questionId":51057,"optIndex":1,"isRightAnswer":0},{"content":"安全资格证书","questionId":51057,"optIndex":2,"isRightAnswer":0},{"content":"特种作业操作证书","questionId":51057,"optIndex":3,"isRightAnswer":1}],"diffType":1,"questionId":51057,"answerResult":{"isRight":1,"userAnswerIds":"113054"},"parsing":"","quesType":1}]}
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

    public static class DataEntity {
        /**
         * quesLibId : 92
         * examScoreId : 172
         * quesDetails : [{"content":"生产经营单位安全生产的分级管理,是把生产经营单位从上到下分为决策层、管理层和()。","opts":[{"content":"基础层","questionId":51033,"optIndex":1,"isRightAnswer":0},{"content":"经营层","questionId":51033,"optIndex":2,"isRightAnswer":0},{"content":"执行层","questionId":51033,"optIndex":3,"isRightAnswer":1}],"diffType":1,"questionId":51033,"answerResult":{"isRight":0,"userAnswerIds":"112981"},"parsing":"","quesType":1},{"content":"根据《尾矿库安全监督管理规定》,直接从事尾矿库放矿、筑坝、巡坝、排洪和排渗设施操作的作业人员必须取得(),方可上岗作业。","opts":[{"content":"中级以上专业技术资格证书","questionId":51057,"optIndex":1,"isRightAnswer":0},{"content":"安全资格证书","questionId":51057,"optIndex":2,"isRightAnswer":0},{"content":"特种作业操作证书","questionId":51057,"optIndex":3,"isRightAnswer":1}],"diffType":1,"questionId":51057,"answerResult":{"isRight":1,"userAnswerIds":"113054"},"parsing":"","quesType":1}]
         */
        private int quesLibId;
        private int examScoreId;
        private List<QuesDetailsEntity> quesDetails;

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }

        public void setExamScoreId(int examScoreId) {
            this.examScoreId = examScoreId;
        }

        public void setQuesDetails(List<QuesDetailsEntity> quesDetails) {
            this.quesDetails = quesDetails;
        }

        public int getQuesLibId() {
            return quesLibId;
        }

        public int getExamScoreId() {
            return examScoreId;
        }

        public List<QuesDetailsEntity> getQuesDetails() {
            return quesDetails;
        }

        public static class QuesDetailsEntity {
            /**
             * content : 生产经营单位安全生产的分级管理,是把生产经营单位从上到下分为决策层、管理层和()。
             * opts : [{"content":"基础层","questionId":51033,"optIndex":1,"isRightAnswer":0},{"content":"经营层","questionId":51033,"optIndex":2,"isRightAnswer":0},{"content":"执行层","questionId":51033,"optIndex":3,"isRightAnswer":1}]
             * diffType : 1
             * questionId : 51033
             * answerResult : {"isRight":0,"userAnswerIds":"112981"}
             * parsing :
             * quesType : 1
             */
            private String content;
            private List<OptsEntity> opts;
            private int diffType;
            private int questionId;
            private AnswerResultEntity answerResult;
            private String parsing;
            private int quesType;

            private int location;

            private int isShowParsing;
            private List<QuesDetailsEntity> children;

            public List<QuesDetailsEntity> getChildren() {
                return children;
            }

            public void setChildren(List<QuesDetailsEntity> children) {
                this.children = children;
            }

            public int getIsShowParsing() {
                return isShowParsing;
            }

            public void setIsShowParsing(int isShowParsing) {
                this.isShowParsing = isShowParsing;
            }



            public int getLocation() {
                return location;
            }

            public void setLocation(int location) {
                this.location = location;
            }

            public int getTag() {
                return tag;
            }

            public void setTag(int tag) {
                this.tag = tag;
            }

            private int tag;




            public void setContent(String content) {
                this.content = content;
            }

            public void setOpts(List<OptsEntity> opts) {
                this.opts = opts;
            }

            public void setDiffType(int diffType) {
                this.diffType = diffType;
            }

            public void setQuestionId(int questionId) {
                this.questionId = questionId;
            }

            public void setAnswerResult(AnswerResultEntity answerResult) {
                this.answerResult = answerResult;
            }

            public void setParsing(String parsing) {
                this.parsing = parsing;
            }

            public void setQuesType(int quesType) {
                this.quesType = quesType;
            }

            public String getContent() {
                return content;
            }

            public List<OptsEntity> getOpts() {
                return opts;
            }

            public int getDiffType() {
                return diffType;
            }

            public int getQuestionId() {
                return questionId;
            }

            public AnswerResultEntity getAnswerResult() {
                return answerResult;
            }

            public String getParsing() {
                return parsing;
            }

            public int getQuesType() {
                return quesType;
            }

            public class OptsEntity {
                /**
                 * content : 基础层
                 * questionId : 51033
                 * optIndex : 1
                 * isRightAnswer : 0
                 */
                private String content;
                private int optId;
                private int optIndex;
                private int isRightAnswer;
                private int optsTag;
                public int getOptsTag() {
                    return optsTag;
                }
                public void setOptsTag(int optsTag) {
                    this.optsTag = optsTag;
                }
                public int getOptId() {
                    return optId;
                }
                public void setOptId(int optId) {
                    this.optId = optId;
                }
                public void setContent(String content) {
                    this.content = content;
                }
                public void setOptIndex(int optIndex) {
                    this.optIndex = optIndex;
                }

                public void setIsRightAnswer(int isRightAnswer) {
                    this.isRightAnswer = isRightAnswer;
                }

                public String getContent() {
                    return content;
                }

                public int getQuestionId() {
                    return questionId;
                }

                public int getOptIndex() {
                    return optIndex;
                }

                public int getIsRightAnswer() {
                    return isRightAnswer;
                }
            }

            public static class AnswerResultEntity {
                /**
                 * isRight : 0
                 * userAnswerIds : 112981
                 */
                private int isRight;
                private String userAnswerIds;

                public void setIsRight(int isRight) {
                    this.isRight = isRight;
                }

                public void setUserAnswerIds(String userAnswerIds) {
                    this.userAnswerIds = userAnswerIds;
                }

                public int getIsRight() {
                    return isRight;
                }

                public String getUserAnswerIds() {
                    return userAnswerIds;
                }
            }
        }
    }
}
