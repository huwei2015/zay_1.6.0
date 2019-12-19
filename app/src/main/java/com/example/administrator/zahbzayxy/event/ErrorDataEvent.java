package com.example.administrator.zahbzayxy.event;

/**
 * Created by ${ZWJ} on 2017/4/28 0028.
 */
public class ErrorDataEvent {
    private final int msg;
    public ErrorDataEvent(int msg) {
        this.msg=msg;
    }
    public int getMsg() {
        return msg;
    }


}
