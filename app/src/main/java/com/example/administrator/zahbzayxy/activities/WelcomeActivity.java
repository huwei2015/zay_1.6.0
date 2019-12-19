package com.example.administrator.zahbzayxy.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.UserInfoBean;
import com.example.administrator.zahbzayxy.interfacecommit.UserInfoInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends BaseActivity {
    private String token;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           // Intent intent=new Intent(WelcomeActivity.this, ChangeAppActivity.class);
            SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
            token = tokenDb.getString("token", "");
            initToken();

//            //先判断是否登录
//            boolean b = dbIsLogin();
//            if (b==false){
//                Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
//               // Intent intent=new Intent(WelcomeActivity.this,RegisterFaceActivity.class);
//                intent.putExtra("loginMethod","home");
//                startActivity(intent);
//                finish();
//                EventBus.getDefault().post("homeLogined");
//            } else if (b==true){
//                Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }else {
//                initToken();
//            }

        }
    };

    private void initToken() {
        UserInfoInterface userInfoInterface = RetrofitUtils.getInstance().createClass(UserInfoInterface.class);
        Call<UserInfoBean> userInfoData = userInfoInterface.getUserInfoData(token);
        userInfoData.enqueue(new Callback<UserInfoBean>() {
            @Override
            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                int code = response.code();
                if (code==200) {
                    UserInfoBean body = response.body();
                    if (body!=null){
                        String code1 = body.getCode();
                        if (code1.equals("99999")){
                            Toast.makeText(WelcomeActivity.this, "系统异常", Toast.LENGTH_SHORT).show();

                        }else if (code1.equals("00003")){//用户未登录
                            Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                            intent.putExtra("loginMethod","home");
                            startActivity(intent);
                            EventBus.getDefault().post("homeLogined");
                            finish();
                        }else if (dbIsLogin()==false){
                            Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                            // Intent intent=new Intent(WelcomeActivity.this,RegisterFaceActivity.class);
                            intent.putExtra("loginMethod","home");
                            startActivity(intent);
                            finish();
                            EventBus.getDefault().post("homeLogined");
                        }else if (dbIsLogin()==true){
                            Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoBean> call, Throwable t) {
                Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                // Intent intent=new Intent(WelcomeActivity.this,RegisterFaceActivity.class);
                intent.putExtra("loginMethod","home");
                startActivity(intent);
                finish();
                EventBus.getDefault().post("homeLogined");
            }
        });
    }

    private ImageView welcome_iv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }


        welcome_iv= (ImageView) findViewById(R.id.welcome_iv);
        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f, 1.5f, 1.0f,1.5f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f );
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(true);
        welcome_iv.startAnimation(scaleAnimation);
        handler.sendEmptyMessageDelayed(0,2000);


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
}
