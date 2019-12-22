package com.example.administrator.zahbzayxy.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.PMyRecommendAdapter;
import com.example.administrator.zahbzayxy.beans.PMyLessonBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadListActivity;
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

public class RecommendCourseActivity extends BaseActivity{

    private ImageView recommedn_back_iv;
    private PullToRefreshListView recLv;
    private TextView sel_classifyTV;

    private List<PMyLessonBean.DataBean.CourseListBean> totalList = new ArrayList<>();
    private static String token;
    PMyRecommendAdapter adapter;
    private int pageSize = 10;
    private int pager = 1;
    private String dividePrice;
    private RelativeLayout rl_empty;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_course);
        initView();
        getSP();
        adapter = new PMyRecommendAdapter(totalList, RecommendCourseActivity.this, token, handler);
        recLv.setAdapter(adapter);
        initPullToRefreshLv();
    }

    private void initPullToRefreshLv() {

        recLv.setMode(PullToRefreshBase.Mode.BOTH);
        recLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                totalList.clear();
                pager = 1;
                downLoadData(pager);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pager++;
                downLoadData(pager);

            }
        });
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
                            initViewVisible(false);
                            Toast.makeText(RecommendCourseActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            initViewVisible(false);
                            Toast.makeText(RecommendCourseActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();

                        } else if (code.equals("99999")) {
                            initViewVisible(false);
                            Toast.makeText(RecommendCourseActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            initViewVisible(true);
                            dividePrice = body.getData().getDividePrice();
                            adapter.setPrice(dividePrice);
                            List<PMyLessonBean.DataBean.CourseListBean> courseList = body.getData().getCourseList();
                            totalList.addAll(courseList);
                            adapter.notifyDataSetChanged();
                        } else {
                            initViewVisible(false);
                            Object errMsg = body.getErrMsg();
                            if (errMsg != null) {
                                Toast.makeText(RecommendCourseActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else{
                    initViewVisible(false);
                }
            }

            @Override
            public void onFailure(Call<PMyLessonBean> call, Throwable t) {
                initViewVisible(false);
                String message = t.getMessage();
                // Log.e("myLessonerror",message);
            }
        });
        if (recLv.isRefreshing()) {
            //刷新获取数据时候，时间太短，就会出现该问题
            recLv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recLv.onRefreshComplete();
                }
            }, 500);
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

    private void initView() {
        recommedn_back_iv = (ImageView) findViewById(R.id.recommedn_back_iv);
        recLv = (PullToRefreshListView) findViewById(R.id.recLv);
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty_layout);
        sel_classifyTV = (TextView) findViewById(R.id.sel_classify);
        recommedn_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sel_classifyTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecommendCourseActivity.this,SelectClassifyActivity.class));
            }
        });

    }

    public void downLoadOnClick(View view) {
        Intent intent = new Intent(RecommendCourseActivity.this, DownloadListActivity.class);
        startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            gotoVideo(msg);
        }
    };

    /**
     * 画面跳转
     *
     * @param msg
     */
    private void gotoVideo(Message msg) {
        final int mWhat = msg.what;
        if (mWhat == 1) {
            //需要人脸识别且照片未上传
            showUploadDialog();
            return;
        } else if (mWhat == 3) {
            Toast.makeText(this, "认证失败", Toast.LENGTH_SHORT).show();
            return;
        } else if (mWhat == 4) {
            Toast.makeText(this, "连接失败", Toast.LENGTH_SHORT).show();
            return;
        }


        final int userCourseId = msg.getData().getInt("userCourseId", 0);
        final int courseId = msg.getData().getInt("coruseId", 0);
        final String token = msg.getData().getString("token");


    }


    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        if (upLoadAlertDialog != null) {
            upLoadAlertDialog.dismiss();
        }
        super.onDestroy();
    }

    private AlertDialog upLoadAlertDialog;

    private void showUploadDialog() {

        if (upLoadAlertDialog != null) {
            upLoadAlertDialog.dismiss();
        }
        upLoadAlertDialog = new AlertDialog.Builder(RecommendCourseActivity.this)
                .setTitle("提示")
                .setMessage(R.string.upload_portrait_prompt)
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(RecommendCourseActivity.this, EditMessageActivity.class));
                        return;
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        upLoadAlertDialog.show();
    }
    private void initViewVisible(boolean isviable){
        if(isviable){
            recLv.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            recLv.setVisibility(View.GONE);
        }
    }
}
