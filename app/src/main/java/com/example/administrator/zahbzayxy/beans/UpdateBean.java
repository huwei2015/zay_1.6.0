package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;

/**
 * Created by huwei.
 * Data 2020-01-09.
 * Time 10:22.
 * 上传
 */
public class UpdateBean {
    private String code;
    private String errMsg;
    private boolean data;

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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
