package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

public class NewTestContentBean implements Serializable {

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

        private int totalScore;
        private int passScore;
        private int multipleScore;
        private int shortScore;
        private boolean isFree;
        private String paperName;
        private int factScore;
        private int notFactScore;
        private int judgeScore;
        private int examTime;
        private int singleScore;
        private int quesLibId;
        private int examScoreId;
        private List<QuesDataBean> quesData;

        public int getExamScoreId() {
            return examScoreId;
        }

        public void setExamScoreId(int examScoreId) {
            this.examScoreId = examScoreId;
        }

        public String getPaperName() {
            return paperName;
        }

        public void setPaperName(String paperName) {
            this.paperName = paperName;
        }

        public int getJudgeScore() {
            return judgeScore;
        }

        public void setJudgeScore(int judgeScore) {
            this.judgeScore = judgeScore;
        }

        public int getExamTime() {
            return examTime;
        }

        public void setExamTime(int examTime) {
            this.examTime = examTime;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public int getPassScore() {
            return passScore;
        }

        public void setPassScore(int passScore) {
            this.passScore = passScore;
        }

        public int getSingleScore() {
            return singleScore;
        }

        public void setSingleScore(int singleScore) {
            this.singleScore = singleScore;
        }

        public int getMultipleScore() {
            return multipleScore;
        }

        public void setMultipleScore(int multipleScore) {
            this.multipleScore = multipleScore;
        }

        public int getQuesLibId() {
            return quesLibId;
        }

        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }

        public List<QuesDataBean> getQuesData() {
            return quesData;
        }

        public void setQuesData(List<QuesDataBean> quesData) {
            this.quesData = quesData;
        }

        public int getShortScore() {
            return shortScore;
        }

        public void setShortScore(int shortScore) {
            this.shortScore = shortScore;
        }

        public boolean isFree() {
            return isFree;
        }

        public void setFree(boolean free) {
            isFree = free;
        }

        public int getFactScore() {
            return factScore;
        }

        public void setFactScore(int factScore) {
            this.factScore = factScore;
        }

        public int getNotFactScore() {
            return notFactScore;
        }

        public void setNotFactScore(int notFactScore) {
            this.notFactScore = notFactScore;
        }

        public static class QuesDataBean {
            public int getBiaoJi() {
                return biaoJi;
            }

            public void setBiaoJi(int biaoJi) {
                this.biaoJi = biaoJi;
            }

            //做过设为1,未做过为0；
            private int biaoJi;
            private int diffType;
            private int id;
            private String parsing;
            private String content;
            private int quesType;
            private int isShowParsing;
            private double score;
            private List<QuesDataBean> children;
            /**
             * isRightAnswer : 1
             * optIndex : 1
             * id : 74791
             * quesId : 35794
             * content : 交通伤害
             */

            private List<OptsBean> opts;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public int getDiffType() {
                return diffType;
            }

            public void setDiffType(int diffType) {
                this.diffType = diffType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getParsing() {
                return parsing;
            }

            public void setParsing(String parsing) {
                this.parsing = parsing;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getQuesType() {
                return quesType;
            }

            public void setQuesType(int quesType) {
                this.quesType = quesType;
            }

            public List<OptsBean> getOpts() {
                return opts;
            }

            public void setOpts(List<OptsBean> opts) {
                this.opts = opts;
            }

            public int getIsShowParsing() {
                return isShowParsing;
            }

            public void setIsShowParsing(int isShowParsing) {
                this.isShowParsing = isShowParsing;
            }

            public List<QuesDataBean> getChildren() {
                return children;
            }

            public void setChildren(List<QuesDataBean> children) {
                this.children = children;
            }

            public static class OptsBean {
                public int getTag() {
                    return tag;
                }

                public void setTag(int tag) {
                    this.tag = tag;
                }

                //判断是否做过该题，解决复用
                private int tag;
                private int isRightAnswer;
                private int optIndex;
                private int id;
                private int quesId;
                private String content;

                public int getIsRightAnswer() {
                    return isRightAnswer;
                }

                public void setIsRightAnswer(int isRightAnswer) {
                    this.isRightAnswer = isRightAnswer;
                }

                public int getOptIndex() {
                    return optIndex;
                }

                public void setOptIndex(int optIndex) {
                    this.optIndex = optIndex;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getQuesId() {
                    return quesId;
                }

                public void setQuesId(int quesId) {
                    this.quesId = quesId;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }
        }
    }
}
