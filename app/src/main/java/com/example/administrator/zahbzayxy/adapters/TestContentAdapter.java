package com.example.administrator.zahbzayxy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.NewTestContentBean;
import com.example.administrator.zahbzayxy.beans.TestContentBean;
import com.example.administrator.zahbzayxy.beans.TestResultBean;
import com.example.administrator.zahbzayxy.myviews.EditTextWithScrollView;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;
import com.example.administrator.zahbzayxy.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/3/20 0020.
 */
public class TestContentAdapter extends RecyclerView.Adapter<TestContentAdapter.MyTestViewHold> implements View.OnClickListener {
    boolean isSelected;
    Context context;
    LayoutInflater inflater;
    List<NewTestContentBean.DataBean.QuesDataBean> list;
    //查看用户结果的list
    private static List<TestResultBean.ExamDetailsBean> listResult;
    //保存用户成绩的list
    private static List<TestResultBean.ExamDetailsBean> listToPost;
    private int optSize;
    private int size;
    private int w, h;
    private int weiZhi;
    private MyTestViewHold mViewHold;
    private int mQuesType;
    private List<NewTestContentBean.DataBean.QuesDataBean.OptsBean> opts;
    private NewTestContentBean.DataBean.QuesDataBean quesDataBean;
    private StringBuffer stringBuffer;
    private static float rightNum = (float) 0.0;
    private static float rongNum = (float) 0.0;
    private int singleScore, postSingleScore;
    private int multipleScore, postMultipleScore;
    private int judgeScore, postJudgeScore;
    // 主观案例
    private int mFactScore, mPostFactScore;
    // 可观案例
    private int mNotFactScore, mPostNotFactScore;
    // 简单题
    private int mShortScore, mPostShortScore;
    private MyRecyclerView recyclerView;
    private TextView dijige;
    private RadioButton[] mSelectArr = new RadioButton[6];

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

    public void setFactScore(int factScore) {
        this.mFactScore = factScore;
    }

    public void setNotFactScore(int notFactScore) {
        this.mNotFactScore = notFactScore;
    }

    public void setShortScore(int shortScore) {
        this.mShortScore = shortScore;
    }

    public void setSingleScore(int singleScore) {
        this.singleScore = singleScore;
    }

    public void setMultipleScore(int multipleScore) {
        this.multipleScore = multipleScore;
    }

    public void setJudgeScore(int judgeScore) {
        this.judgeScore = judgeScore;
    }

    public int getPostFactScore(){
        return this.mPostFactScore;
    }

    public int getPostNotFactScore(){
        return this.mPostNotFactScore;
    }

    public int getPostShortScore(){
        return this.mPostShortScore;
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

    public List<NewTestContentBean.DataBean.QuesDataBean> getDataList() {
        return this.list;
    }

    public static List<TestResultBean.ExamDetailsBean> getListToPost() {
        return listToPost;
    }

    public TestContentAdapter(Context context, List<NewTestContentBean.DataBean.QuesDataBean> list, List<TestResultBean.ExamDetailsBean> listToPost, List<TestResultBean.ExamDetailsBean> listResult, MyRecyclerView recyclerView, TextView dijige) {
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
    public int getItemViewType(int position) {
        int type = 0;
        if (list != null && list.size() > 0) {
            int quesType = list.get(position).getQuesType();
            if (quesType == 6 || quesType == 4) {
                type = 1;
            }
        }
        return type;
    }

    @Override
    public MyTestViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 1) {
            view = inflater.inflate(R.layout.item_test_question_short_layout, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_test_qustion_layout, parent, false);
        }
        mViewHold = new MyTestViewHold(view, viewType);
        return mViewHold;
    }

    private void setSelectArr(MyTestViewHold hold) {
        mSelectArr[0] = hold.rbA;
        mSelectArr[1] = hold.rbB;
        mSelectArr[2] = hold.rbC;
        mSelectArr[3] = hold.rbD;
        mSelectArr[4] = hold.rbE;
        mSelectArr[5] = hold.rbF;
    }

