package com.example.administrator.zahbzayxy.activities;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonSecondGVAdapter;
import com.example.administrator.zahbzayxy.beans.LessonSecondGridViewBean;
import com.example.administrator.zahbzayxy.interfaceserver.LessonGroupInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LessonSecondActivity extends BaseActivity {
    private int pageNo,	pageSize,parentCateId,subCateId;
    PullToRefreshGridView pullToRefreshGridView;
    ImageView back_iv;
    private LessonSecondGVAdapter adapter;
    private List<LessonSecondGridViewBean.DataBean.CourseListBean>totalList=new ArrayList<>();
    private ProgressBarLayout mLoadingBar;
    private TextView lessonName_daoHang_tv;
    private String zCateName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_second);
        initView();
        initBack();
        adapter=new LessonSecondGVAdapter(totalList,LessonSecondActivity.this);
        pullToRefreshGridView.setAdapter(adapter);
        initPulloRefreshGridView();
    }

    private void initPulloRefreshGridView() {
        pullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                totalList.clear();
                pageNo=1;
                downLoadData(pageNo);
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                ++pageNo;
                downLoadData(pageNo);
            }
        });
        downLoadData(pageNo);
    }

    private void initView() {
        mLoadingBar= (ProgressBarLayout) findViewById(R.id.load_bar_layout_evaluating);
        pullToRefreshGridView= (PullToRefreshGridView) findViewById(R.id.lessonSecond_gv);
        back_iv= (ImageView) findViewById(R.id.lessonSecond_back);
        lessonName_daoHang_tv= (TextView) findViewById(R.id.lessonName_daoHang_tv);
        pageNo=1;
        pageSize=10;
         parentCateId = getIntent().getIntExtra("cateId",0);
         subCateId= getIntent().getIntExtra("zCatId",0);
         zCateName = getIntent().getStringExtra("zCateName");
         if (!TextUtils.isEmpty(zCateName)){
             lessonName_daoHang_tv.setText(zCateName);
         }
        Log.e("intentaaaaaaa",parentCateId+","+subCateId);
    }

    private void downLoadData(int pageNo) {
        showLoadingBar(false);
        LessonGroupInterface aClass = RetrofitUtils.getInstance().createClass(LessonGroupInterface.class);
        aClass.getCourseListData(pageNo,pageSize,parentCateId,subCateId).enqueue(new Callback<LessonSecondGridViewBean>() {
            @Override
            public void onResponse(Call<LessonSecondGridViewBean> call, Response<LessonSecondGridViewBean> response) {
                if (response!= null){
                    String s = new Gson().toJson(response);
                    Log.e("aaaalesonsec",s);
                     LessonSecondGridViewBean body = response.body();
                    if (body!=null) {
                          hideLoadingBar();
                          if (body.getCode().equals("99999")){
                              Toast.makeText(LessonSecondActivity.this,"系统异常",Toast.LENGTH_SHORT).show();
                          }
                          if (body.getErrMsg() == null) {
                              List<LessonSecondGridViewBean.DataBean.CourseListBean> courseList = body.getData().getCourseList();
                              if (courseList!=null) {
                                  totalList.addAll(courseList);
                                  adapter.notifyDataSetChanged();
                              }
                          }
                      }
                }
            }

            @Override
            public void onFailure(Call<LessonSecondGridViewBean> call, Throwable t) {

            }
        });
        if (pullToRefreshGridView.isRefreshing()){
            pullToRefreshGridView.onRefreshComplete();
        }
    }
    private void initBack() {
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

}
