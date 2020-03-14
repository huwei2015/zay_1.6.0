package com.example.administrator.zahbzayxy.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.LessonFragmentPageAdapter;
import com.example.administrator.zahbzayxy.beans.BuyInstanceBean;
import com.example.administrator.zahbzayxy.beans.LessonThiredBean;
import com.example.administrator.zahbzayxy.beans.SuccessBean;
import com.example.administrator.zahbzayxy.ccvideo.FreePlayActivity;
import com.example.administrator.zahbzayxy.ccvideo.MediaPlayActivity;
import com.example.administrator.zahbzayxy.fragments.DetailFragment;
import com.example.administrator.zahbzayxy.fragments.DirectoryFragment;
import com.example.administrator.zahbzayxy.fragments.LesssonTestLiberyFragment;
import com.example.administrator.zahbzayxy.interfacecommit.BuyCarGroupInterface;
import com.example.administrator.zahbzayxy.interfaceserver.LessonGroupInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.ToastUtils;
import com.google.gson.Gson;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *在线课课程详情购物车
 */
public class LessonThiredActivity extends BaseActivity {
    @BindView(R.id.load_bar_layout_evaluating)
    ProgressBarLayout mLoadingBar;
    @BindView(R.id.lesson_tab)
    TabLayout lesson_tab;
    @BindView(R.id.lessonTab_vp)
    ViewPager lessonTab_vp;
    private LessonFragmentPageAdapter pageAdapter;
    //头布局中的内容
    @BindView(R.id.thirdLesonName_iv)
    ImageView thirdLessonName_iv;
    @BindView(R.id.thirdLesonName_tv)
    TextView thiredLessonName_tv;
    @BindView(R.id.thirdLesonNum_tv)
    TextView thiredlessonNum_tv;
    @BindView(R.id.lesson_thired_back)
    ImageView finish_iv;
    @BindView(R.id.thirdLesonTeacher_tv)
   TextView teacher_tv;
  //tabLayout中的标题title
   private List<String>titlesList=new ArrayList<>();
  @BindView(R.id.lessonPrice)
  TextView price_tv;
  @BindView(R.id.lessonPrice_two)
  TextView priceTwo_tv;
    //购物车
    @BindView(R.id.shoppingCart_iv)
    ImageView buyCar_iv;
    //viewPager中的fragment
    private List<Fragment>fragmentList=new ArrayList<>();
    FragmentManager fragmentManager;
    private Unbinder bind;
    private List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean>beanList=new ArrayList<>();
    @BindView(R.id.buy_bt)
    Button buy_bt;
    @BindView(R.id.addBuyCart_bt)
    Button buyCar_bt;
    @BindView(R.id.buy_rl)
    RelativeLayout buy_rl;
    @BindView(R.id.suggestBuy_tv)
    TextView sugget_tv;
    @BindView(R.id.tv_watch)
    TextView tv_watch;
    private static int  mainCourseId;
    String token;
    private int courseId;
    private int userCourseId;
    private SharedPreferences tokenDb;
    private String isDatacenter;
    int courseType;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_thired);
        bind = ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        courseId= getIntent().getIntExtra("courseId", 0);
        userCourseId = getIntent().getIntExtra("userCourseId", 0);
        isDatacenter = getIntent().getStringExtra("isDatacenter");
        initHeadView();
        initFragment();
        tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        int isMechanism = tokenDb.getInt("isMechanism", 0);
        if (isMechanism!=0){
            buy_rl.setVisibility(View.GONE);
            sugget_tv.setVisibility(View.VISIBLE);
            sugget_tv.setText("想要学习课程，请联系本地培训机构！");
        }else {
            sugget_tv.setVisibility(View.GONE);
            buy_rl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        token = tokenDb.getString("token","");
        buyOnClickListenner();
        buyCarOnClickListenner();
    }

    //描述接口
    private void initHeadView() {
        showLoadingBar(false);
        int courseId = getIntent().getIntExtra("courseId",0);
        String isDatacenter =getIntent().getStringExtra("isDatacenter");
        Log.e("courseId",String.valueOf(courseId));
        LessonGroupInterface aClass = RetrofitUtils.getInstance().createClass(LessonGroupInterface.class);
        aClass.getLessonDetailData(courseId,isDatacenter).enqueue(new Callback<LessonThiredBean>() {
            @Override
            public void onResponse(Call<LessonThiredBean> call, Response<LessonThiredBean> response) {
                LessonThiredBean body = response.body();
                if (response != null && body != null) {
                    // String s = body.getData().toString();
                    LessonThiredBean.DataBean data = body.getData();
                    if (data != null) {
                        LessonThiredBean.DataBean.CourseDescBean courseDesc = data.getCourseDesc();
                        List<LessonThiredBean.DataBean.CourseDescBean.ChildListBean> childList = data.getCourseDesc().getChildList();
                        beanList.clear();
                        beanList.addAll(childList);
                        mainCourseId = body.getData().getCourseDesc().getId();
                        if (courseDesc != null) {
                            hideLoadingBar();
                            String courseName = courseDesc.getCourseName();
                            int courseHours = courseDesc.getCourseHours();
                            int id=courseDesc.getId();
                            Log.i("ynf","ynf============"+id);
                            String courseImagePath = courseDesc.getCourseImagePath();
                            if (!TextUtils.isEmpty(courseName) && !TextUtils.isEmpty(String.valueOf(courseHours))) {
                                thiredLessonName_tv.setText(courseName);
                                thiredlessonNum_tv.setText("总学时:" + String.valueOf(courseHours) + "学时");
                            }
                            String teachers = courseDesc.getTeachers();
                            if (!TextUtils.isEmpty(teachers)) {
                                teacher_tv.setText("课程讲师:" + teachers);
                            }
                            String devidePrice = courseDesc.getDevidePrice();
                            if (!TextUtils.isEmpty(devidePrice)){
                                if (devidePrice.contains(";")){
                                    String[] split = devidePrice.split(";");
                                    price_tv.setText(split[0]+"");
                                    priceTwo_tv.setText(split[1]+"");

                                }else {
                                    price_tv.setText("" + devidePrice);
                                }
                            }
                            if(devidePrice.equals("0.00")){
                                tv_watch.setVisibility(View.VISIBLE);
                                sugget_tv.setVisibility(View.GONE);
                                buy_rl.setVisibility(View.GONE);
                            }
                            tv_watch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(LessonThiredActivity.this, FreePlayActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("coruseId",id);
                                    bundle.putInt("courseType",courseType);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    ToastUtils.showLongInfo("点击了立即观看");
                                }
                            });
                            if (courseImagePath != null) {

                                Picasso.with(LessonThiredActivity.this).load(courseImagePath).placeholder(R.mipmap.loading_png)
                                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                        .config(Bitmap.Config.RGB_565).into(thirdLessonName_iv);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LessonThiredBean> call, Throwable t) {
             //   Log.e("lessson333333",t.getMessage());

            }
        });


        finish_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initFragment() {
        titlesList.add("目录");
        titlesList.add("课程概述");
        titlesList.add("模考题库");
        DirectoryFragment directoryFragment=new DirectoryFragment();
        DetailFragment titleFragment=new DetailFragment();
        LesssonTestLiberyFragment lesssonTestLiberyFragment =new LesssonTestLiberyFragment();
        fragmentList.add(directoryFragment);
        directoryFragment.setIsDatacenter(isDatacenter);
        fragmentList.add(titleFragment);
        titleFragment.setIsDatacenter(isDatacenter);
        fragmentList.add(lesssonTestLiberyFragment);
        fragmentManager=getSupportFragmentManager();
        pageAdapter=new LessonFragmentPageAdapter(fragmentManager,fragmentList,titlesList);
        lessonTab_vp.setAdapter(pageAdapter);
        lesson_tab.addTab(lesson_tab.newTab().setText(titlesList.get(0)));
        lesson_tab.addTab(lesson_tab.newTab().setText(titlesList.get(1)));
        lesson_tab.addTab(lesson_tab.newTab().setText(titlesList.get(2)));
        lesson_tab.setupWithViewPager(lessonTab_vp);
        buyCar_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LessonThiredActivity.this,BuyCarActivity.class);
                startActivity(intent);
            }
        });
    }

    //加入购物车
    private void buyCarOnClickListenner() {
        buyCar_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String []cIds= new String[beanList.size()];
                for (int i = 0; i < beanList.size(); i++) {
                    int id = beanList.get(i).getId();
                    cIds[i]=(String.valueOf(id));
                }
                if(isDatacenter.equals("yes")){
                    courseType = 0;
                }else if(isDatacenter.equals("no")){
                    courseType = 1;
                }
                BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
                aClass.buyCarAddCourseData(mainCourseId, cIds,token,courseType).enqueue(new Callback<SuccessBean>() {
                    @Override
                    public void onResponse(Call<SuccessBean> call, Response<SuccessBean> response) {
                        SuccessBean body = response.body();
                        String s = new Gson().toJson(body).toString();
                        Log.e("bodybodybody",s);
                        if (response!=null&&body!=null){
                            boolean data = body.getData();
                            String code = body.getCode();
                            String errMsg= (String) body.getErrMsg();
                            if (code.equals("99999")){
                                Toast.makeText(LessonThiredActivity.this,errMsg,Toast.LENGTH_SHORT).show();
                            }
                            else if (code.equals("00003")){
                                Toast.makeText(LessonThiredActivity.this,errMsg,Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences("tokenDb",MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putBoolean("isLogin",false);
                                edit.commit();
                            }else if (dbIsLogin()==false){
                                Toast.makeText(LessonThiredActivity.this,errMsg, Toast.LENGTH_SHORT).show();
                            }
                            else if (code.equals("00012")){
                                Toast.makeText(LessonThiredActivity.this,errMsg,Toast.LENGTH_SHORT).show();
                            }
                            else if (code.equals("00000")){
                                Toast.makeText(LessonThiredActivity.this,"加入购物车成功",Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<SuccessBean> call, Throwable t) {
//                        Toast.makeText(LessonThiredActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    //加入购物车
    private void buyOnClickListenner() {
        buy_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyCarGroupInterface aClass = RetrofitUtils.getInstance().createClass(BuyCarGroupInterface.class);
                aClass.getBuyInstanceData(courseId,token).enqueue(new Callback<BuyInstanceBean>() {
                    @Override
                    public void onResponse(Call<BuyInstanceBean> call, Response<BuyInstanceBean> response) {
                        BuyInstanceBean body = response.body();
                        String code = body.getCode();
                        if (body != null) {
                            if (code.equals("00003")) {
                                //  Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                                SharedPreferences sp = getSharedPreferences("tokenDb",MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putBoolean("isLogin", false);
                                edit.commit();
                                loginDialog();
                                Log.e("buyLogin","111111111");
                            } else if (dbIsLogin() == false) {
                                //Toast.makeText(context, "用户未登录", Toast.LENGTH_SHORT).show();
                                loginDialog();
                                Log.e("buyLogin","2222222");
                            } else if (code.equals("00025")) {
                                Toast.makeText(LessonThiredActivity.this, "用户已购买该课程", Toast.LENGTH_SHORT).show();
                            } else if (code.equals("99999")) {
                                Toast.makeText(LessonThiredActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                            } else if (code.equals("00023")) {
                                Toast.makeText(LessonThiredActivity.this, "该订单已存在", Toast.LENGTH_SHORT).show();
                            } else if (code.equals("00000")) {
                                BuyInstanceBean.DataBean data = body.getData();
                                String pirce = data.getPrice();
                                String orderNumber = data.getOrderNumber();
                                if (orderNumber != null&&!TextUtils.isEmpty(pirce)) {
                                    Intent intent = new Intent(LessonThiredActivity.this, PayUiActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putBoolean("isLessonOrder",true);
                                    //  bundle.putDouble("testPrice", Double.valueOf(pirce));
                                    bundle.putString("testPrice", pirce);
                                    bundle.putString("orderNumber", orderNumber);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<BuyInstanceBean> call, Throwable t) {
//                        Toast.makeText(LessonThiredActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }


    private boolean dbIsLogin() {
        SharedPreferences sharedPreferences =getSharedPreferences("tokenDb",MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin==true){
            return true;
        }else {
            return false;
        }
    }

    public void loginDialog(){
        new AlertDialog.Builder(LessonThiredActivity.this).setTitle("请先登录再购买,去登录?")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog.dismiss();
                        Intent intent=new Intent(LessonThiredActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
    public void showLoadingBar(boolean transparent) {
        mLoadingBar.setBackgroundColor(transparent ? Color.TRANSPARENT : getResources().getColor(R.color.main_bg));
        mLoadingBar.show();
    }

    public void hideLoadingBar() {
        mLoadingBar.hide();
    }
}
