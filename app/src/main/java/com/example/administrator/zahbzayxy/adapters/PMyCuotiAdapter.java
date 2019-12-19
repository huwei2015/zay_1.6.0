package com.example.administrator.zahbzayxy.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PrictaceErrorBean;
import com.example.administrator.zahbzayxy.myinterface.MyInterface;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;

import java.util.List;

/**
 * Created by ${ZWJ} on 2017/4/18 0018.
 */
public class PMyCuotiAdapter extends RecyclerView.Adapter<PMyCuotiAdapter.MyTestViewHold> {
    Context context;
    LayoutInflater inflater;

    List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> list;
    private int isShowParsing;

    public List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> getList() {
        return list;
    }
    private int optSize;
    private int size;
    private List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity.OptsEntity> opts;
    private int w,h;
    private int weiZhi;
    private MyTestViewHold mViewHold;
    private int quesType;
    private MyRecyclerView recyclerView;
    private TextView dijige;
    private PrictaceErrorBean.DataEntity.ErrorRecordsEntity quesDetailsEntity;
    private String userAnswerIds;
    MyInterface.ErrorOnClickedListenner clickedListenner;
    private int questionId;
    public int getWeiZhi() {
        return weiZhi;
    }
    public PMyCuotiAdapter(MyInterface.ErrorOnClickedListenner clickedListenner,List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> list,
                          Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
        w = context.getResources().getDisplayMetrics().widthPixels;
        h = context.getResources().getDisplayMetrics().heightPixels;
        this.clickedListenner=clickedListenner;
    }
    @Override
    public MyTestViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_test_qustion_layout, parent, false);
        mViewHold = new MyTestViewHold(view);
        return mViewHold;
    }
    @Override
    public void onBindViewHolder(MyTestViewHold holder, int position) {
        holder.answer_layout.setVisibility(View.VISIBLE);
        weiZhi = position;
        size=list.size();
        quesDetailsEntity = list.get(position);
        isShowParsing = quesDetailsEntity.getIsShowParsing();
        userAnswerIds = quesDetailsEntity.getErrorAnswerIds();
        opts=quesDetailsEntity.getOpts();
        //没提选项的大小
        optSize = opts.size();
        quesType=quesDetailsEntity.getQuesType();
        questionId = list.get(position).getQuestionId();
        clickedListenner.onMyItemClickedListenner(questionId,position);
      //  holder.parsing_tv.setText(quesDetailsEntity.getParsing());
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
        if (optSize>0) {
            fuyong();
        }
        if (quesType == 1) {//单选
            //每个题目的集合
            holder.selectorType_tv.setText("["+"选择题"+"]");
            holder.tv.setText(quesDetailsEntity.getContent());
            if (optSize==3){
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                if (userAnswerIds==null){//未答
                    for (int i=0;i<3;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                holder.rbA.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                                holder.anser_tv.setText("答案:"+"A");
                                Log.e("noDone","11111");

                            } if (j==1){
                                holder.rbB.setButtonDrawable(R.mipmap.test_right);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                                Log.e("noDone","2222");
                                holder.anser_tv.setText("答案:"+"B");
                            } if (j==2){
                                holder.rbC.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                Log.e("noDone","3333");
                                holder.anser_tv.setText("答案:"+"C");
                            }
                            quesDetailsEntity.setBiaoJi(1);
                        }
                    }

                }else{//已答
                    int optId = opts.get(0).getId();
                    int optId1 = opts.get(1).getId();
                    int optId2 = opts.get(2).getId();
                    for (int i=0;i<optSize;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                //opsTag 0为未做 1为做对 2为做错
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(0).setTag(1);
                                if (!userAnswerIds.equals(optId2)) {
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }
                                else if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"A");

                            }else if (j==1){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(1).setTag(1);
                                if (!userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                } else if (!userAnswerIds.equals(optId2)){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"B");

                            }else if (j==2){
                                mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(2).setTag(1);
                                if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                } else if (userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"C");
                            }
                        }
                    }

                    quesDetailsEntity.setBiaoJi(1);

                }




            }else if (optSize==2){
                holder.rbC.setVisibility(View.INVISIBLE);
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                if (userAnswerIds==null){//未答
                    for (int i=0;i<2;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                holder.rbA.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);

                            } if (j==1){
                                holder.rbB.setButtonDrawable(R.mipmap.test_right);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                            }
                            quesDetailsEntity.setBiaoJi(1);
                        }
                    }

                }else{//已答

                    int optId = opts.get(0).getId();
                    int optId1 = opts.get(1).getId();
                    for (int i=0;i<optSize;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                //opsTag 0为未做 1为做对 2为做错
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(0).setTag(1);

                               if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"A");

                            }else if (j==1){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(1).setTag(1);
                                if (!userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"B");

                            }
                        }
                    }

                    quesDetailsEntity.setBiaoJi(1);

                }


            }else if (optSize==4) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());

                if (userAnswerIds==null){//未答
                    for (int i=0;i<4;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                holder.rbA.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                                holder.rbD.setButtonDrawable(R.mipmap.d_new);


                            } if (j==1){
                                holder.rbB.setButtonDrawable(R.mipmap.test_right);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                                holder.rbD.setButtonDrawable(R.mipmap.d_new);

                            } if (j==2){
                                holder.rbC.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbD.setButtonDrawable(R.mipmap.d_new);
                            } if (j==3){
                                holder.rbD.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                            }
                            quesDetailsEntity.setBiaoJi(1);
                        }
                    }

                }else{//已答
                    int optId2 = 0,optId3 = 0;
                    int optId = opts.get(0).getId();
                    int optId1 = opts.get(1).getId();
                   // if (optSize>=3) {
                        optId2 = opts.get(2).getId();
                  //  }
                  //  if (optSize>=4) {
                        optId3 = opts.get(3).getId();
                 //   }

                    for (int i=0;i<optSize;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                //opsTag 0为未做 1为做对 2为做错
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(0).setTag(1);
                                if (!userAnswerIds.equals(optId2)) {
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }
                                else if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                } else if (!userAnswerIds.equals(optId3)){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(3).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"A");

                            }else if (j==1){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(1).setTag(1);
                                if (!userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                } else if (!userAnswerIds.equals(optId2)){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }else if (!userAnswerIds.equals(optId3)){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(3).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"B");

                            }else if (j==2){
                                mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(2).setTag(1);
                                if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                } else if (userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                }else if (!userAnswerIds.equals(optId3)){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(3).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"C");
                            }else if (j==3){
                                mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(3).setTag(1);
                                if (!userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                } else if (userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                }else if (userAnswerIds.equals(optId2)){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"D");
                            }
                        }
                    }
                    quesDetailsEntity.setBiaoJi(1);

                }
            }
            else if (optSize==5) {
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());
                if (userAnswerIds==null){//未答
                    for (int i=0;i<optSize;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                holder.rbA.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                                holder.rbD.setButtonDrawable(R.mipmap.d_new);
                                holder.rbE.setButtonDrawable(R.mipmap.e_new);


                            } if (j==1){
                                holder.rbB.setButtonDrawable(R.mipmap.test_right);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                                holder.rbD.setButtonDrawable(R.mipmap.d_new);
                                holder.rbE.setButtonDrawable(R.mipmap.e_new);

                            } if (j==2){
                                holder.rbC.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbD.setButtonDrawable(R.mipmap.d_new);
                                holder.rbE.setButtonDrawable(R.mipmap.e_new);
                            } if (j==3){
                                holder.rbD.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                                holder.rbE.setButtonDrawable(R.mipmap.e_new);
                            }if (j==4){
                                holder.rbE.setButtonDrawable(R.mipmap.test_right);
                                holder.rbB.setButtonDrawable(R.mipmap.b_new);
                                holder.rbA.setButtonDrawable(R.mipmap.a_new);
                                holder.rbC.setButtonDrawable(R.mipmap.c_new);
                                holder.rbD.setButtonDrawable(R.mipmap.d_new);
                            }
                            quesDetailsEntity.setBiaoJi(1);
                        }
                    }

                }else{//已答
                    int optId2 = 0,optId3 = 0,optId4 = 0;
                    int optId = opts.get(0).getId();
                    int optId1 = opts.get(1).getId();
                    // if (optSize>=3) {
                    optId2 = opts.get(2).getId();
                    //  }
                    //  if (optSize>=4) {
                    optId3 = opts.get(3).getId();
                    //   }
                    optId4 = opts.get(4).getId();
                    for (int i=0;i<optSize;i++){
                        int isRightAnswer = opts.get(i).getIsRightAnswer();
                        if (isRightAnswer==1){
                            int j=i;
                            if (j==0){
                                //opsTag 0为未做 1为做对 2为做错
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(0).setTag(1);
                                if (!userAnswerIds.equals(optId2)) {
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }
                                else if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                } else if (!userAnswerIds.equals(optId3)){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(3).setTag(2);
                                } else if (!userAnswerIds.equals(optId4)){
                                    mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(4).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"A");

                            }else if (j==1){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(1).setTag(1);
                                if (!userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                } else if (!userAnswerIds.equals(optId2)){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }else if (!userAnswerIds.equals(optId3)){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(3).setTag(2);
                                } else if (!userAnswerIds.equals(optId4)){
                                    mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(4).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"B");

                            }else if (j==2){
                                mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(2).setTag(1);
                                if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                } else if (userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                }else if (!userAnswerIds.equals(optId3)){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(3).setTag(2);
                                } else if (!userAnswerIds.equals(optId4)){
                                    mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(4).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"C");
                            }else if (j==3){
                                mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(3).setTag(1);
                                if (!userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                } else if (userAnswerIds.equals(optId2)){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }else if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                } else if (!userAnswerIds.equals(optId4)){
                                    mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(4).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"D");
                            }else if (j==4){
                                mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                                mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                                opts.get(4).setTag(1);
                                if (!userAnswerIds.equals(optId)){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(0).setTag(2);
                                } else if (userAnswerIds.equals(optId2)){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(2).setTag(2);
                                }else if (!userAnswerIds.equals(optId1)){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(1).setTag(2);
                                } else if (!userAnswerIds.equals(optId3)){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                    opts.get(3).setTag(2);
                                }
                                holder.anser_tv.setText("答案:"+"E");
                            }
                        }
                    }
                    quesDetailsEntity.setBiaoJi(1);

                }
            }



        } else if (quesType == 2) {//多选
            holder.selectorType_tv.setText("["+"多选题"+"]");
            holder.tv.setText(list.get(position).getContent());
            holder.rbA.setVisibility(View.VISIBLE);
            holder.rbB.setVisibility(View.VISIBLE);
            holder.rbC.setVisibility(View.VISIBLE);
            holder.rbD.setVisibility(View.VISIBLE);
            StringBuffer mutilAnswer1=new StringBuffer();
            for (int i=0;i<optSize;i++){
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer==1){
                    if (i==0){
                        mutilAnswer1.append("A");
                    } if (i==1){
                        mutilAnswer1.append("B");
                    } if (i==2){
                        mutilAnswer1.append("C");
                    } if (i==3){
                        mutilAnswer1.append("D");
                    } if (i==4){
                        mutilAnswer1.append("E");
                    }if (i==5){
                        mutilAnswer1.append("F");
                    }
                }
            }
            mViewHold.anser_tv.setText("答案:"+mutilAnswer1);

            if (optSize==4){
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbE.setText("");
                holder.rbF.setText("");
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());

                if (userAnswerIds==null){//未答

                    for (int i=0;i<optSize;i++){
                        if (opts.get(i).getIsRightAnswer()==1){
                            if (i==0){
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==1){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==2){
                                mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==3){
                                mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            }
                        }
                    }
                    quesDetailsEntity.setBiaoJi(1);


                }else {//已答
                    int optId = opts.get(0).getId();
                    int optId1 = opts.get(1).getId();
                    int optId2 = opts.get(2).getId();
                    int optId3 = opts.get(3).getId();
                    for (int i = 0; i < optSize; i++) {
                        if (opts.get(i).getIsRightAnswer() == 1) {
                            int j = i;
                            if (j == 0) {
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                                opts.get(0).setTag(1);
                                if (!userAnswerIds.contains(String.valueOf(optId1))){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(1).setTag(2);
                                }  if (!userAnswerIds.contains(String.valueOf(optId2))){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(2).setTag(2);
                                } if (!userAnswerIds.contains(String.valueOf(optId3))){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(3).setTag(2);
                                }
                            }
                            if (j == 1) {
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                                opts.get(1).setTag(1);
                                if (!userAnswerIds.contains(String.valueOf(optId))){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(0).setTag(2);
                                }  if (!userAnswerIds.contains(String.valueOf(optId2))){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(2).setTag(2);
                                } if (!userAnswerIds.contains(String.valueOf(optId3))){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(3).setTag(2);
                                }
                            }
                            if (j == 2) {
                                mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                                opts.get(2).setTag(1);
                                if (!userAnswerIds.contains(String.valueOf(optId))){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(0).setTag(2);
                                }  if (!userAnswerIds.contains(String.valueOf(optId1))){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(1).setTag(2);
                                } if (!userAnswerIds.contains(String.valueOf(optId3))){
                                    mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(3).setTag(2);
                                }
                            }
                            if (j == 3) {
                                mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                                opts.get(3).setTag(1);
                                if (!userAnswerIds.contains(String.valueOf(optId))){
                                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(0).setTag(2);
                                }  if (!userAnswerIds.contains(String.valueOf(optId2))){
                                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(2).setTag(2);
                                } if (!userAnswerIds.contains(String.valueOf(optId1))){
                                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                    opts.get(1).setTag(2);
                                }
                            }
                        }
                    }
                }
            }
            else if (optSize==5){
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbF.setText("");
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());

                if (userAnswerIds==null){//未答
                    for (int i=0;i<optSize;i++){
                        if (opts.get(i).getIsRightAnswer()==1){
                            if (i==0){
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==1){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==2){
                                mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==3){
                                mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            }if (i==4){
                                mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                            }
                            opts.get(i).setTag(1);
                        }
                    }

                    quesDetailsEntity.setBiaoJi(1);


                }else{//已答

                    int optId = opts.get(0).getId();
                    int optId1 = opts.get(1).getId();
                    int optId2 = opts.get(2).getId();
                    int optId3 = opts.get(3).getId();
                    int optId4 = opts.get(4).getId();
                    if (userAnswerIds.contains(String.valueOf(optId))){
                        if (opts.get(0).getIsRightAnswer()==1){
                            mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                            opts.get(0).setTag(1);
                        }else {
                            mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                            opts.get(0).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId1))){
                        if (opts.get(1).getIsRightAnswer()==1){
                            mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                            opts.get(1).setTag(1);
                        }else {
                            mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                            opts.get(1).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId2))){
                        if (opts.get(2).getIsRightAnswer()==1){
                            mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                            opts.get(2).setTag(1);
                        }else {
                            mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                            opts.get(2).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId3))){
                        if (opts.get(3).getIsRightAnswer()==1){
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            opts.get(3).setTag(1);
                        }else {
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                            opts.get(3).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId4))){
                        if (opts.get(4).getIsRightAnswer()==1){
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                            opts.get(4).setTag(1);
                        }else {
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                            opts.get(4).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                }
            }else if (optSize==6){
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());
                holder.rbF.setText(opts.get(5).getContent());
                if (userAnswerIds==null){//未答
                    for (int i=0;i<optSize;i++){
                        if (opts.get(i).getIsRightAnswer()==1){
                            if (i==0){
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==1){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==2){
                                mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                            }  if (i==3){
                                mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            }if (i==4){
                                mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                            }if (i==5){
                                mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                            }
                        }
                        opts.get(i).setTag(1);
                    }

                    quesDetailsEntity.setBiaoJi(1);


                }else{//已答
                    int optId = opts.get(0).getId();
                    int optId1 = opts.get(1).getId();
                    int optId2 = opts.get(2).getId();
                    int optId3 = opts.get(3).getId();
                    int optId4 = opts.get(4).getId();
                    int optId5 = opts.get(5).getId();
                    if (userAnswerIds.contains(String.valueOf(optId))){
                        if (opts.get(0).getIsRightAnswer()==1){
                            mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                            opts.get(0).setTag(1);
                        }else {
                            mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                            opts.get(0).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId1))){
                        if (opts.get(1).getIsRightAnswer()==1){
                            mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                            opts.get(1).setTag(1);
                        }else {
                            mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                            opts.get(1).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId2))){
                        if (opts.get(2).getIsRightAnswer()==1){
                            mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                            opts.get(2).setTag(1);
                        }else {
                            mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                            opts.get(2).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId3))){
                        if (opts.get(3).getIsRightAnswer()==1){
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            opts.get(3).setTag(1);
                        }else {
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                            opts.get(3).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId4))){
                        if (opts.get(4).getIsRightAnswer()==1){
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                            opts.get(4).setTag(1);
                        }else {
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                            opts.get(4).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                    if (userAnswerIds.contains(String.valueOf(optId5))){
                        if (opts.get(5).getIsRightAnswer()==1){
                            mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                            opts.get(5).setTag(1);
                        }else {
                            mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                            opts.get(5).setTag(2);
                        }
                        quesDetailsEntity.setBiaoJi(1);

                    }
                }
            }
        } else if (quesType==3){//判断
            holder.selectorType_tv.setText("["+"判断题"+"]");
            holder.tv.setText(list.get(position).getContent());
            holder.rbA.setText(opts.get(0).getContent());
            holder.rbB.setText(opts.get(1).getContent());
            holder.rbC.setVisibility(View.INVISIBLE);
            holder.rbD.setVisibility(View.INVISIBLE);

            if (userAnswerIds==null){//未答
                for (int i=0;i<2;i++){
                    int isRightAnswer = opts.get(i).getIsRightAnswer();
                    if (isRightAnswer==1){
                        int j=i;
                        if (j==0){
                            holder.rbA.setButtonDrawable(R.mipmap.test_right);
                            holder.rbB.setButtonDrawable(R.mipmap.b_new);
                            holder.anser_tv.setText("答案:"+"A");

                        } if (j==1){
                            holder.rbB.setButtonDrawable(R.mipmap.test_right);
                            holder.rbA.setButtonDrawable(R.mipmap.a_new);
                            holder.anser_tv.setText("答案:"+"B");
                        }
                        quesDetailsEntity.setBiaoJi(1);
                    }
                }

            }else{//已答
                int optId = opts.get(0).getId();
                int optId1 = opts.get(1).getId();

                for (int i=0;i<optSize;i++){
                    int isRightAnswer = opts.get(i).getIsRightAnswer();
                    if (isRightAnswer==1){
                        int j=i;
                        if (j==0){
                            //opsTag 0为未做 1为做对 2为做错
                            mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(0).setTag(1);
                            if (!userAnswerIds.equals(optId1)){
                                mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                opts.get(1).setTag(2);
                            }
                            holder.anser_tv.setText("答案:"+"A");
                        }else if (j==1){
                            mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            opts.get(1).setTag(1);
                            if (!userAnswerIds.equals(optId)){
                                mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowbgLing));
                                opts.get(0).setTag(2);
                            }
                            holder.anser_tv.setText("答案:"+"B");
                        }
                    }
                }

                quesDetailsEntity.setBiaoJi(1);

            }


        }


    }


    private void fuyong() {

        if (quesType==1) {//单选
            mViewHold.rbA.setVisibility(View.VISIBLE);
            mViewHold.rbB.setVisibility(View.VISIBLE);
            mViewHold.rbC.setVisibility(View.VISIBLE);
            mViewHold.rbD.setVisibility(View.INVISIBLE);
            mViewHold.rbE.setVisibility(View.INVISIBLE);
            if (optSize==3) {
                if (quesDetailsEntity.getBiaoJi() != 1) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                    mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                    mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                }else {//tag==1
                    int optsTag = opts.get(0).getTag();
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

                    int optsTag1 = opts.get(1).getTag();
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

                    int optsTag2 = opts.get(2).getTag();
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
                }
            }
            if (optSize==4){
                mViewHold.rbD.setVisibility(View.VISIBLE);
                mViewHold.rbE.setVisibility(View.INVISIBLE);
                if (quesDetailsEntity.getBiaoJi() != 1) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                    mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                    mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                    mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                }else {//tag==1
                    int optsTag = opts.get(0).getTag();
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

                    int optsTag1 = opts.get(1).getTag();
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

                    int optsTag2 = opts.get(2).getTag();
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
                    int optsTag3 = opts.get(3).getTag();
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
            }
            if (optSize==5){
                mViewHold.rbD.setVisibility(View.VISIBLE);
                mViewHold.rbE.setVisibility(View.VISIBLE);
                mViewHold.rbF.setVisibility(View.INVISIBLE);
                if (quesDetailsEntity.getBiaoJi() != 1) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                    mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                    mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                    mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
                    mViewHold.rbE.setButtonDrawable(R.mipmap.e_new);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
                    mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.testBlack));
                }else {//tag==1
                    int optsTag = opts.get(0).getTag();
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

                    int optsTag1 = opts.get(1).getTag();
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

                    int optsTag2 = opts.get(2).getTag();
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
                    int optsTag3 = opts.get(3).getTag();
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
                    int optsTag4 = opts.get(4).getTag();
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
            }
        }else if (quesType==2){//多选
            if (quesDetailsEntity.getBiaoJi()!=1) {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
            }

            if (quesDetailsEntity.getBiaoJi()!=1) {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
                mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.testBlack));
            }else {

                int optsTag = opts.get(0).getTag();
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

                int optsTag1 = opts.get(1).getTag();
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

                int optsTag2 = opts.get(2).getTag();
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
                int optsTag3 = opts.get(3).getTag();
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

   if (optSize>=5){
       mViewHold.rbF.setVisibility(View.INVISIBLE);
       int optsTag4 = opts.get(4).getTag();
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
                    int optsTag5 = opts.get(5).getTag();
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

            if (quesDetailsEntity.getBiaoJi()!=1) {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.testBlack));
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.testBlack));
            }else {//tag==1
                int optsTag = opts.get(0).getTag();
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

                int optsTag1 = opts.get(1).getTag();
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
        TextView tv;
        LinearLayout ll;
        RadioButton rbA,rbB,rbC,rbD,rbE,rbF;
        RelativeLayout answer_layout;
        TextView anser_tv,parsing_tv,selectorType_tv,parsing_iv,taocanSugest_tv;
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

        }
    }
}
