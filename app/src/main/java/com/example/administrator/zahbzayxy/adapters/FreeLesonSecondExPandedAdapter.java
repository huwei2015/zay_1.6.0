package com.example.administrator.zahbzayxy.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PMyLessonPlayBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadController;
import com.example.administrator.zahbzayxy.ccvideo.FreePlayActivity;
import com.example.administrator.zahbzayxy.ccvideo.MediaPlayActivity;
import com.example.administrator.zahbzayxy.myinterface.MyInterface;
import com.example.administrator.zahbzayxy.myviews.MyExpandableLV;
import com.example.administrator.zahbzayxy.utils.DateUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by huwei.
 * Data 2020-03-13.
 * Time 18:18.
 */
public class FreeLesonSecondExPandedAdapter extends BaseExpandableListAdapter {
    private List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean> list;
    Context context;
    MyInterface.ItemClickedListener itemClickedListener;
    private String title;
    private double playPercent;
    int totalPlayTime;
    private int startPlayTime;
    LayoutInflater mInflater;
    int courseId;
    int videoIndex;
    String userCourseId;
    FreeLessonExpandedAdapter parentAdapter;
    public MyExpandableLV listview;
    int currentRootPosition;
    private String mImagePath;

    public FreeLesonSecondExPandedAdapter(MyExpandableLV listview, int currentRootPosition, FreeLessonExpandedAdapter parentAdapter, MyInterface.ItemClickedListener itemClickedListener, List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean> list, Context context, int selectionId, int courseId, String userCourseId, String imagePath) {
        this.listview = listview;
        this.currentRootPosition = currentRootPosition;
        this.parentAdapter = parentAdapter;
        this.list = list;
        this.context = context;
        this.itemClickedListener = itemClickedListener;
        mInflater = LayoutInflater.from(context);
        this.courseId = courseId;
        this.userCourseId = userCourseId;
        this.mImagePath = imagePath;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSelectionList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getSelectionList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_expanded_iv_layout, parent, false);
        TextView lessonName_tv = view.findViewById(R.id.ziLessonName_tv);
        ImageView expanded_iv = view.findViewById(R.id.ziLesson_iv);
        String chapterName = list.get(groupPosition).getChapterName();
        if (!TextUtils.isEmpty(chapterName)) {
            lessonName_tv.setText(chapterName);
        }
        if (isExpanded) {
            expanded_iv.setImageResource(R.mipmap.open_up);
        } else {
            expanded_iv.setImageResource(R.mipmap.close_down);
        }

        return view;


    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        FreeLessonSecondExpandedChildAdapter.LessonChidViewHold lessonChidViewHold;
        if (convertView == null) {
            lessonChidViewHold = new FreeLessonSecondExpandedChildAdapter.LessonChidViewHold();
            convertView = mInflater.inflate(R.layout.item_chid_lesson_chapt_layout, parent, false);
            lessonChidViewHold.item_lesson_chaptName_tv = convertView.findViewById(R.id.item_lesson_chaptName_tv);
            lessonChidViewHold.item_lesson_time_tv = convertView.findViewById(R.id.item_lesson_time_tv);
            lessonChidViewHold.item_lesson_study_percent_tv = convertView.findViewById(R.id.item_lesson_study_percent_tv);
            lessonChidViewHold.icon_download_big = convertView.findViewById(R.id.item_lessson_chapt_downLoad_iv);
            convertView.setTag(lessonChidViewHold);
        } else {
            lessonChidViewHold = (FreeLessonSecondExpandedChildAdapter.LessonChidViewHold) convertView.getTag();
        }

        PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean selectionListBean = list.get(groupPosition).getSelectionList().get(childPosition);

        final String selectionName = selectionListBean.getSelectionName();
        int totalPlayTime = selectionListBean.getTotalPlayTime();
        double playPercent = selectionListBean.getPlayPercent();
        lessonChidViewHold.item_lesson_chaptName_tv.setText(selectionName + "");


        String time = DateUtil.secondFormat(totalPlayTime);
        lessonChidViewHold.item_lesson_time_tv.setText(time + "");

        BigDecimal bd = new BigDecimal(playPercent * 100);
        BigDecimal bigDecimal = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
        Log.e("formataa", bigDecimal + "");
        if (!TextUtils.isEmpty(String.valueOf(bigDecimal))) {
            lessonChidViewHold.item_lesson_study_percent_tv.setText("学习进度:" + bigDecimal + "%");
            int i1 = bigDecimal.compareTo(BigDecimal.valueOf(100));
            if (i1 > 0) {
                lessonChidViewHold.item_lesson_study_percent_tv.setText("学习进度:" + 100 + "%");
            }
        }

