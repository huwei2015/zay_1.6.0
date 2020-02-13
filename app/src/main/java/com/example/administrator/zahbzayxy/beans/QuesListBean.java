package com.example.administrator.zahbzayxy.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class QuesListBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private long mId;
    private int biaoJi;
    private int quesLibId;

    public int getBiaoJi() {
        return biaoJi;
    }

    public void setBiaoJi(int biaoJi) {
        this.biaoJi = biaoJi;
    }

    private int diffType;
    private int id;
    private String parsing;
    private String content;
    private int quesType;
    /**
     * isRightAnswer : 1
     * optIndex : 1
     * id : 74791
     * quesId : 35794
     * content : 交通伤害
     */

    private String opts;
    private String children;

    @Generated(hash = 791778488)
    public QuesListBean(long mId, int biaoJi, int quesLibId, int diffType, int id,
            String parsing, String content, int quesType, String opts,
            String children) {
        this.mId = mId;
        this.biaoJi = biaoJi;
        this.quesLibId = quesLibId;
        this.diffType = diffType;
        this.id = id;
        this.parsing = parsing;
        this.content = content;
        this.quesType = quesType;
        this.opts = opts;
        this.children = children;
    }

    @Generated(hash = 1477078327)
    public QuesListBean() {
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

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getOpts() {
        return opts;
    }

    public void setOpts(String opts) {
        this.opts = opts;
    }

    public long getMId() {
        return this.mId;
    }

    public void setMId(long mId) {
        this.mId = mId;
    }

    public int getQuesLibId() {
        return this.quesLibId;
    }

    public void setQuesLibId(int quesLibId) {
        this.quesLibId = quesLibId;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }
}