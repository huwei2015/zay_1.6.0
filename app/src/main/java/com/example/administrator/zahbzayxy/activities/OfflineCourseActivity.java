package com.example.administrator.zahbzayxy.activities;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.Lv1CateAdapter;
import com.example.administrator.zahbzayxy.adapters.OfflineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.OnlineCourseAdapter;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.OfflineCourseBean;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadListActivity;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
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

public class OfflineCourseActivity extends BaseActivity implements Lv1CateAdapter.OnClickListener{

    private TextView recommedn_back_iv;
    private PullToRefreshListView recLv;
    private TextView sel_classifyTV;
    private ProgressBarLayout mLoadingBar;
    private RecyclerView gundongRV;

    private List<OfflineCourseBean.DataBean.CourseListBean> totalList = new ArrayList<>();
    private List<CourseCatesBean.DataBean.Cates> catesList = new ArrayList<>();

    private static String token;
    OfflineCourseAdapter adapter;
    Lv1CateAdapter cateAdapter;
    private int pageSize = 10;
    private int pager = 1;
    private Integer cateId=null;
    private Integer isRecommend;
    private Integer isTrailers;
    private Integer isNew;
    private TextView zuixinTV;
    private TextView isrecmmendTV;


    private RelativeLayout rl_empty;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_course);
        Utils.setFullScreen(OfflineCourseActivity.this,getWindow());
        initView();
        getSP();
        adapter = new OfflineCourseAdapter(totalList, OfflineCourseActivity.this, token, handler);
        recLv.setAdapter(adapter);
        initPullToRefreshLv();
        LinearLayoutManager ms= new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        gundongRV.setLayoutManager(ms); //给RecyClerView 添加设置好的布局样式

        cateAdapter=new Lv1CateAdapter(catesList, OfflineCourseActivity.this,gundongRV);//初始化适配器
        gundongRV.setAdapter(cateAdapter); // 对 recyclerview 添加数据内容
        downLoadCatesData();
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
        aClass.offlineCourseList(1,10,token,cateId,isRecommend, isTrailers,isNew,1).enqueue(new Callback<OfflineCourseBean>() {
            @Override
            public void onResponse(Call<OfflineCourseBean> call, Response<OfflineCourseBean> response) {
                int code1 = response.code();
                OfflineCourseBean body = response.body();
                String s = new Gson().toJson(body);
                Log.e("lessonSSss", s);
                if (body != null && body.getData().getCourseList().size() > 0) {
                    String code = body.getCode();
                    if (!TextUtils.isEmpty(code)) {
                        if (code.equals("00003")) {
                            initViewVisible(false);
                            Toast.makeText(OfflineCourseActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            initViewVisible(false);
                            Toast.makeText(OfflineCourseActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            initViewVisible(false);
                            Toast.makeText(OfflineCourseActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            initViewVisible(true);
                            List<OfflineCourseBean.DataBean.CourseListBean> courseList = body.getData().getCourseList();
                            totalList.addAll(courseList);
                            adapter.notifyDataSetChanged();
                        } else {
                            initViewVisible(false);
                            Object errMsg = body.getErrMsg();
                            if (errMsg != null) {
                                Toast.makeText(OfflineCourseActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else{
                    if(totalList==null || totalList.size()==0) {
                        initViewVisible(false);
                    }
                }
                hideLoadingBar();
            }

            @Override
            public void onFailure(Call<OfflineCourseBean> call, Throwable t) {
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

    private void downLoadCatesData() {
        IndexInterface aClass = RetrofitUtils.getInstance().createClass(IndexInterface.class);
        aClass.getOfflineCourseCates(token).enqueue(new Callback<CourseCatesBean>() {
            @Override
            public void onResponse(Call<CourseCatesBean> call, Response<CourseCatesBean> response) {
                int code1 = response.code();
                CourseCatesBean body = response.body();
                String s = new Gson().toJson(body);
                Log.e("lessonSSss", s);
                if (body != null && body.getData().getCates().size() > 0) {
                    String code = body.getCode();
                    if (!TextUtils.isEmpty(code)) {
                        if (code.equals("00003")) {
                            Toast.makeText(OfflineCourseActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            Toast.makeText(OfflineCourseActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            Toast.makeText(OfflineCourseActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            List<CourseCatesBean.DataBean.Cates> cates = body.getData().getCates();
                            catesList.addAll(cates);
                            cateAdapter.notifyDataSetChanged();
                        } else {
                            Object errMsg = body.getErrMsg();
                            if (errMsg != null) {
                                Toast.makeText(OfflineCourseActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CourseCatesBean> call, Throwable t) {
                String message = t.getMessage();
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
    private boolean zxFlag=true;
    private boolean tjFlag=true;
    private boolean skFlag=true;
    private void initView() {
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.load_bar_layout_course);
        gundongRV = (RecyclerView) findViewById(R.id.gundongRV);
        recommedn_back_iv = (TextView) findViewById(R.id.recommedn_back_iv);
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
                Intent intent = new Intent(OfflineCourseActivity.this, SelectClassifyActivity.class);
                intent.putExtra("cateId", cateId);
                startActivity(intent);
            }
        });

        isrecmmendTV=(TextView)findViewById(R.id.recommendTV);
        isrecmmendTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tjFlag) {
                    Drawable drawableLeft = getResources().getDrawable(
                            R.mipmap.tuijian_sel);
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.shikan_text_color));
                    tjFlag=false;
                    totalList.clear();
                    isRecommend=1;
                }else{
                    Drawable drawableLeft = getResources().getDrawable(
                            R.mipmap.tuijian);
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.zx_text_color));
                    tjFlag=true;
                    totalList.clear();
                    isRecommend=null;
                }
                downLoadData(1);
            }
        });
        zuixinTV=(TextView) findViewById(R.id.zuixinTV);
        zuixinTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zxFlag) {
                    Drawable drawableLeft = getResources().getDrawable(
                            R.mipmap.jt_down_sel);
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                    //((TextView) v).setCompoundDrawablePadding(4);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.shikan_text_color));
                    zxFlag=false;
                    totalList.clear();
                    isNew=1;
                }else{
                    Drawable drawableLeft = getResources().getDrawable(
                            R.mipmap.jt_down);
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
                    //((TextView) v).setCompoundDrawablePadding(4);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.zx_text_color));
                    zxFlag=true;
                    totalList.clear();
                    isNew=null;
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
        Intent intent = new Intent(OfflineCourseActivity.this, DownloadListActivity.class);
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
        upLoadAlertDialog = new AlertDialog.Builder(OfflineCourseActivity.this)
                .setTitle("提示")
                .setMessage(R.string.upload_portrait_prompt)
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(OfflineCourseActivity.this, EditMessageActivity.class));
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

    @Override
    public void setSelectedNum(int id) {
         cateId=id;
         totalList.clear();
         downLoadData(1);
    }
}
