package com.example.administrator.zahbzayxy.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ChooseTopicActivity;
import com.example.administrator.zahbzayxy.activities.NewMyChengJiActivity;
import com.example.administrator.zahbzayxy.activities.PLookCuoTiActivity;
import com.example.administrator.zahbzayxy.activities.QueslibActivity;
import com.example.administrator.zahbzayxy.activities.SearchTestActivity;
import com.example.administrator.zahbzayxy.activities.SelectClassifyActivity;
import com.example.administrator.zahbzayxy.activities.TestContentActivity1;
import com.example.administrator.zahbzayxy.activities.TestPracticeAcivity;
import com.example.administrator.zahbzayxy.adapters.SimulationAdapter;
import com.example.administrator.zahbzayxy.adapters.TestNavigationAdapter;
import com.example.administrator.zahbzayxy.beans.OnTransitionTextListener;
import com.example.administrator.zahbzayxy.beans.SimulationBean;
import com.example.administrator.zahbzayxy.beans.SimulationInfoBean;
import com.example.administrator.zahbzayxy.beans.TestNavigationBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.manager.OnLineManager;
import com.example.administrator.zahbzayxy.utils.BarChartManager;
import com.example.administrator.zahbzayxy.utils.ColorBar;
import com.example.administrator.zahbzayxy.utils.DisplayUtil;
import com.example.administrator.zahbzayxy.utils.FixedIndicatorView;
import com.example.administrator.zahbzayxy.utils.Indicator;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.TextAndPictureUtil;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-23.
 * Time 10:05.
 * 模拟考试
 */
