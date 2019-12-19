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
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.BuyCarActivity;
import com.example.administrator.zahbzayxy.adapters.LessonExpandLvAdapter;
import com.example.administrator.zahbzayxy.adapters.LessonNavigationAdapter;
import com.example.administrator.zahbzayxy.beans.LessonNavigationBean;
import com.example.administrator.zahbzayxy.interfaceserver.LessonGroupInterface;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonFragment extends Fragment {
    private View view;
    private Context context;
    @BindView(R.id.expande_lv)
    ExpandableListView mExpandableListView;
    @BindView(R.id.shoppingCart_iv)
    ImageView buyCar_iv;
    @BindView(R.id.load_bar_layout_evaluating)
    ProgressBarLayout mLoadingBar;
    @BindView(R.id.navigation_lesson_lv)
     ListView navigation_lv;

    LessonExpandLvAdapter mExpandlistAdapter;
    private List<LessonNavigationBean.DataBean.ChildBean>lessonGroupList=new ArrayList<>();
    private Unbinder bind;
    private List<LessonNavigationBean.DataBean>navigationList=new ArrayList<>();
    private LessonNavigationAdapter adapter;
   private int myPosition;
   String token;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    public LessonFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_lesson, container, false);
         bind = ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);


        downLoadListViewData();
        initBuyCar();
        //展开
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                mExpandableListView.setDividerHeight(2);

            }
        });
        //闭合
        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                mExpandableListView.setDividerHeight(14);
              //  mExpandableListView.set

            }
        });
        return view;
    }

    private void downLoadExpandedData(int lessonId) {
        mExpandlistAdapter=new LessonExpandLvAdapter(lessonGroupList,context,mExpandableListView);
        mExpandableListView.setAdapter(mExpandlistAdapter);
        LessonGroupInterface aClass = RetrofitUtils.getInstance().createClass(LessonGroupInterface.class);
        aClass.getLessonExpandedData(token,lessonId).enqueue(new Callback<LessonNavigationBean>() {
            @Override
            public void onResponse(Call<LessonNavigationBean> call, Response<LessonNavigationBean> response) {
                LessonNavigationBean body = response.body();
                if (body!=null){
                    String code = body.getCode();
                    Object errMsg = body.getErrMsg();
                   if (body.getErrMsg()==null) {
                       List<LessonNavigationBean.DataBean> data = body.getData();
                       if (data != null) {
                           List<LessonNavigationBean.DataBean.ChildBean> child = data.get(myPosition).getChild();
                           if (child != null) {
                               lessonGroupList.clear();
                               lessonGroupList.addAll(child);
                               mExpandlistAdapter.notifyDataSetChanged();
                           }
                       }
                   }else {
                       Toast.makeText(context, ""+errMsg, Toast.LENGTH_SHORT).show();
                   }
                }
            }

            @Override
            public void onFailure(Call<LessonNavigationBean> call, Throwable t) {

            }
        });



    }

    private void downLoadListViewData() {
        SharedPreferences tokenDb = context.getSharedPreferences("tokenDb", context.MODE_PRIVATE);
        token = tokenDb.getString("token","");
        showLoadingBar(false);
        adapter=new LessonNavigationAdapter(navigationList,context);
        navigation_lv.setAdapter(adapter);
        LessonGroupInterface aClass = RetrofitUtils.getInstance().createClass(LessonGroupInterface.class);
        aClass.getLessonNavigationData(token).enqueue(new Callback<LessonNavigationBean>() {
            @Override
            public void onResponse(Call<LessonNavigationBean> call, Response<LessonNavigationBean> response) {
                hideLoadingBar();
                LessonNavigationBean body = response.body();
                if (body!=null){
                    if (body.getErrMsg()==null){
                        final List<LessonNavigationBean.DataBean> data = body.getData();
                        if (data!=null){
                            navigationList.clear();
                            navigationList.addAll(data);
                            adapter.notifyDataSetChanged();
                            navigation_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                private int centerId;

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    adapter.setSelectorPos(position);
                                    adapter.notifyDataSetChanged();
                                    centerId = data.get(position).getCenterId();
                                    downLoadExpandedData(centerId);
                                    myPosition=position;
                                }
                            });
                            downLoadExpandedData(data.get(0).getCenterId());
                        }
                    }else {
                        Toast.makeText(context, ""+body.getErrMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<LessonNavigationBean> call, Throwable t) {

            }
        });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventForNum(Integer num){
        if (num!=null){
            if (num==0){
                adapter.setSelectorPos(0);
                adapter.notifyDataSetChanged();
            }else if (num==1){
                adapter.setSelectorPos(1);
                adapter.notifyDataSetChanged();
            }else if (num==2){
                adapter.setSelectorPos(2);
                adapter.notifyDataSetChanged();
            }else if (num==3){
                adapter.setSelectorPos(3);
                adapter.notifyDataSetChanged();
            }else if (num==4){
                adapter.setSelectorPos(4);
                adapter.notifyDataSetChanged();
            }else if (num==5){
                adapter.setSelectorPos(5);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventForLogin(String login){
        if (!TextUtils.isEmpty(login)){
            if (login.equals("login")){
                downLoadListViewData();
                Log.e("login","11111113333");
            }
        }
    }

    private void initBuyCar() {
        buyCar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,BuyCarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Destory","lessonDestroy11111");

    }

    @Override
    public void onStop() {
        super.onStop();
        myPosition=0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        Log.e("Destory","lessonDestroy");
        EventBus.getDefault().unregister(this);
    }
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        if (mLoadingBar!=null) {
            mLoadingBar.hide();
        }
    }
}
