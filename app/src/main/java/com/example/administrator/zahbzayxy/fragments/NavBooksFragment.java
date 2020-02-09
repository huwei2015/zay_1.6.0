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
import com.example.administrator.zahbzayxy.activities.BooksActivity;
import com.example.administrator.zahbzayxy.activities.SelectClassifyActivity;
import com.example.administrator.zahbzayxy.adapters.BooksAdapter;
import com.example.administrator.zahbzayxy.adapters.Lv1CateAdapter;
import com.example.administrator.zahbzayxy.adapters.OnlineCourseAdapter;
import com.example.administrator.zahbzayxy.beans.AllOnlineCourseBean;
import com.example.administrator.zahbzayxy.beans.BookBean;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ScreenUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * HYY 在线课
 */
public class NavBooksFragment extends Fragment{
    private View view;
    private ProgressBarLayout mLoadingBar;
    private Context mContext;

    private TextView recommedn_back_iv;
    private PullToRefreshListView recLv;
    private RecyclerView gundongRV;
    private TextView sel_classifyTV;

    private List<BookBean.DataBean.BookListBean> totalList = new ArrayList<>();
    private List<CourseCatesBean.DataBean.Cates> catesList = new ArrayList<>();

    private static String token;
    BooksAdapter adapter;
    Lv1CateAdapter cateAdapter;
    private int pageSize = 10;
    private int pager = 1;
    private Integer cateId=0;
    private Integer s_cateId=0;
    private Integer isNew;
    private TextView zuixinTV;
    private static final int BOOK_SIGN=7;

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
        view=inflater.inflate(R.layout.activity_books,container,false);
        initView();
        getSP();
        isInit=true;
        return view;
    }

    public void getData(){
        adapter = new BooksAdapter(totalList, mContext, token);
        recLv.setAdapter(adapter);
        initPullToRefreshLv();
        LinearLayoutManager ms= new LinearLayoutManager(mContext);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        gundongRV.setLayoutManager(ms); //给RecyClerView 添加设置好的布局样式

        cateAdapter=new Lv1CateAdapter(catesList, mContext,gundongRV,1,cateId);//初始化适配器
        gundongRV.setAdapter(cateAdapter); // 对 recyclerview 添加数据内容
        downLoadCatesData();
        cateAdapter.setOnClickListener(new Lv1CateAdapter.OnClickListener() {
            @Override
            public void setSelectedNum(int num) {
                cateId=num;
                s_cateId=num;
                totalList.clear();
                downLoadData(1);
            }
        });
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
        aClass.bookList(pager,pageSize,token,s_cateId==0?null:s_cateId,isNew,1).enqueue(new Callback<BookBean>() {
            @Override
            public void onResponse(Call<BookBean> call, Response<BookBean> response) {
                int code1 = response.code();
                BookBean body = response.body();
                String s = new Gson().toJson(body);
                Log.e("lessonSSss", s);
                if (body != null && body.getData().getBookList().size() > 0) {
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
                            List<BookBean.DataBean.BookListBean> bookList = body.getData().getBookList();
                            totalList.addAll(bookList);
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
                    if(totalList==null || totalList.size()==0) {
                        initViewVisible(false);
                    }
                }
                hideLoadingBar();
            }

            @Override
            public void onFailure(Call<BookBean> call, Throwable t) {
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
        aClass.getBookCates(token).enqueue(new Callback<CourseCatesBean>() {
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
                            Toast.makeText(mContext, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            Toast.makeText(mContext, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            Toast.makeText(mContext, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            List<CourseCatesBean.DataBean.Cates> cates = body.getData().getCates();
                            catesList.addAll(cates);
                            cateAdapter.notifyDataSetChanged();
                        } else {
                            Object errMsg = body.getErrMsg();
                            if (errMsg != null) {
                                Toast.makeText(mContext, "" + errMsg, Toast.LENGTH_SHORT).show();
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
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean zxFlag=true;
    private void initView() {
        mLoadingBar= view.findViewById(R.id.load_bar_layout_books);
        gundongRV = view.findViewById(R.id.gundongRV);
        recommedn_back_iv = view.findViewById(R.id.recommedn_back_iv);
        recLv = view.findViewById(R.id.recLv);
        rl_empty = view.findViewById(R.id.rl_empty_layout);
        rl_empty.setVisibility(View.GONE);
        top_layout=view.findViewById(R.id.top_layout);
        top_layout.setVisibility(View.GONE);

        zuixinTV=view.findViewById(R.id.zuixinTV);
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

        sel_classifyTV = view.findViewById(R.id.sel_classify);
        sel_classifyTV.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(cateId!=null && cateId!=0){
                    Intent intent = new Intent(mContext, SelectClassifyActivity.class);
                    intent.putExtra("cateId", cateId);
                    intent.putExtra("cateType", "book_cate");
                    intent.putExtra("s_cateId", s_cateId);
                    startActivityForResult(intent,BOOK_SIGN);
                }else{
                    Toast.makeText(mContext, R.string.ctrl_tips, Toast.LENGTH_SHORT).show();
                }
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case BOOK_SIGN :
                if (resultCode == Activity.RESULT_OK) {
                    s_cateId = data.getIntExtra("cateId",0);
                    totalList.clear();
                    downLoadData(1);
                }
                break;
            default:break;
        }
    }

    private static boolean isInit=false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser  && isInit){
            totalList.clear();
            catesList.clear();
            cateId=0;
            s_cateId=0;
            isNew=null;
            getData();
        }else{
            isInit = false;
        }
    }
}