    @Override
    public void onBindViewHolder(MyTestViewHold holder, int position) {
        holder.setIsRecyclable(false);
        int quesType = list.get(position).getQuesType();
        mQuesType = quesType;
        Log.i("=====adapter====", "position = " + position + "  quesType = " + quesType);
        if (quesType == 6 || quesType == 4) {
        } else {
            holder.answer_layout.setVisibility(View.INVISIBLE);
        }
        size = list.size();
        if (size > 0) {
            weiZhi = position;
        }
        opts = list.get(position).getOpts();
        quesDataBean = list.get(position);
        //没提选项的大小
        optSize = opts.size();
        if (quesType == 6 || quesType == 4) {
            fuyong(position, quesType, holder);
        } else {
            if (optSize > 0) {
                holder.rbA.setVisibility(View.VISIBLE);
                holder.rbB.setVisibility(View.VISIBLE);
            }
            setSelectArr(holder);
            fuyong(position, quesType, holder);
        }
        if (quesType != 4) {
            mChildPosition = -1;
        }
        if (quesType != 5) {
            mKePosition = -1;
        }
        if (quesType == 1) {//单选
            //每个题目的集合
            holder.selectorType_tv.setText("[" + "选择题" + "]");
            String content = list.get(position).getContent();
            holder.tv.setText(Html.fromHtml(content + "").toString());
            holder.tijiao.setVisibility(View.INVISIBLE);
            holder.rbE.setVisibility(View.INVISIBLE);
            holder.rbF.setVisibility(View.INVISIBLE);
            if (optSize == 3) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
                holder.rbC.setText(Html.fromHtml(opts.get(2).getContent()));
            } else if (optSize == 2) {
                holder.rbC.setVisibility(View.INVISIBLE);
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
            } else if (optSize == 4) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
                holder.rbC.setText(Html.fromHtml(opts.get(2).getContent()));
                holder.rbD.setText(Html.fromHtml(opts.get(3).getContent()));
            } else if (optSize == 5) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
                holder.rbC.setText(Html.fromHtml(opts.get(2).getContent()));
                holder.rbD.setText(Html.fromHtml(opts.get(3).getContent()));
                holder.rbE.setText(Html.fromHtml(opts.get(4).getContent()));
            } else if (optSize == 6) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.VISIBLE);
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
                holder.rbC.setText(Html.fromHtml(opts.get(2).getContent()));
                holder.rbD.setText(Html.fromHtml(opts.get(3).getContent()));
                holder.rbE.setText(Html.fromHtml(opts.get(4).getContent()));
                holder.rbF.setText(Html.fromHtml(opts.get(5).getContent()));
            }
        } else if (quesType == 2) {//多选
            holder.selectorType_tv.setText("[" + "多选题" + "]");
            holder.tv.setText(Html.fromHtml(list.get(position).getContent()));
            mViewHold.tijiao.setVisibility(View.VISIBLE);
            holder.rbC.setVisibility(View.VISIBLE);
            holder.rbD.setVisibility(View.VISIBLE);
            if (optSize == 4) {
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbE.setText("");
                holder.rbF.setText("");
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
                holder.rbC.setText(Html.fromHtml(opts.get(2).getContent()));
                holder.rbD.setText(Html.fromHtml(opts.get(3).getContent()));
            } else if (optSize == 3) {
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbE.setText("");
                holder.rbF.setText("");
                holder.rbD.setText("");
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
                holder.rbC.setText(Html.fromHtml(opts.get(2).getContent()));
            } else if (optSize == 5) {
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbF.setText("");
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
                holder.rbC.setText(Html.fromHtml(opts.get(2).getContent()));
                holder.rbD.setText(Html.fromHtml(opts.get(3).getContent()));
                holder.rbE.setText(Html.fromHtml(opts.get(4).getContent()));

            } else if (optSize == 6) {
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.VISIBLE);
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
                holder.rbC.setText(Html.fromHtml(opts.get(2).getContent()));
                holder.rbD.setText(Html.fromHtml(opts.get(3).getContent()));
                holder.rbE.setText(Html.fromHtml(opts.get(4).getContent()));
                holder.rbF.setText(Html.fromHtml(opts.get(5).getContent()));
            }
        } else if (quesType == 3) {//判断
            holder.selectorType_tv.setText("[" + "判断题" + "]");
            holder.tv.setText(Html.fromHtml(list.get(position).getContent()));
            holder.tijiao.setVisibility(View.INVISIBLE);
            if (optSize > 1) {
                holder.rbA.setText(Html.fromHtml(opts.get(0).getContent()));
                holder.rbB.setText(Html.fromHtml(opts.get(1).getContent()));
            }
            holder.rbC.setVisibility(View.INVISIBLE);
            holder.rbD.setVisibility(View.INVISIBLE);
        } else if (quesType == 4) { //主观案例
            mKePosition = -1;
            if (mChildPosition < 0) mChildPosition += 1;
            holder.questionTypeTv.setText("[" + "主观案例题" + "]");
            // 获取小题集合
            List<NewTestContentBean.DataBean.QuesDataBean> childrenList = list.get(position).getChildren();
            // 防止数组角标越界
            if (mChildPosition < 0 || mChildPosition >= childrenList.size()) return;
            // 小题的题干
            String childContent = childrenList.get(mChildPosition).getContent();
            String content = list.get(position).getContent() + "<br />" + (mChildPosition + 1) + "、" + childContent;
            holder.questionTitleTv.setText(Html.fromHtml(content));
            holder.answerEt.setHint("请输入主观案例题答案");
            mChildPosition++;
        } else if (quesType == 5) { //客观案例
            if (mKePosition < 0) mKePosition += 1;
            mChildPosition = -1;
            holder.selectorType_tv.setText("[" + "客观案例题" + "]");
            // 获取小题集合
            List<NewTestContentBean.DataBean.QuesDataBean> childrenList = list.get(position).getChildren();
            // 防止数组角标越界
            if (mKePosition < 0 || mKePosition >= childrenList.size()) return;
            // 获取小题的bean
            NewTestContentBean.DataBean.QuesDataBean quesDataBean = childrenList.get(mKePosition);
            // 小题的题干
            String childContent = quesDataBean.getContent();
            // 小题的选项集合
            List<NewTestContentBean.DataBean.QuesDataBean.OptsBean> optsList = quesDataBean.getOpts();
            // 重置选项的显示
            setShowSelectBtn(optsList);
            int localType = quesDataBean.getQuesType();
            String type = localType == 1 ? "[" + "单项题" + "]" : localType == 2 ? "[" + "多项题" + "]" : "[" + "判断题" + "]";
            if (localType == 2) { // 多选题的时候需要展示提交按钮
                holder.tijiao.setVisibility(View.VISIBLE);
            } else {
                holder.tijiao.setVisibility(View.INVISIBLE);
            }

            String content = list.get(position).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(content).append("<br />").append(type).append("<br />").append(mKePosition + 1).append("、").append(childContent);
            holder.tv.setText(Html.fromHtml(sb.toString()));
            mKePosition++;
        } else if (quesType == 6) { //简答
            holder.questionTypeTv.setText("[" + "简答题" + "]");
            holder.questionTitleTv.setText(Html.fromHtml(list.get(position).getContent()));
            holder.answerEt.setHint("请输入简答题答案");
        }

        if (quesType == 6 || quesType == 4) {
            mViewHold.submitTv.setOnClickListener(v -> {
                String answer = mViewHold.answerEt.getText().toString();
                if (TextUtils.isEmpty(answer)) {
                    ToastUtils.showShortInfo("请输入答案后提交");
                    return;
                }
                TestResultBean.ExamDetailsBean resultToPost = listToPost.get(weiZhi);
                if (quesType == 6) {//简单题
                    String userAnswerStr = resultToPost.getUserAnswerIds();
                    if (TextUtils.isEmpty(userAnswerStr)) {
                        // 只在第一次提交答案的时候加一次分数
                        mPostShortScore += mShortScore;
                    }
                    resultToPost.setIsRight(1);
                    // 这里直接设置了答案
                    resultToPost.setUserAnswerIds(answer);
                    resultToPost.setQuestionId(list.get(weiZhi).getId());
                    resultToPost.setQuestionType(6);
                    resultToPost.setTag(6);
                    listToPost.set(weiZhi, resultToPost);
                    recyclerView.scrollToPosition(weiZhi + 1);
                    notifyDataSetChanged();
                    if (weiZhi + 1 == size) {
                        dijige.setText((weiZhi + 1) + "/" + size);
                    } else {

                        dijige.setText((weiZhi + 2) + "/" + size);
                    }
                } else {//主管案例题
                    String userAnswerStr = resultToPost.getUserAnswerIds();
                    if (TextUtils.isEmpty(userAnswerStr)) {
                        mPostFactScore += (mFactScore / list.get(weiZhi).getChildren().size());
                    }
                    String answerPost = resultToPost.getUserAnswerIds();
                    try {
                        JSONObject json = null;
                        if (TextUtils.isEmpty(answerPost)) {
                            json = new JSONObject();
                        } else {
                            json = new JSONObject(answerPost);
                        }

                        json.put(String.valueOf(mChildPosition - 1), answer);
                        resultToPost.setIsRight(1);
                        resultToPost.setUserAnswerIds(json.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    resultToPost.setQuestionId(list.get(weiZhi).getId());
                    resultToPost.setQuestionType(4);
                    resultToPost.setTag(4);
                    listToPost.set(weiZhi, resultToPost);

                    int myPosition = weiZhi + 1;
                    if (mChildPosition >= 0) {
                        if (mChildPosition >= list.get(weiZhi).getChildren().size()) {
                            setChildPosition(-1);
                        } else {
                            myPosition -= 1;
                        }
                    }
                    recyclerView.scrollToPosition(myPosition);
                    notifyDataSetChanged();
                    if (myPosition == size) {
                        dijige.setText((myPosition) + "/" + size);
                    } else {

                        dijige.setText((myPosition + 1) + "/" + size);
                    }
                }
            });
        } else {
            mViewHold.rbA.setOnClickListener(this);
            mViewHold.rbB.setOnClickListener(this);
            mViewHold.rbC.setOnClickListener(this);
            mViewHold.rbD.setOnClickListener(this);
            mViewHold.rbE.setOnClickListener(this);
            mViewHold.rbF.setOnClickListener(this);
            mViewHold.tijiao.setOnClickListener(this);
        }
    }

    /**
     * 设置选择按钮展示
     *
     * @param optsList 展示的选项
     */
    private void setShowSelectBtn(List<NewTestContentBean.DataBean.QuesDataBean.OptsBean> optsList) {
        if (optsList == null) optsList = new ArrayList<>();
        for (int i = 0; i < mSelectArr.length; i++) {
            RadioButton rb = mSelectArr[i];
            if (i < optsList.size()) {
                rb.setVisibility(View.VISIBLE);
                rb.setText(Html.fromHtml(optsList.get(i).getContent()));
            } else {
                rb.setVisibility(View.INVISIBLE);
                rb.setText("");
            }
        }
    }

    // 用于标识主观案例题的小题位置，如果重置位置请重置为 -1
    private int mChildPosition = -1;
    private int mKePosition = -1;

    public void setChildPosition(int position) {
        mChildPosition = position;
    }

    public int getChildPosition() {
        return mChildPosition;
    }

    public void setKeChildPosition(int position) {
        this.mKePosition = position;
    }

    public int getKeChildPosition() {
        return mKePosition;
    }

    //点击a.b.c.d四个按钮时要做下标记是否做过
    public void onClick(View v) {
        //提交的
        TestResultBean.ExamDetailsBean resultToPost = listToPost.get(weiZhi);
        if (mQuesType == 1) {//单选
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
            if (mQuesType == 2) {//多选
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
            } else if (mQuesType == 3) {//判断
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

            } else if (mQuesType == 5) {
                NewTestContentBean.DataBean.QuesDataBean quesDataBean = list.get(weiZhi);
                List<NewTestContentBean.DataBean.QuesDataBean> childrenList = quesDataBean.getChildren();
                NewTestContentBean.DataBean.QuesDataBean childBean = childrenList.get(mKePosition - 1);
                int quesType = childBean.getQuesType();
                int selectPosition = 0;
                switch (v.getId()) {
                    case R.id.radioButtonA:
                        selectPosition = 0;
                        break;
                    case R.id.radioButtonB:
                        selectPosition = 1;
                        break;
                    case R.id.radioButtonC:
                        selectPosition = 2;
                        break;
                    case R.id.radioButtonD:
                        selectPosition = 3;
                        break;
                    case R.id.radioButtonE:
                        selectPosition = 4;
                        break;
                    case R.id.radioButtonF:
                        selectPosition = 5;
                        break;
                    case R.id.tijiao:
                        selectPosition = 6;
                        break;
                }

                if (selectPosition == 6) {
                    mViewHold.tijiao.setText("提交");
                    quesDataBean.setBiaoJi(1);

                    StringBuffer userQusIdBuffer = new StringBuffer();
                    StringBuffer qusIdBuffer = new StringBuffer();

                    List<NewTestContentBean.DataBean.QuesDataBean.OptsBean> optsList = childBean.getOpts();
                    if (mViewHold.rbA.isChecked()) {
                        userQusIdBuffer.append(optsList.get(0).getId() + ",");
                        optsList.get(0).setTag(1);
                    }
                    if (mViewHold.rbB.isChecked()) {
                        userQusIdBuffer.append(optsList.get(1).getId() + ",");
                        optsList.get(1).setTag(1);
                    }
                    if (mViewHold.rbC.isChecked()) {
                        userQusIdBuffer.append(optsList.get(2).getId() + ",");
                        optsList.get(2).setTag(1);
                    }
                    if (mViewHold.rbD.isChecked()) {
                        userQusIdBuffer.append(optsList.get(3).getId() + ",");
                        optsList.get(3).setTag(1);
                    }
                    if (mViewHold.rbE.isChecked()) {
                        userQusIdBuffer.append(optsList.get(4).getId() + ",");
                        optsList.get(4).setTag(1);
                    }
                    if (mViewHold.rbF.isChecked()) {
                        userQusIdBuffer.append(optsList.get(5).getId() + ",");
                        optsList.get(5).setTag(1);
                    }
                    TestResultBean.ExamDetailsBean examDetailsBean = listToPost.get(weiZhi);
                    examDetailsBean.setQuestionId(quesDataBean.getId());

                    for (int i = 0; i < optsList.size(); i++) {
                        if (optsList.get(i).getIsRightAnswer() == 1) {
                            qusIdBuffer.append(optsList.get(i).getId());
                        }
                    }
                    String userAnswer = userQusIdBuffer.toString();
                    if (TextUtils.isEmpty(userAnswer)) {
                        Toast.makeText(context, "请作答后再提交", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        mViewHold.rbA.setEnabled(false);
                        mViewHold.rbB.setEnabled(false);
                        mViewHold.rbC.setEnabled(false);
                        mViewHold.rbD.setEnabled(false);
                        mViewHold.rbE.setEnabled(false);
                        mViewHold.rbF.setEnabled(false);
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
                    int isRight = 0;
                    //判断提交是否做正确
                    if (qusAnswerNew.equals(userAnserNew)) {
                        isRight = 1;
                        int userIsRight = examDetailsBean.getIsRight();
                        if (userIsRight != 1) {
                            // 之前已经作对了，就不需要再重复加分数了
                            examDetailsBean.setIsRight(1);
                            mPostNotFactScore += (mNotFactScore / list.get(weiZhi).getChildren().size());
                        }
                    } else {
                        examDetailsBean.setIsRight(0);
                    }
                    // 答案的json
                    String answer = examDetailsBean.getUserAnswerIds();
                    try {
                        JSONObject json;
                        if (TextUtils.isEmpty(answer)) {
                            json = new JSONObject();
                        } else {
                            json = new JSONObject(answer);
                        }
                        JSONObject answerJson = new JSONObject();
                        answerJson.put("is_right", isRight);
                        answerJson.put("selection", String.valueOf(selectPosition));
                        json.put(String.valueOf(mKePosition - 1), answerJson);
                        examDetailsBean.setUserAnswerIds(json.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    examDetailsBean.setTag(5);
                    examDetailsBean.setQuestionType(5);
                    // 重置数据
                    listToPost.set(weiZhi, examDetailsBean);
                    childBean.setOpts(optsList);
                    childrenList.set(mKePosition - 1, childBean);
                    quesDataBean.setChildren(childrenList);
                    list.set(weiZhi, quesDataBean);
                    nextKe();
                    return;
                }
                if (quesType == 2) {
                    setKeMoreSelect(selectPosition, childBean, quesDataBean, childrenList);
                } else {
                    setKeSelect(selectPosition, childBean, quesDataBean, childrenList);
                }
            }
        }
    }

    // 处理客观案例题的多选
    private void setKeMoreSelect(int selectPosition, NewTestContentBean.DataBean.QuesDataBean childBean, NewTestContentBean.DataBean.QuesDataBean quesDataBean, List<NewTestContentBean.DataBean.QuesDataBean> childrenList) {
        TestResultBean.ExamDetailsBean resultToPost = listToPost.get(weiZhi);
        List<NewTestContentBean.DataBean.QuesDataBean.OptsBean> optsList = childBean.getOpts();
        for (int i = 0; i < mSelectArr.length; i++) {
            RadioButton ra = mSelectArr[i];
            if (i < optsList.size()) {
                NewTestContentBean.DataBean.QuesDataBean.OptsBean optsBean = optsList.get(i);
                if (i == selectPosition) {
                    // 选中的选项高光显示
                    ra.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    resultToPost.setQuestionId(quesDataBean.getId());
                    optsList.set(i, optsBean);
                }
            } else {
                break;
            }
        }
        childBean.setOpts(optsList);
        childrenList.set(mKePosition - 1, childBean);
        quesDataBean.setChildren(childrenList);
        list.set(weiZhi, quesDataBean);
        listToPost.set(weiZhi, resultToPost);
    }

    // 处理客观案例题的单选与判断
    private void setKeSelect(int selectPosition, NewTestContentBean.DataBean.QuesDataBean childBean, NewTestContentBean.DataBean.QuesDataBean quesDataBean, List<NewTestContentBean.DataBean.QuesDataBean> childrenList) {
        List<NewTestContentBean.DataBean.QuesDataBean.OptsBean> optsList = childBean.getOpts();
        TestResultBean.ExamDetailsBean resultToPost = listToPost.get(weiZhi);
        int isRight = 0;
        for (int i = 0; i < mSelectArr.length; i++) {
            if (i < optsList.size()) {
                NewTestContentBean.DataBean.QuesDataBean.OptsBean optsBean = optsList.get(i);
                optsBean.setTag(selectPosition == i ? 1 : 0);
                optsList.set(i, optsBean);
                if (i == selectPosition) {
                    isRight = optsBean.getIsRightAnswer();
                    if (optsBean.getIsRightAnswer() == 1 && resultToPost.getIsRight() != 1){
                        mPostNotFactScore += (mNotFactScore / list.get(weiZhi).getChildren().size());
                    }
                    resultToPost.setIsRight(optsBean.getIsRightAnswer());
                    mSelectArr[i].setTextColor(context.getResources().getColor(R.color.lightBlue));
                } else {
                    mSelectArr[i].setTextColor(context.getResources().getColor(R.color.testBlack));
                }
            } else {
                break;
            }
        }
        childBean.setOpts(optsList);
        list.set(weiZhi, childBean);
        // 设置答案的json
        String answer = resultToPost.getUserAnswerIds();
        try {
            JSONObject json;
            if (TextUtils.isEmpty(answer)) {
                json = new JSONObject();
            } else {
                json = new JSONObject(answer);
            }
            JSONObject answerJson = new JSONObject();
            answerJson.put("is_right", isRight);
            answerJson.put("selection", String.valueOf(selectPosition));
            json.put(String.valueOf(mKePosition - 1), answerJson);
            resultToPost.setUserAnswerIds(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resultToPost.setQuestionType(5);
        resultToPost.setTag(5);
        resultToPost.setQuestionId(quesDataBean.getId());

        childBean.setOpts(optsList);
        childrenList.set(mKePosition - 1, childBean);
        quesDataBean.setChildren(childrenList);
        list.set(weiZhi, quesDataBean);
        listToPost.set(weiZhi, resultToPost);

        nextKe();
    }

    // 客观案例题跳转下一题
    private void nextKe() {
        int size = list.size();
        int myPosition = weiZhi + 1;
        if (mKePosition >= 0) {
            if (mKePosition >= list.get(weiZhi).getChildren().size()) {
                setKeChildPosition(-1);
            } else {
                myPosition -= 1;
            }
        }
        recyclerView.scrollToPosition(myPosition);
        notifyDataSetChanged();
        if (myPosition == size) {
            dijige.setText((myPosition) + "/" + size);
        } else {

            dijige.setText((myPosition + 1) + "/" + size);
        }
    }


    private void fuyong(int position, int quesType, MyTestViewHold holder) {
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

        } else if (quesType == 3) {//判断
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
        } else if (quesType == 4) {
            TestResultBean.ExamDetailsBean resultToPost = listToPost.get(weiZhi);
            String answerStr = resultToPost.getUserAnswerIds();
            if (!TextUtils.isEmpty(answerStr)) {
                int showPosition = mChildPosition;
                if (showPosition < 0) showPosition = 0;
                try {
                    JSONObject json = new JSONObject(answerStr);
                    String answer = json.optString(String.valueOf(showPosition));
                    holder.answerEt.setText(answer);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (quesType == 5) {
            NewTestContentBean.DataBean.QuesDataBean quesDataBean = list.get(weiZhi);
            List<NewTestContentBean.DataBean.QuesDataBean> childrenList = quesDataBean.getChildren();
            int showPosition =  mKePosition;
            if (showPosition < 0) showPosition = 0;
            NewTestContentBean.DataBean.QuesDataBean childBean = childrenList.get(showPosition);
            List<NewTestContentBean.DataBean.QuesDataBean.OptsBean> optsList = childBean.getOpts();
            for (int i = 0; i < mSelectArr.length; i++){
                if (i < optsList.size()) {
                    NewTestContentBean.DataBean.QuesDataBean.OptsBean optsBean = optsList.get(i);
                    if (optsBean.getTag() == 1) {
                        mSelectArr[i].setTextColor(context.getResources().getColor(R.color.lightBlue));
                    } else {
                        mSelectArr[i].setTextColor(context.getResources().getColor(R.color.testBlack));
                    }
                }
            }
        } else if (quesType == 6) {
            // 简答题的答案回显
            TestResultBean.ExamDetailsBean resultToPost = listToPost.get(weiZhi);
            mViewHold.answerEt.setText(resultToPost.getUserAnswerIds());
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

        TextView questionTypeTv, questionTitleTv, submitTv, answerTv, analysisTv;
        EditTextWithScrollView answerEt;


        @SuppressLint("WrongViewCast")
        public MyTestViewHold(View itemView, int viewType) {
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

            questionTypeTv = itemView.findViewById(R.id.item_test_question_type_tv);
            questionTitleTv = itemView.findViewById(R.id.item_test_question_title_tv);
            answerEt = itemView.findViewById(R.id.item_test_question_answer_et);
            submitTv = itemView.findViewById(R.id.item_test_question_submit_ev);
            answerTv = itemView.findViewById(R.id.item_test_question_real_answer_tv);
            analysisTv = itemView.findViewById(R.id.item_test_question_analysis_tv);

        }
    }
}
