package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/3/8 0008.
 */
public class RegisterBackBean {

    /**
     * code : 00000
     * errMsg : null
     * data : 134261
     */

    private String code;
    private Object errMsg;
    private boolean data;
    private int ranking;
    private int defeatNum;
    public int getDefeatNum() {
        return defeatNum;
    }

    public void setDefeatNum(int defeatNum) {
        this.defeatNum = defeatNum;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }



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

    public boolean getData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }


}
