package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

public class OptsBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

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