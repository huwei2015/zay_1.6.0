package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by huwei.
 * Data 2019-12-13.
 * Time 11:18.
 */
public class TimeData {
    private String code;
    private String errMsg;
    private MsgData data;

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

    public MsgData getData() {
        return data;
    }

    public void setData(MsgData data) {
        this.data = data;
    }

    public static class MsgData {
        private List<MsgList> allTopAnnounceList;

        public List<MsgList> getAllTopAnnounceList() {
            return allTopAnnounceList;
        }

        public void setAllTopAnnounceList(List<MsgList> allTopAnnounceList) {
            this.allTopAnnounceList = allTopAnnounceList;
        }
    }

    public static class MsgList {
        private int id;
        private String platformId;
        private String platformOtherId;
        private String classifyName;
        private int classifyId;
        private String title;
        private String source;
        private String content;
        private int isPublish;
        private int type;
        private int readNumber;
        private int likeNum;
        private String isHaveAttachment;
        private int isDelete;
        private String createTime;
        private String newCreateTime;
        private String createTimeStart;
        private String createTimeEnd;
        private String updateTime;
        private int creatorId;
        private int updaterId;
        private boolean yRead;
        private String period;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlatformId() {
            return platformId;
        }

        public void setPlatformId(String platformId) {
            this.platformId = platformId;
        }

        public String getPlatformOtherId() {
            return platformOtherId;
        }

        public void setPlatformOtherId(String platformOtherId) {
            this.platformOtherId = platformOtherId;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public int getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(int classifyId) {
            this.classifyId = classifyId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIsPublish() {
            return isPublish;
        }

        public void setIsPublish(int isPublish) {
            this.isPublish = isPublish;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getReadNumber() {
            return readNumber;
        }

        public void setReadNumber(int readNumber) {
            this.readNumber = readNumber;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getIsHaveAttachment() {
            return isHaveAttachment;
        }

        public void setIsHaveAttachment(String isHaveAttachment) {
            this.isHaveAttachment = isHaveAttachment;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getNewCreateTime() {
            return newCreateTime;
        }

        public void setNewCreateTime(String newCreateTime) {
            this.newCreateTime = newCreateTime;
        }

        public String getCreateTimeStart() {
            return createTimeStart;
        }

        public void setCreateTimeStart(String createTimeStart) {
            this.createTimeStart = createTimeStart;
        }

        public String getCreateTimeEnd() {
            return createTimeEnd;
        }

        public void setCreateTimeEnd(String createTimeEnd) {
            this.createTimeEnd = createTimeEnd;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(int creatorId) {
            this.creatorId = creatorId;
        }

        public int getUpdaterId() {
            return updaterId;
        }

        public void setUpdaterId(int updaterId) {
            this.updaterId = updaterId;
        }

        public boolean isyRead() {
            return yRead;
        }

        public void setyRead(boolean yRead) {
            this.yRead = yRead;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }
    }


    private String posttime;
    private String title;

    public TimeData(String posttime, String title) {
        this.title = title;
        this.posttime = posttime;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
