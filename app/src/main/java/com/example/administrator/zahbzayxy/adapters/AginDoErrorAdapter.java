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
import com.example.administrator.zahbzayxy.beans.SaveUserErrorPrcticeBean;
import com.example.administrator.zahbzayxy.myviews.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${ZWJ} on 2017/12/25 0025.
 */
public class AginDoErrorAdapter extends RecyclerView.Adapter<AginDoErrorAdapter.MyTestViewHold> implements View.OnClickListener {
    Context context;
    LayoutInflater inflater;
    private List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity>list;
    //为了用户查看作对做错的弹出框
    List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList;
    //保存用户错题记录的list
    List<SaveUserErrorPrcticeBean.ErrorQuesBean>saveErrorList1=new ArrayList<>();

    private int optSize;
    private int size;
    private PrictaceErrorBean.DataEntity.ErrorRecordsEntity quesDataBean;
    private List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity.OptsEntity> opts;
    private int isShowParsing;

    public List<SaveUserErrorPrcticeBean.ErrorQuesBean> getSaveErrorList1() {
        return saveErrorList1;
    }
    private int w,h;
    private  int weiZhi;
    private MyTestViewHold mViewHold;
    private int  quesType;
    private StringBuffer stringBuffer;
    StringBuffer erroranswerId;
    private int doRightNum,doWrongNum;
    private MyRecyclerView myRecyclerView;
    private TextView dijige;
    public int getRightNum() {
        return doRightNum;
    }
    public int getRongNum() {
        return doWrongNum;
    }
    public int getWeiZhi() {
        return weiZhi;
    }
    public List<SaveUserErrorPrcticeBean.ErrorQuesBean> getSaveErrorList() {
        return saveErrorList;
    }

    public void setSaveErrorList(List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList) {
        this.saveErrorList = saveErrorList;
    }

