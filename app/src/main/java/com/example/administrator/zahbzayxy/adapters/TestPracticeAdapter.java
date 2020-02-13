package com.example.administrator.zahbzayxy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.example.administrator.zahbzayxy.beans.NewTestContentBean;
import com.example.administrator.zahbzayxy.beans.OptsBean;
import com.example.administrator.zahbzayxy.beans.QuesListBean2;
import com.example.administrator.zahbzayxy.beans.SaveUserErrorPrcticeBean;
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
 * Created by ${ZWJ} on 2017/3/25 0025.
 */
public class TestPracticeAdapter extends RecyclerView.Adapter<TestPracticeAdapter.MyTestViewHold> implements View.OnClickListener {
    Context context;
    LayoutInflater inflater;
    List<QuesListBean2> list;
    //为了用户查看作对做错的弹出框
    List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList;
    //保存用户错题记录的list
    List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList1 = new ArrayList<>();
    private int optSize;
    private int size;
    private RadioButton[] mSelectArr = new RadioButton[6];

    public List<SaveUserErrorPrcticeBean.ErrorQuesBean> getSaveErrorList1() {
        return saveErrorList1;
    }

    private int w, h;
    private int weiZhi;
    private MyTestViewHold mViewHold;
    private int quesType;


    private List<OptsBean> opts;

    private QuesListBean2 quesDataBean;


    private StringBuffer stringBuffer;
    StringBuffer erroranswerId;
    private MyRecyclerView myRecyclerView;
    private TextView dijige;

    private void setSelectArr(MyTestViewHold hold) {
        mSelectArr[0] = hold.rbA;
        mSelectArr[1] = hold.rbB;
        mSelectArr[2] = hold.rbC;
        mSelectArr[3] = hold.rbD;
        mSelectArr[4] = hold.rbE;
        mSelectArr[5] = hold.rbF;
    }

    public int getWeiZhi() {
        return weiZhi;
    }

