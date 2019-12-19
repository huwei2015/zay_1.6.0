package com.example.administrator.zahbzayxy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.MainActivity;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.LoginBean;
import com.example.administrator.zahbzayxy.beans.PhoneCodeBean;
import com.example.administrator.zahbzayxy.interfacecommit.LoginService;
import com.example.administrator.zahbzayxy.utils.BinaryCastUtils;
import com.example.administrator.zahbzayxy.utils.KeyboardUtil;
import com.example.administrator.zahbzayxy.utils.RegisterUtil;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BindingWechatLoginActivity extends AppCompatActivity {

    //手机输入框
    @BindView(R.id.phone_et_forgetPw)
    EditText mPhone_et;
    //验证码输入框
    @BindView(R.id.getCode_et_forgetPw)
    EditText mCode_et;
    //设置密码
    @BindView(R.id.setPW_et_forgetPw)
    EditText mSetPW_et;
    // 获取验证码按钮
    @BindView(R.id.getCode_bt_forgetPw)
    Button mGetCode_bt;

    private Unbinder mUnbinder;
    private String mStr_code; // editText用户输入的短信验证码
    private String mStr_phoneNum; //editText用户输入的手机号
    private String mStr_pw;     // //editText用户输入的密码
    private static final int CODE_MESSAGE_WHAT = 1;
    private static final int TIME_CODE = 60; // 每隔60s可重新获取验证码
    private int mCode_time = TIME_CODE; // 当前剩余秒数
    private Toolbar forgetPW_toolBar;
    private String getCode;
    private String getLoginMethod;
    // 点击获取验证码之后倒计时
    private Handler mDelayHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == CODE_MESSAGE_WHAT) {
                if (mCode_time == 0) {
                    mCode_time = TIME_CODE;
                    mGetCode_bt.setEnabled(true);
                    mGetCode_bt.setText("获取验证码");
                } else {
                    mGetCode_bt.setText("还剩" + mCode_time + "秒");
                    --mCode_time;
                    mDelayHandler.sendEmptyMessageDelayed(CODE_MESSAGE_WHAT, 1000);
                    return true;
                }
            }
            return false;
        }
    });
    private Toolbar toolBar;
    private String uid;
    private String weChatHeadImg;
    private String weChatName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_wechat_login);
        mUnbinder = ButterKnife.bind(this, BindingWechatLoginActivity.this);
        uid = getIntent().getStringExtra("uid");
        weChatHeadImg = getIntent().getStringExtra("weChatHeadImg");
        weChatName = getIntent().getStringExtra("weChatName");
    }



    @OnClick({R.id.bindingNow_bt, R.id.getCode_bt_forgetPw,R.id.bindingPhoneNum_iv})
    public void onClick(View view) {
        KeyboardUtil.hideKeyBoardForAct(BindingWechatLoginActivity.this);
        switch (view.getId()) {
            // 确认
            case R.id.bindingNow_bt:
                if (checkInfo()){
                    Log.e("uidCode","33333");
                    mStr_pw=mSetPW_et.getText().toString();
                    if (!TextUtils.isEmpty(getCode)&&!TextUtils.isEmpty(mStr_pw)){
                        if (!TextUtils.isEmpty(getCode)){
                            Log.e("uidCode","44444");
                            bindWechatUid(getCode);

                        }
                    }
                }
                break;

            // 获取验证码
            case R.id.getCode_bt_forgetPw:
                mStr_phoneNum = mPhone_et.getText().toString();
                getCode(mStr_phoneNum);
                break;
            //销毁当前页
            case R.id.bindingPhoneNum_iv:
                finish();
                break;
        }

    }
    /**
     * 获取验证码
     */
    private void getCode(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            if (RegisterUtil.isPhone(phoneNum)) {
                mGetCode_bt.setEnabled(false);
                mDelayHandler.sendEmptyMessage(CODE_MESSAGE_WHAT);
                requestCode();
            } else {
                Toast.makeText(BindingWechatLoginActivity.this, "请检查手机号是否正确！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(BindingWechatLoginActivity.this, "请输入手机号！", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 获取短信验证码
     */
    private void requestCode() {
        LoginService aClass = RetrofitUtils.getInstance().createClass(LoginService.class);
        aClass.bindingWechatCode(mStr_phoneNum).enqueue(new Callback<PhoneCodeBean>() {
            @Override
            public void onResponse(Call<PhoneCodeBean> call, Response<PhoneCodeBean> response) {
                int code = response.code();
                if (code == 200) {
                    PhoneCodeBean body = response.body();
                    if (body != null) {
                        String code1 = body.getCode();
                        Log.e("bingdingCose", code1);
                        if (!TextUtils.isEmpty(code1)) {
                            if (code1.equals("00041")) {
                                Toast.makeText(BindingWechatLoginActivity.this, "手机号未注册，请先注册", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BindingWechatLoginActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            } else if (code1.equals("00040")) {
                                mGetCode_bt.setEnabled(false);
                                Toast.makeText(BindingWechatLoginActivity.this, "手机号已绑定其它微信号", Toast.LENGTH_SHORT).show();
                            } else if (code1.equals("00000")) {
                                // Toast.makeText(BindingWechatLoginActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                                getCode = body.getData();

                            } else if (code1.equals("99999")) {
                                Toast.makeText(BindingWechatLoginActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }


            @Override
            public void onFailure(Call<PhoneCodeBean> call, Throwable t) {

            }
        });
    }
    private void bindWechatUid(final String code) {
        if (checkInfo()) {
            String mPw = null;
            MessageDigest messageDigest = null;

            try {
                messageDigest = MessageDigest.getInstance("MD5");
                byte[] digest = messageDigest.digest(mStr_pw.getBytes("utf-8"));
                mPw = BinaryCastUtils.parseByte2HexStr(digest);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.e("mpW",mPw);
            LoginService aClass1 = RetrofitUtils.getInstance().createClass(LoginService.class);
            final String finalMPw = mPw;
            aClass1.bindingWechatUid(mPw, mStr_phoneNum, uid, getCode).enqueue(new Callback<LoginBean>() {
                @Override
                public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                    int code1 = response.code();
                    if (code1 == 200) {
                        Log.e("uidCode","555555");
                        LoginBean body = response.body();
                        String code2 = body.getCode();
                        Log.e("uidCode",code2+"66666");
                        Log.e("uidCode","66666"+","+ finalMPw +","+mStr_phoneNum+","+uid+","+getCode);
                        if (code2.equals("00000")) {
                            Toast.makeText(BindingWechatLoginActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                            LoginBean.DataBean data = body.getData();
                            String token = data.getToken();
                            SharedPreferences sp = getSharedPreferences("tokenDb", MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("token",token );
                            edit.putString("weChatHeadImg",weChatHeadImg);
                            edit.putString("weChatName",weChatName);
                            edit.putBoolean("isLogin",true);
                            edit.putBoolean("wechatLogin",true);
                            edit.commit();
                            EventBus.getDefault().post(weChatHeadImg);
                            if (getLoginMethod.equals("homeLogined")){
                                Intent intent=new Intent(BindingWechatLoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        } else if (code2.equals("00040")) {
                            Toast.makeText(BindingWechatLoginActivity.this, "手机号已绑定其它微信号", Toast.LENGTH_SHORT).show();

                        } else if (code2.equals("00041")) {
                            Toast.makeText(BindingWechatLoginActivity.this, "手机号未注册", Toast.LENGTH_SHORT).show();

                        } else if (code2.equals("00005")) {
                            Toast.makeText(BindingWechatLoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();

                        }else if (code2.equals("99999")){
                            Toast.makeText(BindingWechatLoginActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<LoginBean> call, Throwable t) {

                }
            });

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventForImage(String loginMethod){
        if (!TextUtils.isEmpty(loginMethod)){
            if (loginMethod.equals("homeLogined")){
               getLoginMethod=loginMethod;

            }
        }

    }

    private boolean checkInfo() {
        mStr_phoneNum = mPhone_et.getText().toString();
        mStr_pw = mSetPW_et.getText().toString();
        mStr_code = mCode_et.getText().toString();
        if (TextUtils.isEmpty(mStr_phoneNum)) {
            Toast.makeText(BindingWechatLoginActivity.this, "请输入手机号！", Toast.LENGTH_SHORT).show();
        } else if (!RegisterUtil.isPhone(mStr_phoneNum)) {
            Toast.makeText(BindingWechatLoginActivity.this, "请检查手机号是否正确！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mStr_code)) {
            Toast.makeText(BindingWechatLoginActivity.this, "请获取验证码！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mStr_code)) {
            Toast.makeText(BindingWechatLoginActivity.this, "请稍后重新获取验证码！", Toast.LENGTH_SHORT).show();
        } else if (!mStr_code.equals(getCode)) {
            Toast.makeText(BindingWechatLoginActivity.this, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mStr_pw)) {
            Toast.makeText(BindingWechatLoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mDelayHandler.removeMessages(CODE_MESSAGE_WHAT);
            mUnbinder.unbind();
        }
    }
}
