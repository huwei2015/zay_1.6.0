package com.example.administrator.zahbzayxy.vo;

/**
 * 人脸识别上传图片返回结果类
 */

public class UploadInfo {

    /**
     * code : 00000
     * errMsg :
     * data : {"faceUrl":""}
     */

    private String code;
    private String errMsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * faceUrl :
         */

        private String faceUrl;

        public String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            this.faceUrl = faceUrl;
        }
    }
}
