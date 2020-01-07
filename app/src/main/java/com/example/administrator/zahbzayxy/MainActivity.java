package com.example.administrator.zahbzayxy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bokecc.sdk.mobile.util.DWSdkStorage;
import com.bokecc.sdk.mobile.util.DWStorageUtil;
import com.bokecc.sdk.mobile.util.HttpUtil;
import com.example.administrator.zahbzayxy.beans.AppVersionBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadController;
import com.example.administrator.zahbzayxy.ccvideo.DownloadService;
import com.example.administrator.zahbzayxy.fragments.LearningFragment;
import com.example.administrator.zahbzayxy.fragments.LessonFragment;
import com.example.administrator.zahbzayxy.fragments.NewHomeFragment;
import com.example.administrator.zahbzayxy.fragments.NewTestFragment;
import com.example.administrator.zahbzayxy.fragments.UserFragment;
import com.example.administrator.zahbzayxy.myinterface.MyLessonInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_rg)
    RadioGroup radioGroup;
    @BindView(R.id.homePager_rb)
    RadioButton homePager;
    @BindView(R.id.lessonCenter_rb)
    RadioButton lessonCenter;
    @BindView(R.id.testCenter_rb)
    RadioButton testCenter;
    @BindView(R.id.userCenter_rb)
    RadioButton userCenter;
    @BindView(R.id.content_home)
    RelativeLayout homeContent;
    List<Fragment> fragmentList = new ArrayList<>();
    List<RadioButton> radioButtonList = new ArrayList<>();
    private FragmentManager manager;
    private int prePosition = 0;
    //HomeFragment homeFragment;
    NewHomeFragment newHomeFragment;
    //课程分类
//    LessonFragment lessonFragment;
    LearningFragment learningFragment;
    //题库分类
    NewTestFragment newTestFragment;
