package com.example.administrator.zahbzayxy.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.BuyCarActivity;
import com.example.administrator.zahbzayxy.adapters.TestExpandedAdapter;
import com.example.administrator.zahbzayxy.adapters.TestNavigationAdapter;
import com.example.administrator.zahbzayxy.beans.OnTransitionTextListener;
import com.example.administrator.zahbzayxy.beans.TestNavigationBean;
import com.example.administrator.zahbzayxy.interfaceserver.TestGroupInterface;
import com.example.administrator.zahbzayxy.utils.ColorBar;
import com.example.administrator.zahbzayxy.utils.DisplayUtil;
import com.example.administrator.zahbzayxy.utils.FixedIndicatorView;
import com.example.administrator.zahbzayxy.utils.Indicator;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewTestFragment extends Fragment {

    private ProgressBarLayout mLoadingBar;
    private ExpandableListView testexpanded_lv;
    private List<TestNavigationBean.DataBean.ChildBean> list=new ArrayList<>();
    private View view;
    private Context context;
    private ImageView buyCar_iv;
    int myPostion;

    private GridView testNavigation_gv;
    private TestNavigationAdapter adapter;
    private List<TestNavigationBean.DataBean>navigationList=new ArrayList<>();
    TestExpandedAdapter testExpandedAdapter;
    FixedIndicatorView fixedIndicatorView;
    /**
     * 偏移量（手机屏幕宽度 / 选项卡总数 - 选项卡长度） / 2
     */
    private int offset = 0;

    /**
     * 下划线图片宽度
     */
    private int lineWidth;

    /**
     * 当前选项卡的位置
     */
    private int current_index = 0;
private String token;

   // private IndicatorViewPager indicatorViewPager;
    //private LinearLayout containerExpandlv_ll;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_new_test, container, false);
        fixedIndicatorView = (FixedIndicatorView) view.findViewById(R.id.singleTab_fixedIndicatorView);
        EventBus.getDefault().register(this);
        initView();
        initTestNavigationData();
        //闭合
        testexpanded_lv.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                testexpanded_lv.setDividerHeight(14);
                //testexpanded_lv.setDivider(context.getResources().getDrawable(R.color.gray));

            }
        });
        testexpanded_lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                testexpanded_lv.setDividerHeight(14);
            //    testexpanded_lv.setDivider(context.getResources().getDrawable(R.color.gray));

            }
        });
        return view;
    }


    private void set(Indicator indicator, int count) {
        indicator.setAdapter(new MyAdapter(navigationList,indicator));

        indicator.setScrollBar(new ColorBar(context, context.getResources().getColor(R.color.lightBlue), 10));

        float unSelectSize = 14;
        float selectSize = unSelectSize * 1.1f;
        int selectColor = context.getResources().getColor(R.color.lightBlue);
        int unSelectColor =context.getResources().getColor(R.color.black);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        indicator.setCurrentItem(0,true);
    }
    private class MyAdapter extends Indicator.IndicatorAdapter {
        private List<TestNavigationBean.DataBean>list;
        private Indicator indicator;

        public MyAdapter(List<TestNavigationBean.DataBean>list,Indicator indicator) {
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
                    myPostion=position;
                    downLoadTestExpandedData(list.get(myPostion).getCenterId());
                    return false;
                }
            });

            return convertView;
        }
    }




    private void downLoadTestExpandedData(int testId) {

        testExpandedAdapter=new TestExpandedAdapter(list,context);
        testexpanded_lv.setAdapter(testExpandedAdapter);

        TestGroupInterface aClass = RetrofitUtils.getInstance().createClass(TestGroupInterface.class);
        aClass.getTestGroupData(token,testId).enqueue(new Callback<TestNavigationBean>() {
            @Override
            public void onResponse(Call<TestNavigationBean> call, Response<TestNavigationBean> response) {
                TestNavigationBean body = response.body();
                if (body!=null){
                    Object errMsg = body.getErrMsg();
                    String code = body.getCode();
                    if (errMsg==null){
                        List<TestNavigationBean.DataBean.ChildBean> child = body.getData().get(myPostion).getChild();
                        list.clear();
                        if (child!=null) {
                            list.addAll(child);
                        }
                        testExpandedAdapter.notifyDataSetChanged();

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

    private void initTestNavigationData() {
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
        token = tokenDb.getString("token","");
        showLoadingBar(false);
        adapter=new TestNavigationAdapter(navigationList,context);
        testNavigation_gv.setAdapter(adapter);
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
                             downLoadTestExpandedData(data.get(0).getCenterId());
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



    private void initView() {

        testNavigation_gv= (GridView) view.findViewById(R.id.testNavigation_gv);
        mLoadingBar= (ProgressBarLayout) view.findViewById(R.id.load_bar_layout_evaluating);
        testexpanded_lv= (ExpandableListView) view.findViewById(R.id.test_eplv);


        buyCar_iv= (ImageView) view.findViewById(R.id.shoppingCart_iv);
        buyCar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,BuyCarActivity.class);
                startActivity(intent);
            }
        });
       // containerExpandlv_ll= (LinearLayout) view.findViewById(R.id.containerExpandlv_ll);



    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventForLogin(String login){
        if (!TextUtils.isEmpty(login)){
          if (login.equals("login")){
              initTestNavigationData();
              Log.e("login","1111111");
          }
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
