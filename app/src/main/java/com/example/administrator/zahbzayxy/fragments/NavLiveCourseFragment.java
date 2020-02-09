package com.example.administrator.zahbzayxy.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.LiveCourseActivity;
import com.example.administrator.zahbzayxy.activities.SelectClassifyActivity;
import com.example.administrator.zahbzayxy.adapters.LiveCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.Lv1CateAdapter;
import com.example.administrator.zahbzayxy.adapters.OfflineCourseAdapter;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.LiveCourseBean;
import com.example.administrator.zahbzayxy.beans.OfflineCourseBean;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
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
 * HYY 直播课
 */
public class NavLiveCourseFragment extends Fragment{
    private View view;
    private ProgressBarLayout mLoadingBar;
    private Context mContext;

    private TextView back_index_iv;
    private PullToRefreshListView recLv;

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

    private ImageView back_top;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置

    private RelativeLayout top_layout;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_live_course,container,false);
        initView();
        getSP();
        isInit=true;//设置已经
        return view;
    }

    public void getData(){
        adapter = new LiveCourseAdapter(totalList, mContext, token);
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
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        Log.e("danWeiToken", token);
    }

    private void downLoadData(int pager) {
        showLoadingBar(true);
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
                            Toast.makeText(mContext, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            initViewVisible(false);
                            Toast.makeText(mContext, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            initViewVisible(false);
                            Toast.makeText(mContext, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            initViewVisible(true);
                            List<LiveCourseBean.DataBean> courseList = body.getData();
                            totalList.addAll(courseList);
                            adapter.notifyDataSetChanged();
                        } else {
                            initViewVisible(false);
                            Object errMsg = body.getErrMsg();
                            if (errMsg != null) {
                                Toast.makeText(mContext, "" + errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else{
                    Object errMsg = body.getErrMsg();
                    if (errMsg != null) {
                        Toast.makeText(mContext, "" + errMsg, Toast.LENGTH_SHORT).show();
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
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
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
        mLoadingBar= view. findViewById(R.id.load_bar_layout_live);
        back_index_iv = view.findViewById(R.id.back_index_iv);
        recLv = view.findViewById(R.id.recLv);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        top_layout=view.findViewById(R.id.top_layout);
        top_layout.setVisibility(View.GONE);
        //直播中
        lveingTV= view.findViewById(R.id.lveingTV);
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


        //预约直播
        lveingyyTV= view.findViewById(R.id.lveingyyTV);
        if("2".equals(status)){
            Drawable drawableLeft = getResources().getDrawable(R.mipmap.live_yy_sel);
            lveingyyTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
            lveingyyTV.setTextColor(getResources().getColor(R.color.shikan_text_color));
            v2Flag=false;
        }
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
        //直播结束
        lveingendTV= view.findViewById(R.id.liveingendTV);
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


        //返回顶部
        back_top=view.findViewById(R.id.back_top);
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
                        .getScreenHeight(mContext)) {
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

        if(status!=null && "[3,4,5]".equals(status)){
            Drawable drawableLeft = getResources().getDrawable(R.mipmap.live_end_sel);
            lveingendTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
            lveingendTV.setTextColor(getResources().getColor(R.color.shikan_text_color));
            v3Flag=false;
        }

        if(status!=null && "1".equals(status)){
            Drawable drawableLeft = getResources().getDrawable(R.mipmap.liveing);
            lveingTV.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableLeft, null);
            lveingTV.setTextColor(getResources().getColor(R.color.shikan_text_color));
            v1Flag=false;
        }
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

    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
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
    public void onResume() {
        super.onResume();
        onCreate(null);
    }

    private static boolean isInit=false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser  && isInit){
            totalList.clear();
            getData();
        }else{
            isInit = false;
        }
    }

}
