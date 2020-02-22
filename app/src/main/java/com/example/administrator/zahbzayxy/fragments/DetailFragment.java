package com.example.administrator.zahbzayxy.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LessonThiredBean;
import com.example.administrator.zahbzayxy.interfaceserver.LessonGroupInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
   Context context;
    private View view;
    private TextView detail_introduce_wv;
    private String isDatacenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public void setIsDatacenter(String isDatacenter) {
        this.isDatacenter = isDatacenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_title, container, false);
        initTextView();
        return view;
    }

    private void initTextView() {
        int courseId1 = getActivity().getIntent().getIntExtra("courseId", 0);
        Log.e("getCoureId11111",String.valueOf(courseId1));
        detail_introduce_wv= (TextView)view.findViewById(R.id.detail_introduce_wv);
        LessonGroupInterface aClass = RetrofitUtils.getInstance().createClass(LessonGroupInterface.class);
        aClass.getLessonDetailData(courseId1,isDatacenter).enqueue(new Callback<LessonThiredBean>() {
            @Override
            public void onResponse(Call<LessonThiredBean> call, Response<LessonThiredBean> response) {
                LessonThiredBean body = response.body();
                if (body!=null&&response!=null) {
                    LessonThiredBean.DataBean data = body.getData();
                    if (data!=null) {
                        LessonThiredBean.DataBean.CourseDescBean courseDesc = data.getCourseDesc();
                        if (courseDesc != null) {
                            String courseDesc1 = courseDesc.getCourseDesc();
                            if (!TextUtils.isEmpty(courseDesc1)) {
                                String s = Html.fromHtml(courseDesc1).toString();
                                detail_introduce_wv.setText(s);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LessonThiredBean> call, Throwable t) {
                String message = t.getMessage();
                Log.e("failedDetail",message);

            }
        });
    }

}