    public AginDoErrorAdapter(Context context, List<PrictaceErrorBean.DataEntity.ErrorRecordsEntity> list, List<SaveUserErrorPrcticeBean.ErrorQuesBean> saveErrorList,MyRecyclerView myRecyclerView, TextView dijige) {
        this.context = context;
        this.list = list;
        this.saveErrorList = saveErrorList;
        this.myRecyclerView=myRecyclerView;
        this.dijige = dijige;
        inflater=LayoutInflater.from(context);
        w = context.getResources().getDisplayMetrics().widthPixels;
        h = context.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public MyTestViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_test_qustion_layout, parent, false);
        mViewHold = new MyTestViewHold(view);
        return mViewHold;

    }

    @Override
    public void onBindViewHolder(MyTestViewHold holder, int position) {
        weiZhi=position;
        quesDataBean = list.get(position);
        quesType =quesDataBean.getQuesType();
        size = list.size();
        opts = quesDataBean.getOpts();
        optSize=opts.size();
        if (optSize>0) {
            fuyong(position);
        }
        isShowParsing = quesDataBean.getIsShowParsing();
        if (optSize>1){
            mViewHold.rbA.setVisibility(View.VISIBLE);
            mViewHold.rbB.setVisibility(View.VISIBLE);
        }
        mViewHold.rbC.setVisibility(View.INVISIBLE);
        mViewHold.rbD.setVisibility(View.INVISIBLE);
        mViewHold.rbE.setVisibility(View.INVISIBLE);
        if (quesType==1){//单选
            //每个题目的集合
            holder.tv.setText("[单选]"+list.get(position).getContent());
            holder.tijiao.setVisibility(View.INVISIBLE);
            if (optSize==2){
                holder.rbC.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
            }
            if (optSize==3){
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
            }else if (optSize==4){
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
            }else if (optSize==5){
                holder.rbC.setVisibility(View.VISIBLE);
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());
            }
        }else if (quesType==2){//多选
            erroranswerId=new StringBuffer();
            holder.tv.setText("[多选]"+list.get(position).getContent());
            mViewHold.tijiao.setVisibility(View.VISIBLE);
            holder.rbC.setVisibility(View.VISIBLE);
            holder.rbD.setVisibility(View.VISIBLE);
            if (optSize==4){
                holder.rbD.setVisibility(View.VISIBLE);
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
            }
            if (optSize==3){
                holder.rbD.setVisibility(View.INVISIBLE);
                holder.rbE.setVisibility(View.INVISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                //holder.rbD.setText(opts.get(3).getContent());
            }
            else if (optSize==5){
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.INVISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());

            }else if (optSize==6){
                holder.rbE.setVisibility(View.VISIBLE);
                holder.rbF.setVisibility(View.VISIBLE);
                holder.rbA.setText(opts.get(0).getContent());
                holder.rbB.setText(opts.get(1).getContent());
                holder.rbC.setText(opts.get(2).getContent());
                holder.rbD.setText(opts.get(3).getContent());
                holder.rbE.setText(opts.get(4).getContent());
                holder.rbF.setText(opts.get(5).getContent());
            }
        }else {//判断
            holder.tv.setText("[判断]"+list.get(position).getContent());
            holder.tijiao.setVisibility(View.INVISIBLE);
            holder.rbA.setText(opts.get(0).getContent());
            holder.rbB.setText(opts.get(1).getContent());
            holder.rbC.setVisibility(View.INVISIBLE);
            holder.rbD.setVisibility(View.INVISIBLE);
        }
        mViewHold.rbA.setOnClickListener(this);
        mViewHold.rbB.setOnClickListener(this);
        mViewHold.rbC.setOnClickListener(this);
        mViewHold.rbD.setOnClickListener(this);
        mViewHold.tijiao.setOnClickListener(this);
        mViewHold.rbE.setOnClickListener(this);
        mViewHold.rbF.setOnClickListener(this);




    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        //查看当前做题进度的list
        final SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean = saveErrorList.get(weiZhi);
        //保存用户错题记录的list
        final SaveUserErrorPrcticeBean.ErrorQuesBean errorQuesBean1=new SaveUserErrorPrcticeBean.ErrorQuesBean();
        if (quesType==1) {//单选
            switch (v.getId()) {
                case R.id.radioButtonA:
                    quesDataBean.setBiaoJi(1);
                    opts.get(0).setTag(1);
                    if (opts.get(0).getIsRightAnswer() == 0) {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(0).getId()));
                        Log.e( "onClick:errorId ", String.valueOf(opts.get(0).getId()));
                        errorQuesBean1.setQuestionType(1);
                        errorQuesBean1.setQuestionId(quesDataBean.getQuestionId());
                        Log.e( "aaa:quesId ", String.valueOf(quesDataBean.getQuestionId()));
                        saveErrorList1.add(errorQuesBean1);
                        doWrongNum++;

                    }else {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);

                        quesDataBean.setBiaoJi(1);
                        myRecyclerView.scrollToPosition(weiZhi+1);
                        notifyDataSetChanged();
                        if (weiZhi+1>=size){
                            dijige.setText((weiZhi+1)+"/"+size);
                        }else {

                            dijige.setText((weiZhi+2)+"/"+size);
                        }
                        Log.e("weizhi",weiZhi+"");
                        doRightNum++;
                    }
                    break;
                case R.id.radioButtonB:
                    quesDataBean.setBiaoJi(1);
                    opts.get(1).setTag(1);

                    if (opts.get(1).getIsRightAnswer() == 0) {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(1).getId()));
                        Log.e( "onClick:errorId ", String.valueOf(opts.get(1).getId()));
                        errorQuesBean1.setQuestionType(1);
                        errorQuesBean1.setQuestionId(quesDataBean.getQuestionId());
                        saveErrorList1.add(errorQuesBean1);
                        doWrongNum++;
                    }else {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);
                        doRightNum++;
                        myRecyclerView.scrollToPosition(weiZhi+1);
                        notifyDataSetChanged();
                        if (weiZhi+1>=size){
                            dijige.setText((weiZhi+1)+"/"+size);
                        }else {

                            dijige.setText((weiZhi+2)+"/"+size);
                        }
                        Log.e("weizhi",weiZhi+"");

                    }

                    break;
                case R.id.radioButtonC:
                    quesDataBean.setBiaoJi(1);
                    opts.get(2).setTag(1);
                    if (opts.get(2).getIsRightAnswer() == 0) {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(2).getId()));
                        Log.e( "onClick:errorId ", String.valueOf(opts.get(2).getId()));
                        errorQuesBean1.setQuestionType(1);
                        errorQuesBean1.setQuestionId(quesDataBean.getQuestionId());
                        saveErrorList1.add(errorQuesBean1);
                        doWrongNum++;

                    }else {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);
                        doRightNum++;
                        myRecyclerView.scrollToPosition(weiZhi+1);
                        notifyDataSetChanged();
                        if (weiZhi+1>=size){
                            dijige.setText((weiZhi+1)+"/"+size);
                        }else {

                            dijige.setText((weiZhi+2)+"/"+size);
                        }
                        Log.e("weizhi",weiZhi+"");
                    }
                    break;

                case R.id.radioButtonE:
                    quesDataBean.setBiaoJi(1);
                    opts.get(4).setTag(1);
                    if (opts.get(4).getIsRightAnswer() == 0) {
                        mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(4).getId()));
                        Log.e( "onClick:errorId ", String.valueOf(opts.get(4).getId()));
                        errorQuesBean1.setQuestionType(1);
                        errorQuesBean1.setQuestionId(quesDataBean.getQuestionId());
                        saveErrorList1.add(errorQuesBean1);
                        doWrongNum++;

                    }else {
                        mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);
                        doRightNum++;
                        myRecyclerView.scrollToPosition(weiZhi+1);
                        notifyDataSetChanged();
                        if (weiZhi+1>=size){
                            dijige.setText((weiZhi+1)+"/"+size);
                        }else {

                            dijige.setText((weiZhi+2)+"/"+size);
                        }
                        Log.e("weizhi",weiZhi+"");
                    }
                    break;


                case R.id.radioButtonD:
                        quesDataBean.setBiaoJi(1);
                        opts.get(3).setTag(1);
                        if (opts.get(3).getIsRightAnswer() == 0) {
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            errorQuesBean.setIsRight(1);
                            errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(3).getId()));
                            Log.e("onClick:errorId ", String.valueOf(opts.get(3).getId()));
                            errorQuesBean1.setQuestionType(1);
                            errorQuesBean1.setQuestionId(quesDataBean.getQuestionId());
                            saveErrorList1.add(errorQuesBean1);
                            doWrongNum++;
                        } else {
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                            errorQuesBean.setIsRight(2);
                            doRightNum++;
                            myRecyclerView.scrollToPosition(weiZhi+1);
                            notifyDataSetChanged();
                            if (weiZhi+1>=size){
                                dijige.setText((weiZhi+1)+"/"+size);
                            }else {

                                dijige.setText((weiZhi+2)+"/"+size);
                            }
                            Log.e("weizhi",weiZhi+"");
                        }
                        break;


            }

            //设置用户做题的类型和做题的Id