    public TestPracticeAdapter(Context context, List<QuesListBean2> list, List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList, MyRecyclerView myRecyclerView, TextView dijige) {
        this.context = context;
        this.list = list;
        this.saveErrorList = saveErrorList;
        inflater = LayoutInflater.from(context);
        w = context.getResources().getDisplayMetrics().widthPixels;
        h = context.getResources().getDisplayMetrics().heightPixels;
        this.myRecyclerView = myRecyclerView;
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

    @Override
    public void onBindViewHolder(MyTestViewHold holder, int position) {
        holder.setIsRecyclable(false);
        weiZhi = position;
        quesDataBean = list.get(position);
        quesType = quesDataBean.getQuesType();
        size = list.size();
        opts =quesDataBean.getOpts();
        optSize = opts.size();
        if (quesType == 6 || quesType == 4) {
            fuyong(position);
        } else {
            if (optSize > 0) {
                holder.rbA.setVisibility(View.VISIBLE);
                holder.rbB.setVisibility(View.VISIBLE);
            }
            setSelectArr(holder);
            fuyong(position);
        }
        if (quesType != 4) {
            mChildPosition = -1;
        }
        if (quesType != 5) {
            mKePosition = -1;
        }
        //每个题的选项内容的集合
        if (quesType == 1) {//单选
            //每个题目的集合
            holder.selectorType_tv.setText("单选题");
            holder.tv.setText("[单选]" + list.get(position).getContent());
            holder.tijiao.setVisibility(View.INVISIBLE);
            if (optSize == 2) {
                holder.rbC.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
            }
            if (optSize == 3) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
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
            erroranswerId = new StringBuffer();
            holder.selectorType_tv.setText("多选题");
            holder.tv.setText("[多选]" + list.get(position).getContent());
            mViewHold.tijiao.setVisibility(View.VISIBLE);
            holder.rbC.setVisibility(View.VISIBLE);
            holder.rbD.setVisibility(View.VISIBLE);
            if (optSize == 4) {
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
            } else if (optSize == 5) {
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
        } else if (quesType == 3) {//判断
            holder.selectorType_tv.setText("判断题");
            holder.tv.setText("[判断]" + list.get(position).getContent());
            holder.tijiao.setVisibility(View.INVISIBLE);
            holder.rbA.setText(opts.get(0).getContent());
            holder.rbB.setText(opts.get(1).getContent());
            holder.rbC.setVisibility(View.INVISIBLE);
            holder.rbD.setVisibility(View.INVISIBLE);
        } else if (quesType == 4) {//主观案例
            holder.questionTypeTv.setText("主观案例题");
            mKePosition = -1;
            if (mChildPosition < 0) mChildPosition += 1;
            holder.questionTypeTv.setText("[" + "主观案例题" + "]");
            // 获取小题集合
            List<QuesListBean2> childrenList = list.get(position).getChildren();
            // 防止数组角标越界
            if (mChildPosition < 0 || mChildPosition >= childrenList.size()) return;
            // 小题的题干
            String childContent = childrenList.get(mChildPosition).getContent();
            String content = list.get(position).getContent() + "<br />" + (mChildPosition + 1) + "、" + childContent;
            holder.questionTitleTv.setText(Html.fromHtml(content));
            holder.answerEt.setHint("请输入主观案例题答案");
            mChildPosition++;
        } else if (quesType == 5) { //客观案例
            holder.selectorType_tv.setText("客观案例题");
            if (mKePosition < 0) mKePosition += 1;
            mChildPosition = -1;
            holder.selectorType_tv.setText("[" + "客观案例题" + "]");
            // 获取小题集合
            List<QuesListBean2> childrenList = list.get(position).getChildren();
            // 防止数组角标越界
            if (mKePosition < 0 || mKePosition >= childrenList.size()) return;
            // 获取小题的bean
            QuesListBean2 quesDataBean = childrenList.get(mKePosition);
            // 小题的题干
            String childContent = quesDataBean.getContent();
            // 小题的选项集合
            List<OptsBean> optsList = quesDataBean.getOpts();
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
            holder.questionTypeTv.setText("简答题");
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
                SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean = saveErrorList.get(weiZhi);
                if (quesType == 6) {//简单题
                    errorQuesBean.setIsRight(2);
                    // 这里直接设置了答案
                    errorQuesBean.setUserAnswerIds(answer);
                    errorQuesBean.setQuestionId(list.get(weiZhi).getId());
                    errorQuesBean.setQuestionType(6);
//                    errorQuesBean.setTag(6);
                    saveErrorList.set(weiZhi, errorQuesBean);
                    myRecyclerView.scrollToPosition(weiZhi + 1);
                    notifyDataSetChanged();
                    if (weiZhi + 1 == size) {
                        dijige.setText((weiZhi + 1) + "/" + size);
                    } else {

                        dijige.setText((weiZhi + 2) + "/" + size);
                    }
                } else {//主管案例题
                    String answerPost = errorQuesBean.getUserAnswerIds();
                    try {
                        JSONObject json = null;
                        if (TextUtils.isEmpty(answerPost)) {
                            json = new JSONObject();
                        } else {
                            json = new JSONObject(answerPost);
                        }

                        json.put(String.valueOf(mChildPosition - 1), answer);
                        errorQuesBean.setIsRight(2);
                        errorQuesBean.setUserAnswerIds(json.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    errorQuesBean.setQuestionId(list.get(weiZhi).getId());
                    errorQuesBean.setQuestionType(4);
//                    errorQuesBean.setTag(4);
                    saveErrorList.set(weiZhi, errorQuesBean);

                    int myPosition = weiZhi + 1;
                    if (mChildPosition >= 0) {
                        if (mChildPosition >= list.get(weiZhi).getChildren().size()) {
                            setChildPosition(-1);
                        } else {
                            myPosition -= 1;
                        }
                    }
                    myRecyclerView.scrollToPosition(myPosition);
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
            mViewHold.tijiao.setOnClickListener(this);
            mViewHold.rbE.setOnClickListener(this);
            mViewHold.rbF.setOnClickListener(this);
        }

    }


    /**
     * 设置选择按钮展示
     *
     * @param optsList 展示的选项
     */
    private void setShowSelectBtn(List<OptsBean> optsList) {
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

    //点击a.b.c.d四个按钮时要做下标记是否做过
    @Override
    public void onClick(View v) {
        //查看当前做题进度的list
        final SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean = saveErrorList.get(weiZhi);
        //保存用户错题记录的list
        final SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean1 = new SaveUserErrorPrcticeBean.ErrorQuesBean();
        if (quesType == 1) {//单选
            switch (v.getId()) {
                case R.id.radioButtonA:
                    quesDataBean.setBiaoJi(1);
                    opts.get(0).setTag(1);
                    if (opts.get(0).getIsRightAnswer() == 0) {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(0).getId()));
                        Log.e("onClick:errorId ", String.valueOf(opts.get(0).getId()));
                        errorQuesBean1.setQuestionType(1);
                        errorQuesBean1.setQuestionId(quesDataBean.getId());
                        Log.e("aaa:quesId ", String.valueOf(quesDataBean.getId()));
                        saveErrorList1.add(errorQuesBean1);

                    } else {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);

                        quesDataBean.setBiaoJi(1);
                        myRecyclerView.scrollToPosition(weiZhi + 1);
                        notifyDataSetChanged();
                        dijige.setText((weiZhi + 2) + "/" + size);
                        Log.e("weizhi", weiZhi + "");
                    }
                    errorQuesBean.setUserAnswerIds(String.valueOf(opts.get(0).getId()));
                    break;
                case R.id.radioButtonB:
                    quesDataBean.setBiaoJi(1);
                    opts.get(1).setTag(1);

                    if (opts.get(1).getIsRightAnswer() == 0) {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(1).getId()));
                        Log.e("onClick:errorId ", String.valueOf(opts.get(1).getId()));
                        errorQuesBean1.setQuestionType(1);
                        errorQuesBean1.setQuestionId(quesDataBean.getId());
                        saveErrorList1.add(errorQuesBean1);
                    } else {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);
                        myRecyclerView.scrollToPosition(weiZhi + 1);
                        notifyDataSetChanged();
                        dijige.setText((weiZhi + 2) + "/" + size);
                        Log.e("weizhi", weiZhi + "");

                    }
                    errorQuesBean.setUserAnswerIds(String.valueOf(opts.get(1).getId()));
                    break;
                case R.id.radioButtonC:
                    quesDataBean.setBiaoJi(1);
                    opts.get(2).setTag(1);
                    if (opts.get(2).getIsRightAnswer() == 0) {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(2).getId()));
                        Log.e("onClick:errorId ", String.valueOf(opts.get(2).getId()));
                        errorQuesBean1.setQuestionType(1);
                        errorQuesBean1.setQuestionId(quesDataBean.getId());
                        saveErrorList1.add(errorQuesBean1);

                    } else {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);
                        myRecyclerView.scrollToPosition(weiZhi + 1);
                        notifyDataSetChanged();
                        dijige.setText((weiZhi + 2) + "/" + size);
                        Log.e("weizhi", weiZhi + "");
                    }
                    errorQuesBean.setUserAnswerIds(String.valueOf(opts.get(2).getId()));
                    break;

