package com.example.administrator.zahbzayxy.beans;

/**
 * Created by ${ZWJ} on 2017/2/9 0009.
 */
public class TestLiberayBean {
    private String testName;
    private String testDate;
    private String testAndBuy;

    public TestLiberayBean() {

    }

    public TestLiberayBean(String testName, String testDate, String testAndBuy) {
        this.testName = testName;
        this.testDate = testDate;
        this.testAndBuy = testAndBuy;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getTestAndBuy() {
        return testAndBuy;
    }

    public void setTestAndBuy(String testAndBuy) {
        this.testAndBuy = testAndBuy;
    }
}
