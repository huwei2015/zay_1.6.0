package com.example.administrator.zahbzayxy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.AllHaveDoTestBean;
import com.example.administrator.zahbzayxy.beans.OptsBean;
import com.example.administrator.zahbzayxy.beans.QuesListBean2;
import com.example.administrator.zahbzayxy.beans.SaveUserErrorPrcticeBean;
import com.example.administrator.zahbzayxy.myviews.EditTextWithScrollView;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/17 0017.
 */
public class TestLookHaveDongAdapter extends RecyclerView.Adapter<TestLookHaveDongAdapter.MyTestViewHold> {
    Context context;
    LayoutInflater inflater;
    List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> list;
    private int optSize;
    private int size;
    private List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity.OptsEntity> opts;
    private int w,h;
    private int weiZhi;
    private MyTestViewHold mViewHold;
    private int quesType;
    private MyRecyclerView recyclerView;
    private TextView dijige;
    private AllHaveDoTestBean.DataEntity.QuesDetailsEntity quesDetailsEntity;
    private AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity answerResult;
    private String userAnswerIds;
    private int isShowParsing;
    private int userIsRight;


    public int getWeiZhi() {
        return weiZhi;
    }
    public TestLookHaveDongAdapter(Context context, List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> list, MyRecyclerView recyclerView, TextView dijige) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
        w = context.getResources().getDisplayMetrics().widthPixels;
        h = context.getResources().getDisplayMetrics().heightPixels;
        this.recyclerView=recyclerView;
        this.dijige=dijige;

    }
    public List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> getList() {
        return list;
    }

    public void setList(List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> list) {
        this.list = list;
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
        mViewHold = new MyTestViewHold(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(MyTestViewHold holder, int position) {
        holder.setIsRecyclable(false);
        weiZhi = position;
        size=list.size();
        quesDetailsEntity = list.get(position);
        answerResult = quesDetailsEntity.getAnswerResult();
        userAnswerIds = answerResult.getUserAnswerIds();
        userIsRight = answerResult.getIsRight();
        opts = list.get(position).getOpts();
        //每题选项的大小
        optSize = opts.size();
        quesType=quesDetailsEntity.getQuesType();
        isShowParsing = quesDetailsEntity.getIsShowParsing();
        if (quesType == 6 || quesType == 4) {
            mViewHold.analysisTv.setText("购买独家解析题库可查看独家解析");
            mViewHold.submitTv.setVisibility(View.GONE);
        } else {
            holder.answer_layout.setVisibility(View.VISIBLE);
            setSelectArr(holder);
            if (isShowParsing==1) {
                mViewHold.parsing_iv.setVisibility(View.VISIBLE);
                mViewHold.taocanSugest_tv.setVisibility(View.GONE);
                mViewHold.parsing_tv.setVisibility(View.VISIBLE);
                mViewHold.parsing_tv.setText(quesDetailsEntity.getParsing());
                mViewHold.anser_tv.setVisibility(View.GONE);
            }else {
                mViewHold.parsing_tv.setText("");
                mViewHold.parsing_iv.setVisibility(View.GONE);
                mViewHold.taocanSugest_tv.setVisibility(View.VISIBLE);
                mViewHold.taocanSugest_tv.setText("购买独家解析题库可查看独家解析");
            }
        }

        if (quesType != 4) {
            mChildPosition = -1;
        }
        if (quesType != 5) {
            mKePosition = -1;
        }

        //每个题的选项内容的集合
        initFuYong();
        if (quesType == 1) {//单选
            //每个题目的集合
            holder.selectorType_tv.setText("["+"选择题"+"]");
            holder.tv.setText(list.get(position).getContent());
            holder.rbC.setVisibility(View.VISIBLE);
            holder.rbD.setVisibility(View.INVISIBLE);
            holder.rbA.setText(opts.get(0).getContent());
            holder.rbB.setText(opts.get(1).getContent());
            if (optSize>=3) {
                holder.rbC.setText(opts.get(2).getContent());
            }
            if (optSize>=4){
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbD.setText(opts.get(3).getContent());
            }if (optSize>=5){
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbE.setText(opts.get(4).getContent());
            }if (optSize>=6){
                holder.rbF.setVisibility(View.VISIBLE);
                holder.rbF.setText(opts.get(5).getContent());
            }
            //答案
            for (int i=0;i<optSize;i++){
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer==1) {
                    int j = i;
                    if (j == 0){
                        mViewHold.anser_tv.setText("答案:"+"A");
                    }else if(j==1){
                        mViewHold.anser_tv.setText("答案:"+"B");
                    }else if(j==2){
                        mViewHold.anser_tv.setText("答案:"+"C");
                    }else if (j==3){
                        mViewHold.anser_tv.setText("答案:"+"D");
                    }else if (j==4){
                        mViewHold.anser_tv.setText("答案:"+"E");
                    }else if (j==5){
                        mViewHold.anser_tv.setText("答案:"+"F");
                    }
                }
            }
                if (userAnswerIds==null){//未答
                    for (int i=0;i<optSize;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (optSize>3){
                                holder.rbD.setButtonDrawable(R.mipmap.d_new);
                            }if (optSize>4){
                                holder.rbE.setButtonDrawable(R.mipmap.e_new);
                            }if (optSize>5){
                                holder.rbF.setButtonDrawable(R.mipmap.e_new);
                            }
                            if (j==0){
                                holder.rbA.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(0).setOptsTag(1);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                            } if (j==1){
                                holder.rbB.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(1).setOptsTag(1);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);

                            } if (j==2){
                                holder.rbC.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(2).setOptsTag(1);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                            }if (j==3){
                                holder.rbD.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(3).setOptsTag(1);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                            }if (j==4){
                                holder.rbE.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(4).setOptsTag(1);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                            }if (j==5){
                                holder.rbF.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(5).setOptsTag(1);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                            }
                        }
                    }

                }else{//已答
                    int optId = opts.get(0).getOptId();
                    int optId1 = opts.get(1).getOptId();
                    int optId2 = 0;
                    if (optSize>2) {
                         optId2= opts.get(2).getOptId();
                    }
                    int optId3=0;
                    if (optSize==4){
                        optId3=opts.get(3).getOptId();
                    }
                    int optId4=0;
                    if (optSize==5){
                        optId4=opts.get(4).getOptId();
                    }
                    int optId5=0;
                    if (optSize==6){
                        optId5=opts.get(5).getOptId();
                    }
                    for (int i=0;i<optSize;i++){//对的答案显示出来
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                //opsTag 0为未做 1为做对 2为做错
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(0).setOptsTag(1);
                            }else if (j==1){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(1).setOptsTag(1);
                            }else if (j==2){
                                mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(2).setOptsTag(1);
                            }else if (j==3){
                                mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(3).setOptsTag(1);
                            }else if (j==4){
                                mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(4).setOptsTag(1);
                            }else if (j==5){
                                mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(5).setOptsTag(1);
                            }
                        }
                    }
                    if (userIsRight!=1){//作错
                        if(userAnswerIds.equals(String.valueOf(optId))){
                            mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            opts.get(0).setOptsTag(2);
                        } if(userAnswerIds.equals(String.valueOf(optId1))){
                            mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            opts.get(1).setOptsTag(2);
                        } if(userAnswerIds.equals(String.valueOf(optId2))){
                            mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            opts.get(2).setOptsTag(2);
                        }if (optSize==4){
                            if(userAnswerIds.equals(String.valueOf(optId3))){
                                mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                                opts.get(3).setOptsTag(2);
                            }
                        }if (optSize==5){
                            if(userAnswerIds.equals(String.valueOf(optId4))){
                                mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                                mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                                opts.get(4).setOptsTag(2);
                            }
                        }if (optSize==6){
                            if(userAnswerIds.equals(String.valueOf(optId5))){
                                mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                                mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                                opts.get(5).setOptsTag(2);
                            }
                        }
                    }
                }
            quesDetailsEntity.setTag(1);
        } else if (quesType == 2) {//多选
            holder.selectorType_tv.setText("["+"多选题"+"]");
            holder.tv.setText(list.get(position).getContent());
            if (optSize>=3) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
            }
            if (optSize>=4){
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbD.setText(opts.get(3).getContent());
            }if (optSize>=5){
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbE.setText(opts.get(4).getContent());
            }if (optSize>=6){
                holder.rbF.setVisibility(View.VISIBLE);
                holder.rbF.setText(opts.get(5).getContent());
            }

            StringBuffer mutilAnswer=new StringBuffer();
            for (int i=0;i<optSize;i++){
                if (opts.get(i).getIsRightAnswer()==1){
                    int j=i;
                    if (j==0){
                        mutilAnswer.append("A");
                    }  if (j==1){
                        mutilAnswer.append("B");
                    }  if (j==2){
                        mutilAnswer.append("C");
                    }  if (j==3){
                        mutilAnswer.append("D");
                    }
                    if (j==4){
                        mutilAnswer.append("E");
                    }
                    if (j==5){
                        mutilAnswer.append("F");
                    }
                }
            }
            mViewHold.anser_tv.setText("答案:"+mutilAnswer);

            if (userAnswerIds==null){//未答
                for (int i=0;i<optSize;i++){
                    int isRightAnswer = opts.get(i).getIsRightAnswer();
                    if (isRightAnswer==1){
                        int j=i;
                        if (j==0){
                            holder.rbA.setButtonDrawable(R.mipmap.test_right);
                            holder.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(0).setOptsTag(1);
                        } if (j==1){
                            holder.rbB.setButtonDrawable(R.mipmap.test_right);
                            holder.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(1).setOptsTag(1);
                        } if (j==2){
                            holder.rbC.setButtonDrawable(R.mipmap.test_right);
                            holder.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(2).setOptsTag(1);

                        }if (j==3){
                            holder.rbD.setButtonDrawable(R.mipmap.test_right);
                            holder.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(3).setOptsTag(1);
                        }if (j==4){
                            holder.rbE.setButtonDrawable(R.mipmap.test_right);
                            holder.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(4).setOptsTag(1);
                        }if (j==5){
                            holder.rbF.setButtonDrawable(R.mipmap.test_right);
                            holder.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(5).setOptsTag(1);
                        }
                    }
                }

            }else {//已答

                int optId = opts.get(0).getOptId();
                int optId1 = opts.get(1).getOptId();
                int optId2 = opts.get(2).getOptId();
                int optId3=0,optId4=0,optId5=0;
                if (optSize>=4){
                    optId3=opts.get(3).getOptId();
                }
                if (optSize>=5){
                    optId4=opts.get(4).getOptId();
                } if (optSize>=6){
                    optId5=opts.get(5).getOptId();
                }
                for (int i=0;i<optSize;i++){//对的答案显示出来
                    int isRightAnswer = opts.get(i).getIsRightAnswer();
                    if (isRightAnswer==1){
                        int j=i;
                        if (j==0){
                            //opsTag 0为未做 1为做对 2为做错
                            mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(0).setOptsTag(1);
                        } if (j==1){
                            mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(1).setOptsTag(1);
                        } if (j==2){
                            mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(2).setOptsTag(1);
                        }if (j==3){
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(3).setOptsTag(1);
                        }if (j==4){
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(4).setOptsTag(1);
                        }if (j==5){
                            mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(5).setOptsTag(1);
                        }
                    }
                }


                if (userIsRight!=1){//作错
                    if(userAnswerIds.contains(String.valueOf(optId))){
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        holder.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        opts.get(0).setOptsTag(2);
                    } if(userAnswerIds.contains(String.valueOf(optId1))){
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        holder.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        opts.get(1).setOptsTag(2);
                    } if(userAnswerIds.contains(String.valueOf(optId2))){
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                        holder.rbC.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        opts.get(2).setOptsTag(2);
                    }if (optSize>=4){
                        if(userAnswerIds.contains(String.valueOf(optId3))){
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                            holder.rbD.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            opts.get(3).setOptsTag(2);
                        }
                    }if (optSize>=5){
                        if(userAnswerIds.contains(String.valueOf(optId4))){
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                            holder.rbE.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            opts.get(4).setOptsTag(2);
                        }
                    }if (optSize>=6){
                        if(userAnswerIds.contains(String.valueOf(optId5))){
                            mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                            holder.rbF.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            opts.get(5).setOptsTag(2);
                        }
                    }
                }
            }
         quesDetailsEntity.setTag(1);

        } else if (quesType==3){//判断
            holder.selectorType_tv.setText("["+"判断题"+"]");
            holder.tv.setText(list.get(position).getContent());
            holder.rbA.setText(opts.get(0).getContent());
            holder.rbB.setText(opts.get(1).getContent());
            holder.rbC.setVisibility(View.INVISIBLE);
            holder.rbD.setVisibility(View.INVISIBLE);
            //答案
                for (int i = 0; i < optSize; i++) {
                    int isRightAnswer = opts.get(i).getIsRightAnswer();
                    if (isRightAnswer == 1) {
                        int j = i;
                        if (j == 0) {
                            mViewHold.anser_tv.setText("答案:" + "A");
                        } else if (j == 1) {
                            mViewHold.anser_tv.setText("答案:" + "B");
                        }
                    }
                }

            if (userAnswerIds==null){//未答
                for (int i=0;i<optSize;i++){
                    int isRightAnswer = opts.get(i).getIsRightAnswer();
                    if (isRightAnswer==1){
                        int j=i;
                        if (j==0){
                            holder.rbA.setButtonDrawable(R.mipmap.test_right);
                            holder.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            holder.rbB.setButtonDrawable(R.mipmap.b_new);
                        } if (j==1){
                            holder.rbB.setButtonDrawable(R.mipmap.test_right);
                            holder.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            holder.rbA.setButtonDrawable(R.mipmap.a_new);

                        }
                    }
                }

            }else {//已答

                int optId = opts.get(0).getOptId();
                int optId1 = opts.get(1).getOptId();

                for (int i=0;i<optSize;i++){//对的答案显示出来
                    int isRightAnswer = opts.get(i).getIsRightAnswer();
                    if (isRightAnswer==1){
                        int j=i;
                        if (j==0){
                            //opsTag 0为未做 1为做对 2为做错
                            mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(0).setOptsTag(1);
                        }else if (j==1){
                            mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(1).setOptsTag(1);
                        }
                    }
                }

                if (userIsRight!=1){//作错
                    if(userAnswerIds.equals(String.valueOf(optId))){
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        holder.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        opts.get(0).setOptsTag(2);
                    } if(userAnswerIds.equals(String.valueOf(optId1))){
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        holder.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        opts.get(1).setOptsTag(2);
                    }
                }
            }
            quesDetailsEntity.setTag(1);
        } else if (quesType==4) {//主管案例
            holder.questionTypeTv.setText("主观案例题");
            mKePosition = -1;
            if (mChildPosition < 0) mChildPosition += 1;
            holder.questionTypeTv.setText("[" + "主观案例题" + "]");
            // 获取小题集合
            List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> childrenList = quesDetailsEntity.getChildren();
            // 防止数组角标越界
            if (mChildPosition < 0 || mChildPosition >= childrenList.size()) return;
            AllHaveDoTestBean.DataEntity.QuesDetailsEntity children = childrenList.get(mChildPosition);
            // 小题的题干
            String childContent = children.getContent();
            String content = list.get(position).getContent() + "<br />" + (mChildPosition + 1) + "、" + childContent;
            holder.questionTitleTv.setText(Html.fromHtml(content));
            AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity answerResult = children.getAnswerResult();
            holder.answerEt.setText("未作答");
            holder.answerEt.setEnabled(false);
            boolean isRight = false;
            if (answerResult != null) {
                // 用户的答题
                String userCount = answerResult.getUserAnswerIds();
                if (!TextUtils.isEmpty(userCount)) {
                    holder.answerEt.setText(userCount);
                    isRight = true;
                }
            }
            List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity.OptsEntity> optsList = children.getOpts();
            if (optsList != null && optsList.size() > 0) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity.OptsEntity optsEntity = optsList.get(0);
                optsEntity.setOptsTag(isRight?1:2);
                mViewHold.answerTv.setText("答案: " + Html.fromHtml(optsEntity.getContent()));
            }
            mChildPosition++;
        } else if (quesType==5) {//客观案例
            if (mKePosition < 0) mKePosition += 1;
            mChildPosition = -1;
            holder.selectorType_tv.setText("[" + "客观案例题" + "]");
            // 获取小题集合
            List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity> childrenList = quesDetailsEntity.getChildren();
            // 防止数组角标越界
            if (mKePosition < 0 || mKePosition >= childrenList.size()) return;
            // 获取小题的bean
            AllHaveDoTestBean.DataEntity.QuesDetailsEntity children = childrenList.get(mKePosition);
            // 小题的题干
            String childContent = children.getContent();
            // 小题的选项集合
            List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity.OptsEntity> optsList = children.getOpts();
            // 重置选项的显示
            AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity answerResult = children.getAnswerResult();
            setShowSelectBtn(optsList, answerResult);
            int localType = children.getQuesType();
            String type = localType == 1 ? "[" + "单项题" + "]" : localType == 2 ? "[" + "多项题" + "]" : "[" + "判断题" + "]";

            String content = list.get(position).getContent();
            StringBuilder sb = new StringBuilder();
            sb.append(content).append("<br />").append(type).append("<br />").append(mKePosition + 1).append("、").append(childContent);
            holder.tv.setText(Html.fromHtml(sb.toString()));

            StringBuffer mutilAnswer = new StringBuffer();
            char s = 'A';
            for (int i = 0; i < optsList.size(); i++) {
                if (optsList.get(i).getIsRightAnswer() == 1) {
                    mutilAnswer.append(s);
                }
                s++;
            }
            mViewHold.anser_tv.setText("答案:" + mutilAnswer);

            mKePosition++;
        } else if (quesType==6) {//简答题
            holder.questionTypeTv.setText("[" + "简答题" + "]");
            String questionContent = quesDetailsEntity.getContent();
            holder.questionTitleTv.setText(Html.fromHtml(questionContent));
            AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity answerResult = quesDetailsEntity.getAnswerResult();
            holder.answerEt.setText("未作答");
            holder.answerEt.setEnabled(false);
            boolean isRight = false;
            if (answerResult != null) {
                // 用户的答题
                String userCount = answerResult.getUserAnswerIds();
                if (!TextUtils.isEmpty(userCount)) {
                    holder.answerEt.setText(userCount);
                    isRight = true;
                }
            }
            List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity.OptsEntity> optsList = quesDetailsEntity.getOpts();
            if (optsList != null && optsList.size() > 0) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity.OptsEntity optsEntity = optsList.get(0);
                optsEntity.setOptsTag(isRight?1:2);
                mViewHold.answerTv.setText("答案: " + Html.fromHtml(optsEntity.getContent()));
            }
        }
    }

    private RadioButton[] mSelectArr = new RadioButton[6];
    private int[] mSelectImgArr = new int[6];

    private void setSelectArr(MyTestViewHold hold) {
        mSelectArr[0] = hold.rbA;
        mSelectArr[1] = hold.rbB;
        mSelectArr[2] = hold.rbC;
        mSelectArr[3] = hold.rbD;
        mSelectArr[4] = hold.rbE;
        mSelectArr[5] = hold.rbF;
        mSelectImgArr[0] = R.mipmap.a_new;
        mSelectImgArr[1] = R.mipmap.b_new;
        mSelectImgArr[2] = R.mipmap.c_new;
        mSelectImgArr[3] = R.mipmap.d_new;
        mSelectImgArr[4] = R.mipmap.e_new;
        mSelectImgArr[5] = R.mipmap.f_new;
    }

    /**
     * 设置选择按钮展示
     *
     * @param optsList 展示的选项
     * @param answerResult
     */
    private void setShowSelectBtn(List<AllHaveDoTestBean.DataEntity.QuesDetailsEntity.OptsEntity> optsList, AllHaveDoTestBean.DataEntity.QuesDetailsEntity.AnswerResultEntity answerResult) {
        if (optsList == null) optsList = new ArrayList<>();
        for (int i = 0; i < mSelectArr.length; i++) {
            RadioButton rb = mSelectArr[i];
            rb.setButtonDrawable(mSelectImgArr[i]);
            String userAnswerIds = answerResult.getUserAnswerIds();
            if (i < optsList.size()) {
                AllHaveDoTestBean.DataEntity.QuesDetailsEntity.OptsEntity optsEntity = optsList.get(i);
                rb.setVisibility(View.VISIBLE);
                rb.setText(Html.fromHtml(optsEntity.getContent()));
                if (optsEntity.getIsRightAnswer() == 1) {
                    rb.setButtonDrawable(R.mipmap.test_right);
                } else {
                    if (!TextUtils.isEmpty(userAnswerIds) && userAnswerIds.contains(String.valueOf(optsEntity.getOptId()))) {
                        rb.setButtonDrawable(R.mipmap.test_error);
                    }
                }
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


    private void initFuYong() {
        if (quesType==1) {//单选
            mViewHold.rbA.setVisibility(View.VISIBLE);
            mViewHold.rbB.setVisibility(View.VISIBLE);
            mViewHold.rbC.setVisibility(View.VISIBLE);
            mViewHold.rbD.setVisibility(View.INVISIBLE);
            mViewHold.rbE.setVisibility(View.INVISIBLE);
            mViewHold.rbF.setVisibility(View.INVISIBLE);
            if (optSize==4){
                mViewHold.rbD.setVisibility(View.VISIBLE);
                if (quesDetailsEntity.getTag()!=1){
                    mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                }
            }
            if (optSize>=3) {
                if (quesDetailsEntity.getTag() != 1) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                    mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                    mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));

                }else {//tag==1
                    int optsTag = opts.get(0).getOptsTag();
                    if (optsTag==0){
                        mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    }else if (optsTag==1){
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    }else if (optsTag==2){
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                    }

                    int optsTag1 = opts.get(1).getOptsTag();
                    if (optsTag1==0){
                        mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    }else if (optsTag1==1){
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    }else if (optsTag1==2){
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                    }

                    int optsTag2 = opts.get(2).getOptsTag();
                    if (optsTag2==0){
                        mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    }else if (optsTag2==1){
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    }else if (optsTag2==2){
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                    }
                    if (optSize==4){
                        int optsTag3 = opts.get(3).getOptsTag();
                        if (optsTag3==0){
                            mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                        }else if (optsTag3==1){
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        }else if (optsTag3==2){
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                        }
                    }
                    if (optSize==5){
                        int optsTag4 = opts.get(4).getOptsTag();
                        if (optsTag4==0){
                            mViewHold.rbE.setButtonDrawable(R.mipmap.e_new);
                            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
                        }else if (optsTag4==1){
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        }else if (optsTag4==2){
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                        }
                    }
                    if (optSize==6){
                        int optsTag5 = opts.get(5).getOptsTag();
                        if (optsTag5==0){
                            mViewHold.rbF.setButtonDrawable(R.mipmap.f_new);
                            mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.testBlack));
                        }else if (optsTag5==1){
                            mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        }else if (optsTag5==2){
                            mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                        }
                    }
                }
            }

        }else if (quesType==2){//多选
            mViewHold.rbD.setVisibility(View.INVISIBLE);
            mViewHold.rbE.setVisibility(View.INVISIBLE);
            mViewHold.rbF.setVisibility(View.INVISIBLE);
            if (quesDetailsEntity.getTag()!=1) {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                if (optSize>=4) {
                    mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                }
                if (optSize>=5){
                    mViewHold.rbE.setButtonDrawable(R.mipmap.e_new);
                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
                } if (optSize>=6){
                    mViewHold.rbF.setButtonDrawable(R.mipmap.f_new);
                    mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.testBlack));
                }
            }else {

                int optsTag = opts.get(0).getOptsTag();
                if (optsTag==0){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                }else if (optsTag==1){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                }else if (optsTag==2){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                }

                int optsTag1 = opts.get(1).getOptsTag();
                if (optsTag1==0){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                }else if (optsTag1==1){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                }else if (optsTag1==2){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                }

                int optsTag2 = opts.get(2).getOptsTag();
                if (optsTag2==0){
                    mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                }else if (optsTag2==1){
                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                }else if (optsTag2==2){
                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                }
                if (optSize>=4) {
                    mViewHold.rbD.setVisibility(View.VISIBLE);
                    int optsTag3 = opts.get(3).getOptsTag();
                    if (optsTag3 == 0) {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
                        mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                    } else if (optsTag2 == 1) {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    } else if (optsTag2 == 2) {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                    }
                }
               if (optSize>=5){
                   mViewHold.rbE.setVisibility(View.VISIBLE);
                   int optsTag4 = opts.get(4).getOptsTag();
                   if (optsTag4==0){
                       mViewHold.rbE.setButtonDrawable(R.mipmap.e_new);
                       mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
                   }else if (optsTag4==1){
                       mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                       mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                   }else if (optsTag4==2){
                       mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                       mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                   }
               }
                if (optSize>=6){
                    mViewHold.rbF.setVisibility(View.VISIBLE);
                    int optsTag5 = opts.get(5).getOptsTag();
                    if (optsTag5==0){
                        mViewHold.rbF.setButtonDrawable(R.mipmap.f_new);
                        mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.testBlack));
                    }else if (optsTag5==1){
                        mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    }else if (optsTag5==2){
                        mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                    }
                }

            }


        }else if (quesType==3){//判断
            mViewHold.rbA.setVisibility(View.VISIBLE);
            mViewHold.rbB.setVisibility(View.VISIBLE);
            mViewHold.rbC.setVisibility(View.GONE);
            mViewHold.rbD.setVisibility(View.GONE);

            if (quesDetailsEntity.getTag()!=1) {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
            }else {//tag==1
                int optsTag = opts.get(0).getOptsTag();
                if (optsTag==0){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                }else if (optsTag==1){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                }else if (optsTag==2){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                }

                int optsTag1 = opts.get(1).getOptsTag();
                if (optsTag1==0){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                }else if (optsTag1==1){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                }else if (optsTag1==2){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyTestViewHold extends RecyclerView.ViewHolder{
        TextView tv,selectorType_tv;
        LinearLayout ll;
        RadioButton rbA,rbB,rbC,rbD,rbE,rbF;
        RelativeLayout answer_layout;
        TextView anser_tv,parsing_tv,parsing_iv,taocanSugest_tv;

        TextView questionTypeTv, questionTitleTv, submitTv, answerTv, analysisTv;
        EditTextWithScrollView answerEt;

        @SuppressLint("WrongViewCast")
        public MyTestViewHold(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView);
            ll = (LinearLayout) itemView.findViewById(R.id.jjjjjj);
            rbA = (RadioButton) itemView.findViewById(R.id.radioButtonA);
            rbB = (RadioButton) itemView.findViewById(R.id.radioButtonB);
            rbC = (RadioButton) itemView.findViewById(R.id.radioButtonC);
            rbD = (RadioButton) itemView.findViewById(R.id.radioButtonD);
            rbE= (RadioButton) itemView.findViewById(R.id.radioButtonE);
            rbF= (RadioButton) itemView.findViewById(R.id.radioButtonF);
            answer_layout= (RelativeLayout) itemView.findViewById(R.id.answer_layout);
            anser_tv= (TextView) itemView.findViewById(R.id.anwer_choice);
            parsing_tv= (TextView) itemView.findViewById(R.id.detailAnswer_tv);
            selectorType_tv= (TextView) itemView.findViewById(R.id.selectorType_tv);
            parsing_iv= (TextView) itemView.findViewById(R.id.xiangjie_iv);
            taocanSugest_tv= (TextView) itemView.findViewById(R.id.taocan_suggest_tv);

            questionTypeTv = itemView.findViewById(R.id.item_test_question_type_tv);
            questionTitleTv = itemView.findViewById(R.id.item_test_question_title_tv);
            answerEt = itemView.findViewById(R.id.item_test_question_answer_et);
            submitTv = itemView.findViewById(R.id.item_test_question_submit_ev);
            answerTv = itemView.findViewById(R.id.item_test_question_real_answer_tv);
            analysisTv = itemView.findViewById(R.id.item_test_question_analysis_tv);
        }
    }
}
