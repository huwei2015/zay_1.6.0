package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/3/29 0029.
 */
public class TestWaitPayDataBean {

    /**
     * code : 00000
     * errMsg : null
     * data : app_id=2016110902662982&biz_content=%7B%22out_trade_no%22%3A%221490769570100%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%B8%AD%E5%AE%89%E4%BA%91%E5%AD%A6%E9%99%A2-%E8%AE%A2%E5%8D%95%E6%94%AF%E4%BB%98%22%2C%22total_amount%22%3A%220.00%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fstaticwandon.ngrok.cc%2Falipay%2FpayCallBack&sign=SV3LVBnoAsRW3uGj8fRuGVHwbhJX5pSM4Az%2BY%2Bkww0gZvuQaV5BRpyRyHpYd4OgZI3AjRkjWSjPXe6TzOEZ1C4obUgIVIZi0gJLreSSFAGXDzk2h8G2MJUrJ9ftE9ftMwJ7mkDuiJWmAy13xeLukBagXIYEgGi9TUpS%2BthpOMn5qfiDmhEIqhHFdKsiAw4MZzmyNE40plXnnk9wwohyQFN2LnKqEAJYDqTBEE5TbYAbrHAgE%2F6he8FasqTQ0gRskyrmWXJmSPzlkk6kktPjfoY7idYMfkND0Qnr25Plr5FPx6zKkfQtXI332kaER5ftf9eNaXDdPPSsT7P0yYOWg6Q%3D%3D&sign_type=RSA2&timestamp=2017-03-29+03%3A00%3A33&version=1.0
     */

    private String code;
    private Object errMsg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
