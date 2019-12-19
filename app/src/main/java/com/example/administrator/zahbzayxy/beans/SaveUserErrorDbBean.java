package com.example.administrator.zahbzayxy.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ${ZWJ} on 2017/4/14 0014.
 */
@Entity
public class SaveUserErrorDbBean {
      @Id(autoincrement = true)
        private long id;
        private int quesLibId;
        private String json;
        public SaveUserErrorDbBean(int quesLibId, String json) {
            this.quesLibId = quesLibId;
            this.json = json;
        }
        public SaveUserErrorDbBean() {
        }
        @Generated(hash = 333460341)
        public SaveUserErrorDbBean(long id, int quesLibId, String json) {
            this.id = id;
            this.quesLibId = quesLibId;
            this.json = json;
        }
        public long getId() {
            return this.id;
        }
        public void setId(long id) {
            this.id = id;
        }
        public int getQuesLibId() {
            return this.quesLibId;
        }
        public void setQuesLibId(int quesLibId) {
            this.quesLibId = quesLibId;
        }
        public String getJson() {
            return this.json;
        }
        public void setJson(String json) {
            this.json = json;
        }
        


    }

