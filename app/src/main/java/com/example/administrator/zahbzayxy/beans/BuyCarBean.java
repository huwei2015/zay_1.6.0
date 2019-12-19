package com.example.administrator.zahbzayxy.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ${ZWJ} on 2017/3/2 0002.
 */
@Entity
public class BuyCarBean {
    @Id
    long id;

    //记录选中为1(支付中的标记)
    int tag;

    //记录选中为1(编辑中的标记)
    int tag1;

    //记录是否全选
    boolean quanxuan;
    //课程名
    String text1;
    //价格
    String text2;
  @Keep
    public BuyCarBean(long id, int tag, int tag1, String text1,
            String text2, boolean quanxuan) {
        this.id = id;
        this.tag = tag;
        this.tag1 = tag1;
        this.quanxuan = quanxuan;
        this.text1 = text1;
        this.text2 = text2;
    }
    @Keep
    public BuyCarBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getTag() {
        return this.tag;
    }
    public void setTag(int tag) {
        this.tag = tag;
    }
    public int getTag1() {
        return this.tag1;
    }
    public void setTag1(int tag1) {
        this.tag1 = tag1;
    }
    public boolean getQuanxuan() {
        return this.quanxuan;
    }
    public void setQuanxuan(boolean quanxuan) {
        this.quanxuan = quanxuan;
    }
    public boolean isQuanxuan() {
        return quanxuan;
    }
    public String getText1() {
        return this.text1;
    }
    public void setText1(String text1) {
        this.text1 = text1;
    }
    public String getText2() {
        return this.text2;
    }
    public void setText2(String text2) {
        this.text2 = text2;
    }

}
