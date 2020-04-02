package com.example.administrator.zahbzayxy.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/9 0009.
 */
public class LoginBean {
    /**
     * code : 00000
     * errMsg : null
     * data : {"token":"kfkCCHn6kpaaNm8M7GDkC15tNGpp4osq9JNx/xRVsYt/AHkVOhY8d+yj/v6q2AsK"}
     */

    private String code;
    private Object errMsg;
    /**
     * token : kfkCCHn6kpaaNm8M7GDkC15tNGpp4osq9JNx/xRVsYt/AHkVOhY8d+yj/v6q2AsK
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
        private String token;
        private List<DataList> platform;

        public List<DataList> getPlatform() {
            return platform;
        }

        public void setPlatform(List<DataList> platform) {
            this.platform = platform;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class DataList implements Serializable {
        private int platformId;
        private String platformName;

        public int getPlatformId() {
            return platformId;
        }

        public void setPlatformId(int platformId) {
            this.platformId = platformId;
        }

        public String getPlatformName() {
            return platformName;
        }

        public void setPlatformName(String platformName) {
            this.platformName = platformName;
        }
    }
}
