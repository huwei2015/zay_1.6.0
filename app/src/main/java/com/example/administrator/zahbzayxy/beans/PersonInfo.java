package com.example.administrator.zahbzayxy.beans;

/**
 * Created by huwei.
 * Data 2019/8/13.
 * Time 17:34.
 * Description.是否需要完善个人信息
 */
public class PersonInfo {
    private String code;
    private String errMsg;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
