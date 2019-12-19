package com.example.administrator.zahbzayxy.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${ZWJ} on 2017/4/17 0017.
 */
public class WeChatPayBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"package":"Sign=WXPay","appid":"wx0dff7a26ddc49530","sign":"3440D84A912C25B6FDBD758AA6F1541E","partnerid":"1447839102","prepayid":"wx20170417114237ff7ae99dd20212615552","noncestr":"vw8uaeavvtsfqan2hzrnuymwrgh7vcx8","timestamp":"1492400557"}
     */

    private String code;
    private Object errMsg;
    /**
     * package : Sign=WXPay
     * appid : wx0dff7a26ddc49530
     * sign : 3440D84A912C25B6FDBD758AA6F1541E
     * partnerid : 1447839102
     * prepayid : wx20170417114237ff7ae99dd20212615552
     * noncestr : vw8uaeavvtsfqan2hzrnuymwrgh7vcx8
     * timestamp : 1492400557
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
        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private String timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
