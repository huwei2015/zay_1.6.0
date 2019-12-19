package com.example.administrator.zahbzayxy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.TestContentBean;
import com.example.administrator.zahbzayxy.beans.TestResultBean;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/20 0020.
 */
public class TestContentAdapter extends RecyclerView.Adapter<TestContentAdapter.MyTestViewHold> implements View.OnClickListener {
    boolean isSelected;
    Context context;
    LayoutInflater inflater;
    List<TestContentBean.DataBean.QuesDataBean> list;
    //查看用户结果的list
    private static List<TestResultBean.ExamDetailsBean> listResult;
    //保存用户成绩的list
    private static List<TestResultBean.ExamDetailsBean> listToPost;
    private int optSize;
    private int size;
    private int w, h;
    private int weiZhi;
    private MyTestViewHold mViewHold;
    private int quesType;
    private List<TestContentBean.DataBean.QuesDataBean.OptsBean> opts;
    private TestContentBean.DataBean.QuesDataBean quesDataBean;
    private StringBuffer stringBuffer;
    private static float rightNum = (float) 0.0;
    private static float rongNum = (float) 0.0;
    private int singleScore, postSingleScore;
    private int multipleScore, postMultipleScore;
    private int judgeScore, postJudgeScore;
    private MyRecyclerView recyclerView;
    private TextView dijige;
//    public static float getRightNum() {
//        return rightNum;
//    }
//    public static float getRongNum() {
//        return rongNum;
//    }
//    public static void setRightNum(float rightNum) {
//        TestContentAdapter.rightNum = rightNum;
//    }
//    public static void setRongNum(float rongNum) {
//        TestContentAdapter.rongNum = rongNum;
//    }

    public void setSingleScore(int singleScore) {
        this.singleScore = singleScore;
    }

    public void setMultipleScore(int multipleScore) {
        this.multipleScore = multipleScore;
    }

    public void setJudgeScore(int judgeScore) {
        this.judgeScore = judgeScore;
    }

    //把做题的分数传到activity
    public int getSingleScore() {
        return postSingleScore;
    }

    public int getMultipleScore() {
        return postMultipleScore;
    }

    public int getJudgeScore() {
        return postJudgeScore;
    }

    public int getWeiZhi() {
        return weiZhi;
    }

    public static List<TestResultBean.ExamDetailsBean> getListToPost() {
        return listToPost;
    }

    public TestContentAdapter(Context context, List<TestContentBean.DataBean.QuesDataBean> list, List<TestResultBean.ExamDetailsBean> listToPost, List<TestResultBean.ExamDetailsBean> listResult, MyRecyclerView recyclerView, TextView dijige) {
        this.context = context;
        this.list = list;
        this.listToPost = listToPost;
        this.listResult = listResult;
        inflater = LayoutInflater.from(context);
        w = context.getResources().getDisplayMetrics().widthPixels;
        h = context.getResources().getDisplayMetrics().heightPixels;
        this.recyclerView = recyclerView;
        this.dijige = dijige;

    }

