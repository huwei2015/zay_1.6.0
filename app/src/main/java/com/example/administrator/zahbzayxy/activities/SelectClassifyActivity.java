package com.example.administrator.zahbzayxy.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.ListClassifyAdapter;
import com.example.administrator.zahbzayxy.adapters.PMyRecommendAdapter;
import com.example.administrator.zahbzayxy.beans.PMyLessonBean;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectClassifyActivity  extends BaseActivity implements View.OnClickListener {

	private LinearLayout layout;
    private List<PMyLessonBean.DataBean.CourseListBean> totalList = new ArrayList<>();
    private static String token;
    ListClassifyAdapter adapter;

    private int pageSize = 10;
    private int pager = 1;
    private String dividePrice;

    private ListView classifyLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_classify);
        initView();
        getSP();
        adapter = new ListClassifyAdapter(totalList, SelectClassifyActivity.this, token);
        classifyLv.setAdapter(adapter);
        initPullToRefreshLv();
		layout = (LinearLayout) findViewById(R.id.pop_layout);

        // 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
    }

    private void initView() {
        classifyLv=(ListView) findViewById(R.id.classifyLv);

    }

    private void initPullToRefreshLv() {
        downLoadData(pager);
    }

    private void getSP() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        Log.e("danWeiToken", token);
    }

    private void downLoadData(int pager) {
        PersonGroupInterfac aClass = RetrofitUtils.getInstance().createClass(PersonGroupInterfac.class);
        aClass.getPMyLessonData(pager, pageSize, token).enqueue(new Callback<PMyLessonBean>() {
            @Override
            public void onResponse(Call<PMyLessonBean> call, Response<PMyLessonBean> response) {
                int code1 = response.code();
                PMyLessonBean body = response.body();
                String s = new Gson().toJson(body);
                Log.e("lessonSSss", s);
                if (body != null && body.getData().getCourseList().size() > 0) {
                    String code = body.getCode();
                    if (!TextUtils.isEmpty(code)) {
                        if (code.equals("00003")) {
                            Toast.makeText(SelectClassifyActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            Toast.makeText(SelectClassifyActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            Toast.makeText(SelectClassifyActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            dividePrice = body.getData().getDividePrice();
                            adapter.setPrice(dividePrice);
                            List<PMyLessonBean.DataBean.CourseListBean> courseList = body.getData().getCourseList();
                            totalList.addAll(courseList);
                            adapter.notifyDataSetChanged();
                        } else {
                            Object errMsg = body.getErrMsg();
                            if (errMsg != null) {
                                Toast.makeText(SelectClassifyActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PMyLessonBean> call, Throwable t) {
                String message = t.getMessage();
                // Log.e("myLessonerror",message);
            }
        });

    }


    public Boolean dbIsLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin == true) {
            return true;
        } else {
            return false;
        }
    }


    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