        lessonChidViewHold.icon_download_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog();
            }

            private void initDialog() {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("温馨提示");//窗口名
                dialog.setMessage("下载该课程? ");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        title = list.get(groupPosition).getSelectionList().get(childPosition).getVideoId();
                        if (com.example.administrator.zahbzayxy.ccvideo.DataSet.hasDownloadInfo(title)) {
                            Toast.makeText(context, "文件已存在", Toast.LENGTH_SHORT).show();
                            // return;
                        } else {
                            DownloadController.insertDownloadInfo(title, title, selectionName, 0, Integer.parseInt(userCourseId), list.get(groupPosition).getSelectionList().get(childPosition).getSelectionId(), courseId, mImagePath);
                            Toast.makeText(context, "文件已加入下载队列", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeItem(groupPosition, childPosition, list.get(groupPosition).getSelectionList());

            }
        });
        if (list.get(groupPosition).getSelectionList().get(childPosition).getSelectionId() == parentAdapter.getSelectionId) {
            lessonChidViewHold.item_lesson_chaptName_tv.setTextColor(ContextCompat.getColor(context, R.color.tv_red));
            parentAdapter.currentAdapter = this;
            parentAdapter.currentRootPosition = this.currentRootPosition;
            parentAdapter.currentGroupPosition = groupPosition;
            parentAdapter.currentChildPosition = childPosition;

        } else {
            lessonChidViewHold.item_lesson_chaptName_tv.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        return convertView;
    }

    public void changeItem(int groupPosition, int childPosition, List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> selectionList) {
        PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean selectionListBean = selectionList.get(childPosition);
        final int selectionId = selectionListBean.getSelectionId();

        double prePercent = parentAdapter.list.get(parentAdapter.currentRootPosition).getChapterList().get(parentAdapter.currentGroupPosition).getSelectionList().get(parentAdapter.currentChildPosition).getPlayPercent();

        BigDecimal bd = new BigDecimal(prePercent * 100);
        BigDecimal bigDecimal = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
        Log.e("gxj-precent", bd.doubleValue() + "");
        double playingPercent = ((FreePlayActivity) context).getPercent();
        if (!TextUtils.isEmpty(String.valueOf(bigDecimal)) && bd.doubleValue() < 100 && prePercent < playingPercent) {
            Log.e("gxj-precent", "重新改变进度");
            parentAdapter.list.get(parentAdapter.currentRootPosition).getChapterList().get(parentAdapter.currentGroupPosition).getSelectionList().get(parentAdapter.currentChildPosition).setPlayPercent(((FreePlayActivity) context).getPercent());
            Log.e("gxj-playPercent", ((FreePlayActivity) context).getPercent() + "");
        }

        parentAdapter.getSelectionId = selectionId;
        if (parentAdapter.currentAdapter != null) {//上一个选中的adapter
            parentAdapter.currentAdapter.notifyDataSetChanged();
            //刷新
            parentAdapter.currentAdapter.listview.collapseGroup(parentAdapter.currentGroupPosition);
            parentAdapter.currentAdapter.listview.expandGroup(parentAdapter.currentGroupPosition);
        }
        this.notifyDataSetChanged();
        //刷新
        listview.collapseGroup(groupPosition);
        listview.expandGroup(groupPosition);
        //重新赋值
        parentAdapter.currentAdapter = this;
        parentAdapter.currentRootPosition = currentRootPosition;
        parentAdapter.currentGroupPosition = groupPosition;
        parentAdapter.currentChildPosition = childPosition;
        //先取
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
        int selectionId2 = tokenDb.getInt("selectionId", 0);
        String videoId = selectionList.get(childPosition).getVideoId();
        playPercent = selectionList.get(childPosition).getPlayPercent();
        startPlayTime = selectionList.get(childPosition).getPlayTime();
        videoIndex = selectionList.get(childPosition).getVideoIndex();
        String selectionName = selectionList.get(childPosition).getSelectionName();
        //如果点击切换了保存的是上个视频的进度，没有点击切换是保存当前播放的视频的进度。
        //所以点击的时候只有先取后存才能把传回的selectionId2是上一个视频的selectionId;
        // getSelectionId是当没有切换的视频的时候，退出的时候需要保存的视频播放进度
        itemClickedListener.onMyItemClickedListener(videoId, videoIndex, selectionId2, playPercent, selectionName, selectionId, startPlayTime, selectionList);
        Log.e("selecIdtest", "333,,," + selectionId2);
        int selectionId3 = selectionList.get(childPosition).getSelectionId();
        totalPlayTime = selectionList.get(childPosition).getTotalPlayTime();
        //再存
        SharedPreferences sp = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("selectionId", selectionId3);
        edit.apply();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    /**
     * 自动切换下一个视频播放 刷新上个视频UI
     */
    public void automChangeItem() {
        parentAdapter.list.get(parentAdapter.currentRootPosition).getChapterList().get(parentAdapter.currentGroupPosition).getSelectionList().get(parentAdapter.currentChildPosition).setPlayPercent(1);
        Log.e("gxj-playPercent", ((FreePlayActivity) context).getPercent() + "");
        if (parentAdapter.currentAdapter != null) {//上一个选中的adapter
            parentAdapter.currentAdapter.notifyDataSetChanged();
            //刷新
            parentAdapter.currentAdapter.listview.collapseGroup(parentAdapter.currentGroupPosition);
            parentAdapter.currentAdapter.listview.expandGroup(parentAdapter.currentGroupPosition);
        }
    }
}
