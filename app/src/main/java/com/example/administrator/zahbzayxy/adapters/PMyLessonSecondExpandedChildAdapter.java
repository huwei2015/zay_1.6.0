package com.example.administrator.zahbzayxy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PMyLessonPlayBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadController;
import com.example.administrator.zahbzayxy.utils.DateUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author {ZWJ}
 * @date 2019/1/22 0022.
 * description:
 * 视频第三节点adapter
 */

public class PMyLessonSecondExpandedChildAdapter extends BaseAdapter {
    List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> selectionList;
    Context mContext;
    LayoutInflater inflater;
    String title;
    int getSelectionId;
    String userCourseId;
    int selectionId;
    int courseId;
    private String mImagePath;
    public void setSelectorPos(int selectorPos) {
        this.selectorPos = selectorPos;
    }

    private int selectorPos;

    public PMyLessonSecondExpandedChildAdapter( List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> list, Context mContext,int getSelectionId,int courseId, String userCourseId, String imagePath) {
        this.selectionList =list;
        this.mContext = mContext;
        inflater=LayoutInflater.from(mContext);
        this.getSelectionId=getSelectionId;
        this.userCourseId = userCourseId;
        this.mImagePath = imagePath;
    }

    @Override
    public int getCount() {
        return selectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return selectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LessonChidViewHold lessonChidViewHold;
        if (convertView==null){
            lessonChidViewHold=new LessonChidViewHold();
            convertView=inflater.inflate(R.layout.item_chid_lesson_chapt_layout,parent,false);
            lessonChidViewHold.item_lesson_chaptName_tv=convertView.findViewById(R.id.item_lesson_chaptName_tv);
            lessonChidViewHold.item_lesson_time_tv=convertView.findViewById(R.id.item_lesson_time_tv);
            lessonChidViewHold.item_lesson_study_percent_tv=convertView.findViewById(R.id.item_lesson_study_percent_tv);
            lessonChidViewHold.icon_download_big=convertView.findViewById(R.id.item_lessson_chapt_downLoad_iv);
            convertView.setTag(lessonChidViewHold);
        }else {
            lessonChidViewHold= (LessonChidViewHold) convertView.getTag();
        }
        PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean selectionListBean = selectionList.get(position);
        final String selectionName= selectionListBean.getSelectionName();
        int totalPlayTime= selectionListBean.getTotalPlayTime();
        double playPercent = selectionListBean.getPlayPercent();
        lessonChidViewHold.item_lesson_chaptName_tv.setText(selectionName+"");
        String time= DateUtil.secondFormat(totalPlayTime);
        lessonChidViewHold.item_lesson_time_tv.setText(time+"");

        BigDecimal bd = new BigDecimal(playPercent*100);
            BigDecimal bigDecimal = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
            Log.e("formataa",bigDecimal+"");
            if (!TextUtils.isEmpty(String.valueOf(bigDecimal))){
             lessonChidViewHold.item_lesson_study_percent_tv.setText("学习进度:"+bigDecimal+"%");
                int i1 = bigDecimal.compareTo(BigDecimal.valueOf(100));
                if (i1>0){
                    lessonChidViewHold.item_lesson_study_percent_tv.setText("学习进度:"+100+"%");
                }
            }

        lessonChidViewHold.icon_download_big.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initDialog();
                }
                private void initDialog() {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    dialog.setTitle("温馨提示");//窗口名
                    dialog.setMessage("下载该课程? ");
                    dialog.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            title=selectionList.get(position).getVideoId();
                            if (com.example.administrator.zahbzayxy.ccvideo.DataSet.hasDownloadInfo(title)) {
                                Toast.makeText(mContext, "文件已存在", Toast.LENGTH_SHORT).show();
                                // return;
                            }else {
                                DownloadController.insertDownloadInfo(title, title,selectionName,0, Integer.parseInt(userCourseId), selectionId, courseId, mImagePath);
                                Toast.makeText(mContext, "文件已加入下载队列", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    dialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                }
            });

        for (int i=0;i<selectionList.size();i++){
            int selectionId = selectionList.get(i).getSelectionId();
            if (selectionId==getSelectionId){
                    selectorPos=i;

            }
            int selectionId1 = selectionList.get(i).getSelectionId();
            Log.e("selecIdtest","111,,,"+selectionId1);
        }
        //点击视频添加标题颜色
        if (selectorPos==position && selectionListBean.isCurrPlay() == true){
            lessonChidViewHold.item_lesson_chaptName_tv.setTextColor(mContext.getResources().getColor(R.color.green));
        }else {
            lessonChidViewHold.item_lesson_chaptName_tv.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        return convertView;
    }
   static class LessonChidViewHold{
        TextView item_lesson_chaptName_tv,item_lesson_time_tv,item_lesson_study_percent_tv;
        ImageView icon_download_big;

    }
}
