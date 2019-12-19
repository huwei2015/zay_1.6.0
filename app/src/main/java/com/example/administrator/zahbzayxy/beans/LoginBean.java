package com.example.administrator.zahbzayxy.beans;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
    //{"code":"00000","errMsg":null,"data":{"token":"kfkCCHn6kpaaNm8M7GDkC15tNGpp4osq9JNx/xRVsYt/AHkVOhY8d+yj/v6q2AsK"}}
    //18848980064


//{"code":"00000","errMsg":null,"data":{"token":"cYZ2JRd3Suo2PV9TENiz9w1SeAcD9R3IhlPwJvDhiXR/AHkVOhY8d+yj/v6q2AsK"}}
//17743563592


}
