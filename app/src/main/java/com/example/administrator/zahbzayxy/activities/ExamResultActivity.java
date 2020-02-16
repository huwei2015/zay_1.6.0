package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.ExamResultBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019/7/9.
 * Time 11:32.
 * Description.考试结果
 */
public class ExamResultActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_score, exam_time, qualified, exam_name, exam_idcard, tv_exam, exam_title, tv_desc;
    private ImageView exam_bg, exam_archives_back;
    private int examScoreId;
    private ExamResultBean.ResultBean resultBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result);
        double scoreId = getIntent().getDoubleExtra("examScoreId", 0.0);
        examScoreId = Integer.valueOf((int) scoreId);
        initView();
        initExamResult();
    }

    //请求考试接口
    private void initExamResult() {
        TestGroupInterface testGroupInterface = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        String token = tokenDb.getString("token", "");
        testGroupInterface.getExamResult(token, examScoreId).enqueue(new Callback<ExamResultBean>() {
            @Override
            public void onResponse(Call<ExamResultBean> call, Response<ExamResultBean> response) {
                if (response != null && response.body() != null) {
                    String code = response.body().getCode();
                    if (code.equals("00000")) {
                        resultBean = response.body().getData();
                        exam_title.setText(resultBean.getExamName());
                        if (resultBean.isPassed()) {//考试合格
                            tv_score.setText(String.valueOf(resultBean.getExamScore()));
                            exam_time.setText(resultBean.getExamDate());
//                            qualified.setText(resultBean.getPassMsg());
                            exam_name.setText(resultBean.getUserName());
                            exam_idcard.setText(resultBean.getIdCard());
                            qualified.setText("合格");
                            qualified.setTextColor(getResources().getColor(R.color.text_green));
                            tv_desc.setText("考试通过");
                            tv_desc.setTextColor(getResources().getColor(R.color.text_green));
                            tv_exam.setText("查看试卷");
                            tv_exam.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ExamResultActivity.this, TestLookHaveDoneActivity.class);
                                    Bundle bundle = new Bundle();
                                    Integer integer = Integer.valueOf((int) examScoreId);
                                    Log.e("getExamScoreId", examScoreId + ",00");
                                    bundle.putInt("examScoreId", integer);
                                    if (!TextUtils.isEmpty(resultBean.getExamName())) {
                                        bundle.putString("paperName", resultBean.getExamName());
                                    }
                                    bundle.putInt("testType", 2);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                            tv_exam.setBackgroundColor(getResources().getColor(R.color.text_green));
                            exam_bg.setBackground(getResources().getDrawable(R.mipmap.icon_score));

                        } else {
                            tv_score.setText(String.valueOf(resultBean.getExamScore()));
                            exam_time.setText(resultBean.getExamDate());
                            qualified.setText(resultBean.getPassMsg());
                            exam_name.setText(resultBean.getUserName());
                            exam_idcard.setText(resultBean.getIdCard());
                            exam_bg.setBackground(getResources().getDrawable(R.mipmap.icon_no_score));
                            qualified.setText("不合格");
                            qualified.setTextColor(getResources().getColor(R.color.text_ange));
                            tv_desc.setText("请前往考试中心,重新考试");
                            tv_desc.setTextColor(getResources().getColor(R.color.text_ange));
                            tv_exam.setText("重新考试");
                            tv_exam.setBackgroundColor(getResources().getColor(R.color.text_ange));
                            tv_exam.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //前往mainNewActivity里面查找11
                                    Intent intent1 = new Intent(ExamResultActivity.this, MyExamActivity.class);
                                    startActivity(intent1);
                                    EventBus.getDefault().post(11);
                                    finish();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ExamResultBean> call, Throwable t) {
                String errMsg = t.getMessage();
                Toast.makeText(ExamResultActivity.this, errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        tv_score = (TextView) findViewById(R.id.tv_score);//考试分数
        exam_time = (TextView) findViewById(R.id.exam_data);//考试日期
        qualified = (TextView) findViewById(R.id.qualified);//考试是否合格
        exam_name = (TextView) findViewById(R.id.tv_name);//考试姓名
        exam_idcard = (TextView) findViewById(R.id.tv_idcard);//考试人身份证号
        tv_exam = (TextView) findViewById(R.id.tv_exam);//是否重新考试
        exam_bg = (ImageView) findViewById(R.id.img_exam_bg);//背景图片
        exam_title = (TextView) findViewById(R.id.exam_title);//标题
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        exam_archives_back = (ImageView) findViewById(R.id.exam_archives_back);//返回键
        exam_archives_back.setOnClickListener(this);
        tv_exam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exam_archives_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
