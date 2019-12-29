package com.example.administrator.zahbzayxy.beans;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/5 0005.
 */
public class BookBean {

    /**
     * code : 00000
     * errMsg : null
     * data : {"dividePrice":"课时收费标准：1-16学时 12元/学时;16学时以上 10元/学时","courseList":[{"beginDate":1491557859000,"courseName":"金属非金属矿山安全生产管理人员培训课程（地下矿-初训）","userCourseId":19,"totalHours":54,"endDate":1492249059000,"logo":"/file_upload/course/logo/2016/9/ee9a5251-39de-43c7-9773-19cec6f8b148.jpg","coruseId":186}]}
     */

    private String code;
    private Object errMsg;

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
        private List<BookListBean> bookList;

        public List<BookListBean> getBookList() {
            return bookList;
        }

        public void setBookList(List<BookListBean> bookList) {
            this.bookList = bookList;
        }

        public static class BookListBean {
            private int id;
            private String bookName;
            private String sPrice;
            private String imageUrl;
            private String updateTime;


            private int id1;
            private String bookName1;
            private String sPrice1;
            private String imageUrl1;
            private String updateTime1;

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getUpdateTime1() {
                return updateTime1;
            }

            public void setUpdateTime1(String updateTime1) {
                this.updateTime1 = updateTime1;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBookName() {
                return bookName;
            }

            public void setBookName(String bookName) {
                this.bookName = bookName;
            }

            public String getsPrice() {
                return sPrice;
            }

            public void setsPrice(String sPrice) {
                this.sPrice = sPrice;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public int getId1() {
                return id1;
            }

            public void setId1(int id1) {
                this.id1 = id1;
            }

            public String getBookName1() {
                return bookName1;
            }

            public void setBookName1(String bookName1) {
                this.bookName1 = bookName1;
            }

            public String getsPrice1() {
                return sPrice1;
            }

            public void setsPrice1(String sPrice1) {
                this.sPrice1 = sPrice1;
            }

            public String getImageUrl1() {
                return imageUrl1;
            }

            public void setImageUrl1(String imageUrl1) {
                this.imageUrl1 = imageUrl1;
            }
        }
    }
}