//点击选项的时候设置答案可见
            mViewHold.answer_layout.setVisibility(View.VISIBLE);
            if (isShowParsing==1) {
                mViewHold.parsing_tv.setVisibility(View.VISIBLE);
                mViewHold.parsing_iv.setVisibility(View.VISIBLE);
                mViewHold.taocanSugest_tv.setVisibility(View.GONE);
                mViewHold.parsing_tv.setText(quesDataBean.getParsing());
                mViewHold.anser_tv.setVisibility(View.GONE);
            }else {
                mViewHold.parsing_tv.setText("");
                mViewHold.parsing_iv.setVisibility(View.GONE);
                mViewHold.taocanSugest_tv.setVisibility(View.VISIBLE);
                mViewHold.taocanSugest_tv.setText("购买独家解析题库可查看独家解析");
            }
            for(int i=0;i<opts.size();i++){
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer==1){
                    switch (i){
                        case 0:
                            mViewHold.anser_tv.setText("答案:"+" "+"A");
                            break;
                        case 1:
                            mViewHold.anser_tv.setText("答案:"+" "+"B");
                            break;
                        case 2:
                            mViewHold.anser_tv.setText("答案:"+" "+"C");
                            break;
                        case 3:
                            mViewHold.anser_tv.setText("答案:"+" "+"D");
                            break;
                        case 4:
                            mViewHold.anser_tv.setText("答案:"+" "+"E");
                            break;
                            default:
                                mViewHold.anser_tv.setText("");
                            break;
                    }
                }
            }

            quesDataBean.setBiaoJi(1);
            mViewHold.rbA.setEnabled(false);
            mViewHold.rbB.setEnabled(false);
            mViewHold.rbC.setEnabled(false);
            mViewHold.rbD.setEnabled(false);
            mViewHold.rbE.setEnabled(false);
        }else if(quesType==2){//多选
            //  int personRight=0;

            mViewHold.tijiao.setVisibility(View.VISIBLE);
            int rightNum = 0;

            switch (v.getId()){
                case R.id.radioButtonA:
                    opts.get(0).setTag(1);
                    if (opts.get(0).getIsRightAnswer() == 0) {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        // errorQuesBean.setIsRight(1);
                        erroranswerId.append(opts.get(0).getId()+",");
                        Log.e(" erroranswerId",erroranswerId+"");
                    }else {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));

                        rightNum++;
                    }
                    break;
                case R.id.radioButtonB:
                    opts.get(1).setTag(1);
                    if (opts.get(1).getIsRightAnswer() == 0) {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        erroranswerId.append(opts.get(1).getId()+",");
                        Log.e(" erroranswerId",erroranswerId+"");

                    }else {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        rightNum++;


                    }
                    break;
                case R.id.radioButtonC:
                    opts.get(2).setTag(1);
                    if (opts.get(2).getIsRightAnswer() == 0) {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        erroranswerId.append(opts.get(2).getId()+",");
                        Log.e(" erroranswerId",erroranswerId+"");

                    }else {
                        mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbC.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        rightNum++;

                    }
                    break;
                case R.id.radioButtonD:
                    opts.get(3).setTag(1);
                    if (opts.get(3).getIsRightAnswer() == 0) {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        erroranswerId.append(opts.get(3).getId()+",");
                        Log.e(" erroranswerId",erroranswerId+"");

                    }else {
                        mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        rightNum++;


                    }
                    break;
                case R.id.radioButtonE:
                    opts.get(4).setTag(1);
                    if (opts.get(4).getIsRightAnswer() == 0) {
                        mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        erroranswerId.append(opts.get(4).getId()+",");
                        Log.e(" erroranswerId",erroranswerId+"");

                    }else {
                        mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        rightNum++;

                    }
                    break;
                case R.id.radioButtonF:
                    opts.get(5).setTag(1);
                    if (opts.get(5).getIsRightAnswer() == 0) {
                        mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        erroranswerId.append(opts.get(5).getId()+",");
                        Log.e(" erroranswerId",erroranswerId+"");

                    }else {
                        mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbF.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        rightNum++;

                    }
                    break;
                default:
                    break;
            }
            mViewHold.tijiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuffer userAnswerIds=new StringBuffer();
                    StringBuffer optionsAnswerIds=new StringBuffer();
                    int personRight=0;
                    if (mViewHold.rbA.isChecked()){
                        userAnswerIds.append(opts.get(0).getId());

                        if(opts.get(0).getIsRightAnswer()==1){
                            personRight++;
                        }
                    }if (mViewHold.rbB.isChecked()){
                        userAnswerIds.append(opts.get(1).getId());

                        if(opts.get(1).getIsRightAnswer()==1){
                            personRight++;
                        }
                    }if (mViewHold.rbC.isChecked()){
                        userAnswerIds.append(opts.get(2).getId());
                        if(opts.get(2).getIsRightAnswer()==1){
                            personRight++;
                        }


                    }if (mViewHold.rbD.isChecked()){
                        userAnswerIds.append(opts.get(3).getId());
                        if(opts.get(3).getIsRightAnswer()==1){
                            personRight++;
                        }
                    }if (mViewHold.rbE.isChecked()){
                        userAnswerIds.append(opts.get(4).getId());
                        if(opts.get(4).getIsRightAnswer()==1){
                            personRight++;
                        }
                    }if (mViewHold.rbF.isChecked()){
                        userAnswerIds.append(opts.get(5).getId());
                        if(opts.get(5).getIsRightAnswer()==1){
                            personRight++;
                        }
                    }
                    //判断提交是否做正确
                    int right=0;
                    for (int i=0;i<opts.size();i++){
                        if (opts.get(i).getIsRightAnswer()==1){
                            optionsAnswerIds.append(opts.get(i).getId());
                            right++;
                        }
                    }
                    Log.e("right++",right+"");
                    Log.e("personRight",personRight+"");

                    //当选择个数和对的个数不相等的时候再加进错误集合
                    boolean b = compare2(String.valueOf(userAnswerIds), String.valueOf(optionsAnswerIds));
                    if (b==false){
                        //if (personRight!=right){
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(erroranswerId.toString());
                        String s = erroranswerId.toString();
                        Log.e("errraaaaaaaaa",s);
                        errorQuesBean1.setQuestionType(2);
                        errorQuesBean1.setQuestionId(quesDataBean.getQuestionId());
                        saveErrorList1.add(errorQuesBean1);
                        doWrongNum++;

                    }if (b==true){
                        //   if (personRight==right){
                        errorQuesBean.setIsRight(2);
                        doRightNum++;
                    }


                    String s = erroranswerId.toString();
                    Log.e("erroranserId",s);
                    quesDataBean.setBiaoJi(1);
                    mViewHold.rbA.setEnabled(false);
                    mViewHold.rbB.setEnabled(false);
                    mViewHold.rbC.setEnabled(false);
                    mViewHold.rbD.setEnabled(false);
                    mViewHold.rbE.setEnabled(false);

                    mViewHold.answer_layout.setVisibility(View.VISIBLE);
                    if (isShowParsing==1) {
                        mViewHold.parsing_tv.setVisibility(View.VISIBLE);
                        mViewHold.parsing_iv.setVisibility(View.VISIBLE);
                        mViewHold.taocanSugest_tv.setVisibility(View.GONE);
                        mViewHold.parsing_tv.setText(quesDataBean.getParsing());
                        mViewHold.anser_tv.setVisibility(View.GONE);
                    }else {
                        mViewHold.parsing_tv.setText("");
                        mViewHold.parsing_iv.setVisibility(View.GONE);
                        mViewHold.taocanSugest_tv.setVisibility(View.VISIBLE);
                        mViewHold.taocanSugest_tv.setText("购买独家解析题库可查看独家解析");
                    }
                    myRecyclerView.scrollToPosition(weiZhi+1);
                    notifyDataSetChanged();
                    if (weiZhi+1>=size){
                        dijige.setText((weiZhi+1)+"/"+size);
                    }else {

                        dijige.setText((weiZhi+2)+"/"+size);
                    }
                }
            });
            stringBuffer = new StringBuffer();
            for(int i=0;i<opts.size();i++){
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer==1){
                    switch (i){
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
                    mViewHold.anser_tv.setText("答案:"+stringBuffer.toString());
                }
            }

        }else {//判断
            switch (v.getId()){
                case R.id.radioButtonA:
                    opts.get(0).setTag(1);
                    if (opts.get(0).getIsRightAnswer()==0) {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(0).getId()));
                        errorQuesBean1.setQuestionId(quesDataBean.getQuestionId());
                        errorQuesBean1.setQuestionType(2);
                        saveErrorList1.add(errorQuesBean1);
                        doWrongNum++;

                    }else {
                        mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);
                        doRightNum++;

                        myRecyclerView.scrollToPosition(weiZhi+1);
                        notifyDataSetChanged();
                        if (weiZhi+1>=size){
                            dijige.setText((weiZhi+1)+"/"+size);
                        }else {

                            dijige.setText((weiZhi+2)+"/"+size);
                        }
                    }

                    break;
                case R.id.radioButtonB:
                    opts.get(1).setTag(1);
                    if (opts.get(1).getIsRightAnswer()==0) {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                        errorQuesBean.setIsRight(1);
                        errorQuesBean1.setErrorAnswerIds(String.valueOf(opts.get(1).getId()));
                        errorQuesBean1.setQuestionId(quesDataBean.getQuestionId());
                        errorQuesBean1.setQuestionType(2);
                        saveErrorList1.add(errorQuesBean1);
                        doWrongNum++;
                    }else {
                        mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                        mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                        errorQuesBean.setIsRight(2);
                        doRightNum++;
                        myRecyclerView.scrollToPosition(weiZhi+1);
                        notifyDataSetChanged();
                        if (weiZhi+1>=size){
                            dijige.setText((weiZhi+1)+"/"+size);
                        }else {

                            dijige.setText((weiZhi+2)+"/"+size);
                        }

                    }
                    break;
            }

            mViewHold.answer_layout.setVisibility(View.VISIBLE);
            if (isShowParsing==1) {
                mViewHold.parsing_tv.setVisibility(View.VISIBLE);
                mViewHold.parsing_iv.setVisibility(View.VISIBLE);
                mViewHold.taocanSugest_tv.setVisibility(View.GONE);
                mViewHold.parsing_tv.setText(quesDataBean.getParsing());
                mViewHold.anser_tv.setVisibility(View.GONE);
            }else {
                mViewHold.parsing_tv.setText("");
                mViewHold.parsing_iv.setVisibility(View.GONE);
                mViewHold.taocanSugest_tv.setVisibility(View.VISIBLE);
                mViewHold.taocanSugest_tv.setText("购买独家解析题库可查看独家解析");
            }
            for(int i=0;i<opts.size();i++){
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer==1){
                    switch (i){
                        case 0:
                            mViewHold.anser_tv.setText("答案:"+" "+"A");
                            break;
                        case 1:
                            mViewHold.anser_tv.setText("答案:"+" "+"B");
                            break;
                    }
                }
            }

            quesDataBean.setBiaoJi(1);
            mViewHold.rbA.setEnabled(false);
            mViewHold.rbB.setEnabled(false);
        }
        Log.e("saveErrorSize",saveErrorList1.size()+"");
        Log.e("saveErrorSize",saveErrorList1.toString());


    }
    public static boolean compare2(String a,String b){
        byte[] b1 = a.getBytes();
        byte[] b2 = b.getBytes();
        int[] bCount = new int[256];
        for(int i=0;i<256;i++){
            bCount[i] = 0;
        }
        for(int i=0;i<b1.length;i++)
            bCount[b1[i]-'0']++;
        for(int i=0;i<b2.length;i++)
            bCount[b2[i]-'0']--;
        for(int i=0;i<256;i++){
            if(bCount[i]!=0)
                return false;
        }
        return true;
    }
    private void fuyong(int position) {
        mViewHold.answer_layout.setVisibility(View.INVISIBLE);
        if (quesType==1){//单选
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
                if (optSize>3){
                    mViewHold.rbD.setVisibility(View.VISIBLE);
                    mViewHold.rbD.setEnabled(false);
                }
                if (optSize>4){
                    mViewHold.rbE.setVisibility(View.VISIBLE);
                    mViewHold.rbE.setEnabled(false);
                }

            } else {
                mViewHold.rbA.setEnabled(true);
                mViewHold.rbB.setEnabled(true);
                mViewHold.rbC.setEnabled(true);
                if (optSize>3){
                    mViewHold.rbD.setEnabled(true);
                }
                if (optSize>4){
                    mViewHold.rbE.setEnabled(true);
                }
            }

            if (opts.get(0).getTag() == 1) {
                if (opts.get(0).getIsRightAnswer() == 0) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                    if (opts.get(2).getIsRightAnswer() == 1) {
                        mViewHold.anser_tv.setText("答案:C");
                    }else if (opts.get(1).getIsRightAnswer() == 1){
                        mViewHold.anser_tv.setText("答案:B");
                    }
                    else if (opts.get(3).getIsRightAnswer() == 1){
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
                    } else if (opts.get(0).getIsRightAnswer() == 1){
                        mViewHold.anser_tv.setText("答案:A");
                    }else if (opts.get(3).getIsRightAnswer() == 1){
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
            if (optSize>=3) {
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

                if (optSize>3) {
                    mViewHold.rbD.setVisibility(View.VISIBLE);
                    mViewHold.rbE.setVisibility(View.INVISIBLE);
                    if (opts.get(3).getTag() == 1) {
                        if (opts.get(3).getIsRightAnswer() == 0) {
                            mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbD.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            if (opts.get(0).getIsRightAnswer() == 1) {
                                mViewHold.anser_tv.setText("答案:A");
                            } else if (opts.get(1).getIsRightAnswer() == 1) {
                                mViewHold.anser_tv.setText("答案:B");
                            } else if (opts.get(2).getIsRightAnswer() == 1){
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
                if (optSize>4) {
                    mViewHold.rbE.setVisibility(View.VISIBLE);
                    if (opts.get(4).getTag() == 1) {
                        if (opts.get(4).getIsRightAnswer() == 0) {
                            mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
                            mViewHold.rbE.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                            if (opts.get(0).getIsRightAnswer() == 1) {
                                mViewHold.anser_tv.setText("答案:A");
                            } else if (opts.get(1).getIsRightAnswer() == 1) {
                                mViewHold.anser_tv.setText("答案:B");
                            } else if (opts.get(2).getIsRightAnswer() == 1){
                                mViewHold.anser_tv.setText("答案:C");
                            } else if (opts.get(3).getIsRightAnswer() == 1){
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
            }

        }else if (quesType==2){//多选
            mViewHold.rbA.setChecked(false);
            mViewHold.rbB.setChecked(false);
            mViewHold.rbC.setChecked(false);
            mViewHold.rbD.setChecked(false);
            mViewHold.rbE.setChecked(false);
            mViewHold.rbF.setChecked(false);
            mViewHold.rbE.setVisibility(View.GONE);
            mViewHold.rbF.setVisibility(View.GONE);
            if (quesDataBean.getBiaoJi()==1){
                mViewHold.answer_layout.setVisibility(View.VISIBLE);

                mViewHold.rbA.setEnabled(false);
                mViewHold.rbB.setEnabled(false);
                mViewHold.rbC.setEnabled(false);
                mViewHold.rbD.setEnabled(false);
                mViewHold.rbE.setEnabled(false);
                mViewHold.rbF.setEnabled(false);
            }else {
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

            if (opts.get(0).getTag()==1){
                //  mViewHold.rbA.setChecked(true);
                if (opts.get(0).getIsRightAnswer()==0){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);

                }else if (opts.get(0).getIsRightAnswer()==1) {
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                }
            }else {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
            }
            if(opts.get(1).getTag()==1){
                if (opts.get(1).getIsRightAnswer()==0){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                }else if(opts.get(1).getIsRightAnswer()==1){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                }
            }else {
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
            }

            if (opts.get(2).getTag()==1){
                // mViewHold.rbC.setChecked(true);
                if (opts.get(2).getIsRightAnswer()==0){
                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_error);
                }else if (opts.get(2).getIsRightAnswer()==1){
                    mViewHold.rbC.setButtonDrawable(R.mipmap.test_right);
                }
            }else {
                mViewHold.rbC.setButtonDrawable(R.mipmap.c_new);
            }
if (optSize>3) {
    if (opts.get(3).getTag() == 1) {
        if (opts.get(3).getIsRightAnswer() == 0) {
            mViewHold.rbD.setButtonDrawable(R.mipmap.test_error);
        } else if (opts.get(3).getIsRightAnswer() == 1) {
            mViewHold.rbD.setButtonDrawable(R.mipmap.test_right);
        }
    } else {
        mViewHold.rbD.setButtonDrawable(R.mipmap.d_new);
    }
}
            if (optSize==5){
                fuyong5();
                mViewHold.rbE.setVisibility(View.VISIBLE);
            }
            if (optSize==6){
                fuyong5();
                fuyong6();
                mViewHold.rbE.setVisibility(View.VISIBLE);
                mViewHold.rbF.setVisibility(View.VISIBLE);
            }

            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0;i<opts.size();i++){
                int isRightAnswer = opts.get(i).getIsRightAnswer();
                if (isRightAnswer==1){
                    switch (i){
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
                    mViewHold.anser_tv.setText("答案:"+stringBuffer.toString());
                }
            }
        }else {//判断
            mViewHold.rbA.setChecked(false);
            mViewHold.rbB.setChecked(false);
            mViewHold.rbC.setVisibility(View.GONE);
            mViewHold.rbD.setVisibility(View.GONE);
            mViewHold.rbE.setVisibility(View.GONE);
            mViewHold.rbF.setVisibility(View.GONE);
            if (quesDataBean.getBiaoJi()==1){

                mViewHold.answer_layout.setVisibility(View.VISIBLE);
                mViewHold.rbA.setEnabled(false);
                mViewHold.rbB.setEnabled(false);

            }else {
                mViewHold.rbA.setEnabled(true);
                mViewHold.rbB.setEnabled(true);

            }
            if (opts.get(0).getTag()==1){
                if (opts.get(0).getIsRightAnswer()==0){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                    mViewHold.anser_tv.setText("答案:B");

                }else if (opts.get(0).getIsRightAnswer()==1){
                    mViewHold.rbA.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    mViewHold.anser_tv.setText("答案:A");

                }
            }else {
                mViewHold.rbA.setButtonDrawable(R.mipmap.a_new);
                mViewHold.rbA.setTextColor(context.getResources().getColor(R.color.test_black_tv));
            }


            if(opts.get(1).getTag()==1) {
                if (opts.get(1).getIsRightAnswer()==0){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_error);
                    mViewHold.anser_tv.setText("答案:A");
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.yellowWrongTv));
                }else if (opts.get(1).getIsRightAnswer()==1){
                    mViewHold.rbB.setButtonDrawable(R.mipmap.test_right);
                    mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.greenRightTv));
                    mViewHold.anser_tv.setText("答案:B");

                }
            }else {
                mViewHold.rbB.setButtonDrawable(R.mipmap.b_new);
                mViewHold.rbB.setTextColor(context.getResources().getColor(R.color.test_black_tv));
            }
        }


    }
    private void fuyong6() {
        fuyong5();
        if (opts.get(5).getTag()==1){
            if (opts.get(5).getIsRightAnswer()==0){
                mViewHold.rbF.setButtonDrawable(R.mipmap.test_error);
            }else if (opts.get(5).getIsRightAnswer()==1){
                mViewHold.rbF.setButtonDrawable(R.mipmap.test_right);
            }
        }else {
            mViewHold.rbF.setButtonDrawable(R.mipmap.f_new);
        }
    }

    private void fuyong5() {
        if (opts.get(4).getTag()==1){
            if (opts.get(4).getIsRightAnswer()==0){
                mViewHold.rbE.setButtonDrawable(R.mipmap.test_error);
            }else if (opts.get(4).getIsRightAnswer()==1){
                mViewHold.rbE.setButtonDrawable(R.mipmap.test_right);
            }
        }else {
            mViewHold.rbE.setButtonDrawable(R.mipmap.e_new);
        }
    }
    static class MyTestViewHold extends RecyclerView.ViewHolder{
        TextView tv;
        LinearLayout ll;
        RadioButton rbA,rbB,rbC,rbD,rbE,rbF;
        TextView tijiao;
        RelativeLayout answer_layout;
        TextView anser_tv,parsing_tv, parsing_iv,taocanSugest_tv;
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
            tijiao= (TextView) itemView.findViewById(R.id.tijiao);
            answer_layout= (RelativeLayout) itemView.findViewById(R.id.answer_layout);
            anser_tv= (TextView) itemView.findViewById(R.id.anwer_choice);
            parsing_tv= (TextView) itemView.findViewById(R.id.detailAnswer_tv);
            parsing_iv= (TextView) itemView.findViewById(R.id.xiangjie_iv);
            taocanSugest_tv= (TextView) itemView.findViewById(R.id.taocan_suggest_tv);
        }
    }

}
