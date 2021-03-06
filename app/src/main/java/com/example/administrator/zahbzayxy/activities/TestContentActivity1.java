package com.example.administrator.zahbzayxy.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.StickyGridAdapter;
import com.example.administrator.zahbzayxy.adapters.TestContentAdapter;
import com.example.administrator.zahbzayxy.beans.GridItem;
import com.example.administrator.zahbzayxy.beans.NewTestContentBean;
import com.example.administrator.zahbzayxy.beans.TestCommitBean;
import com.example.administrator.zahbzayxy.beans.TestResultBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.myinterface.UpPx;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;
import com.example.administrator.zahbzayxy.stickheadgv.StickyGridHeadersGridView;
import com.example.administrator.zahbzayxy.utils.AutoScrollTextView;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.DateUtil;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//考试提交试卷
public class TestContentActivity1 extends BaseActivity {
    View popView, popDialog;
    PopupWindow popupWindow;
    MyRecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    TestContentAdapter madapter;
    PopupWindow window;
    private ImageView back_tset_iv;
    //请求结果的list
    List<NewTestContentBean.DataBean.QuesDataBean> totalList = new ArrayList<>();
    //查看结果的list
    private static List<TestResultBean.ExamDetailsBean> listResult = new ArrayList<>();
    //保存用户成绩的list
    private static List<TestResultBean.ExamDetailsBean> listToPost = new ArrayList<>();
    private int size;
    TextView dijige, shijian, testCommit_bt;
    private int position;
    private String token;
    private int shengyu;
    Handler handler;
    private float rightNum = (float) 0.0;
    private float rongNum = (float) 0.0;
    private double doNUm;
    private String beginTime;
    private int correctRate;
    private String useTime;
    private int quesLibId;
    private String stopTime;
    JSONArray jsonArray;
    JSONObject object;
    String encodeToken;
    private String paperName;
    PopupWindow popUpWindow1;
    private int singleScore;
    private int multipleScore;
    private int judgeScore;
    private int factScore;
    private int notFactScore;
    private int shortScore;
    private int totalScore;
    private AutoScrollTextView testName;
    private int examTime;
    private ImageView progress_iv;
    private TextView toLeft_tv, toRight_tv;
    private boolean continueTest = true;
    private double ranking;
    private double defeatNum;
    private double examScoreId;
    private int userLibId;
    private boolean isFreeTest;

    View freeTestpopView;
    PopupWindow freeTestpopupWindow;
    private boolean isCommit = false;
    TextView tijiao, zuoti;
    private boolean stopThread = false;
    private int putNewExamScoreId;
    private int examType;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        Log.e("tokenUnCode", token);
        try {
            encodeToken = URLEncoder.encode(token, "utf-8");
            Log.e("tokenTestChengji", encodeToken);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        initView();
        initFinish();
        initRecyClerView();
        initDownLoadData();

    }