//    ExamFragment examFragment;
    UserFragment userFragment;
    private String moreTiKu;
    private String downloadAdd;
    private String versionName;
    private int isForce;
    private long exitTime = 0;
    private PopupWindow popupWindow;
    private String isLoginActivity;
    //download
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private FragmentManager fragmentManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(0x80000000, 0x80000000);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ExitApplication.getInstance().addActivity(this);
        Log.e("aaaaaaaaaa", "oncreat");
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        init();
        initWifi();
        initOnClickListenner();
        initMoreTiKu();

        //download
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //进入到这里代表没有权限.
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
        }
        //LogcatHelper.getInstance(this).start();

        HttpUtil.LOG_LEVEL = HttpUtil.HttpLogLevel.DETAIL;
        //初始化数据库和下载数据
        DownloadController.init();
        //启动后台下载service
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        initDWStorage(); // 针对动态密钥的本地下载、播放的接口进行初始化
    }

    private void initAppVersion() {
        MyLessonInterface aClass = RetrofitUtils.getInstance().createClass(MyLessonInterface.class);
        aClass.getAppVersionData(1).enqueue(new Callback<AppVersionBean>() {
            @Override
            public void onResponse(Call<AppVersionBean> call, Response<AppVersionBean> response) {
                int code = response.code();
                if (code == 200) {
                    AppVersionBean body = response.body();
                    if (body != null) {
                        String code1 = body.getCode();
                        if (code1.equals("00000")) {
                            List<AppVersionBean.DataEntity> data = body.getData();
                            if (data != null) {
                                try {
                                    //此app版本
                                    versionName = getVersionName(MainActivity.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                int size1 = data.size();
                                String verNum1 = data.get(size1 - 1).getVerNum();
                                String verNumString = verNum1.replace(".", "");
                                String versionNameString = versionName.replace(".", "");
                                Integer versionNameInteger = Integer.valueOf(versionNameString);
                                Integer verNumInteger = Integer.valueOf(verNumString);
                                downloadAdd = data.get(size1 - 1).getDownloadAdd();

                                if (versionNameInteger - verNumInteger < 0) {
                                    for (int i = 0; i < size1; i++) {
                                        isForce = data.get(i).getIsForce();
                                        if (isForce == 1) {
                                            String verNum = data.get(i).getVerNum();
                                            String verNumString1 = verNum.replace(".", "");
                                            Integer verNumInteager1 = Integer.valueOf(verNumString1);
                                            if (versionNameInteger - verNumInteager1 < 0) {
                                                isForce = 1;
                                            } else {
                                                isForce = 0;
                                            }
                                        }
                                    }
                                    initAppVersionDialog1();
                                }
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<AppVersionBean> call, Throwable t) {

            }
        });

    }


    private void initDWStorage() {
        DWSdkStorage myDWSdkStorage = new DWSdkStorage() {
            private SharedPreferences sp = getApplicationContext().getSharedPreferences("mystorage", MODE_PRIVATE);

            @Override
            public void put(String key, String value) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(key, value);
                editor.commit();
            }

            @Override
            public String get(String key) {
                return sp.getString(key, "");
            }
        };

        DWStorageUtil.setDWSdkStorage(myDWSdkStorage);
    }

    private void initAppVersionDialog1() {
        View popView = View.inflate(this.getApplication(), R.layout.app_update_pop, null);
        popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, false);
        if (((Activity) MainActivity.this).isFinishing()) {//解决升级为空崩溃问题
            popupWindow.showAsDropDown(popView, 0, -WindowManager.LayoutParams.MATCH_PARENT);
        }
        //设置空的背景图片(这句不加可能会出现黑背景,最好加上)
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        TextView cancel = popView.findViewById(R.id.myquestion_cancel);
        TextView downLoad_tv = popView.findViewById(R.id.downLoadNow_tv);
        downLoad_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去下载
                Uri uri = Uri.parse(downloadAdd);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                MainActivity.this.startActivity(intent);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isForce != 1) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "升级之后才可以使用", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public static String getVersionName(Context context) throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    private void initMoreTiKu() {
        moreTiKu = getIntent().getStringExtra("moreTiKu");
        if (!TextUtils.isEmpty(moreTiKu)) {
            radioButtonList.get(2).setSelected(true);
            radioButtonList.get(0).setSelected(false);
            hideAndShow(2);

        }
    }

    private void initOnClickListenner() {
        radioButtonList.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAndShow(0);
                radioButtonList.get(0).setSelected(true);
                radioButtonList.get(1).setSelected(false);
                radioButtonList.get(2).setSelected(false);
                radioButtonList.get(3).setSelected(false);
            }
        });
        radioButtonList.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAndShow(1);
                radioButtonList.get(0).setSelected(false);
                radioButtonList.get(1).setSelected(true);
                radioButtonList.get(2).setSelected(false);
                radioButtonList.get(3).setSelected(false);
            }
        });
        radioButtonList.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAndShow(2);
                radioButtonList.get(0).setSelected(false);
                radioButtonList.get(1).setSelected(false);
                radioButtonList.get(2).setSelected(true);
                radioButtonList.get(3).setSelected(false);
            }
        });
        radioButtonList.get(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAndShow(3);
                radioButtonList.get(0).setSelected(false);
                radioButtonList.get(1).setSelected(false);
                radioButtonList.get(2).setSelected(false);
                radioButtonList.get(3).setSelected(true);
            }
        });
    }

    private void initWifi() {
        SharedPreferences sp = getSharedPreferences("wifiDb", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("WifiSwitch", true);
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
//         initAppVersion();


    }

    private void init() {
        //homeFragment  = new HomeFragment();
        newHomeFragment = new NewHomeFragment();
//        learningFragment = new LearningFragment();
        learningFragment = new LearningFragment();
        newTestFragment = new NewTestFragment();
        userFragment = new UserFragment();
        //添加fragment集合
        fragmentList.add(newHomeFragment);
//        fragmentList.add(learningFragment);
        fragmentList.add(learningFragment);
        fragmentList.add(newTestFragment);
        fragmentList.add(userFragment);
        //添加radioButton集合
        radioButtonList.add(homePager);
        radioButtonList.add(lessonCenter);
        radioButtonList.add(testCenter);
        radioButtonList.add(userCenter);
        manager.beginTransaction().add(R.id.content_home, newHomeFragment).show(newHomeFragment).commit();
        //进来默认选中颜色
        radioButtonList.get(0).setTextColor(getResources().getColor(R.color.text_bg));
        radioButtonList.get(0).setChecked(true);
        radioButtonList.get(0).setSelected(true);

        String moreTiKu = getIntent().getStringExtra("moreTiKu");
        if (moreTiKu == null) {
            moreTiKu = "testTiKu";
        }
        isLoginActivity = getIntent().getStringExtra("isLoginActivity");
        if (isLoginActivity == null) {
            isLoginActivity = "false";
        }
        if (!moreTiKu.equals("moreTiKu") || (!isLoginActivity.equals("true"))) {
            initAppVersion();
        }

    }

    private void hideAndShow(int position) {

        FragmentTransaction transaction = manager.beginTransaction();
        Log.i("=======================","进来了....."+fragmentList.get(position).isAdded());
        if (fragmentList.get(position).isAdded()) {
            transaction.hide(fragmentList.get(prePosition)).show(fragmentList.get(position)).commit();

        } else {
            transaction.add(R.id.content_home, fragmentList.get(position))
                    .hide(fragmentList.get(prePosition))
                    .show(fragmentList.get(position)).commit();

        }
        //点击后字体变颜色
         radioButtonList.get(prePosition).setTextColor(Color.GRAY);
//        radioButtonList.get(position).setTextColor(Color.parseColor("#0075c2"));//选中字体变蓝色
        radioButtonList.get(position).setTextColor(getResources().getColor(R.color.text_bg));
        prePosition = position;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventForImage(Integer num) {
        if (num != null) {
            if (num == 0 || num == 1 || num == 2 || num == 3 || num == 4 || num == 5) {
                // manager.beginTransaction().add(R.id.content_home,lessonFragment).hide(homeFragment).show(lessonFragment).commit();
                radioButtonList.get(1).setSelected(true);
                radioButtonList.get(0).setSelected(false);
                hideAndShow(1);
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //   DataSet.saveData();
        Log.e("mainDestroy111111111", "qqqqqq");


    }

    @Override
    protected void onDestroy() {
        //  System.exit(0);
        if (popupWindow != null) {

            popupWindow.dismiss();

        }

        Log.e("aaaaaaaaaa", "onDestroy");
        EventBus.getDefault().unregister(this);
        super.onDestroy();

        stopDownloadService();
        DownloadController.setBackDownload(false);

    }


    private void stopDownloadService() {
        Intent intent = new Intent(this, DownloadService.class);
        stopService(intent);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}