public class SimulationFragment extends Fragment implements View.OnClickListener {
    private View view;
    private FixedIndicatorView fixedIndicatorView;
    private ProgressBarLayout mLoadingBar;
    private Context mContext;
    private BarChart mChart;
    private LinearLayout ll_practice,ll_erropic,ll_exam,ll_search,ll_text,ll_img;
    private String token;
    private TextView tv_choose, mExamTitle, mPassScoreTv, mPassCountTv,tv_questionName,
            tv_more,tv_des,tv_msg;
    private ImageView img_add, mScoreSimpleImg;
    private SimulationAdapter adapter;
    private List<SimulationBean.SimulationList>navigationList=new ArrayList<>();
    private RelativeLayout rl_add,rl_empty_layout;
    private int quesLibId;
    private int userLibId;
    private int packageId;
    private String quesLibName;
    private int mLoadDataPosition;
    int createId;//导航id
    private Integer c_userLibId;//传值的话就用些值查询，不传的话取最新的一条
    private LinearLayout mOperateLayout;
    // 剩余考试次数
    private int mCanUseNum;
    // 是否在有效期内  yes 是  no 否
    private String mIsOnTime;
    private ScrollView scroll;
    private String msg_cont;
    private  final static int CHOOSE_TOPIC=1001;
    private boolean isVisible;
    private boolean mLoadView = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_simulation,container,false);
        fixedIndicatorView =view.findViewById(R.id.singleTab_fixedIndicatorView);
        mLoadingBar= view.findViewById(R.id.load_bar_layout_evaluating);
        mExamTitle = view.findViewById(R.id.simulation_exam_title_tv);
        mPassScoreTv = view.findViewById(R.id.text_score);
        mPassCountTv = view.findViewById(R.id.text_account);
        mScoreSimpleImg = view.findViewById(R.id.simulation_score_simple_im);
        tv_questionName=view.findViewById(R.id.tv_questionName);//题库类型
        mOperateLayout = view.findViewById(R.id.frg_simulation_operate_layout);
        scroll=view.findViewById(R.id.scroll);
        rl_add=view.findViewById(R.id.rl_add);
        ll_img=view.findViewById(R.id.ll_img);//对错图标
        ll_text=view.findViewById(R.id.ll_text);
        tv_des=view.findViewById(R.id.tv_des);
        rl_empty_layout=view.findViewById(R.id.rl_empty_layout);
        tv_msg=view.findViewById(R.id.tv_msg);
        adapter = new SimulationAdapter(mContext,navigationList,fixedIndicatorView);
        adapter.setOnItemClickListener((View clickItemView, int position) -> {
            c_userLibId=null;
            loadData(position);
        });
        initView();
        mLoadView = true;
        showBarChartMore(null, 0);
        mOperateLayout.setVisibility(View.GONE);
        initNavigationData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i("======refresh=====", "simulation fragment isVisibleToUser = " + isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser && mLoadView) {
            loadData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    public void loadData(){
        if (!isVisible) return;
        showBarChartMore(null, 0);
        initNavigationData();
    }

    private void set() {
        adapter.setData(navigationList);
        fixedIndicatorView.setAdapter(adapter);
        fixedIndicatorView.setScrollBar(new ColorBar(mContext, mContext.getResources().getColor(R.color.transparent), 6));//设置选中下划线

        float unSelectSize = 14;
        float selectSize = unSelectSize * 1.1f;
        int selectColor = mContext.getResources().getColor(R.color.lightBlue);
        int unSelectColor =mContext.getResources().getColor(R.color.black);
        fixedIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        fixedIndicatorView.setCurrentItem(0,true);
    }

    private void initNavigationData() {
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = tokenDb.getString("token","");
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getSimulationDate(token).enqueue(new Callback<SimulationBean>() {
            @Override
            public void onResponse(Call<SimulationBean> call, Response<SimulationBean> response) {
                hideLoadingBar();
                if(response !=null && response.body() !=null){
                    String code = response.body().getCode();
                    if(code.equals("00000")){
                        navigationList=response.body().getData().getData();
                        set();
                        loadData(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<SimulationBean> call, Throwable t) {
                ToastUtils.showInfo(t.getMessage(),5000);
                Log.i("huwei","huwei======"+t.getMessage());
            }
        });
    }

    private void loadData(int position){
        mLoadDataPosition = position;
        if (navigationList == null || navigationList.size() == 0 || position >= navigationList.size()) {
//            mOperateLayout.setVisibility(View.GONE);
//            rl_add.setVisibility(View.GONE);
            isVisbale(false);
            return;
        }
        createId = navigationList.get(position).getId();
        SharedPreferences tokenDb = mContext.getSharedPreferences("tokenDb", mContext.MODE_PRIVATE);
        token = tokenDb.getString("token","");
        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getSimulationData(createId,c_userLibId,token).enqueue(new Callback<SimulationInfoBean>() {
            @Override
            public void onResponse(Call<SimulationInfoBean> call, Response<SimulationInfoBean> response) {
                hideLoadingBar();
                mOperateLayout.setVisibility(View.VISIBLE);
                rl_add.setVisibility(View.VISIBLE);
                if(response !=null && response.body() !=null){
                    String code = response.body().getCode();
                    if("00000".equals(code)){
                        isVisbale(true);
                        SimulationInfoBean.SimulationDataBean dataBean = response.body().getData();
                        if (dataBean != null) {
                            SimulationInfoBean.QuesLib  quesLib = dataBean.getQuesLib();
                            // 是否拥有查看图表的权限 1、有 0、没有
                            int scoreLine = quesLib.getViewScoreLine();
                            String quesLibName = quesLib.getQuesLibName();
                            mCanUseNum = quesLib.getCanUseNum();
                            mIsOnTime = quesLib.getIsOnTime();
                            msg_cont=quesLib.getMsg_cont();
                            // 名称
                            mExamTitle.setText(quesLibName + "");
                            mExamTitle.setText(TextAndPictureUtil.getTextCssStyle(mContext," "+quesLib.getPackageName()+" ",quesLibName));
                            tv_questionName.setText(quesLib.getPackageName());//题库类型
                            tv_questionName.setVisibility(View.GONE);
                            // 题库设置的及格分数
                            mPassScoreTv.setText(quesLib.getPassScore() + "");
                            // 及格次数
                            mPassCountTv.setText(dataBean.getPassNum() + "");
                            //题库套餐id
                            packageId = quesLib.getQuesLibPackageId();
                            //题库id
                            quesLibId = quesLib.getQuesLibId();
                            //用户题库id
                            userLibId = quesLib.getId();
                            if (scoreLine == 0) {
                                mScoreSimpleImg.setVisibility(View.VISIBLE);
                                mChart.setVisibility(View.GONE);
                                ll_text.setVisibility(View.GONE);
                            } else {
                                mScoreSimpleImg.setVisibility(View.GONE);
                                mChart.setVisibility(View.VISIBLE);
                                ll_text.setVisibility(View.VISIBLE);
                                // 分数的集合
                                List<SimulationInfoBean.StatScore> scoreList = dataBean.getStatScore();
                                if (scoreList == null) scoreList = new ArrayList<>();
                                showBarChartMore(scoreList, quesLib.getPassScore());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SimulationInfoBean> call, Throwable t) {
                ToastUtils.showInfo(t.getMessage(),5000);
                Log.i("huwei","huwei======"+t.getMessage());
            }
        });
    }

    private void initView() {
        mChart=view.findViewById(R.id.Bar_chat1);
        ll_practice = view.findViewById(R.id.ll_practice);
        ll_practice.setOnClickListener(this);
        ll_erropic= view.findViewById(R.id.ll_erropic);
        ll_erropic.setOnClickListener(this);
        ll_exam= view.findViewById(R.id.ll_exam);
        ll_exam.setOnClickListener(this);
        ll_search= view.findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        tv_choose=view.findViewById(R.id.tv_choose);//选择题库
        tv_choose.setOnClickListener(this);
        img_add=view.findViewById(R.id.img_add);//添加题库
        img_add.setOnClickListener(this);
        mChart.getDescription().setEnabled(false);//设置图标描述
        mChart.setPinchZoom(false);//缩放变焦
        mChart.setDrawBarShadow(false);
        mChart.setDrawGridBackground(false);
        mChart.setExtraBottomOffset(15f);//整体剧底边15f
        tv_more=view.findViewById(R.id.tv_more);//模考-更多
        tv_more.setOnClickListener(this);
    }

    /**
     *  设置图表数据
     * @param scoreList 分数的集合
     * @param passScore  题库设置的及格分数
     */
    private void showBarChartMore(List<SimulationInfoBean.StatScore> scoreList, int passScore) {
        if (scoreList == null) scoreList = new ArrayList<>();
        BarChartManager barChartManager = new BarChartManager(mChart);
        List<Float> xAxisValues = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();
        List<Float> x1 = new ArrayList<>();//及格
        // x轴坐标，是固定值。最少30个，如果题库返回的分数超过30个，则按照实际集合设置x轴坐标
        int positionCount = 31;
        if ((scoreList.size() + 1) > positionCount) {
            positionCount = scoreList.size() + 1;
        }
        for (int i = 1; i < positionCount; i++) {
            xAxisValues.add((float) i);
        }

        if (scoreList.size() == 0) {
            // 没有数据时填充一个空，否则，有数据的图标和无数据的图标显示的高度不一致
            x1.add(0f);
        } else {
            for (SimulationInfoBean.StatScore statScore : scoreList) {
                x1.add((float) statScore.getTotalScore());
            }
        }

        labels.add("");
        colours.add(Color.parseColor("#1631E1"));
        barChartManager.showMoreBarChart(xAxisValues, x1, labels, passScore);
        barChartManager.setYAxis(100, 0, 5);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.ll_practice://顺序练习
                if (mCanUseNum < 1) {
                    ToastUtils.showLongInfo("考试次数已用完");
                    return;
                }
                if (!"yes".equals(mIsOnTime)) {
                    ToastUtils.showLongInfo("当前题库已过期");
                    return;
                }
                if(!"other".equals(mIsOnTime)){
                    ToastUtils.showLongInfo(msg_cont);
                }
                intent = new Intent(getActivity(), TestPracticeAcivity.class);
                Bundle bundlePractice = new Bundle();
                bundlePractice.putInt("quesLibId", quesLibId);
                bundlePractice.putString("paperName", quesLibName);
                bundlePractice.putInt("packageId", packageId);
                bundlePractice.putInt("userLibId",userLibId);
                Log.e("aaaaaaaaaquslibslid", quesLibId + "");
                intent.putExtras(bundlePractice);
                startActivity(intent);
                break;
            case R.id.ll_erropic://我的错题
                if (mCanUseNum < 1) {
                    ToastUtils.showLongInfo("考试次数已用完");
                    return;
                }
                if (!"yes".equals(mIsOnTime)) {
                    ToastUtils.showLongInfo("当前题库已过期");
                    return;
                }
                if(!"other".equals(mIsOnTime)){
                    ToastUtils.showLongInfo(msg_cont);
                }
                if (quesLibId <= 0 || userLibId <= 0 || packageId <= 0) {
                    ToastUtils.showLongInfo("暂无题库");
                    return;
                }
                intent = new Intent(getActivity(), PLookCuoTiActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("quesLibId", quesLibId);
                bundle1.putInt("userLibId", userLibId);
                bundle1.putInt("packageId", packageId);
                Log.e("qusLibsId", String.valueOf(quesLibId) + ",1111," + userLibId + "," + packageId);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.ll_exam://模拟考试
                if (mCanUseNum < 1) {
                    ToastUtils.showLongInfo("考试次数已用完");
                    return;
                }
                if (!"yes".equals(mIsOnTime)) {
                    ToastUtils.showLongInfo("当前题库已过期");
                    return;
                }
                if("other".equals(mIsOnTime)){
                    ToastUtils.showLongInfo(msg_cont);
                }
                if (quesLibId <= 0 || userLibId <= 0) {
                    ToastUtils.showLongInfo("暂无题库");
                    return;
                }
                intent = new Intent(getActivity(), TestContentActivity1.class);
                Bundle bundle = new Bundle();
                bundle.putInt("quesLibId", quesLibId);
                bundle.putInt("userLibId", userLibId);
                bundle.putInt("examType", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_search://快速搜题
                if (mCanUseNum < 1) {
                    ToastUtils.showLongInfo("考试次数已用完");
                    return;
                }
                if (!"yes".equals(mIsOnTime)) {
                    ToastUtils.showLongInfo("当前题库已过期");
                    return;
                }
                if(!"other".equals(mIsOnTime)){
                    ToastUtils.showLongInfo(msg_cont);
                }
                intent = new Intent(getActivity(), SearchTestActivity.class);
                Bundle searchBundle = new Bundle();
                searchBundle.putInt("quesLibId", quesLibId);
                intent.putExtras(searchBundle);
                startActivity(intent);
                break;
            case R.id.tv_choose://选择题库
                intent = new Intent(getActivity(),ChooseTopicActivity.class);
                intent.putExtra("id",String.valueOf(createId));
                startActivityForResult(intent,CHOOSE_TOPIC);
                break;
            case R.id.img_add:
                startActivity(new Intent(getActivity(), QueslibActivity.class));
//                  startActivity(new Intent(getActivity(), NavQueslibFragment.class));
                break;
            case R.id.tv_more://模考题库-更多
                startActivity(new Intent(getActivity(), NewMyChengJiActivity.class));
                break;
        }
    }
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        initNavigationData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case CHOOSE_TOPIC :
                if (resultCode == Activity.RESULT_OK) {
                    c_userLibId = data.getIntExtra("userLibId",0);
                    loadData(0);
                }
                break;
            default:break;
        }
    }
    private void isVisbale(boolean flag){
        if(flag){
            scroll.setVisibility(View.VISIBLE);
            rl_empty_layout.setVisibility(View.GONE);
        }else{
            rl_empty_layout.setVisibility(View.VISIBLE);
            scroll.setVisibility(View.GONE);
            tv_msg.setText("暂无数据");
        }
    }
}
