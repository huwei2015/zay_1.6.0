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
import android.util.TypedValue;
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
import com.example.administrator.zahbzayxy.adapters.Lv1CateAdapter;
import com.example.administrator.zahbzayxy.adapters.PMyRecommendAdapter;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.PMyLessonBean;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.myviews.CateTextView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.TextAndPictureUtil;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectClassifyActivity  extends BaseActivity implements ListClassifyAdapter.OnClickListener{

	private LinearLayout layout;
	private LinearLayout all_classify_layout;
    private List<CourseCatesBean.DataBean.Cates> totalList = new ArrayList<>();
    private static String token;
    ListClassifyAdapter adapter;
    private TextView allClassify;

    private ListView classifyLv;
    private Integer cateId;
    private String cateType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_classify);
        Utils.setFullScreen(SelectClassifyActivity.this,getWindow());
        cateId = getIntent().getIntExtra("cateId",0);
        cateType = getIntent().getStringExtra("cateType");
        initView();
        getSP();
        Integer level=3;
        if(cateId>0){
            all_classify_layout.setVisibility(View.GONE);
            level=2;
        }

        adapter = new ListClassifyAdapter(totalList, SelectClassifyActivity.this, token,level);
        classifyLv.setAdapter(adapter);
        downLoadData();
		layout = (LinearLayout) findViewById(R.id.pop_layout);

        // 添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});
    }

    private void initView() {
        classifyLv=(ListView) findViewById(R.id.classifyLv);
        all_classify_layout=(LinearLayout) findViewById(R.id.all_classify_layout);
        allClassify=(TextView) findViewById(R.id.allClassify);
        allClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("cateId",0);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


    private void getSP() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        Log.e("danWeiToken", token);
    }

    private void downLoadData() {
        IndexInterface aClass = RetrofitUtils.getInstance().createClass(IndexInterface.class);
        if("book_cate".equals(cateType)){
            aClass.getBookCates(token).enqueue(new Callback<CourseCatesBean>() {
                @Override
                public void onResponse(Call<CourseCatesBean> call, Response<CourseCatesBean> response) {
                    getCates(response);
                }
                @Override
                public void onFailure(Call<CourseCatesBean> call, Throwable t) {
                    String message = t.getMessage();
                }
            });
        }else if("online_cate".equals(cateType)){
            aClass.getCourseCates(token).enqueue(new Callback<CourseCatesBean>() {
                @Override
                public void onResponse(Call<CourseCatesBean> call, Response<CourseCatesBean> response) {
                    getCates(response);
                }
                @Override
                public void onFailure(Call<CourseCatesBean> call, Throwable t) {
                    String message = t.getMessage();
                }
            });
        }else if("offline_cate".equals(cateType)){
            aClass.getOfflineCourseCates(token).enqueue(new Callback<CourseCatesBean>() {
                @Override
                public void onResponse(Call<CourseCatesBean> call, Response<CourseCatesBean> response) {
                    getCates(response);
                }
                @Override
                public void onFailure(Call<CourseCatesBean> call, Throwable t) {
                    String message = t.getMessage();
                }
            });
        }else if("queslib_cate".equals(cateType)){
            aClass.getQueslibCates(token).enqueue(new Callback<CourseCatesBean>() {
                @Override
                public void onResponse(Call<CourseCatesBean> call, Response<CourseCatesBean> response) {
                    getCates(response);
                }
                @Override
                public void onFailure(Call<CourseCatesBean> call, Throwable t) {
                    String message = t.getMessage();
                }
            });
        }else if("live_cate".equals(cateType)){
            aClass.getCourseCates(token).enqueue(new Callback<CourseCatesBean>() {
                @Override
                public void onResponse(Call<CourseCatesBean> call, Response<CourseCatesBean> response) {
                    getCates(response);
                }
                @Override
                public void onFailure(Call<CourseCatesBean> call, Throwable t) {
                    String message = t.getMessage();
                }
            });
        }
    }


    public void getCates(Response<CourseCatesBean> response){
        int code1 = response.code();
        CourseCatesBean body = response.body();
        String s = new Gson().toJson(body);
        if (body != null && body.getData().getCates().size() > 0) {
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
                    List<CourseCatesBean.DataBean.Cates> courseList = body.getData().getCates();
                    CourseCatesBean.DataBean.Cates cates=new CourseCatesBean.DataBean.Cates();
                    cates.setId(-1);
                    cates.setCateName("全部");
                    List<CourseCatesBean.DataBean.Cates> childs=new ArrayList<CourseCatesBean.DataBean.Cates>();
                    CourseCatesBean.DataBean.Cates child=new  CourseCatesBean.DataBean.Cates();
                    child.setId(-2);
                    child.setCateName("全部课程");
                    child.setChilds(new ArrayList<CourseCatesBean.DataBean.Cates>());
                    childs.add(child);
                    cates.setChilds(childs);
                    totalList.add(cates);
                    if(cateId>0){
                        for(CourseCatesBean.DataBean.Cates ct:courseList){
                            if(ct.getId()==cateId){
                                totalList.addAll(ct.getChilds());
                            }
                        }
                    }else {
                        totalList.addAll(courseList);
                    }
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
    public void setSelectedNum(int num) {
        cateId=num;
        Log.i("===============",cateId+"=====");
        Intent intent = new Intent();
        intent.putExtra("cateId",cateId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