    private void initTime() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    try {
                        if (!stopThread) {
                            Thread.sleep(1000);
                            handler.obtainMessage().sendToTarget();
                            if (shengyu == 1) {
                                break;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ynf", "ynf========onResume===");
        //避免提交一次重复提交
        if (isCommit == true) {
            tijiao.setEnabled(false);
            testCommit_bt.setEnabled(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ynf", "ynf=======onPause=====");
        if(popupWindow !=null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
        timeDialog();
        continueTest = false;
    }

    private void initDownLoadData() {
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        Call<NewTestContentBean> testContentData = aClass.getNewTestContentData(userLibId, quesLibId, token, examType);
        testContentData.enqueue(new Callback<NewTestContentBean>() {
            private int quesId;
            private int passScore;
            // 单选分数
            private int singleScoreTotal;
            // 多选分数
            private int multipleScoreTotal;
            // 判断题分数
            private int judgeScoreTotal;
            // 主观案例
            private int factScoreTotal;
            // 客观案例
            private int notFactScoreTotal;
            // 简答题
            private int shortScoreTotal;

            private int singleNum, multiNum, jungeNum, zhuNUm, keNum, shortNum;

            @Override
            @SuppressLint("HandlerLeak")
            public void onResponse(Call<NewTestContentBean> call, Response<NewTestContentBean> response) {
//                String s = new Gson().toJson(response);
//                Log.e("response", s);
                NewTestContentBean body = response.body();
                if (response != null & body != null) {
//                    String s2 = new Gson().toJson(response.toString());
//                    Log.e("testContent", s);
                    NewTestContentBean.DataBean data = body.getData();
                    String code = body.getCode();
                    if (code.equals("99999")) {
                        Toast.makeText(TestContentActivity1.this, "系统异常", Toast.LENGTH_SHORT).show();
                    }else if(code.equals("00009")){
                        initPopUpWindow1();
                    }else if (code.equals("00008")) {
                        Toast.makeText(TestContentActivity1.this, "用户已达考试次数上限", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("00007")) {
                        Toast.makeText(TestContentActivity1.this, "没有权限", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("00003")) {
                        Toast.makeText(TestContentActivity1.this, "用户未登录", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("00000") & data != null) {
                        //各种题型的总分数
                        judgeScoreTotal = data.getJudgeScore();
                        multipleScoreTotal = data.getMultipleScore();
                        singleScoreTotal = data.getSingleScore();
                        factScoreTotal = data.getFactScore();
                        notFactScoreTotal = data.getNotFactScore();
                        shortScoreTotal = data.getShortScore();
                        putNewExamScoreId = data.getExamScoreId();

                        Log.e("体型分数aaaaaaa", judgeScoreTotal + multipleScoreTotal + singleScoreTotal + "");
                        paperName = data.getPaperName();
                        if (!TextUtils.isEmpty(paperName)) {
                            testName.setText(paperName);
                            Log.e(" paperName paperName", paperName);
                        }
                        passScore = data.getPassScore();
                        //考试时间
                        examTime = body.getData().getExamTime();
                        Log.e("examTimeexamTime", String.valueOf(examTime));
                        shengyu = examTime * 60;
                        handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                if (continueTest == true) {
                                    shengyu = shengyu - 1;
                                }
                                if (continueTest == false) {
                                    shengyu = shengyu - 0;
                                }
                                String time = test(shengyu);
                                shijian.setText(time);
                                //时间剩余为0   直接提交试卷跳转  待写
                                if (shengyu == 0) {
                                    try {
                                        try {
                                            Thread.sleep(1000);
                                            if (!isCommit) {
                                                initToPostJsonData();
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        };
                        initTime();
                        //  shengyu=examTime;
                        String s1 = new Gson().toJson(body);
                        Log.e("getTestData", s1);
                        List<NewTestContentBean.DataBean.QuesDataBean> quesData = body.getData().getQuesData();
                        size = quesData.size();
                        if (size > 0) {
                            dijige.setText((position + 1) + "/" + size);
                        }
                        //先求得每种题型的个数
                        // quesType 1、单选 2、多选 3、判断 4、主管案例 5、客观案例 6、简单题
                        listToPost.clear();
                        for (int i = 0; i < size; i++) {
                            int quesType = quesData.get(i).getQuesType();
                            quesId = quesData.get(i).getId();
                            if (quesType == 1) {
                                singleNum++;
                            } else if (quesType == 2) {
                                multiNum++;
                            } else if (quesType == 3) {
                                jungeNum++;
                            } else if (quesType == 4) {
                                zhuNUm++;
                            } else if (quesType == 5) {
                                keNum++;
                            } else if (quesType == 6) {
                                shortNum++;
                            }

                            //提交给服务端的做题结果
                            TestResultBean.ExamDetailsBean examToPostBean = new TestResultBean.ExamDetailsBean();
                            listToPost.add(examToPostBean);
                            listToPost.get(i).setUserAnswerIds("");
                            listToPost.get(i).setQuestionId(quesId);
                            listToPost.get(i).setIsRight(0);
                            listToPost.get(i).setQuestionType(quesType);
                        }
                        //求出每道题的分数
                        if (singleScoreTotal > 0 & singleNum != 0) {
                            singleScore = singleScoreTotal / singleNum;
                        }
                        if (multipleScoreTotal > 0 & multiNum != 0) {
                            multipleScore = multipleScoreTotal / multiNum;
                        }
                        if (judgeScoreTotal > 0 & jungeNum != 0) {
                            judgeScore = judgeScoreTotal / jungeNum;
                        }
                        if (factScoreTotal > 0 && zhuNUm != 0) {
                            factScore = factScoreTotal / zhuNUm;
                        }
                        if (notFactScoreTotal > 0 && keNum != 0) {
                            notFactScore = notFactScoreTotal / keNum;
                        }
                        if (shortScoreTotal > 0 && shortNum != 0) {
                            shortScore = shortScoreTotal / shortNum;
                        }


                        //提交结果的list
                        listResult.clear();
                        for (int i = 0; i < size; i++) {
                            TestResultBean.ExamDetailsBean examDetailsBean = new TestResultBean.ExamDetailsBean();
                            listResult.add(examDetailsBean);
                            listResult.get(i).setIsRight(-1);
                        }
                        totalList.clear();
                        totalList.addAll(quesData);
                        madapter = new TestContentAdapter(TestContentActivity1.this, totalList, listToPost, listResult, recyclerview, dijige);
                        recyclerview.setAdapter(madapter);
                        //把每道题的分数传给adapter
                        madapter.setJudgeScore(judgeScore);
                        madapter.setMultipleScore(multipleScore);
                        madapter.setSingleScore(singleScore);
                        madapter.setFactScore(factScore);
                        madapter.setNotFactScore(notFactScore);
                        madapter.setShortScore(shortScore);
                        madapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewTestContentBean> call, Throwable t) {
                String msg = t.getMessage();
                Toast.makeText(TestContentActivity1.this, msg, Toast.LENGTH_SHORT).show();
            }
        });


        //点击当前页面的交卷按钮时
        testCommit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
                if (!isCommit) {
                    int size = listToPost.size();
                    int doNum = 0;
                    for (int i = 0; i < size; i++) {
                        int isRight = listToPost.get(i).getIsRight();
                        String userAnswerIds = listToPost.get(i).getUserAnswerIds();
                        if (!TextUtils.isEmpty(userAnswerIds)) {
                            doNum++;
                        }
                        if (isRight == 1) {
                            rightNum++;
                        } else {
                            rongNum++;
                        }
                    }
                    //   correctRate = (int) ((rightNum/(size))*100);

                    popView = LayoutInflater.from(TestContentActivity1.this).inflate(R.layout.tanchukuang, null, false);
                    TextView tvtvtv1 = popView.findViewById(R.id.tvtvtv1);
                    zuoti = popView.findViewById(R.id.zuoti);
                    tijiao = popView.findViewById(R.id.tijiao);

                    if (doNUm == TestContentActivity1.this.size) {
                        tvtvtv1.setText("您已完成全部试卷");
                    } else {
                        double shengYuNum = size - doNUm;
                        if (shengYuNum > 0) {
                            tvtvtv1.setText("您还有" + (size - doNum) + "道题未做,还剩" + (shengyu / 60) + "分钟,是否确认提交?");
                        } else {
                            tvtvtv1.setText("您还剩" + 0 + "道题未做,还剩" + (shengyu / 60) + "分钟,是否确认提交?");
                        }
                    }
                    useTime = test(examTime * 60 - shengyu);
                    popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
                    popupWindow.setTouchable(true);
                    // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
                    popupWindow.setOutsideTouchable(true);
                    // 配合 点击外部区域消失使用 否则 没有效果
                    zuoti.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    //点击提交按钮时
                    tijiao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isCommit = true;
                            try {
                                if (isFreeTest == true) {//免费考试直接弹框显示分数
                                    initFreeTestDialog();
                                    popupWindow.dismiss();
                                } else {
                                    //上传做题结果
                                    initToPostJsonData();
                                    // madapter.setRightNum(0);
                                    //  madapter.setRongNum(0);
                                    //  finish();
                                    popupWindow.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //做题个数
                        }

                    });
                    popupWindow.showAtLocation(popView, Gravity.CENTER, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                }
            }
        });
    }

    private void initFreeTestDialog() {
        freeTestpopView = LayoutInflater.from(TestContentActivity1.this).inflate(R.layout.pop_isfree_test_layout, null, false);
        TextView cancel = (TextView) freeTestpopView.findViewById(R.id.myquestion_cancel);
        TextView getFreeScore_tv = (TextView) freeTestpopView.findViewById(R.id.getScore_tv);
        freeTestpopupWindow = new PopupWindow(freeTestpopView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        freeTestpopupWindow.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        freeTestpopupWindow.setOutsideTouchable(true);
        // 配合 点击外部区域消失使用 否则 没有效果
        freeTestpopupWindow.showAtLocation(freeTestpopView, Gravity.CENTER, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        getFreeScore_tv.setText("您本次得分:" + (rightNum) + "分");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                freeTestpopupWindow.dismiss();
                finish();
            }
        });

    }

    private void initToPostJsonData() throws JSONException {
        if (madapter == null) {
            ToastUtils.showLongInfo("数据提交失败，请稍后重试");
            finish();
            return;
        }
        //从adapter中获得做题所获得的总分数
        totalScore = madapter.getJudgeScore() + madapter.getSingleScore() + madapter.getMultipleScore() +
                madapter.getPostFactScore() + madapter.getPostNotFactScore() + madapter.getPostShortScore();
        Log.e("totalScoreScore", String.valueOf(totalScore));
        jsonArray = new JSONArray();
        final List<TestResultBean.ExamDetailsBean> listToPost = madapter.getListToPost();
        List<NewTestContentBean.DataBean.QuesDataBean> dataList = madapter.getDataList();
        int size1 = listToPost.size();
        // rightFactNum 主管案例  rightNotFactNUm 客观案例  rightShortNum 简答题
        int newTotalScore = 0, rightJungleNum = 0, rightMultiNum = 0, rightSingleNum = 0,
                rightFactNum = 0, rightNotFactNUm = 0, rightShortNum = 0;
        for (int i = 0; i < size1; i++) {
            int j = i;
            TestResultBean.ExamDetailsBean examDetailsBean = listToPost.get(i);
            int isRight = listToPost.get(i).getIsRight();
            int questionType = listToPost.get(j).getQuestionType();
            if (isRight == 1) {
                // newTotalScore++;
                if (questionType == 1) {
                    rightSingleNum++;
                } else if (questionType == 2) {
                    rightMultiNum++;
                } else if (questionType == 3) {
                    rightJungleNum++;
                } else if (questionType == 6) {
                    rightShortNum++;
                }
            }
            if (questionType == 4) {
                // 主观案例题
                String userAnswer = examDetailsBean.getUserAnswerIds();
                if (!TextUtils.isEmpty(userAnswer) && j < dataList.size()) {
                    JSONObject json = new JSONObject(userAnswer);
                    NewTestContentBean.DataBean.QuesDataBean quesDataBean = dataList.get(j);
                    boolean factIsRight = true;
                    for (int m = 0; m < quesDataBean.getChildren().size(); m++) {
                        String ans = json.optString(String.valueOf(m));
                        if (TextUtils.isEmpty(ans)) {
                            factIsRight = false;
                            break;
                        }
                    }
                    if (factIsRight) {
                        rightFactNum++;
                    }
                }
            } else if (questionType == 5) {
                // 客观案例
                String userAnswer = examDetailsBean.getUserAnswerIds();
                if (!TextUtils.isEmpty(userAnswer) && j < dataList.size()) {
                    JSONObject json = new JSONObject(userAnswer);
                    NewTestContentBean.DataBean.QuesDataBean quesDataBean = dataList.get(j);
                    boolean factIsRight = true;
                    for (int m = 0; m < quesDataBean.getChildren().size(); m++) {
                        JSONObject itemJson = json.optJSONObject(String.valueOf(m));
                        if (itemJson == null) {
                            factIsRight = false;
                            break;
                        }
                        int notFactRight = itemJson.optInt("is_right");
                        if (notFactRight != 1) {
                            factIsRight = false;
                            break;
                        }
                    }
                    if (factIsRight) {
                        rightNotFactNUm++;
                    }
                }
            }
        }
        newTotalScore = rightSingleNum * singleScore + rightMultiNum * multipleScore + rightJungleNum * judgeScore
                + rightFactNum * factScore + rightNotFactNUm * notFactScore + rightShortNum * shortScore;
        int totalRightNum = rightSingleNum + rightMultiNum + rightJungleNum + rightFactNum + rightNotFactNUm + rightShortNum;
        if (size1 > 0) {
            correctRate = (int) (totalRightNum * 100 / size1);
        }
        Log.e("postToatalScore", rightSingleNum + "," + rightMultiNum + "," + rightJungleNum + ","
                + singleScore + "," + multipleScore + "," + judgeScore + ",," + totalRightNum + ",," + correctRate);
        if (size1 <= 0) {
            Toast.makeText(TestContentActivity1.this, "请答题后再提交", Toast.LENGTH_SHORT).show();

        } else {
            for (int i = 0; i < size1; i++) {
                object = new JSONObject();
                TestResultBean.ExamDetailsBean examDetailsBean = listToPost.get(i);
                int questionId = examDetailsBean.getQuestionId();
                int questionType = examDetailsBean.getQuestionType();
                String userAnswerIds = examDetailsBean.getUserAnswerIds();
                int isRight = examDetailsBean.getIsRight();
                object.put("questionId", questionId);
                object.put("questionType", questionType);
                if ((questionType == 4 || questionType == 5) && i < dataList.size()) {
                    NewTestContentBean.DataBean.QuesDataBean quesDataBean = dataList.get(i);
                    List<NewTestContentBean.DataBean.QuesDataBean> childrenList = quesDataBean.getChildren();
                    JSONArray childArr = new JSONArray();
                    for (int j = 0; j < childrenList.size(); j++) {
                        JSONObject childJson = new JSONObject();
                        NewTestContentBean.DataBean.QuesDataBean child = childrenList.get(j);
                        childJson.put("questionType", child.getQuesType());
                        childJson.put("questionId", child.getId());
                        childJson.put("score", child.getScore());
                        if (questionType == 4) {
                            JSONObject answerJson = null;
                            if (TextUtils.isEmpty(userAnswerIds)) {
                                answerJson = new JSONObject();
                            } else {
                                answerJson = new JSONObject(userAnswerIds);
                            }
                            int right = 0;
                            String answer = answerJson.optString(String.valueOf(j));
                            if (!TextUtils.isEmpty(answer)) right = 1;
                            childJson.put("userAnswerIds", answer);
                            childJson.put("isRight", right);
                        } else if (questionType == 5) {
                            JSONObject answerJson = null;
                            if (TextUtils.isEmpty(userAnswerIds)) {
                                answerJson = new JSONObject();
                            } else {
                                answerJson = new JSONObject(userAnswerIds);
                            }
                            JSONObject itemAnswer = answerJson.optJSONObject(String.valueOf(j));
                            List<NewTestContentBean.DataBean.QuesDataBean.OptsBean> optsList = child.getOpts();
                            String childAnswerId = "";
                            for (int k = 0; k < optsList.size(); k++) {
                                NewTestContentBean.DataBean.QuesDataBean.OptsBean optsBean = optsList.get(k);
                                if (optsBean.getTag() == 1) {
                                    childAnswerId += optsBean.getId() + ",";
                                }
                            }
                            if (!TextUtils.isEmpty(childAnswerId))
                                childAnswerId = childAnswerId.substring(0, childAnswerId.length() - 1);
                            childJson.put("userAnswerIds", childAnswerId);
                            int right = 0;
                            if (itemAnswer != null) right = itemAnswer.optInt("is_right");
                            childJson.put("isRight", right);
                        }
                        childArr.put(childJson);
                    }
                    object.put("children", childArr);
                    object.put("userAnswerIds", "");

                    if (questionType == 4) {
                        String userAnswer = examDetailsBean.getUserAnswerIds();
                        if (!TextUtils.isEmpty(userAnswer) && i < dataList.size()) {
                            JSONObject json = new JSONObject(userAnswer);
                            NewTestContentBean.DataBean.QuesDataBean quesDataBean1 = dataList.get(i);
                            boolean factIsRight = true;
                            for (int m = 0; m < quesDataBean1.getChildren().size(); m++) {
                                String ans = json.optString(String.valueOf(m));
                                if (TextUtils.isEmpty(ans)) {
                                    factIsRight = false;
                                    break;
                                }
                            }
                            if (factIsRight) {
                                isRight = 1;
                            } else {
                                isRight = 0;
                            }
                        }
                    } else if (questionType == 5) {
                        String userAnswer = examDetailsBean.getUserAnswerIds();
                        if (!TextUtils.isEmpty(userAnswer) && i < dataList.size()) {
                            JSONObject json = new JSONObject(userAnswer);
                            NewTestContentBean.DataBean.QuesDataBean quesDataBean1 = dataList.get(i);
                            boolean factIsRight = true;
                            for (int m = 0; m < quesDataBean1.getChildren().size(); m++) {
                                JSONObject itemJson = json.optJSONObject(String.valueOf(m));
                                if (itemJson == null) {
                                    factIsRight = false;
                                    break;
                                }
                                int notFactRight = itemJson.optInt("is_right");
                                if (notFactRight != 1) {
                                    factIsRight = false;
                                    break;
                                }
                            }
                            if (factIsRight) {
                                isRight = 1;
                            } else {
                                isRight = 0;
                            }
                        }
                    }
                } else {
                    object.put("userAnswerIds", userAnswerIds);
                }

                object.put("isRight", isRight);
                jsonArray.put(object);
            }
            //children 中的每一项有 questionType,questionId,isRight,userAnswerIds  这四个字段就行了

            stopTime = DateUtil.getNow(DateUtil.DEFAULTPATTERN);
            Log.e("currentTime", stopTime);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userLibId", userLibId);
            jsonObject.put("examType", examType);
            jsonObject.put("quesLibId", quesLibId);
            jsonObject.put("examScoreId", putNewExamScoreId);
            jsonObject.put("paperName", paperName);
            jsonObject.put("examBeginTime", beginTime);
            jsonObject.put("assignTime", stopTime);
            jsonObject.put("totalScore", newTotalScore);
            jsonObject.put("correctRate", correctRate);
            jsonObject.put("examDetails", jsonArray);
            String s = jsonObject.toString();
            Log.e("sTsetChengjiJsonjson", s);
            Map<String, String> saveScore = new HashMap<>();
            saveScore.put("examScore", s);
            saveScore.put("token", token);
            saveScore.put("examType", String.valueOf(examType));
            Log.i("dfdf", "test======" + examType);
            TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
            aClass.getExamScoreData1New(saveScore).enqueue(new Callback<TestCommitBean>() {
                @Override
                public void onResponse(Call<TestCommitBean> call, Response<TestCommitBean> response) {
                    TestCommitBean body = response.body();
                    String s1 = new Gson().toJson(body);
                    // {"code":"00000","data":{"defeatNum":1.0,"examScoreId":18971.0,"ranking":1.0}}
                    Log.e("jsonPostBodyaaaaaaaaa", s1);
                    if (response != null && body != null) {
                        String code = body.getCode();
                        Log.e("codeCommit", code);
                        if (code.equals("00000")) {
                            TestCommitBean.DataEntity data = body.getData();
                            defeatNum = data.getDefeatNum();
                            ranking = data.getRanking();
                            examScoreId = data.getExamScoreId();
                            Log.e("examScoreId", examScoreId + "");
                            initToStartResultActivity();
                            isCommit = false;
                            tijiao.setEnabled(true);
                            testCommit_bt.setEnabled(true);
                            return;
                        } else if (code.equals("99999")) {
                            Toast.makeText(TestContentActivity1.this, "系统异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                    isCommit = false;
                    tijiao.setEnabled(true);
                    testCommit_bt.setEnabled(true);
                }

                @Override
                public void onFailure(Call<TestCommitBean> call, Throwable t) {
                    String message = t.getMessage();
                    Log.e("comitError", message);
                    isCommit = false;
                    tijiao.setEnabled(true);
                    testCommit_bt.setEnabled(true);
                }
            });
            //  finish();

        }
    }

    private void initToStartResultActivity() {
        if (listResult != null) {
            EventBus.getDefault().postSticky(listResult);
        }
        Log.e("postResultData", listResult + "");
        if (examType == 1) {
            intent = new Intent(TestContentActivity1.this, ResultActivity.class);
        } else if (examType == 0) {
            intent = new Intent(TestContentActivity1.this, ExamResultActivity.class);
        }
        //模拟考试跳转结果页 examResultActivity

        Bundle bundle = new Bundle();
        bundle.putFloat("rightNum", rightNum);
        bundle.putFloat("rongNum", (size - rightNum));
        bundle.putString("useTime", useTime);
        bundle.putInt("totalScore", totalScore);
        bundle.putInt("quesLibId", quesLibId);
        bundle.putDouble("defeatNum", defeatNum);
        bundle.putDouble("ranking", ranking);
        if (examScoreId > 0.0) {
            bundle.putDouble("examScoreId", examScoreId);
        }
        bundle.putString("quesLibName", paperName);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void initView() {
        testName = (AutoScrollTextView) findViewById(R.id.testLiberaryName_tv);
        testCommit_bt = (TextView) findViewById(R.id.testCommit_bt);
        beginTime = DateUtil.getNow(DateUtil.DEFAULTPATTERN);
        quesLibId = getIntent().getIntExtra("quesLibId", 0);
        userLibId = getIntent().getIntExtra("userLibId", 0);
        examType = getIntent().getIntExtra("examType", examType);
        isFreeTest = getIntent().getBooleanExtra("isFreeTest", false);
        Log.e("quesLibId", quesLibId + ",,," + userLibId);
        back_tset_iv = (ImageView) findViewById(R.id.back_test_iv);
        recyclerview = (MyRecyclerView) findViewById(R.id.recyclerview);
        dijige = (TextView) findViewById(R.id.dijige);
        shijian = (TextView) findViewById(R.id.shijian);
        progress_iv = (ImageView) findViewById(R.id.currentPrgress_iv);
        toLeft_tv = (TextView) findViewById(R.id.toLeft_tv);
        toRight_tv = (TextView) findViewById(R.id.toRight_tv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };

        recyclerview.setLayoutManager(linearLayoutManager);
        shijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                continueTest = false;
//                timeDialog();

            }
        });
    }

    // .setIcon(android.R.drawable.ic_dialog_info)
    public void timeDialog() {
        popDialog = LayoutInflater.from(TestContentActivity1.this).inflate(R.layout.dialog_bankcard_active, null, false);
        TextView tv_save = popDialog.findViewById(R.id.tv_save);
        popupWindow = new PopupWindow(popDialog, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        popupWindow.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        // 配合 点击外部区域消失使用 否则 没有效果
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                continueTest = true;
            }
        });
        popupWindow.showAtLocation(popDialog, Gravity.CENTER, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);




//        new AlertDialog.Builder(this).setTitle("还要继续记时做题吗？")
//               .setCancelable(false)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        continueTest = true;
//                    }
//                }).show();

    }

    private void initRecyClerView() {
        recyclerview.setUp(new UpPx() {
            @Override
            public void upPx(int tag) {
                position = madapter.getWeiZhi();
                if (tag == 1) {
                    if (position > 0) {
                        //左
                        toLeft_tv.setVisibility(View.VISIBLE);
                        toRight_tv.setVisibility(View.VISIBLE);
                        position = position - 1;
                        int childPosition = madapter.getChildPosition();
                        if (childPosition >= 0) {
                            if (childPosition == 1) {
                                madapter.setChildPosition(-1);
                            } else {
                                madapter.setChildPosition(childPosition - 2);
                                position += 1;
                            }
                        }
                        int kePosition = madapter.getKeChildPosition();
                        if (kePosition >= 0) {
                            if (kePosition == 1) {
                                madapter.setKeChildPosition(-1);
                            } else {
                                madapter.setKeChildPosition(kePosition - 2);
                                position += 1;
                            }
                        }
                        recyclerview.scrollToPosition(position);
                        dijige.setText((position + 1) + "/" + size);
                        madapter.notifyDataSetChanged();
                    }

                } else if (tag == 2) {
                    //右
                    if (position < size - 1) {
                        toLeft_tv.setVisibility(View.VISIBLE);
                        toRight_tv.setVisibility(View.VISIBLE);
                        position = position + 1;
                        Log.i("=====adapter====", "rightButton setUp");
                        int childPosition = madapter.getChildPosition();
                        if (childPosition >= 0) {
                            if (childPosition >= totalList.get(position - 1).getChildren().size()) {
                                madapter.setChildPosition(-1);
                            } else {
                                position -= 1;
                            }
                        }
                        int kePosition = madapter.getKeChildPosition();
                        if (kePosition >= 0) {
                            if (kePosition >= totalList.get(position - 1).getChildren().size()) {
                                madapter.setKeChildPosition(-1);
                            } else {
                                position -= 1;
                            }
                        }
                        recyclerview.scrollToPosition(position);
                        dijige.setText((position + 1) + "/" + size);
                        madapter.notifyDataSetChanged();
                    }

                }
            }
        });
    }

    //左按钮
    public void leftButton(View view) {
        if (size > 0) {
            position = madapter.getWeiZhi();
            if (position > 0) {
                //左
                toLeft_tv.setVisibility(View.VISIBLE);
                position = position - 1;
                int childPosition = madapter.getChildPosition();
                if (childPosition >= 0) {
                    if (childPosition == 1) {
                        madapter.setChildPosition(-1);
                    } else {
                        madapter.setChildPosition(childPosition - 2);
                        position += 1;
                    }
                }
                int kePosition = madapter.getKeChildPosition();
                if (kePosition >= 0) {
                    if (kePosition == 1) {
                        madapter.setKeChildPosition(-1);
                    } else {
                        madapter.setKeChildPosition(kePosition - 2);
                        position += 1;
                    }
                }
                recyclerview.scrollToPosition(position);
                dijige.setText((position + 1) + "/" + size);
                madapter.notifyDataSetChanged();
                toRight_tv.setVisibility(View.VISIBLE);
            }
        }
    }

    //右按钮
    public void rightButton(View view) {
        if (size > 0) {
            position = madapter.getWeiZhi();
            //右
            ////////////////////////////////////////
            //此处的100是题的个数  根据实际题的个数做更改
            if (position < size - 1) {
                toRight_tv.setVisibility(View.VISIBLE);
                toLeft_tv.setVisibility(View.VISIBLE);
                Log.i("=====adapter====", "rightButton click");
                position = position + 1;
                int childPosition = madapter.getChildPosition();
                if (childPosition >= 0) {
                    if (childPosition >= totalList.get(position - 1).getChildren().size()) {
                        madapter.setChildPosition(-1);
                    } else {
                        position -= 1;
                    }
                }
                int kePosition = madapter.getKeChildPosition();
                if (kePosition >= 0) {
                    if (kePosition >= totalList.get(position - 1).getChildren().size()) {
                        madapter.setKeChildPosition(-1);
                    } else {
                        position -= 1;
                    }
                }
                recyclerview.scrollToPosition(position);
                dijige.setText((position + 1) + "/" + size);
                madapter.notifyDataSetChanged();
            }
        }
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

    private void initFinish() {
        back_tset_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ynf","ynf======onDestroy====");
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();

            }
        }
        if (popUpWindow1 != null) {
            if (popUpWindow1.isShowing()) {
                popUpWindow1.dismiss();

            }
        }
        //界面退出时把做题个数清零。
        doNUm = 0;
        shengyu = 0;
        stopThread = true;
        Log.e("isdestroy", shengyu + ",destroy," + shengyu);
    }

    //查看当前进度
    public void currentProgressClick(View view) {
        StickyGridAdapter stickyGridAdapter = null;
        int size = listToPost.size();

        int newDoNum = 0;
        for (int i = 0; i < size; i++) {
            String userAnswerIds = listToPost.get(i).getUserAnswerIds();
            if (!TextUtils.isEmpty(userAnswerIds)) {
                newDoNum++;
            }
        }
        final View popView = LayoutInflater.from(TestContentActivity1.this).inflate(R.layout.poplayout_test, null, false);
        ImageView dialogDissmiss_iv = (ImageView) popView.findViewById(R.id.dialog_dismiass_iv);
        StickyGridHeadersGridView gridView = (StickyGridHeadersGridView) popView.findViewById(R.id.asset_grid);
        TextView haveDone_tv = (TextView) popView.findViewById(R.id.haveDone_pop_tv);
        TextView noDone_tv = (TextView) popView.findViewById(R.id.noDone_pop_tv);
        haveDone_tv.setText("已作答" + newDoNum);
        noDone_tv.setText("未答" + (size - newDoNum));
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参值
        p.height = (int) (d.getHeight() * 0.85); // 高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.8
        p.alpha = 1.0f; // 设置本身透明度
        p.dimAmount = 0.0f; // 设置黑暗度
        popUpWindow1 = new PopupWindow(popView, p.width, p.height, true);
        popUpWindow1.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        popUpWindow1.setOutsideTouchable(true);
        popUpWindow1.setFocusable(true);
        popUpWindow1.setAnimationStyle(R.style.take_photo_anim);
        // 配合 点击外部区域消失使用 否则 没有效果
        ColorDrawable colorDrawable = new ColorDrawable(0x3cffffff);
        dialogDissmiss_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpWindow1.dismiss();
            }
        });
        popUpWindow1.setBackgroundDrawable(colorDrawable);
        //展示
        popUpWindow1.showAtLocation(popView, Gravity.BOTTOM, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        List<TestResultBean.ExamDetailsBean> oneList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> twoList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> threeList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> fourList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> fiveList = new ArrayList<>();
        List<TestResultBean.ExamDetailsBean> sixList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            TestResultBean.ExamDetailsBean examDetailsBean = listToPost.get(i);
            int questionType = examDetailsBean.getQuestionType();
            String userAnswerIds = examDetailsBean.getUserAnswerIds();
            if (questionType == 1) {//单选
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                if (!TextUtils.isEmpty(userAnswerIds)) {
                    if (!userAnswerIds.equals("0")) {
                        examDetailsBean1.setIsRight(1);
                    }
                }
                examDetailsBean1.setLocation(j);
                oneList.add(examDetailsBean1);

            } else if (questionType == 2) {//多选
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (!TextUtils.isEmpty(userAnswerIds)) {
                    if (!userAnswerIds.equals("0")) {
                        examDetailsBean1.setIsRight(1);
                    }
                }
                twoList.add(examDetailsBean1);

            } else if (questionType == 3) {//判断
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (!TextUtils.isEmpty(userAnswerIds)) {
                    if (!userAnswerIds.equals("0")) {
                        examDetailsBean1.setIsRight(1);
                    }
                }
                threeList.add(examDetailsBean1);

            } else if (questionType == 4) {//主观案例题
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (!TextUtils.isEmpty(userAnswerIds)) {
                    if (!userAnswerIds.equals("0")) {
                        examDetailsBean1.setIsRight(1);
                    }
                }
                fourList.add(examDetailsBean1);
            } else if (questionType == 5) {//客观案例题
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (!TextUtils.isEmpty(userAnswerIds)) {
                    if (!userAnswerIds.equals("0")) {
                        examDetailsBean1.setIsRight(1);
                    }
                }
                fiveList.add(examDetailsBean1);
            } else if (questionType == 6) {//简答题
                int j = i + 1;
                TestResultBean.ExamDetailsBean examDetailsBean1 = new TestResultBean.ExamDetailsBean();
                examDetailsBean1.setLocation(j);
                if (!TextUtils.isEmpty(userAnswerIds)) {
                    if (!userAnswerIds.equals("0")) {
                        examDetailsBean1.setIsRight(1);
                    }
                }
                sixList.add(examDetailsBean1);
            }
        }
        List<GridItem> mGirdList = new ArrayList<GridItem>();
        GridItem item1 = null;
        GridItem item2 = null;
        GridItem item3 = null;
        GridItem item4 = null;
        GridItem item5 = null;
        GridItem item6 = null;
        int size1 = oneList.size();
        int size2 = twoList.size();
        int size3 = threeList.size();
        int size4 = fourList.size();
        int size5 = fiveList.size();
        int size6 = sixList.size();
        if (size1 > 0) {
            for (int i = 0; i < size1; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = oneList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                Log.e("isRight", isRight + "单选,11111");
                item1 = new GridItem(location + "," + isRight, i + "", 0, "单选", isRight);
                mGirdList.add(item1);
            }
        }
        if (size2 > 0) {
            for (int i = 0; i < size2; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = twoList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item2 = new GridItem(location + "," + isRight, i + "", 1, "多选", isRight);
                mGirdList.add(item2);
                Log.e("isRight", isRight + "多选,11111");
            }
        }
        if (size3 > 0) {
            for (int i = 0; i < size3; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = threeList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item3 = new GridItem(location + "," + isRight, i + "", 2, "判断", isRight);
                Log.e("isRight", isRight + "判断,11111");
                mGirdList.add(item3);
            }
        }
        if (size4 > 0) {
            for (int i = 0; i < size4; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = fourList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item4 = new GridItem(location + "," + isRight, i + "", 3, "主观案例题", isRight);
                Log.e("isRight", isRight + "主观案例题,11111");
                mGirdList.add(item4);
            }
        }
        if (size5 > 0) {
            for (int i = 0; i < size5; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = fiveList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item5 = new GridItem(location + "," + isRight, i + "", 4, "客观案例题", isRight);
                Log.e("isRight", isRight + "客观案例题,11111");
                mGirdList.add(item5);
            }
        }
        if (size6 > 0) {
            for (int i = 0; i < size6; i++) {
                TestResultBean.ExamDetailsBean examDetailsBean = sixList.get(i);
                int location = examDetailsBean.getLocation();
                int isRight = examDetailsBean.getIsRight();
                item6 = new GridItem(location + "," + isRight, i + "", 5, "简答题", isRight);
                Log.e("isRight", isRight + "客观案例题,11111");
                mGirdList.add(item6);
            }
        }
        stickyGridAdapter = new StickyGridAdapter(TestContentActivity1.this, mGirdList, recyclerview, popUpWindow1, madapter, size, dijige);
        gridView.setAdapter(stickyGridAdapter);
    }
    //弹框考试次数已经用完
    private void initPopUpWindow1() {
        View view = View.inflate(TestContentActivity1.this, R.layout.test_pop_layout, null);
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
                Intent intent = new Intent(TestContentActivity1.this, TestDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("quesLibId", quesLibId);
                intent.putExtras(bundle);
                TestContentActivity1.this.startActivity(intent);
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(popupWindow !=null && popupWindow.isShowing()){
                popupWindow.dismiss();
            }
            showDialog();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void showDialog() {
        if (!isCommit) {
            int size = listToPost.size();
            int doNum = 0;
            for (int i = 0; i < size; i++) {
                int isRight = listToPost.get(i).getIsRight();
                String userAnswerIds = listToPost.get(i).getUserAnswerIds();
                if (!TextUtils.isEmpty(userAnswerIds)) {
                    doNum++;
                }
                if (isRight == 1) {
                    rightNum++;
                } else {
                    rongNum++;
                }
            }
            //   correctRate = (int) ((rightNum/(size))*100);

            popView = LayoutInflater.from(TestContentActivity1.this).inflate(R.layout.tanchukuang, null, false);
            TextView tvtvtv1 = popView.findViewById(R.id.tvtvtv1);
            TextView zuoti = popView.findViewById(R.id.zuoti);
            tijiao = popView.findViewById(R.id.tijiao);

            if (doNUm == TestContentActivity1.this.size) {
                tvtvtv1.setText("您已完成全部试卷");
            } else {
                double shengYuNum = size - doNUm;
                if (shengYuNum > 0) {
                    tvtvtv1.setText("您还有" + (size - doNum) + "道题未做,还剩" + (shengyu / 60) + "分钟,是否确认提交?");
                } else {
                    tvtvtv1.setText("您还剩" + 0 + "道题未做,还剩" + (shengyu / 60) + "分钟,是否确认提交?");
                }
            }
            useTime = test(examTime * 60 - shengyu);
            popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
            popupWindow.setTouchable(true);
            // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
            popupWindow.setOutsideTouchable(true);
            // 配合 点击外部区域消失使用 否则 没有效果
            zuoti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
            //点击提交按钮时
            tijiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isCommit = true;
                    Log.e("iscommit", isCommit + "");
                    try {
                        if (isFreeTest == true) {
                            initFreeTestDialog();
                            popupWindow.dismiss();

                        } else {
                            //上传做题结果
                            initToPostJsonData();
                            // madapter.setRightNum(0);
                            //  madapter.setRongNum(0);
                            //  finish();
                            popupWindow.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //做题个数
                }

            });

            popupWindow.showAtLocation(popView, Gravity.CENTER, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }
    }

}
