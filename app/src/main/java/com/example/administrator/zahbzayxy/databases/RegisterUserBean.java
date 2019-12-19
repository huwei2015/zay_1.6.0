package com.example.administrator.zahbzayxy.databases;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ${ZWJ} on 2017/1/18 0018.
 */
@Entity
public class RegisterUserBean {
    //表示主键，而且自动增长,备注：主键必须是long类型,autoincrement自动增长无效
    @Id(autoincrement = true)
    long id;
    String name;
    String telephone;
    String passWord;
    @Generated(hash = 449557721)
    public RegisterUserBean(long id, String name, String telephone,
            String passWord) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.passWord = passWord;
    }
    @Generated(hash = 1062318035)
    public RegisterUserBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTelephone() {
        return this.telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getPassWord() {
        return this.passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
   
}
