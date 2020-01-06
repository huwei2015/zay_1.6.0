package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.administrator.zahbzayxy.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huwei.
 * Data 2019/5/29.
 * Time 14:16.
 * Description. 引导页
 */
public class GuideActivity extends AppCompatActivity {
   private SharedPreferences sharedPreferences;
   private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_guide);
        sharedPreferences =this.getSharedPreferences("check",MODE_PRIVATE);
        editor =sharedPreferences.edit();
        final Intent it = new Intent(this, SplashActivity.class);
        //初始化跳转
        final Intent ittomain = new Intent(this, WelcomeActivity.class);
         Timer timer = new Timer();//创建一个定时器
        firstloadjudge(timer,it,ittomain);
    }
    public void firstloadjudge(Timer timer, Intent it,Intent ittomain) {
        boolean fristload=sharedPreferences.getBoolean("fristload", true);//从SharedPreferences中获取是否第一次启动   默认为true
        if (fristload) {
            delaytowel(timer,it);//第一次进入延迟2秒后进入WelcomeActivity
            editor.putBoolean("fristload", false);//第一次启动后，将firstload 置为false 以便以后直接进入主界面不再显示欢迎界面
            editor.commit();     //提交，执行操作
        }
        else {
//  Toast.makeText(MainActivity.this, "你不是第一次进入，应进入app主界面", Toast.LENGTH_SHORT).show();
            delaytomain(timer,ittomain);   //延时并且进入主界面
        }

    }

    public void delaytowel(Timer timer,final Intent it)// 延时并进入welcome的方法
    {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                startActivity(it); //执行进入
            }
        };
        timer.schedule(task, 1000 * 1);//1秒后就进入welcome
    }
    public void delaytomain(Timer timer,final Intent ittomain)//延时并进入主界面的方法
    {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                startActivity(ittomain); //执行进入
            }
        };
        timer.schedule(task, 1000 * 1);//1秒后就进入main
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
