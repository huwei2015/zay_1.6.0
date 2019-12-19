package com.example.administrator.zahbzayxy.utils;

/**
 * Created by ${ZWJ} on 2017/3/20 0020.
 */
public class Code {
    public static enum  EnumCode {
        success_code("00000","返回成功"),
        error_code("99999","系统异常"),
        checkerror_code("00001","校验失败"),
        param_null("00002","输入参数为空"),
        user_null("00003","用户未登录"),
        data_null("00004","返回数据为空"),
        pwd_error("00005","密码错误"),
        vercode_over("00006","验证码过期"),
        NO_PERMISSIONS("00007","没有权限"),
        USER_EXAM_TIMES_OVER("00008","用户已达考试次数上限"),
        USER_NOT_EXIST("00009","用户不存在"),
        USER_PHONE_EXIST("00010","用户手机号已经存在"),
        USER_IDCARD_EXIST("00011","身份证号已绑定"),
        COURSE_ALREADY_EXISTS_SHOPCART("00012","课程已存在购物车中"),
        USER_HAS_BUY_QUESLIB("00013","用户已经购买了题库"),
        QUESLIB_ORDER_ALREADY_EXISTS("00014","该题库订单已存在"),
        ORDER_COURSE_EMPTY("00015","购买商品不能为空"),
        ORDER_COUNTPRICE_WRONG("00016","价格计算错误"),
        FILE_PATTERN_WRONG("00017","文件格式错误"),
        EXCEL_FILE_WRONG("00018","excel模板错误"),
        IMPORT_FILE_EMPTY("00019","上传文件为空"),
        ORDER_BATCH_USER_WRONG("00020","批量用户下单失败"),
        TOKEN_INVALID("00021","校验token失败"),
        VERCODE_ERROR("00022","无效验证码"),
        USER_COURSE_ORDER_EXIST("00023","用户已购买订单"),
        QUES_IMPORT_ERROR("00024","第%s行试题校验错误"),
        USER_ALREADY_HAS_COURSE("00025","用户已购买该课程"),
        QUESLIB_RULE_NOT_FOUND("00026","未设置题库考试规则"),
        QUES_IS_RULE_LOCK("00027","试题已被题库规则锁定"),
        IDCARD_CANNOT_EMPTY("00028","身份证号不能为空"),
        USERNAME_CANNOT_EMPTY("00029","姓名不能为空")
        ;
        private EnumCode(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static EnumCode getValueByKey(String key) {
            EnumCode[] values = EnumCode.values();
            for (EnumCode consts : values) {
                if (consts.getKey().equals(key)) {
                    return consts;
                }
            }
            return null;
        }
    }
}
