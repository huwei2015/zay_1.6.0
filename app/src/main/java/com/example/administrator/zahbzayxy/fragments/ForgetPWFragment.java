package com.example.administrator.zahbzayxy.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.ModifyPWBean;
import com.example.administrator.zahbzayxy.beans.PhoneCodeBean;
import com.example.administrator.zahbzayxy.beans.PhoneExistBean;
import com.example.administrator.zahbzayxy.interfacecommit.RegisterInterface;
import com.example.administrator.zahbzayxy.utils.BinaryCastUtils;
import com.example.administrator.zahbzayxy.utils.KeyboardUtil;
import com.example.administrator.zahbzayxy.utils.RegisterUtil;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;
import com.google.gson.Gson;

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

public class ForgetPWFragment extends Fragment {

    //手机输入框
    @BindView(R.id.phone_et_forgetPw)
    EditText mPhone_et;
    //验证码输入框
    @BindView(R.id.getCode_et_forgetPw)
    EditText mCode_et;
    //设置密码
    @BindView(R.id.setPW_et_forgetPw)
    EditText mSetPW_et;
    //重置密码
    @BindView(R.id.againPW_et_forgetPw)
    EditText mResetPW_et;
    // 获取验证码按钮
    @BindView(R.id.getCode_bt_forgetPw)
    Button mGetCode_bt;

    private Unbinder mUnbinder;
    private Context mContext;
    private String mStr_codeFromNet; // 从网络上获取的短信验证码
    private String mStr_code; // editText用户输入的短信验证码
    private String mStr_phoneNum; //editText用户输入的手机号
    private String mStr_pw;     // //editText用户输入的密码
    private static final int CODE_MESSAGE_WHAT = 1;
    private static final int TIME_CODE = 60; // 每隔60s可重新获取验证码
    private int mCode_time = TIME_CODE; // 当前剩余秒数
    private Toolbar forgetPW_toolBar;
    View view;
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_forget_pw, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.check_bt_forgetPw, R.id.getCode_bt_forgetPw,R.id.forgetPWBack_iv})
    public void onClick(View view) {
//        KeyboardUtil.hideKeyBoardForFragment(this);
        switch (view.getId()) {
            // 确认
            case R.id.check_bt_forgetPw:
                if (checkInfo()) {
                    requestCheck();
                }
                break;

            // 获取验证码
            case R.id.getCode_bt_forgetPw:
                mStr_phoneNum = mPhone_et.getText().toString();
                getCode(mStr_phoneNum);
                break;
            //销毁当前页
            case R.id.forgetPWBack_iv:
                ((Activity)mContext).finish();
                break;
        }

    }

    /**
     * 获取验证码
     */
    private void getCode(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            if (RegisterUtil.isPhone(phoneNum)) {
                requestCode();
            } else {
                Toast.makeText(mContext, "请检查手机号是否正确！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mContext, "请输入手机号！", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 检查填写信息是否正确
     *
     * @return 验证信息正确返回true，否则为false
     */
    private boolean checkInfo() {
        mStr_phoneNum = mPhone_et.getText().toString();
        mStr_pw = mSetPW_et.getText().toString();
        mStr_code = mCode_et.getText().toString();
        String str_resetPw = mResetPW_et.getText().toString();
        if (TextUtils.isEmpty(mStr_phoneNum)) {
            Toast.makeText(mContext, "请输入手机号！", Toast.LENGTH_SHORT).show();
        } else if (!RegisterUtil.isPhone(mStr_phoneNum)) {
            Toast.makeText(mContext, "请检查手机号是否正确！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mStr_code)) {
            Toast.makeText(mContext, "请获取验证码！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mStr_codeFromNet)) {
            Toast.makeText(mContext, "请稍后重新获取验证码！", Toast.LENGTH_SHORT).show();
        } else if (!mStr_code.equals(mStr_codeFromNet)) {
            Toast.makeText(mContext, "请输入正确的验证码！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mStr_pw)) {
            Toast.makeText(mContext, "请输入密码！", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_resetPw)) {
            Toast.makeText(mContext, "请再次输入密码！", Toast.LENGTH_SHORT).show();
        } else if (!mStr_pw.equals(str_resetPw)) {
            Toast.makeText(mContext, "两次输入的密码不相同！", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    /**
     * 确认
     */
    private void requestCheck() {
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

        RegisterInterface registerInterface = RetrofitUtils.getInstance().createClass(RegisterInterface.class);
        Call<ModifyPWBean> call = registerInterface.modifyPw(mPw, mStr_phoneNum, mStr_code);
        call.enqueue(new Callback<ModifyPWBean>() {
            @Override
            public void onResponse(Call<ModifyPWBean> call, Response<ModifyPWBean> response) {
                String s = new Gson().toJson(response);
                Log.e("changePw",s);
                if (response != null && response.body() != null) {
                    ModifyPWBean modifyPWBean = response.body();
                    if (modifyPWBean.isData()==true) {
                        Toast.makeText(mContext,"修改密码成功",Toast.LENGTH_SHORT).show();
                        // 注册成功之后退出注册界面
                        ((Activity) mContext).finish();
                    } else {
                        Toast.makeText(mContext, "找回密码失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModifyPWBean> call, Throwable t) {
                Toast.makeText(mContext, "找回密码失败，请稍检查网络！", Toast.LENGTH_SHORT).show();
            }
        });


    }


    /**
     * 获取短信验证码
     */
    private void requestCode() {
        final RegisterInterface registerInterface = RetrofitUtils.getInstance().createClass(RegisterInterface.class);
        Call<PhoneExistBean> call = registerInterface.checkPhoneIsExist(mStr_phoneNum);
        call.enqueue(new Callback<PhoneExistBean>() {
            @Override
            public void onResponse(Call<PhoneExistBean> call, Response<PhoneExistBean> response) {
                if (response != null && response.body() != null) {
                    PhoneExistBean body = response.body();
                    boolean data = body.isData();
                    // 返回 true ， 即代表手机号已经被注册
                    if (!data) {
                        Toast.makeText(mContext, "该手机号还没注册！", Toast.LENGTH_LONG).show();
                    } else {
                        Call<PhoneCodeBean> codeBeanCall = registerInterface.getForgetPwVerCode(mStr_phoneNum);
                        codeBeanCall.enqueue(new Callback<PhoneCodeBean>() {
                            @Override
                            public void onResponse(Call<PhoneCodeBean> call, Response<PhoneCodeBean> response) {
                                if (response != null && response.body() != null) {
                                    PhoneCodeBean phoneCodeBean = response.body();
                                    mStr_codeFromNet = phoneCodeBean.getData();
                                    mGetCode_bt.setEnabled(false);
                                    mDelayHandler.sendEmptyMessage(CODE_MESSAGE_WHAT);
                                }
                            }
                            @Override
                            public void onFailure(Call<PhoneCodeBean> call, Throwable t) {
                                Toast.makeText(mContext, "获取验证码失败，请检查网络！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<PhoneExistBean> call, Throwable t) {
                Toast.makeText(mContext, "手机号验证失败，请检查网络！", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mDelayHandler.removeMessages(CODE_MESSAGE_WHAT);
            mUnbinder.unbind();
        }
    }


}
