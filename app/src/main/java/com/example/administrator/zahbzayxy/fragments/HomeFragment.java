package com.example.administrator.zahbzayxy.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.activities.BuyActivity;
import com.example.administrator.zahbzayxy.adapters.ADAdapter;
import com.example.administrator.zahbzayxy.adapters.HomeLessonClassAdapter;
import com.example.administrator.zahbzayxy.adapters.HomeLessonFenLeiAdapter;
import com.example.administrator.zahbzayxy.beans.HomeLessonClassBean;
import com.example.administrator.zahbzayxy.beans.HomePictureBean;
import com.example.administrator.zahbzayxy.interfaceserver.HomeFragmentInterface;
import com.example.administrator.zahbzayxy.myinterface.MyLessonInterface;
import com.example.administrator.zahbzayxy.myviews.MyGridView;
import com.example.administrator.zahbzayxy.myviews.MyListView;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ProgressBarLayout mLoadingBar;
    Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
    @BindView(R.id.dot_container)
    LinearLayout dot_container;
    private View view;
    //广告轮播下的小点
    private List<ImageView> views=new ArrayList<>();
    //广告轮播图片的集合
    private List<HomePictureBean.DataBean>pictureList=new ArrayList<>();
    private ADAdapter adapter;
    HomeLessonClassAdapter lessonClassAdapter;
    private AutoScrollViewPager shouye_viewpager;
    List<Map<String,Object>> list;
    private List<HomeLessonClassBean.DataEntity>lessonClassList=new ArrayList<>();
    @BindView(R.id.home_lesson_fenLei_gv)
    MyGridView lesson_fenLei_gv;
    @BindView(R.id.home_lesson_class_detail_gv)
    MyListView homeLessonClassDeatil_lv;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        initView();
        initViewPager();
        initLessonFenLeiGVData();
        initLessonClassDetailData();
        return view;
    }

    private void initLessonClassDetailData() {
        lessonClassAdapter=new HomeLessonClassAdapter(lessonClassList,context);
        homeLessonClassDeatil_lv.setAdapter(lessonClassAdapter);
        MyLessonInterface aClass = RetrofitUtils.getInstance().createClass(MyLessonInterface.class);
        aClass.getHomeLessonClassData().enqueue(new Callback<HomeLessonClassBean>() {
            @Override
            public void onResponse(Call<HomeLessonClassBean> call, Response<HomeLessonClassBean> response) {

                HomeLessonClassBean body = response.body();
                if (body!=null){
                    String code = body.getCode();
                    if (code.equals("00000")){
                        List<HomeLessonClassBean.DataEntity> data = body.getData();
                        lessonClassList.clear();
                        lessonClassList.addAll(data);
                        lessonClassAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeLessonClassBean> call, Throwable t) {
                String message = t.getMessage();
                Log.e("errqqq",message);

            }
        });
    }

    private void initLessonFenLeiGVData() {
        int gridImags[]=new int[]{R.mipmap.home_zyfzr,R.mipmap.home_aqsc,R.mipmap.home_tzzy,R.mipmap.home_zcaq,R.mipmap.home_zcxf,R.mipmap.home_qbfl};
        list=new ArrayList<>();
        for (int i=0;i<gridImags.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("img",gridImags[i]);
            list.add(map);
        }
        HomeLessonFenLeiAdapter adapter=new HomeLessonFenLeiAdapter(list,context);
        lesson_fenLei_gv.setAdapter(adapter);
        lesson_fenLei_gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
        lesson_fenLei_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        EventBus.getDefault().post(1);
                        break;
                    case 2:
                        EventBus.getDefault().post(2);
                        break;
                    case 3:
                        EventBus.getDefault().post(3);

                        break;
                    case 4:
                        EventBus.getDefault().post(4);
                        break;
                    case 5:
                        EventBus.getDefault().post(5);
                        break;
                    case 6:
                        EventBus.getDefault().post(6);
                        break;

        }
    }
        });
    }


    private void initView() {
        mLoadingBar= (ProgressBarLayout) view.findViewById(R.id.load_bar_layout_evaluating);
        dot_container= (LinearLayout) view.findViewById(R.id.dot_container);
        shouye_viewpager= (AutoScrollViewPager) view.findViewById(R.id.shouye_viewpager);

    }
    //广告轮播
    private void initViewPager() {
        showLoadingBar(false);
        HomeFragmentInterface aClass = RetrofitUtils.getInstance().createClass(HomeFragmentInterface.class);
        aClass.getHomePictureData().enqueue(new Callback<HomePictureBean>() {
            @Override
            public void onResponse(Call<HomePictureBean> call, Response<HomePictureBean> response) {
                if (response.code()==200) {
                    HomePictureBean body = response.body();
                    int code = response.code();
                    Log.e("codePic", code + "");
                    if (body != null && body.getErrMsg() == null) {
                        hideLoadingBar();
                        List<HomePictureBean.DataBean> data = body.getData();
                        pictureList.addAll(data);
                        initViewPagerData(pictureList);
                    }
                }
            }
            @Override
            public void onFailure(Call<HomePictureBean> call, Throwable t) {
                String message = t.getMessage();
                Log.e("lunboPic",message);

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initViewPagerData(final List<HomePictureBean.DataBean> pictureList) {
        int size = pictureList.size();
        if (size>0) {
            for (int i=0;i<size;i++){
                ImageView view=new ImageView(context);
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) v.getTag();
                        Intent intent =new Intent();
                        intent.setClass(context,BuyActivity.class);
                        String jumpUrl = pictureList.get(tag).getJumpUrl();
                        if (!TextUtils.isEmpty(jumpUrl)) {
                            intent.putExtra("homePictureUrl",jumpUrl);
                            startActivity(intent);
                        }
                    }
                });
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.with(context).load(pictureList.get(i).getImageUrl()).into(view);
                views.add(view);
                //viewpager的小点
                //加圆点
                ImageView dotview=new ImageView(context);
                dotview.setPadding(5,0,5,0);
                if(i==0){
                    dotview.setImageResource(R.drawable.dot_shape_selected);
                }else {
                    dotview.setImageResource(R.drawable.dot_shape);
                }
                dot_container.addView(dotview);
            }
            adapter=new ADAdapter(views);
            shouye_viewpager.setAdapter(adapter);
            shouye_viewpager.startAutoScroll();
            shouye_viewpager.setFilterTouchesWhenObscured(true);
//            shouye_viewpager.setTouchscreenBlocksFocus(true);
            shouye_viewpager.setStopScrollWhenTouch(true);
            shouye_viewpager.setCycle(true);
            shouye_viewpager.setStopScrollWhenTouch(true);
          //  shouye_viewpager.setScrollDurationFactor(2);
            shouye_viewpager.setInterval(4000);
            shouye_viewpager.startAutoScroll(4000);
            shouye_viewpager.isStopScrollWhenTouch();
            shouye_viewpager.setHorizontalScrollBarEnabled(true);

            shouye_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                    ImageView vi=(ImageView) dot_container.getChildAt(position);
                    for(int i=0;i<dot_container.getChildCount();++i){
                        if(i==position){
                            vi.setImageResource(R.drawable.dot_shape_selected);
                        }else{
                            ((ImageView)(dot_container.getChildAt(i))).setImageResource(R.drawable.dot_shape);
                        }
                    }
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
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
    public void onPause() {
        super.onPause();
        Log.e("Destory","homeDestroy111111111");

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Destory","homeDestroy");

    }

}
