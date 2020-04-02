package com.example.administrator.zahbzayxy.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.FreeLessonExpandedAdapter;
import com.example.administrator.zahbzayxy.beans.PMyLessonPlayBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.myinterface.MyInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2020-03-12.
 * Time 20:29.
 */
@SuppressLint("ValidFragment")
public class FreeDirectoryFragment extends Fragment {
    private Context context;
    private View view;
    private ExpandableListView expandLv;
    public List<PMyLessonPlayBean.DataBean.ChildCourseListBean> totalList = new ArrayList<>();
    public FreeLessonExpandedAdapter adapter;
    private int courseId;
    private int userCourseId;
    private int courseType;
    private String token;
    private String mImagePath;
    ArrayList<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> listsize = new ArrayList<>();

    MyInterface.ItemClickedListener itemClickedListener;

    public FreeDirectoryFragment() {
    }

    public FreeDirectoryFragment(MyInterface.ItemClickedListener itemClickedListener, String imagePath) {
        // Required empty public constructor
        this.itemClickedListener = itemClickedListener;
        this.mImagePath = imagePath;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_plesson_directory, container, false);
        initView();
        downLoadData();
        return view;
    }

    private void initView() {
        expandLv = view.findViewById(R.id.pMyLesson_eplv);
        courseId = getActivity().getIntent().getIntExtra("coruseId", 0);
        courseType=getActivity().getIntent().getIntExtra("courseType",0);
        userCourseId = getActivity().getIntent().getIntExtra("userCourseId", 0);
        Log.e("fragmentddidddddddd", courseId + "," + userCourseId);
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
        token = tokenDb.getString("token", "");

    }

    @Override
    public void onResume() {
        super.onResume();
        downLoadData();
    }
    private int posIndxe;
    private void downLoadData() {
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getFreePlayData(courseId,courseType,token).enqueue(new Callback<PMyLessonPlayBean>() {
            int recordIndex = 0;
            private int selectionId;
            @Override
            public void onResponse(Call<PMyLessonPlayBean> call, Response<PMyLessonPlayBean> response) {
                PMyLessonPlayBean body = response.body();
                if (body != null && body.getErrMsg() == null) {
                    List<PMyLessonPlayBean.DataBean.ChildCourseListBean> childCourseList = body.getData().getChildCourseList();
                    totalList.clear();
                    totalList.addAll(childCourseList);
                    int size = totalList.size();
                    for (int i = 0; i < size; i++) {
                        PMyLessonPlayBean.DataBean.ChildCourseListBean childCourseListBean = totalList.get(i);
                        List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean> chapterList = childCourseListBean.getChapterList();
                        int size1 = chapterList.size();
                        for (int j = 0; j < size1; j++) {
                            List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean> selectionList = chapterList.get(j).getSelectionList();
                            int size2 = selectionList.size();
                            for (int h = 0; h < size2; h++) {
                                PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean selectionListBean1 = selectionList.get(h);
                                //TODO添加自动播放
                                selectionListBean1.setVideoIndex(recordIndex);
                                listsize.add(selectionListBean1);
                                boolean currPlay = selectionListBean1.isCurrPlay();
                                if (currPlay == true) {
                                    PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean selectionListBean = selectionList.get(h);
                                    selectionId = selectionListBean.getSelectionId();
                                    posIndxe=selectionListBean1.getVideoIndex();
                                    posIndxe = recordIndex;
                                }
                                recordIndex++;
                            }
                        }
                    }


//                    adapter = new PMyLessonExpandedAdapter(new MyInterface.ItemClickedListener() {
//                        @Override
//                        public void onMyItemClickedListener(String vidioId, int selectionId, double playPercent, String selectionName, int backSelectionId) {
//                            itemClickedListener.onMyItemClickedListener(vidioId, videoIndex,selectionId, playPercent, selectionName, backSelectionId,startPlayTime);
//                        }
//                    }, context, totalList, selectionId);
                    adapter = new FreeLessonExpandedAdapter(new MyInterface.ItemClickedListener() {
                        @Override
                        public void onMyItemClickedListener(String vidioId, int videoIndex, int selectionId, double playPercent, String selectionName, int backSelectionId, int startPlayTime, List<PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean>list) {
                            itemClickedListener.onMyItemClickedListener(vidioId, videoIndex, selectionId, playPercent, selectionName, backSelectionId, startPlayTime,list);
                        }
                    }, context, totalList, selectionId, courseId, String.valueOf(userCourseId), mImagePath);
                    expandLv.setAdapter(adapter);
                    //禁止刷新列表
                    adapter.notifyDataSetChanged();

//                    Log.e("directoryViedioId",videoId);
                }
            }

            @Override
            public void onFailure(Call<PMyLessonPlayBean> call, Throwable t) {
            }
        });

    }

    public void updatePlayerProgess(int selectionID, double porgess, int currentTime) {
        for (int i = 0; i < totalList.size(); i++) {
            for (int j = 0; j < totalList.get(i).getChapterList().size(); j++) {
                for (int k = 0; k < totalList.get(i).getChapterList().get(j).getSelectionList().size(); k++) {
                    PMyLessonPlayBean.DataBean.ChildCourseListBean.ChapterListBean.SelectionListBean item = totalList.get(i).getChapterList().get(j).getSelectionList().get(k);
                    if (item.getSelectionId() == selectionID && item.getPlayTime() < currentTime) {
                        item.setPlayPercent(porgess / item.getTotalPlayTime());
                        item.setPlayTime(currentTime);
//                        adapter.notifyDataSetChanged();
                        Log.i("33", "");
                        return;
                    }

                }
            }
        }
    }
}
