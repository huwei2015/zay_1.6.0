package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

public class SimpleBean implements Serializable {
    private String code;
    private String errMsg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "code='" + code + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