                case R.id.radioButtonD:
                    if (size > 3) {
                        quesDataBean.setBiaoJi(1);
                        opts.get(3).setTag(1);
                        if (opts.get(3).getIsRightAnswer() == 0) {
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            errorQuesBean.setIsRight(1);
                            errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(3).getId()));
                            Log.e("onClick:errorId ", String.valueOf(opts.get(3).getId()));
                            errorQuesBean1.setQuestionType(1);
                            errorQuesBean1.setQuestionId(quesDataBean.getId());
                            saveErrorList1.add(errorQuesBean1);


                        } else {
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            errorQuesBean.setIsRight(2);
                            myRecyclerView.scrollToPosition(weiZhi + 1);
                            notifyDataSetChanged();
                            dijige.setText((weiZhi + 2) + "/" + size);
                            Log.e("weizhi", weiZhi + "");
                        }
                    }
                    errorQuesBean.setUserAnswerIds(String.valueOf(opts.get(3).getId()));
                    break;

                case R.id.radioButtonE:
                    if (size > 4) {
                        quesDataBean.setBiaoJi(1);
                        opts.get(4).setTag(1);
                        if (opts.get(4).getIsRightAnswer() == 0) {
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            errorQuesBean.setIsRight(1);
                            errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(4).getId()));
                            Log.e("onClick:errorId ", String.valueOf(opts.get(4).getId()));
                            errorQuesBean1.setQuestionType(1);
                            errorQuesBean1.setQuestionId(quesDataBean.getId());
                            saveErrorList1.add(errorQuesBean1);
                        } else {
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            errorQuesBean.setIsRight(2);
                            myRecyclerView.scrollToPosition(weiZhi + 1);
                            notifyDataSetChanged();
                            dijige.setText((weiZhi + 2) + "/" + size);
                            Log.e("weizhi", weiZhi + "");
                        }
                    }
                    errorQuesBean.setUserAnswerIds(String.valueOf(opts.get(4).getId()));
                    break;
                case R.id.radioButtonF:
                    if (size > 5) {
                        quesDataBean.setBiaoJi(1);
                        opts.get(5).setTag(1);
                        if (opts.get(5).getIsRightAnswer() == 0) {
                            mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            errorQuesBean.setIsRight(1);
                            errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(5).getId()));
                            Log.e("onClick:errorId ", String.valueOf(opts.get(5).getId()));
                            errorQuesBean1.setQuestionType(1);
                            errorQuesBean1.setQuestionId(quesDataBean.getId());
                            saveErrorList1.add(errorQuesBean1);
                        } else {
                            mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            errorQuesBean.setIsRight(2);
                            myRecyclerView.scrollToPosition(weiZhi + 1);
                            notifyDataSetChanged();
                            dijige.setText((weiZhi + 2) + "/" + size);
                            Log.e("weizhi", weiZhi + "");
                        }
                    }
                    errorQuesBean.setUserAnswerIds(String.valueOf(opts.get(5).getId()));
                    break;

            }

            //设置用户做题的类型和做题的Id
