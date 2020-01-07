package com.example.administrator.zahbzayxy.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.zahbzayxy.R;
import com.example.administrator.zahbzayxy.beans.PhoneCodeBean;
import com.example.administrator.zahbzayxy.beans.PhoneExistBean;
import com.example.administrator.zahbzayxy.beans.RegisterBackBean;
import com.example.administrator.zahbzayxy.interfacecommit.RegisterInterface;
import com.example.administrator.zahbzayxy.utils.BinaryCastUtils;
import com.example.administrator.zahbzayxy.utils.RegisterUtil;
import com.example.administrator.zahbzayxy.utils.RetrofitUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    //手机输入框
    @BindView(R.id.phone_et)
    EditText mPhone_et;
    //验证码输入框
    @BindView(R.id.yanZhengCode_et)
    EditText mCode_et;
    //设置密码
    @BindView(R.id.resetPW_et)
    EditText mSetPW_et;
    //确认密码
    @BindView(R.id.aginPW_et)
    EditText mResetPW_et;
    // 获取验证码按钮
    @BindView(R.id.getCode_bt)
    TextView mGetCode_bt;

    private Unbinder mUnbinder;
    private Context mContext;
    private String mStr_codeFromNet; // 从网络上获取的短信验证码
    private String mStr_code; // editText用户输入的短信验证码
    private String mStr_phoneNum; //editText用户输入的手机号
    private String mStr_pw;     // //editText用户输入的密码
    private static final int CODE_MESSAGE_WHAT = 1;
    private static final int TIME_CODE = 60; // 每隔60s可重新获取验证码
    private int mCode_time = TIME_CODE; // 当前剩余秒数
    private byte[] mDigest;
    View popView;
    PopupWindow popupWindow;
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
        View view = inflater.inflate(R.layout.regester_layout, container, false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({R.id.getCode_bt, R.id.register_bt, R.id.back_register})
    public void onClick(View view) {
//        KeyboardUtil.hideKeyBoardForFragment(this);
        switch (view.getId()) {
            // 注册
            case R.id.register_bt:
                if (checkInfo()) {
                    requestRegis();
                }
                break;

            // 获取验证码
            case R.id.getCode_bt:
                mStr_phoneNum = mPhone_et.getText().toString();
                getCode(mStr_phoneNum);
                break;

            // 返回
            case R.id.back_register:
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
               // mGetCode_bt.setEnabled(false);
              //  mDelayHandler.sendEmptyMessage(CODE_MESSAGE_WHAT);
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
        } /*else if (TextUtils.isEmpty(mStr_codeFromNet)) {
            Toast.makeText(mContext, "请稍后重新获取验证码！", Toast.LENGTH_SHORT).show();
        }*/ else if (!mStr_code.equals(mStr_codeFromNet)) {
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
     * 注册
     */
    private void requestRegis() {
        RegisterInterface registerInterface = RetrofitUtils.getInstance().createClass(RegisterInterface.class);
        //加密
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            mDigest = messageDigest.digest(mStr_pw.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String sPw = BinaryCastUtils.parseByte2HexStr(mDigest);

        Call<RegisterBackBean> call = registerInterface.register(mStr_phoneNum,sPw , 2, mStr_code);
        call.enqueue(new Callback<RegisterBackBean>() {
            @Override
            public void onResponse(Call<RegisterBackBean> call, Response<RegisterBackBean> response) {
                if (response != null) {
                    RegisterBackBean body = response.body();
                    if (body != null) {
                        String code = body.getCode();
                        if (code.equals("00000")) {
                            Toast.makeText(mContext, "注册成功", Toast.LENGTH_LONG).show();
                            boolean data = body.getData();
                            if (data==true){//有优惠卷
                                initYouHuiJuanDiaLog();

                            }else {//无优惠卷
                                // 注册成功之后退出注册界面
                                ((Activity) mContext).finish();
                            }


                        } else {
                            Object errMsg = body.getErrMsg();
                            if (errMsg!=null) {
                                Toast.makeText(mContext, errMsg.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterBackBean> call, Throwable t) {
                Toast.makeText(mContext, "注册失败，请检查网络！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initYouHuiJuanDiaLog() {
        popView = LayoutInflater.from(mContext).inflate(R.layout.pop_yhj_register_layout, null, false);
        ImageView yhj_iv= (ImageView) popView.findViewById(R.id.yhj_register_iv);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        popupWindow.setTouchable(true);
        // 设置该属性 点击 popUpWindow外的 区域 弹出框会消失
        popupWindow.setOutsideTouchable(true);
        // 配合 点击外部区域消失使用 否则 没有效果
        popupWindow.showAtLocation(popView, Gravity.CENTER, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        yhj_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow!=null){
                    if (popupWindow.isShowing()){
                        popupWindow.dismiss();
                        // 注册成功之后退出注册界面
                        ((Activity) mContext).finish();
                    }
                }
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
                    if (data==true) {
                        Toast.makeText(mContext, "该手机号已被注册！", Toast.LENGTH_LONG).show();

                    } else {
                        mGetCode_bt.setEnabled(false);
                        mDelayHandler.sendEmptyMessage(CODE_MESSAGE_WHAT);
                        Call<PhoneCodeBean> codeBeanCall = registerInterface.getRegisterVerCode(mStr_phoneNum);
                        codeBeanCall.enqueue(new Callback<PhoneCodeBean>() {
                            @Override
                            public void onResponse(Call<PhoneCodeBean> call, Response<PhoneCodeBean> response) {
                                if (response != null && response.body() != null) {
                                    PhoneCodeBean phoneCodeBean = response.body();
                                    //从网络获取的验证码
                                    mStr_codeFromNet = phoneCodeBean.getData();
                                    if (mStr_codeFromNet==null){
                                        Toast.makeText(mContext,"请稍后重新获取验证码",Toast.LENGTH_LONG).show();
                                    }
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
               // Toast.makeText(mContext, "手机号验证失败，请检查网络！", Toast.LENGTH_LONG).show();
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
        if (popupWindow!=null){
            if (popupWindow.isShowing()){
                popupWindow.dismiss();
            }
        }
    }

}
