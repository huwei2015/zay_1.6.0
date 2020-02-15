package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.TestResultAdapter;
import com.example.administrator.zahbzayxy.beans.GridItem;
import com.example.administrator.zahbzayxy.beans.TestGradAnalyseBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.stickheadgv.StickyGridHeadersGridView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.CircleProgress;
import com.example.administrator.zahbzayxy.utils.DateUtil;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//试卷结果分析页
public class ResultActivity extends BaseActivity {
    StickyGridHeadersGridView resultGridView;
    TextView useTime_tv, suggest_tv;
    private List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> list = new ArrayList<>();
    TestResultAdapter adapter;
    private String useTime;
    private String quesLibName;
    private int arightCent;
    private ImageView test_result_return_iv;
    private CircleProgress error_cv, right_cv, rightPercent_cv;
    private TextView defeatNum_tv, ranking_tv, totalScore_tv, doDate_tv, testAllGrade_tv;
    private String currentTime;
    private double examScoreId;
    private String token;
    private List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> examDetails;
    private int totalScore;
    private int ranking;
    private int quesLibId;
    private String paperName;
    private String examBeginTime;
    private int defeatNum;
    private int correctRate;
    private String assignTime;
    private float rightNum;
    private float rongNum;
    PopupWindow popUpWindow1;
    private int userLibId;
    private String shareUrl;
    private int spareTime;
    private int overdueTime;
    private int isShowPaper;
    PopupWindow window;
    View popView;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
        initDownLoadData();
        initFinish();
    }

    private void initDownLoadData() {

        Integer integer = Integer.valueOf((int) examScoreId);
        Log.e("examScoreId", integer + "");
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestAnalyslisPath(integer, token).enqueue(new Callback<TestGradAnalyseBean>() {
            @Override
            public void onResponse(Call<TestGradAnalyseBean> call, Response<TestGradAnalyseBean> response) {
                int code = response.code();
                TestGradAnalyseBean body1 = response.body();
                String s = new Gson().toJson(body1);
                Log.e("listResult", s);
                if (code == 200) {
                    TestGradAnalyseBean body = response.body();
                    if (body != null) {
                        String errMsg = body.getErrMsg();
                        if (errMsg == null) {
                            String code1 = body.getCode();
                            if (code1.equals("00000")) {
                                TestGradAnalyseBean.DataEntity data = body.getData();
                                shareUrl = data.getShareUrl();
                                assignTime = data.getAssignTime();
                                defeatNum = data.getDefeatNum();
                                examBeginTime = data.getExamBeginTime();
                                paperName = data.getPaperName();
                                quesLibId = data.getQuesLibId();
                                userLibId = data.getUserLibId();
                                ranking = data.getRanking();
                                totalScore = (int) data.getTotalScore();
                                int correctRate = data.getCorrectRate();
                                examDetails = data.getExamDetails();
                                spareTime = data.getSpareTime();
                                overdueTime = (int) data.getRemainingTime();
                                Log.e("spareTime", spareTime + ",1111" + totalScore);
                                isShowPaper = data.getIsShowPaper();
                                String examBeginTime = data.getExamBeginTime();
                                String assignTime = data.getAssignTime();

                                String time = getTime(examBeginTime, assignTime);
                                useTime_tv.setText(time + "");

//                                if (!TextUtils.isEmpty(useTime)) {
//                                    useTime_tv.setText(useTime);
//                                }
                                if (correctRate > 60) {
                                    suggest_tv.setText("恭喜您已通过考试");

                                } else {
                                    suggest_tv.setText("答题正确率较低，仍需努力");
                                }


                                if (Integer.valueOf(totalScore) != null) {
                                    totalScore_tv.setText(totalScore + "分");
                                }

                                if (Integer.valueOf(defeatNum) != null) {

                                    defeatNum_tv.setText(defeatNum + "人");
                                }
                                if (Integer.valueOf(ranking) != null) {
                                    ranking_tv.setText(ranking + "名");
                                }
                                if (Integer.valueOf(correctRate) != null) {
                                    rightPercent_cv.setValueText(correctRate + "%");
                                }


                                if (examDetails != null) {
                                    int size = examDetails.size();
                                    for (int i = 0; i < size; i++) {
                                        int isRight = examDetails.get(i).getIsRight();
                                        if (isRight == 1) {
                                            rightNum++;
                                        }
                                    }
                                    rongNum = size - rightNum;
                                    //  correctRate = (int) ((rightNum/(size))*100);
                                    Log.e("arightCent", arightCent + "," + rightNum + "," + size);
                                    error_cv.setValueText((int) rongNum + "");//错题
                                    right_cv.setValueText((int) rightNum + "");//对题
                                    error_cv.setAnimDuration(5000);
                                    error_cv.setInterpolator(new AccelerateDecelerateInterpolator());
                                    error_cv.setSweepValue(20);
                                    error_cv.setSweepValue(rongNum);
                                    error_cv.anim();

                                    right_cv.setAnimDuration(5000);
                                    right_cv.setInterpolator(new AccelerateDecelerateInterpolator());
                                    right_cv.setSweepValue(rightNum);
                                    right_cv.anim();

                                    rightPercent_cv.setAnimDuration(5000);
                                    rightPercent_cv.setInterpolator(new AccelerateDecelerateInterpolator());
                                    rightPercent_cv.setSweepValue(correctRate);
                                    rightPercent_cv.anim();

                                    //结果的gridList
                                    List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> oneList = new ArrayList<>();
                                    List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> twoList = new ArrayList<>();
                                    List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> threeList = new ArrayList<>();
                                    List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> fourList = new ArrayList<>();
                                    List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> fiveList = new ArrayList<>();
                                    List<TestGradAnalyseBean.DataEntity.ExamDetailsEntity> sixList = new ArrayList<>();
                                    int size1 = examDetails.size();
                                    for (int i = 0; i < size1; i++) {
                                        int questionType = examDetails.get(i).getQuestionType();
                                        int isRight = examDetails.get(i).getIsRight();
                                        if (questionType == 1) {//单选
                                            int j = i + 1;
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = new TestGradAnalyseBean.DataEntity.ExamDetailsEntity();
                                            examDetailsEntity.setLocation(j);
                                            if (isRight == 1) {
                                                examDetailsEntity.setIsRight(1);
                                            }
                                            oneList.add(examDetailsEntity);
                                        } else if (questionType == 2) {//多选
                                            int j = i + 1;
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = new TestGradAnalyseBean.DataEntity.ExamDetailsEntity();
                                            examDetailsEntity.setLocation(j);
                                            if (isRight == 1) {
                                                examDetailsEntity.setIsRight(1);
                                            }
                                            twoList.add(examDetailsEntity);
                                        } else if (questionType == 3){//判断
                                            int j = i + 1;
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = new TestGradAnalyseBean.DataEntity.ExamDetailsEntity();
                                            examDetailsEntity.setLocation(j);
                                            if (isRight == 1) {
                                                examDetailsEntity.setIsRight(1);
                                            }
                                            threeList.add(examDetailsEntity);
                                        } else if (questionType == 4) {//主管案例
                                            int j = i + 1;
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = new TestGradAnalyseBean.DataEntity.ExamDetailsEntity();
                                            examDetailsEntity.setLocation(j);
                                            if (isRight == 1) {
                                                examDetailsEntity.setIsRight(1);
                                            }
                                            fourList.add(examDetailsEntity);
                                        } else if (questionType == 5) {//客观案例
                                            int j = i + 1;
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = new TestGradAnalyseBean.DataEntity.ExamDetailsEntity();
                                            examDetailsEntity.setLocation(j);
                                            if (isRight == 1) {
                                                examDetailsEntity.setIsRight(1);
                                            }
                                            fiveList.add(examDetailsEntity);
                                        } else if (questionType == 6) {//简答题
                                            int j = i + 1;
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = new TestGradAnalyseBean.DataEntity.ExamDetailsEntity();
                                            examDetailsEntity.setLocation(j);
                                            if (isRight == 1) {
                                                examDetailsEntity.setIsRight(1);
                                            }
                                            sixList.add(examDetailsEntity);
                                        }
                                    }
                                    List<GridItem> mGirdList = new ArrayList<GridItem>();
                                    GridItem item1 = null;
                                    GridItem item2 = null;
                                    GridItem item3 = null;
                                    GridItem item4 = null;
                                    GridItem item5 = null;
                                    GridItem item6 = null;
                                    int size4 = oneList.size();
                                    int size2 = twoList.size();
                                    int size3 = threeList.size();
                                    int size5 = fourList.size();
                                    int size6 = fiveList.size();
                                    int size7 = sixList.size();
                                    if (size1 > 0) {
                                        for (int i = 0; i < size4; i++) {
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = oneList.get(i);
                                            int location = examDetailsEntity.getLocation();
                                            int isRight = examDetailsEntity.getIsRight();
                                            Log.e("isRight", isRight + "单选,11111");
                                            item1 = new GridItem(location + "," + isRight, i + "", 0, "单选题", isRight);
                                            mGirdList.add(item1);
                                        }
                                    }
                                    if (size2 > 0) {
                                        for (int i = 0; i < size2; i++) {
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = twoList.get(i);
                                            int location = examDetailsEntity.getLocation();
                                            int isRight = examDetailsEntity.getIsRight();
                                            item2 = new GridItem(location + "," + isRight, i + "", 1, "多选题", isRight);
                                            mGirdList.add(item2);
                                            Log.e("isRight", isRight + "多选,11111");
                                        }
                                    }
                                    if (size3 > 0) {
                                        for (int i = 0; i < size3; i++) {
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = threeList.get(i);
                                            int location = examDetailsEntity.getLocation();
                                            int isRight = examDetailsEntity.getIsRight();
                                            item3 = new GridItem(location + "," + isRight, i + "", 2, "判断题", isRight);
                                            Log.e("isRight", isRight + "判断,11111");
                                            mGirdList.add(item3);
                                        }
                                    }
                                    if (size5 > 0) {
                                        for (int i = 0; i < size5; i++) {
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = fourList.get(i);
                                            int location = examDetailsEntity.getLocation();
                                            int isRight = examDetailsEntity.getIsRight();
                                            item4 = new GridItem(location + "," + isRight, i + "", 3, "主观案例题", isRight);
                                            Log.e("isRight", isRight + "主管案例,11111");
                                            mGirdList.add(item4);
                                        }
                                    }
                                    if (size6 > 0) {
                                        for (int i = 0; i < size6; i++) {
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = fiveList.get(i);
                                            int location = examDetailsEntity.getLocation();
                                            int isRight = examDetailsEntity.getIsRight();
                                            item5 = new GridItem(location + "," + isRight, i + "", 4, "客观案例题", isRight);
                                            Log.e("isRight", isRight + "客观案例,11111");
                                            mGirdList.add(item5);
                                        }
                                    }
                                    if (size7 > 0) {
                                        for (int i = 0; i < size7; i++) {
                                            TestGradAnalyseBean.DataEntity.ExamDetailsEntity examDetailsEntity = sixList.get(i);
                                            int location = examDetailsEntity.getLocation();
                                            int isRight = examDetailsEntity.getIsRight();
                                            item6 = new GridItem(location + "," + isRight, i + "", 5, "简答题", isRight);
                                            Log.e("isRight", isRight + "简答题,11111");
                                            mGirdList.add(item6);
                                        }
                                    }
                                    adapter = new TestResultAdapter(mGirdList, ResultActivity.this);
                                    resultGridView.setAdapter(adapter);
                                    //list.addAll(examDetails);
                                    //adapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Toast.makeText(ResultActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call<TestGradAnalyseBean> call, Throwable t) {
                Log.i("====获取成绩异常=====", t.getMessage());
            }
        });
    }

    public static String test(int second) {
        int hour = 0;
        int minute = 0;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }
        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour + ":" + minute + ":" + second;
        return strtime;
    }

    private void initView() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        testAllGrade_tv = (TextView) findViewById(R.id.testAllGrade_tv);

        test_result_return_iv = (ImageView) findViewById(R.id.test_result_return_iv);
        resultGridView = (StickyGridHeadersGridView) findViewById(R.id.resultGridView);

        error_cv = (CircleProgress) findViewById(R.id.errorNum_cv);
        right_cv = (CircleProgress) findViewById(R.id.rightNum_cv);
        rightPercent_cv = (CircleProgress) findViewById(R.id.rightCent_cv);
        useTime_tv = (TextView) findViewById(R.id.useTime_tv);
        suggest_tv = (TextView) findViewById(R.id.suggest_tv);
        defeatNum_tv = (TextView) findViewById(R.id.defeatNum_tv);
        ranking_tv = (TextView) findViewById(R.id.rank_tv);
        totalScore_tv = (TextView) findViewById(R.id.totalScore_tv);
        doDate_tv = (TextView) findViewById(R.id.doData_tv);
        currentTime = DateUtil.getCurrentTime();
        if (!TextUtils.isEmpty(currentTime)) {
            doDate_tv.setText(currentTime);
        }
        examScoreId = getIntent().getDoubleExtra("examScoreId", 0.0);
        // userLibId = getIntent().getIntExtra("userLibId",0);

    }


    //private String beginTime,asssinTime;
    public String getTime(String beginTime, String assignTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = null;
        try {
            now = df.parse(assignTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = null;
        try {
            date = df.parse(beginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s1 = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println("" + day + "天" + hour + "小时" + min + "分" + s1 + "秒");
        Log.e("ssssssss", hour + ":" + min + ":" + s1);

        return hour + ":" + min + ":" + s1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
            list.clear();
        }
        if (window != null) {
            if (window.isShowing()) {
                window.dismiss();
            }
        }

    }

    private void initFinish() {
        test_result_return_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                list.clear();
            }
        });

    }

    //继续答题
    public void continueTestOnClick(View view) {
        Intent intent = new Intent(ResultActivity.this, TestContentActivity1.class);
        Bundle bundle = new Bundle();
        Log.e("getIdResult", quesLibId + "," + userLibId);
        Integer integer = Integer.valueOf(spareTime);
        if (quesLibId > 0 && userLibId > 0) {
            bundle.putInt("userLibId", userLibId);
            bundle.putInt("quesLibId", quesLibId);
            bundle.putInt("examType", 1);
            Log.e("spareTime", spareTime + ",2222");
            intent.putExtras(bundle);
            if(overdueTime <= 0){
                initPopUpWindow2();
                return;
            }
            if (integer != null) {
                if (integer > 0) {
                    startActivity(intent);
                    finish();
                }
                else {
                    //  Toast.makeText(ResultActivity.this, "考试次数已用完", Toast.LENGTH_SHORT).show();
                    initPopUpWindow1();
                }
            }
        }
    }

    //查看错题
    public void errorAnalysisOnClick(View view) {
        if (examDetails != null) {
            Intent intent = new Intent(ResultActivity.this, TestLookHaveDoneActivity.class);
            Bundle bundle = new Bundle();
            Integer integer = Integer.valueOf((int) examScoreId);
            Log.e("getExamScoreId", examScoreId + ",00");
            bundle.putInt("examScoreId", integer);
            if (!TextUtils.isEmpty(paperName)) {
                bundle.putString("paperName", paperName);
            }
            bundle.putInt("testType", 1);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "您上次打开考试题库没点击交卷自动退出,无法查看错题", Toast.LENGTH_SHORT).show();
        }
    }

    //查看试卷
    public void allAnalysisOnClick(View view) {
        if (examDetails != null) {
            Integer integer1 = Integer.valueOf(isShowPaper);
            if (integer1 != null) {
                if (integer1 == 1) {//可以查看全部试卷
                    Intent intent = new Intent(ResultActivity.this, TestLookHaveDoneActivity.class);
                    Bundle bundle = new Bundle();
                    Integer integer = Integer.valueOf((int) examScoreId);
                    Log.e("getExamScoreId", examScoreId + ",00");
                    bundle.putInt("examScoreId", integer);
                    if (!TextUtils.isEmpty(paperName)) {
                        bundle.putString("paperName", paperName);
                    }
                    bundle.putInt("testType", 2);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {//不可以查看试卷
                    // Toast.makeText(ResultActivity.this, "请先购买B套餐才可以查看试卷", Toast.LENGTH_SHORT).show();
                    initPopUpWindow();
                }
            }
        } else {
            Toast.makeText(this, "您上次打开考试题库没点击交卷自动退出,无法查看试卷", Toast.LENGTH_SHORT).show();
        }


    }

    private void initPopUpWindow() {
        View view = View.inflate(ResultActivity.this, R.layout.pop_taocan_layout, null);
        TextView cancel = view.findViewById(R.id.myquestion_cancel);
        TextView downLoad_tv = view.findViewById(R.id.downLoadNow_tv);
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        //设置动画,就是style里创建的那个j
        window.setAnimationStyle(R.style.take_photo_anim);
        window.showAsDropDown(view, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        window.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        window.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        cancel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        cancel.getPaint().setAntiAlias(true);//抗锯齿

        downLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去购买,跳到题库详情
                Intent intent = new Intent(ResultActivity.this, TestDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("quesLibId", quesLibId);
                intent.putExtras(bundle);
                ResultActivity.this.startActivity(intent);
                window.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (window != null) {
                    if (window.isShowing()) {
                        window.dismiss();
                    }
                }
            }
        });
    }

    private void initPopUpWindow1() {
        View view = View.inflate(ResultActivity.this, R.layout.test_pop_layout, null);
        TextView cancel = (TextView) view.findViewById(R.id.myquestion_cancel);
        TextView downLoad_tv = (TextView) view.findViewById(R.id.downLoadNow_tv);
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        //设置动画,就是style里创建的那个j
        window.setAnimationStyle(R.style.take_photo_anim);
        window.showAsDropDown(view, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        window.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        window.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        cancel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        cancel.getPaint().setAntiAlias(true);//抗锯齿

        downLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去购买,跳到题库详情
                Intent intent = new Intent(ResultActivity.this, TestDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("quesLibId", quesLibId);
                intent.putExtras(bundle);
                ResultActivity.this.startActivity(intent);
                window.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (window != null) {
                    if (window.isShowing()) {
                        window.dismiss();
                    }
                }
            }
        });
    }

    private void initPopUpWindow2() {
        View view = View.inflate(ResultActivity.this, R.layout.test_time_layout, null);
        TextView cancel = (TextView) view.findViewById(R.id.myquestion_cancel);
        TextView downLoad_tv = (TextView) view.findViewById(R.id.downLoadNow_tv);
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        //设置动画,就是style里创建的那个j
        window.setAnimationStyle(R.style.take_photo_anim);
        window.showAsDropDown(view, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        //可以点击外部消失
        window.setOutsideTouchable(true);
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        window.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        cancel.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        cancel.getPaint().setAntiAlias(true);//抗锯齿

        downLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去购买,跳到题库详情
                Intent intent = new Intent(ResultActivity.this, TestDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("quesLibId", quesLibId);
                intent.putExtras(bundle);
                ResultActivity.this.startActivity(intent);
                window.dismiss();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (window != null) {
                    if (window.isShowing()) {
                        window.dismiss();
                    }
                }
            }
        });
    }


    //分享
    public void shareOnClick(View view) {
        popView = LayoutInflater.from(ResultActivity.this).inflate(R.layout.share_dialog, null, false);
        TextView tv_cancal = (TextView) popView.findViewById(R.id.tv_cancal);
        ImageView spina = (ImageView) popView.findViewById(R.id.tv_wb);
        ImageView wechat_quan = (ImageView) popView.findViewById(R.id.tv_wxp);
        ImageView wechat_friend = (ImageView) popView.findViewById(R.id.tv_wx);
        ImageView qq_friend = (ImageView) popView.findViewById(R.id.tv_qq);
        ImageView qq_zone = (ImageView) popView.findViewById(R.id.tv_qqz);
        WindowManager manger = (WindowManager) getSystemService(ResultActivity.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int height = manger.getDefaultDisplay().getHeight() / 3;
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, height, false);
        popupWindow.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        popupWindow.setOutsideTouchable(true);
        // 配合 点击外部区域消失使用 否则 没有效果
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

//新浪
        spina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(shareUrl)) {
                    UMImage image = new UMImage(ResultActivity.this, shareUrl);//网络图片
                    new ShareAction(ResultActivity.this).withText("hello").withMedia(image).setCallback(shareListener)
                            .setPlatform(SHARE_MEDIA.SINA).share();
                }
            }
        });
        //朋友圈
        wechat_quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(shareUrl)) {
                    UMImage image = new UMImage(ResultActivity.this, shareUrl);//网络图片
                    new ShareAction(ResultActivity.this).withText("hello").withMedia(image).setCallback(shareListener)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();
                }
            }

        });
        //微信好友
        wechat_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(shareUrl)) {
                    UMImage image = new UMImage(ResultActivity.this, shareUrl);//网络图片
                    new ShareAction(ResultActivity.this).withText("hello").withMedia(image).setCallback(shareListener)
                            .setPlatform(SHARE_MEDIA.WEIXIN).share();
                }
            }
        });
        //qq好友
        qq_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(shareUrl)) {
                    UMImage image = new UMImage(ResultActivity.this, shareUrl);//网络图片
                    new ShareAction(ResultActivity.this).withText("hello").withMedia(image).setCallback(shareListener)
                            .setPlatform(SHARE_MEDIA.QQ).share();
                }
            }
        });
        //qq空间
        qq_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(shareUrl)) {
                    UMImage image = new UMImage(ResultActivity.this, shareUrl);//网络图片
                    new ShareAction(ResultActivity.this).withText("hello").withMedia(image).setCallback(shareListener)
                            .setPlatform(SHARE_MEDIA.QZONE).share();
                }
            }
        });

        tv_cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ResultActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ResultActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            //Toast.makeText(ResultActivity.this,"取消了",Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


}
