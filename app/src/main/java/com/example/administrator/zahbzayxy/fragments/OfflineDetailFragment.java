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
import com.example.administrator.zahbzayxy.beans.OfflineCoursePOBean;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
import com.example.administrator.zahbzayxy.interfaceserver.LessonGroupInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import io.objectbox.annotation.Index;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineDetailFragment extends Fragment {
   Context context;
    private View view;
    private TextView detail_introduce_wv;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
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
        IndexInterface aClass = RetrofitUtils.getInstance().createClass(IndexInterface.class);
        aClass.getOfflineCourseDetail(courseId1).enqueue(new Callback<OfflineCoursePOBean>() {
            @Override
            public void onResponse(Call<OfflineCoursePOBean> call, Response<OfflineCoursePOBean> response) {
                OfflineCoursePOBean body = response.body();
                if (body!=null&&response!=null) {
                    OfflineCoursePOBean.DataBean data = body.getData();
                    if (data!=null) {
                        OfflineCoursePOBean.DataBean.CourseBean courseDesc = data.getCourse();
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
            public void onFailure(Call<OfflineCoursePOBean> call, Throwable t) {
                String message = t.getMessage();
                Log.e("failedDetail",message);

            }
        });
    }

}
