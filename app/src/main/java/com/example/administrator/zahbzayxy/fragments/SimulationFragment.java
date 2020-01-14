package com.example.administrator.zahbzayxy.fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ChooseTopicActivity;
import com.example.administrator.zahbzayxy.activities.PLookCuoTiActivity;
import com.example.administrator.zahbzayxy.activities.QueslibActivity;
import com.example.administrator.zahbzayxy.activities.SearchTestActivity;
import com.example.administrator.zahbzayxy.activities.TestContentActivity1;
import com.example.administrator.zahbzayxy.activities.TestPracticeAcivity;
import com.example.administrator.zahbzayxy.adapters.SimulationAdapter;
import com.example.administrator.zahbzayxy.adapters.TestNavigationAdapter;
import com.example.administrator.zahbzayxy.beans.OnTransitionTextListener;
import com.example.administrator.zahbzayxy.beans.SimulationBean;
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
    private LinearLayout ll_practice,ll_erropic,ll_exam,ll_search;
    private String token;
    private TextView tv_choose;
    private ImageView img_add;
    private SimulationAdapter adapter;
    private List<SimulationBean.SimulationList>navigationList=new ArrayList<>();
    private int quesLibId;
    private int userLibId;
    private int packageId;
    private String quesLibName;
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
        initView();
        showBarChartMore();
        initNavigationData();
        return view;
    }
    private void set() {
        adapter = new SimulationAdapter(mContext,navigationList,fixedIndicatorView);
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
                      set();
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
    }
    private void showBarChartMore() {
        BarChartManager barChartManager = new BarChartManager(mChart);
        List<Float> xAxisValues = new ArrayList<>();
        List<List<Float>> yAxisValues = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<Integer> colours = new ArrayList<>();
        List<Float> x1 = new ArrayList<>();//及格
        List<Float> x2 = new ArrayList<>();//未及格
        xAxisValues.add(1.0f);
        xAxisValues.add(2.0f);
        xAxisValues.add(3.0f);
        xAxisValues.add(4.0f);
        xAxisValues.add(5.0f);
        xAxisValues.add(5.0f);

        x1.add(10f);
        x1.add(25f);
        x1.add(30f);
        x1.add(43f);
        x1.add(50f);
        x1.add(50f);

        x2.add(50f);
        x2.add(40f);
        x2.add(33f);
        x2.add(20f);
        x2.add(10f);
        x2.add(10f);
        yAxisValues.add(x1);
        yAxisValues.add(x2);
        labels.add("");
        labels.add("");
        colours.add(Color.parseColor("#1631E1"));
        colours.add(Color.parseColor("#C42417"));
        barChartManager.showMoreBarChart(xAxisValues, yAxisValues, labels, colours);
        barChartManager.setXAxis(6, 0, 5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_practice://顺序练习
                Intent intentPractice = new Intent(getActivity(), TestPracticeAcivity.class);
                Bundle bundlePractice = new Bundle();
                bundlePractice.putInt("quesLibId", quesLibId);
                bundlePractice.putString("paperName", quesLibName);
                bundlePractice.putInt("packageId", packageId);
                bundlePractice.putInt("userLibId",userLibId);
                Log.e("aaaaaaaaaquslibslid", quesLibId + "");
                intentPractice.putExtras(bundlePractice);
                startActivity(intentPractice);
                break;
            case R.id.ll_erropic://我的错题
                Intent intent1 = new Intent(getActivity(), PLookCuoTiActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("quesLibId", quesLibId);
                bundle1.putInt("userLibId", userLibId);
                bundle1.putInt("packageId", packageId);
                Log.e("qusLibsId", String.valueOf(quesLibId) + ",1111," + userLibId + "," + packageId);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case R.id.ll_exam://模拟考试
                Intent intent = new Intent(getActivity(), TestContentActivity1.class);
                Bundle bundle = new Bundle();
                bundle.putInt("quesLibId", quesLibId);
                bundle.putInt("userLibId", userLibId);
                bundle.putInt("examType", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ll_search://快速搜题
                Intent searchIntent = new Intent(getActivity(), SearchTestActivity.class);
                Bundle searchBundle = new Bundle();
                searchBundle.putInt("quesLibId", quesLibId);
                searchIntent.putExtras(searchBundle);
                startActivity(searchIntent);
                break;
            case R.id.tv_choose://选择题库
                startActivity(new Intent(getActivity(),ChooseTopicActivity.class));
                break;
            case R.id.img_add:
                startActivity(new Intent(getActivity(), QueslibActivity.class));
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
}