//点击选项的时候设置答案可见
            mViewHold.answer_layout.setVisibility(View.VISIBLE);
            mViewHold.parsing_tv.setText(quesDataBean.getParsing());
            for (int i = 0; i < opts.size(); i++) {
                int isRightAnswer = opts.get(i).getIsRightAnswer();

                if (isRightAnswer == 1) {
                    switch (i) {
                        case 0:
                            mViewHold.anser_tv.setText("答案:" + " " + "A");
                            break;
                        case 1:
                            mViewHold.anser_tv.setText("答案:" + " " + "B");
                            break;
                        case 2:
                            mViewHold.anser_tv.setText("答案:" + " " + "C");
                            break;
                        case 3:
                            mViewHold.anser_tv.setText("答案:" + " " + "D");
                            break;
                    }
                }
            }
            errorQuesBean.setQuestionType(1);
            quesDataBean.setBiaoJi(1);
            mViewHold.rbA.setEnabled(false);
            mViewHold.rbB.setEnabled(false);
            mViewHold.rbC.setEnabled(false);
            mViewHold.rbD.setEnabled(false);
        } else if (quesType == 2) {//多选
            //  int personRight=0;

            mViewHold.tijiao.setVisibility(View.VISIBLE);
            int rightNum = 0;

            switch (v.getId()) {
                case R.id.radioButtonA:
                    opts.get(0).setTag(1);
                    if (opts.get(0).getIsRightAnswer() == 0) {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        erroranswerId.append(opts.get(0).getId() + ",");
                        Log.e(" erroranswerId", erroranswerId + "");
                    } else {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);

                        rightNum++;
                    }
                    break;
                case R.id.radioButtonB:
                    opts.get(1).setTag(1);
                    if (opts.get(1).getIsRightAnswer() == 0) {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        erroranswerId.append(opts.get(1).getId() + ",");
                        Log.e(" erroranswerId", erroranswerId + "");

                    } else {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                        rightNum++;


                    }
                    break;
                case R.id.radioButtonC:
                    opts.get(2).setTag(1);
                    if (opts.get(2).getIsRightAnswer() == 0) {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                        erroranswerId.append(opts.get(2).getId() + ",");
                        Log.e(" erroranswerId", erroranswerId + "");

                    } else {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                        rightNum++;

                    }
                    break;
                case R.id.radioButtonD:
                    opts.get(3).setTag(1);
                    if (opts.get(3).getIsRightAnswer() == 0) {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                        erroranswerId.append(opts.get(3).getId() + ",");
                        Log.e(" erroranswerId", erroranswerId + "");

                    } else {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                        rightNum++;


                    }
                    break;
                case R.id.radioButtonE:
                    opts.get(4).setTag(1);
                    if (opts.get(4).getIsRightAnswer() == 0) {
                        mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                        erroranswerId.append(opts.get(4).getId() + ",");
                        Log.e(" erroranswerId", erroranswerId + "");

                    } else {
                        mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                        rightNum++;

                    }
                    break;
                case R.id.radioButtonF:
                    opts.get(5).setTag(1);
                    if (opts.get(5).getIsRightAnswer() == 0) {
                        mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                        erroranswerId.append(opts.get(5).getId() + ",");
                        Log.e(" erroranswerId", erroranswerId + "");

                    } else {
                        mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                        rightNum++;

                    }
                    break;
                default:
                    break;
            }
            mViewHold.tijiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


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
                    errorQuesBean.setQuestionId(quesDataBean.getId());
                    errorQuesBean.setUserAnswerIds((userQusIdBuffer.toString()));
                    for (int i = 0; i < opts.size(); i++) {
                        if (opts.get(i).getIsRightAnswer() == 1) {
                            qusIdBuffer.append(opts.get(i).getId());
                        }
                    }
                    String userAnswer = userQusIdBuffer.toString();
                    if (TextUtils.isEmpty(userAnswer)) {
                        Toast.makeText(context, "请作答后再提交", Toast.LENGTH_SHORT).show();
                    } else {
                        mViewHold.rbA.setEnabled(false);
                        mViewHold.rbB.setEnabled(false);
                        mViewHold.rbC.setEnabled(false);
                        mViewHold.rbD.setEnabled(false);
                        mViewHold.rbE.setEnabled(false);
                        mViewHold.rbF.setEnabled(false);
                        mViewHold.answer_layout.setVisibility(View.VISIBLE);
                        mViewHold.parsing_tv.setText(quesDataBean.getParsing());

                        myRecyclerView.scrollToPosition(weiZhi + 1);
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
                        errorQuesBean.setIsRight(2);
                    } else {
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(erroranswerId.toString());
                        String s = erroranswerId.toString();
                        Log.e("errraaaaaaaaa", s);
                        errorQuesBean1.setQuestionType(2);
                        errorQuesBean1.setQuestionId(quesDataBean.getId());
                        saveErrorList1.add(errorQuesBean1);
                    }
                    Log.e("qId", qusIdBuffer + "");
                    Log.e("qId", userQusIdBuffer + "," + "user");


                }
            });
            stringBuffer = new StringBuffer();
            for (int i = 0; i < opts.size(); i++) {
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer == 1) {
                    switch (i) {
                        case 0:
                            stringBuffer.append("A");
                            break;
                        case 1:
                            stringBuffer.append("B");
                            break;
                        case 2:
                            stringBuffer.append("C");
                            break;
                        case 3:
                            stringBuffer.append("D");
                            break;
                        case 4:
                            stringBuffer.append("E");
                            break;
                        case 5:
                            stringBuffer.append("F");
                            break;
                    }
                    mViewHold.anser_tv.setText("答案:" + stringBuffer.toString());
                }
            }
            errorQuesBean.setQuestionType(2);

        } else if (quesType == 3) {//判断
            switch (v.getId()) {
                case R.id.radioButtonA:
                    opts.get(0).setTag(1);
                    if (opts.get(0).getIsRightAnswer() == 0) {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(0).getId()));
                        errorQuesBean1.setQuestionId(quesDataBean.getId());
                        errorQuesBean1.setQuestionType(2);
                        saveErrorList1.add(errorQuesBean1);

                    } else {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);

                        myRecyclerView.scrollToPosition(weiZhi + 1);
                        notifyDataSetChanged();
                        dijige.setText((weiZhi + 2) + "/" + size);
                    }
                    errorQuesBean.setUserAnswerIds(opts.get(0).getId() + "");
                    break;
                case R.id.radioButtonB:
                    opts.get(1).setTag(1);
                    if (opts.get(1).getIsRightAnswer() == 0) {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(1).getId()));
                        errorQuesBean1.setQuestionId(quesDataBean.getId());
                        errorQuesBean1.setQuestionType(2);
                        saveErrorList1.add(errorQuesBean1);


                    } else {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);
                        myRecyclerView.scrollToPosition(weiZhi + 1);
                        notifyDataSetChanged();
                        dijige.setText((weiZhi + 2) + "/" + size);
                    }
                    errorQuesBean.setUserAnswerIds(opts.get(1).getId() + "");
                    break;
            }

            mViewHold.answer_layout.setVisibility(View.VISIBLE);
            mViewHold.parsing_tv.setText(quesDataBean.getParsing());
            for (int i = 0; i < opts.size(); i++) {
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer == 1) {
                    switch (i) {
                        case 0:
                            mViewHold.anser_tv.setText("答案:" + " " + "A");
                            break;
                        case 1:
                            mViewHold.anser_tv.setText("答案:" + " " + "B");
                            break;
                    }
                }
            }
            errorQuesBean.setQuestionType(3);
            quesDataBean.setBiaoJi(1);
            mViewHold.rbA.setEnabled(false);
            mViewHold.rbB.setEnabled(false);
        } else if (quesType == 5) { //客观案例
            QuesListBean2 quesDataBean = list.get(weiZhi);
            List<QuesListBean2> childrenList = quesDataBean.getChildren();
            QuesListBean2 childBean = childrenList.get(mKePosition - 1);
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

                List<OptsBean> optsList = childBean.getOpts();
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
                SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean2 = saveErrorList.get(weiZhi);
                errorQuesBean2.setQuestionId(quesDataBean.getId());

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
                    errorQuesBean2.setIsRight(2);
//                    mPostNotFactScore += (mNotFactScore / list.get(weiZhi).getChildren().size());
                } else {
                    errorQuesBean2.setIsRight(1);
                }
                // 答案的json
                String answer = errorQuesBean2.getUserAnswerIds();
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
                    errorQuesBean2.setUserAnswerIds(json.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                errorQuesBean2.setTag(5);
                errorQuesBean2.setQuestionType(5);
                // 重置数据
                saveErrorList.set(weiZhi, errorQuesBean2);
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
        Log.e("saveErrorSize", saveErrorList1.size() + "");
        Log.e("saveErrorSize", saveErrorList1.toString());
    }

    // 处理客观案例题的单选与判断
    private void setKeSelect(int selectPosition, QuesListBean2 childBean, QuesListBean2 quesDataBean, List<QuesListBean2> childrenList) {
        List<OptsBean> optsList = childBean.getOpts();
        SaveUserErrorPrcticeBean.ErrorQuesBean resultToPost = saveErrorList.get(weiZhi);
        int isRight = 0;
        for (int i = 0; i < mSelectArr.length; i++) {
            if (i < optsList.size()) {
                OptsBean optsBean = optsList.get(i);
                optsBean.setTag(selectPosition == i ? 1 : 0);
                optsList.set(i, optsBean);
                if (i == selectPosition) {
                    isRight = optsBean.getIsRightAnswer();
                    resultToPost.setIsRight(isRight == 1?2:1);
                    mSelectArr[i].setTextColor(context.getResources().getColor(R.color.lightBlue));
                    if (isRight == 1) {
                        mSelectArr[i].setButtonDrawable(R.mipmap.test_right);
                    } else {
                        mSelectArr[i].setButtonDrawable(R.mipmap.test_error);
                    }
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
//        resultToPost.setTag(5);
        resultToPost.setQuestionId(quesDataBean.getId());

        childBean.setOpts(optsList);
        childrenList.set(mKePosition - 1, childBean);
        quesDataBean.setChildren(childrenList);
        list.set(weiZhi, quesDataBean);
        saveErrorList.set(weiZhi, resultToPost);
        mViewHold.answer_layout.setVisibility(View.VISIBLE);
        String answerStr = getRightAnswer(optsList);
        mViewHold.anser_tv.setText("答案: " + answerStr);
        if (isRight == 1) {
            nextKe();
        }
    }

    // 处理客观案例题的多选
    private void setKeMoreSelect(int selectPosition, QuesListBean2 childBean, QuesListBean2 quesDataBean, List<QuesListBean2> childrenList) {
        SaveUserErrorPrcticeBean.ErrorQuesBean resultToPost = saveErrorList.get(weiZhi);
        List<OptsBean> optsList = childBean.getOpts();
        for (int i = 0; i < mSelectArr.length; i++) {
            RadioButton ra = mSelectArr[i];
            if (i < optsList.size()) {
                OptsBean optsBean = optsList.get(i);
                if (i == selectPosition) {
                    int isRight = optsBean.getIsRightAnswer();
                    // 选中的选项高光显示
                    ra.setTextColor(context.getResources().getColor(R.color.lightBlue));
                    if (isRight == 1) {
                        ra.setButtonDrawable(R.mipmap.test_right);
                    } else {
                        ra.setButtonDrawable(R.mipmap.test_error);
                    }
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
        saveErrorList.set(weiZhi, resultToPost);
    }

    private String getRightAnswer(List<OptsBean> optsList) {
        StringBuilder sb = new StringBuilder();
        char select = 'A';
        for (OptsBean optsBean : optsList) {
            int isRight = optsBean.getIsRightAnswer();
            if (isRight == 1) {
                sb.append(select);
                sb.append(",");
            }
            select = (char) (select + 1);
        }
        String answer = sb.toString();
        return answer.substring(0, answer.length() - 1);
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
        myRecyclerView.scrollToPosition(myPosition);
        notifyDataSetChanged();
        if (myPosition == size) {
            dijige.setText((myPosition) + "/" + size);
        } else {

            dijige.setText((myPosition + 1) + "/" + size);
        }
    }

    private void fuyong(int position) {
        if (!(quesType == 6 || quesType == 4)) {
            mViewHold.answer_layout.setVisibility(View.INVISIBLE);
        }
        if (quesType == 1) {//单选
            mViewHold.rbD.setVisibility(View.INVISIBLE);
            mViewHold.rbA.setChecked(false);
            mViewHold.rbB.setChecked(false);
            mViewHold.rbC.setChecked(false);
            mViewHold.rbD.setChecked(false);
            mViewHold.rbE.setVisibility(View.GONE);
            mViewHold.rbF.setVisibility(View.GONE);

            if (quesDataBean.getBiaoJi() == 1) {
                //答案的复用
                mViewHold.answer_layout.setVisibility(View.VISIBLE);
                mViewHold.rbA.setEnabled(false);
                mViewHold.rbB.setEnabled(false);
                mViewHold.rbC.setEnabled(false);
                mViewHold.rbD.setEnabled(false);
            } else {
                mViewHold.rbA.setEnabled(true);
                mViewHold.rbB.setEnabled(true);
                mViewHold.rbC.setEnabled(true);
                mViewHold.rbD.setEnabled(true);
            }

            if (opts.get(0).getTag() == 1) {
                if (opts.get(0).getIsRightAnswer() == 0) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                    if (opts.get(2).getIsRightAnswer() == 1) {
                        mViewHold.anser_tv.setText("答案:C");
                    } else if (opts.get(1).getIsRightAnswer() == 1) {
                        mViewHold.anser_tv.setText("答案:B");
                    } else if (opts.get(3).getIsRightAnswer() == 1) {
                        mViewHold.anser_tv.setText("答案:D");
                    }

                } else if (opts.get(0).getIsRightAnswer() == 1) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    mViewHold.anser_tv.setText("答案:A");
                }
                mViewHold.answer_layout.setVisibility(View.VISIBLE);


            } else {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.test_black_tv));
            }
            if (opts.get(1).getTag() == 1) {
                if (opts.get(1).getIsRightAnswer() == 0) {
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                    if (opts.get(2).getIsRightAnswer() == 1) {
                        mViewHold.anser_tv.setText("答案:C");
                    } else if (opts.get(0).getIsRightAnswer() == 1) {
                        mViewHold.anser_tv.setText("答案:A");
                    } else if (opts.get(3).getIsRightAnswer() == 1) {
                        mViewHold.anser_tv.setText("答案:D");
                    }
                } else if (opts.get(1).getIsRightAnswer() == 1) {
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    mViewHold.anser_tv.setText("答案:B");
                }
            } else {
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.test_black_tv));
            }
            if (optSize >= 3) {
                if (opts.get(2).getTag() == 1) {
                    if (opts.get(2).getIsRightAnswer() == 0) {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        if (opts.get(0).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:A");
                        } else if (opts.get(1).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:B");
                        } else if (opts.get(3).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:D");
                        }
                    } else if (opts.get(2).getIsRightAnswer() == 1) {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        mViewHold.anser_tv.setText("答案:C");
                    }
                } else {
                    mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.test_black_tv));
                }
            }
            if (optSize >= 4) {
                if (opts.get(3).getTag() == 1) {
                    if (opts.get(3).getIsRightAnswer() == 0) {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        if (opts.get(0).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:A");
                        } else if (opts.get(1).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:B");
                        } else if (opts.get(2).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:C");
                        }
                    } else if (opts.get(3).getIsRightAnswer() == 1) {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        mViewHold.anser_tv.setText("答案:D");
                    }
                } else {
                    mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.test_black_tv));
                }

            }
            if (optSize >= 5) {
                if (opts.get(4).getTag() == 1) {
                    if (opts.get(4).getIsRightAnswer() == 0) {
                        mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        if (opts.get(0).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:A");
                        } else if (opts.get(1).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:B");
                        } else if (opts.get(2).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:C");
                        } else if (opts.get(3).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:D");
                        }
                    } else if (opts.get(4).getIsRightAnswer() == 1) {
                        mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        mViewHold.anser_tv.setText("答案:E");
                    }
                } else {
                    mViewHold.rbE.setButtonDrawable(R.mipmap.e_new);
                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.test_black_tv));
                }

            }

            if (optSize >= 6) {
                if (opts.get(5).getTag() == 1) {
                    if (opts.get(5).getIsRightAnswer() == 0) {
                        mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        if (opts.get(0).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:A");
                        } else if (opts.get(1).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:B");
                        } else if (opts.get(2).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:C");
                        } else if (opts.get(3).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:D");
                        } else if (opts.get(4).getIsRightAnswer() == 1) {
                            mViewHold.anser_tv.setText("答案:E");
                        }
                    } else if (opts.get(5).getIsRightAnswer() == 1) {
                        mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        mViewHold.anser_tv.setText("答案:F");
                    }
                } else {
                    mViewHold.rbF.setButtonDrawable(R.mipmap.f_new);
                    mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.test_black_tv));
                }

            }

        } else if (quesType == 2) {//多选
            mViewHold.rbA.setChecked(false);
            mViewHold.rbB.setChecked(false);
            mViewHold.rbC.setChecked(false);
            mViewHold.rbD.setChecked(false);
            mViewHold.rbE.setChecked(false);
            mViewHold.rbF.setChecked(false);
            mViewHold.rbE.setVisibility(View.GONE);
            mViewHold.rbF.setVisibility(View.GONE);
            if (quesDataBean.getBiaoJi() == 1) {
                mViewHold.answer_layout.setVisibility(View.VISIBLE);
                mViewHold.rbA.setEnabled(false);
                mViewHold.rbB.setEnabled(false);
                mViewHold.rbC.setEnabled(false);
                mViewHold.rbD.setEnabled(false);
                mViewHold.rbE.setEnabled(false);
                mViewHold.rbF.setEnabled(false);
            } else {
                mViewHold.rbA.setEnabled(true);
                mViewHold.rbB.setEnabled(true);
                mViewHold.rbC.setEnabled(true);
                mViewHold.rbD.setEnabled(true);
                mViewHold.rbE.setEnabled(true);
                mViewHold.rbF.setEnabled(true);
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.testBlack));
            }

            if (opts.get(0).getTag() == 1) {
                //  mViewHold.rbA.setChecked(true);
                if (opts.get(0).getIsRightAnswer() == 0) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);

                } else if (opts.get(0).getIsRightAnswer() == 1) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                }
            } else {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
            }
            if (opts.get(1).getTag() == 1) {
                if (opts.get(1).getIsRightAnswer() == 0) {
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                } else if (opts.get(1).getIsRightAnswer() == 1) {
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                }
            } else {
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
            }

            if (opts.get(2).getTag() == 1) {
                // mViewHold.rbC.setChecked(true);
                if (opts.get(2).getIsRightAnswer() == 0) {
                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                } else if (opts.get(2).getIsRightAnswer() == 1) {
                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                }
            } else {
                mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
            }

            if (opts.get(3).getTag() == 1) {
                if (opts.get(3).getIsRightAnswer() == 0) {
                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                } else if (opts.get(3).getIsRightAnswer() == 1) {
                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                }
            } else {
                mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
            }
            if (optSize == 5) {
                fuyong5();
                mViewHold.rbE.setVisibility(View.VISIBLE);
            }
            if (optSize == 6) {
                fuyong6();
                mViewHold.rbE.setVisibility(View.VISIBLE);
                mViewHold.rbF.setVisibility(View.VISIBLE);
            }

            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < opts.size(); i++) {
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer == 1) {
                    switch (i) {
                        case 0:
                            stringBuffer.append("A");
                            break;
                        case 1:
                            stringBuffer.append("B");
                            break;
                        case 2:
                            stringBuffer.append("C");
                            break;
                        case 3:
                            stringBuffer.append("D");
                            break;
                        case 4:
                            stringBuffer.append("E");
                            break;
                        case 5:
                            stringBuffer.append("F");
                            break;
                    }
                    mViewHold.anser_tv.setText("答案:" + stringBuffer.toString());
                }
            }
        } else if (quesType == 3) {//判断
            mViewHold.rbA.setChecked(false);
            mViewHold.rbB.setChecked(false);
            mViewHold.rbC.setVisibility(View.GONE);
            mViewHold.rbD.setVisibility(View.GONE);
            mViewHold.rbE.setVisibility(View.GONE);
            mViewHold.rbF.setVisibility(View.GONE);
            if (quesDataBean.getBiaoJi() == 1) {

                mViewHold.answer_layout.setVisibility(View.VISIBLE);
                mViewHold.rbA.setEnabled(false);
                mViewHold.rbB.setEnabled(false);

            } else {
                mViewHold.rbA.setEnabled(true);
                mViewHold.rbB.setEnabled(true);

            }
            if (opts.get(0).getTag() == 1) {
                if (opts.get(0).getIsRightAnswer() == 0) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                    mViewHold.anser_tv.setText("答案:B");

                } else if (opts.get(0).getIsRightAnswer() == 1) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    mViewHold.anser_tv.setText("答案:A");

                }
            } else {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.test_black_tv));
            }


            if (opts.get(1).getTag() == 1) {
                if (opts.get(1).getIsRightAnswer() == 0) {
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.anser_tv.setText("答案:A");
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                } else if (opts.get(1).getIsRightAnswer() == 1) {
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    mViewHold.anser_tv.setText("答案:B");

                }
            } else {
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.test_black_tv));
            }
        } else if (quesType == 4) { //主观案例
            SaveUserErrorPrcticeBean.ErrorQuesBean resultToPost = saveErrorList.get(weiZhi);
            String answerStr = resultToPost.getUserAnswerIds();
            if (!TextUtils.isEmpty(answerStr)) {
                int showPosition = mChildPosition;
                if (showPosition < 0) showPosition = 0;
                try {
                    JSONObject json = new JSONObject(answerStr);
                    String answer = json.optString(String.valueOf(showPosition));
                    mViewHold.answerEt.setText(answer);
                    if (!TextUtils.isEmpty(answer)) {
                        QuesListBean2 quesDataBean = list.get(weiZhi);
                        List<QuesListBean2> childrenList = quesDataBean.getChildren();
                        QuesListBean2 childBean = childrenList.get(showPosition);
                        List<OptsBean> optsList = childBean.getOpts();
                        if (optsList != null && optsList.size() > 0) {
                            mViewHold.answerTv.setText("答案: " + Html.fromHtml(optsList.get(0).getContent()));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        } else if (quesType == 5) { //客观案例
            QuesListBean2 quesDataBean = list.get(weiZhi);
            List<QuesListBean2> childrenList = quesDataBean.getChildren();
            int showPosition =  mKePosition;
            if (showPosition < 0) showPosition = 0;
            QuesListBean2 childBean = childrenList.get(showPosition);
            List<OptsBean> optsList = childBean.getOpts();
            boolean select = false;
            for (int i = 0; i < mSelectArr.length; i++){
                if (i < optsList.size()) {
                    OptsBean optsBean = optsList.get(i);
                    int isRight = optsBean.getIsRightAnswer();
                    if (optsBean.getTag() == 1) {
                        select = true;
                        mSelectArr[i].setTextColor(context.getResources().getColor(R.color.lightBlue));
                        if (isRight == 1) {
                            mSelectArr[i].setButtonDrawable(R.mipmap.test_right);
                        } else {
                            mSelectArr[i].setButtonDrawable(R.mipmap.test_error);
                        }
                    } else {
                        mSelectArr[i].setTextColor(context.getResources().getColor(R.color.testBlack));
                    }
                }
            }
            if (select) {
                mViewHold.answer_layout.setVisibility(View.VISIBLE);
                String answer = getRightAnswer(optsList);
                mViewHold.anser_tv.setText("答案: " + answer);
            }
        } else if (quesType == 6) { //简答
            // 简答题的答案回显
            SaveUserErrorPrcticeBean.ErrorQuesBean resultToPost = saveErrorList.get(weiZhi);
            String userAnswer = resultToPost.getUserAnswerIds();
            mViewHold.answerEt.setText(userAnswer);
            if (!TextUtils.isEmpty(userAnswer)) {
                QuesListBean2 quesDataBean = list.get(weiZhi);
                List<OptsBean> optsList = quesDataBean.getOpts();
                if (optsList != null && optsList.size() > 0) {
                    mViewHold.answerTv.setText("答案: " + Html.fromHtml(optsList.get(0).getContent()));
                }
            }
        }
    }

    private void fuyong6() {
        fuyong5();
        if (opts.get(5).getTag() == 1) {
            if (opts.get(5).getIsRightAnswer() == 0) {
                mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
            } else if (opts.get(5).getIsRightAnswer() == 1) {
                mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
            }
        } else {
            mViewHold.rbF.setButtonDrawable(R.mipmap.f_new);
        }
    }

    private void fuyong5() {
        if (opts.get(4).getTag() == 1) {
            if (opts.get(4).getIsRightAnswer() == 0) {
                mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
            } else if (opts.get(4).getIsRightAnswer() == 1) {
                mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
            }
        } else {
            mViewHold.rbE.setButtonDrawable(R.mipmap.e_new);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
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
