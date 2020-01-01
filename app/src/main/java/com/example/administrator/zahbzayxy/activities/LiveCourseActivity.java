package com.example.administrator.zahbzayxy.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LiveCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.Lv1CateAdapter;
import com.example.administrator.zahbzayxy.adapters.OnlineCourseAdapter;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.LiveCourseBean;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadListActivity;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.DateUtil;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveCourseActivity extends BaseActivity{

    private TextView back_index_iv;
    private PullToRefreshListView recLv;
    private ProgressBarLayout mLoadingBar;

    private List<LiveCourseBean.DataBean> totalList = new ArrayList<>();

    private static String token;
    LiveCourseAdapter adapter;
    private int pageSize = 10;
    private int pager = 1;
    private String status;

    private TextView lveingTV;
    private TextView lveingyyTV;
    private TextView lveingendTV;
    private static final int LIVECOURSE_SIGN=5;

    private RelativeLayout rl_empty;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_course);
        Utils.setFullScreen(LiveCourseActivity.this,getWindow());
        initView();
        getSP();
        adapter = new LiveCourseAdapter(totalList, LiveCourseActivity.this, token, handler);
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
        showLoadingBar(false);
        IndexInterface aClass = RetrofitUtils.getInstance().createClass(IndexInterface.class);
        aClass.liveCourseList(pager,pageSize,token,status,1).enqueue(new Callback<LiveCourseBean>() {
            @Override
            public void onResponse(Call<LiveCourseBean> call, Response<LiveCourseBean> response) {
                int code1 = response.code();
                LiveCourseBean body = response.body();
                String s = new Gson().toJson(body);
                Log.e("lessonSSss", s);
                if (body != null && body.getData() !=null && body.getData().size() > 0) {
                    String code = body.getCode();
                    if (!TextUtils.isEmpty(code)) {
                        if (code.equals("00003")) {
                            initViewVisible(false);
                            Toast.makeText(LiveCourseActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            initViewVisible(false);
                            Toast.makeText(LiveCourseActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            initViewVisible(false);
                            Toast.makeText(LiveCourseActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            initViewVisible(true);
                            List<LiveCourseBean.DataBean> courseList = body.getData();
                            totalList.addAll(courseList);
                            adapter.notifyDataSetChanged();
                        } else {
                            initViewVisible(false);
                            Object errMsg = body.getErrMsg();
                            if (errMsg != null) {
                                Toast.makeText(LiveCourseActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else{
                    Object errMsg = body.getErrMsg();
                    if (errMsg != null) {
                        Toast.makeText(LiveCourseActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                    }
                    if(totalList==null || totalList.size()==0) {
                        initViewVisible(false);
                    }
                }
                hideLoadingBar();
            }

            @Override
            public void onFailure(Call<LiveCourseBean> call, Throwable t) {
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
    private boolean v1Flag=true;
    private boolean v2Flag=true;
    private boolean v3Flag=true;
    private void initView() {
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.load_bar_layout_course);
        back_index_iv = (TextView) findViewById(R.id.back_index_iv);
        back_index_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recLv = (PullToRefreshListView) findViewById(R.id.recLv);
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty_layout);
        lveingTV= (TextView)findViewById(R.id.lveingTV);
        lveingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(v1Flag){
                      Drawable drawableLeft = getResources().getDrawable(R.mipmap.liveing);
                     lveingTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                     lveingTV.setTextColor(getResources().getColor(R.color.shikan_text_color));
                     v1Flag=false;
                     totalList.clear();
                     status="1";


                     Drawable drawableLeft1 = getResources().getDrawable(R.mipmap.live_yy);
                     lveingyyTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft1, null);
                     lveingyyTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                     v2Flag=true;
                     Drawable drawableLeft2 = getResources().getDrawable(R.mipmap.live_end);
                     lveingendTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft2, null);
                     lveingendTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                     v3Flag=true;

                 }else{
                     Drawable drawableLeft = getResources().getDrawable(R.mipmap.live);
                     lveingTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                     lveingTV.setTextColor(getResources().getColor(R.color.zx_text_color));

                     v1Flag=true;
                     totalList.clear();
                     status=null;
                 }
                 downLoadData(1);
            }
        });
        lveingyyTV= (TextView)findViewById(R.id.lveingyyTV);
        lveingyyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v2Flag){
                    Drawable drawableLeft = getResources().getDrawable(R.mipmap.live_yy_sel);
                    lveingyyTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                    lveingyyTV.setTextColor(getResources().getColor(R.color.shikan_text_color));
                    v2Flag=false;
                    totalList.clear();
                    status="2";

                    Drawable drawableLeft1 = getResources().getDrawable(R.mipmap.live);
                    lveingTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft1, null);
                    lveingTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                    v1Flag=true;
                    Drawable drawableLeft2 = getResources().getDrawable(R.mipmap.live_end);
                    lveingendTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft2, null);
                    lveingendTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                    v3Flag=true;
                }else{
                    Drawable drawableLeft = getResources().getDrawable(R.mipmap.live_yy);
                    lveingyyTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                    lveingyyTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                    v2Flag=true;
                    totalList.clear();
                    status=null;
                }
                downLoadData(1);
            }
        });
        lveingendTV= (TextView)findViewById(R.id.liveingendTV);
        lveingendTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v3Flag){
                    Drawable drawableLeft = getResources().getDrawable(R.mipmap.live_end_sel);
                    lveingendTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                    lveingendTV.setTextColor(getResources().getColor(R.color.shikan_text_color));
                    v3Flag=false;
                    totalList.clear();
                    status="[3,4,5]";

                    Drawable drawableLeft1 = getResources().getDrawable(R.mipmap.live);
                    lveingTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft1, null);
                    lveingTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                    v1Flag=true;
                    Drawable drawableLeft2 = getResources().getDrawable(R.mipmap.live_yy);
                    lveingyyTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft2, null);
                    lveingyyTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                    v2Flag=true;
                }else{
                    Drawable drawableLeft = getResources().getDrawable(R.mipmap.live_end);
                    lveingendTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                    lveingendTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                    v3Flag=true;
                    totalList.clear();
                    status=null;
                }
                downLoadData(1);
            }
        });
    }




    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }



    public void downLoadOnClick(View view) {
        Intent intent = new Intent(LiveCourseActivity.this, DownloadListActivity.class);
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
        upLoadAlertDialog = new AlertDialog.Builder(LiveCourseActivity.this)
                .setTitle("提示")
                .setMessage(R.string.upload_portrait_prompt)
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(LiveCourseActivity.this, EditMessageActivity.class));
                        return;
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        upLoadAlertDialog.show();
    }
    private void initViewVisible(boolean isviable){
        Log.i("===",""+isviable);
        if(isviable){
            recLv.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            recLv.setVisibility(View.GONE);
        }
    }

}
