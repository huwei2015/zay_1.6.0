package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

public class QuesListBean2 implements Serializable {
    private int biaoJi;
    private int diffType;
    private int id;
    private String parsing;
    private String content;
    private int quesType;

    public QuesListBean2(int biaoJi, int diffType, int id, String parsing, String content, int quesType, List<OptsBean> opts, List<QuesListBean2> children) {
        this.biaoJi = biaoJi;
        this.diffType = diffType;
        this.id = id;
        this.parsing = parsing;
        this.content = content;
        this.quesType = quesType;
        this.opts = opts;
        this.children = children;
    }

    /**
     * isRightAnswer : 1
     * optIndex : 1
     * id : 74791
     * quesId : 35794
     * content : 交通伤害
     */

    private List<OptsBean> opts;
    private List<QuesListBean2> children;

    public int getBiaoJi() {
        return biaoJi;
    }

    public void setBiaoJi(int biaoJi) {
        this.biaoJi = biaoJi;
    }

    public List<OptsBean> getOpts() {
        return opts;
    }

    public void setOpts(List<OptsBean> opts) {
        this.opts = opts;
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

    public List<QuesListBean2> getChildren() {
        return children;
    }

    public void setChildren(List<QuesListBean2> children) {
        this.children = children;
    }
}