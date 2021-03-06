package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.ThirdLessonEXpandedAdapter;
import com.example.administrator.zahbzayxy.beans.LessonThiredBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.interfacecommit.BuyCarGroupInterface;
import com.example.administrator.zahbzayxy.interfaceserver.LessonGroupInterface;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * 课程详情
 */
public class DirectoryFragment extends Fragment implements ThirdLessonEXpandedAdapter.OnItemClickListener {

    private Context context;
    private View view;
    private ExpandableListView expandLv;
    private ThirdLessonEXpandedAdapter thirdLessonAdapter;
    private List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean> beanList = new ArrayList<>();
    private static int mainCourseId;
    private static String token;
    private int courseId;
    int courseType;
    private String isDatacenter;


    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setIsDatacenter(String isDatacenter) {
        this.isDatacenter = isDatacenter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_directory, container, false);
        initData();
        downLoadData();

        return view;
    }

    private void downLoadData() {
        thirdLessonAdapter = new ThirdLessonEXpandedAdapter(context, beanList);
        expandLv.setAdapter(thirdLessonAdapter);
        thirdLessonAdapter.setOnItemClickListener(this);
        courseId = getActivity().getIntent().getIntExtra("courseId", 0);
        Log.e("thiredLessonId", String.valueOf(courseId));
        LessonGroupInterface aClass = RetrofitUtils.getInstance().createClass(LessonGroupInterface.class);
        Call<LessonThiredBean> lessonDetailData = aClass.getLessonDetailData(courseId, isDatacenter);
        lessonDetailData.enqueue(new Callback<LessonThiredBean>() {
            @Override
            public void onResponse(Call<LessonThiredBean> call, Response<LessonThiredBean> response) {
                LessonThiredBean body = response.body();
                if (response != null && body != null) {
                    LessonThiredBean.DataBean data = body.getData();
                    if (data != null) {
                        List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean> childList = data.getCourseDesc().getChildList();
                        if (childList != null) {
                            beanList.clear();
                            beanList.addAll(childList);
                            thirdLessonAdapter.notifyDataSetChanged();
                            mainCourseId = body.getData().getCourseDesc().getId();
                            //把mainCourseId和token传到adapter中
                            thirdLessonAdapter.setMainCourseId(mainCourseId);
                            thirdLessonAdapter.setToken(token);
                            Log.e("mainCourseIdaaaaaaaaaa", String.valueOf(mainCourseId));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LessonThiredBean> call, Throwable t) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        downLoadData();
    }


    private void initData() {
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        expandLv = (ExpandableListView) view.findViewById(R.id.thirdLesson_eplv);
        expandLv.setGroupIndicator(null);

    }

    @Override
    public void onItemClick(View view, int position) {
        int subCourseIds = beanList.get(position).getId();
        final String[] cIds=new String[]{String.valueOf(subCourseIds)};
        if(isDatacenter.equals("yes")){
            courseType = 0;
        }else if(isDatacenter.equals("no")){
            courseType = 1;
        }
        BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
        Log.e("getFromFragment", token + "," + mainCourseId);
        Call<SuccessBean> successBeanCall = aClass.buyCarAddCourseData(mainCourseId, cIds, token, courseType);
        successBeanCall.enqueue(new Callback<SuccessBean>() {
            @Override
            public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                SuccessBean body = response.body();
                if (body != null) {
                    String code = body.getCode();
                    boolean data = body.getData();
                    if (body != null) {
                        String errMsg = (String) body.getErrMsg();
                        if (code.equals("00003")) {
                            Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (code.equals("00000")) {
                            Toast.makeText(context, "加入购物车成功", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00012")) {
                            Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<SuccessBean> call, Throwable t) {
                Toast.makeText(context, "请求服务器失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
