package com.example.administrator.zahbzayxy.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.adapters.Lv1CateAdapter;
import com.example.administrator.zahbzayxy.adapters.QueslibAdapter;
import com.example.administrator.zahbzayxy.beans.CourseCatesBean;
import com.example.administrator.zahbzayxy.beans.QueslibBean;
import com.example.administrator.zahbzayxy.ccvideo.DownloadListActivity;
import com.example.administrator.zahbzayxy.interfacecommit.IndexInterface;
import com.example.administrator.zahbzayxy.utils.BaseActivity;
import com.example.administrator.zahbzayxy.utils.ProgressBarLayout;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.example.administrator.zahbzayxy.utils.Utils;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 线下课签到
 */
public class SignInActivity extends BaseActivity{

    private static String token;
    private TextView back_indexTV;
    private TextView courseNameTV;
    private ImageView signInBtn;

    private String resultString;
    private Integer courseId;
    private String courseName;

    private static final int SUCCESS_BACK=10;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        resultString = getIntent().getStringExtra("result");
        Log.i("resultString",""+resultString);
        Utils.setFullScreen(SignInActivity.this,getWindow());
        initView();
        getSP();
    }

    private void initView() {
        courseNameTV=(TextView) findViewById(R.id.courseNameTV);
        JSONObject obj=JSONObject.parseObject(resultString);
        if(obj.containsKey("courseId") && obj.containsKey("courseName")){
            courseId=obj.getInteger("courseId");
            courseName=obj.getString("courseName");
            courseNameTV.setText(courseName);
        }else{
            courseNameTV.setText("签到课程不存在");
        }

        back_indexTV=(TextView) findViewById(R.id.back_index);
        back_indexTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signInBtn=(ImageView) findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSignIn();
            }
        });
    }

    private void saveSignIn() {
        IndexInterface aClass = RetrofitUtils.getInstance().createClass(IndexInterface.class);
        aClass.saveSignIn(courseId,token).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                int code1 = response.code();
                JSONObject body = response.body();
                String s = new Gson().toJson(body);
                Log.i("ssssssssss",s);
                if (body != null && body.containsKey("code")) {
                    String code = body.getString("code");
                    if (!TextUtils.isEmpty(code)) {
                        if (code.equals("00003")) {
                            Toast.makeText(SignInActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putBoolean("isLogin", false);
                            edit.commit();
                        } else if (dbIsLogin() == false) {
                            Toast.makeText(SignInActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("99999")) {
                            Toast.makeText(SignInActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        } else if (code.equals("00000")) {
                            Intent intent = new Intent(SignInActivity.this, SigninSuccessActivity.class);
                            startActivityForResult(intent, SUCCESS_BACK);
                        } else {
                            Object errMsg = body.getString("errMsg");
                            if (errMsg != null) {
                                Toast.makeText(SignInActivity.this, "" + errMsg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                String message = t.getMessage();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case SUCCESS_BACK :
                if (resultCode == Activity.RESULT_OK) {
                   finish();
                }
                break;
            default:break;
        }
    }

    private void getSP() {
        SharedPreferences tokenDb = getSharedPreferences("tokenDb", MODE_PRIVATE);
        token = tokenDb.getString("token", "");
        Log.e("danWeiToken", token);
    }

    public Boolean dbIsLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("tokenDb", MODE_PRIVATE);
        boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
        if (isLogin == true) {
            return true;
        } else {
            return false;
        }
    }

}
