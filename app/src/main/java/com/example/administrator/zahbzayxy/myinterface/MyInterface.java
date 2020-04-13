package com.example.administrator.zahbzayxy.myinterface;

import com.example.administrator.zahbzayxy.beans.PMyLessonPlayBean;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/9 0009.
 */
public class MyInterface {
    //视频播放传递三级里面的子viewId时调用
    public interface ItemClickedListener{
         void onMyItemClickedListener(String vidioId, int videoIndex, int selectionId, double playPercent, String lessonName, int selectionIdack, int startPlaytTime, List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> list);
    }

    //错题详情时把adapter中位置和qustionId传到activity
    public interface ErrorOnClickedListenner{
        void onMyItemClickedListenner(int questionId, int postion);
    }
}
