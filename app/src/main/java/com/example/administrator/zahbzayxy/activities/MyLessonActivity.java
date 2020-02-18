package com.example.administrator.zahbzayxy.activities;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
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
import com.example.administrator.zahbzayxy.adapters.PMyLessonAdapter;
import com.example.administrator.zahbzayxy.beans.PMyLessonBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadListActivity;
import com.example.administrator.zahbzayxy.ccvideo.MediaPlayActivity;
import com.example.administrator.zahbzayxy.fragments.ChooseNoThroughFragment;
import com.example.administrator.zahbzayxy.fragments.ChooseThroughFragment;
import com.example.administrator.zahbzayxy.fragments.MyCouseFragment;
import com.example.administrator.zahbzayxy.interfacecommit.PersonGroupInterfac;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.FaceRecognitionUtils;
import com.example.administrator.zahbzayxy.utils.NetworkUtils;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyLessonActivity extends BaseActivity implements  MyCouseFragment.OnParamsClickListener{

    private ImageView back_pMyLesson_iv;
    private PullToRefreshListView pMyLesson_plv;
    private List<PMyLessonBean.DataBean.CourseListBean> totalList = new ArrayList<>();
    private static String token;
    PMyLessonAdapter adapter;
    private int pageSize = 10;
    private int pager = 1;
    private String dividePrice;
    private TextView tv_msg;
    private RelativeLayout rl_empty;

    private RelativeLayout content_mycourse;
    MyCouseFragment myCouseFragment;
    private FragmentManager manager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lesson);
        initView();
        getSP();
        //添加item监听
//        adapter=new PMyLessonAdapter(totalList,MyLessonActivity.this,token);
//        adapter = new PMyLessonAdapter(totalList, MyLessonActivity.this, token, handler);
//        pMyLesson_plv.setAdapter(adapter);
//        initPullToRefreshLv();
    }

    private void initPullToRefreshLv() {

//        pMyLesson_plv.setMode(PullToRefreshBase.Mode.BOTH);
        pMyLesson_plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
                            Toast.makeText(MyLessonActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            initViewVisible(false);
                            Toast.makeText(MyLessonActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();

                        } else if (code.equals("99999")) {
                            initViewVisible(false);
                            Toast.makeText(MyLessonActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(MyLessonActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
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
        if (pMyLesson_plv.isRefreshing()) {
            //刷新获取数据时候，时间太短，就会出现该问题
            pMyLesson_plv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pMyLesson_plv.onRefreshComplete();
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
        back_pMyLesson_iv = (ImageView) findViewById(R.id.pLesson_back_iv);
        pMyLesson_plv = (PullToRefreshListView) findViewById(R.id.pMyLesson_plv);
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty_layout);
        tv_msg= (TextView) findViewById(R.id.tv_msg);
        back_pMyLesson_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        manager = getSupportFragmentManager();
        myCouseFragment = new MyCouseFragment();
        manager.beginTransaction().add(R.id.content_mycourse, myCouseFragment).show(myCouseFragment).commit();
        myCouseFragment.setOnParamsClickListener(this);
    }

    public void downLoadOnClick(View view) {
        Intent intent = new Intent(MyLessonActivity.this, DownloadListActivity.class);
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

        //wifi相关内容
        //先检查一下设置提醒按钮wifi开关是否打开,
        SharedPreferences wifiDb = getSharedPreferences("wifiDb", MODE_PRIVATE);
        boolean wifiSwitch = wifiDb.getBoolean("WifiSwitch", false);
        if (wifiSwitch == true) {//如果打开时要检查当前手机的WiFi是否打开和可用，如果不可用或者没打开要提示一下
            boolean wifiEnabled = NetworkUtils.getWifiEnabled(this);
            if (wifiEnabled == true) {
                if (mWhat == 0) {
                    gotoMediaPlayActivity(userCourseId, courseId, token);
                } else if (mWhat == 2) {
                    gotoFaceRecognitionActivity(userCourseId, courseId, token);
                }
            } else if (wifiEnabled == false) {
                new AlertDialog.Builder(this).setTitle("播放提示框").setMessage("当前为非WiFi环境，确定播放？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mWhat == 0) {
                                    gotoMediaPlayActivity(userCourseId, courseId, token);
                                } else if (mWhat == 2) {
                                    gotoFaceRecognitionActivity(userCourseId, courseId, token);
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        } else {
            if (mWhat == 0) {
                gotoMediaPlayActivity(userCourseId, courseId, token);
            } else if (mWhat == 2) {
                gotoFaceRecognitionActivity(userCourseId, courseId, token);
            }
        }
    }

    /**
     * 前往视频播放页面
     *
     * @param userCourseId
     * @param coruseId
     * @param token
     */
    private void gotoMediaPlayActivity(int userCourseId, int coruseId, String token) {
        Intent intent = new Intent(this, MediaPlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", coruseId);
        bundle.putString("token", token);
        bundle.putBoolean("isLocalPlay", false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 前往人脸识别页面
     *
     * @param userCourseId
     * @param coruseId
     * @param token
     */
    private void gotoFaceRecognitionActivity(int userCourseId, int coruseId, String token) {
        //初始化人脸识别SDK
        FaceRecognitionUtils.initContrastFaceRecognition(this);
        Intent intent = new Intent(this, FaceRecognitionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userCourseId", userCourseId);
        bundle.putInt("coruseId", coruseId);
        bundle.putString("token", token);
        bundle.putBoolean("isLocalPlay", false);
        bundle.putInt("rootIn", 1);
        intent.putExtras(bundle);
        startActivity(intent);
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
        upLoadAlertDialog = new AlertDialog.Builder(MyLessonActivity.this)
                .setTitle("提示")
                .setMessage(R.string.upload_portrait_prompt)
                .setNegativeButton(R.string.btn_go_to_upload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(MyLessonActivity.this, EditMessageActivity.class));
                        return;
                    }
                }).create();
        upLoadAlertDialog.setCanceledOnTouchOutside(false);
        upLoadAlertDialog.show();
    }
    private void initViewVisible(boolean isviable){
        if(isviable){
            pMyLesson_plv.setVisibility(View.VISIBLE);
            rl_empty.setVisibility(View.GONE);
        }else{
            rl_empty.setVisibility(View.VISIBLE);
            pMyLesson_plv.setVisibility(View.GONE);
            tv_msg.setText("暂无课程信息");
        }
    }

    @Override
    public void onClick() {
        finish();
    }
}
