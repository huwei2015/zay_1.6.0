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
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.Lv1CateAdapter;
import com.example.administrator.zahbzayxy.adapters.QueslibAdapter;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.QueslibBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadListActivity;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ScreenUtil;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 试用题库
 */
public class RecommendQueslibActivity extends BaseActivity{

    private TextView recommedn_back_iv;
    private PullToRefreshListView recLv;
    private TextView sel_classifyTV;
    private ProgressBarLayout mLoadingBar;

    private List<QueslibBean.DataBean.QueslibListBean> totalList = new ArrayList<>();

    private static String token;
    QueslibAdapter adapter;
    private int pageSize = 10;
    private int pager = 1;
    private Integer s_cateId=0;
    private Integer isTrailers;
    private Integer isNew;
    private TextView zuixinTV;
    private TextView isrecmmendTV;
    private TextView shikanTV;
    private static final int QUESLIB_SIGN=6;

    private RelativeLayout rl_empty;

    private ImageView back_top;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_queslib);
        Utils.setFullScreen(RecommendQueslibActivity.this,getWindow());
        initView();
        getSP();
        adapter = new QueslibAdapter(totalList, RecommendQueslibActivity.this, token, handler);
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
        aClass.queslibList(pager,pageSize,token,s_cateId==0?null:s_cateId,1, isTrailers,isNew,1).enqueue(new Callback<QueslibBean>() {
            @Override
            public void onResponse(Call<QueslibBean> call, Response<QueslibBean> response) {
                int code1 = response.code();
                QueslibBean body = response.body();
                String s = new Gson().toJson(body);
                Log.e("data", s);
                if (body != null && body.getData().getQueslibList().size() > 0) {
                    String code = body.getCode();
                    if (!TextUtils.isEmpty(code)) {
                        if (code.equals("00003")) {
                            initViewVisible(false);
                            Toast.makeText(RecommendQueslibActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            initViewVisible(false);
                            Toast.makeText(RecommendQueslibActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            initViewVisible(false);
                            Toast.makeText(RecommendQueslibActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            initViewVisible(true);
                            List<QueslibBean.DataBean.QueslibListBean> courseList = body.getData().getQueslibList();
                            totalList.addAll(courseList);
                            adapter.notifyDataSetChanged();
                        } else {
                            initViewVisible(false);
                            Object errMsg = body.getErrMsg();
                            if (errMsg != null) {
                                Toast.makeText(RecommendQueslibActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<QueslibBean> call, Throwable t) {
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
    private boolean zxFlag=true;
    private boolean tjFlag=true;
    private boolean skFlag=true;
    private void initView() {
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.load_bar_layout_course);
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
                Intent intent = new Intent(RecommendQueslibActivity.this, SelectClassifyActivity.class);
                intent.putExtra("cateType", "queslib_cate");
                intent.putExtra("s_cateId", s_cateId);
                startActivityForResult(intent,QUESLIB_SIGN);
            }
        });

        isrecmmendTV=(TextView)findViewById(R.id.recommendTV);
        Drawable drawableLeft = getResources().getDrawable(R.mipmap.tuijian_sel);
        isrecmmendTV.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
        isrecmmendTV.setTextColor(getResources().getColor(R.color.shikan_text_color));

        shikanTV=(TextView)findViewById(R.id.shikanTV);
        shikanTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(skFlag) {
                    Drawable drawableLeft = getResources().getDrawable(
                            R.mipmap.play_icon);
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.shikan_text_color));
                    skFlag=false;
                    totalList.clear();
                    isTrailers=1;

                    Drawable drawableLeft2 = getResources().getDrawable(
                            R.mipmap.jt_down);
                    zuixinTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft2, null);
                    zuixinTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                    zxFlag=true;
                    isNew=null;
                }else{
                    Drawable drawableLeft = getResources().getDrawable(
                            R.mipmap.play_icon_nosel);
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
                    ((TextView) v).setTextColor(getResources().getColor(R.color.zx_text_color));
                    skFlag=true;
                    totalList.clear();
                    isTrailers=null;
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

                    Drawable drawableLeft1 = getResources().getDrawable(
                            R.mipmap.play_icon_nosel);
                    shikanTV.setCompoundDrawablesWithIntrinsicBounds(drawableLeft1, null, null, null);
                    shikanTV.setTextColor(getResources().getColor(R.color.zx_text_color));
                    skFlag=true;
                    isTrailers=null;
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


        //返回顶部
        back_top=(ImageView) findViewById(R.id.back_top);
        back_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListViewPos(0);
            }
        });

        recLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
                        scrollFlag = false;
                        // 判断滚动到底部
                        if (recLv.getRefreshableView().getLastVisiblePosition() == (recLv.getRefreshableView().getCount() - 1)) {
                            back_top.setVisibility(View.VISIBLE);
                        }
                        // 判断滚动到顶部
                        if (recLv.getRefreshableView().getFirstVisiblePosition() == 0) {
                            back_top.setVisibility(View.GONE);
                        }

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
                        scrollFlag = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时
                        scrollFlag = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (scrollFlag
                        && ScreenUtil.getScreenViewBottomHeight(recLv.getRefreshableView()) >= ScreenUtil
                        .getScreenHeight(RecommendQueslibActivity.this)) {
                    if (firstVisibleItem > lastVisibleItemPosition) {// 上滑
                        back_top.setVisibility(View.VISIBLE);
                    } else if (firstVisibleItem < lastVisibleItemPosition) {// 下滑
                        back_top.setVisibility(View.GONE);
                    } else {
                        return;
                    }
                    lastVisibleItemPosition = firstVisibleItem;
                }
            }
        });
    }

    /**
     * 滚动ListView到指定位置
     *
     * @param pos
     */
    private void setListViewPos(int pos) {
        if (android.os.Build.VERSION.SDK_INT >= 8) {
            recLv.getRefreshableView().smoothScrollToPosition(pos);
        } else {
            recLv.getRefreshableView().setSelection(pos);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case QUESLIB_SIGN :
                if (resultCode == Activity.RESULT_OK) {
                    s_cateId = data.getIntExtra("cateId",0);
                    totalList.clear();
                    downLoadData(1);
                }
                break;
            default:break;
        }
    }
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

    public void downLoadOnClick(View view) {
        Intent intent = new Intent(RecommendQueslibActivity.this, DownloadListActivity.class);
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
        upLoadAlertDialog = new AlertDialog.Builder(RecommendQueslibActivity.this)
                .setTitle("提示")
                .setMessage(R.string.upload_portrait_prompt)
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(RecommendQueslibActivity.this, EditMessageActivity.class));
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