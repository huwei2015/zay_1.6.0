package com.example.administrator.zahbzayxy.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonTestLiberayAdapter;
import com.example.administrator.zahbzayxy.beans.LessonAttachTestBean;
import com.example.administrator.zahbzayxy.beans.TestLiberayBean;
import com.example.administrator.zahbzayxy.interfaceserver.LessonGroupInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LesssonTestLiberyFragment extends Fragment {
    View view;
    Context context;
    ListView testLibery_lv;
    private List<TestLiberayBean>list=new ArrayList<>();
    LessonTestLiberayAdapter adapter;
    private List<LessonAttachTestBean.DataBean>totalList=new ArrayList<>();
    private int courseId;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_test_libery, container, false);
        initView();
        downLoadData();
        return view;
    }

    private void downLoadData() {

        courseId= getActivity().getIntent().getIntExtra("courseId", 0);
        adapter=new LessonTestLiberayAdapter(context,totalList);
        testLibery_lv.setAdapter(adapter);
        LessonGroupInterface aClass = RetrofitUtils.getInstance().createClass(LessonGroupInterface.class);
        aClass.getLessonAttachTestData(courseId).enqueue(new Callback<LessonAttachTestBean>() {
            @Override
            public void onResponse(Call<LessonAttachTestBean> call, Response<LessonAttachTestBean> response) {
                LessonAttachTestBean body = response.body();
                if (response!=null&&body!=null){
                    List<LessonAttachTestBean.DataBean> data = body.getData();
                    totalList.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LessonAttachTestBean> call, Throwable t) {

            }
        });

    }

    private void initView() {

        testLibery_lv= (ListView) view.findViewById(R.id.testLibery_lv);

    }


}
