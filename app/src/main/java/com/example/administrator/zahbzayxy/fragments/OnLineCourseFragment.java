package com.example.administrator.zahbzayxy.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.ChooseTopicActivity;
import com.example.administrator.zahbzayxy.adapters.LearnOnlineCourseAdapter;
import com.example.administrator.zahbzayxy.adapters.TestNavigationAdapter;
import com.example.administrator.zahbzayxy.beans.OnTransitionTextListener;
import com.example.administrator.zahbzayxy.beans.OnlineCourseBean;
import com.example.administrator.zahbzayxy.beans.TestNavigationBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.ColorBar;
import com.example.administrator.zahbzayxy.utils.DisplayUtil;
import com.example.administrator.zahbzayxy.utils.FixedIndicatorView;
import com.example.administrator.zahbzayxy.utils.Indicator;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huwei.
 * Data 2019-12-19.
 * Time 16:28.
 * 学习在线课
 */
public class OnLineCourseFragment extends Fragment implements PullToRefreshListener,View.OnClickListener{
    private View view;
    private FixedIndicatorView fixedIndicatorView;
    private Context context;
    private String token;
    private ImageView img_add;
    private TestNavigationAdapter adapter;
    private ProgressBarLayout mLoadingBar;
    private List<TestNavigationBean.DataBean>navigationList=new ArrayList<>();
    private LearnOnlineCourseAdapter learnOnlineCourseAdapter;
    private PullToRefreshRecyclerView recyclerview;
    private TextView tv_addTopic;
    private List<OnlineCourseBean.OnLineListBean> onLineListBeanList= new ArrayList<>();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_online_course,container,false);
        fixedIndicatorView =view.findViewById(R.id.singleTab_fixedIndicatorView);
        mLoadingBar= view.findViewById(R.id.load_bar_layout_evaluating);
        recyclerview =view.findViewById(R.id.recyclerview);
        tv_addTopic=view.findViewById(R.id.tv_chooseTopic);//选择题库
        tv_addTopic.setOnClickListener(this);
        img_add=view.findViewById(R.id.img_add);//添加题库
        img_add.setOnClickListener(this);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"点击了添加题库",Toast.LENGTH_LONG).show();
            }
        });
        initNavigationData();
        initDate();
        return view;
    }

    private void initDate() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        for(int i =0; i < 6; i++){
            OnlineCourseBean.OnLineListBean onLineListBean = new OnlineCourseBean.OnLineListBean();
            onLineListBean.setTitle("危险化学品经营单位主要负责人和相关人员培训课程");
            onLineListBean.setTime("学习时间:2019.12.1");
            onLineListBean.setState("完成");
            onLineListBeanList.add(onLineListBean);
        }
//        //初始化adapter
        learnOnlineCourseAdapter = new LearnOnlineCourseAdapter(getActivity(), onLineListBeanList);
//        //添加数据源
        recyclerview.setAdapter(learnOnlineCourseAdapter);
        recyclerview.setLayoutManager(layoutManager);
        //设置是否显示上次刷新时间
        recyclerview.displayLastRefreshTime(true);
        //是否开启上拉加载
        recyclerview.setLoadingMoreEnabled(false);
        //是否开启上拉刷新
        recyclerview.setPullRefreshEnabled(false);
        //设置刷新回调
        recyclerview.setPullToRefreshListener(this);
//        recyclerview.setLoadMoreResource(R.drawable.account);//修改加载图标
        //主动触发下拉刷新操作
//        recyclerview.onRefresh();
        //设置EmptyView
        View emptyView = View.inflate(getActivity(), R.layout.layout_empty_view, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerview.setEmptyView(emptyView);

    }

    private void initNavigationData() {
            SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
            token = tokenDb.getString("token","");
            adapter=new TestNavigationAdapter(navigationList,context);
//            testNavigation_gv.setAdapter(adapter);
            TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
            aClass.getTestNavigationData(token).enqueue(new Callback<TestNavigationBean>() {
                @Override
                public void onResponse(Call<TestNavigationBean> call, Response<TestNavigationBean> response) {
                    hideLoadingBar();
                    TestNavigationBean body = response.body();
                    if (body!=null){
                        String code = body.getCode();
                        Object errMsg = body.getErrMsg();
                        if (errMsg==null){
                            final List<TestNavigationBean.DataBean> data = body.getData();
                            if (data!=null){
                                navigationList.clear();
                                int size = data.size();
                                navigationList.addAll(data);
                                set(fixedIndicatorView,size);
//                                downLoadTestExpandedData(data.get(0).getCenterId());
                            }
                        }else {
                            Toast.makeText(context, ""+errMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TestNavigationBean> call, Throwable t) {

                }
            });
    }
    private void set(Indicator indicator, int count) {
        indicator.setAdapter(new MyAdapter(navigationList,indicator));

        indicator.setScrollBar(new ColorBar(context, context.getResources().getColor(R.color.transparent), 10));//设置选中下划线

        float unSelectSize = 14;
        float selectSize = unSelectSize * 1.1f;
        int selectColor = context.getResources().getColor(R.color.lightBlue);
        int unSelectColor =context.getResources().getColor(R.color.black);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        indicator.setCurrentItem(0,true);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_chooseTopic://选择题库
                startActivity(new Intent(getActivity(), ChooseTopicActivity.class));
                break;
            case R.id.img_add://添加题库
                break;
        }
    }

    private class MyAdapter extends Indicator.IndicatorAdapter {
        private List<TestNavigationBean.DataBean>list;
        private Indicator indicator;

        public MyAdapter(List<TestNavigationBean.DataBean>list, Indicator indicator) {
            super();
            this.list=list;
            this.indicator=indicator;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.tab_top, parent, false);
            }
            TextView textView = (TextView) convertView;
            //用了固定宽度可以避免TextView文字大小变化，tab宽度变化导致tab抖动现象
            textView.setWidth(DisplayUtil.dipToPix(context,80));
            String centerName = list.get(position).getCenterName();
            textView.setText(centerName);

            indicator.setOnIndicatorItemClickListener(new Indicator.OnIndicatorItemClickListener() {
                @Override
                public boolean onItemClick(View clickItemView, int position) {
//                    myPostion=position;
//                    downLoadTestExpandedData(list.get(myPostion).getCenterId());
                    return false;
                }
            });

            return convertView;
        }
    }
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }

}