    @Override
    public MyTestViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_test_qustion_layout, parent, false);
        mViewHold = new MyTestViewHold(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(MyTestViewHold holder, int position) {
        holder.answer_layout.setVisibility(View.INVISIBLE);
        size = list.size();
        if (size > 0) {
            weiZhi = position;
        }
        quesType = list.get(position).getQuesType();
        opts = list.get(position).getOpts();
        quesDataBean = list.get(position);
        //没提选项的大小
        optSize = opts.size();
        if (optSize > 0) {
            fuyong(position);
            holder.rbA.setVisibility(View.VISIBLE);
            holder.rbB.setVisibility(View.VISIBLE);
        }
        if (quesType == 1) {//单选
            //每个题目的集合
            holder.selectorType_tv.setText("[" + "选择题" + "]");
            holder.tv.setText(list.get(position).getContent());
            holder.tijiao.setVisibility(View.INVISIBLE);
            holder.rbE.setVisibility(View.INVISIBLE);
            holder.rbF.setVisibility(View.INVISIBLE);
            if (optSize == 3) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
            } else if (optSize == 2) {
                holder.rbC.setVisibility(View.INVISIBLE);
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
            } else if (optSize == 4) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
            } else if (optSize == 5) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());
            } else if (optSize == 6) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());
                holder.rbF.setText(opts.get(5).getContent());
            }
        } else if (quesType == 2) {//多选
            holder.selectorType_tv.setText("[" + "多选题" + "]");
            holder.tv.setText(list.get(position).getContent());
            mViewHold.tijiao.setVisibility(View.VISIBLE);
            holder.rbC.setVisibility(View.VISIBLE);
            holder.rbD.setVisibility(View.VISIBLE);
            if (optSize == 4) {
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbE.setText("");
                holder.rbF.setText("");
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
            } else if (optSize == 3) {
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbE.setText("");
                holder.rbF.setText("");
                holder.rbD.setText("");
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
            } else if (optSize == 5) {
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbF.setText("");
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());

            } else if (optSize == 6) {
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());
                holder.rbF.setText(opts.get(5).getContent());
            }
        } else {//判断
            holder.selectorType_tv.setText("[" + "判断题" + "]");
            holder.tv.setText(list.get(position).getContent());
            holder.tijiao.setVisibility(View.INVISIBLE);
            if (optSize > 1) {
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
            }
            holder.rbC.setVisibility(View.INVISIBLE);
            holder.rbD.setVisibility(View.INVISIBLE);
        }
        mViewHold.rbA.setOnClickListener(this);
        mViewHold.rbB.setOnClickListener(this);
        mViewHold.rbC.setOnClickListener(this);
        mViewHold.rbD.setOnClickListener(this);
        mViewHold.rbE.setOnClickListener(this);
        mViewHold.rbF.setOnClickListener(this);
        mViewHold.tijiao.setOnClickListener(this);

    }

    //点击a.b.c.d四个按钮时要做下标记是否做过
    public void onClick(View v) {
        //提交的
        TestResultBean.ExamDetailsBean resultToPost = listToPost.get(weiZhi);
        if (quesType == 1) {//单选
            switch (v.getId()) {
                case R.id.radioButtonA:
                    opts.get(0).setTag(1);
                    opts.get(1).setTag(0);
                    if (optSize > 2) {
                        opts.get(2).setTag(0);
                    }

                    if (opts.get(0).getIsRightAnswer() == 0) {
                        resultToPost.setIsRight(0);
                        ++rongNum;
                    } else {
                        resultToPost.setIsRight(1);
                        rightNum++;
                        postSingleScore += singleScore;
                    }
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                    resultToPost.setUserAnswerIds(String.valueOf(opts.get(0).getId()));
                    break;
                case R.id.radioButtonB:
                    opts.get(1).setTag(1);
                    opts.get(0).setTag(0);
                    if (optSize > 2) {
                        opts.get(2).setTag(0);
                    }
                    if (opts.get(1).getIsRightAnswer() == 0) {
                        resultToPost.setIsRight(0);
                        rongNum++;
                    } else {
                        resultToPost.setIsRight(1);
                        rightNum++;
                        postSingleScore += singleScore;
                    }
                    resultToPost.setUserAnswerIds(String.valueOf(opts.get(1).getId()));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));

                    break;
                case R.id.radioButtonC:
                    opts.get(0).setTag(0);
                    opts.get(1).setTag(0);
                    opts.get(2).setTag(1);

                    if (opts.get(2).getIsRightAnswer() == 0) {
                        resultToPost.setIsRight(0);
                        ++rongNum;
                    } else {
                        resultToPost.setIsRight(1);
                        rightNum++;
                        postSingleScore += singleScore;
                    }

                    resultToPost.setUserAnswerIds(String.valueOf(opts.get(2).getId()));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                    break;

                case R.id.radioButtonD:
                    opts.get(0).setTag(0);
                    opts.get(1).setTag(0);
                    opts.get(2).setTag(0);
                    opts.get(3).setTag(1);
                    if (opts.get(3).getIsRightAnswer() == 0) {
                        resultToPost.setIsRight(0);
                        ++rongNum;
                    } else {
                        resultToPost.setIsRight(1);
                        rightNum++;
                        postSingleScore += singleScore;
                    }
                    resultToPost.setUserAnswerIds(String.valueOf(opts.get(3).getId()));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    break;

                case R.id.radioButtonE:
                    opts.get(0).setTag(0);
                    opts.get(1).setTag(0);
                    opts.get(2).setTag(0);
                    opts.get(3).setTag(0);
                    opts.get(4).setTag(1);
                    if (opts.get(4).getIsRightAnswer() == 0) {
                        resultToPost.setIsRight(0);
                        ++rongNum;
                    } else {
                        resultToPost.setIsRight(1);
                        rightNum++;
                        postSingleScore += singleScore;
                    }
                    resultToPost.setUserAnswerIds(String.valueOf(opts.get(4).getId()));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    break;

                case R.id.radioButtonF:
                    opts.get(0).setTag(0);
                    opts.get(1).setTag(0);
                    opts.get(2).setTag(0);
                    opts.get(3).setTag(0);
                    opts.get(4).setTag(0);
                    opts.get(5).setTag(1);
                    if (opts.get(5).getIsRightAnswer() == 0) {
                        resultToPost.setIsRight(0);
                        ++rongNum;
                    } else {
                        resultToPost.setIsRight(1);
                        rightNum++;
                        postSingleScore += singleScore;
                    }
                    resultToPost.setUserAnswerIds(String.valueOf(opts.get(5).getId()));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    break;
            }

            resultToPost.setQuestionType(1);
            resultToPost.setQuestionId(quesDataBean.getId());

            Log.e("singleScore", postSingleScore + "");


            recyclerView.scrollToPosition(weiZhi + 1);
            notifyDataSetChanged();
            if (weiZhi + 1 == size) {
                dijige.setText((weiZhi + 1) + "/" + size);
            } else {

                dijige.setText((weiZhi + 2) + "/" + size);
            }


        } else {
            if (quesType == 2) {//多选
                switch (v.getId()) {
                    case R.id.radioButtonA:


                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.lightBlue));
                        resultToPost.setQuestionId(opts.get(0).getQuesId());
                        break;
                    case R.id.radioButtonB:
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.lightBlue));
                        resultToPost.setQuestionId(opts.get(1).getQuesId());

                        break;
                    case R.id.radioButtonC:

                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.lightBlue));
                        resultToPost.setQuestionId(opts.get(2).getQuesId());

                        break;
                    case R.id.radioButtonD:

                        if (optSize >= 4) {

                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.lightBlue));
                            resultToPost.setQuestionId(opts.get(3).getQuesId());
                        }
                        break;
                    case R.id.radioButtonE:


                        mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.lightBlue));
                        resultToPost.setQuestionId(opts.get(4).getQuesId());
                        break;
                    case R.id.radioButtonF:


                        mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.lightBlue));
                        resultToPost.setQuestionId(opts.get(5).getQuesId());
                        break;
                    case R.id.tijiao:
                        //isSelected=true;
                        mViewHold.tijiao.setText("提交");
                        quesDataBean.setBiaoJi(1);
                        // mViewHold.tijiao.setEnabled(false);


                        StringBuffer userQusIdBuffer = new StringBuffer();
                        StringBuffer qusIdBuffer = new StringBuffer();


                        if (mViewHold.rbA.isChecked()) {
                            userQusIdBuffer.append(opts.get(0).getId() + ",");
                            opts.get(0).setTag(1);
                        }
                        if (mViewHold.rbB.isChecked()) {
                            userQusIdBuffer.append(opts.get(1).getId() + ",");
                            opts.get(1).setTag(1);
                        }
                        if (mViewHold.rbC.isChecked()) {
                            userQusIdBuffer.append(opts.get(2).getId() + ",");
                            opts.get(2).setTag(1);
                        }
                        if (mViewHold.rbD.isChecked()) {
                            userQusIdBuffer.append(opts.get(3).getId() + ",");
                            opts.get(3).setTag(1);
                        }
                        if (mViewHold.rbE.isChecked()) {
                            userQusIdBuffer.append(opts.get(4).getId() + ",");
                            opts.get(4).setTag(1);
                        }
                        if (mViewHold.rbF.isChecked()) {
                            userQusIdBuffer.append(opts.get(5).getId() + ",");
                            opts.get(5).setTag(1);

                        }
                        resultToPost.setQuestionId(quesDataBean.getId());
                        resultToPost.setUserAnswerIds((userQusIdBuffer.toString()));
                        for (int i = 0; i < opts.size(); i++) {
                            if (opts.get(i).getIsRightAnswer() == 1) {
                                qusIdBuffer.append(opts.get(i).getId());
                            }
                        }
                        String userAnswer = userQusIdBuffer.toString();
                        if (TextUtils.isEmpty(userAnswer)) {
                            Toast.makeText(context, "请作答后再提交", Toast.LENGTH_SHORT).show();
                        } else {
                            // quesDataBean.setBiaoJi(1);
                            mViewHold.rbA.setEnabled(false);
                            mViewHold.rbB.setEnabled(false);
                            mViewHold.rbC.setEnabled(false);
                            mViewHold.rbD.setEnabled(false);
                            mViewHold.rbE.setEnabled(false);
                            mViewHold.rbF.setEnabled(false);
                            recyclerView.scrollToPosition(weiZhi + 1);
                            notifyDataSetChanged();
                            if (weiZhi + 1 == size) {
                                dijige.setText((weiZhi + 1) + "/" + size);
                            } else {

                                dijige.setText((weiZhi + 2) + "/" + size);
                            }
                        }
                        String qusAnswer = qusIdBuffer.toString();
                        String[] split = userAnswer.split(",");
                        String[] split1 = qusAnswer.split(",");
                        Arrays.sort(split);
                        Arrays.sort(split1);
                        String userAnserNew = null, qusAnswerNew = null;
                        int length = split.length;
                        int length1 = split1.length;
                        for (int i = 0; i < length; i++) {
                            String s = split[i];
                            userAnserNew += s;
                        }
                        for (int i = 0; i < length1; i++) {
                            String s = split1[i];
                            qusAnswerNew += s;
                        }
                        //判断提交是否做正确
                        if (qusAnswerNew.equals(userAnserNew)) {
                            resultToPost.setIsRight(1);
                            postMultipleScore += multipleScore;
                        } else {
                            resultToPost.setIsRight(0);
                        }
                        Log.e("qId", qusIdBuffer + "");
                        Log.e("qId", userQusIdBuffer + "," + "user");
                        break;
                }
                resultToPost.setQuestionType(2);

                Log.e("multiScore", postMultipleScore + "");
            } else {//判断
                switch (v.getId()) {
                    case R.id.radioButtonA:
                        opts.get(0).setTag(1);
                        opts.get(1).setTag(0);
                        if (opts.get(0).getIsRightAnswer() == 0) {
                            resultToPost.setIsRight(0);
                            ++rongNum;
                        } else {
                            resultToPost.setIsRight(1);
                            ++rightNum;
                            postJudgeScore += judgeScore;
                        }
                        resultToPost.setUserAnswerIds(String.valueOf(opts.get(0).getId()));
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.lightBlue));
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                        break;
                    case R.id.radioButtonB:
                        opts.get(1).setTag(1);
                        opts.get(0).setTag(0);
                        if (opts.get(1).getIsRightAnswer() == 0) {
                            resultToPost.setIsRight(0);
                            ++rongNum;
                        } else {
                            resultToPost.setIsRight(1);
                            ++rightNum;
                            postJudgeScore += judgeScore;
                        }
                        resultToPost.setUserAnswerIds(String.valueOf(opts.get(1).getId()));
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.lightBlue));
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                        break;
                }
                resultToPost.setQuestionId(quesDataBean.getId());
                resultToPost.setQuestionType(3);
                resultToPost.setTag(1);
                Log.e("jungerScore", postJudgeScore + "");
                recyclerView.scrollToPosition(weiZhi + 1);
                notifyDataSetChanged();
                if (weiZhi + 1 == size) {
                    dijige.setText((weiZhi + 1) + "/" + size);
                } else {

                    dijige.setText((weiZhi + 2) + "/" + size);
                }

            }
        }
    }


    private void fuyong(int position) {
        if (quesType == 1) {//单选
            mViewHold.rbA.setChecked(false);
            mViewHold.rbB.setChecked(false);
            mViewHold.rbC.setChecked(false);
            mViewHold.rbD.setChecked(false);
            mViewHold.rbE.setVisibility(View.INVISIBLE);
            mViewHold.rbF.setVisibility(View.INVISIBLE);
            if (opts.get(0).getTag() == 1) {
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.lightBlue));
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
            } else {
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
            }
            if (opts.get(1).getTag() == 1) {
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.lightBlue));
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
            } else {
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
            }
            if (optSize > 2) {
                if (opts.get(2).getTag() == 1) {
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                } else {
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                }
            }
            if (optSize > 3) {
                if (opts.get(3).getTag() == 1) {
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                } else {
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                }
            }
            if (optSize > 4) {
                if (opts.get(4).getTag() == 1) {
                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                } else {
                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
                }
            }
            if (optSize > 5) {
                if (opts.get(5).getTag() == 1) {
                    mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                } else {
                    mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.testBlack));
                }
            }
        } else if (quesType == 2) {//多选
            mViewHold.rbA.setChecked(false);
            mViewHold.rbB.setChecked(false);
            mViewHold.rbC.setChecked(false);
            mViewHold.rbD.setChecked(false);
            mViewHold.rbE.setChecked(false);
            mViewHold.rbF.setChecked(false);
            if (quesDataBean.getBiaoJi() == 1) {
                mViewHold.rbA.setEnabled(false);
                mViewHold.rbB.setEnabled(false);
                mViewHold.rbC.setEnabled(false);
                mViewHold.rbD.setEnabled(false);
                mViewHold.rbE.setEnabled(false);
                mViewHold.rbF.setEnabled(false);
                mViewHold.tijiao.setEnabled(false);
            } else {
                mViewHold.rbA.setEnabled(true);
                mViewHold.rbB.setEnabled(true);
                mViewHold.rbC.setEnabled(true);
                mViewHold.rbD.setEnabled(true);
                mViewHold.rbE.setEnabled(true);
                mViewHold.rbF.setEnabled(true);
                mViewHold.tijiao.setEnabled(true);
            }

            if (opts.get(0).getTag() == 1) {
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.lightBlue));
            } else {
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
            }
            if (opts.get(1).getTag() == 1) {
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.lightBlue));
            } else {
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
            }

            if (opts.get(2).getTag() == 1) {
                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.lightBlue));
            } else {
                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
            }
            if (optSize == 4) {
                fuyong4();
            }
            if (optSize == 5) {
                fuyong5();
            }
            if (optSize == 6) {
                fuyong6();
            }

        } else {//判断
            mViewHold.rbA.setChecked(false);
            mViewHold.rbB.setChecked(false);
            if (quesDataBean.getBiaoJi() == 1) {


                mViewHold.rbA.setEnabled(false);
                mViewHold.rbB.setEnabled(false);

            } else {
                mViewHold.rbA.setEnabled(true);
                mViewHold.rbB.setEnabled(true);

            }
            if (opts.get(0).getTag() == 1) {
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.lightBlue));
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
            } else {
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
            }

            if (opts.get(1).getTag() == 1) {
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.lightBlue));
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
            } else {
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
            }
        }
    }

    private void fuyong4() {
        if (opts.get(3).getTag() == 1) {
            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.lightBlue));
        } else {
            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
        }
    }

    private void fuyong6() {
        fuyong5();
        if (opts.get(5).getTag() == 1) {
            mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.lightBlue));
        } else {
            mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.testBlack));
        }
    }

    private void fuyong5() {
        fuyong4();
        if (opts.get(4).getTag() == 1) {
            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.lightBlue));
        } else {
            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
        }
    }

    @Override
    public int getItemCount() {
        int size = list.size();
        return size > 0 ? size : 0;
    }

    static class MyTestViewHold extends RecyclerView.ViewHolder {
        TextView tv, selectorType_tv;
        LinearLayout ll;
        RadioButton rbA, rbB, rbC, rbD, rbE, rbF;
        TextView tijiao;
        RelativeLayout answer_layout;
        TextView anser_tv, parsing_tv;

        @SuppressLint("WrongViewCast")
        public MyTestViewHold(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView);
            ll = (LinearLayout) itemView.findViewById(R.id.jjjjjj);
            rbA = (RadioButton) itemView.findViewById(R.id.radioButtonA);
            rbB = (RadioButton) itemView.findViewById(R.id.radioButtonB);
            rbC = (RadioButton) itemView.findViewById(R.id.radioButtonC);
            rbD = (RadioButton) itemView.findViewById(R.id.radioButtonD);
            rbE = (RadioButton) itemView.findViewById(R.id.radioButtonE);
            rbF = (RadioButton) itemView.findViewById(R.id.radioButtonF);
            tijiao = (TextView) itemView.findViewById(R.id.tijiao);
            answer_layout = (RelativeLayout) itemView.findViewById(R.id.answer_layout);

            anser_tv = (TextView) itemView.findViewById(R.id.anwer_choice);
            parsing_tv = (TextView) itemView.findViewById(R.id.detailAnswer_tv);
            selectorType_tv = (TextView) itemView.findViewById(R.id.selectorType_tv);

        }
    }
}
